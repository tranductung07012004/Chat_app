package GUI.AdminBoard;

import javax.swing.*;
import java.awt.*;

public class UserFriendListPanel extends JPanel {
    public UserFriendListPanel() {

        setLayout(new BorderLayout());

        // Bảng hiển thị danh sách người dùng và số lượng bạn bè
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Số bạn trực tiếp", "Số bạn của bạn"};
        Object[][] data = {
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"},
                {"user1", "Nguyễn Văn A", "30", "2"}

        }; // Dữ liệu người dùng và bạn bè
        JTable friendListTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(friendListTable);

        JPanel actionPanel = new JPanel();
        JButton sortBtn = new JButton("Sắp xếp");
        JButton filterByNameBtn = new JButton("Lọc theo tên");
        JButton filterByDirectFriendBtn = new JButton("Lọc theo số lượng bạn trực tiếp");

        sortBtn.addActionListener(e -> sort());
        filterByNameBtn.addActionListener(e -> filterByName());
        filterByDirectFriendBtn.addActionListener(e -> filterByDirectFriend());

        actionPanel.add(sortBtn);
        actionPanel.add(filterByNameBtn);
        actionPanel.add(filterByDirectFriendBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }

    private void sort() {

    }

    private void filterByName() {


    }

    private void filterByDirectFriend() {

    }
}
