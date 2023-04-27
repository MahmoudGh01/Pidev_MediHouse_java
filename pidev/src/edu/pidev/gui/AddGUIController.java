/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pidev.gui;

import edu.pidev.entities.question;
import edu.pidev.services.questionCRUD;
import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class AddGUIController implements Initializable {

    @FXML
    private TextArea quest;
    @FXML
    private MenuButton CATmenu;
    private CheckBox anonBTN;
    @FXML
    private Button postBTN;
    @FXML
    private Circle profilepicture;
    @FXML
    private Label username;
    @FXML
    private Button anon;
private int badWordCount = 0;
int countdown = 60;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

  @FXML
void SETcat(ActionEvent event) {
    MenuItem selectedItem = (MenuItem) event.getSource();
    CATmenu.setText(selectedItem.getText()); // Set the text of the MenuButton to the text of the selected MenuItem
}

public boolean isAnonSelected() {
    return anonBTN.isSelected();
}
@FXML
private void POST(ActionEvent event) {
    String content = quest.getText();
    String category = CATmenu.getText();
    questionCRUD qcd = new questionCRUD();
    Date date = new Date(System.currentTimeMillis()); 
    question q = new question();
    
    // Check for empty content
    if(content.isEmpty()){
        Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
        emptyAlert.setTitle("Error");
        emptyAlert.setHeaderText("Question content cannot be empty");
        emptyAlert.showAndWait();
        return;
    }
    
    // Check for bad words
    List<String> badWords = Arrays.asList("mehdi", "chebbi");
    for (String badWord : badWords) {
        if (content.toLowerCase().contains(badWord)) {
            badWordCount++;
            if (badWordCount >= 3) {
                // Disable the post button and show a countdown timer
               // 60 seconds = 1 minute
                Label countdownLabel = new Label(String.format("Posting is disabled for %d seconds", countdown));
                HBox countdownBox = new HBox(countdownLabel);
                countdownBox.setAlignment(Pos.CENTER);
                countdownBox.setPadding(new Insets(10));
                Alert banAlert = new Alert(Alert.AlertType.ERROR);
                banAlert.setTitle("Error");
                banAlert.setHeaderText("You have been banned for 1 minute for using bad language");
                banAlert.getDialogPane().setContent(countdownBox);
                banAlert.show();

                // Start the countdown timer
                Timeline timeline = new Timeline();
                timeline.setCycleCount(countdown);
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        countdown--;
                        if (countdown == 0) {
                            // Enable the post button and close the alert
                            postBTN.setDisable(false);
                            banAlert.setResult(ButtonType.OK);
                        } else {
                            countdownLabel.setText(String.format("Posting is disabled for %d seconds", countdown));
                        }
                    }
                }));
                timeline.play();

                return;
            }
            Alert badWordAlert = new Alert(Alert.AlertType.ERROR);
            badWordAlert.setTitle("Error");
            badWordAlert.setHeaderText("Question contains a bad word");
            badWordAlert.showAndWait();
            return;
        }
    }
 

    q.setQues_contenu(content);
    q.setQues_date_pub(date);
    q.setCategorie(category);

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

    @FXML
    private void anon(ActionEvent event) {
        String content = quest.getText();
    String category = CATmenu.getText();
    questionCRUD qcd = new questionCRUD();
    Date date = new Date(System.currentTimeMillis()); 
    question q = new question();
    
    // Check for empty content
    if(content.isEmpty()){
        Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
        emptyAlert.setTitle("Error");
        emptyAlert.setHeaderText("Question content cannot be empty");
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
 
   q.setHide_name(1);
    q.setQues_contenu(content);
    q.setQues_date_pub(date);
    q.setCategorie(category);

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


    
}
