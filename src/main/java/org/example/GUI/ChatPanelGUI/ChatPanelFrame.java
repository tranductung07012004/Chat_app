package org.example.GUI.ChatPanelGUI;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.ChatPanelHandler.RightPanelButtonListener;
import java.awt.*;
import javax.swing.*;

public class ChatPanelFrame extends JPanel {
    private final MainFrameGUI mainFrame;
    private JTextArea activeChatArea;
    private JLabel friendNameLabel;
    private SidebarFrame sidebar;
    private boolean isGroup = false;  // Track the group status

    public ChatPanelFrame(MainFrameGUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Friend's name label at the top
        friendNameLabel = new JLabel("Chatting ");
        friendNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(friendNameLabel, BorderLayout.NORTH);

        // Chat history display area
        activeChatArea = new JTextArea();
        activeChatArea.setEditable(false);
        activeChatArea.setLineWrap(true);
        activeChatArea.setWrapStyleWord(true);
        JScrollPane chatScrollPane = new JScrollPane(activeChatArea);
        add(chatScrollPane, BorderLayout.CENTER);
    }

    // Method to set the friend's name at the top
    public void setFriendName(String friendName) {
        friendNameLabel.setText("Chatting with " + friendName);
    }

    // Method to append messages to the active chat area
    public void appendMessage(String message) {
        activeChatArea.append(message + "\n");
    }

    // Method to search messages based on a query
    public void searchMessages(String searchQuery) {
        String chatText = activeChatArea.getText();
        if (chatText.contains(searchQuery)) {
            JOptionPane.showMessageDialog(this, "Found messages containing: " + searchQuery);
        } else {
            JOptionPane.showMessageDialog(this, "No messages found containing: " + searchQuery);
        }
    }

    public JPanel createChatPanel() {
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(175);

        SidebarFrame sidebar = new SidebarFrame(mainFrame);
        mainSplitPane.setLeftComponent(sidebar);

        // Chat area panel
        ChatPanelFrame chatPanel = new ChatPanelFrame(mainFrame);
        MessageInputFrame messageInput = new MessageInputFrame(mainFrame, chatPanel);
        JPanel chatAreaPanel = new JPanel(new BorderLayout());
        chatAreaPanel.add(chatPanel, BorderLayout.CENTER);
        chatAreaPanel.add(messageInput, BorderLayout.SOUTH);

        // Right-side panel
        JPanel rightPanel = createRightPanel();

        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatAreaPanel, rightPanel);
        rightSplitPane.setDividerLocation(350); // Adjust divider location as needed

