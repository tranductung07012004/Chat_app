package org.example.Handler.AdminBoardHandler;
import org.example.GUI.AdminBoard.ActiveUserPanel;
import org.example.Model.loginHistoryModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ActiveUserHandler {

    private ActiveUserPanel panel;
    public ActiveUserHandler(ActiveUserPanel inputPanel) {
        this.panel = inputPanel;

        panel.components.reloadBtn.addActionListener(e -> handleReloadBtn());

        panel.components.cancelFilterByName.addActionListener(e -> handleCancelFilterByName());
        panel.components.submitFilterByName.addActionListener(e -> handleSubmitFilterByName());
        panel.components.filterByNameBtn.addActionListener(e -> handleFilterBynameBtn());

        panel.components.submitFilterByActivity.addActionListener(e -> handleSubmitFilterByActivity());
        panel.components.cancelFilterByActivity.addActionListener(e ->  handleCancelFilterByActivity());
        panel.components.filterByActivityBtn.addActionListener(e -> handleFilterByActivityBtn());

        panel.components.filterTimeButton.addActionListener(e -> handleFilterTimeButton());

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
        java.sql.Date sqlStartDate = Date.valueOf("1970-01-01");
        java.sql.Date sqlEndDate = Date.valueOf("9999-12-31");

        Object[][] data = loginHistoryModel.getActiveUserInfo(sqlStartDate, sqlEndDate);
        updateTableData(panel.components.tableModel, data);
    }
    private void handleCancelFilterByName() {
        panel.components.filterByNameDialog.setVisible(false);
    }
    private void handleSubmitFilterByName() {
        String accountName = panel.components.filterByNameField.getText().trim();

        if (accountName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập thông tin vào.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[][] data = loginHistoryModel.getActiveUserByAccountName(accountName);

        if (data.length > 0) {
            JOptionPane.showMessageDialog(null, "Tìm thấy!.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            updateTableData(panel.components.tableModel, data);
        }
        else {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void handleFilterBynameBtn() {
        panel.components.filterByNameDialog.setVisible(true);
    }
    private void handleSubmitFilterByActivity() {
        String numString = panel.components.filterByActivityField.getText().trim();
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

        Object[][] data = loginHistoryModel.getActiveUserByActivityNum(selectedOption, num);

        if (data.length > 0){
            JOptionPane.showMessageDialog(null, "Tìm thấy thông tin.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
            updateTableData(panel.components.tableModel, data);
        }
        else {
            JOptionPane.showMessageDialog(null, "Không có thông tin như yêu cầu.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleCancelFilterByActivity() {
        panel.components.filterByActivityDialog.setVisible(false);
    }

    private void handleFilterByActivityBtn() {
        panel.components.filterByActivityDialog.setVisible(true);
    }

    private void handleFilterTimeButton() {
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

        // Tiến hành nếu các điều kiện đều hợp lệ
        Object[][] data = loginHistoryModel.getActiveUserInfo(sqlStartDate, sqlEndDate);
        if (data.length > 0) {
            JOptionPane.showMessageDialog(null, "Truy vấn thành công.", "Error", JOptionPane.INFORMATION_MESSAGE);
            updateTableData(panel.components.tableModel, data);
        }
        else {
            JOptionPane.showMessageDialog(null, "Không tồn tại.", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
