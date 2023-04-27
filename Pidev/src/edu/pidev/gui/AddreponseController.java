/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pidev.gui;

import edu.pidev.entities.question;
import edu.pidev.entities.reponse;
import edu.pidev.services.questionCRUD;
import edu.pidev.services.reponseCRUD;
import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class AddreponseController implements Initializable {

    @FXML
    private TextArea quest;
    @FXML
    private Button postBTN;
    @FXML
    private Circle profilepicture;
    @FXML
    private Label username;
    private int num;
    private TextField labelll;
    @FXML
    private TextField field;

  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("******************"+labelll);    }    

    @FXML
    private void addRep(ActionEvent event) {
         String content = quest.getText();
        reponseCRUD qcd = new reponseCRUD();
    Date date = new Date(System.currentTimeMillis()); 
        reponse q = new reponse();
    
    // Check for empty content
    if(content.isEmpty()){
        Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
        emptyAlert.setTitle("Error");
        emptyAlert.setHeaderText("reponse content cannot be empty");
        emptyAlert.showAndWait();
        return;
    }
    
    // Check for bad words
    List<String> badWords = Arrays.asList("mehdi", "chebbi");
    for (String badWord : badWords) {
        if (content.toLowerCase().contains(badWord)) {
            Alert badWordAlert = new Alert(Alert.AlertType.ERROR);
            badWordAlert.setTitle("Error");
            badWordAlert.setHeaderText("Question contains a bad word");
            badWordAlert.showAndWait();
            return;
        }
    }
 

    q.setRep_contenu(content);
    q.setRep_date_pub(date);
    // Create a confirmation alert
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText("Are you sure you want to post the question?");

    // Show the alert and wait for the user's response
    Optional<ButtonType> result = alert.showAndWait();

    // If the user confirms, add the question to the database and close the window
    if (result.isPresent() && result.get() == ButtonType.OK) {
        qcd.addEntity2(q);

        // Close the window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    }

    public void setLabelll(String  labelll) {
        this.labelll.setText(labelll);
    }

    public void setField(String field) {
        this.field.setText(field);
    }
    
}
