package org.example.GUI.AdminBoard;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AdminBoardHandler.SpamReportHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

public class SpamReportPanel extends JPanel {

    public overallComponents components;
    public MainFrameGUI mainFrame;
    public SpamReportPanel(MainFrameGUI inputMainFrame) {
        this.mainFrame = inputMainFrame;
        this.components = new overallComponents();
        setLayout(new BorderLayout());


        JTable spamTable = createSpamTable();

        JScrollPane scrollPane = new JScrollPane(spamTable);

        // Panel chức năng
        JPanel actionPanel = new JPanel();

        actionPanel.add(components.reloadBtn);
        actionPanel.add(components.filterUsernameButton);
        actionPanel.add(components.filterTimeButton);
        actionPanel.add(components.lockUserButton);


        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Thêm các dialog
        createFilterByUsernameDialog();
        createLockUsernameDialog();
        createFilterByDate();

        // Xử lý sự kiện khóa tài khoản
        new SpamReportHandler(this);
    }

    private JTable createSpamTable() {
        // Tạo bảng hiển thị báo cáo spam
        String[] columnNames = {"Tên đăng nhập", "Thời gian", "Khóa"};
        Object[][] data = {}; // Dữ liệu báo cáo spam

        components.tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable spamTable = new JTable(components.tableModel){
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
        spamTable.setRowSorter(sorter);


        sorter.setSortable(0, true);
        sorter.setSortable(1, true);

        return spamTable;
    }

    public void createFilterByUsernameDialog() {

        this.components.filterUsernameDialog.setSize(400, 130);
        this.components.filterUsernameDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tên đăng nhập:"));
        inputPanel.add(this.components.filterUsernameField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitFilterUsername);
        buttonPanel.add(this.components.cancelFilterUsername);

        this.components.filterUsernameDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.filterUsernameDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.filterUsernameDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.filterUsernameDialog.setVisible(false);
    }

    public void createLockUsernameDialog() {

        this.components.lockDialog.setSize(400, 130);
        this.components.lockDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Tên đăng nhập:"));
        inputPanel.add(this.components.lockUsernameField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitLockUsername);
        buttonPanel.add(this.components.cancelLockUsername);

        this.components.lockDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.lockDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.lockDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.lockDialog.setVisible(false);
    }

    public void createFilterByDate() {
        this.components.filterTimeDialog.setSize(400, 130);
        this.components.filterTimeDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Bắt đầu:"));
        this.components.dateStartField.setForeground(Color.GRAY);
        this.components.dateStartField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (components.dateStartField.getText().equals("dd/mm/yyyy")) {
                    components.dateStartField.setText("");
                    components.dateStartField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (components.dateStartField.getText().isEmpty()) {
                    components.dateStartField.setText("dd/mm/yyyy");
                    components.dateStartField.setForeground(Color.GRAY);
                }
            }
        });
        inputPanel.add(this.components.dateStartField);
        inputPanel.add(new JLabel("Kết thúc"));
        this.components.dateEndField.setForeground(Color.GRAY);
        this.components.dateEndField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (components.dateEndField.getText().equals("dd/mm/yyyy")) {
                    components.dateEndField.setText("");
                    components.dateEndField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (components.dateEndField.getText().isEmpty()) {
                    components.dateEndField.setText("dd/mm/yyyy");
                    components.dateEndField.setForeground(Color.GRAY);
                }
            }
        });
        inputPanel.add(this.components.dateEndField);


        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.components.submitFilterTime);
        buttonPanel.add(this.components.cancelFilterTime);

        this.components.filterTimeDialog.add(inputPanel, BorderLayout.CENTER);
        this.components.filterTimeDialog.add(buttonPanel, BorderLayout.SOUTH);

        this.components.filterTimeDialog.setLocationRelativeTo(mainFrame); // Căn giữa dialog trên frame
        this.components.filterTimeDialog.setVisible(false);
    }

    public class overallComponents {
        public DefaultTableModel tableModel;
        public JButton reloadBtn = new JButton("RELOAD");

        // Chức năng tìm kiếm theo tên đăng nhập
        public JButton filterUsernameButton = new JButton("Lọc theo tên đăng nhập");
        public JDialog filterUsernameDialog = new JDialog(mainFrame, "Lọc theo tên đăng nhập", true);
        public JTextField filterUsernameField = new JTextField(10);
        public JButton submitFilterUsername = new JButton("OK");
        public JButton cancelFilterUsername = new JButton("Hủy");

        // Chức năng khóa tài khoản dựa trên tên đăng nhập
        public JButton lockUserButton = new JButton("Khóa tài khoản");
        public JDialog lockDialog = new JDialog(mainFrame, "Khóa theo tên đăng nhập", true);
        public JTextField lockUsernameField = new JTextField(10);
        public JButton submitLockUsername = new JButton("OK");
        public JButton cancelLockUsername = new JButton("Hủy");

        // Chức năng lọc theo thời gian
        public JButton filterTimeButton = new JButton("Lọc theo thời gian");
        public JDialog filterTimeDialog = new JDialog(mainFrame, "Lọc theo thời gian", true);
        public JTextField dateStartField = new JTextField("dd/mm/yyyy", 10);
        public JTextField dateEndField = new JTextField("dd/mm/yyyy",10);

        public JButton submitFilterTime = new JButton("OK");
        public JButton cancelFilterTime = new JButton("Hủy");

    }
}
