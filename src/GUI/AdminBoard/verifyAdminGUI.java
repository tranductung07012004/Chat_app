package GUI.AdminBoard;

import GUI.MainFrameGUI;
import Handler.AdminBoardHandler.verifyAdminHandler;
import javax.swing.*;

public class verifyAdminGUI extends JPanel {
    private JButton goBackToLogin;
    private JButton adminVerification;
    private MainFrameGUI mainFrame;

    public verifyAdminGUI(MainFrameGUI inputMainFrame) {

        this.mainFrame = inputMainFrame;

        JLabel adminCodeLabel = new JLabel("Admin code:");
        adminCodeLabel.setBounds(150, 200, 100, 40);

        JPasswordField adminCode = new JPasswordField();
        adminCode.setBounds(250, 200, 150, 30);

        adminVerification = new JButton("OK!");
        adminVerification.setBounds(280, 250, 100, 40);

        goBackToLogin = new JButton("Go back");
        goBackToLogin.setBounds(5, 5, 95, 40);



        new verifyAdminHandler(this, mainFrame);
        add(adminCodeLabel);
        add(adminCode);
        add(adminVerification);
        add(goBackToLogin);

        setSize(700, 500);
        setLayout(null);
    }

    public JButton getGoBackToLoginBtn() { return goBackToLogin; }
    public JButton getAdminVerificationBtn() { return adminVerification; }
}
