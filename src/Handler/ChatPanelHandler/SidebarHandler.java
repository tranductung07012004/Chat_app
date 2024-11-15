package Handler.ChatPanelHandler;

import GUI.MainFrameGUI;
import GUI.ChatPanelGUI.SidebarFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        if ("Settings".equals(selectedOption)) {
            mainFrame.showSettingsPanel(); // Show Settings Panel when selected
        } else if ("Chat".equals(selectedOption)) {
            mainFrame.showChatPanel(); // Show Chat Panel when selected
        } else if ("Friend Request".equals(selectedOption)) {
            mainFrame.showFriendRequestFrame(); // Show FriendRequest Frame when selected
        }
    }
}
