package GUI.AdminBoard;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import javax.swing.table.*;


public class UserManagementPanel extends JPanel {
    public UserManagementPanel() {

        setLayout(new BorderLayout());

        // Tạo bảng hiển thị danh sách người dùng
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Ngày tạo (đăng ký)"};
        Object[][] data = {
                {"manu", "marcus rashford", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "Hồ Chí Minh", "1998-03-22", "Nữ", "user2@example.com", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "Đà Nẵng", "2000-12-01", "Nam", "user3@example.com", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "Cần Thơ", "1992-11-11", "Nữ", "user4@example.com", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "Hải Phòng", "1985-05-05", "Nam", "user5@example.com", "2020-01-30 09:04:06"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2024-02-27 09:59:03"},
                {"manu", "marcus rashford", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "Hồ Chí Minh", "1998-03-22", "Nữ", "user2@example.com", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "Đà Nẵng", "2000-12-01", "Nam", "user3@example.com", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "Cần Thơ", "1992-11-11", "Nữ", "user4@example.com", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "Hải Phòng", "1985-05-05", "Nam", "user5@example.com", "2020-01-30 09:04:06"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2024-02-27 09:59:03"},
                {"manu", "marcus rashford", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "Hồ Chí Minh", "1998-03-22", "Nữ", "user2@example.com", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "Đà Nẵng", "2000-12-01", "Nam", "user3@example.com", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "Cần Thơ", "1992-11-11", "Nữ", "user4@example.com", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "Hải Phòng", "1985-05-05", "Nam", "user5@example.com", "2020-01-30 09:04:06"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2024-02-27 09:59:03"},
                {"manu", "marcus rashford", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "Hồ Chí Minh", "1998-03-22", "Nữ", "user2@example.com", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "Đà Nẵng", "2000-12-01", "Nam", "user3@example.com", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "Cần Thơ", "1992-11-11", "Nữ", "user4@example.com", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "Hải Phòng", "1985-05-05", "Nam", "user5@example.com", "2020-01-30 09:04:06"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2024-02-27 09:59:03"},
                {"manu", "marcus rashford", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "Hồ Chí Minh", "1998-03-22", "Nữ", "user2@example.com", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "Đà Nẵng", "2000-12-01", "Nam", "user3@example.com", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "Cần Thơ", "1992-11-11", "Nữ", "user4@example.com", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "Hải Phòng", "1985-05-05", "Nam", "user5@example.com", "2020-01-30 09:04:06"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2024-02-27 09:59:03"},
                {"manu", "marcus rashford", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "Hồ Chí Minh", "1998-03-22", "Nữ", "user2@example.com", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "Đà Nẵng", "2000-12-01", "Nam", "user3@example.com", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "Cần Thơ", "1992-11-11", "Nữ", "user4@example.com", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "Hải Phòng", "1985-05-05", "Nam", "user5@example.com", "2020-01-30 09:04:06"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2024-02-27 09:59:03"},
                {"manu", "marcus rashford", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "Hồ Chí Minh", "1998-03-22", "Nữ", "user2@example.com", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "Đà Nẵng", "2000-12-01", "Nam", "user3@example.com", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "Cần Thơ", "1992-11-11", "Nữ", "user4@example.com", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "Hải Phòng", "1985-05-05", "Nam", "user5@example.com", "2020-01-30 09:04:06"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2024-02-27 09:59:03"},
                {"manu", "marcus rashford", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "Hồ Chí Minh", "1998-03-22", "Nữ", "user2@example.com", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "Đà Nẵng", "2000-12-01", "Nam", "user3@example.com", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "Cần Thơ", "1992-11-11", "Nữ", "user4@example.com", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "Hải Phòng", "1985-05-05", "Nam", "user5@example.com", "2020-01-30 09:04:06"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2024-02-27 09:59:03"},
                {"manu", "marcus rashford", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "Hồ Chí Minh", "1998-03-22", "Nữ", "user2@example.com", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "Đà Nẵng", "2000-12-01", "Nam", "user3@example.com", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "Cần Thơ", "1992-11-11", "Nữ", "user4@example.com", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "Hải Phòng", "1985-05-05", "Nam", "user5@example.com", "2020-01-30 09:04:06"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2024-02-27 09:59:03"},
                {"manu", "marcus rashford", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "Hồ Chí Minh", "1998-03-22", "Nữ", "user2@example.com", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "Đà Nẵng", "2000-12-01", "Nam", "user3@example.com", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "Cần Thơ", "1992-11-11", "Nữ", "user4@example.com", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "Hải Phòng", "1985-05-05", "Nam", "user5@example.com", "2020-01-30 09:04:06"},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", "2024-02-27 09:59:03"}

        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable userTable = new JTable(tableModel){
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
        userTable.setRowSorter(sorter);


        sorter.setSortable(0, false);
        sorter.setSortable(1, true);
        sorter.setSortable(2, false);
        sorter.setSortable(3, false);
        sorter.setSortable(4, false);
        sorter.setSortable(5, false);
        sorter.setSortable(6, true);



        JScrollPane scrollPane = new JScrollPane(userTable);

        String[] columnNamesLogin = {"Lịch sử đăng nhập"};
        Object[][] dataLogin = {
                {"2021-12-16 10:59:01"},
                {"2022-01-30 11:22:10"},
                {"2022-02-27 01:27:30"},
                {"2023-05-29 05:36:54"},
                {"2023-05-11 08:44:01"},
                {"2024-09-10 17:51:59"},
                {"2021-12-16 10:59:01"},
                {"2022-01-30 11:22:10"},
                {"2022-02-27 01:27:30"},
                {"2023-05-29 05:36:54"},
                {"2023-05-11 08:44:01"},
                {"2024-09-10 17:51:59"},
                {"2021-12-16 10:59:01"},
                {"2022-01-30 11:22:10"},
                {"2022-02-27 01:27:30"},
                {"2023-05-29 05:36:54"},
                {"2023-05-11 08:44:01"},
                {"2024-09-10 17:51:59"},
                {"2021-12-16 10:59:01"},
                {"2022-01-30 11:22:10"},
                {"2022-02-27 01:27:30"},
                {"2023-05-29 05:36:54"},
                {"2023-05-11 08:44:01"},
                {"2024-09-10 17:51:59"},
                {"2021-12-16 10:59:01"},
                {"2022-01-30 11:22:10"},
                {"2022-02-27 01:27:30"},
                {"2023-05-29 05:36:54"},
                {"2023-05-11 08:44:01"},
                {"2024-09-10 17:51:59"},
                {"2021-12-16 10:59:01"},
                {"2022-01-30 11:22:10"},
                {"2022-02-27 01:27:30"},
                {"2023-05-29 05:36:54"},
                {"2023-05-11 08:44:01"},
                {"2024-09-10 17:51:59"},
                {"2021-12-16 10:59:01"},
                {"2022-01-30 11:22:10"},
                {"2022-02-27 01:27:30"},
                {"2023-05-29 05:36:54"},
                {"2023-05-11 08:44:01"},
                {"2024-09-10 17:51:59"},
                {"2021-12-16 10:59:01"},
                {"2022-01-30 11:22:10"},
                {"2022-02-27 01:27:30"},
                {"2023-05-29 05:36:54"},
                {"2023-05-11 08:44:01"},
                {"2024-09-10 17:51:59"},
                {"2021-12-16 10:59:01"},
                {"2022-01-30 11:22:10"},
                {"2022-02-27 01:27:30"},
                {"2023-05-29 05:36:54"},
                {"2023-05-11 08:44:01"},
                {"2024-09-10 17:51:59"},
                {"2021-12-16 10:59:01"},
                {"2022-01-30 11:22:10"},
                {"2022-02-27 01:27:30"},
                {"2023-05-29 05:36:54"},
                {"2023-05-11 08:44:01"},
                {"2024-09-10 17:51:59"},
                {"2021-12-16 10:59:01"},
                {"2022-01-30 11:22:10"},
                {"2022-02-27 01:27:30"},
                {"2023-05-29 05:36:54"},
                {"2023-05-11 08:44:01"},
                {"2024-09-10 17:51:59"}

        };

        DefaultTableModel tableModelLogin = new DefaultTableModel(dataLogin, columnNamesLogin) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable LoginTable = new JTable(tableModelLogin){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorterLoginTable = new TableRowSorter<>(tableModelLogin);
        LoginTable.setRowSorter(sorterLoginTable);


        sorterLoginTable.setSortable(0, true);

        JScrollPane scrollPaneLogin = new JScrollPane(LoginTable);


        String[] columnNamesFriend = {"Account name", "Họ tên"};
        Object[][] dataFriend = {
                {"manu", "marcus rashford"},
                {"user1", "Nguyễn Văn A"},
                {"user2", "Trần Thị B"},
                {"user3", "Lê Văn C"},
                {"user4", "Phạm Thị D"},
                {"user5", "Hoàng Văn E"},
                {"manu", "marcus rashford"},
                {"user1", "Nguyễn Văn A"},
                {"user2", "Trần Thị B"},
                {"user3", "Lê Văn C"},
                {"user4", "Phạm Thị D"},
                {"user5", "Hoàng Văn E"},
                {"manu", "marcus rashford"},
                {"user1", "Nguyễn Văn A"},
                {"user2", "Trần Thị B"},
                {"user3", "Lê Văn C"},
                {"user4", "Phạm Thị D"},
                {"user5", "Hoàng Văn E"},
                {"manu", "marcus rashford"},
                {"user1", "Nguyễn Văn A"},
                {"user2", "Trần Thị B"},
                {"user3", "Lê Văn C"},
                {"user4", "Phạm Thị D"},
                {"user5", "Hoàng Văn E"},
                {"manu", "marcus rashford"},
                {"user1", "Nguyễn Văn A"},
                {"user2", "Trần Thị B"},
                {"user3", "Lê Văn C"},
                {"user4", "Phạm Thị D"},
                {"user5", "Hoàng Văn E"},
                {"manu", "marcus rashford"},
                {"user1", "Nguyễn Văn A"},
                {"user2", "Trần Thị B"},
                {"user3", "Lê Văn C"},
                {"user4", "Phạm Thị D"},
                {"user5", "Hoàng Văn E"}
        };

        DefaultTableModel tableModelFriend = new DefaultTableModel(dataFriend, columnNamesFriend) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable FriendTable = new JTable(tableModelFriend){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorterFriendTable = new TableRowSorter<>(tableModelFriend);
        FriendTable.setRowSorter(sorterFriendTable);


        sorterFriendTable.setSortable(0, true);

        JScrollPane scrollPanelFriend = new JScrollPane(FriendTable);

        JSplitPane FriendLoginPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneLogin, scrollPanelFriend);
        FriendLoginPane.setDividerLocation(200); // Đặt vị trí chia
        FriendLoginPane.setResizeWeight(0.5); // Đặt tỷ lệ kích thước giữa hai bảng

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, FriendLoginPane);
        splitPane.setDividerLocation(200); // Đặt vị trí chia
        splitPane.setResizeWeight(0.5); // Đặt tỷ lệ kích thước giữa hai bảng


        // Tạo panel cho các chức năng
        JPanel actionPanel = new JPanel();
        JButton addButton = new JButton("Thêm");
        JButton updateButton = new JButton("Cập nhật");
        JButton deleteButton = new JButton("Xóa");
        JButton lockButton = new JButton("Khóa / Mở khóa");
        JButton searchBtn = new JButton("Tìm kiếm");
        JButton loginHistoryBtn = new JButton("Lịch sử đăng nhập");
        JButton friendListBtn = new JButton("Bạn bè");

        actionPanel.add(loginHistoryBtn);
        actionPanel.add(friendListBtn);
        actionPanel.add(searchBtn);
        actionPanel.add(addButton);
        actionPanel.add(updateButton);
        actionPanel.add(deleteButton);
        actionPanel.add(lockButton);

        // Thêm các thành phần vào panel
        add(splitPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện cho các nút
        addButton.addActionListener(e -> addUser());
        updateButton.addActionListener(e -> updateUser());
        deleteButton.addActionListener(e -> deleteUser());
        lockButton.addActionListener(e -> lockUnlockUser());
        searchBtn.addActionListener(e -> search());
        loginHistoryBtn.addActionListener(e -> showLoginHistory());
        friendListBtn.addActionListener(e -> showFriendList());


    }


    private void addUser() {

    }


    private void updateUser() {

    }


    private void deleteUser() {

    }

    private void lockUnlockUser() {

    }
    private void search() {

    }
    private void showLoginHistory() {

    }
    private void showFriendList() {

    }


}
