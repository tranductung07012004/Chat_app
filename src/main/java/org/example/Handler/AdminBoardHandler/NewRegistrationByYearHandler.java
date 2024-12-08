package org.example.Handler.AdminBoardHandler;

import org.example.GUI.AdminBoard.NewRegistrationByYear;
import org.example.GUI.MainFrameGUI;
import org.example.Model.activityHistoryModel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.example.Model.endUserModel;

import javax.swing.table.DefaultTableModel;
import java.sql.Date;

public class NewRegistrationByYearHandler {

    private NewRegistrationByYear panel;

    public NewRegistrationByYearHandler(NewRegistrationByYear inputPanel) {
        this.panel = inputPanel;

        panel.components.reloadBtn.addActionListener(e -> handleReloadBtn());
        handleReloadBtn();
    }

    private void createActivityDataset(String year) {
        // Tạo đối tượng dataset

        // Lấy dữ liệu từ endUserModel
        Object[] monthlyRegistrations = endUserModel.countNewRegistrationByYear(year);

        // Điền dữ liệu vào dataset
        String[] months = {
                "01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12"
        };

        for (int i = 0; i < months.length; i++) {
            int registrationCount = (int) monthlyRegistrations[i];
            panel.components.dataset.addValue(registrationCount, "Số người dùng", months[i]);
        }
    }

    // Hàm cập nhật dữ liệu cho biểu đồ khi chọn năm mới
    private void updateChartDataset(String year) {
        createActivityDataset(year);
        panel.components.barChart.getCategoryPlot().setDataset(this.panel.components.dataset);
    }

    private void handleReloadBtn() {
        String newestYear = endUserModel.getNewestYear();
        panel.components.barChart.setTitle("Số lượng người dùng đăng ký mới năm " + newestYear);
        updateChartDataset(newestYear);
    }
}
