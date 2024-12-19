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
            JOptionPane.showMessageDialog(null, "Báo cáo spam thành công.");
        } else {
            JOptionPane.showMessageDialog(null, "Báo cáp thất bại, hãy thử lại sau.");
        }
    }

    public static void handleBlock(int currentUserId, int targetUserId) {


        // Check if the user is already blocked
        if (blockModel.isBlocked(currentUserId, targetUserId)) {
            JOptionPane.showMessageDialog(null, "Người dùng đã bị block");
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
            JOptionPane.showMessageDialog(null, "Lỗi.");
        }
    }
    public static void handleUnfriend(int currentUserId, int targetUserId){
        if (userFriendModel.isFriend(currentUserId, targetUserId))
            userFriendModel.deleteFriend(currentUserId, targetUserId);
    }
    public static boolean handleDeleteAllUserChat(int currentUserId, int targetUserId) {
        // Hiển thị hộp thoại xác nhận
        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn xoá lịch sử chat với người này chứ?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        // Nếu người dùng chọn "Yes", thực hiện xóa
        if (confirm == JOptionPane.YES_OPTION) {
            boolean isDeleted = messageOfUserModel.deleteChatHistory(currentUserId, targetUserId);

            if (isDeleted) {
                JOptionPane.showMessageDialog(null,
                        "Xoá lịch sử chat thành công.",
                        "Deletion Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Xoá lịch sử chat thất bại.",
                        "Deletion Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

            return isDeleted;
        } else {
            // Người dùng chọn "No"
            return false;
        }
    }

    public static boolean handleRenameGroup(int currentUserId, int groupId) {
        // Prompt the user to enter a new group name
        String newName = JOptionPane.showInputDialog(null, "Nhập tên mới:",
                "Rename Group", JOptionPane.QUESTION_MESSAGE);

        // If the user cancels or enters an invalid name, return false
        if (newName == null || newName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tên không hợp lệ",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Call renameGroup() with the entered name
        boolean result = groupChatModel.renameGroup(groupId, newName.trim());

        // Notify user about the success/failure of renaming
        if (result) {
            JOptionPane.showMessageDialog(null, "Đổi tên nhóm thành công",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Đổi tên nhóm thất bại. Hãy thử lại sau",
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
        String input = JOptionPane.showInputDialog(null, "Nhập username muốn chuyển thành admin");

        // Validate input
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "username không thể trống");
            return false;
        }
// Get the user ID based on the entered username
        int targetUserId = endUserModel.getUserIdByUsername(input);

        // Check if the user exists in the system
        if (targetUserId == -1) {
            JOptionPane.showMessageDialog(null, "Người dùng không tồn tại.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the targetUserId is in the group
        if (!groupChatMemberModel.isUserInGroup(targetUserId, groupId)) {
            JOptionPane.showMessageDialog(null, "Người dùng không phải là thành viên nhóm");
            return false;
        }

        // Proceed to change admin if the user is in the group
        boolean success = groupChatMemberModel.changeAdmin(currentUserId, targetUserId, groupId);
        if (success) {
            JOptionPane.showMessageDialog(null, "Thay đổi admin thành công.");
        } else {
            JOptionPane.showMessageDialog(null, "Thay đổi admin thất bại.");
        }
        return success;
    }
    public static boolean handleRemoveMember(int currentUserId, int groupId) {
        // Pop-up dialog to enter the username of the member to remove
        String username = JOptionPane.showInputDialog(null, "Nhập username muốn xoá khỏi nhóm:",
                "Remove Member", JOptionPane.PLAIN_MESSAGE);

        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "username không hợp lệ.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Get the user ID based on the entered username
        int targetUserId = endUserModel.getUserIdByUsername(username);

        // Check if the user exists in the system
        if (targetUserId == -1) {
            JOptionPane.showMessageDialog(null, "Người dùng không tồn tại.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the current user is an admin of the group
        if (!groupChatMemberModel.isGroupAdmin(currentUserId, groupId)) {
            JOptionPane.showMessageDialog(null, "Bạn không đủ quyền hạn để xoá thaành viên.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the user is part of the group
        if (!groupChatMemberModel.isUserInGroup(targetUserId, groupId)) {
            JOptionPane.showMessageDialog(null, "Người dùng không phải là thành viên của nhóm.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Proceed to remove the user from the group
        boolean success = groupChatMemberModel.removeMember(currentUserId, targetUserId, groupId);
        if (success) {
            JOptionPane.showMessageDialog(null, "Xoá thành viên khỏi nhóm thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Xoá thành viên khỏi nhóm thất bại", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }
    public static boolean handleAddMember(int currentUserId, int groupId) {
        // Pop-up dialog to enter the username
        String username = JOptionPane.showInputDialog(null, "Nhập username thành viên muốn thêm",
                "Add Member", JOptionPane.PLAIN_MESSAGE);

        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "username không hợp lệ.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!endUserModel.checkIfUserExists(username)) {
            JOptionPane.showMessageDialog(null, "Người dùng không tồn tại.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int id=endUserModel.getUserIdByUsername(username);
        // Check if the entered username is a friend of the current user
        boolean isFriend = userFriendModel.isFriend(currentUserId, id);
        if (id==-1 || !isFriend) {
            JOptionPane.showMessageDialog(null, "Người dùng không phải bạn của bạn.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (endUserModel.checkBlockedByAdmin(username.trim())) {
            JOptionPane.showMessageDialog(null, "Người dùng đã bị khóa bởi admin.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Add the friend to the group
        boolean success = groupChatMemberModel.addMember(groupId, id, false);
        if (success) {
            JOptionPane.showMessageDialog(null, "Thêm thành viên thành công", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Thêm thành viên thất bại.", "Error",
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
                "Bạn có chắc muốn xoá bộ lịch sử chat với nhóm này?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        // Nếu người dùng chọn "Yes", thực hiện xóa
        if (confirm == JOptionPane.YES_OPTION) {
            boolean isDeleted = deletedMessageOfGroupModel.deleteAlltGroupChatHistory(currentUserId, groupId);

            if (isDeleted) {
                JOptionPane.showMessageDialog(null,
                        "Xoá lịch sử chat nhóm thành công.",
                        "Deletion Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Xoá lịch sử chat thất bại.",
                        "Deletion Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

            return isDeleted;
        } else {
            // Người dùng chọn "No"
            return false;
        }
    }

}

