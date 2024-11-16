package GUI.Auth;

import GUI.MainFrameGUI;
import Handler.AuthHandler.LoginHandler;

import javax.swing.*;
import java.awt.*;


public class LoginGUI extends JPanel {
    private final JTextField accountName;
    private final JPasswordField passField;
    private final JButton loginBtn;
    private final JButton registerBtn;
    private final JButton adminBtn;
    //private MainFrameGUI mainFrame;


    public LoginGUI(MainFrameGUI inputMainFrame) {

        //this.mainFrame = inputMainFrame;
        // Define the label for login screen
        JLabel screenName = new JLabel("LOGIN");
        screenName.setBounds(315, 80, 95, 50);
        screenName.setFont(new Font("SANS_SERIF", Font.BOLD, 24));
        screenName.setForeground(Color.BLUE);


        // Define the account name input
        accountName = new JTextField();
        accountName.setBounds(200, 150, 300, 30);
        JLabel accLabel = new JLabel("Username:");
        accLabel.setBounds(130, 150, 100, 30);

        // Define the password field
        passField = new JPasswordField();
        passField.setBounds(200, 200, 300, 30);
        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(130, 200, 100, 30);

        // Define the properties for login button
        loginBtn = new JButton("OK!");
        loginBtn.setBounds(247, 300, 95, 40);

        // Define the properties for register button
        registerBtn = new JButton("Register ?");
        registerBtn.setBounds(347, 300, 95, 40);

        // Define the properties for admin button
        adminBtn = new JButton("Administrator ?");
        adminBtn.setBounds(247, 350, 95 * 2 + 5, 40);


        // Add the components above to the JFrame window
        add(registerBtn);
        add(loginBtn);
        add(passLabel);
        add(passField);
        add(accountName);
        add(accLabel);
        add(screenName);
        add(adminBtn);

        new LoginHandler(this, inputMainFrame);

        setSize(700, 500);
        setLayout(null);
    }

    public JButton getLoginBtn() { return loginBtn; }
    public JButton getRegisterBtn() { return registerBtn; }
    public JButton getAdminBtn() { return adminBtn; }
    public JTextField getAccountName() { return accountName; }
    public JPasswordField getPassField() { return passField; }
}





