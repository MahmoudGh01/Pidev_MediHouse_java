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
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class FXML1Controller implements Initializable {

    private VBox questionContainer;
private VBox reponsecontainer;
   questionCRUD qc = new questionCRUD();
    reponseCRUD rc = new reponseCRUD();
    /**
     * Initializes the controller class.
     */
   @Override
public void initialize(URL url, ResourceBundle rb) {
    // create a VBox to hold the response nodes
    reponsecontainer = new VBox();
    
    // retrieve the responses from the database
    List<reponse> reponses = rc.display();

    // create a Label for each response and add it to the VBox
    for (reponse reponse : reponses) {
        Label reponseLabel = new Label(reponse.getRep_contenu());
        reponseLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        reponseLabel.setPadding(new Insets(10));
        reponsecontainer.getChildren().add(reponseLabel);
    }



}}
