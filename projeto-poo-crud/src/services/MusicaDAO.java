package services;

import java.util.List;

import models.Musica;
import tools.PlaylistException;

public interface MusicaDAO {
    void inserir( Musica c ) throws PlaylistException;
    void atualizar( Musica c ) throws PlaylistException;
    void remover( Musica c ) throws PlaylistException;
    List<Musica> pesquisarPorTitulo( String nome ) throws PlaylistException;
}
