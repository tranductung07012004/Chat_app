package org.example.GUI;

import org.example.GUI.AdminBoard.AdminFunctions;
import org.example.GUI.AdminBoard.AdminScreenButton;
import org.example.GUI.AdminBoard.verifyAdminGUI;
import org.example.GUI.Auth.LoginGUI;
import org.example.GUI.Auth.RegisterGUI;
import org.example.GUI.ChatPanelGUI.ChatPanelFrame;
import org.example.GUI.ChatPanelGUI.SidebarFrame;
import org.example.GUI.UserFriendRequest.FriendRequestFrame;
import org.example.GUI.UserSettingGUI.SettingsPanel;
import org.example.Model.endUserModel;
import org.example.Model.userFriendModel;
import org.example.Server.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MainFrameGUI extends JFrame {
    private ChatClient chatClient;  // Instance of ChatClient to send/receive messages
    private int currentUserId = -1;
    private ChatPanelFrame chatPanelFrame = new ChatPanelFrame(this);

    public MainFrameGUI() {
        setTitle("Main Frame");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        // Initialize ChatClient with both message and delete handlers
        chatClient = new ChatClient(
                "localhost",
                12343,
                new ChatClient.MessageListener() {
                    @Override
                    public void onMessageReceived(String message) {
                        handleIncomingMessage(message);
                    }

                    @Override
                    public void onDeleteMessage(String deleteMessage) {
                        handleIncomingMessage(deleteMessage);
                    }

                    @Override
                    public void onLoginMessage(String login) {
                        handleLoginMessage(login);
                    }
                }
        );


        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.println("Window is closing...");

                // Set user as offline when the window is closed
                endUserModel user = endUserModel.getUserFromId(currentUserId);
                if (user != null)
                    user.setOnline(false);  // Set user as offline
                getChatClient().notifyLogin(getCurrentUserId());

                // Optional cleanup tasks
                logOut(); // Optional, you can call logOut() if you want

                // Exit the application
                System.exit(0);
            }
        });

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

        JPanel chatPanel = chatPanelFrame.createChatPanel(); // Get chat panel from ChatPanelFrame
        add(chatPanel);

        revalidate();
        repaint();
    }

    public void logOut() {
        removePanel(); // Remove the previous panel
        currentUserId=-1;
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

    // Setter for currentUserId
    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
    }

    // Getter for currentUserId
    public int getCurrentUserId() {
        return currentUserId;
    }

    public ChatPanelFrame getChatPanelFrame() {
        return chatPanelFrame;
    }

    private void handleIncomingMessage(String message) {
        ChatPanelFrame activeChatPanel = getChatPanelFrame();
        if (activeChatPanel != null) {
            if (message.startsWith("DELETE|")) {
                activeChatPanel.handleDeleteMessage(message);
            } else {
                activeChatPanel.handleIncomingMessage(message);
            }
        }
    }

    private void handleLoginMessage(String message) {
        ChatPanelFrame activeChatPanel = getChatPanelFrame();
        if (activeChatPanel != null) {
            if (message.startsWith("LOGIN|")) {
                activeChatPanel.handleInputOutputMessage(message);
            }
        }
    }

    public ChatClient getChatClient() {
        return chatClient;
    }
}
