package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private MainFrameGUI mainFrame;
    private boolean isUpdateInfoVisible = false;
    private boolean isPasswordFieldsVisible = false;

    public SettingsPanel(MainFrameGUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(700, 550));

        // Title Label
        JLabel titleLabel = new JLabel("Settings", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Create tabbed pane for sections
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 16));

        // Update Information Panel
        JPanel updateInfoPanel = createUpdateInfoPanel();
        tabbedPane.addTab("Update Information", updateInfoPanel);

        // Change Password Panel
        JPanel passwordPanel = createPasswordPanel();
        tabbedPane.addTab("Change Password", passwordPanel);

        // Add tabbed pane to center
        add(tabbedPane, BorderLayout.CENTER);

        // Bottom Navigation Panel
        JPanel navButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton backButton = new JButton("Back");
        JButton logoutButton = new JButton("Log Out");

        backButton.addActionListener(e -> mainFrame.showChatPanel());
        logoutButton.addActionListener(e -> mainFrame.logOut());

        navButtonPanel.add(backButton);
        navButtonPanel.add(logoutButton);

        add(navButtonPanel, BorderLayout.SOUTH);
    }

    private JPanel createUpdateInfoPanel() {
        JPanel updateInfoPanel = new JPanel();
        updateInfoPanel.setLayout(new BorderLayout(10, 10));
        updateInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Inner panel to hold the fields with a grid layout
        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        // Labels and fields for user information
        JLabel fullNameLabel = new JLabel("Full Name:");
        JTextField fullNameField = new JTextField();

        JLabel genderLabel = new JLabel("Gender:");
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();

        JLabel dobLabel = new JLabel("Date of Birth:");
        JTextField dobField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        infoPanel.add(fullNameLabel);
        infoPanel.add(fullNameField);
        infoPanel.add(genderLabel);
        infoPanel.add(genderComboBox);
        infoPanel.add(addressLabel);
        infoPanel.add(addressField);
        infoPanel.add(dobLabel);
        infoPanel.add(dobField);
        infoPanel.add(emailLabel);
        infoPanel.add(emailField);

        JButton saveInfoButton = new JButton("Save Changes");
        saveInfoButton.addActionListener(e -> {
            // Implement save logic here
        });

        updateInfoPanel.add(infoPanel, BorderLayout.CENTER);
        updateInfoPanel.add(saveInfoButton, BorderLayout.SOUTH);

        return updateInfoPanel;
    }

    private JPanel createPasswordPanel() {
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel for both old and new password fields horizontally aligned
        JPanel passwordFieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Reduced vertical space
        passwordFieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Old Password
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        oldPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField oldPasswordField = new JPasswordField(15);
        passwordFieldsPanel.add(oldPasswordLabel);
        passwordFieldsPanel.add(oldPasswordField);
    
        // New Password
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField newPasswordField = new JPasswordField(15);
        passwordFieldsPanel.add(newPasswordLabel);
        passwordFieldsPanel.add(newPasswordField);
    
        // Save button centered below the fields
        JButton savePasswordButton = new JButton("Save Changes");
        savePasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        savePasswordButton.setPreferredSize(new Dimension(140, 30));
        savePasswordButton.setFont(new Font("Arial", Font.BOLD, 14));
        savePasswordButton.addActionListener(e -> {
            // Implement save logic here
        });
    
        // Add components to passwordPanel with spacing
        passwordPanel.add(passwordFieldsPanel);
        passwordPanel.add(Box.createVerticalStrut(20));  // Spacing between fields and button
        passwordPanel.add(savePasswordButton);
    
        return passwordPanel;
    }
    
    
    
    
}
