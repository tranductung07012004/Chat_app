package GUI.ChatPanelGUI;

import Handler.ChatPanelHandler.MessageInputHandler;
import java.awt.*;
import javax.swing.*;

import GUI.MainFrameGUI;

public class MessageInputFrame extends JPanel {
    private JTextField messageField;
    private ChatPanelFrame chatPanel;
    private MessageInputHandler handler;

    public MessageInputFrame(MainFrameGUI mainFrame, ChatPanelFrame chatPanel) {
        this.chatPanel = chatPanel;  // Save reference to ChatPanelFrame

        // Initialize the messageField before passing it to the handler
        messageField = new JTextField();

        // Pass both chatPanel and messageField to the handler
        this.handler = new MessageInputHandler(chatPanel, messageField); // Initialize the handler

        setLayout(new BorderLayout());
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(e -> handler.sendMessage(messageField.getText().trim()));

        add(messageField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);
    }
}
