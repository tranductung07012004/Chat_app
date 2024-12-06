package org.example.GUI.AdminBoard;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AdminBoardHandler.verifyAdminHandler;

import javax.swing.*;

public class verifyAdminGUI extends JPanel {
    private JButton goBackToLogin;
    private JButton adminVerification;
    private MainFrameGUI mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField adminCodeField;

    public verifyAdminGUI(MainFrameGUI inputMainFrame) {
        this.mainFrame = inputMainFrame;

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(150, 150, 100, 40);

        usernameField = new JTextField();
        usernameField.setBounds(250, 150, 200, 30);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(150, 200, 100, 40);

        passwordField = new JPasswordField();
        passwordField.setBounds(250, 200, 200, 30);

        // Admin Code Label and Field
        JLabel adminCodeLabel = new JLabel("Admin Code:");
        adminCodeLabel.setBounds(150, 250, 100, 40);

        adminCodeField = new JPasswordField();
        adminCodeField.setBounds(250, 250, 200, 30);

        // Buttons
        adminVerification = new JButton("Verify");
        adminVerification.setBounds(250, 310, 90, 30);

        goBackToLogin = new JButton("Go Back");
        goBackToLogin.setBounds(350, 310, 90, 30);

        // Adding components
        new verifyAdminHandler(this, mainFrame);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(adminCodeLabel);
        add(adminCodeField);
        add(adminVerification);
        add(goBackToLogin);

        // Panel settings
        setSize(700, 500);
        setLayout(null);
    }

    // Getters for the fields
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getAdminCodeField() {
        return adminCodeField;
    }

    public JButton getGoBackToLoginBtn() {
        return goBackToLogin;
    }

    public JButton getAdminVerificationBtn() {
        return adminVerification;
    }
}
