package org.example.Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class activityHistoryModel {
    private long activty_id;
    private int user_id;
    private Date time_period;
    private int open_app;
    private int peopleChatted;
    private int groupChatted;

    public activityHistoryModel(long initActivty_id,
                                int initUser_id,
                                Date initTime_period,
                                int initOpen_app,
                                int initPeopleChatted,
                                int initGroupChatted) {
        this.activty_id = initActivty_id;
        this.user_id = initUser_id;
        this.time_period = initTime_period;
        this.open_app = initOpen_app;
        this.peopleChatted = initPeopleChatted;
        this.groupChatted = initGroupChatted;
    }

    public static Object[][] getActiveUserInfo(java.sql.Date sqlStartDate, java.sql.Date sqlEndDate) {
        // Xây dựng câu truy vấn SQL
        String query = """ 
                select eu.username, eu.time_registered, eu.account_name, ah.open_app, ah.peoplechatted, ah.groupchatted
                from activity_history ah 
                left join end_user eu on ah.user_id = eu.user_id
                where ah.time_period between ? and ?;
                """;

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị cho các tham số truy vấn
            stmt.setDate(1, sqlStartDate);
            stmt.setDate(2, sqlEndDate);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> resultList = new ArrayList<>();

                while (rs.next()) {
                    String username = rs.getString("username");
                    Timestamp time_registered = rs.getTimestamp("time_registered");
                    String accountName = rs.getString("account_name");
                    int loginCount = rs.getInt("open_app");
                    int userMessageCount = rs.getInt("peoplechatted");
                    int groupMessageCount = rs.getInt("groupchatted");
                    int sumActivityCount = loginCount + userMessageCount + groupMessageCount;


                    // Thêm dữ liệu vào danh sách
                    resultList.add(new Object[]{username, accountName, time_registered.toString(), loginCount, userMessageCount, groupMessageCount, sumActivityCount});
                }

                // Chuyển đổi danh sách thành mảng 2 chiều Object
                return resultList.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][0]; // Trả về mảng rỗng nếu xảy ra lỗi
        }
    }

    public static Object[][] getActiveUserByActivityNum(String selectedOption, int num) {
        // Xây dựng câu truy vấn SQL
        String query = """ 
                select eu.username, eu.time_registered, eu.account_name, ah.open_app, ah.peoplechatted, ah.groupchatted
                from activity_history ah 
                left join end_user eu on ah.user_id = eu.user_id
                where ah.time_period between ? and ?;
                """;
        java.sql.Date sqlStartDate = Date.valueOf("1970-01-01");
        java.sql.Date sqlEndDate = Date.valueOf("9999-12-31");

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị cho các tham số truy vấn
            stmt.setDate(1, sqlStartDate);
            stmt.setDate(2, sqlEndDate);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> resultList = new ArrayList<>();

                while (rs.next()) {
                    String username = rs.getString("username");
                    Timestamp time_registered = rs.getTimestamp("time_registered");
                    String accountName = rs.getString("account_name");
                    int loginCount = rs.getInt("open_app");
                    int userMessageCount = rs.getInt("peoplechatted");
                    int groupMessageCount = rs.getInt("groupchatted");
                    int sumActivityCount = loginCount + userMessageCount + groupMessageCount;

                    if ((selectedOption.equals(">") && sumActivityCount <= num)
                            || (selectedOption.equals("<") && sumActivityCount >= num)
                            || (selectedOption.equals("=") && sumActivityCount != num)) {
                        continue;
                    }
                    // Thêm dữ liệu vào danh sách
                    resultList.add(new Object[]{username, accountName, time_registered.toString(), loginCount, userMessageCount, groupMessageCount, sumActivityCount});
                }

                // Chuyển đổi danh sách thành mảng 2 chiều Object
                return resultList.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][0]; // Trả về mảng rỗng nếu xảy ra lỗi
        }
    }

    public static Object[][] getActiveUserByAccountName(String accountName) {
        // Xây dựng câu truy vấn SQL
        String query = """ 
                select eu.username, eu.time_registered, eu.account_name, ah.open_app, ah.peoplechatted, ah.groupchatted
                from activity_history ah 
                left join end_user eu on ah.user_id = eu.user_id
                where eu.account_name LIKE ?;
                """;


        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị cho các tham số truy vấn
            stmt.setString(1, accountName + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> resultList = new ArrayList<>();

                while (rs.next()) {
                    String username = rs.getString("username");
                    Timestamp time_registered = rs.getTimestamp("time_registered");
                    String accountname = rs.getString("account_name");
                    int loginCount = rs.getInt("open_app");
                    int userMessageCount = rs.getInt("peoplechatted");
                    int groupMessageCount = rs.getInt("groupchatted");
                    int sumActivityCount = loginCount + userMessageCount + groupMessageCount;

                    // Thêm dữ liệu vào danh sách
                    resultList.add(new Object[]{username, accountname, time_registered.toString(), loginCount, userMessageCount, groupMessageCount, sumActivityCount});
                }

                // Chuyển đổi danh sách thành mảng 2 chiều Object
                return resultList.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][0]; // Trả về mảng rỗng nếu xảy ra lỗi
        }
    }
}
