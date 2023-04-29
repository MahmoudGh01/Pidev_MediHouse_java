/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import edu.MediHouse.entities.Users;
import edu.MediHouse.services.ServiceUser;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author chaab
 */
public class InterfaceePatientController implements Initializable {

    @FXML
    private Circle ProfilePic;
    @FXML
    private Label UserName;
    Users u =new Users();
     ServiceUser su=new ServiceUser();
    /**
     * initialises the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         u=su.getUserByEmail(InterfaceLogineeController.iduserglobal);
        Image im = new Image(u.getProfilePicture());
        ImagePattern pattern = new ImagePattern(im);
        ProfilePic.setFill(pattern);
        ProfilePic.setStroke(Color.SEAGREEN);
        ProfilePic.setEffect(new DropShadow(20, Color.BLACK));
        UserName.setText(u.getNom().toUpperCase()+" "+u.getPrenom().toUpperCase());
        ProfilePic.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Profile1(event);
        }
    });
    }    

    private void Profile(ActionEvent event) {
         FXMain.setScene("ProfilePatient");
 
    }

   @FXML
    private void Logout(ActionEvent event) { 
        Stage stage;
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Déconnexion");
    alert.setHeaderText("Vous êtes sur le point de vous déconnecter");
    alert.setContentText("Voulez-vous vous déconnecter "+u.getEmail()+"?");
    ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
    alert.getButtonTypes().setAll(okButton, cancelButton);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == okButton) {
         FXMain.setScene("InterfaceLogin");
        
    }
    }
    
    @FXML
     private void RechMed(ActionEvent event) {
        FXMain.setScene("RechMed");
    }

    @FXML
    private void parapharmacie(ActionEvent event) {
        FXMain.setScene("parapharmacie");
    }
    
    @FXML
    private void forum(ActionEvent event) {
        FXMain.setScene("Forum");
    }

    @FXML
    private void reclamation(ActionEvent event) {
       FXMain.setScene("Reclamation");
    }

    @FXML
    private void RDVP(ActionEvent event) {
       FXMain.setScene("RDVP");
    }

    @FXML
    private void Profile1(MouseEvent event) {
        FXMain.setScene("ProfilePatient");
    }

}
