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
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author DELL
 */

public class AdminPOVController implements Initializable {
   @FXML
    private TableView<question> data_ques;
    @FXML
    private TableColumn<question, Integer> id;
    @FXML
    private TableColumn<question, String> question;
    @FXML
    private TableColumn<question, Date> date_publication;
    @FXML
    private TableColumn<question, Integer> likes;
    @FXML
    private TableColumn<question, Integer> dislikes;
    @FXML
    private TableColumn<question, String> category;
    questionCRUD qc = new questionCRUD();
ObservableList<question> questions = FXCollections.observableArrayList(qc.display());
    @FXML
    private Circle profilepicture;
    @FXML
    private Label username;
    @FXML
    private Button Deletebtn;
            


    @Override
    public void initialize(URL url, ResourceBundle rb) {
id.setCellValueFactory(new PropertyValueFactory<question,Integer>("id"));
question.setCellValueFactory(new PropertyValueFactory<question,String>("ques_contenu"));
date_publication.setCellValueFactory(new PropertyValueFactory<question,Date>("ques_date_pub"));
likes.setCellValueFactory(new PropertyValueFactory<question,Integer>("likes"));
dislikes.setCellValueFactory(new PropertyValueFactory<question,Integer>("dislikes"));
category.setCellValueFactory(new PropertyValueFactory<question,String>("categorie"));
data_ques.setItems(questions);
 Deletebtn.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 16px;");


    }    

@FXML
private void Delete(ActionEvent event) {
    List<question> selectedQuestions = data_ques.getSelectionModel().getSelectedItems();
    if (selectedQuestions.isEmpty()) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("No Question Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select at least one question to delete.");
        alert.showAndWait();
    } else {
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation Dialog");
        confirm.setHeaderText(null);
        if (selectedQuestions.size() == 1) {
            confirm.setContentText("Are you sure you want to delete the selected question?");
        } else {
            confirm.setContentText("Are you sure you want to delete the selected questions?");
        }
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            selectedQuestions.forEach(q -> {
                qc.deletequestion2(String.valueOf(q.getId()));
                questions.remove(q);
            });
        }
    }
}


    
}
