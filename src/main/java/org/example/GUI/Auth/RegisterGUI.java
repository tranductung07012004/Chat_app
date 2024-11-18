package org.example.GUI.Auth;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AuthHandler.RegisterHandler;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JPanel {
    private final JButton registerBtn;
    private final JButton goBackToLoginBtn;

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
        addLabelAndField("Username:", new JTextField(), formPanel, gbc, 0);
        addLabelAndField("Full Name:", new JTextField(), formPanel, gbc, 1);
        addLabelAndField("Address:", new JTextField(), formPanel, gbc, 2);

        // Gender selection
        JLabel genderLabel = new JLabel("Gender:");
        JRadioButton maleOption = new JRadioButton("Male");
        JRadioButton femaleOption = new JRadioButton("Female");
        JRadioButton otherOption = new JRadioButton("Other");
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
        JTextField DOB = new JTextField("dd/mm/yyyy");
        DOB.setForeground(Color.GRAY);
        DOB.addFocusListener(new PlaceholderFocusListener(DOB, "dd/mm/yyyy"));
        addLabelAndField("Date of Birth:", DOB, formPanel, gbc, 4);

        addLabelAndField("Email:", new JTextField(), formPanel, gbc, 5);
        addLabelAndField("Password:", new JPasswordField(), formPanel, gbc, 6);
        addLabelAndField("Confirm Password:", new JPasswordField(), formPanel, gbc, 7);

        // Admin Checkbox
        JTextField adminCodeInput = new JTextField();
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