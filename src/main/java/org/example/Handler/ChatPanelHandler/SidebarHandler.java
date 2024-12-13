package org.example.Handler.ChatPanelHandler;

import org.example.GUI.ChatPanelGUI.SidebarFrame;
import org.example.GUI.MainFrameGUI;
import org.example.Model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.util.stream.Stream;
import javax.swing.table.DefaultTableModel;

public class SidebarHandler {
    private SidebarFrame sidebarFrame;
    private static MainFrameGUI mainFrame;


    public SidebarHandler(SidebarFrame sidebarFrame, MainFrameGUI mainFrame) {
        this.sidebarFrame = sidebarFrame;
        this.mainFrame = mainFrame;

        this.sidebarFrame.getTheComponents().searchAllMess.addActionListener(e -> handleSearchAllMess());
        this.sidebarFrame.getTheComponents().cancelSearchAllMess.addActionListener(e -> handleCancelSearchAllMess());
        this.sidebarFrame.getTheComponents().submitSearchAllMess.addActionListener(e -> handleSubmitSearchAllMess());
        this.sidebarFrame.getTheComponents().cancelSearchResultDialog.addActionListener(e -> handleCancelSearchResultDialog());
        initializeHandlers();
    }

    public void updateTableData(DefaultTableModel inputTableModel, Object[][] data) {
        // Xóa dữ liệu cũ
        inputTableModel.setRowCount(0);

        // Thêm dữ liệu mới
        for (Object[] row : data) {
            inputTableModel.addRow(row);
        }
    }

    private void handleCancelSearchResultDialog() {
        this.sidebarFrame.getTheComponents().searchResultDialog.setVisible(false);
    }

    private void handleSubmitSearchAllMess() {
        this.sidebarFrame.getTheComponents().searchAllMessDialog.setVisible(false);
        String chat_content = this.sidebarFrame.getTheComponents().searchAllMessField.getText();
        int user_id = mainFrame.getCurrentUserId();
        Object[][] messageUser = messageOfUserModel.searchForMessage(chat_content, user_id);
        Object[][] messageGroup = messageOfGroupModel.searchForMessage(chat_content, user_id);

        if (messageGroup != null && messageGroup.length == 0) {
            JOptionPane.showMessageDialog(null, "Không có tin nhắn nào có chuỗi như vậy với nhóm khác.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
        }
        if (messageUser != null && messageUser.length == 0) {
            JOptionPane.showMessageDialog(null, "Không có tin nhắn nào có chuỗi như vậy với người khác.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
        }

        Object[][] mergedMessages = Stream.concat(
                messageUser != null ? Stream.of(messageUser) : Stream.empty(),
                messageGroup != null ? Stream.of(messageGroup) : Stream.empty()
        ).toArray(Object[][]::new);

        updateTableData(this.sidebarFrame.getTheComponents().tableModel, mergedMessages);

        this.sidebarFrame.getTheComponents().searchResultDialog.setVisible(true);
    }

    private void handleCancelSearchAllMess() {
        this.sidebarFrame.getTheComponents().searchAllMessDialog.setVisible(false);
    }
    private void handleSearchAllMess() {
        this.sidebarFrame.getTheComponents().searchAllMessDialog.setVisible(true);
    }

    private void initializeHandlers() {
        // Add action listener for the dropdown options
        JComboBox<String> optionsComboBox = sidebarFrame.getOptionsComboBox();
        optionsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleOptionSelection((String) optionsComboBox.getSelectedItem());
            }
        });

       
    }
    public static List<Contact> loadAllContacts(int currentUserId) {
        List<Contact> contacts = new ArrayList<>();
        // Load individual chat contacts
        contacts.addAll(loadChatContacts(currentUserId));
        // Load group chat contacts
        contacts.addAll(loadGroupContacts(currentUserId));
        return contacts;
    }
    public static List<Contact> loadChatContacts(int currentUserId)
    {

        List<Contact> contacts = new ArrayList<>();

        // SQL query to get users who have chatted with the current user
        String sql = "SELECT DISTINCT CASE WHEN from_user = ? THEN to_user ELSE from_user END AS contact_id " +
                "FROM message_of_user WHERE from_user = ? OR to_user = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the current user ID in the query
            stmt.setInt(1, currentUserId);
            stmt.setInt(2, currentUserId);
            stmt.setInt(3, currentUserId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int contactId = rs.getInt("contact_id");
                endUserModel user=endUserModel.getUserFromId(contactId);

                // Create a Contact object for each user who has had a chat with the current user
                // You can fetch additional user info if needed, like username, online status, etc.
                Contact contact = new Contact(
                        user.getAccountName(), // Fetch the contact's username from the database
                        user.getOnline(),    // Check if the user is online
                        false,                      // Assuming this is not a group chat
                        contactId                   // Set the contact's ID
                );

                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;

    }
    private static List<Contact> loadGroupContacts(int currentUserId) {
        List<Contact> groupContacts = new ArrayList<>();
        String sql = "SELECT gc.group_id, gc.group_name " +
                "FROM group_chat_member gcm " +
                "JOIN group_chat gc ON gcm.group_chat_id = gc.group_id " +
                "WHERE gcm.group_member_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, currentUserId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int groupId = rs.getInt("group_id");
                String groupName = rs.getString("group_name");

                Contact groupContact = new Contact(
                        groupName,    // Group name as contact name
                        true,        // Groups are generally "online"
                        true,         // This is a group chat
                        groupId       // Group ID
                );
                groupContacts.add(groupContact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groupContacts;
    }

    private void handleOptionSelection(String selectedOption) {
        if (null != selectedOption) switch (selectedOption) {
            case "Cài đặt":
                mainFrame.showSettingsPanel(); // Show Settings Panel when selected
                break;
            case "Nhắn tin":
                mainFrame.showChatPanel(); // Show Chat Panel when selected
                break;
            case "Lời mời kết bạn":
                mainFrame.showFriendRequestFrame(); // Show FriendRequest Frame when selected
                break;
            default:
                break;
        }
    }
}
