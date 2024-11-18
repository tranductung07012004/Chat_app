package org.example.GUI.ChatPanelGUI;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.ChatPanelHandler.SidebarHandler;
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
    

    public void createFriendList() {
        // Tạo frame mới cho danh sách bạn bè
        JFrame friendListFrame = new JFrame("Friend List");
        friendListFrame.setSize(300, 550); // Kích thước cố định
        friendListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        friendListFrame.setLayout(new BorderLayout());
        friendListFrame.setLocationRelativeTo(null); // Đặt ở giữa màn hình

        // Tạo panel chính cho danh sách bạn bè
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Ô tìm kiếm để lọc theo tên
        JTextField searchField = new JTextField("Search...");
        searchField.setPreferredSize(new Dimension(0, 30)); // Chiều cao 30px
        searchField.setForeground(Color.GRAY); // Placeholder màu xám

        // Xóa placeholder khi focus và khôi phục nếu để trống khi mất focus
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

        // Panel chứa danh sách bạn bè
        JPanel friendListPanel = new JPanel();
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));

        // Tạo danh sách bạn bè từ `contacts`
        List<Contact> filteredContacts = new ArrayList<>();
        filteredContacts.add(new Contact("Alice", true, false));
        filteredContacts.add(new Contact("Bob", false, false));
        for (Contact contact : filteredContacts) {
            friendListPanel.add(createContactPanel(contact));
        }

        // Thêm thanh cuộn nếu danh sách bạn bè quá dài
        JScrollPane friendScrollPane = new JScrollPane(friendListPanel);
        mainPanel.add(friendScrollPane, BorderLayout.CENTER);

        // Nút để lọc danh sách online
        JButton filterOnlineButton = new JButton("Show Online Only");
        filterOnlineButton.addActionListener(new ActionListener() {
            private boolean isFilteringOnline = false; // Trạng thái lọc

            @Override
            public void actionPerformed(ActionEvent e) {
                friendListPanel.removeAll(); // Xóa tất cả các panel hiện tại
                if (isFilteringOnline) {
                    // Thoát chế độ lọc, hiển thị toàn bộ danh sách
                    for (Contact contact : filteredContacts) {
                        friendListPanel.add(createContactPanel(contact));
                    }
                    filterOnlineButton.setText("Show Online Only");
                } else {
                    // Lọc danh sách chỉ hiện online
                    for (Contact contact : filteredContacts) {
                        if (contact.isOnline()) {
                            friendListPanel.add(createContactPanel(contact));
                        }
                    }
                    filterOnlineButton.setText("Show All");
                }
                isFilteringOnline = !isFilteringOnline; // Đổi trạng thái lọc
                friendListPanel.revalidate();
                friendListPanel.repaint();
            }
        });

        // Thêm nút lọc ở dưới cùng
        mainPanel.add(filterOnlineButton, BorderLayout.SOUTH);

        // Thêm ActionListener cho ô tìm kiếm để lọc theo tên
        searchField.addActionListener(e -> {
            String searchText = searchField.getText().trim().toLowerCase();
            friendListPanel.removeAll(); // Xóa tất cả các panel hiện tại
            for (Contact contact : contacts) {
                if (contact.getName().toLowerCase().contains(searchText)) {
                    friendListPanel.add(createContactPanel(contact));
                }
            }
            friendListPanel.revalidate();
            friendListPanel.repaint();
        });

        // Thêm panel chính vào frame
        friendListFrame.add(mainPanel);
        friendListFrame.setVisible(true);
    }

}

