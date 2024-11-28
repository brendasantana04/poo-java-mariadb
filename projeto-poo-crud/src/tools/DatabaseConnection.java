package tools;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/musicadb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "haine";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws Exception {
        // Opcional para drivers modernos:
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

