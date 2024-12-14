package org.example.Handler.SettingPanelHandler;

import org.example.GUI.MainFrameGUI;
import org.example.GUI.UserSettingGUI.SettingsPanel;
import org.example.Handler.ChatPanelHandler.Contact;
import org.example.Handler.ChatPanelHandler.FriendListHandle;
import org.example.Model.DBConn;
import org.example.Model.endUserModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        settingsPanel.getLogoutButton().addActionListener(e -> {
            // Update user's online status to false before logging out
            endUserModel user = endUserModel.getUserFromId(mainFrame.getCurrentUserId());
            user.setOnline(false);  // Set user as offline
            //remove newcontacts
            List<Contact>contacts=FriendListHandle.getNewcontacts();
            contacts.clear();

            // Now log the user out
            mainFrame.logOut();

        });


        // Save Information button
        settingsPanel.getSaveInfoButton().addActionListener(e -> saveUserInfo());

        // Save Password button
        settingsPanel.getSavePasswordButton().addActionListener(e -> savePassword());

    }

    private void saveUserInfo() {
        String accountName = settingsPanel.getFullNameField().getText().trim(); // Assuming fullNameField maps to account_name
        String gender = (String) settingsPanel.getGenderComboBox().getSelectedItem();
        String address = settingsPanel.getAddressField().getText().trim();
        String dob = settingsPanel.getDobField().getText().trim();
        String email = settingsPanel.getEmailField().getText().trim();

        // Validate email format if email is provided
        if (!email.isEmpty() && !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(null, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate and parse date of birth if dob is provided
        java.sql.Date sqlDate = null;
        if (dob.equals("dd/MM/yyyy"))
            dob="";
        if (!dob.isEmpty()) {
            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                dateFormatter.setLenient(false);
                java.util.Date parsedDate = dateFormatter.parse(dob);

                // Convert to SQL date
                sqlDate = new java.sql.Date(parsedDate.getTime());
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Invalid date format! Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Handle gender translation
        if (gender != null) {
            gender = switch (gender) {
                case "Male" -> "Nam";
                case "Female" -> "Nữ";
                case "Other" -> "Khác";
                default -> "Khác"; // Default to "Khác" if none of the options match
            };
        }

        // Prepare update query with conditional values for each field
        StringBuilder updateQuery = new StringBuilder("UPDATE END_USER SET ");
        List<Object> params = new ArrayList<>();

        if (!accountName.isEmpty()) {
            updateQuery.append("account_name = ?, ");
            params.add(accountName);
        }
        if (gender != null && !gender.isEmpty()) {
            updateQuery.append("gender = ?, ");
            params.add(gender);
        }
        if (!address.isEmpty()) {
            updateQuery.append("address = ?, ");
            params.add(address);
        }
        if (sqlDate != null) {
            updateQuery.append("dob = ?, ");
            params.add(sqlDate);
        }
        if (!email.isEmpty()) {
            updateQuery.append("email = ?, ");
            params.add(email);
        }

        // Remove the trailing comma and space
        updateQuery.setLength(updateQuery.length() - 2);
        updateQuery.append(" WHERE user_id = ?");

        // Add user_id as the last parameter
        int userId = mainFrame.getCurrentUserId(); // Get the current user ID
        params.add(userId);

        // Execute the update query
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery.toString())) {

            // Set the parameters dynamically based on the fields that are provided
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "User information updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update user information.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error occurred!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void savePassword() {
        String oldPassword = new String(settingsPanel.getOldPasswordField().getPassword());
        String newPassword = new String(settingsPanel.getNewPasswordField().getPassword());

        // Password validation
        if (newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "New password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (newPassword.length() < 6) {
            JOptionPane.showMessageDialog(null, "New password must be at least 6 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String validatePasswordQuery = "SELECT pass FROM END_USER WHERE user_id = ?";
        String updatePasswordQuery = "UPDATE END_USER SET pass = ? WHERE user_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement validateStmt = conn.prepareStatement(validatePasswordQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updatePasswordQuery)) {

            int userId = mainFrame.getCurrentUserId(); // Fetch current user ID
            validateStmt.setInt(1, userId);

            try (ResultSet rs = validateStmt.executeQuery()) {
                if (rs.next() && rs.getString("pass").equals(oldPassword)) {
                    updateStmt.setString(1, newPassword);
                    updateStmt.setInt(2, userId);

                    int rowsUpdated = updateStmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Old password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error occurred!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
