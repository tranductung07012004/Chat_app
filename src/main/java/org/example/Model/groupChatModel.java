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
    public static int createGroupChat(String groupName, int creatorId, List<Integer> memberIds) {
        String insertGroupQuery = "INSERT INTO group_chat (group_name, time_created) VALUES (?, ?) RETURNING group_id";
        String insertMemberQuery = "INSERT INTO group_chat_member (group_chat_id, group_member_id, isadminofgroup) VALUES (?, ?, ?)";

        try (Connection conn = DBConn.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Insert the group chat and get the generated group ID
            int groupId;
            try (PreparedStatement groupStmt = conn.prepareStatement(insertGroupQuery)) {
                groupStmt.setString(1, groupName);
                groupStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));

                try (ResultSet rs = groupStmt.executeQuery()) {
                    if (rs.next()) {
                        groupId = rs.getInt("group_id");
                    } else {
                        conn.rollback();
                        throw new SQLException("Failed to create group chat, no ID returned.");
                    }
                }
            }

            // Add the creator as an admin
            try (PreparedStatement memberStmt = conn.prepareStatement(insertMemberQuery)) {
                // Add the creator
                memberStmt.setInt(1, groupId);
                memberStmt.setInt(2, creatorId);
                memberStmt.setBoolean(3, true); // Admin flag
                memberStmt.executeUpdate();

                // Add additional members
                for (int memberId : memberIds) {
                    memberStmt.setInt(1, groupId);
                    memberStmt.setInt(2, memberId);
                    memberStmt.setBoolean(3, false); // Not admin
                    memberStmt.executeUpdate();
                }
            }

            conn.commit(); // Commit transaction
            return groupId;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Indicate failure
        }
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
        String query = "select g.group_name, g.time_created " +
                "from group_chat g " +
                "where group_name like ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán tham số cho câu truy vấn
            stmt.setString(1, group_name + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> friendsList = new ArrayList<>();
                while (rs.next()) {
                    String name_of_group = rs.getString("group_name");
                    Timestamp timeRegistered = rs.getTimestamp("time_created");
                    friendsList.add(new Object[]{name_of_group, timeRegistered.toString()});
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
        String query =  "select gc.group_name, eu.username" +
                " from group_chat gc " +
                " join group_chat_member gm on gc.group_id = gm.group_chat_id" +
                " join end_user eu on eu.user_id = gm.group_member_id" +
                " where gm.isadminofgroup is true and gc.group_name like ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán tham số cho câu truy vấn
            stmt.setString(1, group_name + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> groupAdmin = new ArrayList<>();
                while (rs.next()) {
                    String name_of_group = rs.getString("group_name");
                    String usernameofadmin = rs.getString("username");
                    groupAdmin.add(new Object[]{name_of_group, usernameofadmin});
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
        String query =  " select g.group_name, eu.username" +
                " from group_chat g " +
                " join group_chat_member gm on g.group_id = gm.group_chat_id" +
                " join end_user eu on eu.user_id = gm.group_member_id" +
                " where g.group_name like ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán tham số cho câu truy vấn
            stmt.setString(1, group_name + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> groupMember = new ArrayList<>();
                while (rs.next()) {
                    String name_of_group = rs.getString("group_name");
                    String usernameofmember = rs.getString("username");
                    groupMember.add(new Object[]{name_of_group, usernameofmember});
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

    public static boolean renameGroup(int groupId, String newGroupName) {
        String updateQuery = "UPDATE group_chat SET group_name = ? WHERE group_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            // Set parameters for the query
            stmt.setString(1, newGroupName); // New group name
            stmt.setInt(2, groupId);         // Group ID

            int rowsAffected = stmt.executeUpdate(); // Execute the update query
            return rowsAffected > 0; // Return true if any rows were updated

        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
            return false; // Indicate failure
        }
    }

}
