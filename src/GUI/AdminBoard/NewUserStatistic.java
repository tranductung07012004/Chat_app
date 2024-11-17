package GUI.AdminBoard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class NewUserStatistic extends JPanel {
    public  NewUserStatistic() {

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

        // Tạo bảng hiển thị danh sách người dùng đăng ký mới
        String[] columnNames = {"Tên đăng nhập", "Họ tên", "Thời gian tạo (đăng ký)"};
        Object[][] data = {
                {"manu", "marcus rashford", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "2020-01-30 09:04:06"},
                {"manu", "marcus rashford", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "2020-01-30 09:04:06"},
                {"manu", "marcus rashford", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "2020-01-30 09:04:06"},
                {"manu", "marcus rashford", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "2020-01-30 09:04:06"},
                {"manu", "marcus rashford", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "2020-01-30 09:04:06"},
                {"manu", "marcus rashford", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "2020-01-30 09:04:06"},
                {"manu", "marcus rashford", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "2020-01-30 09:04:06"},
                {"manu", "marcus rashford", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "2020-01-30 09:04:06"},
                {"manu", "marcus rashford", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "2020-01-30 09:04:06"},
                {"manu", "marcus rashford", "2021-12-16 10:30:01"},
                {"user1", "Nguyễn Văn A", "2022-11-16 10:00:01"},
                {"user2", "Trần Thị B", "2022-11-17 08:51:02"},
                {"user3", "Lê Văn C", "2022-11-16 10:25:34"},
                {"user4", "Phạm Thị D", "2019-09-28 02:15:39"},
                {"user5", "Hoàng Văn E", "2020-01-30 09:04:06"}
        }; // Dữ liệu người dùng đăng ký mới

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
            }
        };


        JTable newUserTable = new JTable(tableModel){
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
        newUserTable.setRowSorter(sorter);


        sorter.setSortable(0, false);
        sorter.setSortable(1, true);
        sorter.setSortable(2, true);


        JScrollPane scrollPane = new JScrollPane(newUserTable);

        JPanel actionPanel = new JPanel();
        JButton filterByNameBtn = new JButton("Lọc theo tên");

        filterByNameBtn.addActionListener(e -> filterByName());

        actionPanel.add(filterByNameBtn);

        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

    }



    private void filterByName() {

    }
}
