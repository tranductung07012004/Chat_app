package org.example.GUI.ChatPanelGUI;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.ChatPanelHandler.ChatPanelHandler;
import org.example.Handler.ChatPanelHandler.Contact;
import org.example.Handler.ChatPanelHandler.RightPanelButtonListener;
import org.example.Model.endUserModel;
import org.example.Model.messageOfUserModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;

public class ChatPanelFrame extends JPanel {
    public final MainFrameGUI mainFrame;
    private static JPanel activeChatArea;
    private JLabel friendNameLabel;
    private SidebarFrame sidebar;
    private boolean isGroup = false;

    private int targetUserId = -1;
    private Contact contact;
    private JPanel rightPanel; // Declare as a class-level variable
    private JButton searchButton, reportButton, deleteHistoryButton, blockFriendButton, unfriendButton, groupButton;
    private JButton addMemberButton, changeAdminButton, removeMemberButton, renameButton, deleteGroupButton, outGroupButton;
    private JPanel memberListPanel;
    private JScrollPane scrollPane;
    private JTextField searchTextField;


    public ChatPanelFrame(MainFrameGUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Friend's name label at the top
        friendNameLabel = new JLabel("Chatting ");
        friendNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(friendNameLabel, BorderLayout.NORTH);


        // Message panel to hold individual messages
        activeChatArea = new JPanel();
        activeChatArea.setLayout(new BoxLayout(activeChatArea, BoxLayout.Y_AXIS)); // Vertical layout for messages
        JScrollPane chatScrollPane = new JScrollPane(activeChatArea);
        add(chatScrollPane, BorderLayout.CENTER);

    }

    // Method to set the friend's name at the top
    public void setFriendName(String friendName) {
        friendNameLabel.setText("Chatting with " + friendName);
    }

