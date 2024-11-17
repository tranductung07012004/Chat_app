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
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Sử dụng BoxLayout
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JTextField searchTextField = new JTextField();
        searchTextField.setMaximumSize(new Dimension(200, 25));

        // Thêm margin bằng EmptyBorder
        searchTextField.setBorder(BorderFactory.createEmptyBorder(2, 15, 2, 10)); // Top, Left, Bottom, Right
        
        JButton searchButton = new JButton("Search message");
        JButton reportButton = new JButton("Report spam");
        JButton deleteHistoryButton = new JButton("Delete chat history");
        JButton blockFriendButton = new JButton("Block");
        JButton unfriendButton = new JButton("Unfriend");
        JButton groupButton = new JButton("Create group chat");

        // Add "Add member" and "Change admin" buttons, but keep them hidden by default
        JButton addMemberButton = new JButton("Add member");
        JButton changeAdminButton = new JButton("Change admin");
        JButton removeMemberButton = new JButton("Remove member");
        JButton renameButton = new JButton("Rename group");
        JButton deleteGroupButton = new JButton("Delete group");
        JButton outGroupButton=new JButton("Out group");




         // Member List
    JPanel memberListPanel = new JPanel();
    memberListPanel.setLayout(new BoxLayout(memberListPanel, BoxLayout.Y_AXIS));
    memberListPanel.setBorder(BorderFactory.createTitledBorder("Members List"));

    // Mock member list
    String[] members = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank"};
    for (String member : members) {
        JLabel memberLabel = new JLabel(member);
        memberListPanel.add(memberLabel);
    }

    // Scrollable panel
    JScrollPane scrollPane = new JScrollPane(memberListPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setPreferredSize(new Dimension(250, 150));

        // Set visibility of buttons and member panel based on `isGroup`
        if (isGroup()) {
            groupButton.setVisible(false); // Hide "Create group chat" button
            addMemberButton.setVisible(true); // Show "Add member" button
            changeAdminButton.setVisible(true); // Show "Change admin" button
            removeMemberButton.setVisible(true); //Show "remove member" button
            renameButton.setVisible(true);//Show "rename group" button
            memberListPanel.setVisible(true); // Show member list
            deleteGroupButton.setVisible(true);//Show delete group button
            outGroupButton.setVisible(true);//Show out group button
            unfriendButton.setVisible(false);
            blockFriendButton.setVisible(false);

        } else {
            groupButton.setVisible(true); // Show "Create group chat" button
            addMemberButton.setVisible(false); // Hide "Add member" button
            changeAdminButton.setVisible(false); // Hide "Change admin" button
            memberListPanel.setVisible(false); // Hide member list
            removeMemberButton.setVisible(false); //Hide "remove member" button
            renameButton.setVisible(false);//Hide "rename group" button
            deleteGroupButton.setVisible(false);//Show delete group button
            outGroupButton.setVisible(false);//Hide out group button
            unfriendButton.setVisible(true);
            blockFriendButton.setVisible(true);
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
        removeMemberButton.addActionListener(new RightPanelButtonListener(this, removeMemberButton, searchTextField));
        renameButton.addActionListener(new RightPanelButtonListener(this, renameButton, searchTextField));
        deleteGroupButton.addActionListener(new RightPanelButtonListener(this, deleteGroupButton, searchTextField));
        outGroupButton.addActionListener(new RightPanelButtonListener(this, outGroupButton, searchTextField));


        rightPanel.add(new JLabel("Search Chat History:"));
        rightPanel.add(searchTextField); // Add search text field to the panel
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
        rightPanel.add (outGroupButton);
        rightPanel.add(deleteGroupButton);
  // Add scrollable member list
    rightPanel.add(scrollPane);


        wrapperPanel.add(rightPanel, BorderLayout.CENTER);
        return wrapperPanel;
    }

    private boolean isGroup() {

        return true;
    }
    

}
