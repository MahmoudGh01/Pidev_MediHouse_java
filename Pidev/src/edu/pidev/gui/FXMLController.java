package edu.pidev.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add labels to the grid pane
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label("Question " + i + "," + j);
                label.setPrefSize(200, 200);

                StackPane box = new StackPane();
                box.getStyleClass().add("label-box"); // Add CSS class for styling
                box.getChildren().add(label);

                gridPane.add(box, j, i);
            }
        }
    }
}
