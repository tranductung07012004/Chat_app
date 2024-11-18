package org.example.GUI.AdminBoard;

import org.example.GUI.MainFrameGUI;
import javax.swing.*;
import java.awt.*;

public class AdminScreenButton extends JPanel {
    public AdminScreenButton(MainFrameGUI inputMainFrame,
                             CardLayout inputCardLayout,
                             AdminFunctions inputAdminFunctions) {

        setLayout(new GridLayout(8, 1, 10, 10));

        // Tạo các nút chức năng
        JButton btnUserManagement = new JButton("Quản lý người dùng");
        JButton btnLoginHistory = new JButton("Lịch sử đăng nhập");
        JButton btnChatGroup = new JButton("Quản lý nhóm chat");
        JButton btnSpamReport = new JButton("Báo cáo spam");
        JButton btnNewUser = new JButton("Người dùng mới");
        JButton btnUserFriendList = new JButton("Danh sách bạn bè");
        JButton btnActiveUser = new JButton("Người dùng hoạt động");
        JButton btnActivityChart = new JButton("Biểu đồ hoạt động");
        JButton btnNewRegistrationByYear = new JButton("Biểu đồ đăng ký mới");
        JButton btnHome = new JButton("Home");


        // Gắn sự kiện để chuyển panel
        btnUserManagement.addActionListener(e -> inputCardLayout.show(inputAdminFunctions, "UserManagement"));
        btnLoginHistory.addActionListener(e -> inputCardLayout.show(inputAdminFunctions, "LoginHistory"));
        btnChatGroup.addActionListener(e -> inputCardLayout.show(inputAdminFunctions, "ChatGroup"));
        btnSpamReport.addActionListener(e -> inputCardLayout.show(inputAdminFunctions, "SpamReport"));
        btnNewUser.addActionListener(e -> inputCardLayout.show(inputAdminFunctions, "NewUser"));
        btnUserFriendList.addActionListener(e -> inputCardLayout.show(inputAdminFunctions, "UserFriendList"));
        btnActiveUser.addActionListener(e -> inputCardLayout.show(inputAdminFunctions, "ActiveUser"));
        btnActivityChart.addActionListener(e -> inputCardLayout.show(inputAdminFunctions, "ActivityChart"));
        btnNewRegistrationByYear.addActionListener(e -> inputCardLayout.show(inputAdminFunctions, "NewUserChart"));
        btnHome.addActionListener(e -> inputMainFrame.showLoginPanel());



        // Thêm các nút vào panel
        add(btnUserManagement);
        add(btnLoginHistory);
        add(btnChatGroup);
        add(btnSpamReport);
        add(btnNewUser);
        add(btnNewRegistrationByYear);
        add(btnUserFriendList);
        add(btnActiveUser);
        add(btnActivityChart);
        add(btnHome);
    }

}
