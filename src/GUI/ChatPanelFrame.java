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
    }

    // Method to append messages to the active chat area
    public void appendMessage(String message) {
        activeChatArea.append(message + "\n");
    }

    public JPanel createChatPanel() {
        // Create a split pane for sidebar, chat area, and right panel
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(175);

        // Add sidebar
        SidebarFrame sidebar = new SidebarFrame(mainFrame);
        mainSplitPane.setLeftComponent(sidebar);

        // Create chat area panel
        ChatPanelFrame chatPanel = new ChatPanelFrame(mainFrame);
        MessageInputFrame messageInput = new MessageInputFrame(mainFrame, chatPanel);
        JPanel chatAreaPanel = new JPanel(new BorderLayout());
        chatAreaPanel.add(chatPanel, BorderLayout.CENTER);
        chatAreaPanel.add(messageInput, BorderLayout.SOUTH);

        // Create the right-side panel with options
        JPanel rightPanel = createRightPanel();

        // Create a split pane between chat area and the right panel
        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatAreaPanel, rightPanel);
        rightSplitPane.setDividerLocation(350);  // Adjust divider location as needed

        // Set the combined split pane to the main split pane
        mainSplitPane.setRightComponent(rightSplitPane);

        // Ensure the panels are initially visible by setting the main layout properly
        mainSplitPane.setResizeWeight(0.5);  // Split the space equally between the left and right panels

        // Create the main panel that holds the split pane
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(mainSplitPane, BorderLayout.CENTER);

        return panel;
    }

    // Method to create the right panel with options wrapped in another panel
    private JPanel createRightPanel() {
        // Create the main panel that will hold the right panel
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout());  // Or any layout you prefer, here BorderLayout is used

        // Create the right panel that holds the options
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 1, 10, 10));

        // Search in Chat Option
        JButton searchButton = new JButton("Search");

        // Report in Chat Option
        JButton reportButton = new JButton("Report");

        // Delete All Chat History Option
        JButton deleteHistoryButton = new JButton("Delete chat history");

        // Block Friend Option
        JButton blockFriendButton = new JButton("Block");

        // Unfriend Option
        JButton unfriendButton = new JButton("Unfriend");

        // Add buttons to rightPanel
        rightPanel.add(searchButton);

        rightPanel.add(reportButton);

        rightPanel.add(deleteHistoryButton);

        rightPanel.add(blockFriendButton);

        rightPanel.add(unfriendButton);

        // Add rightPanel to the wrapperPanel
        wrapperPanel.add(rightPanel, BorderLayout.CENTER);

        // Return the wrapper panel containing the rightPanel
        return wrapperPanel;
    }

}
