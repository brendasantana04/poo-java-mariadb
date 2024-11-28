package services;

import models.Musica;
import tools.DatabaseConnection;
import tools.PlaylistException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicaDAOImpl implements MusicaDAO {


    //definindo conexão com a maria
    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/musicadb";
    private final static String DB_USER = "haine";
    private final static String DB_PASS = "1234";

    private Connection con = null;
    

    public MusicaDAOImpl() throws PlaylistException { 
        try { 
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) { 
            throw new PlaylistException("Erro ao conectar ao banco de dados");
        }
    }

    @Override
    public void inserir(Musica musica) throws Exception {
        try (Connection con = DatabaseConnection.getConnection()) {
            System.out.println("Entrando aqui");
            String sql = "INSERT INTO musicas (titulo, artista, album, duracao) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, musica.getTitulo());
                stmt.setString(2, musica.getArtista());
                stmt.setString(3, musica.getAlbum());
                stmt.setString(4, musica.getDuracao());
                
                int rowsAffected = stmt.executeUpdate(); // Verifica quantas linhas foram afetadas
                System.out.println("Linhas inseridas: " + rowsAffected);
            } catch (SQLException e) {
                System.out.println("Erro ao preparar o statement: " + e.getMessage());
                throw new PlaylistException(e);
            }
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados 1: " + e.getMessage());
            throw new PlaylistException("Erro ao inserir música");
        }
    }


    @Override
    public void atualizar(Musica m) throws PlaylistException {
        try { 
            String SQL = """
                    UPDATE musicas SET titulo = ?, artista = ?, album = ?, duracao = ?
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, m.getTitulo());
            stm.setString(2, m.getArtista());
            stm.setString(3, m.getAlbum());
            stm.setString(5, m.getDuracao());
            stm.setInt(6, m.getId());
            stm.executeUpdate();
        } catch (SQLException e) { 
            throw new PlaylistException("Erro ao atualizar música");
        }        
    }

    @Override
    public void remover(Musica m) throws PlaylistException {
        try { 
            String SQL = """
                    DELETE FROM musicas WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setInt(1, m.getId());
            stm.executeUpdate();
        } catch (SQLException e) { 
            throw new PlaylistException("Erro ao remover música");
        }
    }

    @Override
    public List<Musica> pesquisarPorTitulo(String titulo) throws PlaylistException {
        List<Musica> lista = new ArrayList<>();
        try { 
            String SQL = """
                    SELECT * FROM musicas WHERE titulo LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, "%" + titulo + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) { 
                Musica m = new Musica();
                m.setId(rs.getInt("id"));
                m.setTitulo(rs.getString("titulo"));
                m.setArtista(rs.getString("artista"));
                m.setAlbum(rs.getString("album"));
                m.setDuracao(rs.getString("duracao"));
                lista.add(m);
            }
        } catch (SQLException e) { 
            throw new PlaylistException("Erro ao pesquisar músicas por título");
        }
        return lista;
    }
    
}
