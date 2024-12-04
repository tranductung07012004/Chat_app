package org.example.Model;

import java.sql.*;
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
}
