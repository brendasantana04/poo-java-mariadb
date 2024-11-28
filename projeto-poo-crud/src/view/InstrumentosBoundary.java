package view;

import java.util.Optional;

import controller.InstrumentosControl;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import models.Instrumentos;
import tools.PlaylistException;
import tools.Tela;

public class InstrumentosBoundary implements Tela {

    private Label lblId = new Label("");
    private TextField txtNome = new TextField();
    private TextField txtTipo = new TextField();
    private TextField txtPreco = new TextField();

    private InstrumentosControl control = null;
    private TableView<Instrumentos> tableView = new TableView<>();

    @Override
    public Pane render() {
        try {
            control = new InstrumentosControl();
        } catch (PlaylistException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }
        BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction(e -> {
            try {
                control.gravar();
            } catch (Exception err) {
                new Alert(Alert.AlertType.ERROR, "Erro ao gravar o instrumento", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> {
            try {
                control.pesquisar();
            } catch (PlaylistException err) {
                new Alert(Alert.AlertType.ERROR, "Erro ao pesquisar instrumentos", ButtonType.OK).showAndWait();
            }
        });

        Button btnNovo = new Button("Limpar tudo");
        btnNovo.setOnAction(e -> control.limparTudo());


        // paneForm.add(new Label("Id: "), 0, 0);
        // paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Nome: "), 0, 1);
        paneForm.add(txtNome, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Tipo: "), 0, 2);
        paneForm.add(txtTipo, 1, 2);
        paneForm.add(new Label("Preço: "), 0, 3);
        paneForm.add(txtPreco, 1, 3);

        paneForm.add(btnGravar, 0, 4);
        paneForm.add(btnPesquisar, 1, 4);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop(paneForm);
        panePrincipal.setCenter(tableView);

        return panePrincipal;
    }

    public void gerarColunas() {
        TableColumn<Instrumentos, Integer> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Instrumentos, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Instrumentos, String> col3 = new TableColumn<>("Tipo");
        col3.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Instrumentos, Double> col4 = new TableColumn<>("Preço");
        col4.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                    if (novo != null) {
                        System.out.println("Selecionado: " + novo.getNome());
                        control.paraTela(novo);
                    }
                });

        Callback<TableColumn<Instrumentos, Void>, TableCell<Instrumentos, Void>> cb =
                new Callback<>() {
                    @Override
                    public TableCell<Instrumentos, Void> call(TableColumn<Instrumentos, Void> param) {
                        return new TableCell<>() {
                            final Button btnApagar = new Button("Apagar");

                            {
                                btnApagar.setOnAction(e -> {
                                    Instrumentos instrumento = tableView.getItems().get(getIndex());
                                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                                            "Tem certeza que deseja apagar o instrumento?",
                                            ButtonType.YES, ButtonType.NO);
                                    Optional<ButtonType> result = confirm.showAndWait();
                                    if (result.isPresent() && result.get() == ButtonType.YES) {
                                        try {
                                            control.excluir(instrumento);
                                        } catch (PlaylistException err) {
                                            new Alert(Alert.AlertType.ERROR, "Erro ao excluir o instrumento", ButtonType.OK).showAndWait();
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

        TableColumn<Instrumentos, Void> col5 = new TableColumn<>("Ação");
        col5.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5);
        tableView.setItems(control.getLista());
    }

    public void ligacoes() {
        DoubleStringConverter doubleConverter = new DoubleStringConverter();

        control.idProperty().addListener((obs, antigo, novo) -> lblId.setText(String.valueOf(novo)));
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());
        Bindings.bindBidirectional(control.tipoProperty(), txtTipo.textProperty());
        Bindings.bindBidirectional(txtPreco.textProperty(), control.precoProperty(), (StringConverter) doubleConverter );

    }
}
