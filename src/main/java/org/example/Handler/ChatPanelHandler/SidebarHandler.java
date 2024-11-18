package org.example.Handler.ChatPanelHandler;

import org.example.GUI.ChatPanelGUI.SidebarFrame;
import org.example.GUI.MainFrameGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SidebarHandler {
    private SidebarFrame sidebarFrame;
    private MainFrameGUI mainFrame;

    public SidebarHandler(SidebarFrame sidebarFrame, MainFrameGUI mainFrame) {
        this.sidebarFrame = sidebarFrame;
        this.mainFrame = mainFrame;
        initializeHandlers();
    }

    private void initializeHandlers() {
        // Add action listener for the dropdown options
        JComboBox<String> optionsComboBox = sidebarFrame.getOptionsComboBox();
        optionsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleOptionSelection((String) optionsComboBox.getSelectedItem());
            }
        });

       
    }

    private void handleOptionSelection(String selectedOption) {
        if (null != selectedOption) switch (selectedOption) {
            case "Settings":
                mainFrame.showSettingsPanel(); // Show Settings Panel when selected
                break;
            case "Chat":
                mainFrame.showChatPanel(); // Show Chat Panel when selected
                break;
            case "Friend Request":
                mainFrame.showFriendRequestFrame(); // Show FriendRequest Frame when selected
                break;
            default:
                break;
        }
    }
}
