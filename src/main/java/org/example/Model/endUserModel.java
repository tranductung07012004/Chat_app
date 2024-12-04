package org.example.Model;
import java.sql.*;


import java.util.ArrayList;
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

    public static Object[][] getAllUser() {
        List<Object[]> userList = new ArrayList<>();
        String query = "SELECT username, account_name, address, dob, gender, email, time_registered FROM end_user";

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

                userList.add(new Object[]{
                        username,
                        accountName,
                        address,
                        dob.toString(),
                        gender,
                        email,
                        timeRegistered.toString()
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

}
