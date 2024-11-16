package GUI.AdminBoard;

import javax.swing.*;
import java.awt.*;

public class ChatGroupPanel extends JPanel {
    public ChatGroupPanel() {

        setLayout(new BorderLayout());

        // Bảng danh sách nhóm chat
        String[] groupColumnNames = {"Tên nhóm", "Thời gian tạo"};
        Object[][] groupData = {
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"},
                {"Nhà hát của những giấc mơ", "2022-11-16 10:00:05"},
                {"Đội marketing", "2022-11-16 10:00:05"}
        }; // Dữ liệu nhóm chat
        JTable groupTable = new JTable(groupData, groupColumnNames);
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

        // Sử dụng JSplitPane để tách hai bảng
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, groupScrollPane, adminMemberScrollPane);
        splitPane.setDividerLocation(200); // Đặt vị trí chia
        splitPane.setResizeWeight(0.5); // Đặt tỷ lệ kích thước giữa hai bảng

        // Panel chức năng
        JPanel actionPanel = new JPanel();
        JButton sortGroupButton = new JButton("Sắp xếp nhóm");
        JButton searchGroupButton = new JButton("Tìm kiếm nhóm");

        actionPanel.add(sortGroupButton);
        actionPanel.add(searchGroupButton);

        add(splitPane, BorderLayout.CENTER); // Đặt JSplitPane vào CENTER
        add(actionPanel, BorderLayout.SOUTH); // Đặt actionPanel vào SOUTH

        // Xử lý sự kiện
        sortGroupButton.addActionListener(e -> sortGroup());
        searchGroupButton.addActionListener(e -> searchGroup());
    }

    private void sortGroup() {
        // Thêm logic sắp xếp nhóm
        JOptionPane.showMessageDialog(this, "Chức năng sắp xếp đang được phát triển.");
    }

    private void searchGroup() {
        // Thêm logic tìm kiếm nhóm
        JOptionPane.showMessageDialog(this, "Chức năng tìm kiếm đang được phát triển.");
    }
}
