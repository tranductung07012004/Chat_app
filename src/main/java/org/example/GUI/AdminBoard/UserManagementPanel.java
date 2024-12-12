package org.example.GUI.AdminBoard;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AdminBoardHandler.UserManagementHandler;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import javax.swing.table.*;




public class UserManagementPanel extends JPanel {
    public userFriendComponents friendComponents;
    public loginHistoryUserComponents loginHistoryComponents;
    public UserManagementComponents components;
    public deleteUserComponents deleteComponents;
    public addUserComponents addComponents;
    public searchUserComponents searchComponents;
    public updateUserComponents updateComponents;
    public lockUserComponents lockComponents;
    public updatePassUserComponents updatePassComponents;


    private MainFrameGUI mainFrame;

    public UserManagementPanel(MainFrameGUI inputmainFrame) {

        this.updatePassComponents = new updatePassUserComponents();
        this.friendComponents = new userFriendComponents();
        this.loginHistoryComponents = new loginHistoryUserComponents();
        this.lockComponents = new lockUserComponents();
        this.updateComponents = new updateUserComponents();
        this.searchComponents = new searchUserComponents();
        this.addComponents = new addUserComponents();
        this.deleteComponents = new deleteUserComponents();
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
        friendLoginPane.setDividerLocation(350);
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
        lockUserDialog();
        loginHistoryDialog();
        friendDialog();
        updatePassDialog();

        // Xử lý sự kiện cho các nút
        new UserManagementHandler(this);


    }

    private JTable createUserTable() {
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Ngày tạo (đăng ký)", "Khóa", "Online"};
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
        String[] columnNames = {"Tên đăng nhập", "Lịch sử đăng nhập"};
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

        sorter.setSortable(0, false);
        sorter.setSortable(1, true);

        return loginTable;
    }

    private JTable createFriendTable() {
        String[] columnNames = {"Tên đăng nhập", "bạn bè"};
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

        actionPanel.add(this.components.reloadBtn);
        actionPanel.add(this.deleteComponents.deleteButton);
        actionPanel.add(this.addComponents.addButton);
        actionPanel.add(this.searchComponents.searchBtn);
        actionPanel.add(this.updateComponents.updateButton);
        actionPanel.add(this.lockComponents.lockButton);
        actionPanel.add(this.loginHistoryComponents.loginHistoryBtn);
        actionPanel.add(this.friendComponents.friendBtn);
        actionPanel.add(this.updatePassComponents.updatePassword);

        return actionPanel;
    }

    public void updateTableData(DefaultTableModel inputTableModel, Object[][] data) {
        // Xóa dữ liệu cũ
        inputTableModel.setRowCount(0);

        // Thêm dữ liệu mới
        for (Object[] row : data) {
            inputTableModel.addRow(row);
        }
    }



    public void deleteUserDialog() {

        this.deleteComponents.deleteDialog.setSize(400, 130);
        this.deleteComponents.deleteDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tên đăng nhập:"));
        inputPanel.add(this.deleteComponents.textFieldDeleteBtn);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.deleteComponents.submitDeleteBtn);
        buttonPanel.add(this.deleteComponents.cancelDeleteBtn);

