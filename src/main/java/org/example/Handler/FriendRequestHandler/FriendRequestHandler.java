package org.example.Handler.FriendRequestHandler;

import org.example.GUI.UserFriendRequest.FriendRequestFrame;
import org.example.Model.*;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestHandler {
    private final FriendRequestFrame frame;

    public FriendRequestHandler(FriendRequestFrame frame) {
        this.frame = frame;
    }

    public void handleAccept(friendRequestModel request) {
        String deleteRequestSql = "DELETE FROM friend_request WHERE from_user_id = ? AND target_user_id = ?";
        String addFriendSql = "INSERT INTO user_friend (user_id, friend_id) VALUES (?, ?)";

        try (Connection conn = DBConn.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteRequestSql);
                 PreparedStatement addFriendStmt = conn.prepareStatement(addFriendSql)) {

                // Delete the friend request
                deleteStmt.setInt(1, request.getFrom_user_id());
                deleteStmt.setInt(2, request.getTarget_user_id());
                deleteStmt.executeUpdate();

                // Add friends in both directions
                addFriendStmt.setInt(1, request.getFrom_user_id());
                addFriendStmt.setInt(2, request.getTarget_user_id());
                addFriendStmt.executeUpdate();

                addFriendStmt.setInt(1, request.getTarget_user_id());
                addFriendStmt.setInt(2, request.getFrom_user_id());
                addFriendStmt.executeUpdate();

                conn.commit();
//                JOptionPane.showMessageDialog(frame, "Friend request accepted!");
                frame.refreshRequests(friendRequestModel.loadFriendRequests(frame.mainFrame.getCurrentUserId()));
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleDecline(friendRequestModel request) {
        // Call deleteFriendRequest from the model
        if (friendRequestModel.deleteFriendRequest(request.getFrom_user_id(), request.getTarget_user_id())) {
//            JOptionPane.showMessageDialog(frame, "Friend request declined!");
            frame.refreshRequests(friendRequestModel.loadFriendRequests(frame.mainFrame.getCurrentUserId()));
        } else {
            JOptionPane.showMessageDialog(frame, "Error declining the friend request.");
        }
    }

    public void handleBack() {
        frame.mainFrame.showChatPanel(); // Navigate back to the previous screen
    }

    public void handleSearch(String query) {

        // Load all friend requests for the current user
        List<friendRequestModel> allRequests = friendRequestModel.loadFriendRequests(frame.mainFrame.getCurrentUserId());

        // Filter the requests based on the search query
        List<friendRequestModel> filteredRequests = new ArrayList<>();
        for (friendRequestModel request : allRequests) {
            endUserModel user = endUserModel.getUserFromId(request.getFrom_user_id());
            // Check if the username contains the search query (case-insensitive)
            boolean matchesUsername = user.getUsername().toLowerCase().contains(query.toLowerCase());
            boolean matchesAccountName = user.getAccountName().toLowerCase().contains(query.toLowerCase());

            // Convert the user_id to a string and check if it contains the search query (case-insensitive)
            boolean matchesUserId = String.valueOf(user.getUserId()).toLowerCase().contains(query.toLowerCase());

            // If either username or user_id matches the query, add the request to the filtered list (only once)
            if (matchesUsername || matchesUserId||matchesAccountName) {
                filteredRequests.add(request);
            }

        }

        // If no results found, show a message
        if (filteredRequests.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Không có lời mời kết bạn nào giống như thông tin bạn tìm kiếm!");
        } else {
            // Update the UI with the filtered results
            frame.refreshRequests(filteredRequests);
        }
    }


    public void handleFriendSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid search query!");
            return;
        }

        String sql = "SELECT * FROM end_user WHERE (username ILIKE ? OR account_name ILIKE ?) AND user_id != ?";
        List<endUserModel> results = new ArrayList<>();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");  // Search in both username and account_name
            stmt.setInt(3, frame.mainFrame.getCurrentUserId()); // Exclude current user

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");

                // Check if this user has been blocked by the current user
                if (blockModel.isBlocked(userId, frame.mainFrame.getCurrentUserId())) {
                    continue;  // Skip this user if they have blocked the current user
                }

                String username = rs.getString("username");
                String accountName = rs.getString("account_name");
                String password = rs.getString("pass");  // Assuming "pass" is the password column
                Date dob = rs.getDate("dob");
                String address = rs.getString("address");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                boolean isAdmin = rs.getBoolean("isAdmin");
                boolean online = rs.getBoolean("online");
                boolean blockedAccountByAdmin = rs.getBoolean("blockedAccountByAdmin");
                Timestamp timeRegistered = rs.getTimestamp("time_registered");

                if (blockedAccountByAdmin) {
                    continue;
                }

                // Create a new endUserModel object with all the fields
                endUserModel user = new endUserModel(userId, username, password, accountName, dob, address, gender, isAdmin, email, online, blockedAccountByAdmin, timeRegistered);
                results.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error searching for users.");
        }
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có người dùng nào thỏa mãn thông tin bạn tìm kiếm.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
        }
        frame.updateSearchResults(results); // Pass the result to updateSearchResults
    }

    public void handleSendFriendRequest(int targetUserId) {


        int currentUserId = frame.mainFrame.getCurrentUserId();

        // Call addFriendRequest from the model
        if (friendRequestModel.addFriendRequest(currentUserId, targetUserId)) {
//            JOptionPane.showMessageDialog(frame, "Friend request sent!");
        } else {
            JOptionPane.showMessageDialog(frame, "Error sending friend request.");
        }
    }

    public void handleBlockUser(int targetUserId) {

        int currentUserId = frame.mainFrame.getCurrentUserId();

        // Check if the user is already blocked
        if (blockModel.isBlocked(currentUserId, targetUserId)) {
            JOptionPane.showMessageDialog(frame, "User is already blocked.");
            return;
        }

        // Block the user
        boolean isBlocked = blockModel.addBlock(currentUserId, targetUserId);
        if (isBlocked) {
            //remove friend request before block
            if (friendRequestModel.isFriendRequestSent((currentUserId), targetUserId))
                friendRequestModel.deleteFriendRequest(currentUserId, targetUserId);
            //unfriend before block
            if (userFriendModel.isFriend(currentUserId, targetUserId))
                userFriendModel.deleteFriend(currentUserId, targetUserId);

//            JOptionPane.showMessageDialog(frame, "User blocked!");
        } else {
            JOptionPane.showMessageDialog(frame, "Error blocking user.");
        }
    }
    public void handleUnfriend(int targetUserId){
        int currentUserId = frame.mainFrame.getCurrentUserId();
        if (userFriendModel.isFriend(currentUserId, targetUserId))
            userFriendModel.deleteFriend(currentUserId, targetUserId);
    }

    public List<friendRequestModel> loadFriendRequests() {
        int currentUserId = frame.mainFrame.getCurrentUserId();
        return friendRequestModel.loadFriendRequests(currentUserId);
    }

    public boolean handleUnblockUser( int  targetID)
    {
        int userId=frame.mainFrame.getCurrentUserId();
        return blockModel.removeBlock(userId, targetID);
    }

    public boolean handleRetrieveFriendRequest(int targetUserID){
        return friendRequestModel.deleteFriendRequest(frame.mainFrame.getCurrentUserId(), targetUserID);
    }

}
