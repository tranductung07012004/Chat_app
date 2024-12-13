package org.example.Handler.AuthHandler;

import org.example.GUI.Auth.*;
import org.example.GUI.MainFrameGUI;
import org.example.Model.endUserModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;


public class RegisterHandler implements ActionListener {
    private final RegisterGUI registerScreen;
    private final MainFrameGUI mainFrame;
    public static final String ADMIN_CODE = "12345"; // Example admin code


    public RegisterHandler(RegisterGUI inputRegisterScreen, MainFrameGUI inputMainFrame) {
        this.registerScreen = inputRegisterScreen;
        this.mainFrame = inputMainFrame;

        registerScreen.getRegisterBtn().addActionListener(this);
        registerScreen.getGoBackToLoginBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerScreen.getRegisterBtn()) {
            // Lấy dữ liệu từ form
            String username = registerScreen.getUsernameField().getText().trim();
            String fullName = registerScreen.getFullNameField().getText().trim();
            String address = registerScreen.getAddressField().getText().trim();
            String gender = getSelectedGender().trim();
            String email = registerScreen.getEmailField().getText().trim();
            String dobText = registerScreen.getDobField().getText().trim();
            String password = new String(registerScreen.getPasswordField().getPassword()).trim();
            String confirmPassword = new String(registerScreen.getConfirmPasswordField().getPassword()).trim();
            String adminCode = registerScreen.getAdminCodeField().getText().trim();

            // Kiểm tra dữ liệu hợp lệ
            if (validateForm(username, fullName, address, gender, email, dobText, password, confirmPassword)) {
                Date dob = parseDate(dobText);
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                boolean isAdmin= Objects.equals(adminCode, ADMIN_CODE);
                System.out.println(isAdmin);

                // Tạo đối tượng endUserModel
                endUserModel user = new endUserModel(
                        0,  // Chưa có ID, sẽ được tự động sinh trong DB
                        username,
                        password,
                        fullName,
                        dob,
                        address,
                        gender,
                        isAdmin,  // isAdmin mặc định là false (không phải admin)
                        email,
                        false,  // online mặc định là false
                        false,  // blockedAccountByAdmin mặc định là false
                        currentTimestamp
                );

                // Thêm người dùng vào cơ sở dữ liệu
                boolean success = endUserModel.addUser(user);

                if (success) {
                    JOptionPane.showMessageDialog(registerScreen, "Registration successful!");
                    mainFrame.showLoginPanel();
                } else {
                    JOptionPane.showMessageDialog(registerScreen, "Registration failed. Please try again.");
                }
            }
        }
        else if (e.getSource() == registerScreen.getGoBackToLoginBtn()) {
            mainFrame.showLoginPanel();
        }
    }

    private boolean validateForm(String username, String fullName, String address, String gender, String email, String dobText, String password, String confirmPassword) {


        // Check if any required field is empty
        if (username.isEmpty() || fullName.isEmpty() || address.isEmpty() || gender.isEmpty() || email.isEmpty() || dobText.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(registerScreen, "Hãy điền đầy đủ các thông tin.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (endUserModel.checkIfUserExists(username)) {
            JOptionPane.showMessageDialog(null, "Username đã tồn tại, hãy chọn một username khác.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!username.matches("[a-zA-Z0-9_]+")) {
            JOptionPane.showMessageDialog(null, "Username không được có khoảng trắng và có dấu.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(registerScreen, "Xác nhận lại mật khẩu.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // check if email is not unique
        if (endUserModel.checkIfEmailExists(email)) {
            JOptionPane.showMessageDialog(registerScreen, "Email đã được sử dụng cho tài khoản khác, hãy sử dụng email khác.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate email format
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(registerScreen, "Format của email không đúng!.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate date of birth format
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFormatter.setLenient(false);
        try {
            java.util.Date parsedDate = dateFormatter.parse(dobText);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(registerScreen, "Format của Date không đúng! Hãy dùng dd/MM/yyyy.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // All checks passed
        return true;
    }

    private Date parseDate(String dobText) {
        // Phân tích chuỗi ngày tháng (định dạng dd/mm/yyyy) thành đối tượng Date
        String[] parts = dobText.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]) - 1; // Tháng trong Java bắt đầu từ 0
        int year = Integer.parseInt(parts[2]);
        return new Date(year - 1900, month, day); // Cách tạo Date trong Java cũ
    }

    private String getSelectedGender() {
        // Kiểm tra xem nút radio nào được chọn
        if (registerScreen.getMaleOption().isSelected()) {
            return "Nam";
        } else if (registerScreen.getFemaleOption().isSelected()) {
            return "Nữ";
        } else {
            return "Khác";
        }
    }
}
