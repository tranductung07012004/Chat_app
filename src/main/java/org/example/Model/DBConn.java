package org.example.Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private static final String URL = "jdbc:postgresql://localhost:5433/chat_app";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "111111";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }
}


