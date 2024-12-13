package org.example.GUI.ChatPanelGUI;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.ChatPanelHandler.ChatPanelHandler;
import org.example.Handler.ChatPanelHandler.Contact;
import org.example.Handler.ChatPanelHandler.RightPanelButtonListener;
import org.example.Model.*;
import org.example.Server.ChatClient;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;

public class ChatPanelFrame extends JPanel {
    public final MainFrameGUI mainFrame;
    private static JPanel activeChatArea;
    private static JLabel friendNameLabel;

    private static int targetUserId = -1;
    private static Contact contact;
    private JPanel rightPanel; // Declare as a class-level variable
    private JButton searchButton, reportButton, deleteHistoryButton, blockFriendButton, unfriendButton, groupButton;
    private JButton addMemberButton, changeAdminButton, removeMemberButton, renameButton;
    private static JButton nextButton, previousButton;
    private JPanel memberListPanel;
    private JScrollPane scrollPane;
    private JTextField searchTextField;
    private static JTextField messageField;
    private static JButton sendButton;

    private static int currentMessageIndex; // Tracks the index of the current message being displayed
    private static int totalMessages;       // Total number of messages for the contact

    private int CHUNK=5;
    // Biến để lưu trạng thái tìm kiếm hiện tại
    private int currentSearchIndex = -1; // Vị trí kết quả hiện tại
    private String lastKeyword = ""; // Từ khóa tìm kiếm gần nhất
    private ChatClient client;  // Instance of ChatClient to send/receive messages


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

        // Pagination buttons
        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");

        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.add(previousButton);
        paginationPanel.add(nextButton);

        add(paginationPanel, BorderLayout.SOUTH);

