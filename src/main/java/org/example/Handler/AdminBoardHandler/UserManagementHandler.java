package org.example.Handler.AdminBoardHandler;

import org.example.GUI.AdminBoard.UserManagementPanel;
import org.example.Model.endUserModel;
import org.example.Model.loginHistoryModel;
import org.example.Model.userFriendModel;

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

        // Reload function
        userManagement.components.reloadBtn.addActionListener(e -> handleReloadBtn());

        // Delete function
        userManagement.deleteComponents.deleteButton.addActionListener(e -> handleDeleteButton());
        userManagement.deleteComponents.submitDeleteBtn.addActionListener(e -> handleSubmitDelete());
        userManagement.deleteComponents.cancelDeleteBtn.addActionListener(e -> handleCancelDelete());

        // Add function
        userManagement.addComponents.addButton.addActionListener(e -> handleAddButton());
        userManagement.addComponents.cancelAddButton.addActionListener(e -> handleCancelAddButton());
        userManagement.addComponents.submitAddButton.addActionListener(e -> handleSubmitAddButton());

        // Search function
        userManagement.searchComponents.searchBtn.addActionListener(e -> handleSearchButton());
        userManagement.searchComponents.submitUsernameBtn.addActionListener(e -> handleSubmitUsernameBtn());
        userManagement.searchComponents.submitAccountnameBtn.addActionListener(e -> handleSubmitAccountnameBtn());
        userManagement.searchComponents.submitStateBtn.addActionListener(e -> handleSubmitStateBtn());
        userManagement.searchComponents.cancelSearchButton.addActionListener(e -> handleCancelSearchButton());

        // Update function
        userManagement.updateComponents.updateButton.addActionListener(e -> handleUpdateButton());
        userManagement.updateComponents.cancelUpdateButton.addActionListener(e -> handleCancelUpdateButton());
        userManagement.updateComponents.usernameOkUpdateButton.addActionListener(e -> handleUsernameOKUpdateButton());
        userManagement.updateComponents.confirmUpdateButton.addActionListener(e -> handleConfirmUpdateButton());

        // Lock function
        userManagement.lockComponents.lockButton.addActionListener(e -> handleLockButton());
        userManagement.lockComponents.submitUnlockButton.addActionListener(e -> handleSubmitUnlockButton());
        userManagement.lockComponents.cancelLockButton.addActionListener(e -> handleCancelLockButton());
        userManagement.lockComponents.submitLockButton.addActionListener(e -> handleSubmitLockButton());

        // Login history function
        userManagement.loginHistoryComponents.submitLoginHistoryBtn.addActionListener(e -> handleSubmitLoginHistoryBtn());
        userManagement.loginHistoryComponents.cancelLoginHistoryBtn.addActionListener(e -> handleCancelLoginHistoryBtn());
        userManagement.loginHistoryComponents.loginHistoryBtn.addActionListener(e -> handleLoginHistoryBtn());

        // Friend function
        userManagement.friendComponents.friendBtn.addActionListener(e -> handleFriendBtn());
        userManagement.friendComponents.cancelFriendBtn.addActionListener(e -> handleCancelFriendBtn());
        userManagement.friendComponents.submitFriendBtn.addActionListener(e -> handleSubmitFriendBtn());

        // Update-pass function
        userManagement.updatePassComponents.cancelUpdatePassBtn.addActionListener(e -> handleCancelUpdatePassBtn());
        userManagement.updatePassComponents.submitUpdatePassBtn.addActionListener(e -> handleSubmitUpdatePassBtn());
        userManagement.updatePassComponents.updatePassword.addActionListener(e -> handleUpdatePassBtn());
        loadUserData();
    }

    private void handleReloadBtn() {
        loadUserData();
    }

    private void loadUserData() {
        Object[][] userData = endUserModel.getAllUser();
        userManagement.updateTableData(userManagement.components.tableModel, userData);
    }



    private void handleDeleteButton() {
        userManagement.deleteComponents.deleteDialog.setVisible(true);
    }

    private void handleCancelDelete() {
        userManagement.deleteComponents.deleteDialog.setVisible(false);
    }

    private void handleSubmitDelete() {
        String username = userManagement.deleteComponents.textFieldDeleteBtn.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username không thể để trống.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (endUserModel.deleteUser(username) > 0) {
                loadUserData();
            }
        }
    }

    private void handleAddButton() {
        userManagement.addComponents.addDialog.setVisible(true);
    }

    private void handleCancelAddButton() {
        userManagement.addComponents.addDialog.setVisible(false);
    }

    private void handleSubmitAddButton() {
        // Lấy dữ liệu từ các JTextField
        String username = userManagement.addComponents.usernameFieldAddButton.getText();
        String password = userManagement.addComponents.passFieldAddButton.getText();
        String accountName = userManagement.addComponents.accountnameFieldAddButton.getText();
        String dob = userManagement.addComponents.dobFieldAddButton.getText();
        String address = userManagement.addComponents.addressFieldAddButton.getText();
        String gender = userManagement.addComponents.genderFieldAddButton.getText();
        String email = userManagement.addComponents.emailFieldAddButton.getText();

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
        userManagement.searchComponents.searchDialog.setVisible(false);
    }

    private void handleSearchButton() {
        userManagement.searchComponents.searchDialog.setVisible(true);
    }

    private void handleSubmitUsernameBtn() {
        // Lấy dữ liệu từ JTextField
        String username = userManagement.searchComponents.usernameField.getText().trim();

        // Kiểm tra nếu người dùng không nhập gì
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập một tên đăng nhập.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Gọi hàm tìm kiếm trong cơ sở dữ liệu
        Object[][] userData = endUserModel.searchUserByUsername(username);

        // Kiểm tra kết quả trả về
        if (userData != null) {
            // Hiển thị thông tin người dùng (có thể cập nhật vào giao diện hoặc hiện thông báo)
            if (userData.length > 0) {
                JOptionPane.showMessageDialog(null, "Tìm thấy người dùng thành công", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
                userManagement.updateTableData(userManagement.components.tableModel, userData);
            }
            else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy người dùng", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            // Thông báo nếu không tìm thấy người dùng
            JOptionPane.showMessageDialog(null, "Lỗi trong truy vấn database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSubmitAccountnameBtn() {
        // Lấy dữ liệu từ JTextField
        String accountname = userManagement.searchComponents.accountnameField.getText().trim();

        // Kiểm tra nếu người dùng không nhập gì
        if (accountname.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập một họ tên.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Gọi hàm tìm kiếm trong cơ sở dữ liệu
        Object[][] userData = endUserModel.searchUserByAccountName(accountname);

        // Kiểm tra kết quả trả về
        if (userData.length > 0) {
            JOptionPane.showMessageDialog(null, "Tìm kiếm thành công.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
            userManagement.updateTableData(userManagement.components.tableModel, userData);
        }
        else {
            // Thông báo nếu không tìm thấy người dùng
            JOptionPane.showMessageDialog(null, "Không tìm thấy người dùng.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleSubmitStateBtn() {
        String selectedOption = (String) userManagement.searchComponents.option.getSelectedItem();

        if (selectedOption == null) {
            JOptionPane.showMessageDialog(null, "Lỗi selectedOption, không được để trống.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (selectedOption.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lỗi selectedOption, không được để trống.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[][] data = endUserModel.searchUserByState(selectedOption);

        if (data.length > 0) {
            JOptionPane.showMessageDialog(null, "Tìm thấy thành công.", "Thông tin", JOptionPane.INFORMATION_MESSAGE);
            userManagement.updateTableData(userManagement.components.tableModel, data);
        }
        else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy.", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    private void handleUpdateButton() {
        userManagement.updateComponents.updateUserDialog.setVisible(true);
    }

    private void handleCancelUpdateButton() {
        userManagement.updateComponents.updateUserDialog.setVisible(false);
    }

    private void handleUsernameOKUpdateButton() {
        String username = userManagement.updateComponents.usernameFieldUpdateButton.getText();

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
        String curUsername = userManagement.updateComponents.usernameFieldUpdateButton.getText();
        String newAccountName = userManagement.updateComponents.accountNameUpdateField.getText();
        String newDOB = userManagement.updateComponents.dobUpdateField.getText();
        String newAddress = userManagement.updateComponents.addressUpdateField.getText();
        String newGender = userManagement.updateComponents.genderUpdateField.getText();
        String newEmail = userManagement.updateComponents.emailUpdateField.getText();

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

    private void handleCancelLockButton() {
        userManagement.lockComponents.lockDialog.setVisible(false);
    }

    private void handleLockButton() {
        userManagement.lockComponents.lockDialog.setVisible(true);
    }

    private void handleSubmitLockButton() {
        String username = userManagement.lockComponents.userNameTextField.getText();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phải nhập tên đăng nhập.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (endUserModel.lockUser(username) > 0) {
            loadUserData();
        }
    }

    private void handleSubmitUnlockButton() {
        String username = userManagement.lockComponents.userNameTextField.getText();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phải nhập tên đăng nhập.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (endUserModel.unLockUser(username) > 0) {
            loadUserData();
        }
    }

    private void handleLoginHistoryBtn() {
        userManagement.loginHistoryComponents.loginHistoryDialog.setVisible(true);
    }

    private void handleSubmitLoginHistoryBtn() {
        String username = userManagement.loginHistoryComponents.usernameTextField.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phải nhập tên đăng nhập.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Object[][] loginHistoryData = loginHistoryModel.getLoginHistoryOfUsername(username);

            if (loginHistoryData == null) {
                // Tên đăng nhập không tồn tại
                JOptionPane.showMessageDialog(null, "Tên đăng nhập không tồn tại trong database.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (loginHistoryData.length == 0) {
                // Tên đăng nhập không có lịch sử đăng nhập
                JOptionPane.showMessageDialog(null, "Người dùng này không có lịch sử đăng nhập.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Hiển thị dữ liệu
                JOptionPane.showMessageDialog(null, "Tìm kiếm lịch sử đăng nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                userManagement.updateTableData(userManagement.components.tableModelLogin, loginHistoryData);
            }
        }
    }


    private void handleCancelLoginHistoryBtn() {
        userManagement.loginHistoryComponents.loginHistoryDialog.setVisible(false);
    }

    private void handleFriendBtn() {
        userManagement.friendComponents.friendDialog.setVisible(true);
    }

    private void handleCancelFriendBtn() {
        userManagement.friendComponents.friendDialog.setVisible(false);
    }

    private void handleSubmitFriendBtn() {
        // Lấy tên đăng nhập từ giao diện
        String username = userManagement.friendComponents.usernameTextField.getText().trim();

        if (username.isEmpty()) {
            // Thông báo lỗi nếu tên đăng nhập bị để trống
            JOptionPane.showMessageDialog(null, "Phải nhập tên đăng nhập.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (!endUserModel.checkIfUserExists(username)) {
            // Thông báo lỗi nếu tên đăng nhập không tồn tại
            JOptionPane.showMessageDialog(null, "Tên đăng nhập không tồn tại trong hệ thống.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
         // Lấy danh sách bạn bè từ hàm getFriendOfUsername
        Object[][] friendsData = userFriendModel.getFriendOfUsername(username);

        if (friendsData != null) {
            if (friendsData.length == 0) {
                // Thông báo nếu không có bạn bè
                JOptionPane.showMessageDialog(null, "Người dùng này không có bạn bè.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                // Hiển thị danh sách bạn bè
                JOptionPane.showMessageDialog(null, "Tìm kiếm danh sách bạn bè thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                userManagement.updateTableData(userManagement.components.tableModelFriend, friendsData);
            }
        }
    }

    private void handleCancelUpdatePassBtn() {
        userManagement.updatePassComponents.updatePassDialog.setVisible(false);
    }

    private void handleUpdatePassBtn() {
        userManagement.updatePassComponents.updatePassDialog.setVisible(true);
    }

    private void handleSubmitUpdatePassBtn() {
        String username = userManagement.updatePassComponents.usernameTextField.getText();
        char[] oldPassArray = userManagement.updatePassComponents.oldPassTextField.getPassword();
        char[] newPassArray = userManagement.updatePassComponents.newPassTextField.getPassword();

        // Chuyển mảng char[] thành String để so sánh (nên xóa mảng sau khi sử dụng để bảo mật)
        String oldPass = new String(oldPassArray);
        String newPass = new String(newPassArray);

        // Kiểm tra nếu username rỗng
        if (username.isEmpty() || oldPass.isEmpty() || newPass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phải nhập đầy đủ thông tin.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Dừng hàm nếu username rỗng
        }

        // Kiểm tra nếu user tồn tại trong hệ thống
        if (!endUserModel.checkIfUserExists(username)) {
            JOptionPane.showMessageDialog(null, "Tên đăng nhập không tồn tại.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Dừng hàm nếu username không tồn tại
        }

        // Kiểm tra mật khẩu cũ
        if (!endUserModel.checkOldPassword(username, oldPass)) {
            JOptionPane.showMessageDialog(null, "Mật khẩu cũ không đúng.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Dừng hàm nếu mật khẩu cũ không đúng
        }

        // Cập nhật mật khẩu mới
        if (endUserModel.updatePassword(username, newPass)) {
            JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu thất bại.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Xóa mảng char[] sau khi sử dụng để bảo mật
        java.util.Arrays.fill(oldPassArray, '0');
        java.util.Arrays.fill(newPassArray, '0');
    }



}
