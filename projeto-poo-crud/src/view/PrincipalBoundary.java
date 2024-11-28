package view;


import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        telas.put("musica", new MusicaBoundary());
        telas.put("instrumentos", new InstrumentosBoundary() );  
    
        BorderPane panePrincipal = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Label txtLabel = new Label("Brenda Santana");

        //personalizar
        // Mudando a cor de fundo da tela principal
        panePrincipal.setStyle("-fx-background-color: #FF0000;"); 
        
        // Mudando a cor de fundo do MenuBar
        menuBar.setStyle("-fx-background-color: #FF0000;"); 
        menuBar.setStyle("-fx-text-fill: white;"); 

        // Mudando a cor de fundo de um Label
        txtLabel.setStyle("-fx-text-fill: #333333;"); 
    
        // Menu para Música
        MenuItem mnuItemMusica = new MenuItem("Música");
        mnuItemMusica.setOnAction(e -> 
            panePrincipal.setCenter(telas.get("musica").render()) 
        );

        MenuItem mnuItemInstrumentos = new MenuItem("Instrumentos");
        mnuItemInstrumentos.setOnAction(e -> 
            panePrincipal.setCenter(telas.get("instrumentos").render())
        );

        //criando menus
        Menu mnuMusica = new Menu("Música");
        mnuMusica.getItems().add(mnuItemMusica);

        Menu mnuInstrumentos = new Menu("Instrumentos");
        mnuInstrumentos.getItems().add(mnuItemInstrumentos);
        
        // mnuMusica.getItems().addAll(mnuItemMusica); 
        // mnuInstrumento.getItems().addAll(mnuIteminstrumentos); 
        //mnuAjuda.getItems().add(mnuItemCreditos);

        menuBar.getMenus().addAll(mnuMusica, mnuInstrumentos);
        panePrincipal.setTop(menuBar);
        panePrincipal.setBottom(txtLabel);
    
        // // Define a tela inicial para Musica
        // panePrincipal.setCenter(telas.get("musica").render());
        // panePrincipal.setCenter(telas.get("instrumentos").render());
    
        Scene scn = new Scene(panePrincipal, 470, 600);
        stage.setScene(scn);
        stage.setTitle("Gerenciador de Música");
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch(PrincipalBoundary.class, args);
    }
}