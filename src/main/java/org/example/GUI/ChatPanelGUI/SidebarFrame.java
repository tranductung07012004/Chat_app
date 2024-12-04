package org.example.GUI.ChatPanelGUI;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.ChatPanelHandler.SidebarHandler;
import org.example.Model.endUserModel;
import org.example.Model.userFriendModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class SidebarFrame extends JPanel {
    private MainFrameGUI mainFrame;
    private JComboBox<String> optionsComboBox;
    private List<Contact> contacts;
    public boolean contactIsGroup=true;

    public SidebarFrame(MainFrameGUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Search bar at the top
        JTextField searchField = new JTextField("Search..."); // Placeholder text
        searchField.setPreferredSize(new Dimension(0, 30));
        searchField.setForeground(Color.GRAY); // Placeholder color

        // Add FocusListener to handle placeholder
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText(""); // Clear placeholder
                    searchField.setForeground(Color.BLACK); // Change text color
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search..."); // Restore placeholder
                    searchField.setForeground(Color.GRAY); // Change text color back
                }
            }
        });

        add(searchField, BorderLayout.NORTH);

        // Panel for contacts and "Create Group Chat" button
        JPanel topPanel = new JPanel(new BorderLayout());

        // Left side for contacts
        JPanel contactsPanel = new JPanel();
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));
        contactsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding

        // Simulated contact list
        contacts = new ArrayList<>();
        contacts.add(new Contact("Alice", true, false));
        contacts.add(new Contact("Bob", false, false));
        contacts.add(new Contact("Group chat", true, true));

        for (Contact contact : contacts) {
            JPanel contactPanel = createContactPanel(contact);
            contactsPanel.add(contactPanel);
        }

        // Scrollable contact list
        JScrollPane contactScrollPane = new JScrollPane(contactsPanel);
        topPanel.add(contactScrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.CENTER);

        // Panel for the bottom section (dropdown list and action button)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton friendList = new JButton("Friend List");
        friendList.setPreferredSize(new Dimension(175, 25));

        // Dropdown list for actions (on the left side)
        String[] options = { "Chat", "Friend Request", "Settings" };
        optionsComboBox = new JComboBox<>(options);
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dropdownPanel.add(optionsComboBox);
        bottomPanel.add(friendList, BorderLayout.WEST);
        bottomPanel.add(dropdownPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
        SidebarHandler sidebarHandler = new SidebarHandler(this, mainFrame);
        friendList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                createFriendList();
            }
        });

    }
    private JPanel createContactPanel(Contact contact) {
        JPanel contactPanel = new JPanel(new BorderLayout());
        contactPanel.setPreferredSize(new Dimension(0, 50)); // Fixed height
        contactPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Full width

        // Name label
        JLabel nameLabel = new JLabel(contact.getName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Status label
        JLabel statusLabel = new JLabel(contact.isOnline() ? "Online" : "Offline");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(contact.isOnline() ? Color.GREEN : Color.RED);
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        contactPanel.add(nameLabel, BorderLayout.CENTER);
        contactPanel.add(statusLabel, BorderLayout.EAST);

        // Add padding and a bottom border for spacing
        contactPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY), // Bottom border
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
        ));

        // Add mouse listener to detect click events
        contactPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Handle the click event: open chat with the contact
                contactIsGroup=contact.isGroup;
            }
        });

        return contactPanel;
    }
    private JPanel createFriendListObject(endUserModel user) {
        JPanel contactPanel = new JPanel(new BorderLayout());
        contactPanel.setPreferredSize(new Dimension(0, 50)); // Fixed height
        contactPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Full width

        // Name label
        JLabel nameLabel = new JLabel(user.getAccountName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Status label
        JLabel statusLabel = new JLabel(user.getOnline() ? "Online" : "Offline");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(user.getOnline() ? Color.GREEN : Color.RED);
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        contactPanel.add(nameLabel, BorderLayout.CENTER);
        contactPanel.add(statusLabel, BorderLayout.EAST);

        // Add padding and a bottom border for spacing
        contactPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY), // Bottom border
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
        ));


        return contactPanel;
    }


    public JComboBox<String> getOptionsComboBox() {
        return optionsComboBox;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public static class Contact {
        private String name;
        private boolean online;
        public boolean isGroup;
    
        public Contact(String name, boolean online, boolean isGroup) {
            this.name = name;
            this.online = online;
            this.isGroup = isGroup; // Initialize the isGroup flag
        }
    
        public String getName() {
            return name;
        }
    
        public boolean isOnline() {
            return online;
        }
    }


    private void createFriendList() {
        // Fetch user friends from the database
        List<userFriendModel> friends = userFriendModel.loadUserFriends(mainFrame.getCurrentUserId());  // Assuming you have the userId available
        List<endUserModel> filteredUsers = new ArrayList<>();

        // Convert the userFriendModel list to endUserModel list
        for (userFriendModel friend : friends) {
            endUserModel person = endUserModel.getUserFromId(friend.getFriend_id());
            filteredUsers.add(person); // Add the endUserModel directly
        }

        // Create the frame for the Friend List
        JFrame friendListFrame = new JFrame("Friend List");
        friendListFrame.setSize(300, 550); // Fixed size
        friendListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        friendListFrame.setLayout(new BorderLayout());
        friendListFrame.setLocationRelativeTo(null); // Center the frame

        // Main panel for the friend list
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Search field to filter friends
        JTextField searchField = new JTextField("Search...");
        searchField.setPreferredSize(new Dimension(0, 30)); // Height 30px
        searchField.setForeground(Color.GRAY); // Placeholder text color

        // Remove placeholder on focus
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        mainPanel.add(searchField, BorderLayout.NORTH);

        // Panel to hold friends list
        JPanel friendListPanel = new JPanel();
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));

        // Add each friend to the panel directly using endUserModel
        for (endUserModel user : filteredUsers) {
            friendListPanel.add(createFriendListObject(user));
        }

        // Add a scroll pane for the list
        JScrollPane friendScrollPane = new JScrollPane(friendListPanel);
        mainPanel.add(friendScrollPane, BorderLayout.CENTER);

        // Button to filter online friends only
        JButton filterOnlineButton = new JButton("Show Online Only");
        filterOnlineButton.addActionListener(new ActionListener() {
            private boolean isFilteringOnline = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                friendListPanel.removeAll(); // Clear the current list
                if (isFilteringOnline) {
                    // Show all friends
                    for (endUserModel user : filteredUsers) {
                        friendListPanel.add(createFriendListObject(user));
                    }
                    filterOnlineButton.setText("Show Online Only");
                } else {
                    // Show only online friends
                    for (endUserModel user : filteredUsers) {
                        if (user.getOnline()) {
                            friendListPanel.add(createFriendListObject(user));
                        }
                    }
                    filterOnlineButton.setText("Show All");
                }
                isFilteringOnline = !isFilteringOnline; // Toggle the filter
                friendListPanel.revalidate();
                friendListPanel.repaint();
            }
        });

        // Add filter button at the bottom
        mainPanel.add(filterOnlineButton, BorderLayout.SOUTH);

        // Add search action to filter friends by name
        searchField.addActionListener(e -> {
            String searchText = searchField.getText().trim().toLowerCase();
            friendListPanel.removeAll();
            for (endUserModel user : filteredUsers) {
                if (user.getAccountName().toLowerCase().contains(searchText)) {
                    friendListPanel.add(createFriendListObject(user));
                }
            }
            friendListPanel.revalidate();
            friendListPanel.repaint();
        });

        // Add the main panel to the frame
        friendListFrame.add(mainPanel);
        friendListFrame.setVisible(true);
    }

}

