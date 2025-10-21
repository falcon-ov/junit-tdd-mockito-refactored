package db;

import org.example.db.DatabaseConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

class DatabaseConnectionTest {

    @BeforeAll
    static void setup() {
        // Устанавливаем тестовые ENV переменные
        System.setProperty("DB_URL", "jdbc:mysql://localhost:3306/testdb");
        System.setProperty("DB_USER", "root");
        System.setProperty("DB_PASSWORD", "admin");
    }

    @Test
    void testConnectionNotNull() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        assertNotNull(conn, "Connection should not be null");
        conn.close();
    }

    @Test
    void testConnectionIsValid() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        assertTrue(conn.isValid(2), "Connection should be valid");
        conn.close();
    }

    @Test
    void testConnectionNotClosed() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        assertFalse(conn.isClosed(), "Connection should be open");
        conn.close();
    }

}