package org.example.Model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class userFriendModel {
    private int user_id;
    private int friend_id;

    public userFriendModel(int initUser_id, int initFriend_id) {
        this.user_id = initUser_id;
        this.friend_id = initFriend_id;
    }

    public static Object[][] getFriendOfUsername(String username) {
        String query = "SELECT eu1.username AS username, eu2.username AS friend_username " +
                "FROM user_friend uf " +
                "JOIN end_user eu1 ON uf.user_id = eu1.user_id " +
                "JOIN end_user eu2 ON uf.friend_id = eu2.user_id " +
                "WHERE eu1.username = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán tham số cho câu truy vấn
            stmt.setString(1, username);

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
}