        mainSplitPane.setRightComponent(rightSplitPane);
        mainSplitPane.setResizeWeight(0.5);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(mainSplitPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical arrangement
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField searchTextField = new JTextField("Search chat"); // Placeholder text
        searchTextField.setMaximumSize(new Dimension(200, 25));

        // Set initial placeholder color
        searchTextField.setForeground(Color.GRAY);

        // Add FocusListener to handle placeholder
        searchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchTextField.getText().equals("Search chat")) {
                    searchTextField.setText(""); // Clear placeholder
                    searchTextField.setForeground(Color.BLACK); // Change text color
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchTextField.getText().isEmpty()) {
                    searchTextField.setText("Search chat"); // Restore placeholder
                    searchTextField.setForeground(Color.GRAY); // Change text color back
                }
            }
        });

        JButton searchButton = new JButton("Search message");
        JButton reportButton = new JButton("Report spam");
        JButton deleteHistoryButton = new JButton("Delete chat history");
        JButton blockFriendButton = new JButton("Block");
        JButton unfriendButton = new JButton("Unfriend");
        JButton groupButton = new JButton("Create group chat");

        // Member List Buttons (Add, Change, Remove, etc.)
        JButton addMemberButton = new JButton("Add member");
        JButton changeAdminButton = new JButton("Change admin");
        JButton removeMemberButton = new JButton("Remove member");
        JButton renameButton = new JButton("Rename group");
        JButton deleteGroupButton = new JButton("Delete group");
        JButton outGroupButton = new JButton("Out group");

        // Member List
        JPanel memberListPanel = new JPanel();
        memberListPanel.setLayout(new BoxLayout(memberListPanel, BoxLayout.Y_AXIS));
        memberListPanel.setBorder(BorderFactory.createTitledBorder("Members List"));

        // Mock member list
        String[] members = { "Alice", "Bob", "Charlie", "David", "Eve", "Frank" };
        String[] roles = { "member", "member", "member", "member", "member", "admin" };

        for (int i = 0; i < members.length; i++) {
            String displayText = members[i] + "  - " + roles[i];
            JLabel memberLabel = new JLabel(displayText);
            memberListPanel.add(memberLabel);
        }

        // Scrollable panel
        JScrollPane scrollPane = new JScrollPane(memberListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(250, 150));

        // Adjust visibility based on isGroup status
        updatePanelVisibility(rightPanel, memberListPanel, scrollPane, groupButton, addMemberButton, changeAdminButton, removeMemberButton, renameButton, deleteGroupButton, outGroupButton, unfriendButton, blockFriendButton);

        // Add listeners to buttons
        groupButton.addActionListener(e -> {
            isGroup = !isGroup; // Set the isGroup flag to true when the button is clicked
            updatePanelVisibility(rightPanel, memberListPanel, scrollPane, groupButton, addMemberButton, changeAdminButton, removeMemberButton, renameButton, deleteGroupButton, outGroupButton, unfriendButton, blockFriendButton);
            rightPanel.revalidate(); // Revalidate the right panel layout
            rightPanel.repaint();    // Repaint the right panel
        });

        // Add listeners for other buttons...

        rightPanel.add(searchTextField);
        rightPanel.add(searchButton);
        rightPanel.add(reportButton);
        rightPanel.add(deleteHistoryButton);
        rightPanel.add(blockFriendButton);
        rightPanel.add(unfriendButton);
        rightPanel.add(groupButton);

        rightPanel.add(addMemberButton);
        rightPanel.add(changeAdminButton);
        rightPanel.add(removeMemberButton);
        rightPanel.add(renameButton);
        rightPanel.add(outGroupButton);
        rightPanel.add(deleteGroupButton);
        rightPanel.add(scrollPane);

        wrapperPanel.add(rightPanel, BorderLayout.CENTER);
        return wrapperPanel;
    }

    // Method to update visibility based on isGroup status
    private void updatePanelVisibility(JPanel rightPanel, JPanel memberListPanel, JScrollPane scrollPane, JButton groupButton, JButton addMemberButton, JButton changeAdminButton, JButton removeMemberButton, JButton renameButton, JButton deleteGroupButton, JButton outGroupButton, JButton unfriendButton, JButton blockFriendButton) {
        if (isGroup) {
            groupButton.setVisible(false); // Hide "Create group chat" button
            addMemberButton.setVisible(true); // Show "Add member" button
            changeAdminButton.setVisible(true); // Show "Change admin" button
            removeMemberButton.setVisible(true); // Show "remove member" button
            renameButton.setVisible(true); // Show "rename group" button
            memberListPanel.setVisible(true); // Show member list
            deleteGroupButton.setVisible(true); // Show delete group button
            outGroupButton.setVisible(true); // Show out group button
            unfriendButton.setVisible(false); // Hide "Unfriend" button
            blockFriendButton.setVisible(false); // Hide "Block" button
            scrollPane.setVisible(true); // Show member list scroll
        } else {
            groupButton.setVisible(true); // Show "Create group chat" button
            addMemberButton.setVisible(false); // Hide "Add member" button
            changeAdminButton.setVisible(false); // Hide "Change admin" button
            memberListPanel.setVisible(false); // Hide member list
            removeMemberButton.setVisible(false); // Hide "remove member" button
            renameButton.setVisible(false); // Hide "rename group" button
            deleteGroupButton.setVisible(false); // Hide delete group button
            outGroupButton.setVisible(false); // Hide out group button
            unfriendButton.setVisible(true); // Show "Unfriend" button
            blockFriendButton.setVisible(true); // Show "Block" button
            scrollPane.setVisible(false); // Hide member list scroll
        }
    }
}

