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

    public static Object[] countAppUsersByYear(String year) {
        // Mảng lưu trữ số lượng đăng ký mới cho từng tháng
        Object[] monthlyCounts = new Object[12];

        // Truy vấn SQL lấy dữ liệu theo từng tháng
        String query = """
            SELECT 
                EXTRACT(MONTH FROM time_period) AS month,
                COUNT(DISTINCT user_id) AS user_count
            FROM activity_history
            WHERE open_app > 0 AND EXTRACT(YEAR FROM time_period) = ?
            GROUP BY EXTRACT(MONTH FROM time_period)
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
            SELECT MAX(EXTRACT(YEAR FROM time_period)) AS newest_year
            FROM activity_history;
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
