package GUI.AdminBoard;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;


public class UserManagementPanel extends JPanel {
    public UserManagementPanel() {

        setLayout(new BorderLayout());

        // Tạo bảng hiển thị danh sách người dùng
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Blocked"};
        Object[][] data = {
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user2", "Trần Thị B", "Hồ Chí Minh", "1998-03-22", "Nữ", "user2@example.com", true},
                {"user3", "Lê Văn C", "Đà Nẵng", "2000-12-01", "Nam", "user3@example.com", false},
                {"user4", "Phạm Thị D", "Cần Thơ", "1992-11-11", "Nữ", "user4@example.com", true},
                {"user5", "Hoàng Văn E", "Hải Phòng", "1985-05-05", "Nam", "user5@example.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false},
                {"user1", "Nguyễn Văn A", "Hà Nội", "1995-06-15", "Nam", "eyesontheprize2k4@gmail.com", false}

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


        JScrollPane scrollPane = new JScrollPane(userTable);

        // Tạo panel cho các chức năng thêm, cập nhật, xóa
        JPanel actionPanel = new JPanel();
        JButton addButton = new JButton("Thêm");
        JButton updateButton = new JButton("Cập nhật");
        JButton deleteButton = new JButton("Xóa");
        JButton lockButton = new JButton("Khóa / Mở khóa");
        JButton searchButton = new JButton("Tìm kiếm");
        JButton sortButton = new JButton("Sắp xếp");
        JButton loginHistory = new JButton("Lịch sử đăng nhập");
        JButton friendList = new JButton("Bạn bè");

        actionPanel.add(sortButton);
        actionPanel.add(loginHistory);
        actionPanel.add(friendList);
        actionPanel.add(searchButton);
        actionPanel.add(addButton);
        actionPanel.add(updateButton);
        actionPanel.add(deleteButton);
        actionPanel.add(lockButton);

        // Thêm các thành phần vào panel
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện cho các nút
        addButton.addActionListener(e -> addUser());
        updateButton.addActionListener(e -> updateUser());
        deleteButton.addActionListener(e -> deleteUser());
        lockButton.addActionListener(e -> lockUnlockUser());

    }

    // Hàm thêm người dùng
    private void addUser() {
        // Hiển thị form để thêm người dùng
    }

    // Hàm cập nhật người dùng
    private void updateUser() {
        // Hiển thị form để cập nhật người dùng
    }

    // Hàm xóa người dùng
    private void deleteUser() {
        // Xử lý xóa người dùng
    }

    // Hàm khóa / mở khóa tài khoản người dùng
    private void lockUnlockUser() {
        // Xử lý khóa / mở khóa
    }

}
