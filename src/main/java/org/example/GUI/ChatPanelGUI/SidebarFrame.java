package org.example.GUI.ChatPanelGUI;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.ChatPanelHandler.Contact;
import org.example.Handler.ChatPanelHandler.FriendListHandle;
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
    private static MainFrameGUI mainFrame;
    private JComboBox<String> optionsComboBox;
    private static List<Contact> contacts;

    private static JDialog friendListDialog;
    private JPanel friendListPanel; // Declare as class field
    private static JPanel contactsPanel; // Declare as class field
    private List<endUserModel> filteredUsers;


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
        contactsPanel = new JPanel();
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));
        contactsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding

        // example data
        contacts = SidebarHandler.loadAllContacts(mainFrame.getCurrentUserId());
        contacts.addAll(FriendListHandle.getNewcontacts());

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
    public static void updateContactsPanel() {
        // Assuming `contactsPanel` is the JPanel displaying contacts
        contacts.addAll(FriendListHandle.getNewcontacts());


        // Clear the panel
        contactsPanel.removeAll();

        // Recreate the contact panels
        for (Contact contact : contacts) {
            JPanel contactPanel = createContactPanel(contact); // A method to create contact panels
            contactsPanel.add(contactPanel);
        }

        // Revalidate and repaint the panel
        contactsPanel.revalidate();
        contactsPanel.repaint();
    }

    private static JPanel createContactPanel(Contact contact) {
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
                ChatPanelFrame chatPanel = mainFrame.getChatPanelFrame(); // Get reference to ChatPanel
                chatPanel.openChat(contact); // Pass the selected contact to ChatPanel

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

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonsPanel.setOpaque(false); // Make the panel transparent

        // Chat button
        JButton chatButton = new JButton("Chat");
        chatButton.setFont(new Font("Arial", Font.PLAIN, 12));
        FriendListHandle handler = new FriendListHandle();
        // Chat button logic
        chatButton.addActionListener(e -> {
            handler.openChat(mainFrame.getCurrentUserId(), user); // Pass current user ID and target user
        });

        // Unfriend button
        JButton unfriendButton = new JButton("Unfriend");
        unfriendButton.setFont(new Font("Arial", Font.PLAIN, 12));
        unfriendButton.setForeground(Color.RED);

        unfriendButton.addActionListener(e -> {
            // Optionally remove from the database (if needed)
            handler.unfriendUser(mainFrame.getCurrentUserId(), user.getUserId());

            // Remove the user from the filteredUsers list
            filteredUsers.remove(user); // This removes the user from the list

            // Rebuild the friend list from the filteredUsers list
            friendListPanel.removeAll(); // Clear the list

            // Re-add remaining users
            for (endUserModel remainingUser : filteredUsers) {
                friendListPanel.add(createFriendListObject(remainingUser));
            }

            // Revalidate and repaint to refresh the UI
            friendListPanel.revalidate();
            friendListPanel.repaint();
        });


        // Add buttons to the panel
        buttonsPanel.add(chatButton);
        buttonsPanel.add(unfriendButton);

        // Combine name and status with buttons
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false); // Transparent panel
        infoPanel.add(nameLabel, BorderLayout.CENTER);
        infoPanel.add(statusLabel, BorderLayout.EAST);

        contactPanel.add(infoPanel, BorderLayout.CENTER);
        contactPanel.add(buttonsPanel, BorderLayout.EAST);

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

    public static List<Contact> getContacts() {
        return contacts;
    }


    public static JDialog getFriendListDialog() {
        return friendListDialog; // Return the friend list dialog
    }






    private void createFriendList() {
        // Kiểm tra nếu dialog đã tồn tại và đang hiển thị
        if (friendListDialog != null && friendListDialog.isShowing()) {
            friendListDialog.toFront(); // Đưa dialog lên trước
            return;
        }

        // Fetch user friends from database
        List<userFriendModel> friends = userFriendModel.loadUserFriends(mainFrame.getCurrentUserId());
         filteredUsers = new ArrayList<>();

        // Convert userFriendModel to endUserModel
        for (userFriendModel friend : friends) {
            endUserModel person = endUserModel.getUserFromId(friend.getFriend_id());
            filteredUsers.add(person);
        }

        // Create dialog
        friendListDialog = new JDialog(mainFrame, "Friend List", true);
        friendListDialog.setSize(300, 550);
        friendListDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        friendListDialog.setLayout(new BorderLayout());
        friendListDialog.setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Search field
        JTextField searchField = new JTextField("Search...");
        searchField.setPreferredSize(new Dimension(0, 30));
        searchField.setForeground(Color.GRAY);
        mainPanel.add(searchField, BorderLayout.NORTH);

        // Friend list panel
        friendListPanel = new JPanel();
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));

        // Add friends to the list
        for (endUserModel user : filteredUsers) {
            JPanel contactPanel = createFriendListObject(user);
            friendListPanel.add(contactPanel);
        }

        // JScrollPane for the friend list
        JScrollPane friendScrollPane = new JScrollPane(friendListPanel);
        mainPanel.add(friendScrollPane, BorderLayout.CENTER);

        // Button to filter online friends
        JButton filterOnlineButton = new JButton("Show Online Only");
        filterOnlineButton.addActionListener(new ActionListener() {
            private boolean isFilteringOnline = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                friendListPanel.removeAll(); // Clear the list
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
                isFilteringOnline = !isFilteringOnline;
                friendListPanel.revalidate();
                friendListPanel.repaint();
            }
        });
        mainPanel.add(filterOnlineButton, BorderLayout.SOUTH);

        // Add search field functionality
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

        searchField.addActionListener(e -> {
            String searchText = searchField.getText().trim().toLowerCase();
            friendListPanel.removeAll(); // Clear the list
            for (endUserModel user : filteredUsers) {
                if (user.getAccountName().toLowerCase().contains(searchText)) {
                    friendListPanel.add(createFriendListObject(user));
                }
            }
            friendListPanel.revalidate();
            friendListPanel.repaint();
        });

        // Add the main panel to the dialog
        friendListDialog.add(mainPanel);
        friendListDialog.setVisible(true);
    }

}

