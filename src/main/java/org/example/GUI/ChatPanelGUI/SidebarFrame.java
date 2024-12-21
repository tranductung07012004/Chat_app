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
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SidebarFrame extends JPanel {
    private static MainFrameGUI mainFrame;
    private JComboBox<String> optionsComboBox;
    private static List<Contact> contacts;

    private static JDialog friendListDialog;
    private JPanel friendListPanel; // Declare as class field
    private static JPanel contactsPanel; // Declare as class field
    private List<endUserModel> filteredUsers;

    public class overallComponents {
        public JButton searchAllMess = new JButton("Tìm tất cả tin nhắn");
        public JDialog searchAllMessDialog = new JDialog(mainFrame, "Tìm tất cả tin nhắn", true);
        public JTextField searchAllMessField = new JTextField(10);
        public JButton submitSearchAllMess = new JButton("OK");
        public JButton cancelSearchAllMess = new JButton("Hủy");

        public JDialog searchResultDialog = new JDialog(mainFrame, "Tìm kết quả", true);
        public JButton cancelSearchResultDialog = new JButton("Tắt");
        public DefaultTableModel tableModel;


    }
    overallComponents components;

    public overallComponents getTheComponents() { return this.components; }

    public SidebarFrame(MainFrameGUI mainFrame) {
        this.components = new overallComponents();
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Search bar at the top
        JTextField searchField = new JTextField("Tìm kiếm..."); // Placeholder text
        searchField.setPreferredSize(new Dimension(0, 30));
        searchField.setForeground(Color.GRAY); // Placeholder color

        // Add FocusListener to handle placeholder
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Tìm kiếm...")) {
                    searchField.setText(""); // Clear placeholder
                    searchField.setForeground(Color.BLACK); // Change text color
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Tìm kiếm..."); // Restore placeholder
                    searchField.setForeground(Color.GRAY); // Change text color back
                }
            }
        });
        searchField.addActionListener(e -> {
            String searchText = searchField.getText().trim().toLowerCase();
            contactsPanel.removeAll(); // Xóa danh sách hiện tại

            // Lọc danh sách liên lạc
            for (Contact contact : contacts) {
                if (contact.getName().toLowerCase().contains(searchText)) {
                    contactsPanel.add(createContactPanel(contact)); // Thêm liên lạc phù hợp
                }
            }

            // Làm mới giao diện
            contactsPanel.revalidate();
            contactsPanel.repaint();

            // Nếu không tìm thấy liên lạc nào
            if (contactsPanel.getComponentCount() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Không có kết quả cho: " + searchText,
                        "Kết quả",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });


        add(searchField, BorderLayout.NORTH);

        // Panel for contacts and "Create Group Chat" button
        JPanel topPanel = new JPanel(new BorderLayout());

        // Left side for contacts
        contactsPanel = new JPanel();
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));
        contactsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding


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
        JButton friendList = new JButton("Danh sách bạn");
        friendList.setPreferredSize(new Dimension(175, 25));
        components.searchAllMess = new JButton("Tìm tất cả message");
        components.searchAllMess.setPreferredSize(new Dimension(175, 25));

        // Dropdown list for actions (on the left side)
        String[] options = { "Nhắn tin", "Lời mời kết bạn", "Cài đặt" };
        optionsComboBox = new JComboBox<>(options);
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dropdownPanel.add(optionsComboBox);
        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.add(friendList, BorderLayout.CENTER);
        actionPanel.add(components.searchAllMess, BorderLayout.SOUTH);
        bottomPanel.add(actionPanel, BorderLayout.WEST);
        bottomPanel.add(dropdownPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
        SidebarHandler sidebarHandler = new SidebarHandler(this, mainFrame);
        friendList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                createFriendList();
            }
        });

        // Thêm các dialog vào
        searchAllMessDialog();
        createSearchResultDialog();
    }


    private void createSearchResultDialog() {
        this.components.searchResultDialog.setSize(550, 200);
        this.components.searchResultDialog.setLayout(new BorderLayout());

        String[] columnNames = {"Người gửi (Tên)", "Người nhận (Tên)", "Nhóm nhận", "Nội dung"};
        Object[][] data = {};
        this.components.tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable friendTable = new JTable(this.components.tableModel) {
            @Override
            public String getToolTipText(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int column = columnAtPoint(e.getPoint());
                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.components.tableModel);
        friendTable.setRowSorter(sorter);

        sorter.setSortable(0, true);
        sorter.setSortable(1, true);

        this.components.searchResultDialog.add(new JScrollPane(friendTable), BorderLayout.CENTER);
        this.components.searchResultDialog.add(this.components.cancelSearchResultDialog, BorderLayout.SOUTH);
        this.components.searchResultDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.searchResultDialog.setVisible(false);
    }

    private void searchAllMessDialog() {
        this.components.searchAllMessDialog.setSize(400, 130);
        this.components.searchAllMessDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tin nhắn:"));
        inputPanel.add(this.components.searchAllMessField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitSearchAllMess);
        buttonPanel.add(this.components.cancelSearchAllMess);

        this.components.searchAllMessDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.searchAllMessDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.searchAllMessDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.searchAllMessDialog.setVisible(false);



    }

    public static void updateContactsPanel() {
        // Assuming `contactsPanel` is the JPanel displaying contacts
        List<Contact>newcontacts=FriendListHandle.getNewcontacts();
        contacts.addAll(FriendListHandle.getNewcontacts());

        List<Contact>loadcontacts=SidebarHandler.loadAllContacts(mainFrame.getCurrentUserId());
        // Create a Contact object for the target user

        for (Contact existingContact : loadcontacts) {
            for (Contact contact:contacts)
                if (existingContact.getId() == contact.getId() && existingContact.isGroup()==contact.isGroup()) {
                    contacts.remove(contact);
                    break;
                }
        }

        contacts.addAll(loadcontacts);
        newcontacts.clear();
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

        JLabel statusLabel;
        if (contact.isGroup()) {
            statusLabel = new JLabel("Nhóm");
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            statusLabel.setForeground(Color.BLUE);  // Set color to blue for group
        } else {
            statusLabel = new JLabel(contact.isOnline() ? "Online" : "Offline");
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            statusLabel.setForeground(contact.isOnline() ? Color.GREEN : Color.RED);  // Green for online, red for offline
        }

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
        JButton chatButton = new JButton("Nhắn tin");
        chatButton.setFont(new Font("Arial", Font.PLAIN, 12));
        FriendListHandle handler = new FriendListHandle();
        // Chat button logic
        chatButton.addActionListener(e -> {
            handler.openChat(mainFrame.getCurrentUserId(), user); // Pass current user ID and target user
        });

        // Unfriend button
        JButton unfriendButton = new JButton("Hủy kết bạn");
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

        // Create Group Chat button
        JButton createGroupChatButton = new JButton("Tạo nhóm chat");
        createGroupChatButton.setFont(new Font("Arial", Font.PLAIN, 12));
        createGroupChatButton.addActionListener(e -> {
            // Create group chat logic
            handler.createGroupChat(mainFrame.getCurrentUserId(), user);

            // Pass current user ID and target user
        });

        // Add buttons to the panel
        buttonsPanel.add(chatButton);
        buttonsPanel.add(unfriendButton);
        buttonsPanel.add(createGroupChatButton); // Add the new button

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
        friendListDialog = new JDialog(mainFrame, "Danh sách bạn", true);
        friendListDialog.setSize(500, 550);
        friendListDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        friendListDialog.setLayout(new BorderLayout());
        friendListDialog.setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Search field
        JTextField searchField = new JTextField("Tìm kiếm...");
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
        JButton filterOnlineButton = new JButton("Chỉ ai online");
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
                    filterOnlineButton.setText("Chỉ ai online");
                } else {
                    // Show only online friends
                    for (endUserModel user : filteredUsers) {
                        if (user.getOnline()) {
                            friendListPanel.add(createFriendListObject(user));
                        }
                    }
                    filterOnlineButton.setText("Hiện tất cả");
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
                if (searchField.getText().equals("Tìm kiếm...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Tìm kiếm...");
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
    public static Contact getSpecialContact(Contact targetContact) {
        for (Contact contact : contacts) {
            if (contact.isGroup == targetContact.isGroup &&
                    contact.Id == targetContact.Id) {
                return contact;
            }
        }
        return null; // Return null if no matching contact is found
    }

}

