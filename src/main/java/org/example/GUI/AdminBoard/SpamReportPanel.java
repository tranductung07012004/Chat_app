package org.example.GUI.AdminBoard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

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

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable spamTable = new JTable(tableModel){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        spamTable.setRowSorter(sorter);


        sorter.setSortable(0, true);
        sorter.setSortable(1, true);


        JScrollPane scrollPane = new JScrollPane(spamTable);

        // Panel chức năng
        JPanel actionPanel = new JPanel();
        JButton lockUserButton = new JButton("Khóa tài khoản");
        JButton filterTimeButton = new JButton("Lọc theo thời gian");
        JButton filterAccountNameButton = new JButton("Lọc theo tên đăng nhập");


        actionPanel.add(filterAccountNameButton);
        actionPanel.add(filterTimeButton);
        actionPanel.add(lockUserButton);

        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện khóa tài khoản
        lockUserButton.addActionListener(e -> lockUser());
        filterTimeButton.addActionListener(e -> filterTime());
        filterAccountNameButton.addActionListener(e -> filterAccountName());

    }

    private void lockUser() {

    }

    private void filterAccountName() {

    }

    private void filterTime() {

    }
}
