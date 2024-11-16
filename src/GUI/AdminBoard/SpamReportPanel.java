package GUI.AdminBoard;

import javax.swing.*;
import java.awt.*;

public class SpamReportPanel extends JPanel {
    public SpamReportPanel() {
        setLayout(new BorderLayout());

        // Tạo bảng hiển thị báo cáo spam
        String[] columnNames = {"Thời gian", "Tên đăng nhập"};
        Object[][] data = {
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"},
                {"2024-11-16 10:10:50", "user2"},
                {"2024-11-17 11:23:30", "user5"},
                {"2024-12-01 09:59:00", "user3"},
                {"2024-12-10 03:40:10", "user5"}
        }; // Dữ liệu báo cáo spam
        JTable spamTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(spamTable);

        // Panel chức năng
        JPanel actionPanel = new JPanel();
        JButton lockUserButton = new JButton("Khóa tài khoản");
        JButton filterTimeButton = new JButton("Lọc theo thời gian");
        JButton filterAccountNameButton = new JButton("Lọc theo tên đăng nhập");
        JButton sortButton = new JButton("Sắp xếp");


        actionPanel.add(sortButton);
        actionPanel.add(filterAccountNameButton);
        actionPanel.add(filterTimeButton);
        actionPanel.add(lockUserButton);

        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện khóa tài khoản
        lockUserButton.addActionListener(e -> lockUser());
        sortButton.addActionListener(e -> sort());
        filterTimeButton.addActionListener(e -> filterTime());
        filterAccountNameButton.addActionListener(e -> filterAccountName());

    }

    private void lockUser() {

    }
    private void sort(){

    }
    private void filterAccountName() {

    }
    private void filterTime() {

    }
}
