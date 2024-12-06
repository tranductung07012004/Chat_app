package org.example.Handler.AdminBoardHandler;

import org.example.GUI.AdminBoard.ChatGroupPanel;
import org.example.Model.groupChatModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChatGroupHandler {

    private ChatGroupPanel chatGroup;
    public ChatGroupHandler(ChatGroupPanel inputChatGroup) {

        this.chatGroup = inputChatGroup;

        chatGroup.components.reloadBtn.addActionListener(e -> handleReloadBtn());
        // Tra cứu nhóm theo tên nhóm
        chatGroup.components.submitSearchBtn.addActionListener(e -> handleSubmitSearchBtn());
        chatGroup.components.cancelSearchBtn.addActionListener(e -> handleCancelSearchBtn());
        chatGroup.components.searchByNameBtn.addActionListener(e -> handleSearchByNameBtn());

        // Tra cứu thành viên nhóm theo tên nhóm
        chatGroup.components.viewAdminBtn.addActionListener(e -> handleViewAdminBtn());
        chatGroup.components.submitViewAdminBtn.addActionListener(e -> handleSubmitViewAdminBtn());
        chatGroup.components.cancelViewAdminBtn.addActionListener(e -> handleCancelViewAdminBtn());

        // Tra cứu admin nhóm theo tên nhóm
        chatGroup.components.viewMemberBtn.addActionListener(e -> handleViewMemberBtn());
        chatGroup.components.submitViewMemberBtn.addActionListener(e -> handleSubmitViewMemberBtn());
        chatGroup.components.cancelViewMemberBtn.addActionListener(e -> handleCancelViewMemberBtn());

        handleReloadBtn();
    }

    public void updateTableData(DefaultTableModel inputTableModel, Object[][] data) {
        // Xóa dữ liệu cũ
        inputTableModel.setRowCount(0);

        // Thêm dữ liệu mới
        for (Object[] row : data) {
            inputTableModel.addRow(row);
        }
    }



    private void handleReloadBtn() {
        Object[][] data = groupChatModel.getAllGroupInfo();
        updateTableData(chatGroup.components.tableGroupModel, data);
    }

    private void handleSubmitSearchBtn() {
        String groupName = chatGroup.components.textFieldSearch.getText().trim();

        if (groupName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập tên của một nhóm muốn tìm kiếm.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[][] data = groupChatModel.getGroupByGroupName(groupName);
        if (data != null) {
            if (data.length > 0) {
                JOptionPane.showMessageDialog(null, "Tìm thấy tên nhóm.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                updateTableData(chatGroup.components.tableGroupModel, data);
            }
            else {
                JOptionPane.showMessageDialog(null, "Tên nhóm không tồn tại.", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Lỗi trong lúc truy xuất dữ liệu.", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleCancelSearchBtn() {
        chatGroup.components.searchDialog.setVisible(false);
    }
    private void handleSearchByNameBtn() {
        chatGroup.components.searchDialog.setVisible(true);
    }

    private void handleViewAdminBtn() {
        chatGroup.components.viewAdminDialog.setVisible(true);
    }

    private void handleCancelViewAdminBtn() {
        chatGroup.components.viewAdminDialog.setVisible(false);
    }
    private void handleSubmitViewAdminBtn() {
        String group_name = chatGroup.components.textFieldViewAdmin.getText().trim();

        if (group_name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập tên của một nhóm nào đó.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[][] data = groupChatModel.getGroupAdminGroupName(group_name);
        if (data != null) {
            if (data.length > 0) {
                JOptionPane.showMessageDialog(null, "Truy xuất thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                updateTableData(chatGroup.components.adminModel, data);
            }
            else {
                JOptionPane.showMessageDialog(null, "Nhóm không có admin.", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Lỗi trong lúc truy xuất dữ liệu.", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleViewMemberBtn() {
        chatGroup.components.viewMemberDialog.setVisible(true);
    }

    private void handleCancelViewMemberBtn() {
        chatGroup.components.viewMemberDialog.setVisible(false);
    }

    private void handleSubmitViewMemberBtn() {
        String group_name = chatGroup.components.textFieldViewMember.getText().trim();

        if (group_name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập tên của một nhóm nào đó.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[][] data = groupChatModel.getMemberByGroupName(group_name);
        if (data != null) {
            if (data.length > 0) {
                JOptionPane.showMessageDialog(null, "Truy xuất thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                updateTableData(chatGroup.components.memberModel, data);
            }
            else {
                JOptionPane.showMessageDialog(null, "Nhóm không có thành viên hoặc nhóm không tồn tại.", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Lỗi trong lúc truy xuất dữ liệu.", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
}

