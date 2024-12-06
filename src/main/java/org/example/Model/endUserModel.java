package org.example.Model;
import javax.swing.*;
import java.sql.*;


import java.util.ArrayList;
import java.util.List;

public class endUserModel {
    private int user_id;
    private String username;
    private String account_name;
    private Date dob;
    private String address;
    private String gender;
    private String email;
    private int isAdmin;
    private int online;
    private int blockedAccountByAdmin;
    private Timestamp time_registered;

    public endUserModel(int initID,
                        String initUsername,
                        String initAccount_name,
                        Date initDob,
                        String initAddress,
                        String initGender,
                        int initAdmin,
                        String initEmail,
                        int initOnline,
                        int initBlocked,
                        Timestamp initTime) {
        this.user_id = initID;
        this.username = initUsername;
        this.account_name = initAccount_name;
        this.dob = initDob;
        this.address = initAddress;
        this.gender = initGender;
        this.email = initEmail;
        this.online = initOnline;
        this.blockedAccountByAdmin = initBlocked;
        this.time_registered = initTime;
    }

    public static Object[][] getAllUser() {
        List<Object[]> userList = new ArrayList<>();
        String query = "SELECT username, account_name, address, dob, gender, email, time_registered, blockedaccountbyadmin FROM end_user";

        // try-with-resources
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String accountName = rs.getString("account_name");
                String address = rs.getString("address");
                Date dob = rs.getDate("dob");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                Timestamp timeRegistered = rs.getTimestamp("time_registered");
                boolean blocked = rs.getBoolean("blockedaccountbyadmin");

                String blockedString = blocked ? "True" : "False";
                userList.add(new Object[]{
                        username,
                        accountName,
                        address,
                        dob.toString(),
                        gender,
                        email,
                        timeRegistered.toString(),
                        blockedString
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Chuyển danh sách thành mảng 2D
        Object[][] userArray = new Object[userList.size()][];
        return userList.toArray(userArray);
    }

    public static int deleteUser(String username) {
        String query = "DELETE FROM end_user WHERE username = ?";
        int rowsAffected = 0;
        // try-with-resources để đảm bảo đóng kết nối sau khi sử dụng
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set tham số vào PreparedStatement
            stmt.setString(1, username);

            // Thực thi câu lệnh xóa
            rowsAffected = stmt.executeUpdate();

            // In ra số dòng bị ảnh hưởng (hoặc thông báo nếu không xóa được bản ghi nào)
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "User was deleted successfully.", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No username found.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        } catch (SQLException e) {
            System.err.println("An error occurred while trying to delete the user: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0 ? 1 : 0;
    }

    public static int addUser(String username, String password, String accountName, Date dob, String address, String gender, String email) {
        String query = "INSERT INTO end_user (username, pass, account_name, dob, address, gender, email, time_registered) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = 0;
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set tham số vào PreparedStatement
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, accountName);
            stmt.setDate(4, dob);
            stmt.setString(5, address);
            stmt.setString(6, gender);
            stmt.setString(7, email);
            stmt.setTimestamp(8, currentTime);

            // Thực thi câu lệnh thêm
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "User was added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add user. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.err.println("An error occurred while trying to add the user: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0 ? 1 : 0;
    }

    public static Object[][] searchUserByUsername(String username) {
        String query = "SELECT username, account_name, address, dob, gender, email FROM end_user WHERE username LIKE ?";
        List<Object[]> userDataList = new ArrayList<>();

        // Sử dụng try-with-resources để đảm bảo đóng kết nối
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị tham số với ký tự đại diện
            stmt.setString(1, username + "%");

            // Thực thi truy vấn
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Lấy dữ liệu từ kết quả truy vấn
                    String resultUsername = rs.getString("username");
                    String accountName = rs.getString("account_name");
                    String address = rs.getString("address");
                    Date dob = rs.getDate("dob");
                    String gender = rs.getString("gender");
                    String email = rs.getString("email");

                    // Gán dữ liệu vào danh sách
                    userDataList.add(new Object[]{resultUsername, accountName, address, dob.toString(), gender, email});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Chuyển danh sách thành mảng 2 chiều
        return userDataList.toArray(new Object[0][]);
    }


    public static Object[][] searchUserByAccountName(String accountname) {
        String query = "SELECT username, account_name, address, dob, gender, email, time_registered, blockedaccountbyadmin FROM end_user WHERE account_name LIKE ?";
        List<Object[]> userData = new ArrayList<>();

        // Sử dụng try-with-resources để đảm bảo đóng kết nối
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị tham số
            stmt.setString(1, accountname + "%");

            // Thực thi truy vấn
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Lấy dữ liệu từ kết quả truy vấn
                    String resultUsername = rs.getString("username");
                    String accountName = rs.getString("account_name");
                    String address = rs.getString("address");
                    Date dob = rs.getDate("dob");
                    String gender = rs.getString("gender");
                    String email = rs.getString("email");
                    Timestamp timeRegistered = rs.getTimestamp("time_registered");

                    // Gán dữ liệu vào mảng
                    userData.add(new Object[]{
                        resultUsername,
                        accountName,
                        address,
                        dob,
                        gender,
                        email,
                        timeRegistered.toString()
                    });
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Chuyển danh sách thành mảng 2D
        Object[][] userArray = new Object[userData.size()][];
        return userData.toArray(userArray);
    }

    public static boolean checkIfUserExists(String username) {
        String query = "SELECT 1 FROM end_user WHERE username LIKE ?";
        boolean exists = false;

        // Sử dụng try-with-resources để đảm bảo đóng kết nối
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị tham số
            stmt.setString(1, username + "%");

            // Thực thi truy vấn
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Nếu có dữ liệu trả về thì người dùng tồn tại
                    exists = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public static int updateUser(String curUsername, String newAccountName, Date sqlDate,
                                 String newAddress, String newGender, String newEmail) {
        String query = "UPDATE end_user SET account_name = ?, dob = ?, address = ?, gender = ?, email = ? " +
                "WHERE username = ?";
        int rowsAffected = 0;

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set các tham số cho PreparedStatement
            stmt.setString(1, newAccountName);
            stmt.setDate(2, sqlDate);
            stmt.setString(3, newAddress);
            stmt.setString(4, newGender);
            stmt.setString(5, newEmail);
            stmt.setString(6, curUsername);

            // Thực thi câu lệnh cập nhật
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "User information was updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update user information. User might not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.err.println("An error occurred while trying to update the user: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0 ? 1 : 0;
    }

    public static int lockUser(String username) {
        String query = "UPDATE end_user SET blockedaccountbyadmin = ? WHERE username = ?";

        int rowsAffected = 0;
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set các tham số cho PreparedStatement
            stmt.setBoolean(1, true);
            stmt.setString(2, username);

            // Thực thi câu lệnh cập nhật
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Người dùng đã bị khóa.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Không thể thực hiện tác vụ, có thể người dùng không tồn tại.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.err.println("An error occurred while trying to update the user: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0 ? 1 : 0;
    }

    public static int unLockUser(String username) {
        String query = "UPDATE end_user SET blockedaccountbyadmin = ? WHERE username = ?";

        int rowsAffected = 0;
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set các tham số cho PreparedStatement
            stmt.setBoolean(1, false);
            stmt.setString(2, username);

            // Thực thi câu lệnh cập nhật
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Người dùng đã được mở khóa.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Không thể thực hiện tác vụ, có thể người dùng không tồn tại.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.err.println("An error occurred while trying to update the user: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0 ? 1 : 0;
    }

    public static boolean checkOldPassword(String username, String oldPass) {
        String query = "SELECT pass FROM end_user WHERE username = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("pass");
                    System.out.println(storedPassword);
                    System.out.println(oldPass);
                    return storedPassword.equals(oldPass); // So sánh mật khẩu lưu trữ với mật khẩu cũ nhập vào
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu xảy ra lỗi hoặc không tìm thấy user
    }

    public static boolean updatePassword(String username, String newPass) {
        String query = "UPDATE end_user SET pass = ? WHERE username = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newPass);
            stmt.setString(2, username);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }




}
