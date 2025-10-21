package org.example.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final Properties props = loadProperties();

    private static Properties loadProperties() {
        Properties p = new Properties();
        try (InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "application.properties not found"
                );
            }
            p.load(input);

        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config", e);
        }
        return p;
    }

    private static final String URL = props.getProperty("db.url");
    private static final String USER = props.getProperty("db.user");
    private static final String PASSWORD = props.getProperty("db.password");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}