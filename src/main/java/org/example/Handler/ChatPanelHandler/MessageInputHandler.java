package org.example.Handler.ChatPanelHandler;

import org.example.GUI.ChatPanelGUI.ChatPanelFrame;
import javax.swing.JTextField;

public class MessageInputHandler {

    private ChatPanelFrame chatPanel;
    private JTextField messageField;

    public MessageInputHandler(ChatPanelFrame chatPanel, JTextField messageField) {
        this.chatPanel = chatPanel;
        this.messageField = messageField;  // Save reference to message field
    }

    public void sendMessage(String message) {

    }
}
