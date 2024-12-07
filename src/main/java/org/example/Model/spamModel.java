package org.example.Model;
import javax.swing.*;
import java.sql.*;

public class spamModel {
    private long spam_id;
    private int target_user;
    private Timestamp time_created;

    public spamModel(long initSpam_id, int initTarget_user, Timestamp initTime_created) {
        this.spam_id = initSpam_id;
        this.target_user = initTarget_user;
        this.time_created = initTime_created;
    }
    public static boolean saveSpamReport(int currentUserId, int target_user) {
        String query = "INSERT INTO spam (target_user, time_created) VALUES (?, ?)";

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the parameters for the query
            stmt.setInt(1, target_user);          // Target user being reported
            stmt.setTimestamp(2, currentTime);   // Current timestamp


            // Execute the update query
            int rowsAffected = stmt.executeUpdate();

            // Return true if the insert was successful
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
