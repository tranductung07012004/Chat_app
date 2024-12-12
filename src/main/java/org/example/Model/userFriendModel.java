package org.example.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class userFriendModel {
    private int user_id;
    private int friend_id;

    public userFriendModel(int user_id, int friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    // Fetch all friends of a user
    public static List<userFriendModel> loadUserFriends(int userId) {
        List<userFriendModel> friends = new ArrayList<>();
        String sql = "SELECT user_id, friend_id FROM USER_FRIEND WHERE user_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userId1 = rs.getInt("user_id");
                int friendId = rs.getInt("friend_id");
                friends.add(new userFriendModel(userId1, friendId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    // Add a new friendship
    public static boolean addFriend(int userId, int friendId) {
        String sql = "INSERT INTO USER_FRIEND (user_id, friend_id) VALUES (?, ?)";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if two users are friends
    public static boolean isFriend(int userId, int friendId) {
        String sql = "SELECT COUNT(*) AS friend_count FROM USER_FRIEND " +
                "WHERE (user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?)";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            stmt.setInt(3, friendId);
            stmt.setInt(4, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("friend_count") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete a friendship
    public static boolean deleteFriend(int userId, int friendId) {
        String sql = "DELETE FROM USER_FRIEND WHERE (user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?)";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            stmt.setInt(3, friendId);
            stmt.setInt(4, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object[][] getFriendOfUsername(String username) {
        String query = "SELECT eu1.username AS username, eu2.username AS friend_username " +
                "FROM user_friend uf " +
                "JOIN end_user eu1 ON uf.user_id = eu1.user_id " +
                "JOIN end_user eu2 ON uf.friend_id = eu2.user_id " +
                "WHERE eu1.username LIKE ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán tham số cho câu truy vấn
            stmt.setString(1, username + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> friendsList = new ArrayList<>();
                while (rs.next()) {
                    String user = rs.getString("username");
                    String friendUsername = rs.getString("friend_username");
                    friendsList.add(new Object[]{user, friendUsername});
                }

                // Kiểm tra danh sách bạn bè
                if (friendsList.isEmpty()) {
                    return new Object[0][0]; // Không có bạn bè
                }

                // Chuyển danh sách thành mảng 2 chiều
                return friendsList.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu xảy ra lỗi
        }
    }

    public static Object[][] getUserFriendListInfo(String filterByAccountname) {
        StringBuilder query = new StringBuilder("""
        WITH AdminFriends AS (
            SELECT uf.friend_id
            FROM user_friend uf
            JOIN end_user eu ON uf.user_id = eu.user_id
            WHERE eu.username = ?
        ),
        UserFriendCounts AS (
            SELECT 
                eu.user_id, 
                eu.username, 
                eu.account_name,  -- Thêm cột account_name
                eu.time_registered, 
                COUNT(uf.friend_id) AS friend_count
            FROM end_user eu
            LEFT JOIN user_friend uf ON eu.user_id = uf.user_id
            GROUP BY eu.user_id, eu.username, eu.account_name, eu.time_registered
        )
        SELECT 
            ufc.username AS username,
            ufc.account_name AS account_name,  -- Lấy account_name từ UserFriendCounts
            ufc.time_registered AS time_registered,
            ufc.friend_count AS friend_count,
            COUNT(DISTINCT af.friend_id) AS common_friends
        FROM UserFriendCounts ufc
        LEFT JOIN user_friend uf ON ufc.user_id = uf.user_id
        LEFT JOIN AdminFriends af ON uf.friend_id = af.friend_id
        WHERE 1 = 1
    """);

        // Thêm điều kiện nếu filterByUsername không rỗng
        if (!filterByAccountname.isEmpty()) {
            query.append(" AND ufc.account_name LIKE ? ");
        }

        query.append("""
        GROUP BY ufc.username, ufc.account_name, ufc.time_registered, ufc.friend_count
        ORDER BY ufc.username
    """);

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            // Gán tham số cho câu truy vấn
            stmt.setString(1, endUserModel.AdminSessionUsername);

            // Gán tham số cho filterByUsername nếu cần
            if (!filterByAccountname.isEmpty()) {
                stmt.setString(2, filterByAccountname + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> resultList = new ArrayList<>();

                while (rs.next()) {
                    String username = rs.getString("username");
                    if (username.equals(endUserModel.AdminSessionUsername)) {
                        continue;
                    }
                    String accountName = rs.getString("account_name"); // Lấy account_name
                    Timestamp timeRegistered = rs.getTimestamp("time_registered");
                    int friendCount = rs.getInt("friend_count");
                    int commonFriends = rs.getInt("common_friends");

                    resultList.add(new Object[]{username, accountName, timeRegistered.toString().split("\\.")[0], friendCount, commonFriends});
                }

                // Kiểm tra danh sách kết quả
                if (resultList.isEmpty()) {
                    return new Object[0][0]; // Không có kết quả
                }

                // Chuyển danh sách thành mảng 2 chiều
                return resultList.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu xảy ra lỗi
        }
    }

    public static Object[][] getUserFriendListInfo(String selectedOption, int num) {
        StringBuilder query = new StringBuilder("""
    WITH AdminFriends AS (
        SELECT uf.friend_id
        FROM user_friend uf
        JOIN end_user eu ON uf.user_id = eu.user_id
        WHERE eu.username = ?
    ),
    UserFriendCounts AS (
        SELECT 
            eu.user_id, 
            eu.username, 
            eu.account_name,  -- Thêm cột account_name
            eu.time_registered, 
            COUNT(uf.friend_id) AS friend_count
        FROM end_user eu
        LEFT JOIN user_friend uf ON eu.user_id = uf.user_id
        GROUP BY eu.user_id, eu.username, eu.account_name, eu.time_registered
    )
    SELECT 
        ufc.username AS username,
        ufc.account_name AS account_name,  -- Lấy account_name từ UserFriendCounts
        ufc.time_registered AS time_registered,
        ufc.friend_count AS friend_count,
        COUNT(DISTINCT af.friend_id) AS common_friends
    FROM UserFriendCounts ufc
    LEFT JOIN user_friend uf ON ufc.user_id = uf.user_id
    LEFT JOIN AdminFriends af ON uf.friend_id = af.friend_id
    GROUP BY ufc.username, ufc.account_name, ufc.time_registered, ufc.friend_count
    HAVING 1=1
    """);

        // Thêm điều kiện so với selectedOption và num
        if (selectedOption != null && !selectedOption.isEmpty()) {
            switch (selectedOption) {
                case "<":
                    query.append(" AND ufc.friend_count < ? ");
                    break;
                case ">":
                    query.append(" AND ufc.friend_count > ? ");
                    break;
                case "=":
                    query.append(" AND ufc.friend_count = ? ");
                    break;
                default:
                    throw new IllegalArgumentException("Selected option is invalid.");
            }
        }

        query.append("""
    ORDER BY ufc.username
    """);

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            // Gán tham số cho câu truy vấn
            stmt.setString(1, endUserModel.AdminSessionUsername);

            // Gán tham số cho num nếu có điều kiện so với friend_count
            if (selectedOption != null && !selectedOption.isEmpty()) {
                stmt.setInt(2, num);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> resultList = new ArrayList<>();

                while (rs.next()) {
                    String username = rs.getString("username");
                    if (username.equals(endUserModel.AdminSessionUsername)) {
                        continue;
                    }
                    String accountName = rs.getString("account_name"); // Lấy account_name
                    Timestamp timeRegistered = rs.getTimestamp("time_registered");
                    int friendCount = rs.getInt("friend_count");
                    int commonFriends = rs.getInt("common_friends");

                    resultList.add(new Object[]{username, accountName, timeRegistered.toString().split("\\.")[0], friendCount, commonFriends});
                }

                // Kiểm tra danh sách kết quả
                if (resultList.isEmpty()) {
                    return new Object[0][0]; // Không có kết quả
                }

                // Chuyển danh sách thành mảng 2 chiều
                return resultList.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu xảy ra lỗi
        }
    }






}
