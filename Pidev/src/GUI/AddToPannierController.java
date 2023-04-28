/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class AddToPannierController implements Initializable {

    @FXML
    private Button aa;
    @FXML
    private Label prName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prName.setText(Main.pr.getNomproduit());
    }    

    @FXML
    private void Confirmersupp(ActionEvent event) throws IOException {
          aa.getScene().getWindow().hide();
           
    }
    
}
