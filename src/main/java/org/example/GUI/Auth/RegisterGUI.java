package org.example.GUI.Auth;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AuthHandler.RegisterHandler;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JPanel {
    private final JButton registerBtn;
    private final JButton goBackToLoginBtn;

    // Các trường nhập liệu
    private final JTextField usernameField;
    private final JTextField fullNameField;
    private final JTextField addressField;
    private final JTextField dobField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JTextField adminCodeInput;
    private final JRadioButton maleOption;
    private final JRadioButton femaleOption;
    private final JRadioButton otherOption;

    public RegisterGUI(MainFrameGUI inputMainFrame) {
        setLayout(new BorderLayout(10, 10)); // Sử dụng BorderLayout cho bố cục chính

        // Title Panel
        JLabel screenName = new JLabel("REGISTER", JLabel.CENTER);
        screenName.setFont(new Font("SANS_SERIF", Font.BOLD, 24));
        screenName.setForeground(Color.BLUE);
        add(screenName, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding giữa các thành phần

        // Add components to the form panel
        usernameField = new JTextField();
        addLabelAndField("Username:", usernameField, formPanel, gbc, 0);

        fullNameField = new JTextField();
        addLabelAndField("Full Name:", fullNameField, formPanel, gbc, 1);

        addressField = new JTextField();
        addLabelAndField("Address:", addressField, formPanel, gbc, 2);

        // Gender selection
        maleOption = new JRadioButton("Male");
        femaleOption = new JRadioButton("Female");
        otherOption = new JRadioButton("Other");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleOption);
        genderGroup.add(femaleOption);
        genderGroup.add(otherOption);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(maleOption);
        genderPanel.add(femaleOption);
        genderPanel.add(otherOption);

        addLabelAndComponent("Gender:", genderPanel, formPanel, gbc, 3);

        // Date of Birth
        dobField = new JTextField("dd/mm/yyyy");
        dobField.setForeground(Color.GRAY);
        dobField.addFocusListener(new PlaceholderFocusListener(dobField, "dd/mm/yyyy"));
        addLabelAndField("Date of Birth:", dobField, formPanel, gbc, 4);

        emailField = new JTextField();
        addLabelAndField("Email:", emailField, formPanel, gbc, 5);

        passwordField = new JPasswordField();
        addLabelAndField("Password:", passwordField, formPanel, gbc, 6);

        confirmPasswordField = new JPasswordField();
        addLabelAndField("Confirm Password:", confirmPasswordField, formPanel, gbc, 7);

        // Admin Checkbox
        adminCodeInput = new JTextField();
        addLabelAndComponent("Admin code:", adminCodeInput, formPanel, gbc, 8);

        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        registerBtn = new JButton("Register");
        goBackToLoginBtn = new JButton("Go Back");
        buttonPanel.add(registerBtn);
        buttonPanel.add(goBackToLoginBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        new RegisterHandler(this, inputMainFrame);

        // Add properties for JFrame window
        setSize(700, 550);
    }

    private void addLabelAndField(String label, JTextField field, JPanel panel, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void addLabelAndComponent(String label, JComponent component, JPanel panel, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(component, gbc);
    }

    // Getter methods for form fields
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getFullNameField() {
        return fullNameField;
    }

    public JTextField getAddressField() {
        return addressField;
    }

    public JRadioButton getMaleOption() {
        return maleOption;
    }

    public JRadioButton getFemaleOption() {
        return femaleOption;
    }

    public JRadioButton getOtherOption() {
        return otherOption;
    }

    public JTextField getDobField() {
        return dobField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JTextField getAdminCodeField() {
        return adminCodeInput;
    }

    public JButton getRegisterBtn() {
        return registerBtn;
    }

    public JButton getGoBackToLoginBtn() {
        return goBackToLoginBtn;
    }

    // FocusListener for placeholder functionality
    private static class PlaceholderFocusListener implements java.awt.event.FocusListener {
        private final JTextField field;
        private final String placeholder;

        public PlaceholderFocusListener(JTextField field, String placeholder) {
            this.field = field;
            this.placeholder = placeholder;
        }

        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            if (field.getText().equals(placeholder)) {
                field.setText("");
                field.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            if (field.getText().isEmpty()) {
                field.setText(placeholder);
                field.setForeground(Color.GRAY);
            }
        }
    }
}
