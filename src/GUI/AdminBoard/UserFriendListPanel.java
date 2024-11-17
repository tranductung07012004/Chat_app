package GUI.AdminBoard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class UserFriendListPanel extends JPanel {
    public UserFriendListPanel() {

        setLayout(new BorderLayout());

        // Bảng hiển thị danh sách người dùng và số lượng bạn bè
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Thời gian tạo (đăng ký)", "Số bạn trực tiếp", "Số bạn của bạn"};
        Object[][] data = {
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"},
                {"user1", "Nguyễn Văn A", "2024-11-16 09:00:00",  "90", "2"},
                {"user2", "Trần Thị B", "2022-11-10 11:00:01",  "101", "3"},
                {"user3", "Trần Đức D", "2023-01-23 09:00:00",  "100", "5"},
                {"user4", "Lò Văn E", "2024-10-26 09:00:00",  "60", "10"},
                {"user5", "Nguyễn Được M", "2023-02-23 09:00:00",  "50", "40"}

        }; // Dữ liệu người dùng và bạn bè


        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable friendListTable = new JTable(tableModel){
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
        friendListTable.setRowSorter(sorter);


        sorter.setSortable(0, false);
        sorter.setSortable(1, true);
        sorter.setSortable(2, true);
        sorter.setSortable(3, false);
        sorter.setSortable(4, false);


        JScrollPane scrollPane = new JScrollPane(friendListTable);

        JPanel actionPanel = new JPanel();
        JButton filterByNameBtn = new JButton("Lọc theo tên");
        JButton filterByDirectFriendBtn = new JButton("Lọc theo số lượng bạn trực tiếp");

        filterByNameBtn.addActionListener(e -> filterByName());
        filterByDirectFriendBtn.addActionListener(e -> filterByDirectFriend());

        actionPanel.add(filterByNameBtn);
        actionPanel.add(filterByDirectFriendBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }


    private void filterByName() {


    }

    private void filterByDirectFriend() {

    }
}
