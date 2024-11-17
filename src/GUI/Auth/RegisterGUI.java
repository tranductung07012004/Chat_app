package GUI.Auth;

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

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleOption);
        genderGroup.add(femaleOption);
        genderGroup.add(otherOption);

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
    }
}
