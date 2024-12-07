package org.example.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class messageOfUserModel {
    private long message_user_id;
    private int from_user;
    private int to_user;
    private Timestamp chat_time;
    private String chat_content;
    private boolean isDeleted;
    private boolean isDeletedAll;

    public messageOfUserModel(long message_user_id, int from_user, int to_user, Timestamp chat_time, String chat_content, boolean isDeleted, boolean isDeletedAll) {
        this.message_user_id = message_user_id;
        this.from_user = from_user;
        this.to_user = to_user;
        this.chat_time = chat_time;
        this.chat_content = chat_content;
        this.isDeleted = isDeleted;
        this.isDeletedAll = isDeletedAll;
    }

    // Getters (if needed)
    public long getMessageUserId() {
        return message_user_id;
    }

    public int getFromUser() {
        return from_user;
    }

    public int getToUser() {
        return to_user;
    }

    public Timestamp getChatTime() {
        return chat_time;
    }

    public String getChatContent() {
        return chat_content;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public boolean getIsDeletedAll() {
        return isDeletedAll;
    }

    // Fetch chat history from database
    public static List<messageOfUserModel> loadChatHistory(int currentUserId, int targetUserId) {
        List<messageOfUserModel> chatHistory = new ArrayList<>();
        String query = "SELECT * FROM MESSAGE_OF_USER " +
                "WHERE ((from_user = ? AND to_user = ?) " +
                "OR (from_user = ? AND to_user = ?)) " +
                "AND isDeleted = false " +
                "AND isDeletedAll = false " +
                "ORDER BY chat_time ASC";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, currentUserId);
            stmt.setInt(2, targetUserId);
            stmt.setInt(3, targetUserId);
            stmt.setInt(4, currentUserId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messageOfUserModel message = new messageOfUserModel(
                        rs.getLong("message_user_id"),
                        rs.getInt("from_user"),
                        rs.getInt("to_user"),
                        rs.getTimestamp("chat_time"),
                        rs.getString("chat_content"),
                        rs.getBoolean("isDeleted"),
                        rs.getBoolean("isDeletedAll")
                );
                chatHistory.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
        }

        return chatHistory;
    }
}
