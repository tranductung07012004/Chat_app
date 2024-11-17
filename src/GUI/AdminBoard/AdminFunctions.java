package GUI.AdminBoard;

import javax.swing.*;
import java.awt.*;

import GUI.MainFrameGUI;


public class AdminFunctions extends JPanel {
    private final CardLayout cardLayout;
    private MainFrameGUI mainFrame;

    public AdminFunctions(MainFrameGUI inputMainFrame) {


        setSize(1150, 600);

        cardLayout = new CardLayout();
        setLayout(cardLayout);
        this.mainFrame = inputMainFrame;


        // Khởi tạo các panel cho từng chức năng
        JPanel userManagementPanel = new UserManagementPanel();
        JPanel loginHistoryPanel = new LoginHistoryPanel();
        JPanel chatGroupPanel = new ChatGroupPanel();
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

