package org.example.Handler.ChatPanelHandler;

import org.example.Model.messageOfUserModel;

import java.util.ArrayList;
import java.util.List;

public class ChatPanelHandler {

    // Fetch the chat history between two users
    public static List<messageOfUserModel> loadChatHistory(int currentUserId, int targetUserId) {
        return messageOfUserModel.loadChatHistory(currentUserId, targetUserId);
    }

    // Search messages containing a specific query
    public static List<messageOfUserModel> searchMessages(List<messageOfUserModel> chatHistory, String query) {
        List<messageOfUserModel> result = new ArrayList<>();
        for (messageOfUserModel message : chatHistory) {
            if (message.getChatContent().toLowerCase().contains(query.toLowerCase())) {
                result.add(message);
            }
        }
        return result;
    }

//    // Delete a message by ID
//    public static void deleteMessageById(long messageId) {
//        messageOfUserModel.deleteMessage(messageId);
//    }
}
