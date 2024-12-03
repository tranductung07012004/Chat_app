package org.example.GUI.AdminBoard;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AdminBoardHandler.ChatGroupHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ChatGroupPanel extends JPanel {
    public chatGroupComponents components;

    private MainFrameGUI mainFrame;

    public ChatGroupPanel(MainFrameGUI inputMainFrame) {
        this.mainFrame = inputMainFrame;
        this.components = new chatGroupComponents();
        setLayout(new BorderLayout());

        JTable groupTable = createGroupTable();
        JScrollPane groupScrollPane = new JScrollPane(groupTable);

        JTable adminTable = createAdminTable();
        JScrollPane adminScrollPane = new JScrollPane(adminTable);

        JTable memberTable = createMemberTable();
        JScrollPane memberScrollPane = new JScrollPane(memberTable);

        JSplitPane adminMemberPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, adminScrollPane, memberScrollPane);
        adminMemberPane.setDividerLocation(200);
        adminMemberPane.setResizeWeight(0.5);
        // Tách hai bảng
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, groupScrollPane, adminMemberPane);
        splitPane.setDividerLocation(200); // Đặt vị trí chia
        splitPane.setResizeWeight(0.5); // Đặt tỷ lệ kích thước giữa hai bảng

        // Panel chức năng
        JPanel actionPanel = new JPanel();
        actionPanel.add(this.components.reloadBtn);
        actionPanel.add(this.components.searchByNameBtn);
        actionPanel.add(this.components.viewAdminBtn);
        actionPanel.add(this.components.viewMemberBtn);


        add(splitPane, BorderLayout.CENTER); // Đặt JSplitPane vào CENTER
        add(actionPanel, BorderLayout.SOUTH); // Đặt actionPanel vào SOUTH

        // Thêm dialog các chức năng
        searchGroupDialog();
        viewMemberDialog();
        viewAdminDialog();

        // Xử lý sự kiện
        new ChatGroupHandler(this);
    }

    public void searchGroupDialog() {

        this.components.searchDialog.setSize(400, 130);
        this.components.searchDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tên nhóm chat:"));
        inputPanel.add(this.components.textFieldSearch);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitSearchBtn);
        buttonPanel.add(this.components.cancelSearchBtn);

        this.components.searchDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.searchDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.searchDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.searchDialog.setVisible(false);
    }

    public void viewMemberDialog() {

        this.components.viewMemberDialog.setSize(400, 130);
        this.components.viewMemberDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tên nhóm chat:"));
        inputPanel.add(this.components.textFieldViewMember);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitViewMemberBtn);
        buttonPanel.add(this.components.cancelViewMemberBtn);

        this.components.viewMemberDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.viewMemberDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.viewMemberDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.viewMemberDialog.setVisible(false);
    }

    public void viewAdminDialog() {

        this.components.viewAdminDialog.setSize(400, 130);
        this.components.viewAdminDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tên nhóm chat:"));
        inputPanel.add(this.components.textFieldViewAdmin);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitViewAdminBtn);
        buttonPanel.add(this.components.cancelViewAdminBtn);

        this.components.viewAdminDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.viewAdminDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.viewAdminDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.viewAdminDialog.setVisible(false);
    }

    private JTable createMemberTable() {

        // Bảng admin nhóm và thành viên
        String[] ColumnNames = {"Tên nhóm", "Thành viên nhóm"};
        Object[][] Data = {};

        this.components.memberModel = new DefaultTableModel(Data, ColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable memberTable = new JTable(this.components.memberModel) {
            @Override
            public String getToolTipText(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int column = columnAtPoint(e.getPoint());
                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        return memberTable;
    }

    private JTable createAdminTable() {

        // Bảng admin nhóm và thành viên
        String[] adminMemberColumnNames = {"Tên nhóm", "Admin nhóm"};
        Object[][] adminMemberData = {};

        this.components.adminModel = new DefaultTableModel(adminMemberData, adminMemberColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable adminMemberTable = new JTable(this.components.adminModel) {
            @Override
            public String getToolTipText(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int column = columnAtPoint(e.getPoint());
                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        return adminMemberTable;
    }

    private JTable createGroupTable() {
        // Bảng danh sách nhóm chat
        String[] groupColumnNames = {"Tên nhóm", "Thời gian tạo"};
        Object[][] groupData = {}; // Dữ liệu nhóm chat

        this.components.tableGroupModel = new DefaultTableModel(groupData, groupColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable groupTable = new JTable(this.components.tableGroupModel){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorterGroupTable = new TableRowSorter<>(this.components.tableGroupModel);
        groupTable.setRowSorter(sorterGroupTable);


        sorterGroupTable.setSortable(0, true);
        sorterGroupTable.setSortable(1, true);

        return groupTable;
    }



    public class chatGroupComponents {
        public JButton reloadBtn = new JButton("RELOAD");
        public DefaultTableModel memberModel;
        public DefaultTableModel tableGroupModel;
        public DefaultTableModel adminModel;

        // Tìm kiếm nhóm
        public JButton searchByNameBtn = new JButton("Tìm kiếm nhóm");
        public JDialog searchDialog = new JDialog(mainFrame, "Tra cứu theo tên nhóm", true);
        public JTextField textFieldSearch = new JTextField(10);
        public JButton submitSearchBtn = new JButton("OK");
        public JButton cancelSearchBtn = new JButton("Hủy");

        // Xem danh sách thành viên
        public JButton viewMemberBtn = new JButton("Tra cứu thành viên nhóm");
        public JDialog viewMemberDialog = new JDialog(mainFrame, "Tra cứu theo tên nhóm", true);
        public JTextField textFieldViewMember = new JTextField(10);
        public JButton submitViewMemberBtn = new JButton("OK");
        public JButton cancelViewMemberBtn = new JButton("Hủy");

        // Xem danh sách admin
        public JButton viewAdminBtn = new JButton("Tra cứu admin nhóm");
        public JDialog viewAdminDialog = new JDialog(mainFrame, "Tra cứu theo tên nhóm", true);
        public JTextField textFieldViewAdmin = new JTextField(10);
        public JButton submitViewAdminBtn = new JButton("OK");
        public JButton cancelViewAdminBtn = new JButton("Hủy");
    }
}
