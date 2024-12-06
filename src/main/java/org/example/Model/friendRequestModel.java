package org.example.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class friendRequestModel {
    private int from_user_id;
    private int target_user_id;
    private Timestamp time_created;

    public friendRequestModel(int from_user_id, int target_user_id, Timestamp time_created) {
        this.from_user_id = from_user_id;
        this.target_user_id = target_user_id;
        this.time_created = time_created;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }

    public int getTarget_user_id() {
        return target_user_id;
    }

    public Timestamp getTime_created() {
        return time_created;
    }

    // Fetch all friend requests for a user
    public static List<friendRequestModel> loadFriendRequests(int userId) {
        List<friendRequestModel> friendRequests = new ArrayList<>();
        String sql = "SELECT from_user_id, target_user_id, time_created FROM FRIEND_REQUEST WHERE target_user_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int fromUserId = rs.getInt("from_user_id");
                int targetUserId = rs.getInt("target_user_id");
                Timestamp timeCreated = rs.getTimestamp("time_created");
                friendRequests.add(new friendRequestModel(fromUserId, targetUserId, timeCreated));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendRequests;
    }

    // Add a new friend request
    public static boolean addFriendRequest(int fromUserId, int targetUserId) {
        String sql = "INSERT INTO FRIEND_REQUEST (from_user_id, target_user_id, time_created) VALUES (?, ?, NOW())";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fromUserId);
            stmt.setInt(2, targetUserId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a friend request
    public static boolean deleteFriendRequest(int fromUserId, int targetUserId) {
        String sql = "DELETE FROM FRIEND_REQUEST WHERE from_user_id = ? AND target_user_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fromUserId);
            stmt.setInt(2, targetUserId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Does this friend request exist?
    public static boolean isFriendRequestSent(int fromUserId, int targetUserId) {
        String query = "SELECT COUNT(*) AS request_count FROM friend_request WHERE from_user_id = ? AND target_user_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, fromUserId);
            stmt.setInt(2, targetUserId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("request_count") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
