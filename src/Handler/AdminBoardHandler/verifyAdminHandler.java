package Handler.AdminBoardHandler;

import GUI.AdminBoard.verifyAdminGUI;
import GUI.MainFrameGUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class verifyAdminHandler implements ActionListener {
    private final verifyAdminGUI verifyAdminScreen;
    private final MainFrameGUI mainFrame;

    public verifyAdminHandler(verifyAdminGUI inputVerifyAdminScreen, MainFrameGUI inputMainFrame) {
        this.mainFrame = inputMainFrame;
        this.verifyAdminScreen = inputVerifyAdminScreen;

        verifyAdminScreen.getGoBackToLoginBtn().addActionListener(this);
        verifyAdminScreen.getAdminVerificationBtn().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == verifyAdminScreen.getGoBackToLoginBtn()) {
            mainFrame.showLoginPanel();
        }
        else if (e.getSource() == verifyAdminScreen.getAdminVerificationBtn()) {
            mainFrame.showAdminPanel();
        }
    }
}
