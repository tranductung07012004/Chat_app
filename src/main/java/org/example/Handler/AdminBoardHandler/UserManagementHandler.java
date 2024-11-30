package org.example.Handler.AdminBoardHandler;

import org.example.GUI.AdminBoard.UserManagementPanel;
import org.example.Model.endUserModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class UserManagementHandler implements ActionListener {
    private UserManagementPanel userManagement;
    private Map<Object, Runnable> actionMap;

    public UserManagementHandler(UserManagementPanel inputUserManagement) {
        this.userManagement = inputUserManagement;
        this.actionMap = new HashMap<>();

        // Đăng ký các hành động
        actionMap.put(userManagement.components.deleteButton, this::handleDeleteButton);
        actionMap.put(userManagement.components.submitDeleteBtn, this::handleSubmitDelete);
        actionMap.put(userManagement.components.cancelDeleteBtn, this::handleCancelDelete);
        actionMap.put(userManagement.components.addButton, this::handleAddButton);
        actionMap.put(userManagement.components.cancelAddButton, this::handleCancelAddButton);
        actionMap.put(userManagement.components.submitAddButton, this::handleSubmitAddButton);

        actionMap.put(userManagement.components.searchBtn, this::handleSearchButton);
        actionMap.put(userManagement.components.submitSearchButton, this::handleSubmitSearchButton);
        actionMap.put(userManagement.components.cancelSearchButton, this::handleCancelSearchButton);


        // Gắn sự kiện
        userManagement.components.deleteButton.addActionListener(this);
        userManagement.components.submitDeleteBtn.addActionListener(this);
        userManagement.components.cancelDeleteBtn.addActionListener(this);

        userManagement.components.addButton.addActionListener(this);
        userManagement.components.cancelAddButton.addActionListener(this);
        userManagement.components.submitAddButton.addActionListener(this);

        userManagement.components.searchBtn.addActionListener(this);
        userManagement.components.submitSearchButton.addActionListener(this);
        userManagement.components.cancelSearchButton.addActionListener(this);

        userManagement.components.friendBtn.addActionListener(this);
        userManagement.components.lockButton.addActionListener(this);
        userManagement.components.loginHistoryBtn.addActionListener(this);
        userManagement.components.updateButton.addActionListener(this);

        loadUserData();
    }

    private void loadUserData() {
        Object[][] userData = endUserModel.getAllUser();
        userManagement.updateTableData(userData);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (actionMap.containsKey(source)) {
            actionMap.get(source).run();
        }
    }

    private void handleDeleteButton() {
        userManagement.components.deleteDialog.setVisible(true);
    }

    private void handleCancelDelete() {
        userManagement.components.deleteDialog.setVisible(false);
    }

    private void handleSubmitDelete() {
        String username = userManagement.components.textFieldDeleteBtn.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username không thể để trống.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (endUserModel.deleteUser(username) > 0) {
                loadUserData();
            }
        }
    }

    private void handleAddButton() {
        userManagement.components.addDialog.setVisible(true);
    }

    private void handleCancelAddButton() {
        userManagement.components.addDialog.setVisible(false);
    }

    private void handleSubmitAddButton() {
        // Lấy dữ liệu từ các JTextField
        String username = userManagement.components.usernameFieldAddButton.getText();
        String password = userManagement.components.passFieldAddButton.getText();
        String accountName = userManagement.components.accountnameFieldAddButton.getText();
        String dob = userManagement.components.dobFieldAddButton.getText();
        String address = userManagement.components.addressFieldAddButton.getText();
        String gender = userManagement.components.genderFieldAddButton.getText();
        String email = userManagement.components.emailFieldAddButton.getText();

        // Kiểm tra nếu có trường nào để trống
        if (username.isEmpty() || password.isEmpty() || accountName.isEmpty() || dob.isEmpty() ||
                address.isEmpty() || gender.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phải điền đầy đủ thông tin.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra định dạng ngày sinh (yyyy/mm/dd)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false); // Không cho phép ngày không hợp lệ (ví dụ: 2024/02/30)
        java.util.Date utilDate;
        try {
            utilDate = dateFormat.parse(dob);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "DOB phải có dạng là yyyy/mm/dd.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi java.util.Date thành java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        // Tiến hành thêm người dùng nếu tất cả điều kiện đều hợp lệ
        if (endUserModel.addUser(username, password, accountName, sqlDate, address, gender, email) > 0) {
            loadUserData(); // Tải lại dữ liệu người dùng nếu thêm thành công
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add user. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleCancelSearchButton() {
        userManagement.components.searchDialog.setVisible(false);
    }

    private void handleSearchButton() {
        userManagement.components.searchDialog.setVisible(true);
    }

    private void handleSubmitSearchButton() {
        // Lấy dữ liệu từ JTextField
        String username = userManagement.components.textFieldSearchDialog.getText();

        // Kiểm tra nếu người dùng không nhập gì
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập một tên đăng nhập.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Gọi hàm tìm kiếm trong cơ sở dữ liệu
        Object[] userData = endUserModel.searchUserByUsername(username);

        // Kiểm tra kết quả trả về
        if (userData != null) {
            // Hiển thị thông tin người dùng (có thể cập nhật vào giao diện hoặc hiện thông báo)
            JOptionPane.showMessageDialog(null,
                    "User found successfully!!!\n" +
                            "Username: " + userData[0] + "\n" +
                            "Account Name: " + userData[1] + "\n" +
                            "Address: " + userData[2] + "\n" +
                            "Date of Birth: " + userData[3] + "\n" +
                            "Gender: " + userData[4] + "\n" +
                            "Email: " + userData[5],
                    "User Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Thông báo nếu không tìm thấy người dùng
            JOptionPane.showMessageDialog(null, "Không tìm thấy người dùng.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
