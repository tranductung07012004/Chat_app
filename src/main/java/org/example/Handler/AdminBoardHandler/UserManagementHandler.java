package org.example.Handler.AdminBoardHandler;

import org.example.GUI.AdminBoard.UserManagementPanel;
import org.example.GUI.MainFrameGUI;
import org.example.Model.endUserModel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class UserManagementHandler implements ActionListener {
    private UserManagementPanel userManagement;

    public UserManagementHandler(UserManagementPanel inputUserManagement) {
        this.userManagement = inputUserManagement;

        userManagement.getAddButton().addActionListener(this);
        userManagement.getDeleteButton().addActionListener(this);
        userManagement.getFriendBtn().addActionListener(this);
        userManagement.getLockButton().addActionListener(this);
        userManagement.getLoginHistoryBtn().addActionListener(this);
        userManagement.getSearchBtn().addActionListener(this);
        userManagement.getUpdateButton().addActionListener(this);

        loadUserData();
    }

    private void loadUserData() {
        Object[][] userData = endUserModel.getAllUser();

        userManagement.updateTableData(userData);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userManagement.getAddButton()) {
            System.out.println("haha");
        }
    }

}
