package org.example.Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class loginHistoryModel {
    private int login_id;
    private int user_id;
    private Timestamp login_time;

    public loginHistoryModel(int initLogin_id, int initUser_id, Timestamp initLogin_time) {
        this.login_id = initLogin_id;
        this.user_id = initUser_id;
        this.login_time = initLogin_time;
    }

    public static Object[][] getAllLoginHistory() {
        String query = " select lh.login_time, eu.username, eu.account_name" +
                       " from login_history lh " +
                       " join end_user eu on eu.user_id = lh.user_id";
        List<Object[]> loginHistoryList = new ArrayList<>();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    java.sql.Timestamp login_time = rs.getTimestamp("login_time");
                    String username = rs.getString("username");
                    String account_name = rs.getString("account_name");
                    loginHistoryList.add(new Object[]{login_time.toString().split("\\.")[0], username, account_name});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Kiểm tra danh sách bạn bè
        if (loginHistoryList.isEmpty()) {
            return new Object[0][0]; // Không có bạn bè
        }

        // Chuyển danh sách thành mảng 2 chiều
        return loginHistoryList.toArray(new Object[0][]);
    }

    public static Object[][] getLoginHistoryOfUsername(String username) {
        String checkUserQuery = "SELECT user_id FROM end_user WHERE username LIKE ?";
        String loginHistoryQuery = "SELECT eu.username, lh.login_time FROM login_history lh JOIN end_user eu ON lh.user_id = eu.user_id WHERE eu.username LIKE ?";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement checkUserStmt = conn.prepareStatement(checkUserQuery);
             PreparedStatement loginHistoryStmt = conn.prepareStatement(loginHistoryQuery)) {

            // Kiểm tra sự tồn tại của username
            checkUserStmt.setString(1, username + "%");
            try (ResultSet rs = checkUserStmt.executeQuery()) {
                if (!rs.next()) {
                    // Username không tồn tại
                    return null; // Trả về null để báo lỗi trong xử lý trên giao diện
                }
            }

            // Nếu username tồn tại, kiểm tra login history
            loginHistoryStmt.setString(1, username + "%");
            try (ResultSet rs = loginHistoryStmt.executeQuery()) {
                List<Object[]> loginHistoryList = new ArrayList<>();
                while (rs.next()) {
                    String rsUsername = rs.getString("username");
                    Timestamp loginTime = rs.getTimestamp("login_time");
                    loginHistoryList.add(new Object[]{rsUsername, loginTime.toString().split("\\.")[0]});
                }

                if (loginHistoryList.isEmpty()) {
                    // Username tồn tại nhưng không có lịch sử đăng nhập
                    return new Object[0][0]; // Trả về mảng rỗng
                }

                // Chuyển danh sách thành mảng 2 chiều
                return loginHistoryList.toArray(new Object[0][]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu xảy ra lỗi
        }
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
            System.out.println("Lỗi loginHistoryModel: getNewestYear");
            e.printStackTrace();
        }

        return newestYear;
    }



}
