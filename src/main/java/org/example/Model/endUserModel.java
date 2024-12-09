package org.example.Model;
import javax.swing.*;
import javax.swing.*;
import java.sql.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class endUserModel {
    private int user_id;
    private String username;
    private String password;
    private String account_name;
    private Date dob;
    private String address;
    private String gender;
    private String email;
    private boolean isAdmin;
    private boolean online;
    private boolean blockedAccountByAdmin;
    private Timestamp time_registered;

    public static String AdminSessionUsername;

    public endUserModel(int initID,
                        String initUsername,
                        String init_password,
                        String initAccount_name,

                        Date initDob,
                        String initAddress,
                        String initGender,
                        boolean initAdmin,
                        String initEmail,
                        boolean initOnline,
                        boolean initBlocked,
                        Timestamp initTime) {
        this.user_id = initID;
        this.username = initUsername;
        this.password=init_password;
        this.account_name = initAccount_name;
        this.dob = initDob;
        this.address = initAddress;
        this.gender = initGender;
        this.email = initEmail;
        this.online = initOnline;
        this.blockedAccountByAdmin = initBlocked;
        this.time_registered = initTime;
        this.isAdmin=initAdmin;
    }

    public static Object[][] filterRegisteredByDate(java.sql.Date sqlStartDate, java.sql.Date sqlEndDate) {
        String query = " SELECT eu.username, eu.account_name, eu.time_registered " +
                " FROM  end_user eu " +
                " WHERE eu.time_registered >= ? AND eu.time_registered <= ?";
        List<Object[]> resultList = new ArrayList<>();

        // Sử dụng try-with-resources để đảm bảo đóng kết nối
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán tham số cho câu lệnh SQL
            stmt.setDate(1, sqlStartDate);
            stmt.setDate(2, sqlEndDate);

            // Thực thi truy vấn và xử lý kết quả
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Lấy dữ liệu từ các cột
                    String userName = rs.getString("username");
                    String accountName = rs.getString("account_name");
                    Timestamp timeCreated = rs.getTimestamp("time_registered");

                    // Thêm dữ liệu vào danh sách
                    resultList.add(new Object[]{userName, accountName, timeCreated.toString()});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu xảy ra lỗi
        }

        // Chuyển danh sách thành mảng 2 chiều
        return resultList.toArray(new Object[0][]);
    }

    public static Object[][] getAllNewUserStatisticInfo() {
        List<Object[]> userList = new ArrayList<>();
        String query = "SELECT username, account_name, time_registered FROM end_user";

        // try-with-resources
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String accountName = rs.getString("account_name");
                Timestamp timeRegistered = rs.getTimestamp("time_registered");
                userList.add(new Object[]{username, accountName, timeRegistered.toString()});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Chuyển danh sách thành mảng 2D
        Object[][] userArray = new Object[userList.size()][];
        return userList.toArray(userArray);
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

    public static endUserModel getUserFromId(int ID) {
        String query = "SELECT * FROM end_user WHERE user_id = ?";
        endUserModel user = null;

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int user_id = rs.getInt("user_id");
                String username = rs.getString("username");
                String account_name = rs.getString("account_name");
                Date dob = rs.getDate("dob");
                String address = rs.getString("address");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                boolean isAdmin = rs.getBoolean("isAdmin");
                boolean online = rs.getBoolean("online");
                boolean blockedAccountByAdmin = rs.getBoolean("blockedAccountByAdmin");
                Timestamp time_registered = rs.getTimestamp("time_registered");
                String password = rs.getString("pass"); // Assuming password is stored here.

                // Create a new user model object and populate with data
                user = new endUserModel(
                        user_id, username, password, account_name, dob, address, gender,
                        isAdmin, email, online, blockedAccountByAdmin, time_registered
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    // Getter và Setter cho từng thuộc tính
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountName() {
        return account_name;
    }

    public void setAccountName(String account_name) {
        this.account_name = account_name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public String getPassword() {
        return password;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
        // Now update the online status in the database
        String updateQuery = "UPDATE end_user SET online = ? WHERE user_id = ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setBoolean(1, online);
            stmt.setInt(2, this.user_id); // Use the user_id of the current instance

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
            } else {
                System.out.println("Failed to update user online status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getBlockedAccountByAdmin() {
        return blockedAccountByAdmin;
    }

    public void setBlockedAccountByAdmin(boolean blockedAccountByAdmin) {
        this.blockedAccountByAdmin = blockedAccountByAdmin;
    }

    public Timestamp getTimeRegistered() {
        return time_registered;
    }
    public static boolean addUser(endUserModel user) {
        String query = "INSERT INTO end_user (username, account_name, dob, address, gender, email, isAdmin, online, blockedAccountByAdmin, time_registered, pass) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getAccountName());
            stmt.setDate(3, user.getDob());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getEmail());
            stmt.setBoolean(7, user.getIsAdmin());
            stmt.setBoolean(8, user.getOnline());
            stmt.setBoolean(9, user.getBlockedAccountByAdmin());
            stmt.setTimestamp(10, user.getTimeRegistered());
            stmt.setString(11, user.getPassword());


            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public static Object[][] getNewUserStatisticByAccountname(String accountName) {
        String query = "SELECT username, account_name, time_registered FROM end_user WHERE account_name LIKE ?";
        List<Object[]> userData = new ArrayList<>();

        // Sử dụng try-with-resources để đảm bảo đóng kết nối
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị tham số
            stmt.setString(1, accountName + "%");

            // Thực thi truy vấn
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Lấy dữ liệu từ kết quả truy vấn
                    String resultUsername = rs.getString("username");
                    String account_name = rs.getString("account_name");
                    Timestamp timeRegistered = rs.getTimestamp("time_registered");

                    // Gán dữ liệu vào mảng
                    userData.add(new Object[]{resultUsername, account_name, timeRegistered.toString()});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Chuyển danh sách thành mảng 2D
        Object[][] userArray = new Object[userData.size()][];
        return userData.toArray(userArray);
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
        String query = "UPDATE end_user SET blockedaccountbyadmin = ?, online = ? WHERE username = ?";

        int rowsAffected = 0;
        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set các tham số cho PreparedStatement
            stmt.setBoolean(1, true);
            stmt.setBoolean(2, false);
            stmt.setString(3, username);

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

    public static Object[] countNewRegistrationByYear(String year) {
        // Mảng lưu trữ số lượng đăng ký mới cho từng tháng
        Object[] monthlyCounts = new Object[12];

        // Truy vấn SQL lấy dữ liệu theo từng tháng
        String query = """
            SELECT 
                EXTRACT(MONTH FROM time_registered) AS month,
                COUNT(*) AS registration_count
            FROM end_user
            WHERE EXTRACT(YEAR FROM time_registered) = ?
            GROUP BY EXTRACT(MONTH FROM time_registered)
            ORDER BY month;
        """;

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị cho tham số
            int yearInt = Integer.parseInt(year); // Chuyển year từ String sang int
            stmt.setInt(1, yearInt); // Sử dụng setInt thay cho setString


            // Thực thi truy vấn
            try (ResultSet rs = stmt.executeQuery()) {
                // Khởi tạo mảng với giá trị mặc định là 0
                Arrays.fill(monthlyCounts, 0);

                // Lấy dữ liệu từ kết quả truy vấn
                while (rs.next()) {
                    int month = rs.getInt("month") - 1; // Chuyển thành chỉ số mảng (0-based index)
                    int count = rs.getInt("registration_count");
                    monthlyCounts[month] = count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthlyCounts;
    }

    public static String getNewestYear() {
        // Câu truy vấn SQL để lấy năm mới nhất
        String query = """
            SELECT MAX(EXTRACT(YEAR FROM time_registered)) AS newest_year
            FROM end_user;
        """;

        String newestYear = null; // Giá trị mặc định nếu không tìm thấy

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                newestYear = rs.getString("newest_year");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi endUserModel: getNewestYear");
            e.printStackTrace();
        }

        return newestYear;
    }

    public static Object[][] searchUserByState(String state) {
        StringBuilder query = new StringBuilder("SELECT username, account_name, address, dob, gender, email FROM end_user ");
        List<Object[]> userDataList = new ArrayList<>();

        // Sử dụng try-with-resources để đảm bảo đóng kết nối
        try (Connection conn = DBConn.getConnection();) {

            if (state.equals("online")) {
                query.append(" WHERE online = ? ");
            }
            else if (state.equals("blocked")) {
                query.append(" WHERE blockedaccountbyadmin = ? ");
            }

            PreparedStatement stmt = conn.prepareStatement(String.valueOf(query));
            // Gán giá trị tham số với ký tự đại diện
            stmt.setBoolean(1, true);

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

}
