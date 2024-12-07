package org.example.Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class messageOfGroupModel {
    private long message_group_id;
    private int from_user;
    private int to_group;
    private Timestamp chat_time;
    private String chat_content;
    private boolean isDeleted;
    private boolean isDeletedAll;

    public messageOfGroupModel(long initMessage_group_id,
                               int initFrom_user,
                               int initTo_group,
                               Timestamp initChat_time,
                               String initChat_content,
                               boolean initIsDeleted,
                               boolean initIsDeletedAll) {

        this.message_group_id = initMessage_group_id;
        this.from_user = initFrom_user;
        this.to_group = initTo_group;
        this.chat_time = initChat_time;
        this.chat_content = initChat_content;
        this.isDeleted = initIsDeleted;
        this.isDeletedAll = initIsDeletedAll;
    }
    // Fetch chat history from database
    public static List<messageOfUserModel> loadChatHistory( int groupId) {
        List<messageOfUserModel> chatHistory = new ArrayList<>();
        String query = "SELECT * FROM MESSAGE_OF_GROUP  " +
                "WHERE ( to_group = ?) " +
                "AND isDeleted = false " +
                "AND isDeletedAll = false " +
                "ORDER BY chat_time ASC";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, groupId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messageOfUserModel message = new messageOfUserModel(
                        rs.getLong("message_group_id"),
                        rs.getInt("from_user"),
                        rs.getInt("to_group"),
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
