package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/musicadb";
        private static final String USER = "haine";
        private static final String PASSWORD = "1234";

         static {
            try {
                System.out.println("Tentando conectar...");
                Class.forName("org.mariadb.jdbc.Driver");
                System.out.println("Driver JDBC do MariaDB carregado com sucesso.");
            } catch (ClassNotFoundException e) {
                System.out.println("Erro ao carregar o driver JDBC do MariaDB: " + e.getMessage());
            }
        }

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
}