        // Add listeners for the buttons
        previousButton.addActionListener(e -> {
            loadPreviousMessage();
        });
        nextButton.addActionListener(e -> {
            loadNextMessage();
        });
        // Disable buttons initially
        previousButton.setEnabled(false);
        nextButton.setEnabled(false);



    }
    public static JPanel getActiveChatArea(){
        return activeChatArea;
    }

    public void setFriendName(String friendName) {
        friendNameLabel.setText(friendName); // Update label text


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
        messageLabel.setText("<html><body style='width: 100px;'>" + fullMessage + "</body></html>");

        // Add the message label to the active chat area (JPanel)
        activeChatArea.add(messageLabel);

// Add mouse listener to detect click on the message
        // Add mouse listener to the message label
        messageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Show the delete message dialog and get the user's choice
                int choice = showDeleteMessageDialog();

                // Process the message deletion based on the user's choice
                processMessageDeletion(choice, messageId, contact);
            }
        });


        // Revalidate and repaint the chat area
        activeChatArea.revalidate();
        activeChatArea.repaint();
    }
    // Method to show the delete message dialog and return the user's choice
    private int showDeleteMessageDialog() {
        // Các tùy chọn trong hộp thoại
        String[] options = {"Cancel", "Delete", "Delete All"};

        // Hiển thị hộp thoại tùy chọn và trả về sự lựa chọn của người dùng
        return JOptionPane.showOptionDialog(null,
                "What would you like to do with this message?",
                "Delete Message",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

            // Method to handle the deletion of the message
            private void processMessageDeletion(int choice, long messageId, Contact contact) {
                switch (choice) {
                    case 1: // "Delete" - Xóa tin nhắn của phía người dùng hiện tại
                        if (!contact.isGroup()) {
                            // Nếu không phải là nhóm, gọi phương thức delete1Message
                            if (messageOfUserModel.deleteChat(mainFrame.getCurrentUserId(), messageId)) {
                                JOptionPane.showMessageDialog(null, "Message deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to delete message.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            // Nếu là nhóm, gọi phương thức deleteGroupMessage
                            deletedMessageOfGroupModel deleteMessageOfGroupModel=new deletedMessageOfGroupModel(mainFrame.getCurrentUserId(), messageId);
                            if (deleteMessageOfGroupModel.DeleteGroupMessage(mainFrame.getCurrentUserId(), messageId)) {
                                JOptionPane.showMessageDialog(null, "Message deleted from the group successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to delete message from the group.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        openChat(contact); // Refresh chat
                        break;

                    case 2: // "Delete Both Sides" - Xóa tin nhắn ở cả hai phía
                        if (!contact.isGroup()) {
                            // Nếu không phải là nhóm, gọi phương thức delete1Message
                            if (messageOfUserModel.deleteChatBothSides(mainFrame.getCurrentUserId(), messageId)) {
                                JOptionPane.showMessageDialog(null, "Message deleted on both sides successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to delete message on both sides.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            // Nếu là nhóm, gọi phương thức deleteGroupMessage
                            if (messageOfGroupModel.deleteChat(contact.getId(),mainFrame.getCurrentUserId(), messageId)) {
                                JOptionPane.showMessageDialog(null, "Message deleted from the group on both sides successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to delete message from the group on both sides.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        openChat(contact); // Refresh chat
                        break;

                    default: // "Cancel" hoặc đóng hộp thoại
                        System.out.println("Deletion cancelled.");
                        break;
                }
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
        // Message input area
        JPanel messageInputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Send");

        ChatClient client = new ChatClient("localhost", 12343, message -> {
            // Update chat panel or UI with the received message
            // Parse the incoming payload
            if(currentMessageIndex==0&&currentSearchIndex==0) {
                String[] parts = message.split("\\|");
                if (parts.length == 4) {
                    int senderId = Integer.parseInt(parts[0]);
                    int receiverId = Integer.parseInt(parts[1]);
                    boolean isGroup = Boolean.parseBoolean(parts[2]);
                    String content = parts[3];
                    if (contact.isGroup() == isGroup && (senderId == contact.getId() || receiverId == contact.getId())) {
                        String sender = (!contact.isGroup() && senderId == mainFrame.getCurrentUserId())
                                ? "You: "
                                : endUserModel.getUserFromId(senderId).getUsername() + ": ";
                        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                        appendMessage(sender + content, senderId, currentTime);

                    }
                }
            }
        });


        // Add action listener for send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //save to database

                String message = messageField.getText().trim();
                sendMessage();
                if (!message.isEmpty()) {
                    boolean isBlock=blockModel.isBlocked(mainFrame.getCurrentUserId(), targetUserId)||blockModel.isBlocked(targetUserId, mainFrame.getCurrentUserId());
                    if(!isBlock)
                        client.sendMessage(message,contact.isGroup(),targetUserId,mainFrame.getCurrentUserId());

                } else {
                    System.out.println("Message cannot be empty.");
                }

            }
        });

        messageInputPanel.add(messageField, BorderLayout.CENTER);
        messageInputPanel.add(sendButton, BorderLayout.EAST);
        add(messageInputPanel, BorderLayout.SOUTH);

        JPanel chatAreaPanel = new JPanel(new BorderLayout());
        chatAreaPanel.add(chatPanel, BorderLayout.CENTER);
        chatAreaPanel.add(messageInputPanel, BorderLayout.SOUTH);



        // Right-side panel)
        rightPanel = createRightPanel();

        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatAreaPanel, rightPanel);
        rightSplitPane.setDividerLocation(325); // Adjust divider location as needed

        mainSplitPane.setRightComponent(rightSplitPane);
        mainSplitPane.setResizeWeight(0.5);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(mainSplitPane, BorderLayout.CENTER);
        return panel;

    }
    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            boolean isBlock=blockModel.isBlocked(mainFrame.getCurrentUserId(), targetUserId)||blockModel.isBlocked(targetUserId, mainFrame.getCurrentUserId());


            // Thêm tin nhắn vào cơ sở dữ liệu
            if (!contact.isGroup()) {
                messageOfUserModel.sendUserMessage(message, mainFrame.getCurrentUserId(), targetUserId);
            } else {
                messageOfGroupModel.addGroupMessage(message, mainFrame.getCurrentUserId(), targetUserId);
            }
            if (!isBlock) {
                String sender = "You: ";
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                currentMessageIndex = 0;
                currentSearchIndex = 0;
                appendMessage(sender + message, targetUserId, currentTime);

            }
            // Clear the message input field
            messageField.setText("");
        } else {
            System.out.println("Message cannot be empty.");
        }
    }

    public void openChat(Contact contact) {
        ChatPanelFrame.contact = contact;
        targetUserId = contact.getId();
        currentMessageIndex = 0; // Reset to load the latest message first

        activeChatArea.removeAll(); // Clear chat area
        activeChatArea.revalidate();
        activeChatArea.repaint();

        int currentUserId = mainFrame.getCurrentUserId();
        List<messageOfUserModel> chatHistory;

        if (!contact.isGroup()) {
            chatHistory = ChatPanelHandler.loadChatHistory(currentUserId, targetUserId, CHUNK, 0); // Load latest message
        } else {
            int groupId = targetUserId;
            chatHistory = ChatPanelHandler.loadGroupChatHistory(currentUserId,groupId, CHUNK, 0);
        }

        totalMessages = contact.isGroup?messageOfGroupModel.getTotalMessages(targetUserId, mainFrame.getCurrentUserId()): messageOfUserModel.getTotalMessages(currentUserId, targetUserId);

        if (!chatHistory.isEmpty()) {
            for (int i = chatHistory.size() - 1; i >= 0; i--) {
                messageOfUserModel message = chatHistory.get(i);

                String sender = (!contact.isGroup() && message.getFromUser() == currentUserId)
                        ? "You: "
                        : endUserModel.getUserFromId(message.getFromUser()).getUsername() + ": ";

                appendMessage(sender + message.getChatContent(), message.getMessageUserId(), message.getChatTime());
            }
        }

        setFriendName(contact.getName());
        updatePanelVisibility();
        updatePaginationButtons();

    }



    private JPanel createRightPanel() {
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make buttons stretch horizontally
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding between buttons
        gbc.gridx = 0; // Single column layout
        gbc.weightx = 1; // Ensure buttons stretch to fill available width

        // Initialize buttons
        searchTextField = new JTextField("Search chat");
        searchTextField.setMaximumSize(new Dimension(200, 25));

        // Placeholder behavior
        searchTextField.setForeground(Color.GRAY);
        searchTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (searchTextField.getText().equals("Search chat")) {
                    searchTextField.setText("");
                    searchTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (searchTextField.getText().isEmpty()) {
                    searchTextField.setText("Search chat");
                    searchTextField.setForeground(Color.GRAY);
                }
            }
        });

        // Buttons
        searchButton = new JButton("Search message");
        reportButton = new JButton("Report spam");
        deleteHistoryButton = new JButton("Delete chat history");
        blockFriendButton = new JButton("Block");
        unfriendButton = new JButton("Unfriend");
        addMemberButton = new JButton("Add member");
        changeAdminButton = new JButton("Change admin");
        removeMemberButton = new JButton("Remove member");
        renameButton = new JButton("Rename group");



        int currentUserId = mainFrame.getCurrentUserId();


// Gắn sự kiện cho nút tìm kiếm
        searchButton.addActionListener(e -> {
            String keyword = searchTextField.getText().trim();
            if (!keyword.equals(lastKeyword)) {
                // Nếu từ khóa mới, reset trạng thái tìm kiếm
                currentSearchIndex = -1;
                lastKeyword = keyword;
            }
            searchMessage(keyword);
        });
        // Assign listeners to buttons
        reportButton.addActionListener(e -> RightPanelButtonListener.handleReportSpam(currentUserId, targetUserId));

        unfriendButton.addActionListener(e -> {
            RightPanelButtonListener.handleUnfriend(currentUserId, targetUserId);
            updatePanelVisibility();
        });
        deleteHistoryButton.addActionListener(e -> {
            if (contact.isGroup())
            {
                if(RightPanelButtonListener.handleDeleteAllGroupChat(currentUserId, targetUserId)) {
                    activeChatArea.removeAll(); // Clear chat area
                    activeChatArea.revalidate();
                    activeChatArea.repaint();
                }
            }
            else
                if(RightPanelButtonListener.handleDeleteAllUserChat(currentUserId, targetUserId)) {
                    activeChatArea.removeAll(); // Clear chat area
                    activeChatArea.revalidate();
                    activeChatArea.repaint();
                }


        });
        renameButton.addActionListener(e -> RightPanelButtonListener.handleRenameGroup(currentUserId, targetUserId));
        addMemberButton.addActionListener(e -> {
            RightPanelButtonListener.handleAddMember(currentUserId, targetUserId);
            updatePanelVisibility();
        });
        changeAdminButton.addActionListener(e -> {
            RightPanelButtonListener.handleChangeAdmin(currentUserId, targetUserId);
            updatePanelVisibility();
        });
        removeMemberButton.addActionListener(e -> {
            RightPanelButtonListener.handleRemoveMember(currentUserId, targetUserId);
            updatePanelVisibility();
        });


        // Add buttons to the panel
        rightPanel.add(searchTextField, gbc);

        gbc.gridy = 1; rightPanel.add(searchButton, gbc);
        gbc.gridy++; rightPanel.add(reportButton, gbc);
        gbc.gridy++; rightPanel.add(deleteHistoryButton, gbc);
        gbc.gridy++; rightPanel.add(blockFriendButton, gbc);
        gbc.gridy++; rightPanel.add(unfriendButton, gbc); // Add Unfriend Button
        gbc.gridy++; rightPanel.add(addMemberButton, gbc);
        gbc.gridy++; rightPanel.add(changeAdminButton, gbc);
        gbc.gridy++; rightPanel.add(removeMemberButton, gbc);
        gbc.gridy++; rightPanel.add(renameButton, gbc);



        // Add a scrollable member list panel
        memberListPanel = new JPanel();
        memberListPanel.setLayout(new BoxLayout(memberListPanel, BoxLayout.Y_AXIS));
        memberListPanel.setBorder(BorderFactory.createTitledBorder("Members List"));
        scrollPane = new JScrollPane(memberListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Increase the height of the member list
        scrollPane.setPreferredSize(new Dimension(250, 300)); // Increased height to 300
        // Make the member list visible
        memberListPanel.setVisible(true);
        scrollPane.setVisible(true);

        // Add the scroll pane to the panel
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH; // Allow the scroll pane to expand in both directions
        gbc.weighty = 1; // Allocate extra vertical space to the scroll pane
        rightPanel.add(scrollPane, gbc);

        // Initially hide other components
        searchButton.setVisible(false);
        reportButton.setVisible(false);
        deleteHistoryButton.setVisible(false);

        addMemberButton.setVisible(false);
        changeAdminButton.setVisible(false);
        removeMemberButton.setVisible(false);
        renameButton.setVisible(false);


        blockFriendButton.setVisible(false);
        scrollPane.setVisible(false);
        searchTextField.setVisible(false); // Hide the text field
        unfriendButton.setVisible(false);


        wrapperPanel.add(rightPanel, BorderLayout.CENTER);
        return wrapperPanel;
    }

    // Method to update visibility based on isGroup status
    public void updatePanelVisibility() {
        int currentUserId=mainFrame.getCurrentUserId();
        if (contact == null) {
            searchButton.setVisible(false);
            reportButton.setVisible(false);
            deleteHistoryButton.setVisible(true);

            addMemberButton.setVisible(false);
            changeAdminButton.setVisible(false);
            removeMemberButton.setVisible(false);
            renameButton.setVisible(false);
            memberListPanel.setVisible(false);


            blockFriendButton.setVisible(false);
            scrollPane.setVisible(false);
            searchTextField.setVisible(false);
            unfriendButton.setVisible(false);


        } else if (contact.isGroup()) {
            searchButton.setVisible(true);
            reportButton.setVisible(false);
            deleteHistoryButton.setVisible(true);

            addMemberButton.setVisible(true);

            boolean checkAdmin=groupChatMemberModel.isGroupAdmin(mainFrame.getCurrentUserId(), contact.getId());
            removeMemberButton.setVisible(checkAdmin);//remove member button
            changeAdminButton.setVisible(checkAdmin);
            renameButton.setVisible(true);
            memberListPanel.setVisible(true);

            unfriendButton.setVisible(false);
            blockFriendButton.setVisible(false);
            scrollPane.setVisible(true);
            searchTextField.setVisible(true);

            List<String> memberNames = RightPanelButtonListener.handleMemberList(contact.getId());
            // Populate the member list
            addMembersToList(memberNames);
        } else {
            searchButton.setVisible(true);
            reportButton.setVisible(true);
            deleteHistoryButton.setVisible(true);
            addMemberButton.setVisible(false);
            changeAdminButton.setVisible(false);
            removeMemberButton.setVisible(false);
            renameButton.setVisible(false);
            scrollPane.setVisible(false);
            searchTextField.setVisible(true);
            // Check if the user is a friend and set the visibility of the unfriend button
            unfriendButton.setVisible(userFriendModel.isFriend(currentUserId, targetUserId));
            // Check if the user is blocked and update the block button
            blockFriendButton.setVisible(true);

            boolean isBlocked = blockModel.isBlocked(currentUserId, contact.getId());
            if (isBlocked) {
                blockFriendButton.setText("Unblock");

                // Remove existing listeners to prevent duplicate actions
                for (ActionListener listener : blockFriendButton.getActionListeners()) {
                    blockFriendButton.removeActionListener(listener);
                }

                blockFriendButton.addActionListener(e -> {
                    RightPanelButtonListener.handleUnblockUser(currentUserId, targetUserId);
                    updatePanelVisibility(); // Refresh visibility to update UI
                });
            } else {
                blockFriendButton.setText("Block");

                // Remove existing listeners to prevent duplicate actions
                for (ActionListener listener : blockFriendButton.getActionListeners()) {
                    blockFriendButton.removeActionListener(listener);
                }

                blockFriendButton.addActionListener(e -> {
                    RightPanelButtonListener.handleBlock(currentUserId, targetUserId);
                    updatePanelVisibility(); // Refresh visibility to update UI
                });
            }

        }
    }
    private void addMembersToList(List<String> memberNames) {
        memberListPanel.removeAll(); // Clear the existing list
        // Create a smaller font for the member labels
        Font smallerFont = new Font("Arial", Font.PLAIN, 10);

        for (String name : memberNames) {
            JLabel memberLabel = new JLabel(name);
            memberLabel.setFont(smallerFont); // Set smaller font
            memberLabel.setBorder(BorderFactory.createEmptyBorder(5, 1, 5, 5)); // Add some padding
            memberListPanel.add(memberLabel);
        }
        memberListPanel.revalidate(); // Refresh the panel
        memberListPanel.repaint();
    }
    private void loadPreviousMessage() {
        if (this.currentMessageIndex + CHUNK <= totalMessages) {
            this.currentMessageIndex+=CHUNK; // Move back in history
            loadMessage(this.currentMessageIndex);
        }
    }

    private void loadNextMessage() {
        if (this.currentMessageIndex >= 0) {
            this.currentMessageIndex-=CHUNK; // Move forward in history
            loadMessage(this.currentMessageIndex);
        }
    }

    private void loadMessage(int index) {

        activeChatArea.removeAll(); // Clear chat area
        activeChatArea.revalidate();
        activeChatArea.repaint();

        int currentUserId = mainFrame.getCurrentUserId();
        List<messageOfUserModel> chatHistory;

        if (!contact.isGroup()) {



            chatHistory = ChatPanelHandler.loadChatHistory(currentUserId, targetUserId, CHUNK, index); // Load latest message
        } else {
            int groupId = targetUserId;
            chatHistory = ChatPanelHandler.loadGroupChatHistory(currentUserId,groupId, CHUNK, index);
        }

        if (!chatHistory.isEmpty()) {
            for (int i = chatHistory.size() - 1; i >= 0; i--) {
                messageOfUserModel message = chatHistory.get(i);

                String sender = (!contact.isGroup() && message.getFromUser() == currentUserId)
                        ? "You: "
                        : endUserModel.getUserFromId(message.getFromUser()).getUsername() + ": ";

                appendMessage(sender + message.getChatContent(), message.getMessageUserId(), message.getChatTime());
            }
        }

        // Update button states
        updatePaginationButtons();
    }

    private void updatePaginationButtons() {
        nextButton.setEnabled(currentMessageIndex > 0); // Enable if not on the latest message
        previousButton.setEnabled(currentMessageIndex +CHUNK < totalMessages); // Enable if not on the oldest message


    }
    private void searchMessage(String keyword) {
        if (contact == null || keyword == null || keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid keyword.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int currentUserId = mainFrame.getCurrentUserId();
        List<messageOfUserModel> chatHistory;
        boolean found = false;

        // Duyệt qua toàn bộ lịch sử tin nhắn từ vị trí hiện tại
        for (int i = currentSearchIndex + 1; i < totalMessages; i += CHUNK) {
            if (!contact.isGroup()) {
                chatHistory = ChatPanelHandler.loadChatHistory(currentUserId, targetUserId, CHUNK, i);
            } else {
                int groupId = targetUserId;
                chatHistory = ChatPanelHandler.loadGroupChatHistory(currentUserId,groupId, CHUNK, i);
            }

            for (int j = 0; j < chatHistory.size(); j++) {
                messageOfUserModel message = chatHistory.get(j);
                if (message.getChatContent().toLowerCase().contains(keyword.toLowerCase())) {
                    currentSearchIndex = i + j; // Cập nhật vị trí tìm kiếm
                    loadMessage(currentSearchIndex);
                    found = true;
                    break;
                }
            }

            if (found) break;
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "No more messages found containing: " + keyword, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            currentSearchIndex = -1; // Reset nếu không tìm thấy kết quả
        }
    }





}
