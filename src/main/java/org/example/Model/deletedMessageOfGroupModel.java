package org.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class deletedMessageOfGroupModel {
    private int group_id;
    private long user_id;

    public deletedMessageOfGroupModel(int initGroup_id, long initUser_id) {
        this.group_id = initGroup_id;
        this.user_id = initUser_id;
    }

    public boolean DeleteGroupMessage(int userId, long message_group_id) {
        String query = "INSERT INTO DELETED_MESSAGE_OF_GROUP (user_id, message_group_id) VALUES (?, ?)";

        try (Connection conn = DBConn.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                // Set the parameters for the query
                preparedStatement.setInt(1, userId);
                preparedStatement.setLong(2, message_group_id);

                // Execute the insert query and check if the row is inserted
                int rowsAffected = preparedStatement.executeUpdate();

                // If rows are affected, return true
                if (rowsAffected > 0) {
                    return true;  // Successfully inserted
                } else {
                    return false;  // Insertion failed
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print the exception
            return false;  // In case of an error, return false
        }
    }
    public static boolean deleteAlltGroupChatHistory(int currentUserId, int groupId) {
        // Step 1: Retrieve all messages for the specified groupId
        String selectQuery = "SELECT message_group_id FROM message_of_group WHERE to_group = ? AND isDeletedAll = false";

        // Step 2: Insert each message into the deleted_message_of_group table and update message status
        String insertDeleteQuery = "INSERT INTO deleted_message_of_group (user_id, message_group_id) VALUES (?, ?)";
        String updateQuery = "UPDATE message_of_group SET isDeletedAll = true WHERE message_group_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertDeleteQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            // Set the groupId parameter
            selectStmt.setInt(1, groupId);
            ResultSet rs = selectStmt.executeQuery();

            // Process each message in the group
            while (rs.next()) {
                long messageGroupId = rs.getLong("message_group_id");

                // Insert into deleted_message_of_group table
                insertStmt.setInt(1, currentUserId);
                insertStmt.setLong(2, messageGroupId);
                insertStmt.addBatch();

                // Update message status to deleted
                updateStmt.setLong(1, messageGroupId);
                updateStmt.addBatch();
            }

            // Execute batch updates
            int[] insertResults = insertStmt.executeBatch();
            int[] updateResults = updateStmt.executeBatch();

            // Check if all operations were successful
            boolean allInsertionsSuccess = true;
            for (int result : insertResults) {
                if (result == PreparedStatement.EXECUTE_FAILED) {
                    allInsertionsSuccess = false;
                    break;
                }
            }

            boolean allUpdatesSuccess = true;
            for (int result : updateResults) {
                if (result == PreparedStatement.EXECUTE_FAILED) {
                    allUpdatesSuccess = false;
                    break;
                }
            }

            return allInsertionsSuccess && allUpdatesSuccess;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