    public void appendMessage(String message, long messageId, Timestamp timestamp) {
        // Format the timestamp into "hh:mm dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        String formattedTime = dateFormat.format(new Date(timestamp.getTime())); // Convert Timestamp to Date and format

        // Create the message content with message ID and formatted time
        String fullMessage = "<html>" + message + "<br><font color='gray'>" + formattedTime + "</font></html>";

        // Create a clickable label for the message
        JLabel messageLabel = new JLabel(fullMessage);
        messageLabel.setOpaque(true);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set font and styling for the message
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Enable wrapping for the JLabel
        messageLabel.setText("<html><body style='width: 230px;'>" + fullMessage + "</body></html>");

        // Add the message label to the active chat area (JPanel)
        activeChatArea.add(messageLabel);

        // Add mouse listener to detect click on the message
        messageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Trigger deletion or other action based on messageId
                int confirm = JOptionPane.showConfirmDialog(null, "Delete this message?");
                if (confirm == JOptionPane.YES_OPTION) {
                    // Call deleteMessage(messageId); // Implement deletion logic here
                }
            }
        });

        // Revalidate and repaint the chat area
        activeChatArea.revalidate();
        activeChatArea.repaint();
    }

    public JPanel createChatPanel() {
        contact=null;
        targetUserId=-1;
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

        // Right-side panel)
        rightPanel = createRightPanel();

        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatAreaPanel, rightPanel);
        rightSplitPane.setDividerLocation(350); // Adjust divider location as needed

        mainSplitPane.setRightComponent(rightSplitPane);
        mainSplitPane.setResizeWeight(0.5);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(mainSplitPane, BorderLayout.CENTER);
        return panel;

    }

    public void openChat(Contact contact) {
        this.contact = contact;
        this.targetUserId = contact.getId();

        // Load chat history and update UI
        activeChatArea.removeAll(); // Clear all existing messages from the chat panel
        activeChatArea.revalidate(); // Revalidate the panel to reflect the changes
        activeChatArea.repaint();    // Repaint the panel to ensure it displays correctly

        int currentUserId = mainFrame.getCurrentUserId();

        List<messageOfUserModel> chatHistory;
        if (!contact.isGroup()) {
            chatHistory = ChatPanelHandler.loadChatHistory(currentUserId, targetUserId);
        } else {
            int groupId = targetUserId;
            chatHistory = ChatPanelHandler.loadGroupChatHistory(groupId);
        }

        // If chat history is empty, display a placeholder or do nothing
        if (chatHistory.isEmpty()) {
            //append something if needed
        } else {
            for (messageOfUserModel message : chatHistory) {
                String sender = (!contact.isGroup() && message.getFromUser() == currentUserId)
                        ? "You: "
                        : endUserModel.getUserFromId(message.getFromUser()).getAccountName() + ": ";
                appendMessage(sender + message.getChatContent(), message.getMessageUserId(), message.getChatTime());
            }
        }

        // Update panel visibility and buttons dynamically
        updatePanelVisibility(rightPanel, memberListPanel, scrollPane, groupButton, addMemberButton,
                changeAdminButton, removeMemberButton, renameButton, deleteGroupButton,
                outGroupButton, searchButton, reportButton, deleteHistoryButton, unfriendButton, blockFriendButton, searchTextField);
    }


    private JPanel createRightPanel() {
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Vertical layout
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchTextField = new JTextField("Search chat");

        searchTextField.setMaximumSize(new Dimension(200, 25));

        // Handle placeholder text in the search field
        searchTextField.setForeground(Color.GRAY);
        searchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchTextField.getText().equals("Search chat")) {
                    searchTextField.setText("");
                    searchTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchTextField.getText().isEmpty()) {
                    searchTextField.setText("Search chat");
                    searchTextField.setForeground(Color.GRAY);
                }
            }
        });
        // Initialize buttons as instance variables
        searchButton = new JButton("Search message");
        reportButton = new JButton("Report spam");
        deleteHistoryButton = new JButton("Delete chat history");
        blockFriendButton = new JButton("Block");
        unfriendButton = new JButton("Unfriend");
        groupButton = new JButton("Create group chat");

        addMemberButton = new JButton("Add member");
        changeAdminButton = new JButton("Change admin");
        removeMemberButton = new JButton("Remove member");
        renameButton = new JButton("Rename group");
        deleteGroupButton = new JButton("Delete group");
        outGroupButton = new JButton("Out group");

        // Member list
        memberListPanel = new JPanel();
        memberListPanel.setLayout(new BoxLayout(memberListPanel, BoxLayout.Y_AXIS));
        memberListPanel.setBorder(BorderFactory.createTitledBorder("Members List"));

        // Add scrollable member list panel
        scrollPane = new JScrollPane(memberListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(250, 150));

        // Add buttons to the right panel
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

        searchButton.setVisible(false);
        reportButton.setVisible(false);
        deleteHistoryButton.setVisible(false);
        groupButton.setVisible(false);
        addMemberButton.setVisible(false);
        changeAdminButton.setVisible(false);
        removeMemberButton.setVisible(false);
        renameButton.setVisible(false);
        memberListPanel.setVisible(false);
        deleteGroupButton.setVisible(false);
        outGroupButton.setVisible(false);
        unfriendButton.setVisible(false);
        blockFriendButton.setVisible(false);
        scrollPane.setVisible(false);
        searchTextField.setVisible(false); // Hide the text field


        wrapperPanel.add(rightPanel, BorderLayout.CENTER);
        return wrapperPanel;
    }

    // Method to update visibility based on isGroup status
    private void updatePanelVisibility(JPanel rightPanel, JPanel memberListPanel, JScrollPane scrollPane, JButton groupButton,
                                       JButton addMemberButton, JButton changeAdminButton, JButton removeMemberButton,
                                       JButton renameButton, JButton deleteGroupButton, JButton outGroupButton,
                                       JButton searchButton, JButton reportButton,JButton deleteHistoryButton,
                                       JButton unfriendButton, JButton blockFriendButton, JTextField searchTextField) {

        if (contact == null) {
            searchButton.setVisible(false);
            reportButton.setVisible(false);
            deleteHistoryButton.setVisible(false);
            groupButton.setVisible(false);
            addMemberButton.setVisible(false);
            changeAdminButton.setVisible(false);
            removeMemberButton.setVisible(false);
            renameButton.setVisible(false);
            memberListPanel.setVisible(false);
            deleteGroupButton.setVisible(false);
            outGroupButton.setVisible(false);
            unfriendButton.setVisible(false);
            blockFriendButton.setVisible(false);
            scrollPane.setVisible(false);
            searchTextField.setVisible(false);
        } else if (contact.isGroup()) {
            searchButton.setVisible(true);
            reportButton.setVisible(true);
            deleteHistoryButton.setVisible(true);
            groupButton.setVisible(false);
            addMemberButton.setVisible(true);
            changeAdminButton.setVisible(true);
            removeMemberButton.setVisible(true);
            renameButton.setVisible(true);
            memberListPanel.setVisible(true);
            deleteGroupButton.setVisible(true);
            outGroupButton.setVisible(true);
            unfriendButton.setVisible(false);
            blockFriendButton.setVisible(false);
            scrollPane.setVisible(true);
            searchTextField.setVisible(true);

        } else {
            searchButton.setVisible(true);
            reportButton.setVisible(true);
            deleteHistoryButton.setVisible(true);
            groupButton.setVisible(true);
            addMemberButton.setVisible(false);
            changeAdminButton.setVisible(false);
            removeMemberButton.setVisible(false);
            renameButton.setVisible(false);
            deleteGroupButton.setVisible(false);
            outGroupButton.setVisible(false);
            unfriendButton.setVisible(true);
            blockFriendButton.setVisible(true);
            scrollPane.setVisible(false);
            searchTextField.setVisible(true);

        }
    }
}

