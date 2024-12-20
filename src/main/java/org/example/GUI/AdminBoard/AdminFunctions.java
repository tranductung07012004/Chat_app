package org.example.GUI.AdminBoard;

import javax.swing.*;
import java.awt.*;

import org.example.GUI.MainFrameGUI;


public class AdminFunctions extends JPanel {
    private final CardLayout cardLayout;
    public AdminFunctions(MainFrameGUI inputMainFrame) {


        setSize(1200, 600);

        cardLayout = new CardLayout();
        setLayout(cardLayout);


        // Khởi tạo các panel cho từng chức năng
        JPanel userManagementPanel = new UserManagementPanel(inputMainFrame);
        JPanel loginHistoryPanel = new LoginHistoryPanel(inputMainFrame);
        JPanel chatGroupPanel = new ChatGroupPanel(inputMainFrame);
        JPanel spamReportPanel = new SpamReportPanel();
        JPanel newUserPanel = new NewUserStatistic();
        JPanel userFriendListPanel = new UserFriendListPanel();
        JPanel activeUserPanel = new ActiveUserPanel();
        JPanel activityChartPanel = new ActivityChartPanel();
        JPanel newRegistrationByYearPanel = new NewRegistrationByYear();

        // Thêm các panel vào
        add(userManagementPanel, "UserManagement");
        add(loginHistoryPanel, "LoginHistory");
        add(chatGroupPanel, "ChatGroup");
        add(spamReportPanel, "SpamReport");
        add(newUserPanel, "NewUser");
        add(newRegistrationByYearPanel, "NewUserChart");
        add(userFriendListPanel, "UserFriendList");
        add(activeUserPanel, "ActiveUser");
        add(activityChartPanel, "ActivityChart");


    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

}

