package org.example.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class blockModel {
    private int block_id;
    private int block_by_id;
    private int target_user_id;

    public blockModel(int block_id, int block_by_id, int target_user_id) {
        this.block_id = block_id;
        this.block_by_id = block_by_id;
        this.target_user_id = target_user_id;
    }

    // Getters for the block model fields
    public int getBlock_id() {
        return block_id;
    }

    public int getBlock_by_id() {
        return block_by_id;
    }

    public int getTarget_user_id() {
        return target_user_id;
    }

    // Add a block entry to the BLOCK table
    public static boolean addBlock(int blockerId, int blockedId) {
        String sql = "INSERT INTO BLOCK (block_by_id, target_user_id) VALUES (?, ?)";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, blockerId);
            stmt.setInt(2, blockedId);
            stmt.executeUpdate();
            return true; // Successfully added the block
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Block insertion failed
        }
    }

    // Remove a block entry from the BLOCK table
    public static boolean removeBlock(int blockerId, int blockedId) {
        String sql = "DELETE FROM BLOCK WHERE block_by_id = ? AND target_user_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, blockerId);
            stmt.setInt(2, blockedId);
            stmt.executeUpdate();
            return true; // Successfully removed the block
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Block removal failed
        }
    }

    // Check if a user has blocked another user
    public static boolean isBlocked(int blockerId, int blockedId) {
        String sql = "SELECT COUNT(*) AS block_count FROM BLOCK WHERE block_by_id = ? AND target_user_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, blockerId);
            stmt.setInt(2, blockedId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt("block_count") > 0) {
                return true; // Block exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // No block found
    }

    // Fetch all blocks related to a specific user
    public static List<blockModel> getUserBlocks(int userId) {
        List<blockModel> blocks = new ArrayList<>();
        String sql = "SELECT block_id, block_by_id, target_user_id FROM BLOCK WHERE block_by_id = ? OR target_user_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int blockId = rs.getInt("block_id");
                int blockById = rs.getInt("block_by_id");
                int targetUserId = rs.getInt("target_user_id");

                blocks.add(new blockModel(blockId, blockById, targetUserId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blocks;
    }
}
