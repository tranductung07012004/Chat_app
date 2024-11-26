package org.example.Model;
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
}
