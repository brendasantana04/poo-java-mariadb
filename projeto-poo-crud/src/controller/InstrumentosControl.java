package controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Instrumentos;
import services.InstrumentosDAO;
import services.InstrumentosDAOImpl;
import tools.PlaylistException;

import java.util.List;

public class InstrumentosControl {

    private ObservableList<Instrumentos> lista = FXCollections.observableArrayList();
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty tipo = new SimpleStringProperty("");
    private SimpleDoubleProperty preco = new SimpleDoubleProperty(0.0);
    private StringProperty nome = new SimpleStringProperty("");

    private InstrumentosDAO instrumentosDAO;
    private int count;

    public InstrumentosControl() throws PlaylistException {
        instrumentosDAO = new InstrumentosDAOImpl();
    }

    // Converte os dados da tela para uma entidade Instrumentos
    public Instrumentos paraEntidade() {
        Instrumentos i = new Instrumentos();
        i.setId(id.get());
        i.setTipo(tipo.get());
        i.setPreco(preco.get());
        i.setNome(nome.get());
        return i;
    }

    // Limpa os campos da tela
    public void limparTudo() {
        id.set(0);
        tipo.set("");
        preco.set(0.0);
        nome.set("");
    }

    // Preenche os campos da tela com os dados de uma entidade Instrumentos
    public void paraTela(Instrumentos i) {
        if (i != null) {
            id.set(i.getId());
            tipo.set(i.getTipo());
            preco.set(i.getPreco());
            nome.set(i.getNome());
        }
    }

    // Salva um novo instrumento ou atualiza um existente
    public void gravar() throws Exception {
        Instrumentos instrumentos = paraEntidade();
        if (instrumentos.getId() == 0) {  // Caso de novo registro
            this.count += 1;
            instrumentos.setId(this.count);
            instrumentosDAO.inserir(instrumentos);  // Chama o DAO para inserir o instrumento
        } else {  // Caso de atualização
            instrumentosDAO.atualizar(instrumentos);
        }
        pesquisarTodos();  // Atualiza a lista após a inserção
    }

    // Remove um instrumento e atualiza a lista
    public void excluir(Instrumentos i) throws PlaylistException {
        instrumentosDAO.remover(i);
        pesquisarTodos();
    }

    // Pesquisa instrumentos pelo nome
    public void pesquisar() throws PlaylistException {
        lista.clear();
        List<Instrumentos> resultado = instrumentosDAO.pesquisarPorNome(nome.get());
        lista.addAll(resultado);
    }

    // Pesquisa todos os instrumentos
    public void pesquisarTodos() throws PlaylistException {
        lista.clear();
        List<Instrumentos> resultado = instrumentosDAO.pesquisarPorNome(""); // Traz todos os instrumentos
        lista.addAll(resultado);
    }

    // Propriedades para vinculação com a interface (JavaFX)
    public IntegerProperty idProperty() {
        return this.id;
    }

    public StringProperty tipoProperty() {
        return this.tipo;
    }

    public StringProperty nomeProperty() {
        return this.nome;
    }

    public SimpleDoubleProperty precoProperty() {
        return this.preco;
    }

    public ObservableList<Instrumentos> getLista() {
        return this.lista;
    }
}
