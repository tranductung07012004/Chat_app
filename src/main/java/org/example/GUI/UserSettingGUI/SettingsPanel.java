package org.example.GUI.UserSettingGUI;

import org.example.Handler.SettingPanelHandler.SettingsHandler;
import java.awt.*;
import javax.swing.*;

import org.example.GUI.MainFrameGUI;

public class SettingsPanel extends JPanel {
    private MainFrameGUI mainFrame;
    private JButton backButton;
    private JButton logoutButton;
    private JButton saveInfoButton;
    private JButton savePasswordButton;
    private JTextField fullNameField;
    private JComboBox<String> genderComboBox;
    private JTextField addressField;
    private JTextField dobField;
    private JTextField emailField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;

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
        backButton = new JButton("Back");
        logoutButton = new JButton("Log Out");

        navButtonPanel.add(backButton);
        navButtonPanel.add(logoutButton);

        add(navButtonPanel, BorderLayout.SOUTH);
        SettingsHandler settingsHandler = new SettingsHandler(this, mainFrame);
    }

    private JPanel createUpdateInfoPanel() {
        JPanel updateInfoPanel = new JPanel(new BorderLayout(10, 10));
        updateInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Inner panel to hold the fields with a grid layout
        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        // Labels and fields for user information
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField();

        JLabel genderLabel = new JLabel("Gender:");
        String[] genders = {"Male", "Female", "Other"};
        genderComboBox = new JComboBox<>(genders);

        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobField = new JTextField("dd/MM/yyyy");
        dobField.setForeground(Color.GRAY);

        dobField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (dobField.getText().equals("dd/MM/yyyy")) {
                    dobField.setText("");
                    dobField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (dobField.getText().isEmpty()) {
                    dobField.setText("dd/MM/yyyy");
                    dobField.setForeground(Color.GRAY);
                }
            }
        });


        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

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

        saveInfoButton = new JButton("Save Changes");
        updateInfoPanel.add(infoPanel, BorderLayout.CENTER);
        updateInfoPanel.add(saveInfoButton, BorderLayout.SOUTH);

        return updateInfoPanel;
    }

    private JPanel createPasswordPanel() {
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Panel for password fields
        JPanel passwordFieldsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
    
        // Old Password
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        oldPasswordField = new JPasswordField(15);
    
        // New Password
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordField = new JPasswordField(15);
    
        // Confirm New Password
        JLabel confirmPasswordLabel = new JLabel("Confirm New Password:");
        JPasswordField confirmPasswordField = new JPasswordField(15);
    
        // Add all fields to the panel
        passwordFieldsPanel.add(oldPasswordLabel);
        passwordFieldsPanel.add(oldPasswordField);
        passwordFieldsPanel.add(newPasswordLabel);
        passwordFieldsPanel.add(newPasswordField);
        passwordFieldsPanel.add(confirmPasswordLabel);
        passwordFieldsPanel.add(confirmPasswordField);
    
        // Save button
        savePasswordButton = new JButton("Save Changes");
        savePasswordButton.setPreferredSize(new Dimension(140, 30));
        
        // Add validation on Save Changes button
        savePasswordButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
          
        });
    
        // Layout arrangement
        passwordPanel.add(passwordFieldsPanel);
        passwordPanel.add(Box.createVerticalStrut(20));
        passwordPanel.add(savePasswordButton);
    
        return passwordPanel;
    }
    
    // Getters for handler to access components
    public JButton getBackButton() {
        return backButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getSaveInfoButton() {
        return saveInfoButton;
    }

    public JButton getSavePasswordButton() {
        return savePasswordButton;
    }

    public JTextField getFullNameField() {
        return fullNameField;
    }

    public JComboBox<String> getGenderComboBox() {
        return genderComboBox;
    }

    public JTextField getAddressField() {
        return addressField;
    }

    public JTextField getDobField() {
        return dobField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getOldPasswordField() {
        return oldPasswordField;
    }

    public JPasswordField getNewPasswordField() {
        return newPasswordField;
    }
}

