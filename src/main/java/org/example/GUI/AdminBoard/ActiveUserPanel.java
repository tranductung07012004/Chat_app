package org.example.GUI.AdminBoard;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AdminBoardHandler.ActiveUserHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ActiveUserPanel extends JPanel {
    public overallComponents components;
    MainFrameGUI mainFrame;
    public ActiveUserPanel(MainFrameGUI inputMainframe) {
        this.components = new overallComponents();
        this.mainFrame = inputMainframe;

        setLayout(new BorderLayout());

        // Panel chọn khoảng thời gian
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Từ ngày:"));
        filterPanel.add(this.components.fromDateField);
        filterPanel.add(new JLabel("Đến ngày:"));
        filterPanel.add(this.components.toDateField);
        filterPanel.add(this.components.filterTimeButton);





        JTable activeUserTable = createActiveUserTable();
        JScrollPane scrollPane = new JScrollPane(activeUserTable);

        JPanel actionPanel = new JPanel();

        actionPanel.add(this.components.reloadBtn);
        actionPanel.add(this.components.filterByNameBtn);
        actionPanel.add(this.components.filterByActivityBtn);

        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Dialog của các chức năng
        showFilterByNameDialog();
        showFilterByActivityDialog();

        new ActiveUserHandler(this);
    }

    private JTable createActiveUserTable() {
        String[] columnNames = {"Tên đăng nhập", "Họ tên",
                "Thời gian tạo (đăng ký)", "Mở ứng dụng", "Số người chat",
                "Số nhóm chat", "Tổng số hoạt động"};

        Object[][] data = {};

        this.components.tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable activeUserTable = new JTable(this.components.tableModel){
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
        activeUserTable.setRowSorter(sorter);


        sorter.setSortable(0, false);
        sorter.setSortable(1, true);
        sorter.setSortable(2, true);
        sorter.setSortable(3, false);
        sorter.setSortable(4, false);
        sorter.setSortable(5, false);

        return activeUserTable;
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

    public void showFilterByActivityDialog() {
        // Tạo dialog
        this.components.filterByActivityDialog.setSize(400, 200);
        this.components.filterByActivityDialog.setLayout(new GridBagLayout());

        // Tạo các thành phần
        Component[][] components = {
                {new JLabel("Nhập số:"), this.components.filterByActivityField},
                {new JLabel("Chọn toán tử:"), this.components.option },
                {this.components.submitFilterByActivity, this.components.cancelFilterByActivity}
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
                this.components.filterByActivityDialog.add(components[row][col], gbc);
            }
        }

        this.components.filterByActivityDialog.setLocationRelativeTo(mainFrame);
        this.components.filterByActivityDialog.setVisible(false);
    }


    public class overallComponents {

        public JButton filterByNameBtn = new JButton("Lọc theo tên");
        public JDialog filterByNameDialog = new JDialog(mainFrame, "Lọc theo tên", true);
        public JTextField filterByNameField = new JTextField(10);
        public JButton submitFilterByName = new JButton("OK");
        public JButton cancelFilterByName = new JButton("Hủy");

        public JButton filterByActivityBtn = new JButton("Lọc theo số lượng hoạt động");
        public JDialog filterByActivityDialog = new JDialog((Frame) null, "Lọc theo số lượng hoạt động", true);
        public JTextField filterByActivityField = new JTextField(10);
        public JButton submitFilterByActivity = new JButton("OK");
        public JButton cancelFilterByActivity = new JButton("Hủy");
        public JComboBox<String> option = new JComboBox<>(new String[]{"=", ">", "<"});

        public JTextField fromDateField = new JTextField(10);
        public JTextField toDateField = new JTextField(10);
        public JButton filterTimeButton = new JButton("Lọc");

        public JButton reloadBtn = new JButton("RELOAD");

        public DefaultTableModel tableModel;

    }
}
