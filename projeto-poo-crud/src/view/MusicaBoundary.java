package view;

import java.util.Optional;

import controller.MusicaControl;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import models.Musica;
import tools.PlaylistException;
import tools.Tela;

public class MusicaBoundary implements Tela {

    private Label lblId = new Label("");
    private TextField txtTitulo = new TextField();
    private TextField txtArtista = new TextField();
    private TextField txtAlbum = new TextField();
    private TextField txtDuracao = new TextField(); // Para minutos ou segundos

    private MusicaControl control = null;
    private TableView<Musica> tableView = new TableView<>();

    @Override
    public Pane render() {
        try {
            control = new MusicaControl();
        } catch (PlaylistException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }
        BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction(e -> {
            try {
                control.gravar();
            } catch (PlaylistException err) {
                new Alert(Alert.AlertType.ERROR, "Erro ao gravar a música", ButtonType.OK).showAndWait();
            } catch (Exception e1) {
                            e1.printStackTrace();
                        }
            tableView.refresh();
        });

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> {
            try {
                control.pesquisar();
            } catch (PlaylistException err) {
                new Alert(Alert.AlertType.ERROR, "Erro ao pesquisar músicas", ButtonType.OK).showAndWait();
            }
        });

        Button btnNovo = new Button("Limpar tudo");
        btnNovo.setOnAction(e -> control.limparTudo());

        // paneForm.add(new Label("Id: "), 0, 0);
        // paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Título: "), 0, 1);
        paneForm.add(txtTitulo, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Artista: "), 0, 2);
        paneForm.add(txtArtista, 1, 2);
        paneForm.add(new Label("Álbum: "), 0, 3);
        paneForm.add(txtAlbum, 1, 3);
        paneForm.add(new Label("Duração: "), 0, 5);
        paneForm.add(txtDuracao, 1, 5);

        paneForm.add(btnGravar, 0, 6);
        paneForm.add(btnPesquisar, 1, 6);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop(paneForm);
        panePrincipal.setCenter(tableView);

        return panePrincipal;
    }

    
    public void gerarColunas() {
        TableColumn<Musica, Integer> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Musica, String> col2 = new TableColumn<>("Título");
        col2.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Musica, String> col3 = new TableColumn<>("Artista");
        col3.setCellValueFactory(new PropertyValueFactory<>("artista"));

        TableColumn<Musica, String> col4 = new TableColumn<>("Álbum");
        col4.setCellValueFactory(new PropertyValueFactory<>("album"));

        TableColumn<Musica, String> col6 = new TableColumn<>("Duração");
        col6.setCellValueFactory(new PropertyValueFactory<>("duracao"));

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                    if (novo != null) {
                        System.out.println("Selecionado: " + novo.getTitulo());
                        control.paraTela(novo);
                    }
                });

        Callback<TableColumn<Musica, Void>, TableCell<Musica, Void>> cb =
                new Callback<>() {
                    @Override
                    public TableCell<Musica, Void> call(TableColumn<Musica, Void> param) {
                        return new TableCell<>() {
                            final Button btnApagar = new Button("Apagar");

                            {
                                btnApagar.setOnAction(e -> {
                                    Musica musica = tableView.getItems().get(getIndex());
                                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, 
                                            "Tem certeza que deseja apagar a música?", 
                                            ButtonType.YES, ButtonType.NO);
                                    Optional<ButtonType> result = confirm.showAndWait();
                                    if (result.isPresent() && result.get() == ButtonType.YES) {
                                        try {
                                            control.excluir(musica);
                                        } catch (PlaylistException err) {
                                            new Alert(Alert.AlertType.ERROR, "Erro ao excluir a música", ButtonType.OK).showAndWait();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (!empty) {
                                    setGraphic(btnApagar);
                                } else {
                                    setGraphic(null);
                                }
                            }
                        };
                    }
                };

        TableColumn<Musica, Void> col7 = new TableColumn<>("Ação");
        col7.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col6, col7);
        tableView.setItems(control.getLista());
    }

    public void ligacoes() {
        control.idProperty().addListener((obs, antigo, novo) -> lblId.setText(String.valueOf(novo)));
        Bindings.bindBidirectional(control.tituloProperty(), txtTitulo.textProperty());
        Bindings.bindBidirectional(control.artistaProperty(), txtArtista.textProperty());
        Bindings.bindBidirectional(control.albumProperty(), txtAlbum.textProperty());
        Bindings.bindBidirectional(control.duracaoProperty(), txtDuracao.textProperty());


    }
}

