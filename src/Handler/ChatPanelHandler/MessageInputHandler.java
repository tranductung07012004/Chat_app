package Handler.ChatPanelHandler;

import GUI.ChatPanelGUI.ChatPanelFrame;
import javax.swing.JTextField;

public class MessageInputHandler {

    private ChatPanelFrame chatPanel;
    private JTextField messageField;

    public MessageInputHandler(ChatPanelFrame chatPanel, JTextField messageField) {
        this.chatPanel = chatPanel;
        this.messageField = messageField;  // Save reference to message field
    }

    public void sendMessage(String message) {
        if (!message.isEmpty()) {
            chatPanel.appendMessage("You: " + message);  // Append the message to the chat panel
            messageField.setText("");  // Reset the input text field after sending
        }
    }
}
