package GUI.AdminBoard;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer;

import javax.swing.*;
import java.awt.*;

public class NewRegistrationByYear extends JPanel {
    public NewRegistrationByYear() {
        setLayout(new BorderLayout());

        // Panel chọn năm
        JPanel yearPanel = new JPanel();
        JLabel yearLabel = new JLabel("Chọn năm:");
        JComboBox<String> yearComboBox = new JComboBox<>(new String[]{"2022", "2023", "2024"});
        yearPanel.add(yearLabel);
        yearPanel.add(yearComboBox);

        // Lấy dữ liệu ban đầu cho biểu đồ
        CategoryDataset dataset = createActivityDataset("2022");

        // Tạo biểu đồ cột với JFreeChart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Số lượng người dùng đăng ký mới từng năm", // Tiêu đề
                "Tháng",                                  // Trục x
                "Số lượng đăng ký mới",                    // Trục y
                dataset,                                  // Dữ liệu
                PlotOrientation.VERTICAL,                 // Hướng biểu đồ
                false,                                    // Hiển thị chú thích (legend)
                true,                                     // Hiển thị tooltips
                false                                     // URLs
        );

        BarRenderer renderer = (BarRenderer) barChart.getCategoryPlot().getRenderer();
        renderer.setSeriesPaint(0, new Color(52, 28, 128));
        renderer.setBarPainter(new StandardBarPainter()); // Đảm bảo màu hiển thị đơn giản

        // Tạo Panel chứa biểu đồ
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 400));

        // Thêm biểu đồ và panel chọn năm vào giao diện chính
        add(yearPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

        // Xử lý sự kiện khi chọn năm mới
        yearComboBox.addActionListener(e -> {
            String selectedYear = (String) yearComboBox.getSelectedItem();
            updateChartDataset(barChart, selectedYear);
        });
    }

    // Tạo dữ liệu mẫu cho biểu đồ
    private CategoryDataset createActivityDataset(String year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // Dữ liệu giả lập cho mỗi tháng
        dataset.addValue(100, "Số người dùng", "Tháng 1");
        dataset.addValue(120, "Số người dùng", "Tháng 2");
        dataset.addValue(90, "Số người dùng", "Tháng 3");
        dataset.addValue(140, "Số người dùng", "Tháng 4");
        dataset.addValue(200, "Số người dùng", "Tháng 5");
        dataset.addValue(150, "Số người dùng", "Tháng 6");
        dataset.addValue(170, "Số người dùng", "Tháng 7");
        dataset.addValue(130, "Số người dùng", "Tháng 8");
        dataset.addValue(160, "Số người dùng", "Tháng 9");
        dataset.addValue(140, "Số người dùng", "Tháng 10");
        dataset.addValue(180, "Số người dùng", "Tháng 11");
        dataset.addValue(210, "Số người dùng", "Tháng 12");
        return dataset;
    }

    // Hàm cập nhật dữ liệu cho biểu đồ khi chọn năm mới
    private void updateChartDataset(JFreeChart chart, String year) {
        CategoryDataset newDataset = createActivityDataset(year);
        chart.getCategoryPlot().setDataset(newDataset);
    }
}
