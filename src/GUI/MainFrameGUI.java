package GUI;

import GUI.AdminBoard.AdminFunctions;
import GUI.AdminBoard.AdminScreenButton;
import GUI.AdminBoard.verifyAdminGUI;
import GUI.Auth.LoginGUI;
import GUI.Auth.RegisterGUI;

import javax.swing.*;
import java.awt.*;

public class MainFrameGUI extends JFrame {
    public MainFrameGUI() {
        setTitle("Main Frame");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);


        showLoginPanel();

    }

    public void removePanel() {
        Container container = getContentPane();
        for (Component component : container.getComponents()) {
            if (component instanceof JPanel) {
                container.remove(component);
            }
        }
    }

    public void showLoginPanel() {

        removePanel();
        setSize(700, 550);
        add(new LoginGUI(this));

        revalidate();
        repaint();
    }

    public void showRegisterPanel() {

        removePanel();
        setSize(700, 550);
        add(new RegisterGUI(this));

        revalidate();
        repaint();
    }

    public void showVerifyAdminGUIPanel() {

        removePanel();
        setSize(700, 550);
        add(new verifyAdminGUI(this));

        revalidate();
        repaint();
    }

    public void showAdminPanel() {

        removePanel();
        setSize(1200, 600);

        setLayout(new BorderLayout());

        AdminFunctions adminFunctions = new AdminFunctions(this);

        AdminScreenButton adminScreenButton = new AdminScreenButton(this, adminFunctions.getCardLayout(), adminFunctions);


        add(adminScreenButton, BorderLayout.WEST);
        add(adminFunctions, BorderLayout.CENTER);

        revalidate();
        repaint();

    }

}
