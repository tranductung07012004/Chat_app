package org.example.Handler.AuthHandler;

import org.example.GUI.Auth.LoginGUI;
import org.example.GUI.MainFrameGUI;
import org.example.Model.DBConn;
import org.example.Model.endUserModel;
import org.example.Model.loginHistoryModel;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            String email = emailField.getText().trim();
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

//        if (endUserModel.checkOnline(username)) {
//            JOptionPane.showMessageDialog(loginScreen, "Tài khoản đang được đăng nhập ở nơi khác, vui lòng kiểm tra lại", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
//            return;
//        }

        if (endUserModel.checkBlockedByAdmin(username)) {
            JOptionPane.showMessageDialog(loginScreen, "Tài khoản đã bị khóa bởi admin, vui lòng liên hệ tổng đài hỗ trợ", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (authenticateUser(username, password)) {
            //JOptionPane.showMessageDialog(loginScreen, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.setCurrentUserId(currentUserId); // Pass userId to MainFrameGUI
            endUserModel user=endUserModel.getUserFromId(currentUserId);
            user.setOnline(true);
            addUserLoginToLoginHistory(username);
            mainFrame.showChatPanel();
            mainFrame.getChatClient().notifyLogin(currentUserId);
        } else {
            JOptionPane.showMessageDialog(loginScreen, "Tài khoản hoặc mật khẩu không đúng", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addUserLoginToLoginHistory(String username) {
        int res = loginHistoryModel.recordLoginOfUser(username);
        if (res == -1) {
            JOptionPane.showMessageDialog(loginScreen, "Người dùng không tồn tại", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (res == 0) {
            JOptionPane.showMessageDialog(loginScreen, "Thêm bản ghi vào login_history không thành công, hãy kiểm tra lại", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println("Hành vi đăng nhập của người dùng đã được thêm bản ghi vào login_history");
    }

    private boolean authenticateUser(String username, String password) {
        String query = "SELECT user_id FROM end_user WHERE username = ? AND pass = ? AND blockedaccountbyadmin=false";

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
}
