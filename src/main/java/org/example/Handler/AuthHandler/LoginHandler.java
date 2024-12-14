package org.example.Handler.AuthHandler;

import org.example.GUI.Auth.LoginGUI;
import org.example.GUI.MainFrameGUI;
import org.example.Model.DBConn;
import org.example.Model.endUserModel;
import org.example.Model.loginHistoryModel;
import org.example.Service.resetPass;

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
                resetPass.handlePasswordResetRequest(email);
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

        if (endUserModel.checkOnline(username)) {
            JOptionPane.showMessageDialog(loginScreen, "Tài khoản đang được đăng nhập ở nơi khác, vui lòng kiểm tra lại", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

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






}
