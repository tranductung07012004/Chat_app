package GUI.Auth;

<<<<<<< HEAD
import GUI.MainFrameGUI;
import Handler.AuthHandler.RegisterHandler;

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
=======
import javax.swing.*;

import GUI.MainFrameGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RegisterGUI extends JPanel {
    public RegisterGUI(MainFrameGUI mainFrame) {

        // Define the label for login screen
        JLabel screenName = new JLabel("REGISTER");
        screenName.setBounds(280, 30, 200, 50);
        screenName.setFont(new Font("SANS_SERIF", Font.BOLD, 24));
        screenName.setForeground(Color.BLUE);


        // Define the account name input
        JTextField accountName = new JTextField();
        accountName.setBounds(210, 80, 300, 30);
        JLabel accLabel = new JLabel("Username:");
        accLabel.setBounds(130, 80, 100, 30);

        // Define the full name input
        JTextField fullName = new JTextField();
        fullName.setBounds(210, 120, 300, 30);
        JLabel fullNameLabel = new JLabel("Full name:");
        fullNameLabel.setBounds(130, 120, 100, 30);

        // Define the address input
        JTextField address = new JTextField();
        address.setBounds(210, 160, 300, 30);
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(130, 160, 100, 30);

        // Define the gender selection

        JLabel label = new JLabel("Gender:");
        label.setBounds(130, 200, 100, 30);

        JRadioButton maleOption = new JRadioButton("Male");
        maleOption.setBounds(200, 205, 100, 20);
        JRadioButton femaleOption = new JRadioButton("Female");
        femaleOption.setBounds(310, 205, 100, 20);
        JRadioButton otherOption = new JRadioButton("Other");
        otherOption.setBounds(410, 205, 100, 20);

>>>>>>> 0c01b7724ff277ffcb914d347bb213f9420d1b0b
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleOption);
        genderGroup.add(femaleOption);
        genderGroup.add(otherOption);

<<<<<<< HEAD
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
=======
        // Define the date of birth input
        JTextField DOB = new JTextField("dd/mm/yyyy");
        DOB.setBounds(230, 245, 100, 30);

        JLabel dobLabel = new JLabel("Date Of Birth:");
        dobLabel.setBounds(130, 230, 100, 60);

        // Đặt màu chữ làm mờ cho placeholder
        DOB.setForeground(Color.GRAY);

        // Thêm FocusListener để xử lý khi focus vào và rời khỏi JTextField
        DOB.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Xóa placeholder khi người dùng bắt đầu gõ
                if (DOB.getText().equals("dd/mm/yyyy")) {
                    DOB.setText("");
                    DOB.setForeground(Color.BLACK); // Đổi màu chữ lại bình thường
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Hiển thị lại placeholder khi người dùng chưa nhập gì
                if (DOB.getText().isEmpty()) {
                    DOB.setText("dd/mm/yyyy");
                    DOB.setForeground(Color.GRAY); // Đặt lại màu chữ làm mờ
                }
            }
        });


        // Define the email input field

        JTextField email = new JTextField();
        email.setBounds(210, 285, 300, 30);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(130, 285, 100, 30);


        // Define the password field
        JPasswordField passField = new JPasswordField();
        passField.setBounds(210, 325, 300, 30);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(130, 325, 100, 30);

        // Define the confirm-password field
        JPasswordField confirmPassField = new JPasswordField();
        confirmPassField.setBounds(210, 365, 300, 30);
        JLabel confirmPassLabel = new JLabel("Confirm pass:");
        confirmPassLabel.setBounds(130, 365, 100, 30);


        // Define the properties for login button
        JButton registerBtn = new JButton("OK!");
        registerBtn.setBounds(302, 435, 95, 40);

        registerBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.showLoginPanel();
                }
            }
        );


        // Add to the JFrame
        add(screenName);
        add(accountName);
        add(accLabel);
        add(passField);
        add(passLabel);
        add(registerBtn);
        add(fullName);
        add(fullNameLabel);
        add(address);
        add(addressLabel);
        add(label);
        add(maleOption);
        add(femaleOption);
        add(otherOption);
        add(DOB);
        add(dobLabel);
        add(email);
        add(emailLabel);
        add(confirmPassField);
        add(confirmPassLabel);

        // Add properties for JFrame window
        setSize(700, 550);
        setLayout(null);
        setVisible(true);
>>>>>>> 0c01b7724ff277ffcb914d347bb213f9420d1b0b
    }
}
