package org.example.Handler.ChatPanelHandler;

import org.example.GUI.ChatPanelGUI.ChatPanelFrame;
import org.example.Model.blockModel;
import org.example.Model.friendRequestModel;
import org.example.Model.spamModel;
import org.example.Model.userFriendModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import javax.swing.*;

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
}

