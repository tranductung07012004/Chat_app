package Handler.AuthHandler;

import GUI.Auth.*;
import GUI.MainFrameGUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterHandler implements ActionListener {
    private final RegisterGUI registerScreen;
    private final MainFrameGUI mainFrame;

    public RegisterHandler(RegisterGUI inputRegisterScreen, MainFrameGUI inputMainFrame) {
        this.registerScreen = inputRegisterScreen;
        this.mainFrame = inputMainFrame;

        registerScreen.getRegisterBtn().addActionListener(this);
        registerScreen.getGoBackToLoginBtn().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerScreen.getRegisterBtn()) {
            mainFrame.showLoginPanel();
        }
        else if (e.getSource() == registerScreen.getGoBackToLoginBtn()) {
            mainFrame.showLoginPanel();
        }
    }
}
