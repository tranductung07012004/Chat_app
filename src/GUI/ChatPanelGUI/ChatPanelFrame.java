package GUI.ChatPanelGUI;

import GUI.MainFrameGUI;
import Handler.ChatPanelHandler.RightPanelButtonListener;
import java.awt.*;
import javax.swing.*;

public class ChatPanelFrame extends JPanel {
    private final MainFrameGUI mainFrame;
    private JTextArea activeChatArea;
    private JLabel friendNameLabel;


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
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout());

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 1, 10, 10));

        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(150, 25)); // Set width and height for the text field
        searchTextField.setMaximumSize(new Dimension(150, 25)); // Set max size to prevent stretching

        JButton searchButton = new JButton("Search message");
        JButton reportButton = new JButton("Report spam");
        JButton deleteHistoryButton = new JButton("Delete chat history");
        JButton blockFriendButton = new JButton("Block");
        JButton unfriendButton = new JButton("Unfriend");
        JButton groupButton = new JButton("Create group chat");

        // Add "Add member" and "Change admin" buttons, but keep them hidden by default
        JButton addMemberButton = new JButton("Add member");
        JButton changeAdminButton = new JButton("Change admin");

        // Panel for displaying group members
        JPanel memberListPanel = new JPanel();
        memberListPanel.setLayout(new BoxLayout(memberListPanel, BoxLayout.Y_AXIS));
        memberListPanel.setBorder(BorderFactory.createTitledBorder("Members"));

        // Set preferred size to increase the width
        memberListPanel.setPreferredSize(new Dimension(700, 150)); // Width = 250, Height = 150 (adjust as needed)

        memberListPanel.setVisible(false); // Initially hidden

        // Add mock members to the list (replace with real data later)
        if (isGroup()) {
            String[] members = { "Alice", "Bob", "Charlie" }; // Example member names
            for (String member : members) {
                JLabel memberLabel = new JLabel(member);
                memberListPanel.add(memberLabel);
            }
        }

        // Set visibility of buttons and member panel based on `isGroup`
        if (isGroup()) {
            groupButton.setVisible(true); // Hide "Create group chat" button
            addMemberButton.setVisible(true); // Show "Add member" button
            changeAdminButton.setVisible(true); // Show "Change admin" button
            memberListPanel.setVisible(true); // Show member list
        } else {
            groupButton.setVisible(true); // Show "Create group chat" button
            addMemberButton.setVisible(false); // Hide "Add member" button
            changeAdminButton.setVisible(false); // Hide "Change admin" button
            memberListPanel.setVisible(false); // Hide member list
        }

        // Add listeners to buttons
        searchButton.addActionListener(new RightPanelButtonListener(this, searchButton, searchTextField));
        reportButton.addActionListener(new RightPanelButtonListener(this, reportButton, searchTextField));
        deleteHistoryButton.addActionListener(new RightPanelButtonListener(this, deleteHistoryButton, searchTextField));
        blockFriendButton.addActionListener(new RightPanelButtonListener(this, blockFriendButton, searchTextField));
        unfriendButton.addActionListener(new RightPanelButtonListener(this, unfriendButton, searchTextField));
        groupButton.addActionListener(new RightPanelButtonListener(this, groupButton, searchTextField));

        addMemberButton.addActionListener(new RightPanelButtonListener(this, addMemberButton, searchTextField));
        changeAdminButton.addActionListener(new RightPanelButtonListener(this, changeAdminButton, searchTextField)); 

        rightPanel.add(new JLabel("Search Chat History:"));
        rightPanel.add(searchTextField); // Add search text field to the panel
        rightPanel.add(searchButton);
        rightPanel.add(reportButton);
        rightPanel.add(deleteHistoryButton);
        rightPanel.add(blockFriendButton);
        rightPanel.add(unfriendButton);
        rightPanel.add(groupButton);

        // Add the new buttons for "Add member" and "Change admin" at the bottom
        rightPanel.add(addMemberButton);
        rightPanel.add(changeAdminButton);
        // Add the member list panel at the bottom
        rightPanel.add(memberListPanel);

        wrapperPanel.add(rightPanel, BorderLayout.CENTER);
        return wrapperPanel;
    }

    private boolean isGroup() {

        return true;
    }

}
