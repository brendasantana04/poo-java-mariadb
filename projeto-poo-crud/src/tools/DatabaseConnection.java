package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // // Variáveis de conexão (ajuste com suas credenciais de banco)
    // private static final String DB_URL = "jdbc:mysql://localhost:3306/seu_banco_de_dados";
    // private static final String USER = "root";
    // private static final String PASSWORD = "senha";

    // public static Connection getConnection() throws SQLException {
    //     try {
    //         // Carregar o driver do banco de dados
    //         Class.forName("com.mysql.cj.jdbc.Driver");
    //         return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    //     } catch (ClassNotFoundException e) {
    //         throw new SQLException("Driver JDBC não encontrado.", e);
    //     }
    // }


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
