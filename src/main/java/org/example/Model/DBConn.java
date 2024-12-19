package org.example.Model;

import org.example.Handler.configLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    public static Connection getConnection() throws SQLException {
        // Lấy cấu hình từ configLoader
        configLoader config = configLoader.getInstance();

        String URL = config.getProperty("db.url");
        String USER_NAME = config.getProperty("db.username");
        String PASSWORD = config.getProperty("db.password");

        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }

}
