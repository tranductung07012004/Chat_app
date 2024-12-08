package org.example.Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class loginHistoryModel {
    private int login_id;
    private int user_id;
    private Timestamp login_time;

    public loginHistoryModel(int initLogin_id, int initUser_id, Timestamp initLogin_time) {
        this.login_id = initLogin_id;
        this.user_id = initUser_id;
        this.login_time = initLogin_time;
    }

    public static Object[][] getAllLoginHistory() {
        String query = " select lh.login_time, eu.username, eu.account_name" +
                       " from login_history lh " +
                       " join end_user eu on eu.user_id = lh.user_id";
        List<Object[]> loginHistoryList = new ArrayList<>();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    java.sql.Timestamp login_time = rs.getTimestamp("login_time");
                    String username = rs.getString("username");
                    String account_name = rs.getString("account_name");
                    loginHistoryList.add(new Object[]{login_time.toString(), username, account_name});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Kiểm tra danh sách bạn bè
        if (loginHistoryList.isEmpty()) {
            return new Object[0][0]; // Không có bạn bè
        }

        // Chuyển danh sách thành mảng 2 chiều
        return loginHistoryList.toArray(new Object[0][]);
    }

    public static Object[][] getLoginHistoryOfUsername(String username) {
        String checkUserQuery = "SELECT user_id FROM end_user WHERE username LIKE ?";
        String loginHistoryQuery = "SELECT eu.username, lh.login_time FROM login_history lh JOIN end_user eu ON lh.user_id = eu.user_id WHERE eu.username LIKE ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement checkUserStmt = conn.prepareStatement(checkUserQuery);
             PreparedStatement loginHistoryStmt = conn.prepareStatement(loginHistoryQuery)) {

            // Kiểm tra sự tồn tại của username
            checkUserStmt.setString(1, username + "%");
            try (ResultSet rs = checkUserStmt.executeQuery()) {
                if (!rs.next()) {
                    // Username không tồn tại
                    return null; // Trả về null để báo lỗi trong xử lý trên giao diện
                }
            }

            // Nếu username tồn tại, kiểm tra login history
            loginHistoryStmt.setString(1, username + "%");
            try (ResultSet rs = loginHistoryStmt.executeQuery()) {
                List<Object[]> loginHistoryList = new ArrayList<>();
                while (rs.next()) {
                    String rsUsername = rs.getString("username");
                    Timestamp loginTime = rs.getTimestamp("login_time");
                    loginHistoryList.add(new Object[]{rsUsername, loginTime.toString()});
                }

                if (loginHistoryList.isEmpty()) {
                    // Username tồn tại nhưng không có lịch sử đăng nhập
                    return new Object[0][0]; // Trả về mảng rỗng
                }

                // Chuyển danh sách thành mảng 2 chiều
                return loginHistoryList.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu xảy ra lỗi
        }
    }




}
