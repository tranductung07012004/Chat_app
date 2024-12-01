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

public class UserManagementHandler  {
    private UserManagementPanel userManagement;

    public UserManagementHandler(UserManagementPanel inputUserManagement) {
        this.userManagement = inputUserManagement;

        // Gắn sự kiện trực tiếp với lambda expressions
        userManagement.components.deleteButton.addActionListener(e -> handleDeleteButton());
        userManagement.components.submitDeleteBtn.addActionListener(e -> handleSubmitDelete());
        userManagement.components.cancelDeleteBtn.addActionListener(e -> handleCancelDelete());

        userManagement.components.addButton.addActionListener(e -> handleAddButton());
        userManagement.components.cancelAddButton.addActionListener(e -> handleCancelAddButton());
        userManagement.components.submitAddButton.addActionListener(e -> handleSubmitAddButton());

        userManagement.components.searchBtn.addActionListener(e -> handleSearchButton());
        userManagement.components.submitSearchButton.addActionListener(e -> handleSubmitSearchButton());
        userManagement.components.cancelSearchButton.addActionListener(e -> handleCancelSearchButton());

        userManagement.components.updateButton.addActionListener(e -> handleUpdateButton());
        userManagement.components.cancelUpdateButton.addActionListener(e -> handleCancelUpdateButton());
        userManagement.components.usernameOkUpdateButton.addActionListener(e -> handleUsernameOKUpdateButton());
        userManagement.components.confirmUpdateButton.addActionListener(e -> handleConfirmUpdateButton());

        //userManagement.components.friendBtn.addActionListener(e -> handleFriendButton());
        //userManagement.components.lockButton.addActionListener(e -> handleLockButton());
        //userManagement.components.loginHistoryBtn.addActionListener(e -> handleLoginHistoryButton());


        loadUserData();
    }

    private void loadUserData() {
        Object[][] userData = endUserModel.getAllUser();
        userManagement.updateTableData(userData);
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

    private void handleUpdateButton() {
        userManagement.components.updateUserDialog.setVisible(true);
    }

    private void handleCancelUpdateButton() {
        userManagement.components.updateUserDialog.setVisible(false);
    }

    private void handleUsernameOKUpdateButton() {
        String username = userManagement.components.usernameFieldUpdateButton.getText();

        // Kiểm tra nếu người dùng không nhập gì
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập một tên đăng nhập.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra nếu tên người dùng tồn tại hay không
        if (endUserModel.checkIfUserExists(username)) {
            JOptionPane.showMessageDialog(null, "Người dùng tồn tại, có thể cập nhật.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Người dùng không tồn tại, hãy thêm vào.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void handleConfirmUpdateButton() {
        String curUsername = userManagement.components.usernameFieldUpdateButton.getText();
        String newAccountName = userManagement.components.accountNameUpdateField.getText();
        String newDOB = userManagement.components.dobUpdateField.getText();
        String newAddress = userManagement.components.addressUpdateField.getText();
        String newGender = userManagement.components.genderUpdateField.getText();
        String newEmail = userManagement.components.emailUpdateField.getText();

        if (curUsername.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phải nhập tên đăng nhập hiện tại trước.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Nếu tất cả các trường đều để trống thì không update
        if (newAccountName.isEmpty() && newDOB.isEmpty() && newAddress.isEmpty() &&
                newGender.isEmpty() && newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phải điền một thông tin nào đó.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra định dạng ngày sinh (yyyy/mm/dd)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false); // Không cho phép ngày không hợp lệ (ví dụ: 2024/02/30)
        java.util.Date utilDate;
        try {
            utilDate = dateFormat.parse(newDOB);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "DOB phải có dạng là yyyy/mm/dd.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển đổi java.util.Date thành java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        // Cập nhật thông tin người dùng nếu tất cả điều kiện đều hợp lệ
        if (endUserModel.updateUser(curUsername, newAccountName, sqlDate, newAddress, newGender, newEmail) > 0) {
            loadUserData(); // Tải lại dữ liệu người dùng nếu thêm thành công
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add user. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
