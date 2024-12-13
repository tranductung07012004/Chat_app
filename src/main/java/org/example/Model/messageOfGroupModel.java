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

    private boolean isDeletedAll;

    public messageOfGroupModel(long initMessage_group_id,
                               int initFrom_user,
                               int initTo_group,
                               Timestamp initChat_time,
                               String initChat_content,

                               boolean initIsDeletedAll) {

        this.message_group_id = initMessage_group_id;
        this.from_user = initFrom_user;
        this.to_group = initTo_group;
        this.chat_time = initChat_time;
        this.chat_content = initChat_content;

        this.isDeletedAll = initIsDeletedAll;
    }
    // Fetch chat history from database
    public static List<messageOfUserModel> loadChatHistory(int groupId, int limit, int offset, int currentUserId) {
        List<messageOfUserModel> chatHistory = new ArrayList<>();
        String query = "SELECT * FROM MESSAGE_OF_GROUP  " +
                "WHERE to_group = ? " +
                "AND isDeletedAll = false " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 FROM DELETED_MESSAGE_OF_GROUP " +
                "    WHERE DELETED_MESSAGE_OF_GROUP.user_id = ? " +
                "    AND DELETED_MESSAGE_OF_GROUP.message_group_id = MESSAGE_OF_GROUP.message_group_id" +
                ") " +
                "ORDER BY chat_time DESC " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, groupId);      // Set the groupId
            stmt.setInt(2, currentUserId); // Set the currentUserId to exclude deleted messages for the user
            stmt.setInt(3, limit);         // Set the limit for the query
            stmt.setInt(4, offset);        // Set the offset for pagination

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messageOfUserModel message = new messageOfUserModel(
                        rs.getLong("message_group_id"),
                        rs.getInt("from_user"),
                        rs.getInt("to_group"),
                        rs.getTimestamp("chat_time"),
                        rs.getString("chat_content"),
                        rs.getBoolean("isDeletedAll"),
                        rs.getBoolean("isDeletedAll")
                );
                chatHistory.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log exception
        }

        return chatHistory;
    }

    public static int getTotalMessages(int groupId, int currentUserId) {
        String query = "SELECT COUNT(*) FROM message_of_group " +
                "WHERE to_group = ? " +
                "AND isDeletedAll = false " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 FROM DELETED_MESSAGE_OF_GROUP " +
                "    WHERE DELETED_MESSAGE_OF_GROUP.user_id = ? " +
                "    AND DELETED_MESSAGE_OF_GROUP.message_group_id = message_of_group.message_group_id" +
                ")";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, groupId);      // Set the groupId
            stmt.setInt(2, currentUserId); // Set the currentUserId to exclude deleted messages for the user

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Return the total count
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public static boolean addGroupMessage(String message, int currentUserID, int groupId) {
        // Validate inputs
        if (message == null || message.trim().isEmpty()) {
            System.out.println("Message cannot be empty.");
            return false;
        }

        if (currentUserID <= 0 || groupId <= 0) {
            System.out.println("Invalid user ID or group ID.");
            return false;
        }

        // Get the current timestamp for the message
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());


        // Insert message into the database
        try (Connection connection = DBConn.getConnection()) {
            String insertMessageSQL = "INSERT INTO message_of_group (from_user, to_group, chat_time, chat_content, isdeletedall) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertMessageSQL)) {
                preparedStatement.setInt(1,currentUserID);
                preparedStatement.setInt(2, groupId);
                preparedStatement.setTimestamp(3, currentTime);
                preparedStatement.setString(4, message);
                preparedStatement.setBoolean(5, false);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Group message sent successfully.");
                    return true;
                } else {
                    System.out.println("Failed to send the group message.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while sending the group message.");
            return false;
        }
    }
    public static boolean deleteChat(int groupId, int currentUserId, long messageGroupId) {
        // Step 1: Get the message's timestamp and sender to check if it was sent within the last 24 hours
        String query = "SELECT chat_time, from_user FROM message_of_group WHERE message_group_id = ? AND to_group = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the parameters for the query
            stmt.setLong(1, messageGroupId);
            stmt.setInt(2, groupId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp chatTime = rs.getTimestamp("chat_time");
                int fromUser = rs.getInt("from_user");

                // Step 2: Check if the current user is the sender of the message
                if (fromUser != currentUserId) {
                    System.out.println("You can only delete your own messages.");
                    return false;
                }

                // Step 3: Check if the message is less than 24 hours old
                long timeDifference = System.currentTimeMillis() - chatTime.getTime();
                if (timeDifference > 24 * 60 * 60 * 1000) { // 24 hours in milliseconds
                    System.out.println("You can only delete messages sent within the last 24 hours.");
                    return false;
                }

                // Step 4: Mark the message as deleted by setting isDeletedAll to true
                String updateQuery = "UPDATE message_of_group SET isDeletedAll = ? WHERE message_group_id = ? ";

                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setBoolean(1, true); // Set isDeletedAll to true
                    updateStmt.setLong(2, messageGroupId); // Set the message ID


                    int rowsAffected = updateStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Message deleted successfully.");
                        return true;
                    } else {
                        System.out.println("Failed to delete the message.");
                        return false;
                    }
                }
            } else {
                System.out.println("Message not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object[][] searchForMessage(String chatContent, int user_id) {
        String query = " SELECT eu.account_name AS sender, gc.group_name AS receiver, mog.chat_content " +
                " FROM message_of_group mog " +
                " JOIN end_user eu ON mog.from_user = eu.user_id" +
                " JOIN group_chat gc ON mog.to_group = gc.group_id" +
                " WHERE  mog.from_user = ? AND mog.chat_content LIKE ?";

        List<Object[]> resultList = new ArrayList<>();

        // Sử dụng try-with-resources để đảm bảo đóng kết nối
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán tham số cho câu lệnh SQL
            stmt.setInt(1, user_id);
            stmt.setString(2, chatContent + "%");

            // Thực thi truy vấn và xử lý kết quả
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Lấy dữ liệu từ các cột
                    String sender = rs.getString("sender");
                    String receiver = rs.getString("receiver");
                    String chat_content = rs.getString("chat_content");

                    // Thêm dữ liệu vào danh sách
                    resultList.add(new Object[]{sender, "N/A", receiver, chat_content});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu xảy ra lỗi
        }

        // Chuyển danh sách thành mảng 2 chiều
        return resultList.toArray(new Object[0][]);
    }

}
