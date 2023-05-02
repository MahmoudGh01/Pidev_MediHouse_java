/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;


import edu.MediHouse.services.ServiceCommande;
import edu.MediHouse.tools.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sabri
 */
public class SupprimerCommandeController implements Initializable {
 Connection cnx = MyConnection.getInstance().getCnx();
    ServiceCommande sc = new ServiceCommande(cnx);
    @FXML
    private Button aa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    

    @FXML
    private void Confirmersupp(ActionEvent event) throws IOException {
                sc.supprimer(FXMain.cm.getId());
        aa.getScene().getWindow().hide();
              Parent root = FXMLLoader.load(getClass().getResource("ListCommandes.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }

    @FXML
    private void retourSupp(ActionEvent event) throws IOException {
                aa.getScene().getWindow().hide();
              Parent root = FXMLLoader.load(getClass().getResource("ListCommandes.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }
    
}
