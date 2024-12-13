package org.example.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class groupChatMemberModel {
    private int group_chat_id;
    private int group_member_id;
    private int isAdminOfGroup;

    public groupChatMemberModel(int initGroup_chat_id, int initGroup_member_id, int initIsAdminOfGroup) {
        this.group_chat_id = initGroup_chat_id;
        this.group_member_id = initGroup_member_id;
        this.isAdminOfGroup = initIsAdminOfGroup;
    }

    // Function to retrieve all members of a group as a list of endUserModel
    public static List<endUserModel> getGroupMembers(int groupId) {
        String query = "SELECT eu.user_id, eu.username, eu.password, eu.account_name, eu.dob, eu.address, eu.gender, " +
                "eu.email, eu.online, eu.is_admin, eu.blocked_account_by_admin, eu.time_registered, " +
                "gm.isadminofgroup " +
                "FROM group_chat_member gm " +
                "JOIN end_user eu ON gm.group_member_id = eu.user_id " +
                "WHERE gm.group_chat_id = ?";

        List<endUserModel> groupMembers = new ArrayList<>();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, groupId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String accountName = rs.getString("account_name");
                    Date dob = rs.getDate("dob");
                    String address = rs.getString("address");
                    String gender = rs.getString("gender");
                    String email = rs.getString("email");
                    boolean online = rs.getBoolean("online");
                    boolean isAdmin = rs.getBoolean("is_admin");
                    boolean blocked = rs.getBoolean("blocked_account_by_admin");
                    Timestamp timeRegistered = rs.getTimestamp("time_registered");
                    boolean isGroupAdmin = rs.getBoolean("isadminofgroup");

                    // Create endUserModel object and add to the list
                    endUserModel user = new endUserModel(userId, username, password, accountName, dob, address, gender,
                            isAdmin, email, online, blocked, timeRegistered);
                    groupMembers.add(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groupMembers;
    }
    public static boolean isGroupAdmin(int currentUserId, int groupId) {
        String query = "SELECT isadminofgroup FROM group_chat_member " +
                "WHERE group_member_id = ? AND group_chat_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, currentUserId);
            stmt.setInt(2, groupId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("isadminofgroup"); // Return true if admin
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Default to false if no admin status is found or an error occurs
    }
    public static List<String> getMemberList( int groupId) {
        String query = "SELECT eu.username, " +
                "CASE WHEN gm.isadminofgroup = true THEN 'Admin' ELSE 'Member' END AS role " +
                "FROM group_chat_member gm " +
                "JOIN end_user eu ON gm.group_member_id = eu.user_id " +
                "WHERE gm.group_chat_id = ?";

        List<String> groupMembers = new ArrayList<>();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the group ID parameter
            stmt.setInt(1, groupId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    String role = rs.getString("role");
                    groupMembers.add(username + " - " + role);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groupMembers;
    }
    public static boolean changeAdmin(int currentUserId, int targetUserId, int groupId) {
        // SQL query to update the admin status of both users
        String updateQuery = "UPDATE group_chat_member SET isadminofgroup = ? WHERE group_chat_id = ? AND group_member_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            // Set current user as not an admin
            stmt.setBoolean(1, false);  // currentUserId will no longer be an admin
            stmt.setInt(2, groupId);
            stmt.setInt(3, currentUserId);

            // Execute the update for the current user
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                return false; // If no rows were affected, it means the currentUserId was not found in the group
            }

            // Set target user as an admin
            stmt.setBoolean(1, true);   // targetUserId will be an admin
            stmt.setInt(2, groupId);
            stmt.setInt(3, targetUserId);

            // Execute the update for the target user
            rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if targetUserId's admin status was updated
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Return false if an error occurred or if no rows were affected
    }
    public static boolean removeMember(int currentUserId, int targetUserId, int groupId) {
        // First, check if the current user is an admin of the group
        if (!isGroupAdmin(currentUserId, groupId)) {
            // If the current user is not an admin, return false
            System.out.println("You do not have permission to remove members from the group.");
            return false;
        }

        // Check if the target user is in the group
        if (!isUserInGroup(targetUserId, groupId)) {
            // If the target user is not in the group, return false
            System.out.println("The user is not a member of the group.");
            return false;
        }

        // SQL query to delete the target user from the group
        String deleteQuery = "DELETE FROM group_chat_member WHERE group_chat_id = ? AND group_member_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            // Set parameters for the delete query
            stmt.setInt(1, groupId);
            stmt.setInt(2, targetUserId);

            // Execute the delete query
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User removed successfully from the group.");
                return true; // Return true if the target user was removed
            } else {
                System.out.println("Failed to remove user from the group.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Return false if an error occurred or no rows were affected
    }
    // Helper method to check if the user is in the group
    public static boolean isUserInGroup(int targetUserId, int groupId) {
        String query = "SELECT COUNT(*) FROM group_chat_member WHERE group_chat_id = ? AND group_member_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, groupId);
            stmt.setInt(2, targetUserId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if the user is found in the group
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean addMember(int groupId, int targetUserId, boolean isAdmin) {
        String query = "INSERT INTO group_chat_member (group_chat_id, group_member_id, isadminofgroup) " +
                "VALUES (?, ?, ?)"; // Default isadminofgroup to 0 (not an admin)

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, groupId);
            stmt.setInt(2, targetUserId);
            stmt.setBoolean(3, isAdmin);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if a row was inserted

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Return false if an error occurred
    }

}
