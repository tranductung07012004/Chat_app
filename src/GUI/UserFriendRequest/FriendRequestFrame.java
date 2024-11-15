package GUI.UserFriendRequest;

import Handler.FriendRequestHandler.FriendRequestHandler;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import GUI.MainFrameGUI;

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

        // Header with Back Button and Search Field
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Friend Requests", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));

        backButton = new JButton("Back");

        // Search field for filtering requests by name
        searchField = new JTextField();
        searchField.setToolTipText("Search by name...");

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(backButton, BorderLayout.WEST);
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

        // Add initial requests (optional example data)
        friendRequests.add("Alice has sent you a friend request!");
        friendRequests.add("Bob has sent you a friend request!");
        friendRequests.add("Charlie has sent you a friend request!");
        refreshRequests(friendRequests);

        // Wire the search field and back button actions through the handler
        backButton.addActionListener(e -> friendRequestHandler.handleBack());
        searchField.addActionListener(e -> friendRequestHandler.handleSearch(searchField.getText()));
    }

    // Refresh the request list panel
    public void refreshRequests(List<String> requests) {
        requestListPanel.removeAll();

        for (String request : requests) {
            JPanel requestPanel = new JPanel();
            requestPanel.setLayout(new BorderLayout());
            requestPanel.setPreferredSize(new Dimension(0, 50));
            requestPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            requestPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

            JLabel nameLabel = new JLabel(request);
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
    public void updateSearchResults(List<String> results) {
        searchResultsPanel.removeAll();

        for (String result : results) {
            JPanel resultPanel = new JPanel(new BorderLayout());
            resultPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
            JLabel nameLabel = new JLabel(result);
            JButton sendRequestButton = new JButton("Send Request");

            sendRequestButton.addActionListener(e -> friendRequestHandler.handleSendFriendRequest(result));

            resultPanel.add(nameLabel, BorderLayout.CENTER);
            if (!result.startsWith("No results")) {
                resultPanel.add(sendRequestButton, BorderLayout.EAST);
            }

            searchResultsPanel.add(resultPanel);
        }

        searchResultsPanel.revalidate();
        searchResultsPanel.repaint();
    }
}
