package org.example.Handler.AdminBoardHandler;

import org.example.GUI.AdminBoard.verifyAdminGUI;
import org.example.GUI.MainFrameGUI;
import org.example.Model.DBConn;
import org.example.Model.endUserModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.Handler.AuthHandler.RegisterHandler.ADMIN_CODE;

public class verifyAdminHandler implements ActionListener {
    private final verifyAdminGUI verifyAdminScreen;
    private final MainFrameGUI mainFrame;
    private int currentUserId=-1;

    // Constructor to initialize GUI components and add listeners
    public verifyAdminHandler(verifyAdminGUI inputVerifyAdminScreen, MainFrameGUI inputMainFrame) {
        this.mainFrame = inputMainFrame;
        this.verifyAdminScreen = inputVerifyAdminScreen;

        verifyAdminScreen.getGoBackToLoginBtn().addActionListener(this);
        verifyAdminScreen.getAdminVerificationBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == verifyAdminScreen.getGoBackToLoginBtn()) {
            mainFrame.showLoginPanel();
        }
        else if (e.getSource() == verifyAdminScreen.getAdminVerificationBtn()) {
            String username = verifyAdminScreen.getUsernameField().getText();
            String password = new String(verifyAdminScreen.getPasswordField().getPassword());
            String adminCode = new String(verifyAdminScreen.getAdminCodeField().getPassword());

            // Check if the credentials are valid
            if (validateAdminCredentials(username, password, adminCode)) {
                // Admin verified successfully, show admin panel

                endUserModel user=endUserModel.getUserFromId(mainFrame.getCurrentUserId());
                user.setOnline(true);
                endUserModel.AdminSessionUsername = username;
                mainFrame.showAdminPanel();


            }
        }
    }

    private boolean validateAdminCredentials(String username, String password, String adminCode) {
        // Check if any field is empty
        if (username.isEmpty() || password.isEmpty() || adminCode.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }



        boolean success = false;

        // SQL query to validate user credentials
        String query = "SELECT user_id FROM end_user WHERE username = ? AND pass = ? AND isadmin = true";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the parameters for the SQL query
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // User is a valid admin
                    mainFrame.setCurrentUserId(rs.getInt("user_id")); // Store the logged-in user's ID

                    success = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // If no valid admin is found
        if (!success) {
            JOptionPane.showMessageDialog(null, "Invalid username or password.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }

        // Check if the admin code matches
        final String ADMIN_CODE = "12345"; // The correct admin code
        if (!adminCode.equals(ADMIN_CODE)) {
            JOptionPane.showMessageDialog(null, "Admin code không đúng.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }

        return success;
    }

}
