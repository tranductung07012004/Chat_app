package org.example.Handler.AdminBoardHandler;

import org.example.GUI.AdminBoard.LoginHistoryPanel;
import org.example.Model.loginHistoryModel;

import javax.swing.table.DefaultTableModel;

public class LoginHistoryHandler {

    private LoginHistoryPanel loginHistory;
    public LoginHistoryHandler(LoginHistoryPanel inputLoginHistoryPanel) {

        this.loginHistory = inputLoginHistoryPanel;

        this.loginHistory.components.reloadBtn.addActionListener(e -> handleReloadBtn());

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
        Object[][] data = loginHistoryModel.getAllLoginHistory();
        updateTableData(loginHistory.components.tableModel, data);
    }
}
