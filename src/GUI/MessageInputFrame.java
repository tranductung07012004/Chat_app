package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageInputFrame extends JPanel {
    private JTextField messageField;
    private ChatPanelFrame chatPanel;

    public MessageInputFrame(MainFrameGUI mainFrame, ChatPanelFrame chatPanel) {
        this.chatPanel = chatPanel;  // Save reference to ChatPanelFrame

        setLayout(new BorderLayout());
        messageField = new JTextField();
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();  // Call sendMessage on button press
            }
        });

        add(messageField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            messageField.setText("");
            chatPanel.appendMessage("You: " + message);  // Use chatPanel instance to append message
        }
    }
}
