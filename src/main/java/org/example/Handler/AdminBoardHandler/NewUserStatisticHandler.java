package org.example.Handler.AdminBoardHandler;
import org.example.GUI.AdminBoard.NewUserStatistic;
import org.example.Model.endUserModel;
import org.example.Model.spamModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NewUserStatisticHandler {

    private NewUserStatistic panel;
    public NewUserStatisticHandler(NewUserStatistic inputPanel) {
        this.panel = inputPanel;

        panel.components.reloadBtn.addActionListener(e -> handleReloadBtn());

        panel.components.filterByTimeButton.addActionListener(e -> handleFilterByTimeBtn());
        panel.components.filterByAccountNameBtn.addActionListener(e -> handleFilterByAccountNameBtn());
        panel.components.submitAccountNameButton.addActionListener(e -> handleSubmitAccountNameBtn());
        panel.components.cancelAccountNameButton.addActionListener(e -> handleCancelAccountNameBtn());

        panel.components.filterByEmailBtn.addActionListener(e -> handleFilterByEmailBtn());
        panel.components.submitEmailButton.addActionListener(e -> handleSubmitEmailBtn());
        panel.components.cancelEmailButton.addActionListener(e -> handleCancelEmailButton());
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
        Object[][] data = endUserModel.getAllNewUserStatisticInfo();
        updateTableData(panel.components.tableModel, data);
    }

    private void handleFilterByTimeBtn() {
        String startString = panel.components.fromDateField.getText().trim();
        String endString = panel.components.toDateField.getText().trim();

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

        // Tiến hành lọc nếu tất cả điều kiện đều hợp lệ
        Object[][] data = endUserModel.filterRegisteredByDate(sqlStartDate, sqlEndDate);
        if (data == null) {
            JOptionPane.showMessageDialog(null, "Có lỗi trong quá trình truy vấn.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (data.length > 0) {
            JOptionPane.showMessageDialog(null, "Truy vấn thành công.", "Error", JOptionPane.INFORMATION_MESSAGE);
            updateTableData(panel.components.tableModel, data);
        }
        else {
            JOptionPane.showMessageDialog(null, "Không tồn tại.", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void handleFilterByAccountNameBtn() {
        panel.components.filterByAccountNameDialog.setVisible(true);
    }
    private void handleSubmitAccountNameBtn() {
        String accountname = panel.components.accountNameField.getText().trim();
        // Kiểm tra nếu người dùng không nhập gì
        if (accountname.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập một họ tên.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Gọi hàm tìm kiếm trong cơ sở dữ liệu
        Object[][] userData = endUserModel.getNewUserStatisticByAccountname(accountname);

        // Kiểm tra kết quả trả về
        if (userData.length > 0) {
            JOptionPane.showMessageDialog(null, "Tìm kiếm thành công.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
            updateTableData(panel.components.tableModel, userData);
        }
        else {
            // Thông báo nếu không tìm thấy người dùng
            JOptionPane.showMessageDialog(null, "Không tìm thấy người dùng.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    private void handleCancelAccountNameBtn() {
        panel.components.filterByAccountNameDialog.setVisible(false);
    }

    private void handleFilterByEmailBtn() {
        panel.components.filterByEmailDialog.setVisible(true);
    }
    private void handleSubmitEmailBtn() {
        String email = panel.components.emailField.getText().trim();
        // Kiểm tra nếu người dùng không nhập gì
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập một họ tên.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Gọi hàm tìm kiếm trong cơ sở dữ liệu
        Object[][] userData = endUserModel.getNewUserStatisticByEmail(email);

        // Kiểm tra kết quả trả về
        if (userData.length > 0) {
            JOptionPane.showMessageDialog(null, "Tìm kiếm thành công.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
            updateTableData(panel.components.tableModel, userData);
        }
        else {
            // Thông báo nếu không tìm thấy người dùng
            JOptionPane.showMessageDialog(null, "Không tìm thấy người dùng.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleCancelEmailButton() {
        panel.components.filterByEmailDialog.setVisible(false);
    }
}
