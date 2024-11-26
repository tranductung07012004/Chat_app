package org.example.GUI.AdminBoard;

import org.example.Handler.AdminBoardHandler.UserManagementHandler;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import javax.swing.table.*;


public class UserManagementPanel extends JPanel {

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton lockButton;
    private JButton searchBtn;
    private JButton loginHistoryBtn;
    private JButton friendBtn;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModelLogin;
    private DefaultTableModel tableModelFriend;

    public UserManagementPanel() {

        setLayout(new BorderLayout());

        // Tạo bảng hiển thị danh sách người dùng
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Ngày tạo (đăng ký)"};
        Object[][] data = {};

        this.tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable userTable = new JTable(this.tableModel){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.tableModel);
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
        Object[][] dataLogin = {};

        this.tableModelLogin = new DefaultTableModel(dataLogin, columnNamesLogin) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable LoginTable = new JTable(this.tableModelLogin){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorterLoginTable = new TableRowSorter<>(this.tableModelLogin);
        LoginTable.setRowSorter(sorterLoginTable);


        sorterLoginTable.setSortable(0, true);

        JScrollPane scrollPaneLogin = new JScrollPane(LoginTable);


        String[] columnNamesFriend = {"Tên đăng nhập", "Họ tên"};
        Object[][] dataFriend = {};

        this.tableModelFriend = new DefaultTableModel(dataFriend, columnNamesFriend) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable FriendTable = new JTable(this.tableModelFriend){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorterFriendTable = new TableRowSorter<>(this.tableModelFriend);
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
        this.addButton = new JButton("Thêm");
        this.updateButton = new JButton("Cập nhật");
        this.deleteButton = new JButton("Xóa");
        this.lockButton = new JButton("Khóa / Mở khóa");
        this.searchBtn = new JButton("Tìm kiếm");
        this.loginHistoryBtn = new JButton("Lịch sử đăng nhập");
        this.friendBtn = new JButton("Bạn bè");


        actionPanel.add(searchBtn);
        actionPanel.add(addButton);
        actionPanel.add(updateButton);
        actionPanel.add(deleteButton);
        actionPanel.add(lockButton);
        actionPanel.add(loginHistoryBtn);
        actionPanel.add(friendBtn);

        // Thêm các thành phần vào panel
        add(splitPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện cho các nút
        new UserManagementHandler(this);


    }

    public void updateTableData(Object[][] data) {
        // Xóa dữ liệu cũ
        this.tableModel.setRowCount(0);

        // Thêm dữ liệu mới
        for (Object[] row : data) {
            this.tableModel.addRow(row);
        }
    }


   public JButton getAddButton() { return this.addButton; }
   public JButton getUpdateButton() { return this.updateButton; }
   public JButton getDeleteButton() { return this.deleteButton; }
   public JButton getLockButton() { return this.lockButton; }
   public JButton getSearchBtn() { return this.searchBtn; }
   public JButton getLoginHistoryBtn() { return this.loginHistoryBtn; }
   public JButton getFriendBtn() { return this.friendBtn; }

}
