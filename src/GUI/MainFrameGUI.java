package GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrameGUI extends JFrame {
    private JPanel curPanel;

    public MainFrameGUI() {
        setTitle("Main Frame");
        setSize(700, 550); // Adjusted for a larger layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Initial panel
        showLoginPanel();
    }

    public void showLoginPanel() {
        if (curPanel != null) {
            remove(curPanel);
        }
        curPanel = new LoginGUI(this); // Display Login Panel
        add(curPanel);
        revalidate();
        repaint();
    }

    public void showRegisterPanel() {
        if (curPanel != null) {
            remove(curPanel);
        }
        curPanel = new RegisterGUI(this); // Display Register Panel
        add(curPanel);
        revalidate();
        repaint();
    }

    public void showChatPanel() {
        if (curPanel != null) {
            remove(curPanel);
        }

        // Use ChatPanelFrame to create the chat panel
        ChatPanelFrame ChatPanelFrame = new ChatPanelFrame(this);
        curPanel = ChatPanelFrame.createChatPanel(); // Get chat panel from ChatPanelFrame

        add(curPanel);
        revalidate();
        repaint();
    }
    public void showSettingsPanel() {
        if (curPanel != null) {
            remove(curPanel);
        }
        curPanel = new SettingsPanel(this); // Display Settings Panel
        add(curPanel);
        revalidate();
        repaint();
    }

}
