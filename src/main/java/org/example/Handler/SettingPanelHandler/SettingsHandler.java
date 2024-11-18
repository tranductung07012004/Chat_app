package org.example.Handler.SettingPanelHandler;

import org.example.GUI.MainFrameGUI;
import org.example.GUI.UserSettingGUI.SettingsPanel;
import javax.swing.*;

public class SettingsHandler {
    private SettingsPanel settingsPanel;
    private MainFrameGUI mainFrame;

    public SettingsHandler(SettingsPanel settingsPanel, MainFrameGUI mainFrame) {
        this.settingsPanel = settingsPanel;
        this.mainFrame = mainFrame;
        initializeHandlers();
        
    }

    private void initializeHandlers() {
        // Back button
        settingsPanel.getBackButton().addActionListener(e -> mainFrame.showChatPanel());

        // Logout button
        settingsPanel.getLogoutButton().addActionListener(e -> mainFrame.logOut());

        // Save Information button
        settingsPanel.getSaveInfoButton().addActionListener(e -> saveUserInfo());

        // Save Password button
        settingsPanel.getSavePasswordButton().addActionListener(e -> savePassword());
    }

    private void saveUserInfo() {
        String fullName = settingsPanel.getFullNameField().getText();
        String gender = (String) settingsPanel.getGenderComboBox().getSelectedItem();
        String address = settingsPanel.getAddressField().getText();
        String dob = settingsPanel.getDobField().getText();
        String email = settingsPanel.getEmailField().getText();

        // Implement saving logic here
        JOptionPane.showMessageDialog(null, "User information saved successfully.");
    }

    private void savePassword() {
        String oldPassword = new String(settingsPanel.getOldPasswordField().getPassword());
        String newPassword = new String(settingsPanel.getNewPasswordField().getPassword());

        // Implement password change logic here
        JOptionPane.showMessageDialog(null, "Password changed successfully.");
    }
}
