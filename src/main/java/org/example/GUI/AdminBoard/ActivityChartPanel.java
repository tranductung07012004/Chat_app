package org.example.GUI.AdminBoard;

import org.example.GUI.MainFrameGUI;
import org.example.Handler.AdminBoardHandler.ActivityChartHandler;
import org.example.Handler.AdminBoardHandler.NewRegistrationByYearHandler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer;

import javax.swing.*;
import java.awt.*;

public class ActivityChartPanel extends JPanel {

    public overallComponents components;
    private MainFrameGUI mainFrame;
    public ActivityChartPanel(MainFrameGUI inputMainFrame) {

        this.components = new overallComponents();
        this.mainFrame = inputMainFrame;

        setLayout(new BorderLayout());

        // Panel chọn năm
        JPanel yearPanel = new JPanel();
        JLabel yearLabel = new JLabel("Nhập năm:");

        yearPanel.add(yearLabel);
        yearPanel.add(this.components.yearInputField);
        yearPanel.add(this.components.submitYearFilterBtn);


        // Tạo Panel chứa biểu đồ
        createBarChart();
        ChartPanel chartPanel = new ChartPanel(this.components.barChart);
        chartPanel.setPreferredSize(new Dimension(800, 400));

        JPanel actionPanel = new JPanel();
        actionPanel.add(this.components.reloadBtn);

        // Thêm biểu đồ và panel chọn năm vào giao diện chính
        add(yearPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        new ActivityChartHandler(this);

    }

    private void createBarChart() {
        // Tạo biểu đồ cột với JFreeChart
        this.components.barChart = ChartFactory.createBarChart(
                "", // Tiêu đề được initilize ở NewRegistrationByYearHandler
                "Tháng",                                  // Trục x
                "Số lượng đăng ký mới",                    // Trục y
                this.components.dataset,                   // Dữ liệu
                PlotOrientation.VERTICAL,                  // Hướng biểu đồ
                false,                                     // Hiển thị chú thích (legend)
                true,                                      // Hiển thị tooltips
                false                                      // URLs
        );

        // Tùy chỉnh renderer cho biểu đồ
        BarRenderer renderer = (BarRenderer) this.components.barChart.getCategoryPlot().getRenderer();
        renderer.setSeriesPaint(0, new Color(52, 28, 128));
        renderer.setBarPainter(new StandardBarPainter()); // Đảm bảo màu hiển thị đơn giản

        // Lấy CategoryPlot từ biểu đồ
        CategoryPlot plot = this.components.barChart.getCategoryPlot();

        // Lấy trục tung (ValueAxis) và ép kiểu sang NumberAxis
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();

        // Đặt giá trị tối thiểu cho trục tung là 0
        yAxis.setLowerBound(0);

        // Cho phép tự động tính toán giá trị tối đa
        yAxis.setAutoRange(true);

        // Đảm bảo các giá trị là số nguyên
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    }



    public class overallComponents {
        public DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        public JTextField yearInputField = new JTextField(10);
        public JButton submitYearFilterBtn = new JButton("OK");
        public JFreeChart barChart;
        public JButton reloadBtn = new JButton("RELOAD");
    }

}
