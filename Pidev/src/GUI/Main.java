/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entites.Commande;
import Entites.Produit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author sabri
 */
public class Main extends Application {
  public  static String user = "Imen ";
    
      public  static Double prix ;

    static Produit pr = new Produit();
    static Commande cm = new Commande();
static  ObservableList<Produit> produitsCommandes = FXCollections.observableArrayList();
static Produit pn = new Produit();
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ProduitsUser.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments 
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
