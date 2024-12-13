package org.example.Handler.ChatPanelHandler;

import org.example.GUI.ChatPanelGUI.ChatPanelFrame;
import org.example.Model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RightPanelButtonListener {

    public static void handleReportSpam(int currentUserId, int targetUserId) {


        // Simulate saving the report to the database or performing other logic
        boolean success = spamModel.saveSpamReport(currentUserId, targetUserId);

        // Show a message dialog to inform the user
        if (success) {
            JOptionPane.showMessageDialog(null, "Friend reported as spam successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to report friend as spam. Please try again.");
        }
    }

    public static void handleBlock(int currentUserId, int targetUserId) {


        // Check if the user is already blocked
        if (blockModel.isBlocked(currentUserId, targetUserId)) {
            JOptionPane.showMessageDialog(null, "User is already blocked.");
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
            JOptionPane.showMessageDialog(null, "Error blocking user.");
        }
    }
    public static void handleUnfriend(int currentUserId, int targetUserId){
        if (userFriendModel.isFriend(currentUserId, targetUserId))
            userFriendModel.deleteFriend(currentUserId, targetUserId);
    }
    public static boolean handleDeleteAllUserChat(int currentUserId, int targetUserId) {
        // Hiển thị hộp thoại xác nhận
        int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete all chat history with this user?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        // Nếu người dùng chọn "Yes", thực hiện xóa
        if (confirm == JOptionPane.YES_OPTION) {
            boolean isDeleted = messageOfUserModel.deleteChatHistory(currentUserId, targetUserId);

            if (isDeleted) {
                JOptionPane.showMessageDialog(null,
                        "Chat history deleted successfully.",
                        "Deletion Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Failed to delete chat history.",
                        "Deletion Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

            return isDeleted;
        } else {
            // Người dùng chọn "No"
            System.out.println("Deletion cancelled by user.");
            return false;
        }
    }

    public static boolean handleRenameGroup(int currentUserId, int groupId) {
        // Prompt the user to enter a new group name
        String newName = JOptionPane.showInputDialog(null, "Enter new group name:",
                "Rename Group", JOptionPane.QUESTION_MESSAGE);

        // If the user cancels or enters an invalid name, return false
        if (newName == null || newName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid group name. Rename canceled.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Call renameGroup() with the entered name
        boolean result = groupChatModel.renameGroup(groupId, newName.trim());

        // Notify user about the success/failure of renaming
        if (result) {
            JOptionPane.showMessageDialog(null, "Group renamed successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to rename group. Try again later.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return result;
    }


    public static List<String> handleMemberList(int groupid)
    {
        return groupChatMemberModel.getMemberList(groupid);
    }
    public static boolean handleChangeAdmin(int currentUserId, int groupId) {
        // Prompt the user to enter the targetUserId via an input dialog
        String input = JOptionPane.showInputDialog(null, "Enter the usrename to promote to Admin:");

        // Validate input
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "username cannot be empty.");
            return false;
        }
// Get the user ID based on the entered username
        int targetUserId = endUserModel.getUserIdByUsername(input);

        // Check if the user exists in the system
        if (targetUserId == -1) {
            JOptionPane.showMessageDialog(null, "The specified user does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the targetUserId is in the group
        if (!groupChatMemberModel.isUserInGroup(targetUserId, groupId)) {
            JOptionPane.showMessageDialog(null, "The user is not a member of the group.");
            return false;
        }

        // Proceed to change admin if the user is in the group
        boolean success = groupChatMemberModel.changeAdmin(currentUserId, targetUserId, groupId);
        if (success) {
            JOptionPane.showMessageDialog(null, "Admin status changed successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to change admin status.");
        }
        return success;
    }
    public static boolean handleRemoveMember(int currentUserId, int groupId) {
        // Pop-up dialog to enter the username of the member to remove
        String username = JOptionPane.showInputDialog(null, "Enter the username of the member to remove:",
                "Remove Member", JOptionPane.PLAIN_MESSAGE);

        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid username entered.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Get the user ID based on the entered username
        int targetUserId = endUserModel.getUserIdByUsername(username);

        // Check if the user exists in the system
        if (targetUserId == -1) {
            JOptionPane.showMessageDialog(null, "The specified user does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the current user is an admin of the group
        if (!groupChatMemberModel.isGroupAdmin(currentUserId, groupId)) {
            JOptionPane.showMessageDialog(null, "You do not have permission to remove members from this group.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the user is part of the group
        if (!groupChatMemberModel.isUserInGroup(targetUserId, groupId)) {
            JOptionPane.showMessageDialog(null, "The specified user is not a member of the group.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Proceed to remove the user from the group
        boolean success = groupChatMemberModel.removeMember(currentUserId, targetUserId, groupId);
        if (success) {
            JOptionPane.showMessageDialog(null, "Member removed from the group successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to remove member from the group.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }
    public static boolean handleAddMember(int currentUserId, int groupId) {
        // Pop-up dialog to enter the username
        String username = JOptionPane.showInputDialog(null, "Enter the username of the member to add:",
                "Add Member", JOptionPane.PLAIN_MESSAGE);

        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid username entered.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int id=endUserModel.getUserIdByUsername(username);
        // Check if the entered username is a friend of the current user
        boolean isFriend = userFriendModel.isFriend(currentUserId, id);
        if (id==-1 || !isFriend) {
            JOptionPane.showMessageDialog(null, "The specified user is not your friend.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Add the friend to the group
        boolean success = groupChatMemberModel.addMember(groupId, id);
        if (success) {
            JOptionPane.showMessageDialog(null, "Member added to the group successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add member to the group.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    public static boolean handleUnblockUser(int currentUserId, int groupId)
    {
        return blockModel.removeBlock(currentUserId, groupId);
    }

    public static boolean handleDeleteAllGroupChat(int currentUserId, int groupId) {
        // Hiển thị hộp thoại xác nhận
        int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete all chat history with this group?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        // Nếu người dùng chọn "Yes", thực hiện xóa
        if (confirm == JOptionPane.YES_OPTION) {
            boolean isDeleted = deletedMessageOfGroupModel.deleteAlltGroupChatHistory(currentUserId, groupId);

            if (isDeleted) {
                JOptionPane.showMessageDialog(null,
                        "Chat history deleted successfully.",
                        "Deletion Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Failed to delete chat history.",
                        "Deletion Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

            return isDeleted;
        } else {
            // Người dùng chọn "No"
            System.out.println("Deletion cancelled by user.");
            return false;
        }
    }

}

