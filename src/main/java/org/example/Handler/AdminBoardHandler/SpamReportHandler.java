package org.example.Handler.AdminBoardHandler;
import org.example.GUI.AdminBoard.SpamReportPanel;
import org.example.Model.endUserModel;
import org.example.Model.groupChatModel;
import org.example.Model.spamModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SpamReportHandler {
    private SpamReportPanel spamPanel;

    public SpamReportHandler(SpamReportPanel inputSpamPanel) {
        this.spamPanel = inputSpamPanel;

        spamPanel.components.reloadBtn.addActionListener(e -> handleReloadBtn());

        spamPanel.components.filterUsernameButton.addActionListener(e -> handleFilterUsernameBtn());
        spamPanel.components.submitFilterUsername.addActionListener(e -> handleSubmitFilterUsername());
        spamPanel.components.cancelFilterUsername.addActionListener(e -> handleCancelFilterUsername());

        spamPanel.components.lockUserButton.addActionListener(e -> handleLockUserBtn());
        spamPanel.components.submitLockUsername.addActionListener(e -> handleSubmitLockUsernameBtn());
        spamPanel.components.cancelLockUsername.addActionListener(e -> handleCancelLockUsernameBtn());


        spamPanel.components.filterTimeButton.addActionListener(e -> handleFilterTimeBtn());
        spamPanel.components.submitFilterTime.addActionListener(e -> handleSubmitFilterTime());
        spamPanel.components.cancelFilterTime.addActionListener(e -> handleCancelFilterTime());


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
        Object[][] data = spamModel.getAllInfo();
        updateTableData(spamPanel.components.tableModel, data);
    }

    private void handleFilterUsernameBtn() {
        spamPanel.components.filterUsernameDialog.setVisible(true);
    }

    private void handleSubmitFilterUsername() {
        String username = spamPanel.components.filterUsernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không được để trống username", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Object[][] data = spamModel.filterByUsername(username);

        if (data == null) {
            JOptionPane.showMessageDialog(null, "Có lỗi trong quá trình truy vấn.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (data.length > 0) {
            JOptionPane.showMessageDialog(null, "Tìm thấy!.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            updateTableData(spamPanel.components.tableModel, data);
        }
        else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }


    }

    private void handleSubmitLockUsernameBtn() {
        String username = spamPanel.components.lockUsernameField.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không được để trống username.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int status = spamModel.lockUser(username);
        if (status > 0) {
            JOptionPane.showMessageDialog(null, "Người dùng đã bị khóa.", "Success", JOptionPane.INFORMATION_MESSAGE);
            handleReloadBtn();
        } else {
            JOptionPane.showMessageDialog(null, "Không thể thực hiện tác vụ, có thể người dùng không tồn tại.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancelLockUsernameBtn() {
        spamPanel.components.lockDialog.setVisible(false);
    }

    private void handleLockUserBtn() {
        spamPanel.components.lockDialog.setVisible(true);
    }

    private void handleCancelFilterUsername() {
        spamPanel.components.filterUsernameDialog.setVisible(false);
    }

    private void handleFilterTimeBtn() {
        spamPanel.components.filterTimeDialog.setVisible(true);
    }

    private void handleCancelFilterTime() {
        spamPanel.components.filterTimeDialog.setVisible(false);
    }

    private void handleSubmitFilterTime() {
        String startString = spamPanel.components.dateStartField.getText().trim();
        String endString = spamPanel.components.dateEndField.getText().trim();

        if (startString.isEmpty() || endString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phải nhập đầy đủ thông tin.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Kiểm tra định dạng ngày sinh (yyyy/mm/dd)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false); // Không cho phép ngày không hợp lệ (ví dụ: 2024/02/30)
        java.util.Date startDate;
        java.util.Date endDate;
        try {
            startDate = dateFormat.parse(startString);
            endDate = dateFormat.parse(endString);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "startDate hoặc endDate phải có dạng là dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(null, "startDate phải trước endDate.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi java.util.Date thành java.sql.Date
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

        // Tiến hành thêm người dùng nếu tất cả điều kiện đều hợp lệ
        Object[][] data = spamModel.filterByDate(sqlStartDate, sqlEndDate);
        if (data == null) {
            JOptionPane.showMessageDialog(null, "Có lỗi trong quá trình truy vấn.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (data.length > 0) {
            JOptionPane.showMessageDialog(null, "Truy vấn thành công.", "Error", JOptionPane.INFORMATION_MESSAGE);
            updateTableData(spamPanel.components.tableModel, data);
        }
        else {
            JOptionPane.showMessageDialog(null, "Không tồn tại.", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }



}
