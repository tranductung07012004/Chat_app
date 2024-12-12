package org.example.GUI.AdminBoard;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AdminBoardHandler.UserFriendListHandler;
import org.example.Handler.AdminBoardHandler.UserManagementHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class UserFriendListPanel extends JPanel {

    public overallComponents components;
    private MainFrameGUI mainFrame;
    public UserFriendListPanel(MainFrameGUI inputMainFrame) {
        this.components = new overallComponents();
        this.mainFrame = inputMainFrame;
        setLayout(new BorderLayout());


        JTable friendListTable = createfriendListTable();
        JScrollPane scrollPane = new JScrollPane(friendListTable);

        JPanel actionPanel = new JPanel();
        actionPanel.add(this.components.reloadBtn);
        actionPanel.add(this.components.filterByNameBtn);
        actionPanel.add(this.components.filterByDirectFriendBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Thêm các dialog cho các chức năng
        showFilterByNameDialog();
        showFilterByDirectFriendDialog();

        new UserFriendListHandler(this);
    }

    private JTable createfriendListTable() {
        // Bảng hiển thị danh sách người dùng và số lượng bạn bè
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Thời gian tạo (đăng ký)", "Số bạn trực tiếp", "Số bạn của bạn"};
        Object[][] data = {}; // Dữ liệu người dùng và bạn bè


        this.components.tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable friendListTable = new JTable(this.components.tableModel){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorter = new TableRowSorter<>( this.components.tableModel);
        friendListTable.setRowSorter(sorter);


        sorter.setSortable(0, false);
        sorter.setSortable(1, true);
        sorter.setSortable(2, true);
        sorter.setSortable(3, false);
        sorter.setSortable(4, false);

        return friendListTable;
    }

    public void showFilterByNameDialog() {

        this.components.filterByNameDialog.setSize(400, 130);
        this.components.filterByNameDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Họ tên:"));
        inputPanel.add(this.components.filterByNameField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitFilterByName);
        buttonPanel.add(this.components.cancelFilterByName);

        this.components.filterByNameDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.filterByNameDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.filterByNameDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.filterByNameDialog.setVisible(false);
    }

    public void showFilterByDirectFriendDialog() {
        // Tạo dialog
        this.components.filterByDirectFriendDialog.setSize(400, 200);
        this.components.filterByDirectFriendDialog.setLayout(new GridBagLayout());

        // Tạo các thành phần
        Component[][] components = {
                {new JLabel("Nhập số:"), this.components.filterByDirectFriendField},
                {new JLabel("Chọn toán tử:"), this.components.option },
                {this.components.submitFilterByDirectFriend, this.components.cancelFilterByDirectFriend}
        };

        // Cấu hình layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Sử dụng vòng lặp để thêm các thành phần
        for (int row = 0; row < components.length; row++) {
            for (int col = 0; col < components[row].length; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                this.components.filterByDirectFriendDialog.add(components[row][col], gbc);
            }
        }

        this.components.filterByDirectFriendDialog.setLocationRelativeTo(mainFrame);
        this.components.filterByDirectFriendDialog.setVisible(false);
    }

    public class overallComponents {
        public DefaultTableModel tableModel;

        public JButton filterByNameBtn = new JButton("Lọc theo tên");
        public JDialog filterByNameDialog = new JDialog(mainFrame, "Lọc theo tên", true);
        public JTextField filterByNameField = new JTextField(10);
        public JButton submitFilterByName = new JButton("OK");
        public JButton cancelFilterByName = new JButton("Hủy");


        public JButton reloadBtn = new JButton("RELOAD");
        public JButton filterByDirectFriendBtn = new JButton("Lọc theo số lượng bạn trực tiếp");
        public JDialog filterByDirectFriendDialog = new JDialog((Frame) null, "Filter By Direct Friend", true);
        public JTextField filterByDirectFriendField = new JTextField(10);
        public JButton submitFilterByDirectFriend = new JButton("OK");
        public JButton cancelFilterByDirectFriend = new JButton("Hủy");
        public JComboBox<String> option = new JComboBox<>(new String[]{"=", ">", "<"});

    }




}
