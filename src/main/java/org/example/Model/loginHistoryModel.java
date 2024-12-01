package org.example.Model;
import javax.swing.*;
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

    public static Object[][] getLoginHistoryOfUsername(String username) {
        String checkUserQuery = "SELECT user_id FROM end_user WHERE username = ?";
        String loginHistoryQuery = "SELECT lh.login_time FROM login_history lh JOIN end_user eu ON lh.user_id = eu.user_id WHERE eu.username = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement checkUserStmt = conn.prepareStatement(checkUserQuery);
             PreparedStatement loginHistoryStmt = conn.prepareStatement(loginHistoryQuery)) {

            // Kiểm tra sự tồn tại của username
            checkUserStmt.setString(1, username);
            try (ResultSet rs = checkUserStmt.executeQuery()) {
                if (!rs.next()) {
                    // Username không tồn tại
                    return null; // Trả về null để báo lỗi trong xử lý trên giao diện
                }
            }

            // Nếu username tồn tại, kiểm tra login history
            loginHistoryStmt.setString(1, username);
            try (ResultSet rs = loginHistoryStmt.executeQuery()) {
                List<Object[]> loginHistoryList = new ArrayList<>();
                while (rs.next()) {
                    Timestamp loginTime = rs.getTimestamp("login_time");
                    loginHistoryList.add(new Object[]{username, loginTime.toString()});
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
