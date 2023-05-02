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
import java.util.Random;
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
public class InterfaceAdminController implements Initializable {

    @FXML
    private Circle ProfilePic1;
    @FXML
    private Label UserName1;
Users u =new Users();
     ServiceUser su=new ServiceUser();
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        u=su.getUserByEmail(InterfaceLogineeController.iduserglobal);
        Image im = new Image(u.getProfilePicture());
        ImagePattern pattern = new ImagePattern(im);
        ProfilePic1.setFill(pattern);
        ProfilePic1.setStroke(Color.SEAGREEN);
        ProfilePic1.setEffect(new DropShadow(20, Color.BLACK));
       // String pseudo = generatePseudo(u.getNom(), u.getPrenom());
        UserName1.setText(generatePseudo(u.getNom(), u.getPrenom()));
          ProfilePic1.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            profile(event);
                  }
    });
    }
      public static String generatePseudo(String tfnom, String tfprenom) {
        String firstInitial = tfprenom.substring(0, 1).toLowerCase();
        String lastInitial = tfnom.substring(0, 1).toLowerCase();
        int randomNum = new Random().nextInt(10000); // generate a random number between 0 and 9999
        String pseudo = firstInitial + lastInitial + randomNum;
        return pseudo;
        
    }
      
    private void Profile(ActionEvent event) {
         FXMain.setScene("ProfileAdmin");
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
    private void Dashboard(ActionEvent event) {
         FXMain.setScene("Dashbord");
    }

    @FXML
    private void reclamation(ActionEvent event) {
         FXMain.setScene("Reponse");
    }

    @FXML
    private void forum(ActionEvent event) {
         FXMain.setScene("Forum");
    }

    @FXML
    private void Users(ActionEvent event) {
         FXMain.setScene("Usersmanagment");
    }

    @FXML
    private void RDV(ActionEvent event) {
         FXMain.setScene("RDV");
    }

    @FXML
    private void profile(MouseEvent event) {
         FXMain.setScene("ProfileAdmin");
    }
    
}
