package controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Musica;
import services.MusicaDAO;
import services.MusicaDAOImpl;
import tools.PlaylistException;

import java.util.List;

public class MusicaControl {

    private ObservableList<Musica> lista = FXCollections.observableArrayList();
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty titulo = new SimpleStringProperty("");
    private StringProperty artista = new SimpleStringProperty("");
    private StringProperty album = new SimpleStringProperty("");
    private StringProperty duracao = new SimpleStringProperty("");

    private MusicaDAO musicaDAO;
    private int count;

    public MusicaControl() throws PlaylistException {
        musicaDAO = new MusicaDAOImpl();
    }

    // Converte os dados da tela para uma entidade Musica
    public Musica paraEntidade() {
        Musica m = new Musica();
        m.setId(id.get());
        m.setTitulo(titulo.get());
        m.setArtista(artista.get());
        m.setAlbum(album.get());
        m.setDuracao(duracao.get());
        return m;
    }

    // Limpa os campos da tela
    public void limparTudo() {
        id.set(0);
        titulo.set("");
        artista.set("");
        album.set("");
        duracao.set("");
    }

    // Preenche os campos da tela com os dados de uma entidade Musica
    public void paraTela(Musica m) {
        if (m != null) {
            id.set(m.getId());
            titulo.set(m.getTitulo());
            artista.set(m.getArtista());
            album.set(m.getAlbum());
            duracao.set(m.getDuracao());
        }
    }

    // Salva uma nova música ou atualiza uma existente
    public void gravar() throws Exception {
        Musica musica = paraEntidade();
        if (musica.getId() == 0) {  // Caso de novo registro
            this.count += 1;
        musica.setId(this.count);
        musicaDAO.inserir(musica);  // Chama o DAO para inserir a música
        } else {  // Caso de atualização
            musicaDAO.atualizar(musica);
        }
        pesquisarTodos();  // Atualiza a lista após a inserção
    }


    // Remove uma música e atualiza a lista
    public void excluir(Musica m) throws PlaylistException {
        musicaDAO.remover(m);
        pesquisarTodos();
    }

    // Pesquisa músicas pelo título
    public void pesquisar() throws PlaylistException {
        lista.clear();
        List<Musica> resultado = musicaDAO.pesquisarPorTitulo(titulo.get());
        lista.addAll(resultado);
    }

    // Pesquisa todas as músicas
    public void pesquisarTodos() throws PlaylistException {
        lista.clear();
        List<Musica> resultado = musicaDAO.pesquisarPorTitulo(""); 
        lista.addAll(resultado);
    }

    // Propriedades para vinculação com a interface 
    public IntegerProperty idProperty() {
        return this.id;
    }

    public StringProperty tituloProperty() {
        return this.titulo;
    }

    public StringProperty artistaProperty() {
        return this.artista;
    }

    public StringProperty albumProperty() {
        return this.album;
    }

    public StringProperty duracaoProperty() {
        return this.duracao;
    }

    public ObservableList<Musica> getLista() {
        return this.lista;
    }
}

