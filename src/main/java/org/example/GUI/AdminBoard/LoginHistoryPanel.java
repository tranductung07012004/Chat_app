package org.example.GUI.AdminBoard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LoginHistoryPanel extends JPanel {
    public LoginHistoryPanel() {

        setLayout(new BorderLayout());
        // Tạo bảng hiển thị lịch sử đăng nhập
        String[] columnNames = {"Thời gian", "Tên đăng nhập", "Họ tên"};
        Object[][] data = {
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-17 03:01:20", "user5", "Hoàng Văn E"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-17 03:01:20", "user5", "Hoàng Văn E"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-17 03:01:20", "user5", "Hoàng Văn E"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-17 03:01:20", "user5", "Hoàng Văn E"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-17 03:01:20", "user5", "Hoàng Văn E"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"},
                {"2024-11-16 09:00:00", "user2", "Nguyễn Văn A"},
                {"2024-12-18 10:01:09", "user3", "Lê Văn C"}
        }; // Dữ liệu lịch sử đăng nhập

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };

        JTable loginHistoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(loginHistoryTable);

        add(scrollPane, BorderLayout.CENTER);
    }
}
