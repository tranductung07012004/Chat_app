package org.example.GUI.AdminBoard;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AdminBoardHandler.NewUserStatisticHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class NewUserStatistic extends JPanel {

    public overallComponents components;
    MainFrameGUI mainFrame;
    public  NewUserStatistic(MainFrameGUI inputMainFrame) {
        this.mainFrame = inputMainFrame;
        this.components = new overallComponents();
        setLayout(new BorderLayout());

        // Panel chọn khoảng thời gian
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Từ ngày:"));
        filterPanel.add(this.components.fromDateField);
        filterPanel.add(new JLabel("Đến ngày:"));
        filterPanel.add(this.components.toDateField);
        filterPanel.add(this.components.filterByTimeButton);



        JTable newUserTable = createNewUserTable();
        JScrollPane scrollPane = new JScrollPane(newUserTable);

        JPanel actionPanel = new JPanel();
        actionPanel.add(this.components.reloadBtn);
        actionPanel.add(this.components.filterByEmailBtn);
        actionPanel.add(this.components.filterByAccountNameBtn);

        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Thêm các dialog
        searchByAccountNameDialog();
        searchByEmailDialog();

        new NewUserStatisticHandler(this);

    }

    private JTable createNewUserTable() {
        // Tạo bảng hiển thị danh sách người dùng đăng ký mới
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Email", "Thời gian tạo (đăng ký)"};
        Object[][] data = {}; // Dữ liệu người dùng đăng ký mới

        components.tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable newUserTable = new JTable(components.tableModel){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(components.tableModel);
        newUserTable.setRowSorter(sorter);


        sorter.setSortable(0, false);
        sorter.setSortable(1, true);
        sorter.setSortable(2, true);
        sorter.setSortable(3, true);

        return newUserTable;
    }

    private void searchByAccountNameDialog() {
        this.components.filterByAccountNameDialog.setSize(400, 130);
        this.components.filterByAccountNameDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Họ tên:"));
        inputPanel.add(this.components.accountNameField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitAccountNameButton);
        buttonPanel.add(this.components.cancelAccountNameButton);

        this.components.filterByAccountNameDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.filterByAccountNameDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.filterByAccountNameDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.filterByAccountNameDialog.setVisible(false);
    }

    private void searchByEmailDialog() {
        this.components.filterByEmailDialog.setSize(400, 130);
        this.components.filterByEmailDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(this.components.emailField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitEmailButton);
        buttonPanel.add(this.components.cancelEmailButton);

        this.components.filterByEmailDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.filterByEmailDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.filterByEmailDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.filterByEmailDialog.setVisible(false);
    }

    public class overallComponents {
        public JTextField fromDateField = new JTextField(10);
        public JTextField toDateField = new JTextField(10);
        public JButton filterByTimeButton = new JButton("Lọc theo thời gian");


        public JButton filterByAccountNameBtn = new JButton("Lọc theo tên");
        public JDialog filterByAccountNameDialog = new JDialog(mainFrame, "Lọc theo họ tên", true);
        public JTextField accountNameField = new JTextField(10);
        public JButton submitAccountNameButton = new JButton("OK");
        public JButton cancelAccountNameButton = new JButton("Hủy");

        public JButton filterByEmailBtn = new JButton("Lọc theo Email");
        public JDialog filterByEmailDialog = new JDialog(mainFrame, "Lọc theo Email", true);
        public JTextField emailField = new JTextField(10);
        public JButton submitEmailButton = new JButton("OK");
        public JButton cancelEmailButton = new JButton("Hủy");


        public DefaultTableModel tableModel;
        public JButton reloadBtn = new JButton("RELOAD");

    }
}
