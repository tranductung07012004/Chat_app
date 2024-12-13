package org.example.GUI.UserFriendRequest;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.ChatPanelHandler.FriendListHandle;
import org.example.Handler.FriendRequestHandler.FriendRequestHandler;
import org.example.Model.blockModel;
import org.example.Model.endUserModel;
import org.example.Model.friendRequestModel;
import org.example.Model.userFriendModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class FriendRequestFrame extends JPanel {
    public MainFrameGUI mainFrame;
    private JPanel requestListPanel;
    private JPanel searchResultsPanel; // Panel to display search results
    private JTextField searchField;
    private JTextField friendSearchField; // Search bar for finding new friends
    private JButton backButton;

    // Public access to friendRequests so handler can manage it
    public List<String> friendRequests;

    private FriendRequestHandler friendRequestHandler;

    public FriendRequestFrame(MainFrameGUI mainFrame) {
        this.mainFrame = mainFrame;
        this.friendRequests = new ArrayList<>();
        this.friendRequestHandler = new FriendRequestHandler(this); // Initialize the handler
    
        setLayout(new BorderLayout());
    
        // Header with Back Button, Search Field, and My ID
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Friend Requests", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
    
        backButton = new JButton("Back");
    
        // Search field for filtering requests by name
        searchField = new JTextField();
        searchField.setToolTipText("Search by name/ID...");
    
        // "My ID" Display
        int myId = mainFrame.getCurrentUserId();
        JLabel myIdLabel = new JLabel("My ID: " + myId);
        myIdLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        myIdLabel.setForeground(Color.GRAY);
        myIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    
        JPanel topRowPanel = new JPanel(new BorderLayout());
        topRowPanel.add(backButton, BorderLayout.WEST);
        topRowPanel.add(myIdLabel, BorderLayout.EAST);
    
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(topRowPanel, BorderLayout.NORTH);
    
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);
    
        // Panel to display the list of requests
        requestListPanel = new JPanel();
        requestListPanel.setLayout(new BoxLayout(requestListPanel, BoxLayout.Y_AXIS));
    
        JScrollPane requestScrollPane = new JScrollPane(requestListPanel);
    
        // Panel for searching new friends
        JPanel friendSearchPanel = new JPanel(new BorderLayout());
        friendSearchPanel.setBorder(BorderFactory.createTitledBorder("Find New Friends"));
    
        friendSearchField = new JTextField();
        friendSearchField.setToolTipText("Enter friend's name...");
        JButton searchFriendButton = new JButton("Search");
    
        searchFriendButton.addActionListener(e -> friendRequestHandler.handleFriendSearch(friendSearchField.getText()));
    
        JPanel friendSearchInputPanel = new JPanel(new BorderLayout());
        friendSearchInputPanel.add(friendSearchField, BorderLayout.CENTER);
        friendSearchInputPanel.add(searchFriendButton, BorderLayout.EAST);
    
        friendSearchPanel.add(friendSearchInputPanel, BorderLayout.NORTH);
    
        // Panel to display search results
        searchResultsPanel = new JPanel();
        searchResultsPanel.setLayout(new BoxLayout(searchResultsPanel, BoxLayout.Y_AXIS));
        JScrollPane searchResultsScrollPane = new JScrollPane(searchResultsPanel);
    
        friendSearchPanel.add(searchResultsScrollPane, BorderLayout.CENTER);
    
        // Combine the request list and friend search panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, requestScrollPane, friendSearchPanel);
        splitPane.setResizeWeight(0.5);
    
        add(splitPane, BorderLayout.CENTER);
    

        // Add dynamic loading
        refreshRequests(friendRequestHandler.loadFriendRequests());
        backButton.addActionListener(e -> friendRequestHandler.handleBack());
        searchField.addActionListener(e -> friendRequestHandler.handleSearch(searchField.getText()));
    }


    // Updated method signature for refreshing requests
    public void refreshRequests(List<friendRequestModel> requests) {
        requestListPanel.removeAll();

        for (friendRequestModel request : requests) {
            JPanel requestPanel = new JPanel();
            requestPanel.setLayout(new BorderLayout());
            requestPanel.setPreferredSize(new Dimension(0, 50));
            requestPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            requestPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

            endUserModel user= endUserModel.getUserFromId(request.getFrom_user_id());
            String displayText = "User " + user.getUsername()+", Full name: "+user.getAccountName()+ ", ID: "+user.getUserId()+ " sent a friend request!";
            JLabel nameLabel = new JLabel(displayText);
            nameLabel.setHorizontalAlignment(SwingConstants.LEFT);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton acceptButton = new JButton("Accept");
            JButton declineButton = new JButton("Decline");

            // Wire buttons to handler
            acceptButton.addActionListener(e -> friendRequestHandler.handleAccept(request));
            declineButton.addActionListener(e -> friendRequestHandler.handleDecline(request));

            buttonPanel.add(acceptButton);
            buttonPanel.add(declineButton);

            requestPanel.add(nameLabel, BorderLayout.CENTER);
            requestPanel.add(buttonPanel, BorderLayout.EAST);

            requestListPanel.add(requestPanel);
        }

        requestListPanel.revalidate();
        requestListPanel.repaint();
    }

    // Update the search results panel
    public void updateSearchResults(List<endUserModel> results) {
        searchResultsPanel.removeAll(); // Clears previous search results

        for (endUserModel result : results) {
            JPanel resultPanel = new JPanel(new BorderLayout());
            resultPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)); // Adds a border for each result

            // Displays user information
            JLabel nameLabel = new JLabel("Username: " + result.getUsername());
            JLabel accountNameLabel = new JLabel("Account: " + result.getAccountName()); // Account name
            JLabel userIdLabel = new JLabel("ID: " + result.getUserId());
            userIdLabel.setFont(new Font("Arial", Font.ITALIC, 10));
            userIdLabel.setForeground(Color.GRAY);

            // User info panel (username, account_name, user id)
            JPanel userInfoPanel = new JPanel(new BorderLayout());
            userInfoPanel.add(nameLabel, BorderLayout.NORTH);
            userInfoPanel.add(accountNameLabel, BorderLayout.CENTER);
            userInfoPanel.add(userIdLabel, BorderLayout.SOUTH);

            // Buttons for sending/withdrawing friend requests and blocking/unblocking the user
            JButton actionButton = new JButton(); // Dynamic button for friend requests
            JButton blockButton = new JButton();  // Dynamic button for blocking/unblocking
            JButton chatButton = new JButton("Chat"); // Chat button
            JButton groupChatButton = new JButton("Group Chat"); // Group chat button

            // Determine button states
            int currentUserId = mainFrame.getCurrentUserId();
            boolean isRequestSent = friendRequestModel.isFriendRequestSent(currentUserId, result.getUserId());
            boolean isBlocked = blockModel.isBlocked(currentUserId, result.getUserId());
            boolean isFriend = userFriendModel.isFriend(currentUserId, result.getUserId());

            // Configure action button for friend requests
            if (isFriend) {
                actionButton.setText("Unfriend");
                actionButton.addActionListener(e -> {
                    // Handle unfriend logic here
                    friendRequestHandler.handleUnfriend(result.getUserId());
                    updateSearchResults(results); // Refresh results after unfriending
                });

                // Enable Group Chat button for friends
                groupChatButton.setEnabled(true);
                groupChatButton.addActionListener(e -> {
                    // Handle group chat logic here
                    FriendListHandle handler= new FriendListHandle();
                    handler.createGroupChat(mainFrame.getCurrentUserId(), result);
                    mainFrame.showChatPanel();


                });
            } else if (isBlocked) {
                actionButton.setEnabled(false); // Disable button if the user is blocked
                groupChatButton.setEnabled(true);
                chatButton.setEnabled(true);
            } else if (isRequestSent) {
                actionButton.setText("Retrieve Request");
                actionButton.addActionListener(e -> {
                    friendRequestHandler.handleRetrieveFriendRequest(result.getUserId());
                    updateSearchResults(results); // Refresh results after retrieving request
                });
            } else {
                actionButton.setText("Send Request");
                actionButton.addActionListener(e -> {
                    friendRequestHandler.handleSendFriendRequest(result.getUserId());
                    updateSearchResults(results); // Refresh results after sending request
                });

                // Disable Group Chat button if not friends
                groupChatButton.setEnabled(false);
            }

            // Configure block/unblock button
            if (isBlocked) {
                blockButton.setText("Unblock");
                blockButton.addActionListener(e -> {
                    friendRequestHandler.handleUnblockUser(result.getUserId());
                    updateSearchResults(results); // Refresh results after unblocking
                });
            } else {
                blockButton.setText("Block");
                blockButton.addActionListener(e -> {
                    friendRequestHandler.handleBlockUser(result.getUserId());
                    updateSearchResults(results); // Refresh results after blocking
                });
            }

            // Configure Chat button
            chatButton.addActionListener(e -> {
                FriendListHandle handler= new FriendListHandle();

                mainFrame.showChatPanel();
                handler.openChat(mainFrame.getCurrentUserId(), result); // Pass current user ID and target user

            });

            // Panel for buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(actionButton);
            buttonPanel.add(blockButton);
            buttonPanel.add(chatButton); // Add chat button
            buttonPanel.add(groupChatButton); // Add group chat button

            // Adding panels to the result panel
            resultPanel.add(userInfoPanel, BorderLayout.CENTER);
            resultPanel.add(buttonPanel, BorderLayout.EAST);

            searchResultsPanel.add(resultPanel); // Add result panel to search results panel
        }

        searchResultsPanel.revalidate(); // Revalidate the UI components
        searchResultsPanel.repaint(); // Repaint the panel to reflect changes
    }


}