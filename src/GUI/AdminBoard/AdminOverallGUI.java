package GUI.AdminBoard;

import GUI.MainFrameGUI;

import javax.swing.*;


public class AdminOverallGUI extends JPanel {

    public AdminOverallGUI(MainFrameGUI mainFrame) {


        JButton userManagement = new JButton("User management");
        userManagement.setBounds(30, 50, 200, 40);

        JButton loginHistory = new JButton("Login history");
        loginHistory.setBounds(240, 50, 200, 40);

        JButton chatGroup = new JButton("Chat group");
        chatGroup.setBounds(450, 50, 200, 40);

        JButton spamCheck = new JButton("Spam check");
        spamCheck.setBounds(30, 100, 200, 40);

        JButton newBie = new JButton("New register");
        newBie.setBounds(240, 100, 200, 40);
    
        JButton friendNetwork = new JButton("Friend Network");
        friendNetwork.setBounds(450, 100, 200, 40);

        JButton usageStatistic = new JButton("Usage statistics");
        usageStatistic.setBounds(30, 150, 200, 40);

        add(userManagement);
        add(loginHistory);
        add(chatGroup);
        add(spamCheck);
        add(newBie);
        add(friendNetwork);
        add(usageStatistic);


        setSize(700, 500);
        setLayout(null);
    }
}
