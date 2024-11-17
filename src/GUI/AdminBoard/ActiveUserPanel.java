package GUI.AdminBoard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ActiveUserPanel extends JPanel {
    public ActiveUserPanel() {

        setLayout(new BorderLayout());

        // Panel chọn khoảng thời gian
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Từ ngày:"));
        JTextField fromDateField = new JTextField(10);
        filterPanel.add(fromDateField);
        filterPanel.add(new JLabel("Đến ngày:"));
        JTextField toDateField = new JTextField(10);
        filterPanel.add(toDateField);
        JButton filterButton = new JButton("Lọc");
        filterPanel.add(filterButton);



        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Thời gian tạo (đăng ký)", "Mở ứng dụng", "Số người chat", "Số nhóm chat"};

        Object[][] data = {
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"},
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"},
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"},
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"},
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"},
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"},
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"},
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"},
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"},
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"},
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"},
                {"user2", "Trần Thị B", "2022-01-16 10:00:00", "186", "20", "11"},
                {"user3", "Trần Thị C", "2024-10-16 03:00:00", "59", "41", "5"},
                {"user2", "Nguyễn Đăng A", "2023-08-16 04:00:00", "451", "16", "25"},
                {"user4", "Nguyễn Trần Trinh A", "2023-05-14 06:30:00", "300", "30", "25"},
                {"user5", "Nguyễn Hoàng L", "2023-04-01 08:01:00", "289", "51", "25"}



        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable activeUserTable = new JTable(tableModel){
            public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
        };

        // Tính năng sắp xếp
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        activeUserTable.setRowSorter(sorter);


        sorter.setSortable(0, false);
        sorter.setSortable(1, true);
        sorter.setSortable(2, true);
        sorter.setSortable(3, false);
        sorter.setSortable(4, false);
        sorter.setSortable(5, false);


        JScrollPane scrollPane = new JScrollPane(activeUserTable);

        JPanel actionPanel = new JPanel();
        JButton filterByNameBtn = new JButton("Lọc theo tên");
        JButton filterByActivityBtn = new JButton("Lọc theo số lượng hoạt động");

        filterByNameBtn.addActionListener(e -> filterByName());
        filterByActivityBtn.addActionListener(e -> filterByActivity());

        actionPanel.add(filterByNameBtn);
        actionPanel.add(filterByActivityBtn);

        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

    }


    private void filterByName() {

    }
    private void filterByActivity() {

    }
}
