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


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
/**
 * FXML Controller class
 *
 * @author msi
 */
public class SuccCommandeController implements Initializable {

    @FXML
    private Button aa;
    @FXML
    private Button aa1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Confirmersupp(ActionEvent event) throws IOException {
         
         FXMain.setScene("ProduitsUser");
           
    }

    @FXML
    private void pdfFacture(ActionEvent event) {
         Connection cnx = MyConnection.getInstance().getCnx();

        ServiceCommande sc= new  ServiceCommande(cnx);
        sc.pdf(FXMain.cm , FXMain.produitsCommandes);
        File file = new File("Facture.pdf");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            try {
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
