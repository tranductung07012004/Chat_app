package org.example.Handler;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configLoader {
    private static configLoader instance;
    private Properties properties;

    // Private constructor to prevent direct instantiation
    private configLoader() {
        properties = new Properties();
        try {
            // Đường dẫn cố định tới file cấu hình
            String configPath = "config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Singleton: Ensure only one instance of configLoader is created
    public static configLoader getInstance() {
        if (instance == null) {
            synchronized (configLoader.class) {
                if (instance == null) {
                    instance = new configLoader();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Overloaded method to handle default values
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException | NullPointerException e) {
            // Nếu key không tồn tại hoặc giá trị không thể chuyển thành số, trả về defaultValue
            return defaultValue;
        }
    }

}
