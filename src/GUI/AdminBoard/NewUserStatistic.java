package GUI.AdminBoard;

import javax.swing.*;
import java.awt.*;

public class NewUserStatistic extends JPanel {
    public  NewUserStatistic() {

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

        // Tạo bảng hiển thị danh sách người dùng đăng ký mới
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Ngày đăng ký"};
        Object[][] data = {
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00"},
                {"user555", "Nguyễn Văn A", "2024-11-16 09:00:00"}
        }; // Dữ liệu người dùng đăng ký mới
        JTable newUserTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(newUserTable);

        JPanel actionPanel = new JPanel();
        JButton sortBtn = new JButton("Sắp xếp");
        JButton filterByNameBtn = new JButton("Lọc theo tên");

        sortBtn.addActionListener(e -> sort());
        filterByNameBtn.addActionListener(e -> filterByName());

        actionPanel.add(sortBtn);
        actionPanel.add(filterByNameBtn);

        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

    }

    private void sort() {

    }

    private void filterByName() {

    }
}
