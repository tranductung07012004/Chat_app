package GUI;

import javax.swing.*;

public class MainFrameGUI extends JFrame {
    private JPanel curPanel;

    public MainFrameGUI() {
        setTitle("Main Frame");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create the first panel when popped up
        showLoginPanel();

    }

    public void showLoginPanel() {
        if (curPanel != null) {
            remove(curPanel);
        }

        curPanel = new LoginGUI(this);
        add(curPanel);
        revalidate();
        repaint();
    }

    public void showRegisterPanel() {

        if (curPanel != null) {
            remove(curPanel);
        }

        curPanel = new RegisterGUI(this);
        add(curPanel);
        revalidate();
        repaint();
    }

    public void showVerifyAdminGUIPanel() {

        if (curPanel != null) {
            remove(curPanel);
        }

        curPanel = new verifyAdminGUI(this);
        add(curPanel);
        revalidate();
        repaint();
    }

    public void showAdminPanel() {

        if (curPanel != null) {
            remove(curPanel);
        }

        curPanel = new AdminOverallGUI(this);
        add(curPanel);
        revalidate();
        repaint();
    }

}
