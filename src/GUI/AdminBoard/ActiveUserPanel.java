package GUI.AdminBoard;

import javax.swing.*;
import java.awt.*;

public class ActiveUserPanel extends JPanel {
    public ActiveUserPanel() {

        setLayout(new BorderLayout());

        // Panel chọn khoảng thời gian
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Từ ngày:"));
        JTextField fromDateField = new JTextField(10);
        filterPanel.add(fromDateField);
        filterPanel.add(new JLabel("Đến ngày:"));
        JTextField toDateField = new JTextField(10);
        filterPanel.add(toDateField);
        JButton filterButton = new JButton("Lọc");
        filterPanel.add(filterButton);


        // Bảng hiển thị danh sách người dùng hoạt động
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Mở ứng dụng", "Số người chat", "Số nhóm chat"};

        Object[][] data = {
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"},
                {"user2", "Trần Thị B", "200", "50", "10"}


        }; // Dữ liệu người dùng hoạt động
        JTable activeUserTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(activeUserTable);

        JPanel actionPanel = new JPanel();
        JButton sortBtn = new JButton("Sắp xếp");
        JButton filterByNameBtn = new JButton("Lọc theo tên");
        JButton filterByActivityBtn = new JButton("Lọc theo số lượng hoạt động");

        sortBtn.addActionListener(e -> sort());
        filterByNameBtn.addActionListener(e -> filterByName());
        filterByActivityBtn.addActionListener(e -> filterByActivity());

        actionPanel.add(sortBtn);
        actionPanel.add(filterByNameBtn);
        actionPanel.add(filterByActivityBtn);

        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

    }

    private void sort() {

    }

    private void filterByName() {

    }
    private void filterByActivity() {

    }
}
