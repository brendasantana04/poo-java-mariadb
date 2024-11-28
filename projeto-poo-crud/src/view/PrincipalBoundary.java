package view;


import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tools.Tela;

public class PrincipalBoundary extends Application {
    private Map<String, Tela> telas = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        // Adiciona MusicaBoundary ao mapa de telas
        telas.put("musica", new MusicaBoundary()); // Altere para 'musica' no lugar de 'contato'
        //telas.put("endereco", new EnderecoBoundary() );  // Caso tenha outras telas como 'Endereco', descomente isso
    
        BorderPane panePrincipal = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu mnuCadastro = new Menu("Cadastro");
        Menu mnuAjuda = new Menu("Ajuda");
    
        // MenuItem para Musica
        MenuItem mnuItemMusica = new MenuItem("Música");
        mnuItemMusica.setOnAction(e -> 
            panePrincipal.setCenter(telas.get("musica").render()) // Alterado para "musica"
        );
    
        // MenuItem para Endereco (se necessário)
        // MenuItem mnuItemEndereco = new MenuItem("Endereco");
        // mnuItemEndereco.setOnAction(e -> 
        //     panePrincipal.setCenter(telas.get("endereco").render())
        // );
    
        MenuItem mnuItemCreditos = new MenuItem("Creditos");
    
        mnuCadastro.getItems().addAll(mnuItemMusica); // Adiciona Música no menu Cadastro
       // mnuCadastro.getItems().addAll(mnuItemEndereco); // Se necessário, adicione Endereco também
        mnuAjuda.getItems().add(mnuItemCreditos);
    
        menuBar.getMenus().addAll(mnuCadastro, mnuAjuda);
        panePrincipal.setTop(menuBar);
    
        // Define a tela inicial para Musica
        panePrincipal.setCenter(telas.get("musica").render());
    
        Scene scn = new Scene(panePrincipal, 800, 600);
        stage.setScene(scn);
        stage.setTitle("Gerenciador de Música");
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch(PrincipalBoundary.class, args);
    }
}