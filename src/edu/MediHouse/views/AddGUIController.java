/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import edu.MediHouse.entities.Users;
import edu.MediHouse.entities.question;
import edu.MediHouse.services.ServiceUser;
import edu.MediHouse.services.questionCRUD;
import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

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
    @FXML
    private ChoiceBox<String> choice;
    private String[] field = {"Médecine générale", "Médecine dentaire", "Pédiatrie", "Médecine interne", " Je ne sais pas"};
 ServiceUser U =new ServiceUser();
    Users u = new Users();
    String ch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        u=U.getUserByEmail(InterfaceLogineeController.iduserglobal);
        choice.getItems().addAll(field);
        choice.getSelectionModel().selectFirst();

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
        if (content.isEmpty()) {
            Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
            emptyAlert.setTitle("Error");
            emptyAlert.setHeaderText("Question ne peut pas etre vide");
            emptyAlert.showAndWait();
            return;
        }

        // Check for bad words
        List<String> badWords = Arrays.asList("mehdi", "chebbi");
        for (String badWord : badWords) {
            if (content.toLowerCase().contains(badWord)) {
                badWordCount++; // Increment bad word count

                // If bad word count is greater than or equal to 3, ban user
                if (badWordCount >= 3) {
                    postBTN.setDisable(true); // Disable post button
                    anon.setDisable(true);
                    long banEndTime = System.currentTimeMillis() + BAN_DURATION * 1000;
                    scheduler = Executors.newScheduledThreadPool(1);

                    // Schedule task to update post button text every second
                    scheduler.scheduleAtFixedRate(() -> {
                        long remainingTime = banEndTime - System.currentTimeMillis();
                        if (remainingTime <= 0) {
                            // Enable post button and reset bad word count
                            Platform.runLater(() -> {
                                postBTN.setDisable(false);
                                postBTN.setText("publier");
                                anon.setDisable(false);
                                anon.setText("publier comme anonyme");
                                badWordCount = 0;
                            });
                            scheduler.shutdown();
                            return;
                        }
                        // Update post button text with remaining time
                        Platform.runLater(() -> {
                            postBTN.setText("banni(e) for " + remainingTime / 1000 + "s");
                            anon.setText("banni(e) for " + remainingTime / 1000 + "s");
                        });
                    }, 0, 1, TimeUnit.SECONDS);

                    Alert banAlert = new Alert(Alert.AlertType.ERROR);
                    banAlert.setTitle("Error");
                    banAlert.setHeaderText("Vous avez été banni pour " + BAN_DURATION + " secondes");
                    banAlert.showAndWait();
                    return;
                }

                // Show bad word alert
                Alert badWordAlert = new Alert(Alert.AlertType.ERROR);
                badWordAlert.setTitle("Error");
                badWordAlert.setHeaderText("La question contient un gros mot");
                badWordAlert.showAndWait();
                return;
            }
        }

        q.setQues_contenu(content);
        q.setQues_date_pub(date);
        q.setCategorie(choice.getValue());
        q.setHide_name(0);
        q.setId_user(u.getId());//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Create a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("confirmation");

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
    private long blockStartTime = 0; // start time of the current block (0 means not blocked)
    private final int BAN_DURATION = 10; // In seconds
    private ScheduledExecutorService scheduler;

    @FXML
    private void anon(ActionEvent event) {
        String content = quest.getText();
        String category = CATmenu.getText();
        questionCRUD qcd = new questionCRUD();
        Date date = new Date(System.currentTimeMillis());
        question q = new question();

        // Check for empty content
        if (content.isEmpty()) {
            Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
            emptyAlert.setTitle("Error");
            emptyAlert.setHeaderText("Question ne peut pas etre vide");
            emptyAlert.showAndWait();
            return;
        }

        // Check for bad words
        List<String> badWords = Arrays.asList("mehdi", "chebbi");
        for (String badWord : badWords) {
            if (content.toLowerCase().contains(badWord)) {
                badWordCount++; // Increment bad word count

                // If bad word count is greater than or equal to 3, ban user
                if (badWordCount >= 3) {
                    anon.setDisable(true); // Disable post button
                    postBTN.setDisable(true);
                    long banEndTime = System.currentTimeMillis() + BAN_DURATION * 1000;
                    scheduler = Executors.newScheduledThreadPool(1);

                    // Schedule task to update post button text every second
                    scheduler.scheduleAtFixedRate(() -> {
                        long remainingTime = banEndTime - System.currentTimeMillis();
                        if (remainingTime <= 0) {
                            // Enable post button and reset bad word count
                            Platform.runLater(() -> {
                                anon.setDisable(false);
                                anon.setText("publier comme anonyme");
                                postBTN.setDisable(false);
                                postBTN.setText("publier");
                                badWordCount = 0;
                            });
                            scheduler.shutdown();
                            return;
                        }
                        // Update post button text with remaining time
                        Platform.runLater(() -> {
                            anon.setText("banni(e) for " + remainingTime / 1000 + "s");
                            postBTN.setText("banni(e) for " + remainingTime / 1000 + "s");

                        });
                    }, 0, 1, TimeUnit.SECONDS);

                    Alert banAlert = new Alert(Alert.AlertType.ERROR);
                    banAlert.setTitle("Error");
                    banAlert.setHeaderText("Vous avez été banni pour " + BAN_DURATION + " secondes");
                    banAlert.showAndWait();
                    return;
                }

                // Show bad word alert
                Alert badWordAlert = new Alert(Alert.AlertType.ERROR);
                badWordAlert.setTitle("Error");
                badWordAlert.setHeaderText("La question contient un gros mot");
                badWordAlert.showAndWait();
                return;
            }
        }
        q.setCategorie(ch);
        q.setHide_name(1);
        q.setQues_contenu(content);
        q.setQues_date_pub(date);
        q.setId_user(u.getId());////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        q.setCategorie(choice.getValue());

        // Create a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("confirmation");

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
