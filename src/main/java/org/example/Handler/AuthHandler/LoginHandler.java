package org.example.Handler.AuthHandler;

import org.example.GUI.Auth.LoginGUI;
import org.example.GUI.MainFrameGUI;
import org.example.Model.DBConn;
import org.example.Model.endUserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class LoginHandler implements ActionListener {
    private final LoginGUI loginScreen;
    private final MainFrameGUI mainFrame;
    private int currentUserId = -1; // Stores the ID of the logged-in user

    public LoginHandler(LoginGUI inputLoginScreen, MainFrameGUI inputFrame) {
        this.loginScreen = inputLoginScreen;
        this.mainFrame = inputFrame;

        loginScreen.getAdminBtn().addActionListener(this);
        loginScreen.getRegisterBtn().addActionListener(this);

        loginScreen.getForgotPasswordLink().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openForgotPasswordDialog(inputFrame);
            }
        });

        loginScreen.getLoginBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginScreen.getAdminBtn()) {
            mainFrame.showVerifyAdminGUIPanel();
        } else if (e.getSource() == loginScreen.getRegisterBtn()) {
            mainFrame.showRegisterPanel();
        } else if (e.getSource() == loginScreen.getLoginBtn()) {
            handleLogin();
        }
    }

    private void openForgotPasswordDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Forgot Password", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());

        JLabel emailLabel = new JLabel("Enter your email:");
        JTextField emailField = new JTextField(20);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(event -> {
            String email = emailField.getText();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter your email!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                handlePasswordResetRequest(email);
                dialog.dispose();
            }
        });

        dialog.add(emailLabel);
        dialog.add(emailField);
        dialog.add(okButton);

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    private void handleLogin() {
        String username = loginScreen.getAccountName().getText();
        String password = new String(loginScreen.getPassField().getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginScreen, "Please enter both username and password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (authenticateUser(username, password)) {
            //JOptionPane.showMessageDialog(loginScreen, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

            mainFrame.setCurrentUserId(currentUserId); // Pass userId to MainFrameGUI
            endUserModel user=endUserModel.getUserFromId(currentUserId);
            user.setOnline(true);



            mainFrame.showChatPanel();
        } else {
            JOptionPane.showMessageDialog(loginScreen, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean authenticateUser(String username, String password) {
        String query = "SELECT user_id FROM end_user WHERE username = ? AND pass = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    currentUserId = rs.getInt("user_id"); // Store the logged-in user's ID
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    private void handlePasswordResetRequest(String email) {
        // Check if the email exists in the database
        String query = "SELECT user_id, username FROM end_user WHERE email = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Generate a new random password
                    String newPassword = generateRandomPassword();

                    // Update the password in the database
                    String updateQuery = "UPDATE end_user SET pass = ? WHERE email = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, newPassword);
                        updateStmt.setString(2, email);
                        updateStmt.executeUpdate();
                    }

                    // Send the new password to the user's email
                    sendEmail(email, newPassword);

                    JOptionPane.showMessageDialog(loginScreen, "Password reset successful. Please check your email.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(loginScreen, "No account found with that email address.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginScreen, "An error occurred while processing the password reset request.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendEmail(String toEmail, String newPassword) {
        String fromEmail = "your-email@example.com"; // Your email address
        String host = "smtp.example.com"; // SMTP server (e.g., Gmail SMTP: smtp.gmail.com)

        // Set properties for email session
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Get the session
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your-email@example.com", "your-email-password");
            }
        });

        try {
            // Create a new email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Password Reset Request");
            message.setText("Your new password is: " + newPassword);

            // Send the email
            Transport.send(message);
            System.out.println("Password reset email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
