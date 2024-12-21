package org.example.GUI.ChatPanelGUI;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.ChatPanelHandler.ChatPanelHandler;
import org.example.Handler.ChatPanelHandler.Contact;
import org.example.Handler.ChatPanelHandler.RightPanelButtonListener;
import org.example.Model.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    private static JTextArea  messageField;
    private static JButton sendButton;

    private static int currentMessageIndex; // Tracks the index of the current message being displayed
    private static int totalMessages;       // Total number of messages for the contact

    private int CHUNK=5;
    // Biến để lưu trạng thái tìm kiếm hiện tại
    private int currentSearchIndex = -1; // Vị trí kết quả hiện tại
    private String lastKeyword = ""; // Từ khóa tìm kiếm gần nhất


    public ChatPanelFrame(MainFrameGUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Friend's name label at the top
        friendNameLabel = new JLabel("Tin nhắn ");
        friendNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(friendNameLabel, BorderLayout.NORTH);


        // Message panel to hold individual messages
        activeChatArea = new JPanel();
        activeChatArea.setLayout(new BoxLayout(activeChatArea, BoxLayout.Y_AXIS)); // Vertical layout for messages
        JScrollPane chatScrollPane = new JScrollPane(activeChatArea);
        add(chatScrollPane, BorderLayout.CENTER);


        // Pagination buttons
        previousButton = new JButton("Trước");
        nextButton = new JButton("Sau");

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
    public void handleIncomingMessage(String message) {
        // Process incoming messages specific to this chat panel
        String[] parts = message.split("\\|");
        if (parts.length == 6) {
            int senderId = Integer.parseInt(parts[1]);
            int receiverId = Integer.parseInt(parts[2]);
            boolean isGroup = Boolean.parseBoolean(parts[3]);
            long messageId=Long.parseLong((parts[4]));
            String content = parts[5];

            if (contact!=null && contact.isGroup() == isGroup &&
                    (senderId == contact.getId() || receiverId == contact.getId())) {
                String sender = (!contact.isGroup() && senderId == mainFrame.getCurrentUserId())
                        ? "You: "
                        : endUserModel.getUserFromId(senderId).getUsername() + ": ";
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                if (currentSearchIndex<=0 && currentMessageIndex <=0)
                appendMessage(sender + content, messageId, currentTime, senderId == mainFrame.getCurrentUserId());
            }
            SidebarFrame.updateContactsPanel();
        }
    }
    public void handleInputOutputMessage(String message)
    {
            String[] parts = message.split("\\|");
            if (parts.length == 2) { // Validate number of parts
                try {
                    int userId = Integer.parseInt(parts[1]);

                    if (userFriendModel.isFriend(userId, mainFrame.getCurrentUserId()))
                    {
                        SidebarFrame.updateContactsPanel();
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Update online/offline fail: " + Arrays.toString(parts));
                }
            } else {
                System.err.println("Invalid login payload format: " + Arrays.toString(parts));
            }
    }
    public void handleDeleteMessage(String message) {
        // Process incoming messages specific to this chat panel
        String[] parts = message.split("\\|");
        if (parts.length == 5) { // Validate number of parts
            try {
                int messageId = Integer.parseInt(parts[1]);
                int who_delete = Integer.parseInt(parts[2]);
                int who_not = Integer.parseInt(parts[3]);
                boolean isGroup = Boolean.parseBoolean(parts[4]);


                // Check if the message is relevant to this chat panel
                if (contact!=null&& contact.isGroup() == isGroup && (who_delete == contact.getId()||who_not== contact.getId())) {
                    updateAfterDelete(messageId);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format in delete message payload: " + Arrays.toString(parts));
            }
        } else {
            System.err.println("Invalid delete payload format: " + Arrays.toString(parts));
        }
    }

    public static JPanel getActiveChatArea(){
        return activeChatArea;
    }

    public void setFriendName(String friendName) {
        friendNameLabel.setText(friendName); // Update label text


    }

    public String formatTextWithLineBreaks(String input) {
        // Tách chuỗi thành các từ
        String[] words = input.split(" ");

        // Chia chuỗi thành các phần nhỏ với tối đa 5 từ mỗi phần
        StringBuilder formattedText = new StringBuilder();
        int wordCountInLine = 0;

        for (String word : words) {
            // Nếu từ chỉ chứa dấu câu, thêm vào nhưng không tăng wordCountInLine
            if (word.matches("[.,?!]+")) {
                formattedText.append(word).append(" ");
                continue;
            }

            // Nếu từ dài hơn 25 ký tự, chia nhỏ từ đó
            if (word.length() > 25) {
                if (wordCountInLine > 0) {
                    formattedText.append("<br>"); // Xuống dòng nếu dòng hiện tại không trống
                }
                for (int i = 0; i < word.length(); i += 25) {
                    int end = Math.min(i + 25, word.length());
                    formattedText.append(word, i, end).append("<br>");
                }
                wordCountInLine = 0; // Reset bộ đếm từ cho dòng tiếp theo
            }
            // Nếu từ dài hơn 10 ký tự nhưng không quá 25 ký tự, đưa xuống dòng mới
            else if (word.length() > 10) {
                if (wordCountInLine > 0) {
                    formattedText.append("<br>");
                }
                formattedText.append(word).append("<br>");
                wordCountInLine = 0;
            }
            // Xử lý từ ngắn bình thường
            else {
                formattedText.append(word).append(" ");
                wordCountInLine++;

                // Nếu đủ 5 từ thì xuống dòng
                if (wordCountInLine >= 5) {
                    formattedText.append("<br>");
                    wordCountInLine = 0;
                }
            }
        }

        // Chuyển chuỗi thành dạng HTML để có thể sử dụng trong JLabel
        return formattedText.toString().trim();
    }


    public void appendMessage(String message, long messageId, Timestamp timestamp, boolean side) {
        // Format the timestamp into "hh:mm dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        String formattedTime = dateFormat.format(new Date(timestamp.getTime())); // Convert Timestamp to Date and format
        String formattedMessage = formatTextWithLineBreaks(message);

        // Create the message label with automatic word wrap (without fixed width in HTML)
        JLabel messageLabel = new JLabel("<html>" + formattedMessage  + "<br><font color='gray' size='2'>" + formattedTime + "</font></html>");
        messageLabel.setOpaque(true);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the label
        messageLabel.putClientProperty("messageId", messageId);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Set background color for the message box based on the side
        if (side) {
            messageLabel.setBackground(new Color(173, 216, 230)); // Light blue for the right side
            messageLabel.setForeground(Color.BLACK);
        } else {
            messageLabel.setBackground(new Color(211, 211, 211)); // Light gray for the left side
            messageLabel.setForeground(Color.BLACK);
        }

        // Create a panel to hold the label
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout(side ? FlowLayout.RIGHT : FlowLayout.LEFT)); // Use FlowLayout for left or right alignment
        messagePanel.setOpaque(false); // Make the background of the panel transparent
        messagePanel.add(messageLabel);

        // Set vertical padding between messages by adjusting panel border
        messagePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Adjust top and bottom spacing

        // Add mouse listener to detect click on the message
        messageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Show the delete message dialog and get the user's choice
                int choice = showDeleteMessageDialog();

                // Process the message deletion based on the user's choice
                processMessageDeletion(choice, messageId, contact);
            }
        });

        // Add the message panel to the active chat area
        activeChatArea.setLayout(new BoxLayout(activeChatArea, BoxLayout.Y_AXIS)); // Set BoxLayout for vertical stacking
        activeChatArea.add(messagePanel);

        // Revalidate and repaint the chat area
        activeChatArea.revalidate();
        activeChatArea.repaint();
    }




    // Method to show the delete message dialog and return the user's choice
    private int showDeleteMessageDialog() {
        // Các tùy chọn trong hộp thoại
        String[] options = {"Đóng", "Xóa", "Thu hồi"};

        // Hiển thị hộp thoại tùy chọn và trả về sự lựa chọn của người dùng
        return JOptionPane.showOptionDialog(null,
                "Bạn muốn làm gì với tin nhắn này?",
                "Xóa tin nhắn",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

    private void updateAfterDelete(long messageId) {
        for (Component component : activeChatArea.getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;

                // Check if the panel contains a JLabel with the specified messageId
                for (Component child : panel.getComponents()) {
                    if (child instanceof JLabel) {
                        JLabel label = (JLabel) child;

                        // Retrieve the associated message ID stored as a client property
                        Long labelMessageId = (Long) label.getClientProperty("messageId");
                        if (labelMessageId != null && labelMessageId == messageId) {
                            // Remove the message panel from the chat area
                            activeChatArea.remove(panel);
                            break; // Exit the inner loop once the message is found
                        }
                    }
                }
            }
        }
        // Revalidate and repaint the chat area to reflect the change
        activeChatArea.revalidate();
        activeChatArea.repaint();
    }

            // Method to handle the deletion of the message
            private void processMessageDeletion(int choice, long messageId, Contact contact) {
                switch (choice) {
                    case 1: // "Delete" - Xóa tin nhắn của phía người dùng hiện tại
                        if (!contact.isGroup()) {
                            // Nếu không phải là nhóm, gọi phương thức delete1Message
                            if (messageOfUserModel.deleteChat(mainFrame.getCurrentUserId(), messageId)) {
                                JOptionPane.showMessageDialog(null, "Xóa tin nhắn thành công.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                updateAfterDelete(messageId);
                            } else {
                                JOptionPane.showMessageDialog(null, "Lỗi khi xóa tin nhắn.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            // Nếu là nhóm, gọi phương thức deleteGroupMessage
                            deletedMessageOfGroupModel deleteMessageOfGroupModel=new deletedMessageOfGroupModel(mainFrame.getCurrentUserId(), messageId);
                            if (deleteMessageOfGroupModel.DeleteGroupMessage(mainFrame.getCurrentUserId(), messageId)) {
                                JOptionPane.showMessageDialog(null, "Xóa tin nhắn khỏi nhóm thành công.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                updateAfterDelete(messageId);

                            } else {
                                JOptionPane.showMessageDialog(null, "Lỗi xóa tin nhắn trong nhóm.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        break;

                    case 2: // "Delete Both Sides" - Xóa tin nhắn ở cả hai phía
                        if (!contact.isGroup()) {
                            // Send delete event to the server
                            String deleteCommand = "DELETE|" + messageId + "|" + contact.getId() + "|false";

                            // Perform local deletion (optional as a safeguard)
                            if (messageOfUserModel.deleteChatBothSides(mainFrame.getCurrentUserId(), messageId)) {
                                JOptionPane.showMessageDialog(null, "Tin nhắn được thu hồi thành công.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                updateAfterDelete(messageId);
                                mainFrame.getChatClient().deleteMessage(messageId,mainFrame.getCurrentUserId(), contact.getId(), contact.isGroup());


                            } else {
                                JOptionPane.showMessageDialog(null, "Lỗi khi thu hồi tin nhắn.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            // Nếu là nhóm, gọi phương thức deleteGroupMessage
                            if (messageOfGroupModel.deleteChat(contact.getId(),mainFrame.getCurrentUserId(), messageId)) {
                                JOptionPane.showMessageDialog(null, "Tin nhắn được thu hồi trong nhóm thành công.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                updateAfterDelete(messageId);
                                // Send delete event to the server for a group message
                                mainFrame.getChatClient().deleteMessage(messageId,mainFrame.getCurrentUserId(), contact.getId(), contact.isGroup());

                            } else {
                                JOptionPane.showMessageDialog(null, "Lỗi khi thu hồi tin nhắn trong nhóm", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        break;


                    default: // "Cancel" hoặc đóng hộp thoại
                        System.out.println("Tắt delete.");
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
        // Tạo JTextArea với 3 dòng và 20 ký tự chiều rộng
        messageField = new JTextArea(3, 20);
        messageField.setLineWrap(true);  // Kích hoạt tính năng xuống dòng tự động
        messageField.setWrapStyleWord(true); // Đảm bảo từ sẽ không bị cắt giữa chừng
        sendButton = new JButton("Gửi");

        if (contact==null)
            sendButton.setEnabled(false);

        // Add action listener for send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //save to database

                String message = messageField.getText().trim();
                long messageid=sendMessage();
                if (!message.isEmpty()) {
                    boolean isBlock=blockModel.isBlocked(mainFrame.getCurrentUserId(), targetUserId)||blockModel.isBlocked(targetUserId, mainFrame.getCurrentUserId());
                    if(!isBlock)
                        mainFrame.getChatClient().sendMessage(
                                message,
                                contact.isGroup(),
                                targetUserId,
                                mainFrame.getCurrentUserId(),
                                messageid
                        );

                } else {
                    System.out.println("Tin nhắn không thể rỗng.");
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
    public void updateSendButton(){
        if (contact==null)
            sendButton.setEnabled(false);
        else
        {
            boolean isBlock=blockModel.isBlocked(mainFrame.getCurrentUserId(), targetUserId)||blockModel.isBlocked(targetUserId, mainFrame.getCurrentUserId());
            sendButton.setEnabled(!isBlock);
        }
    }
    private long sendMessage() {
        String message = messageField.getText().trim();
        long messageId=-1;

        if (!message.isEmpty()) {
            boolean isBlock=blockModel.isBlocked(mainFrame.getCurrentUserId(), targetUserId)||blockModel.isBlocked(targetUserId, mainFrame.getCurrentUserId());

            // Thêm tin nhắn vào cơ sở dữ liệu
            if (!contact.isGroup()) {
                 messageId=messageOfUserModel.sendUserMessage(message, mainFrame.getCurrentUserId(), targetUserId);
            } else {
                 messageId=messageOfGroupModel.addGroupMessage(message, mainFrame.getCurrentUserId(), targetUserId);
            }
            if (!isBlock) {
                String sender = "Bạn: ";
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                if(currentSearchIndex>0 ||currentMessageIndex>0)
                {

                    loadMessage(0);
                    currentSearchIndex=0;
                    currentMessageIndex=0;
                    updatePaginationButtons();

                }
                else
                    appendMessage(sender + message, messageId, currentTime, true);

            }
            else
                updateSendButton();
            // Clear the message input field
            messageField.setText("");
        } else {

            System.out.println("Tin nhắn không được để trống.");
        }
        return  messageId;
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

                String sender = ( message.getFromUser() == currentUserId)
                        ? "Bạn: "
                        : endUserModel.getUserFromId(message.getFromUser()).getUsername() + ": ";

                appendMessage(sender + message.getChatContent(), message.getMessageUserId(), message.getChatTime(), message.getFromUser() == currentUserId);
            }
        }

        setFriendName(contact.getName());
        updatePanelVisibility();
        updatePaginationButtons();
        updateSendButton();

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
        searchTextField = new JTextField("Tìm tin nhắn");
        searchTextField.setMaximumSize(new Dimension(200, 25));

        // Placeholder behavior
        searchTextField.setForeground(Color.GRAY);
        searchTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (searchTextField.getText().equals("Tìm tin nhắn")) {
                    searchTextField.setText("");
                    searchTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (searchTextField.getText().isEmpty()) {
                    searchTextField.setText("Tìm tin nhắn");
                    searchTextField.setForeground(Color.GRAY);
                }
            }
        });

        // Buttons
        searchButton = new JButton("Tìm tin nhắn");
        reportButton = new JButton("Báo spam");
        deleteHistoryButton = new JButton("Xóa all chat");
        blockFriendButton = new JButton("Chặn");
        unfriendButton = new JButton("Hủy kết bạn");
        addMemberButton = new JButton("Thêm thành viên");
        changeAdminButton = new JButton("Đổi admin");
        removeMemberButton = new JButton("Xóa thành viên");
        renameButton = new JButton("Đổi tên nhóm");



        int currentUserId = mainFrame.getCurrentUserId();


// Gắn sự kiện cho nút tìm kiếm
        searchButton.addActionListener(e -> {
            String keyword = searchTextField.getText().trim();
                // Nếu từ khóa mới, reset trạng thái tìm kiếm
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
        renameButton.addActionListener(e -> {
            RightPanelButtonListener.handleRenameGroup(currentUserId, targetUserId);
            updatePanelVisibility();
            SidebarFrame.updateContactsPanel();
            contact=SidebarFrame.getSpecialContact(contact);
            setFriendName(contact.getName());
            System.out.println(contact.getName());


        });
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
        memberListPanel.setBorder(BorderFactory.createTitledBorder("Thành viên"));
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
                blockFriendButton.setText("Bỏ chặn");

                // Remove existing listeners to prevent duplicate actions
                for (ActionListener listener : blockFriendButton.getActionListeners()) {
                    blockFriendButton.removeActionListener(listener);
                }

                blockFriendButton.addActionListener(e -> {
                    RightPanelButtonListener.handleUnblockUser(currentUserId, targetUserId);
                    updatePanelVisibility(); // Refresh visibility to update UI
                    updateSendButton();
                });
            } else {
                blockFriendButton.setText("Chặn");

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

                String sender = (message.getFromUser() == currentUserId)
                        ? "Bạn: "
                        : endUserModel.getUserFromId(message.getFromUser()).getUsername() + ": ";

                appendMessage(sender + message.getChatContent(), message.getMessageUserId(), message.getChatTime(), message.getFromUser() == currentUserId);
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
        if (contact == null || keyword == null || keyword.trim().isEmpty() || keyword.equals("Tìm tin nhắn")) {
            JOptionPane.showMessageDialog(this, "Hãy nhập keyword chuẩn.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    currentMessageIndex=currentSearchIndex;
                    loadMessage(currentSearchIndex);
                    found = true;
                    break;
                }
            }

            if (found) {
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Không có tin nhắn nào chứa: " + keyword, "Kết quả", JOptionPane.INFORMATION_MESSAGE);
            currentSearchIndex = -1; // Reset nếu không tìm thấy kết quả
        }
    }






}
