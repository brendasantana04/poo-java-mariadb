package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Instrumentos;
import tools.DatabaseConnection;
import tools.PlaylistException;

public class InstrumentosDAOImpl implements InstrumentosDAO{
    
    //definindo conexão com a maria
    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/musicadb";
    private final static String DB_USER = "haine";
    private final static String DB_PASS = "1234";

    private Connection con = null;
    

    
    public InstrumentosDAOImpl() throws PlaylistException { 
        try { 
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) { 
            throw new PlaylistException("Erro ao conectar ao banco de dados");
        }
    }

    //inserindo dados no banco de dados
    @Override
    public void inserir (Instrumentos i) throws PlaylistException {
        try (Connection con = DatabaseConnection.getConnection()){
            System.out.println("entrando no inserir");
            String sql = "INSERT INTO instrumentos (tipo, preco, nome) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = con.prepareStatement(sql)){
                stmt.setString(3, i.getTipo());
                stmt.setDouble(2, i.getPreco());
                stmt.setString(1, i.getNome());

                int rowsAffected = stmt.executeUpdate();
                System.out.println("Linhas que foram inseridas: " + rowsAffected);
            } catch (Exception e) {
                System.out.println("Erro ao preparar o statement: " + e.getMessage());
                throw new PlaylistException(e);
            }
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados 1: " + e.getMessage());
            throw new PlaylistException("Erro ao inserir música");
        }
    }

    @Override
    public void atualizar(Instrumentos i) throws PlaylistException {
        try {
            String SQL = """
                UPDATE instrumentos SET nome = ?, preco = ?, tipo = ?
                WHERE id = ?
                """;
        PreparedStatement stm = con.prepareStatement(SQL);

        stm.setString('1', i.getNome());
        stm.setDouble('2', i.getPreco());
        stm.setString('3', i.getTipo());
        stm.setInt('4', i.getId());
        stm.executeUpdate();
        } catch (SQLException e) {
            throw new PlaylistException("Erro ao tentar atualizar a musica. " + e);
        }
    }

    @Override
    public void remover(Instrumentos i) throws PlaylistException {
        try { 
            String SQL = """
                    DELETE FROM instrumentos WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setInt(1, i.getId());
            stm.executeUpdate();
        } catch (SQLException e) { 
            throw new PlaylistException("Erro ao remover música");
        }
    }

    @Override
    public List<Instrumentos> pesquisarPorNome(String nome) throws PlaylistException {
        List<Instrumentos> lista = new ArrayList<>();
        try { 
            String SQL = """
                    SELECT * FROM instrumentos WHERE nome LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) { 
                Instrumentos i = new Instrumentos();
                i.setId(rs.getInt("id"));
                i.setNome(rs.getString("nome"));
                i.setPreco(rs.getDouble("preco"));
                i.setTipo(rs.getString("tipo"));
                lista.add(i);
            }
        } catch (SQLException e) { 
            throw new PlaylistException("Erro ao pesquisar músicas por título");
        }
        return lista;
    }
    
}
