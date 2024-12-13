package org.example.Handler.ChatPanelHandler;

import org.example.GUI.ChatPanelGUI.SidebarFrame;
import org.example.Model.endUserModel;
import org.example.Model.groupChatMemberModel;
import org.example.Model.groupChatModel;
import org.example.Model.userFriendModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FriendListHandle {
    private static List<Contact> newcontacts=new ArrayList<>();


    /**
     * Unfriend a user.
     *
     * @param userId       The ID of the current user.
     * @param targetUserId The ID of the friend to unfriend.
     * @return true if the unfriend operation was successful, false otherwise.
     */
    public boolean unfriendUser(int userId, int targetUserId) {
        boolean success = userFriendModel.deleteFriend(userId, targetUserId);
        if (success) {
            JOptionPane.showMessageDialog(null,
                    "You have successfully unfriended the user.",
                    "Unfriend Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Failed to unfriend the user. Please try again later.",
                    "Unfriend Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    /**
     * Open a chat window between the current user and the target user.
     *
     * @param currentUserId The ID of the current user.
     * @param targetUser    The target user's model object.
     */
    /**
     * Open a chat window between the current user and the target user.
     *
     * @param currentUserId The ID of the current user.
     * @param targetUser    The target user's model object.
     */
    public void openChat(int currentUserId, endUserModel targetUser) {
        // Check if the target user is valid
        if (targetUser == null) {
            JOptionPane.showMessageDialog(null,
                    "The selected user does not exist.",
                    "Chat Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the friend list dialog using the getFriendListDialog method
        JDialog friendListDialog = SidebarFrame.getFriendListDialog();

        // Close the friend list dialog
        if (friendListDialog != null && friendListDialog.isShowing()) {
            friendListDialog.dispose(); // Close the dialog
        }

        // Create a Contact object for the target user
        Contact contact = new Contact(
                targetUser.getAccountName(),
                targetUser.getOnline(),
                false, // Assuming this is a single user chat, not a group
                targetUser.getUserId()
        );

        // Update new contacts list
        boolean contactExists = false;
        List<Contact> contacts=SidebarFrame.getContacts();
        // Check if the contact already exists
        for (Contact existingContact : contacts) {
            if (existingContact.getId() == contact.getId() && !existingContact.isGroup()) {
                contactExists = true;
                break;
            }
        }

        // Add the contact if it doesn't exist
        if (!contactExists) {
            newcontacts.add(contact);
        }

        // Update SidebarFrame's UI
        SidebarFrame.updateContactsPanel();
    }
    public static List<Contact> getNewcontacts()
    {return newcontacts;}

    public void createGroupChat(int currentUserId, endUserModel user)
    {
        // Get the friend list dialog using the getFriendListDialog method
        JDialog friendListDialog = SidebarFrame.getFriendListDialog();

        // Close the friend list dialog
        if (friendListDialog != null && friendListDialog.isShowing()) {
            friendListDialog.dispose(); // Close the dialog
        }
        String groupName="group "+user.getAccountName();
        List<Integer> memberIds = new ArrayList<>();
        memberIds.add(user.getUserId());
        int groupid=groupChatModel.createGroupChat(groupName, currentUserId,memberIds);

        Contact contact = new Contact(
                groupName,
                user.getOnline(),
                true, // this is group chat
                groupid
         );
        newcontacts.add(contact);
        SidebarFrame.updateContactsPanel();

    }


}
