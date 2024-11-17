package Handler.ChatPanelHandler;

import GUI.ChatPanelGUI.ChatPanelFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RightPanelButtonListener implements ActionListener {

    private final ChatPanelFrame chatPanelFrame;
    private final JButton sourceButton;
    private final JTextField searchTextField; // Text field for search

    // Constructor to pass the chat panel and button
    public RightPanelButtonListener(ChatPanelFrame chatPanelFrame, JButton sourceButton, JTextField searchTextField) {
        this.chatPanelFrame = chatPanelFrame;
        this.sourceButton = sourceButton;
        this.searchTextField = searchTextField; // Store the search text field
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = sourceButton.getText();

        switch (buttonText) {
            case "Search message":
                handleSearchMessage();
                break;
            case "Report spam":
                handleReportSpam();
                break;
            case "Delete chat history":
                handleDeleteChatHistory();
                break;
            case "Block":
                handleBlockFriend();
                break;
            case "Unfriend":
                handleUnfriend();
                break;
            case "Create group chat":
                handleCreateGroupChat();

                break;
            case "Add member":
                handleAddMember();
                break;
            case "Change admin":
                handleChangeAdmin();
                break;
            case "Remove member":
                handleRemoveMember();
                break;
            case "Rename group":
                handleRenameGroup();
                break;
            case "Delete group":
                handleDeleteGroup();
                break;
            case "Out group":
                handleOutGroup();
                break;
            default:
                System.out.println("Unknown action: " + buttonText);
        }
    }

    private void handleSearchMessage() {
        String searchQuery = searchTextField.getText().trim();

        if (!searchQuery.isEmpty()) {
            // Call a method in ChatPanelFrame to search for the query in chat history
            chatPanelFrame.searchMessages(searchQuery);
        } else {
            JOptionPane.showMessageDialog(chatPanelFrame, "Please enter a search term.");
        }
    }

    private void handleReportSpam() {
        // Handle reporting the friend as spam
        JOptionPane.showMessageDialog(chatPanelFrame, "Friend reported as spam");
    }

    private void handleDeleteChatHistory() {
        // Handle the logic for deleting the chat history
        int confirmation = JOptionPane.showConfirmDialog(chatPanelFrame,
                "Are you sure you want to delete chat history?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            chatPanelFrame.appendMessage("Chat history deleted.");
        }
    }

    private void handleBlockFriend() {
        // Handle blocking the friend
        JOptionPane.showMessageDialog(chatPanelFrame, "Friend blocked");
    }

    private void handleUnfriend() {
        // Handle unfriending the friend
        JOptionPane.showMessageDialog(chatPanelFrame, "Unfriended");
    }

    private void handleCreateGroupChat() {
        // Handle the logic to create a group chat
        JOptionPane.showMessageDialog(chatPanelFrame,
                "This button is only availaible in one-and-one chats. It would be hidden in group chat");
    }

    private void handleAddMember() {
        // Handle logic for adding a new member to the group
        JOptionPane.showMessageDialog(chatPanelFrame,
                "Add member is only availaible in group chats. It would be hidden in 1-and-1 chats");
    }

    private void handleChangeAdmin() {
        // Handle logic for changing the admin of the group
        JOptionPane.showMessageDialog(chatPanelFrame,
                "Add member is only availaible in group chats. It would be hidden in 1-and-1 chats");
    }

    private void handleRemoveMember() {
        JOptionPane.showMessageDialog(chatPanelFrame,
                "Remove member is only availaible in group chats. It would be hidden in 1-and-1 chats");
    }

    private void handleRenameGroup() {
        JOptionPane.showMessageDialog(chatPanelFrame,
                "Rename is only availaible in group chats. It would be hidden in 1-and-1 chats");
    }

    private void handleDeleteGroup() {
        JOptionPane.showMessageDialog(chatPanelFrame,
                "Rename is only availaible in group chats. It would be hidden in 1-and-1 chats");
    }

    private void handleOutGroup() {
        JOptionPane.showMessageDialog(chatPanelFrame,
                "Rename is only availaible in group chats. It would be hidden in 1-and-1 chats");
    }

}
