package org.example.Model;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class messageOfUserModel {
    private long message_user_id;
    private int from_user;
    private int to_user;
    private Timestamp chat_time;
    private String chat_content;
    private boolean delete_by_sender;
    private boolean delete_by_receiver;

    public messageOfUserModel(long message_user_id, int from_user, int to_user, Timestamp chat_time, String chat_content, boolean delete_by_sender, boolean delete_by_receiver) {
        this.message_user_id = message_user_id;
        this.from_user = from_user;
        this.to_user = to_user;
        this.chat_time = chat_time;
        this.chat_content = chat_content;
        this.delete_by_sender = delete_by_sender;
        this.delete_by_receiver = delete_by_receiver;
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

    public boolean getdelete_by_sender() {
        return delete_by_sender;
    }

    public boolean getdelete_by_receiver() {
        return delete_by_receiver;
    }

    public static boolean deleteChatBothSides(int currentUserId, long messageId) {
        String updateQuery = "UPDATE MESSAGE_OF_USER " +
                "SET delete_by_sender = true, delete_by_receiver = true " +
                "WHERE message_user_id = ? " +
                "  AND (from_user = ? OR to_user = ?)";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            // Set parameters for the query
            stmt.setLong(1, messageId);     // Set message ID to identify the message
            stmt.setInt(2, currentUserId); // Check if the currentUserId matches from_user
            stmt.setInt(3, currentUserId); // Check if the currentUserId matches to_user

            int rowsAffected = stmt.executeUpdate(); // Execute the update query
            return rowsAffected > 0; // Return true if any rows were updated

        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
            return false; // Indicate failure
        }
    }
    public static boolean deleteChat(int currentUserId, long messageId) {
        String updateQuery = "UPDATE MESSAGE_OF_USER " +
                "SET delete_by_sender = CASE WHEN from_user = ? THEN true ELSE delete_by_sender END, " +
                "    delete_by_receiver = CASE WHEN to_user = ? THEN true ELSE delete_by_receiver END " +
                "WHERE message_user_id = ? " +
                "  AND (from_user = ? OR to_user = ?)";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            // Set parameters for the query
            stmt.setInt(1, currentUserId); // Set delete_by_sender if currentUserId is from_user
            stmt.setInt(2, currentUserId); // Set delete_by_receiver if currentUserId is to_user
            stmt.setLong(3, messageId);     // Specify the message by its ID
            stmt.setInt(4, currentUserId); // Check that currentUserId matches from_user
            stmt.setInt(5, currentUserId); // Check that currentUserId matches to_user

            int rowsAffected = stmt.executeUpdate(); // Execute the update query
            return rowsAffected > 0; // Return true if any rows were updated

        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
            return false; // Indicate failure
        }
    }
    public static boolean deleteChatHistory(int currentUserId, int targetUserId) {
        String query = "UPDATE MESSAGE_OF_USER " +
                "SET delete_by_sender = CASE WHEN from_user = ? THEN true ELSE delete_by_sender END, " +
                "    delete_by_receiver = CASE WHEN to_user = ? THEN true ELSE delete_by_receiver END " +
                "WHERE (from_user = ? AND to_user = ?) " +
                "   OR (from_user = ? AND to_user = ?)";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, currentUserId);
            stmt.setInt(2, currentUserId);
            stmt.setInt(3, currentUserId);
            stmt.setInt(4, targetUserId);
            stmt.setInt(5, targetUserId);
            stmt.setInt(6, currentUserId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Thành công nếu có ít nhất một bản ghi được cập nhật

        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi
            return false; // Thất bại do lỗi truy vấn
        }
    }
    public static boolean sendUserMessage(String message, int currentUserID, int targetUserId) {
        if (message == null || message.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Message cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (currentUserID <= 0 || targetUserId <= 0) {
            JOptionPane.showMessageDialog(null, "Invalid user IDs.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        boolean isBlock=blockModel.isBlocked(currentUserID, targetUserId)||blockModel.isBlocked(targetUserId, currentUserID);

        if(isBlock)
        {
            JOptionPane.showMessageDialog(null, "Invalid user", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Assume a method `getConnection()` to fetch the database connection
        try (Connection connection = DBConn.getConnection()) {
            String insertMessageSQL = "INSERT INTO message_of_user (from_user, to_user, chat_time, chat_content, delete_by_sender, delete_by_receiver) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertMessageSQL)) {
                preparedStatement.setInt(1, currentUserID);
                preparedStatement.setInt(2, targetUserId);
                preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // Current timestamp
                preparedStatement.setString(4, message);
                preparedStatement.setBoolean(5, false); // `delete_by_sender`
                preparedStatement.setBoolean(6, false); // `delete_by_receiver`

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Message sent successfully.");
                    return true;
                } else {
                    System.out.println("Failed to send the message.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while sending the message.");
            return false;
        }
    }
    public static int getTotalMessages(int currentUserId, int targetUserId) {
        String query = "SELECT COUNT(*) FROM MESSAGE_OF_USER " +
                "WHERE ((from_user = ? AND to_user = ? AND delete_by_sender = false) " +
                "OR (from_user = ? AND to_user = ? AND delete_by_receiver = false))";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, currentUserId);
            stmt.setInt(2, targetUserId);
            stmt.setInt(3, targetUserId);
            stmt.setInt(4, currentUserId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Return the total count
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static List<messageOfUserModel> loadChatHistory(int currentUserId, int targetUserId, int limit, int offset) {
        List<messageOfUserModel> chatHistory = new ArrayList<>();
        String query = "SELECT * FROM MESSAGE_OF_USER " +
                "WHERE ((from_user = ? AND to_user = ? AND delete_by_sender = false) " +
                "OR (from_user = ? AND to_user = ? AND delete_by_receiver = false)) " +
                "ORDER BY chat_time DESC " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, currentUserId);
            stmt.setInt(2, targetUserId);
            stmt.setInt(3, targetUserId);
            stmt.setInt(4, currentUserId);
            stmt.setInt(5, limit);
            stmt.setInt(6, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messageOfUserModel message = new messageOfUserModel(
                        rs.getLong("message_user_id"),
                        rs.getInt("from_user"),
                        rs.getInt("to_user"),
                        rs.getTimestamp("chat_time"),
                        rs.getString("chat_content"),
                        rs.getBoolean("delete_by_sender"),
                        rs.getBoolean("delete_by_receiver")
                );
                chatHistory.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chatHistory;
    }

}
