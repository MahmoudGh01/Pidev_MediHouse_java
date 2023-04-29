/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import edu.MediHouse.tools.MailAPI;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author chaab
 */
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class VerficationController implements Initializable {

   @FXML
    private TextField tfcode;
    int code=(int)Math.floor(Math.random()*(999999-100000+1)+100000);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void envoyercodeemail(ActionEvent event) {
        try {
            
            MailAPI.sendMail(InterfacesignupController.emailsignup, "Verfication du compte par mail", "Veuiller saisire ce code pour verifier votre compte:",code);
            //
        } catch (MessagingException ex) {
            Logger.getLogger(VerficationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void verification(ActionEvent event) {
        
        if(Integer.valueOf(tfcode.getText())==code){
            FXMain.setScene("InterfaceLogin");
        }
        else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide code");
            alert.setContentText("veuillez saisire le code correct");
            alert.showAndWait();
        }
    }

}
