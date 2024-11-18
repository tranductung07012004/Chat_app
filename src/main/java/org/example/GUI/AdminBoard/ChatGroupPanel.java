package org.example.GUI.AdminBoard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ChatGroupPanel extends JPanel {
    public ChatGroupPanel() {

        setLayout(new BorderLayout());

        // Bảng danh sách nhóm chat
        String[] groupColumnNames = {"Tên nhóm", "Thời gian tạo"};
        Object[][] groupData = {
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Chân đèo đội bóng số 1 nước Anh", "2021-01-13 08:23:05"},
                {"Hùm xám nước Đức", "2022-04-16 01:18:30"},
                {"Man xanh nước Anh", "2023-05-29 05:45:51"}

        }; // Dữ liệu nhóm chat

        DefaultTableModel tableGroupModel = new DefaultTableModel(groupData, groupColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable groupTable = new JTable(tableGroupModel){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorterGroupTable = new TableRowSorter<>(tableGroupModel);
        groupTable.setRowSorter(sorterGroupTable);


        sorterGroupTable.setSortable(0, true);
        sorterGroupTable.setSortable(1, true);


        JScrollPane groupScrollPane = new JScrollPane(groupTable);

        // Bảng admin nhóm và thành viên
        String[] adminMemberColumnNames = {"Thành viên", "Admin nhóm"};
        Object[][] adminMemberData = {
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"},
                {"Rash ford", "Ten hag"},
                {"Antony", "Hansi flick"}
        };

        JTable adminMemberTable = new JTable(adminMemberData, adminMemberColumnNames);

        JScrollPane adminMemberScrollPane = new JScrollPane(adminMemberTable);

        // Tách hai bảng
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, groupScrollPane, adminMemberScrollPane);
        splitPane.setDividerLocation(200); // Đặt vị trí chia
        splitPane.setResizeWeight(0.5); // Đặt tỷ lệ kích thước giữa hai bảng

        // Panel chức năng
        JPanel actionPanel = new JPanel();
        JButton searchGroupButton = new JButton("Tìm kiếm nhóm");

        actionPanel.add(searchGroupButton);

        add(splitPane, BorderLayout.CENTER); // Đặt JSplitPane vào CENTER
        add(actionPanel, BorderLayout.SOUTH); // Đặt actionPanel vào SOUTH

        // Xử lý sự kiện
        searchGroupButton.addActionListener(e -> searchGroup());
    }


    private void searchGroup() {
        JOptionPane.showMessageDialog(this, "Chức năng tìm kiếm đang được phát triển.");
    }
}
