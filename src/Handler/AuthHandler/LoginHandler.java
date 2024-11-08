package Handler.AuthHandler;

import GUI.Auth.*;
import GUI.MainFrameGUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginHandler implements ActionListener {
    private final LoginGUI loginScreen;
    private final MainFrameGUI mainFrame;

    public LoginHandler(LoginGUI inputLoginScreen, MainFrameGUI inputFrame) {
        this.loginScreen = inputLoginScreen;
        this.mainFrame = inputFrame;

        loginScreen.getAdminBtn().addActionListener(this);
        loginScreen.getRegisterBtn().addActionListener(this);
        //registerScreen.get
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginScreen.getAdminBtn()) {
            mainFrame.showVerifyAdminGUIPanel();
        }
        else if (e.getSource() == loginScreen.getRegisterBtn()) {
            mainFrame.showRegisterPanel();
        }
    }

}
