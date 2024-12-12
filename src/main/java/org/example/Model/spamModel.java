package org.example.Model;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class spamModel {
    private long spam_id;
    private int target_user;
    private Timestamp time_created;

    public spamModel(long initSpam_id, int initTarget_user, Timestamp initTime_created) {
        this.spam_id = initSpam_id;
        this.target_user = initTarget_user;
        this.time_created = initTime_created;
    }


    public static Object[][] getAllInfo() {
        String query = "SELECT eu.username, sp.time_created, eu.email, eu.blockedaccountbyadmin " +
                "FROM spam sp JOIN end_user eu ON sp.target_user = eu.user_id";
        List<Object[]> resultList = new ArrayList<>();

        // Sử dụng try-with-resources để đảm bảo đóng kết nối
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Lặp qua các dòng của ResultSet
            while (rs.next()) {
                // Lấy dữ liệu từ các cột
                boolean blocked = rs.getBoolean("blockedaccountbyadmin");
                String resultUserName = rs.getString("username");
                String resultEmail = rs.getString("email");
                Timestamp timeCreated = rs.getTimestamp("time_created");

                String blockedString = blocked ? "TRUE" : "FALSE";

                // Thêm dữ liệu vào danh sách
                resultList.add(new Object[]{resultUserName, timeCreated.toString().split("\\.")[0], resultEmail, blockedString});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Chuyển danh sách thành mảng 2 chiều
        return resultList.toArray(new Object[0][]);
    }

    public static Object[][] filterByUsername(String username) {
        String query = "SELECT eu.username, sp.time_created, eu.email, eu.blockedaccountbyadmin " +
                "FROM spam sp JOIN end_user eu ON sp.target_user = eu.user_id" +
                " WHERE eu.username LIKE ?";
        List<Object[]> resultList = new ArrayList<>();

        // Sử dụng try-with-resources để đảm bảo đóng kết nối
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);) {

            stmt.setString(1, username + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                // Lặp qua các dòng của ResultSet
                while (rs.next()) {
                    // Lấy dữ liệu từ các cột
                    boolean blocked = rs.getBoolean("blockedaccountbyadmin");
                    String resultUsername = rs.getString("username");
                    Timestamp timeCreated = rs.getTimestamp("time_created");
                    String blockedString = blocked ? "TRUE" : "FALSE";

                    // Thêm dữ liệu vào danh sách
                    resultList.add(new Object[]{resultUsername, timeCreated.toString().split("\\.")[0], blockedString});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // Chuyển danh sách thành mảng 2 chiều
        return resultList.toArray(new Object[0][]);
    }

    public static Object[][] filterByEmail(String email) {
        String query = "SELECT eu.username, sp.time_created, eu.email, eu.blockedaccountbyadmin " +
                "FROM spam sp JOIN end_user eu ON sp.target_user = eu.user_id" +
                " WHERE eu.email LIKE ?";
        List<Object[]> resultList = new ArrayList<>();

        // Sử dụng try-with-resources để đảm bảo đóng kết nối
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);) {

            stmt.setString(1, email + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                // Lặp qua các dòng của ResultSet
                while (rs.next()) {
                    // Lấy dữ liệu từ các cột
                    boolean blocked = rs.getBoolean("blockedaccountbyadmin");
                    String resultUsername = rs.getString("username");
                    String resultEmail = rs.getString("email");
                    Timestamp timeCreated = rs.getTimestamp("time_created");
                    String blockedString = blocked ? "TRUE" : "FALSE";

                    // Thêm dữ liệu vào danh sách
                    resultList.add(new Object[]{resultUsername, timeCreated.toString().split("\\.")[0], resultEmail, blockedString});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // Chuyển danh sách thành mảng 2 chiều
        return resultList.toArray(new Object[0][]);
    }

    public static int lockUser(String username) {
        String query = "UPDATE end_user SET blockedaccountbyadmin = ?, online = ? WHERE username = ?";

        int rowsAffected = 0;
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set các tham số cho PreparedStatement
            stmt.setBoolean(1, true);
            stmt.setBoolean(2, false);
            stmt.setString(3, username);

            // Thực thi câu lệnh cập nhật
            rowsAffected = stmt.executeUpdate();


        } catch (SQLException e) {
            System.err.println("An error occurred while trying to update the user: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0 ? 1 : 0;
    }

    public static Object[][] filterByDate(java.sql.Date sqlStartDate, java.sql.Date sqlEndDate) {
        String query = " SELECT eu.username, sp.time_created, eu.email, eu.blockedaccountbyadmin " +
                " FROM spam sp JOIN end_user eu on sp.target_user = eu.user_id" +
                " WHERE sp.time_created >= ? AND sp.time_created <= ?";
        List<Object[]> resultList = new ArrayList<>();

        // Sử dụng try-with-resources để đảm bảo đóng kết nối
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán tham số cho câu lệnh SQL
            stmt.setDate(1, sqlStartDate);
            stmt.setDate(2, sqlEndDate);

            // Thực thi truy vấn và xử lý kết quả
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Lấy dữ liệu từ các cột
                    String userName = rs.getString("username");
                    Timestamp timeCreated = rs.getTimestamp("time_created");
                    String resultEmail = rs.getString("email");
                    boolean blocked = rs.getBoolean("blockedaccountbyadmin");
                    String blockedString = blocked ? "TRUE" : "FALSE";

                    // Thêm dữ liệu vào danh sách
                    resultList.add(new Object[]{userName, timeCreated.toString().split("\\.")[0], resultEmail, blockedString});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu xảy ra lỗi
        }

        // Chuyển danh sách thành mảng 2 chiều
        return resultList.toArray(new Object[0][]);
    }

}
