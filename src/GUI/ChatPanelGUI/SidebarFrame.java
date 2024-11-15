package GUI.ChatPanelGUI;

import Handler.ChatPanelHandler.SidebarHandler;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import GUI.MainFrameGUI;

public class SidebarFrame extends JPanel {
    private MainFrameGUI mainFrame;
    private JComboBox<String> optionsComboBox;
    private List<Contact> contacts;



    public SidebarFrame(MainFrameGUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Search bar at the top
        JTextField searchField = new JTextField("");
        searchField.setPreferredSize(new Dimension(0, 30));
        add(searchField, BorderLayout.NORTH);

        // Panel for contacts and "Create Group Chat" button
        JPanel topPanel = new JPanel(new BorderLayout());

        // Left side for contacts
        JPanel contactsPanel = new JPanel();
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));
        contactsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding

        // Simulated contact list
        contacts = new ArrayList<>();
        contacts.add(new Contact("Alice", true));
        contacts.add(new Contact("Bob", false));
        contacts.add(new Contact("Charlie", true));

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

        // Dropdown list for actions (on the left side)
        String[] options = {"Chat", "Friend Request", "Settings"};
        optionsComboBox = new JComboBox<>(options);
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dropdownPanel.add(optionsComboBox);
        bottomPanel.add(dropdownPanel, BorderLayout.WEST);

        add(bottomPanel, BorderLayout.SOUTH);
        SidebarHandler sidebarHandler = new SidebarHandler(this, mainFrame);

    }

    // Helper method to create a contact panel
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

        return contactPanel;
    }

    public JComboBox<String> getOptionsComboBox() {
        return optionsComboBox;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    // Inner class to represent a contact
    public static class Contact {
        private String name;
        private boolean online;
        public boolean isGroup=false;

        public Contact(String name, boolean online) {
            this.name = name;
            this.online = online;
        }

        public String getName() {
            return name;
        }

        public boolean isOnline() {
            return online;
        }
    }
}