        this.deleteComponents.deleteDialog.add(inputPanel, BorderLayout.CENTER);
        this.deleteComponents.deleteDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.deleteComponents.deleteDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.deleteComponents.deleteDialog.setVisible(false);
    }

    public void addUserDialog() {
        this.addComponents.addDialog.setSize(350, 400);
        this.addComponents.addDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Khoảng cách giữa các thành phần và lề dialog
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Cho phép các text field mở rộng ra hết chiều ngang

        String[] labels = {"Tên đăng nhập:", "Họ và tên:", "Pass:", "DOB:", "Address:", "Gender:", "Email:"};
        JTextField[] fields = {
                this.addComponents.usernameFieldAddButton,
                this.addComponents.accountnameFieldAddButton,
                this.addComponents.passFieldAddButton,
                this.addComponents.dobFieldAddButton,
                this.addComponents.addressFieldAddButton,
                this.addComponents.genderFieldAddButton,
                this.addComponents.emailFieldAddButton
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
        buttonPanel.add(this.addComponents.submitAddButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); // Tạo khoảng cách giữa nút "OK" và nút "Hủy"
        buttonPanel.add(this.addComponents.cancelAddButton);

        this.addComponents.addDialog.add(inputPanel, BorderLayout.CENTER);
        this.addComponents.addDialog.add(buttonPanel, BorderLayout.SOUTH);
        this.addComponents.addDialog.setLocationRelativeTo(mainFrame);
        this.addComponents.addDialog.setVisible(false);
    }

    public void searchUserDialog() {
        // Tạo searchDialog
        this.searchComponents.searchDialog.setSize(400, 200);
        this.searchComponents.searchDialog.setLayout(new BorderLayout(10, 10)); // Layout chính

        // Tạo panel chính với GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding giữa các thành phần

        String[] labels = {"Tên đăng nhập", "Họ tên", "Trạng thái"};
        JTextField[] textFields = {
                this.searchComponents.usernameField,
                this.searchComponents.accountnameField
        };
        JButton[] buttons = {
                this.searchComponents.submitUsernameBtn,
                this.searchComponents.submitAccountnameBtn,
                this.searchComponents.submitStateBtn
        };


        // Thêm các thành phần vào mainPanel
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.WEST;
            mainPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Sử dụng JTextField hoặc JComboBox tùy theo i
            if (i < textFields.length) {
                mainPanel.add(textFields[i], gbc);
            } else {
                mainPanel.add(this.searchComponents.option, gbc); // Thay thế stateField bằng JComboBox
            }

            gbc.gridx = 2;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            mainPanel.add(buttons[i], gbc);
        }

        // Tạo panel cho nút Cancel
        JPanel cancelPanel = new JPanel();
        cancelPanel.add(this.searchComponents.cancelSearchButton);

        // Thêm các panel vào searchDialog
        this.searchComponents.searchDialog.add(mainPanel, BorderLayout.CENTER);
        this.searchComponents.searchDialog.add(cancelPanel, BorderLayout.SOUTH);

        // Hiển thị dialog
        this.searchComponents.searchDialog.setLocationRelativeTo(mainFrame);
        this.searchComponents.searchDialog.setVisible(false);
    }






    public void updateUserDialog() {
        // Tạo dialog chính
        this.updateComponents.updateUserDialog.setSize(400, 500);
        this.updateComponents.updateUserDialog.setLayout(new BorderLayout());

        // Panel nhập username
        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBorder(BorderFactory.createTitledBorder("Nhập tên đăng nhập hiện tại"));
        usernamePanel.add(this.updateComponents.usernameFieldUpdateButton, BorderLayout.CENTER);
        usernamePanel.add(this.updateComponents.usernameOkUpdateButton, BorderLayout.EAST);

        String[] labels = {
                "Họ tên:",
                "Ngày sinh (yyyy/mm/dd):",
                "Địa chỉ:",
                "Giới tính:",
                "Email:"
        };

        JComponent[] components = {
                this.updateComponents.accountNameUpdateField,
                this.updateComponents.dobUpdateField,
                this.updateComponents.addressUpdateField,
                this.updateComponents.genderUpdateField,
                this.updateComponents.emailUpdateField
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
        actionPanel.add(this.updateComponents.confirmUpdateButton);
        actionPanel.add(this.updateComponents.cancelUpdateButton);

        // Thêm các thành phần vào dialog
        this.updateComponents.updateUserDialog.add(usernamePanel, BorderLayout.NORTH);
        this.updateComponents.updateUserDialog.add(updatePanel, BorderLayout.CENTER);
        this.updateComponents.updateUserDialog.add(actionPanel, BorderLayout.SOUTH);

        // Hiển thị dialog
        this.updateComponents.updateUserDialog.setLocationRelativeTo(mainFrame);
        this.updateComponents.updateUserDialog.setVisible(false);

    }

    public void lockUserDialog() {
        this.lockComponents.lockDialog.setSize(400, 130);
        this.lockComponents.lockDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tên đăng nhập:"));
        inputPanel.add(this.lockComponents.userNameTextField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.lockComponents.submitLockButton);
        buttonPanel.add(this.lockComponents.submitUnlockButton);
        buttonPanel.add(this.lockComponents.cancelLockButton);

        this.lockComponents.lockDialog.add(inputPanel, BorderLayout.CENTER);
        this.lockComponents.lockDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.lockComponents.lockDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.lockComponents.lockDialog.setVisible(false);
    }

    public void loginHistoryDialog() {
        this.loginHistoryComponents.loginHistoryDialog.setSize(400, 130);
        this.loginHistoryComponents.loginHistoryDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tên đăng nhập:"));
        inputPanel.add(this.loginHistoryComponents.usernameTextField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.loginHistoryComponents.submitLoginHistoryBtn);
        buttonPanel.add(this.loginHistoryComponents.cancelLoginHistoryBtn);

        this.loginHistoryComponents.loginHistoryDialog.add(inputPanel, BorderLayout.CENTER);
        this.loginHistoryComponents.loginHistoryDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.loginHistoryComponents.loginHistoryDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.loginHistoryComponents.loginHistoryDialog.setVisible(false);
    }

    public void friendDialog() {
        this.friendComponents.friendDialog.setSize(400, 130);
        this.friendComponents.friendDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tên đăng nhập:"));
        inputPanel.add(this.friendComponents.usernameTextField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.friendComponents.submitFriendBtn);
        buttonPanel.add(this.friendComponents.cancelFriendBtn);

        this.friendComponents.friendDialog.add(inputPanel, BorderLayout.CENTER);
        this.friendComponents.friendDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.friendComponents.friendDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.friendComponents.friendDialog.setVisible(false);
    }

    public void updatePassDialog() {
        this.updatePassComponents.updatePassDialog.setSize(350, 200);
        this.updatePassComponents.updatePassDialog.setLayout(new BorderLayout());

        // Sử dụng GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL; // TextField mở rộng theo chiều ngang

        // Danh sách các cặp label và text field
        String[] labels = {"Tên đăng nhập:", "Mật khẩu cũ:", "Mật khẩu mới:"};
        JTextField[] textFields = {
                updatePassComponents.usernameTextField,
                updatePassComponents.oldPassTextField,
                updatePassComponents.newPassTextField
        };

        // Thêm các thành phần vào inputPanel
        for (int i = 0; i < labels.length; i++) {
            // Thêm JLabel
            gbc.gridx = 0; // Cột đầu tiên
            gbc.gridy = i; // Hàng thứ i
            inputPanel.add(new JLabel(labels[i]), gbc);

            // Thêm JTextField từ updatePassComponents
            gbc.gridx = 1; // Cột thứ hai
            gbc.gridy = i;
            inputPanel.add(textFields[i], gbc);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.updatePassComponents.submitUpdatePassBtn);
        buttonPanel.add(this.updatePassComponents.cancelUpdatePassBtn);

        this.updatePassComponents.updatePassDialog.add(inputPanel, BorderLayout.CENTER);
        this.updatePassComponents.updatePassDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.updatePassComponents.updatePassDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.updatePassComponents.updatePassDialog.setVisible(false);
    }


    public class UserManagementComponents {

        public JButton reloadBtn = new JButton("RELOAD");

        public DefaultTableModel tableModel;
        public DefaultTableModel tableModelLogin;
        public DefaultTableModel tableModelFriend;
    }

    public class deleteUserComponents {
        // Tính năng xóa người dùng
        public JButton deleteButton = new JButton("Xóa");
        public JDialog deleteDialog = new JDialog(mainFrame, "Xóa người dùng", true);
        public JButton submitDeleteBtn = new JButton("OK");
        public JButton cancelDeleteBtn = new JButton("Hủy");
        public JTextField textFieldDeleteBtn = new JTextField(10);
    }

    public class addUserComponents {
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
    }

    public class searchUserComponents {

        // Tính năng tìm kiếm
        public JButton searchBtn = new JButton("Lọc");
        public JDialog searchDialog = new JDialog(mainFrame, "Tìm kiếm người dùng", true);
        public JTextField usernameField = new JTextField(10);
        public JTextField accountnameField = new JTextField(10);
        public JComboBox<String> option = new JComboBox<>(new String[]{"online", "offline", "blocked", "unblocked"});
        public JButton submitUsernameBtn = new JButton("OK");
        public JButton submitAccountnameBtn = new JButton("OK");
        public JButton submitStateBtn = new JButton("OK");
        public JButton cancelSearchButton = new JButton("Hủy");

    }

    public class updateUserComponents {
        // Tính năng cập nhật
        public JButton updateButton = new JButton("Cập nhật");
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
    }

    public class lockUserComponents {
        public JButton lockButton  = new JButton("Khóa / Mở khóa");
        public JDialog lockDialog = new JDialog(mainFrame, "Khóa/Mở khóa người dùng", true);
        public JTextField userNameTextField = new JTextField(10);
        public JButton submitLockButton = new JButton("Khóa");
        public JButton submitUnlockButton = new JButton("Mở khóa");
        public JButton cancelLockButton = new JButton("Hủy");
    }

    public class loginHistoryUserComponents {
        public JButton loginHistoryBtn = new JButton("Lịch sử đăng nhập");
        public JDialog loginHistoryDialog = new JDialog(mainFrame, "Lịch sử đăng nhập", true);
        public JTextField usernameTextField = new JTextField(10);
        public JButton cancelLoginHistoryBtn = new JButton("Hủy");
        public JButton submitLoginHistoryBtn = new JButton("OK");
    }

    public class userFriendComponents {
        public JButton friendBtn = new JButton("Bạn bè");
        public JDialog friendDialog = new JDialog(mainFrame, "Tra cứu bạn bè", true);
        public JTextField usernameTextField = new JTextField(10);
        public JButton cancelFriendBtn = new JButton("Hủy");
        public JButton submitFriendBtn = new JButton("OK");
    }

    public class updatePassUserComponents {
        public JButton updatePassword = new JButton("Cập nhật mật khẩu");
        public JDialog updatePassDialog = new JDialog(mainFrame, "Tra cứu bạn bè", true);
        public JTextField usernameTextField = new JTextField(10);
        public JPasswordField oldPassTextField = new JPasswordField(10);
        public JPasswordField newPassTextField = new JPasswordField(10);
        public JButton cancelUpdatePassBtn = new JButton("Hủy");
        public JButton submitUpdatePassBtn = new JButton("OK");
    }

}


