package org.example.Handler.AdminBoardHandler;
import org.example.GUI.AdminBoard.UserFriendListPanel;
import org.example.Model.endUserModel;
import org.example.Model.userFriendModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserFriendListHandler {
    UserFriendListPanel panel;
    public UserFriendListHandler(UserFriendListPanel inputPanel) {
        this.panel = inputPanel;

        panel.components.reloadBtn.addActionListener(e -> handleReloadBtn());

        panel.components.cancelFilterByName.addActionListener(e -> handleCancelFilterByName());
        panel.components.submitFilterByName.addActionListener(e -> handleSubmitFilterByName());
        panel.components.filterByNameBtn.addActionListener(e -> handleFilterBynameBtn());

        panel.components.submitFilterByDirectFriend.addActionListener(e -> handleSubmitFilterByDirectFriend());
        panel.components.cancelFilterByDirectFriend.addActionListener(e ->  handleCancelFilterByDirectFriend());
        panel.components.filterByDirectFriendBtn.addActionListener(e -> handleFilterByDirectFriendBtn());

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
        Object[][] data = userFriendModel.getUserFriendListInfo("");
        updateTableData(panel.components.tableModel, data);
    }

    private void handleCancelFilterByName() {
        panel.components.filterByNameDialog.setVisible(false);
    }
    private void handleSubmitFilterByName() {
        String username = panel.components.filterByNameField.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username không thể để trống.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[][] data = userFriendModel.getUserFriendListInfo(username);
        if (data == null) {
            JOptionPane.showMessageDialog(null, "Username không thể để trống.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (data.length > 0) {
            JOptionPane.showMessageDialog(null, "Lọc thành công.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
            updateTableData(panel.components.tableModel, data);
        }
        else {
            JOptionPane.showMessageDialog(null, "Không có người dùng như vậy.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleFilterBynameBtn() {
        panel.components.filterByNameDialog.setVisible(true);
    }

    private void handleSubmitFilterByDirectFriend() {
        String numString = panel.components.filterByDirectFriendField.getText().trim();
        String selectedOption = (String) panel.components.option.getSelectedItem();

        // Kiểm tra nếu numString là số nguyên không dấu (số nguyên dương hoặc 0)
        if (!numString.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập một số nguyên không dấu hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int num = Integer.parseInt(numString);
        try {
            if (selectedOption.equals("<")) {
                if (num == 0) {
                    JOptionPane.showMessageDialog(null, "Không thể lựa chọn < 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi. Vui lòng kiểm tra lại lựa chọn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi không xác định.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        Object[][] data = userFriendModel.getUserFriendListInfo(selectedOption, num);

        if (data == null) {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi trong quá trình truy vấn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (data.length > 0){
            JOptionPane.showMessageDialog(null, "Tìm thấy thông tin.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
            updateTableData(panel.components.tableModel, data);
        }
        else {
            JOptionPane.showMessageDialog(null, "Không có thông tin như yêu cầu.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    private void handleCancelFilterByDirectFriend() {
        panel.components.filterByDirectFriendDialog.setVisible(false);
    }
    private void handleFilterByDirectFriendBtn() {
        panel.components.filterByDirectFriendDialog.setVisible(true);
    }
}
