package org.example.GUI.AdminBoard;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AdminBoardHandler.UserManagementHandler;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import javax.swing.table.*;




public class UserManagementPanel extends JPanel {

    public UserManagementComponents components;

    private MainFrameGUI mainFrame;

    public UserManagementPanel(MainFrameGUI inputmainFrame) {

        this.components = new UserManagementComponents();
        this.mainFrame = inputmainFrame;
        setLayout(new BorderLayout());

        // Tạo bảng hiển thị danh sách người dùng
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Ngày tạo (đăng ký)"};
        Object[][] data = {};

        this.components.tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable userTable = new JTable(this.components.tableModel){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.components.tableModel);
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

        this.components.tableModelLogin = new DefaultTableModel(dataLogin, columnNamesLogin) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable LoginTable = new JTable(this.components.tableModelLogin){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorterLoginTable = new TableRowSorter<>(this.components.tableModelLogin);
        LoginTable.setRowSorter(sorterLoginTable);


        sorterLoginTable.setSortable(0, true);

        JScrollPane scrollPaneLogin = new JScrollPane(LoginTable);


        String[] columnNamesFriend = {"Tên đăng nhập", "Họ tên"};
        Object[][] dataFriend = {};

        this.components.tableModelFriend = new DefaultTableModel(dataFriend, columnNamesFriend) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable FriendTable = new JTable(this.components.tableModelFriend){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorterFriendTable = new TableRowSorter<>(this.components.tableModelFriend);
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




        actionPanel.add(this.components.deleteButton);
        actionPanel.add(this.components.addButton);
        actionPanel.add(this.components.searchBtn);
        actionPanel.add(this.components.updateButton);
        actionPanel.add(this.components.lockButton);
        actionPanel.add(this.components.loginHistoryBtn);
        actionPanel.add(this.components.friendBtn);

        // Thêm các thành phần vào panel
        add(splitPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);


        // Tạo các dialog cho các chức năng
        deleteUserDialog();
        addUserDialog();
        searchUserDialog();

        // Xử lý sự kiện cho các nút
        new UserManagementHandler(this);


    }

    public void updateTableData(Object[][] data) {
        // Xóa dữ liệu cũ
        this.components.tableModel.setRowCount(0);

        // Thêm dữ liệu mới
        for (Object[] row : data) {
            this.components.tableModel.addRow(row);
        }
    }

    public void deleteUserDialog() {

        this.components.deleteDialog.setSize(400, 130);
        this.components.deleteDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tên đăng nhập:"));
        inputPanel.add(this.components.textFieldDeleteBtn);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitDeleteBtn);
        buttonPanel.add(this.components.cancelDeleteBtn);

        this.components.deleteDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.deleteDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.deleteDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.deleteDialog.setVisible(false);
    }

    public void addUserDialog() {
        this.components.addDialog.setSize(350, 400);
        this.components.addDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Khoảng cách giữa các thành phần và lề dialog
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Cho phép các text field mở rộng ra hết chiều ngang

        String[] labels = {"Tên đăng nhập:", "Họ và tên:", "Pass:", "DOB:", "Address:", "Gender:", "Email:"};
        JTextField[] fields = {
                this.components.usernameFieldAddButton,
                this.components.accountnameFieldAddButton,
                this.components.passFieldAddButton,
                this.components.dobFieldAddButton,
                this.components.addressFieldAddButton,
                this.components.genderFieldAddButton,
                this.components.emailFieldAddButton
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0; // Label không cần mở rộng
            inputPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.weightx = 1.0; // Text field được phép mở rộng
            inputPanel.add(fields[i], gbc);
        }

        // Tạo panel chứa các nút và thêm khoảng cách giữa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5)); // Thêm khoảng cách ngang giữa các nút
        buttonPanel.add(this.components.submitAddButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); // Tạo khoảng cách giữa nút "OK" và nút "Hủy"
        buttonPanel.add(this.components.cancelAddButton);

        this.components.addDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.addDialog.add(buttonPanel, BorderLayout.SOUTH);
        this.components.addDialog.setLocationRelativeTo(mainFrame);
        this.components.addDialog.setVisible(false);
    }

    public void searchUserDialog() {

        this.components.searchDialog.setSize(400, 130);
        this.components.searchDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tên đăng nhập:"));
        inputPanel.add(this.components.textFieldSearchDialog);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitSearchButton);
        buttonPanel.add(this.components.cancelSearchButton);

        this.components.searchDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.searchDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.searchDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.searchDialog.setVisible(false);
    }



    public class UserManagementComponents {

        public JButton updateButton = new JButton("Cập nhật");
        public JButton lockButton  = new JButton("Khóa / Mở khóa");
        public JButton searchBtn = new JButton("Tìm kiếm");
        public JButton loginHistoryBtn = new JButton("Lịch sử đăng nhập");
        public JButton friendBtn = new JButton("Bạn bè");


        // Tính năng xóa người dùng
        public JButton deleteButton = new JButton("Xóa");
        public JDialog deleteDialog = new JDialog(mainFrame, "Xóa người dùng", true);
        public JButton submitDeleteBtn = new JButton("OK");
        public JButton cancelDeleteBtn = new JButton("Hủy");
        public JTextField textFieldDeleteBtn = new JTextField(10);

        // Tính năng thêm người dùng
        public JButton addButton = new JButton("Thêm");
        public JDialog addDialog = new JDialog(mainFrame, "Thêm người dùng", true);
        public JButton submitAddButton = new JButton("OK");
        public JButton cancelAddButton = new JButton("Hủy");
        public JTextField usernameFieldAddButton = new JTextField(10);
        public JTextField passFieldAddButton = new JTextField(10);
        public JTextField accountnameFieldAddButton = new JTextField(10);
        public JTextField dobFieldAddButton = new JTextField(10);
        public JTextField addressFieldAddButton = new JTextField(10);
        public JTextField genderFieldAddButton = new JTextField(10);
        public JTextField emailFieldAddButton = new JTextField(10);

        // Tính năng tìm kiếm
        public JDialog searchDialog = new JDialog(mainFrame, "Tìm kiếm người dùng", true);
        public JTextField textFieldSearchDialog = new JTextField(10);
        public JButton submitSearchButton = new JButton("OK");
        public JButton cancelSearchButton = new JButton("Hủy");


        public DefaultTableModel tableModel;
        public DefaultTableModel tableModelLogin;
        public DefaultTableModel tableModelFriend;
    }

}


