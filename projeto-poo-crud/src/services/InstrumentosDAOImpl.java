package services;

import java.sql.Connection;
import java.sql.PreparedStatement;



import models.Instrumentos;
import tools.DatabaseConnection;
import tools.PlaylistException;

public class InstrumentosDAOImpl {
    
    private Connection con = null;

    //inserindo dados no banco de dados
    public void inserir (Instrumentos instrumentos) throws PlaylistException {
        try (Connection con = DatabaseConnection.getConnection()){
            System.out.println("entrando no inserir");
            String sql = "INSERT INTO musicas (nome, preco, tipo) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = con.prepareStatement(sql)){
                stmt.setString(1, instrumentos.getNome());
                stmt.setDouble(1, instrumentos.getPreco());
                stmt.setString(1, instrumentos.getTipo());

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

        // @Override
        // public void atualizar(Instrumentos instrumentos) throws PlaylistExceptio{

        // }
    }
}
