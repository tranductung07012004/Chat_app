package org.example.GUI;

import org.example.GUI.AdminBoard.AdminFunctions;
import org.example.GUI.AdminBoard.AdminScreenButton;
import org.example.GUI.AdminBoard.verifyAdminGUI;
import org.example.GUI.Auth.LoginGUI;
import org.example.GUI.Auth.RegisterGUI;
import org.example.GUI.ChatPanelGUI.ChatPanelFrame;
import org.example.GUI.UserFriendRequest.FriendRequestFrame;
import org.example.GUI.UserSettingGUI.SettingsPanel;

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
    public void showChatPanel() {
        removePanel(); // Remove the previous panel

        // Create and add the new chat panel
        ChatPanelFrame chatPanelFrame = new ChatPanelFrame(this);
        JPanel chatPanel = chatPanelFrame.createChatPanel(); // Get chat panel from ChatPanelFrame
        add(chatPanel);

        revalidate();
        repaint();
    }

    public void logOut() {
        removePanel(); // Remove the previous panel

        // Log out and show the login panel again
        JPanel loginPanel = new LoginGUI(this);
        add(loginPanel);

        revalidate();
        repaint();
    }

    public void showSettingsPanel() {
        removePanel(); // Remove the previous panel

        // Create and add the new settings panel
        JPanel settingsPanel = new SettingsPanel(this);
        add(settingsPanel);

        revalidate();
        repaint();
    }

    public void showFriendRequestFrame() {
        removePanel(); // Remove the previous panel

        // Create and add the new friend request frame
        JPanel friendRequestPanel = new FriendRequestFrame(this);
        add(friendRequestPanel);

        revalidate();
        repaint();
    }


}
