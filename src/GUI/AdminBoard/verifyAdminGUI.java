package GUI.AdminBoard;

import GUI.MainFrameGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class verifyAdminGUI extends JPanel {
    public verifyAdminGUI(MainFrameGUI mainFrame) {
        JLabel adminCodeLabel = new JLabel("Admin code:");
        adminCodeLabel.setBounds(150, 200, 100, 40);

        JPasswordField adminCode = new JPasswordField();
        adminCode.setBounds(250, 200, 150, 30);

        JButton adminVerification = new JButton("OK!");
        adminVerification.setBounds(280, 250, 100, 40);

        JButton goBackToLogin = new JButton("Go back");
        goBackToLogin.setBounds(5, 5, 95, 40);

        adminVerification.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.showAdminPanel();
                }
            }

        );

        goBackToLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.showLoginPanel();
                }
            }

        );

        add(adminCodeLabel);
        add(adminCode);
        add(adminVerification);
        add(goBackToLogin);

        setSize(700, 500);
        setLayout(null);
    }
}
