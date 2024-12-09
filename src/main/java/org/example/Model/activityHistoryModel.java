package org.example.Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static Object[][] getActiveUserInfo(java.sql.Date startDate, java.sql.Date endDate) {
        String query = """
            WITH LoginCount AS (
                SELECT lh.user_id, COUNT(*) AS login_count
                FROM login_history lh
                WHERE lh.login_time BETWEEN ? AND ?
                GROUP BY lh.user_id
            ),
            PeopleChatted AS (
                SELECT mou.from_user AS user_id, COUNT(DISTINCT mou.to_user) AS people_chatted
                FROM message_of_user mou
                WHERE mou.chat_time BETWEEN ? AND ?
                GROUP BY mou.from_user
            ),
            GroupChatted AS (
                SELECT mog.from_user AS user_id, COUNT(DISTINCT mog.to_group) AS group_chatted
                FROM message_of_group mog
                WHERE mog.chat_time BETWEEN ? AND ?
                GROUP BY mog.from_user
            )
            SELECT 
                eu.username, 
                eu.account_name, 
                eu.time_registered, 
                COALESCE(lc.login_count, 0) AS login_count, 
                COALESCE(pc.people_chatted, 0) AS people_chatted, 
                COALESCE(gc.group_chatted, 0) AS group_chatted
            FROM end_user eu
            LEFT JOIN LoginCount lc ON eu.user_id = lc.user_id
            LEFT JOIN PeopleChatted pc ON eu.user_id = pc.user_id
            LEFT JOIN GroupChatted gc ON eu.user_id = gc.user_id
            WHERE COALESCE(lc.login_count, 0) > 0 
                        OR COALESCE(pc.people_chatted, 0) > 0 
                        OR COALESCE(gc.group_chatted, 0) > 0;
            """;

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị cho các tham số
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);
            stmt.setDate(5, startDate);
            stmt.setDate(6, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> resultList = new ArrayList<>();

                while (rs.next()) {
                    String username = rs.getString("username");
                    String accountName = rs.getString("account_name");
                    Timestamp timeRegistered = rs.getTimestamp("time_registered");
                    int loginCount = rs.getInt("login_count");
                    int peopleChatted = rs.getInt("people_chatted");
                    int groupChatted = rs.getInt("group_chatted");
                    int totalActivity = loginCount + peopleChatted + groupChatted;

                    // Thêm dữ liệu vào danh sách
                    resultList.add(new Object[]{
                            username, accountName, timeRegistered.toString().split("\\.")[0], loginCount, peopleChatted, groupChatted, totalActivity
                    });
                }

                // Chuyển danh sách thành mảng 2 chiều
                return resultList.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }


    public static Object[][] getActiveUserByActivityNum(String selectedOption, int num) {
        // Xây dựng câu truy vấn SQL
        String query = """ 
                WITH LoginCount AS (
                     SELECT lh.user_id, COUNT(*) AS login_count
                     FROM login_history lh
                     WHERE lh.login_time BETWEEN ? AND ?
                     GROUP BY lh.user_id
                    ),
                PeopleChatted AS (
                     SELECT mou.from_user AS user_id, COUNT(DISTINCT mou.to_user) AS people_chatted
                     FROM message_of_user mou
                     WHERE mou.chat_time BETWEEN ? AND ?
                     GROUP BY mou.from_user
                    ),
                GroupChatted AS (
                     SELECT mog.from_user AS user_id, COUNT(DISTINCT mog.to_group) AS group_chatted
                     FROM message_of_group mog
                     WHERE mog.chat_time BETWEEN ? AND ?
                     GROUP BY mog.from_user
                    )
                 SELECT
                     eu.username,
                     eu.account_name,
                     eu.time_registered,
                     COALESCE(lc.login_count, 0) AS login_count,
                     COALESCE(pc.people_chatted, 0) AS people_chatted,
                     COALESCE(gc.group_chatted, 0) AS group_chatted
                 FROM end_user eu
                 LEFT JOIN LoginCount lc ON eu.user_id = lc.user_id
                 LEFT JOIN PeopleChatted pc ON eu.user_id = pc.user_id
                 LEFT JOIN GroupChatted gc ON eu.user_id = gc.user_id
                 WHERE COALESCE(lc.login_count, 0) > 0 
                        OR COALESCE(pc.people_chatted, 0) > 0 
                        OR COALESCE(gc.group_chatted, 0) > 0;
                """;
        java.sql.Date sqlStartDate = Date.valueOf("1970-01-01");
        java.sql.Date sqlEndDate = Date.valueOf("9999-12-31");

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị cho các tham số truy vấn
            stmt.setDate(1, sqlStartDate);
            stmt.setDate(2, sqlEndDate);
            stmt.setDate(3, sqlStartDate);
            stmt.setDate(4, sqlEndDate);
            stmt.setDate(5, sqlStartDate);
            stmt.setDate(6, sqlEndDate);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> resultList = new ArrayList<>();

                while (rs.next()) {
                    String username = rs.getString("username");
                    Timestamp time_registered = rs.getTimestamp("time_registered");
                    String accountName = rs.getString("account_name");
                    int loginCount = rs.getInt("login_count");
                    int userMessageCount = rs.getInt("people_chatted");
                    int groupMessageCount = rs.getInt("group_chatted");
                    int sumActivityCount = loginCount + userMessageCount + groupMessageCount;

                    if ((selectedOption.equals(">") && sumActivityCount <= num)
                            || (selectedOption.equals("<") && sumActivityCount >= num)
                            || (selectedOption.equals("=") && sumActivityCount != num)) {
                        continue;
                    }
                    // Thêm dữ liệu vào danh sách
                    resultList.add(new Object[]{username, accountName, time_registered.toString().split("\\.")[0], loginCount, userMessageCount, groupMessageCount, sumActivityCount});
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
                WITH LoginCount AS (
                     SELECT lh.user_id, COUNT(*) AS login_count
                     FROM login_history lh
                     WHERE lh.login_time BETWEEN ? AND ?
                     GROUP BY lh.user_id
                    ),
                PeopleChatted AS (
                     SELECT mou.from_user AS user_id, COUNT(DISTINCT mou.to_user) AS people_chatted
                     FROM message_of_user mou
                     WHERE mou.chat_time BETWEEN ? AND ?
                     GROUP BY mou.from_user
                    ),
                GroupChatted AS (
                     SELECT mog.from_user AS user_id, COUNT(DISTINCT mog.to_group) AS group_chatted
                     FROM message_of_group mog
                     WHERE mog.chat_time BETWEEN ? AND ?
                     GROUP BY mog.from_user
                    )
                 SELECT
                     eu.username,
                     eu.account_name,
                     eu.time_registered,
                     COALESCE(lc.login_count, 0) AS login_count,
                     COALESCE(pc.people_chatted, 0) AS people_chatted,
                     COALESCE(gc.group_chatted, 0) AS group_chatted
                 FROM end_user eu
                 LEFT JOIN LoginCount lc ON eu.user_id = lc.user_id
                 LEFT JOIN PeopleChatted pc ON eu.user_id = pc.user_id
                 LEFT JOIN GroupChatted gc ON eu.user_id = gc.user_id
                 WHERE eu.account_name LIKE ?;
                """;

        java.sql.Date sqlStartDate = Date.valueOf("1970-01-01");
        java.sql.Date sqlEndDate = Date.valueOf("9999-12-31");

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị cho các tham số truy vấn
            stmt.setDate(1, sqlStartDate);
            stmt.setDate(2, sqlEndDate);
            stmt.setDate(3, sqlStartDate);
            stmt.setDate(4, sqlEndDate);
            stmt.setDate(5, sqlStartDate);
            stmt.setDate(6, sqlEndDate);
            stmt.setString(7, accountName + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> resultList = new ArrayList<>();

                while (rs.next()) {
                    String username = rs.getString("username");
                    Timestamp time_registered = rs.getTimestamp("time_registered");
                    String accountname = rs.getString("account_name");
                    int loginCount = rs.getInt("login_count");
                    int userMessageCount = rs.getInt("people_chatted");
                    int groupMessageCount = rs.getInt("group_chatted");
                    if (loginCount > 0 || userMessageCount > 0 || groupMessageCount > 0) {
                        int sumActivityCount = loginCount + userMessageCount + groupMessageCount;
                        resultList.add(new Object[]{username, accountname, time_registered.toString().split("\\.")[0], loginCount, userMessageCount, groupMessageCount, sumActivityCount});
                    }
                }

                // Chuyển đổi danh sách thành mảng 2 chiều Object
                return resultList.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][0]; // Trả về mảng rỗng nếu xảy ra lỗi
        }
    }

    public static Object[] countAppUsersByYear(String year) {
        // Mảng lưu trữ số lượng đăng ký mới cho từng tháng
        Object[] monthlyCounts = new Object[12];

        // Truy vấn SQL lấy dữ liệu theo từng tháng
        String query = """
            SELECT 
                EXTRACT(MONTH FROM login_time) AS month,
                COUNT(DISTINCT user_id) AS user_count
            FROM login_history
            WHERE EXTRACT(YEAR FROM login_time) = ?
            GROUP BY EXTRACT(MONTH FROM login_time)
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
                    int count = rs.getInt("user_count");
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
            SELECT MAX(EXTRACT(YEAR FROM login_time)) AS newest_year
            FROM login_history;
        """;

        String newestYear = null; // Giá trị mặc định nếu không tìm thấy

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                newestYear = rs.getString("newest_year");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi activityHistoryModel: getNewestYear");
            e.printStackTrace();
        }

        return newestYear;
    }
}
