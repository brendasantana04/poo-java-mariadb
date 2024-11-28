package services;

import java.util.List;

import models.Instrumentos;
import tools.PlaylistException;

public interface InstrumentosDAO {
    
    //metodos da aplicacao
    void inserir (Instrumentos i) throws PlaylistException;
    void atualizar (Instrumentos i) throws PlaylistException;
    void remover (Instrumentos i) throws PlaylistException;

    List<Instrumentos> pesquisarPorNome (String nome) throws PlaylistException;

}
