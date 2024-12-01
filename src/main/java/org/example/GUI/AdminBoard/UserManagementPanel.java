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
        JTable userTable = createUserTable();
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Tạo bảng lịch sử đăng nhập
        JTable loginTable = createLoginTable();
        JScrollPane scrollPaneLogin = new JScrollPane(loginTable);

        // Tạo bảng bạn bè
        JTable friendTable = createFriendTable();
        JScrollPane scrollPaneFriend = new JScrollPane(friendTable);

        // Kết hợp bảng lịch sử đăng nhập và bạn bè
        JSplitPane friendLoginPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneLogin, scrollPaneFriend);
        friendLoginPane.setDividerLocation(200);
        friendLoginPane.setResizeWeight(0.5);

        // Kết hợp bảng người dùng với bảng lịch sử đăng nhập và bạn bè
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, friendLoginPane);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.5);


        // Tạo panel cho các chức năng
        JPanel actionPanel = createActionPanel();


        // Thêm các thành phần vào panel
        add(splitPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);


        // Tạo các dialog cho các chức năng
        deleteUserDialog();
        addUserDialog();
        searchUserDialog();
        updateUserDialog();

        // Xử lý sự kiện cho các nút
        new UserManagementHandler(this);


    }

    private JTable createUserTable() {
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Ngày tạo (đăng ký)", "Khóa"};
        Object[][] data = {};
        this.components.tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable userTable = new JTable(this.components.tableModel) {
            @Override
            public String getToolTipText(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int column = columnAtPoint(e.getPoint());
                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.components.tableModel);
        userTable.setRowSorter(sorter);

        sorter.setSortable(0, false);
        sorter.setSortable(1, true);
        sorter.setSortable(2, false);
        sorter.setSortable(3, false);
        sorter.setSortable(4, false);
        sorter.setSortable(5, false);
        sorter.setSortable(6, true);
        sorter.setSortable(7, false);


        return userTable;
    }

    private JTable createLoginTable() {
        String[] columnNames = {"Lịch sử đăng nhập"};
        Object[][] data = {};
        this.components.tableModelLogin = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable loginTable = new JTable(this.components.tableModelLogin) {
            @Override
            public String getToolTipText(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int column = columnAtPoint(e.getPoint());
                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.components.tableModelLogin);
        loginTable.setRowSorter(sorter);

        sorter.setSortable(0, true);

        return loginTable;
    }

    private JTable createFriendTable() {
        String[] columnNames = {"Tên đăng nhập", "Họ tên"};
        Object[][] data = {};
        this.components.tableModelFriend = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable friendTable = new JTable(this.components.tableModelFriend) {
            @Override
            public String getToolTipText(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int column = columnAtPoint(e.getPoint());
                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.components.tableModelFriend);
        friendTable.setRowSorter(sorter);

        sorter.setSortable(0, true);
        sorter.setSortable(1, true);

        return friendTable;
    }

    public JPanel createActionPanel() {
        JPanel actionPanel = new JPanel();

        actionPanel.add(this.components.deleteButton);
        actionPanel.add(this.components.addButton);
        actionPanel.add(this.components.searchBtn);
        actionPanel.add(this.components.updateButton);
        actionPanel.add(this.components.lockButton);
        actionPanel.add(this.components.loginHistoryBtn);
        actionPanel.add(this.components.friendBtn);
        actionPanel.add(this.components.updatePassword);
        return actionPanel;
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


    public void updateUserDialog() {
        // Tạo dialog chính
        this.components.updateUserDialog.setSize(400, 500);
        this.components.updateUserDialog.setLayout(new BorderLayout());

        // Panel nhập username
        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBorder(BorderFactory.createTitledBorder("Nhập tên đăng nhập hiện tại"));
        usernamePanel.add(this.components.usernameFieldUpdateButton, BorderLayout.CENTER);
        usernamePanel.add(this.components.usernameOkUpdateButton, BorderLayout.EAST);

        String[] labels = {
                "Họ tên:",
                "Ngày sinh (yyyy/mm/dd):",
                "Địa chỉ:",
                "Giới tính:",
                "Email:"
        };

        JComponent[] components = {
                this.components.accountNameUpdateField,
                this.components.dobUpdateField,
                this.components.addressUpdateField,
                this.components.genderUpdateField,
                this.components.emailUpdateField
        };

        // Panel cập nhật thông tin
        JPanel updatePanel = new JPanel(new GridBagLayout());
        updatePanel.setBorder(BorderFactory.createTitledBorder("Cập nhật thông tin"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 2, 2, 5); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dùng vòng lặp để thêm các thành phần vào panel
        for (int i = 0; i < labels.length; i++) {
            // Thêm nhãn
            gbc.gridx = 0;
            gbc.gridy = i;
            updatePanel.add(new JLabel(labels[i]), gbc);

            // Thêm trường dữ liệu
            gbc.gridx = 1;
            gbc.gridy = i;
            updatePanel.add(components[i], gbc);
        }


        // Panel nút hành động
        JPanel actionPanel = new JPanel();
        actionPanel.add(this.components.cancelUpdateButton);
        actionPanel.add(this.components.confirmUpdateButton);

        // Thêm các thành phần vào dialog
        this.components.updateUserDialog.add(usernamePanel, BorderLayout.NORTH);
        this.components.updateUserDialog.add(updatePanel, BorderLayout.CENTER);
        this.components.updateUserDialog.add(actionPanel, BorderLayout.SOUTH);

        // Hiển thị dialog
        this.components.updateUserDialog.setLocationRelativeTo(mainFrame);
        this.components.updateUserDialog.setVisible(false);

    }



    public class UserManagementComponents {

        public JButton updateButton = new JButton("Cập nhật");
        public JButton lockButton  = new JButton("Khóa / Mở khóa");
        public JButton searchBtn = new JButton("Tìm kiếm");
        public JButton loginHistoryBtn = new JButton("Lịch sử đăng nhập");
        public JButton friendBtn = new JButton("Bạn bè");
        public JButton updatePassword = new JButton("Cập nhật mật khẩu");


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
        public JTextField usernameFieldAddButton = new JTextField(15);
        public JTextField passFieldAddButton = new JTextField(15);
        public JTextField accountnameFieldAddButton = new JTextField(15);
        public JTextField dobFieldAddButton = new JTextField(15);
        public JTextField addressFieldAddButton = new JTextField(15);
        public JTextField genderFieldAddButton = new JTextField(15);
        public JTextField emailFieldAddButton = new JTextField(15);

        // Tính năng tìm kiếm
        public JDialog searchDialog = new JDialog(mainFrame, "Tìm kiếm người dùng", true);
        public JTextField textFieldSearchDialog = new JTextField(10);
        public JButton submitSearchButton = new JButton("OK");
        public JButton cancelSearchButton = new JButton("Hủy");


        // Tính năng cập nhật
        public JDialog updateUserDialog = new JDialog(mainFrame, "Cập nhật thông tin người dùng", true);

        public JTextField usernameFieldUpdateButton = new JTextField(10);
        public JButton usernameOkUpdateButton = new JButton("OK");

        public JTextField accountNameUpdateField = new JTextField(10);
        public JTextField dobUpdateField = new JTextField(10);
        public JTextField addressUpdateField = new JTextField(10);
        public JTextField genderUpdateField = new JTextField(10);
        public JTextField emailUpdateField = new JTextField(10);

        public JButton cancelUpdateButton = new JButton("Hủy");
        public JButton confirmUpdateButton = new JButton("Cập nhật");


        public DefaultTableModel tableModel;
        public DefaultTableModel tableModelLogin;
        public DefaultTableModel tableModelFriend;
    }

}


