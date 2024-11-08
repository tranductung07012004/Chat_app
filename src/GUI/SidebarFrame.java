package GUI;

import javax.swing.*;
import java.awt.*;

public class SidebarFrame extends JPanel {
    private MainFrameGUI mainFrame;

    public SidebarFrame(MainFrameGUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Search bar at the top
        JTextField searchField = new JTextField("Search...");
        add(searchField, BorderLayout.NORTH);

        // Create a panel for contacts and group chat button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // Create Group Chat button below the search bar
        JButton createGroupButton = new JButton("Create Group Chat");
        topPanel.add(createGroupButton, BorderLayout.SOUTH);

        // Left side for contacts
        JPanel contactsPanel = new JPanel();
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));

        // Simulated contact list for demonstration
        String[] contacts = {"Alice", "Bob"};
        for (String contact : contacts) {
            JLabel contactLabel = new JLabel(contact);
            contactsPanel.add(contactLabel);
        }

        JScrollPane contactScrollPane = new JScrollPane(contactsPanel);
        topPanel.add(contactScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.CENTER);

        // Panel for the bottom section (dropdown list and action button)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        // Dropdown list for actions (on the left side)
        String[] options = {"Chat", "Notification", "Settings"};
        JComboBox<String> optionsComboBox = new JComboBox<>(options);
        optionsComboBox.addActionListener(e -> {
            String selectedOption = (String) optionsComboBox.getSelectedItem();
            if ("Settings".equals(selectedOption)) {
                mainFrame.showSettingsPanel();  // Show Settings Panel when selected
            } else if ("Chat".equals(selectedOption)) {
                mainFrame.showChatPanel();  // Show Chat Panel when selected
            }
        });

        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dropdownPanel.add(optionsComboBox);
        bottomPanel.add(dropdownPanel, BorderLayout.WEST);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}
