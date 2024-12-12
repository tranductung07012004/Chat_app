package org.example.GUI.AdminBoard;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AdminBoardHandler.LoginHistoryHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class LoginHistoryPanel extends JPanel {
    public loginHistoryComponent components;
    private MainFrameGUI mainFrame;

    public LoginHistoryPanel(MainFrameGUI inputMainFrame) {
        this.components = new loginHistoryComponent();
        this.mainFrame = inputMainFrame;



        JTable loginHistoryTable = createLoginHistoryTable();
        JScrollPane scrollPane = new JScrollPane(loginHistoryTable);

        add(scrollPane, BorderLayout.CENTER);
        add(this.components.reloadBtn, BorderLayout.SOUTH);

        new LoginHistoryHandler(this);
    }

    private JTable createLoginHistoryTable() {
        // Bảng danh sách nhóm chat
        String[] columnNames = {"Thời gian", "Tên đăng nhập", "Họ tên"};
        Object[][] data = {}; // Dữ liệu nhóm chat

        this.components.tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable loginHistoryTable = new JTable(this.components.tableModel){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorterGroupTable = new TableRowSorter<>(this.components.tableModel);
        loginHistoryTable.setRowSorter(sorterGroupTable);


        sorterGroupTable.setSortable(0, true);
        sorterGroupTable.setSortable(1, true);

        return loginHistoryTable;
    }

    public class loginHistoryComponent {
        public DefaultTableModel tableModel;
        public JButton reloadBtn = new JButton("RELOAD");
    }
}
