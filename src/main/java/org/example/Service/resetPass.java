package org.example.Service;

import org.example.Model.DBConn;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class resetPass {

    public static String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    public static void sendEmail(String toEmail, String newPassword) {
        String fromEmail = "tranductung07012004@gmail.com"; // Your email address
        String host = "smtp.gmail.com"; // SMTP server (e.g., Gmail SMTP: smtp.gmail.com)

        // Set properties for email session
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true"); // Bật xác thực
        properties.put("mail.smtp.port", "587"); // Cổng dùng cho STARTTLS
        properties.put("mail.smtp.starttls.enable", "true"); // Kích hoạt STARTTLS
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // Tin cậy máy chủ
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2"); // Đảm bảo sử dụng TLS v1.2

        // Get the session
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tranductung07012004@gmail.com", "pttz vfqe vhyb xdcw");
            }
        });

        // Create a new task to send the email in a background thread
        Runnable emailTask = new Runnable() {
            @Override
            public void run() {
                try {
                    // Create a new email message
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(fromEmail));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
                    message.setSubject("Password Reset Request");
                    message.setText("Your new password is: " + newPassword);

                    // Send the email
                    Transport.send(message);
                    // Update the UI on the EDT (Event Dispatch Thread)
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Password reset successful. Please check your email.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    });

                } catch (MessagingException e) {
                    // Handle failure to send the email
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Failed to send mail. Please recheck the connection or your email.", "Failed", JOptionPane.ERROR_MESSAGE);
                    });
                    e.printStackTrace();
                }
            }
        };

        // Use ExecutorService to manage the background thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(emailTask);
        executor.shutdown();
    }

    public static void handlePasswordResetRequest(String email) {
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

                } else {
                    JOptionPane.showMessageDialog(null, "No account found with that email address.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while processing the password reset request.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
