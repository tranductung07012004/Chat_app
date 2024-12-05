package org.example.Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class groupChatModel {
    private int group_id;
    private String group_name;
    private Timestamp time_created;

    public groupChatModel(int initGroup_id, String initGroup_name, Timestamp initTime_created) {
        this.group_id = initGroup_id;
        this.group_name = initGroup_name;
        this.time_created = initTime_created;
    }

    public static Object[][] getAllGroupInfo() {
        String query = "select g.group_name, g.time_created " +
                      "from group_chat g ";
        List<Object[]> groupsList = new ArrayList<>();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String groupName = rs.getString("group_name");
                    Timestamp timeRegistered = rs.getTimestamp("time_created");
                    groupsList.add(new Object[]{groupName, timeRegistered.toString()});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Kiểm tra danh sách bạn bè
        if (groupsList.isEmpty()) {
            return new Object[0][0]; // Không có bạn bè
        }

        // Chuyển danh sách thành mảng 2 chiều
        return groupsList.toArray(new Object[0][]);
    }
    public static Object[][] getGroupByGroupName(String group_name) {
        String query = "select g.time_created " +
                       "from group_chat g " +
                       "where group_name = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán tham số cho câu truy vấn
            stmt.setString(1, group_name);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> friendsList = new ArrayList<>();
                while (rs.next()) {
                    Timestamp timeRegistered = rs.getTimestamp("time_created");
                    friendsList.add(new Object[]{group_name, timeRegistered.toString()});
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

    public static Object[][] getGroupAdminGroupName(String group_name) {
        String query =  "select eu.username" +
                        " from group_chat gc " +
                        " join group_chat_member gm on gc.group_id = gm.group_chat_id" +
                        " join end_user eu on eu.user_id = gm.group_member_id" +
                        " where gm.isadminofgroup is true and gc.group_name = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán tham số cho câu truy vấn
            stmt.setString(1, group_name);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> groupAdmin = new ArrayList<>();
                while (rs.next()) {
                    String usernameofadmin = rs.getString("username");
                    groupAdmin.add(new Object[]{group_name, usernameofadmin});
                }

                // Kiểm tra danh sách bạn bè
                if (groupAdmin.isEmpty()) {
                    return new Object[0][0]; // Không có bạn bè
                }

                // Chuyển danh sách thành mảng 2 chiều
                return groupAdmin.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu xảy ra lỗi
        }
    }

    public static Object[][] getMemberByGroupName(String group_name) {
        String query =  " select eu.username" +
                        " from group_chat g " +
                        " join group_chat_member gm on g.group_id = gm.group_chat_id" +
                        " join end_user eu on eu.user_id = gm.group_member_id" +
                        " where g.group_name = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán tham số cho câu truy vấn
            stmt.setString(1, group_name);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> groupMember = new ArrayList<>();
                while (rs.next()) {
                    String usernameofmember = rs.getString("username");
                    groupMember.add(new Object[]{group_name, usernameofmember});
                }

                // Kiểm tra danh sách bạn bè
                if (groupMember.isEmpty()) {
                    return new Object[0][0]; // Không có bạn bè
                }

                // Chuyển danh sách thành mảng 2 chiều
                return groupMember.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu xảy ra lỗi
        }
    }
}
