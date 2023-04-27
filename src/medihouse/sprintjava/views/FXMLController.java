/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse.sprintjava.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author user
 */
public class FXMLController implements Initializable {

    @FXML
    private Button btnReserver;
    @FXML
    private Button btnListRDV;
    @FXML
    private Button btnCalendar;
    @FXML
    private Button btnFiche;
    @FXML
    private Button btnListFiche;
    @FXML
    private BorderPane b;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnReserver(ActionEvent event) {
        loadPage("Reserver");
    }

    @FXML
    private void btnListRDV(ActionEvent event) {
        loadPage("ListRDV");
    }

    @FXML
    private void btnCalendar(ActionEvent event) {
        loadPage("Calendar");
    }

    @FXML
    private void btnFiche(ActionEvent event) {
        loadPage("NewFiche");
    }

    @FXML
    private void btnListFiche(ActionEvent event) {
        loadPage("ListerFiche");
    }
    
    private void loadPage(String page){
    
        Parent root =null;
        try {
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        b.setCenter(root);
        
    }
    
}
