package GUI;

import javax.swing.*;
import java.awt.*;

public class ChatPanelFrame extends JPanel {
    private MainFrameGUI mainFrame;
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

        // // Example in a friend selection event handler
        // String selectedFriend = "Alice"; // Replace with the actual selected friend's name
        // chatPanel.setFriendName(selectedFriend);
    }

    // Method to append messages to the active chat area
    public void appendMessage(String message) {
        activeChatArea.append(message + "\n");
    }
    public JPanel createChatPanel() {
        // Create a split pane for sidebar and chat area
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(175);

        // Add sidebar
        SidebarFrame sidebar = new SidebarFrame(mainFrame);
        splitPane.setLeftComponent(sidebar);

        // Create chat area panel
        ChatPanelFrame chatPanel = new ChatPanelFrame(mainFrame);
        MessageInputFrame messageInput = new MessageInputFrame(mainFrame, chatPanel);
        JPanel chatAreaPanel = new JPanel(new BorderLayout());
        chatAreaPanel.add(chatPanel, BorderLayout.CENTER);
        chatAreaPanel.add(messageInput, BorderLayout.SOUTH);

        splitPane.setRightComponent(chatAreaPanel);

        // Create the main panel that holds the split pane
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }
}
