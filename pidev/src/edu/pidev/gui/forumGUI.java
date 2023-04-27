package edu.pidev.gui;

import edu.pidev.entities.question;
import edu.pidev.services.questionCRUD;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.LocalDate;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;

public class forumGUI extends Application {
    private questionCRUD crud = new questionCRUD();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create question list
        ObservableList<question> questionList = FXCollections.observableArrayList(crud.display());

        // Create list view
        ListView<question> questionListView = new ListView<>();
        questionListView.setItems(questionList);
questionListView.setCellFactory(param -> new ListCell<question>() {
    @Override
    protected void updateItem(question item, boolean empty) {
        super.updateItem(item, empty);


        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            HBox post = new HBox();
            post.setAlignment(Pos.CENTER_LEFT);
            post.setSpacing(10);
            post.setPadding(new Insets(20, 10, 15, 10)); // added more top padding here
            post.setStyle("-fx-background-color: #f2f2f2; -fx-border-width: 1px 0 1px 0; -fx-border-color: #ddd;");

            // Create user icon
            Image userIcon = new Image("file:///C:/Users/DELL/Downloads/use.png");
            ImageView userIconView = new ImageView(userIcon);
            userIconView.setFitHeight(30);
            userIconView.setFitWidth(30);

            VBox questionDetails = new VBox();
            questionDetails.setSpacing(10);

            Label contentLabel = new Label("Question:");
            contentLabel.setStyle("-fx-font-weight: bold;");
            Label content = new Label(item.getQues_contenu());
            content.setWrapText(true);

            Label dateLabel = new Label("Date Published:");
            dateLabel.setStyle("-fx-font-weight: bold;");
            Label date = new Label(item.getQues_date_pub().toString());

            HBox likesDislikesReportBox = new HBox();
            likesDislikesReportBox.setSpacing(10);

            Button likesButton = new Button("👍 " + item.getLikes());
            likesButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

            // Create dislikes button
            Button dislikesButton = new Button("👎 " + item.getDislikes());
            dislikesButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
            dislikesButton.setOnAction(e -> {
                item.setDislikes(item.getDislikes()+1);
                dislikesButton.setText("👎 " + item.getDislikes());
                likesButton.setDisable(true); // Disable the like button
            });

            // Create likes button
            likesButton.setOnAction(e -> {
                item.incrementLikes();
                likesButton.setText("👍 " + item.getLikes());
                dislikesButton.setDisable(true); // Disable the dislike button
            });

            Button reportButton = new Button("Report");
            reportButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
reportButton.setOnAction(e -> {

    Stage popup = new Stage();
    popup.initModality(Modality.APPLICATION_MODAL);
    popup.setTitle("Report");

    Label label = new Label("Why are you reporting this?");
    label.setFont(Font.font("System", 16));

    RadioButton spamButton = new RadioButton("Spam");
    RadioButton offensiveButton = new RadioButton("Offensive content");
    RadioButton inappropriateButton = new RadioButton("Inappropriate");
    RadioButton otherButton = new RadioButton("Other");

    ToggleGroup toggleGroup = new ToggleGroup();
    spamButton.setToggleGroup(toggleGroup);
    offensiveButton.setToggleGroup(toggleGroup);
    inappropriateButton.setToggleGroup(toggleGroup);
    otherButton.setToggleGroup(toggleGroup);

    VBox optionsBox = new VBox(10);
    optionsBox.getChildren().addAll(spamButton, offensiveButton, inappropriateButton, otherButton);

    TextField otherField = new TextField();
    otherField.setPromptText("Please specify"); // Add a prompt to the text field
    otherField.setVisible(false); // Hide the text field by default

    // Show the text field when the "Other" option is selected
    otherButton.setOnAction(event -> {
        otherField.setVisible(true);
    });

    Button submitButton = new Button("Submit");
    submitButton.setOnAction(event -> {

        if (toggleGroup.getSelectedToggle() == null) {
            // Display an error message if no option has been selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select an option before submitting.");
            alert.showAndWait();
        } else if (otherButton.isSelected() && otherField.getText().isEmpty()) {
            // Display an error message if the "Other" option is selected but no text is entered
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please specify a reason before submitting.");
            alert.showAndWait();
        } else {
            // Handle submit button click
            popup.close(); // Close the report popup


            Stage messagePopup = new Stage();
            messagePopup.initModality(Modality.APPLICATION_MODAL);
            messagePopup.setTitle("Thank You");

            Label messageLabel = new Label("Thanks for reporting. We will check it.");
            messageLabel.setFont(Font.font("System", 16));

            Button closeButton = new Button("Close");
            closeButton.setOnAction(closeEvent -> {
                messagePopup.close();
            });

            VBox layout = new VBox(20);
            layout.setPadding(new Insets(20));
            layout.getChildren().addAll(messageLabel, closeButton);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout, 400, 150);
            messagePopup.setScene(scene);
            messagePopup.showAndWait();
        }
    });

    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.getChildren().addAll(label, optionsBox, otherField, submitButton);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout, 400, 300);
    popup.setScene(scene);
    popup.showAndWait();
});



// Add the report button to your layout

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            likesDislikesReportBox.getChildren().addAll(likesButton, dislikesButton, spacer, reportButton);

            questionDetails.getChildren().addAll(contentLabel, content, dateLabel, date, likesDislikesReportBox);
            post.getChildren().addAll(userIconView, questionDetails);
            setGraphic(post);
        }
    }
});



        // Create add question form
        Label contentLabel = new Label("Ask a question:");
        contentLabel.setStyle("-fx-font-weight: bold;");
        TextField contentField = new TextField();
        Button addButton = new Button("Post");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
addButton.setOnAction(e -> {
    String content = contentField.getText();
    if (content.isEmpty()) {
        // Show error message
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Error");
        dialog.setHeaderText("Content field cannot be empty");

        // Create custom graphic for dialog
ImageView graphic = new ImageView(new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIv0powmUe5YtJ-4svf7TqVLxH-9IYthgvx6sE3I1xb5ydr8NY7VxOFXOh25dXI5veuko&usqp=CAU"));
        graphic.setFitHeight(50);
        graphic.setFitWidth(50);
        dialog.setGraphic(graphic);

        // Add OK button
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);

        // Wait for user to close dialog
        dialog.showAndWait();
        return; // Stop further execution of the code
    }
     
        
    Date date = new Date(System.currentTimeMillis()); 
    
    question q = new question(content, date);
    
    crud.addEntity2(q);

    questionList.add(q); // Add question to list view
    contentField.clear(); // Clear form
});
// Create a new ChoiceBox and add the options
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Yes", "No");
        
        // Set the default value to "Yes"
        choiceBox.setValue("Yes");
        
        // Create a new VBox and add the ChoiceBox to it
        VBox vbox = new VBox(choiceBox);
        vbox.setPadding(new Insets(10));

        // Create refresh button
        Button refreshButton = new Button("Refresh");
        refreshButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setOnAction(e -> {
            // Clear and repopulate list with updated data
            questionList.clear();
            questionList.addAll(crud.display());
        });

        // Create top panel
        HBox topPanel = new HBox(contentLabel, contentField, addButton, refreshButton,choiceBox);
        topPanel.setSpacing(10);
        topPanel.setPadding(new Insets(10));
        topPanel.setStyle("-fx-background-color: #f2f2f2; -fx-border-width: 1px 0 1px 0; -fx-border-color: #ddd;");

        
        // Create layout and add components
        VBox layout = new VBox(topPanel, questionListView);
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));

        // Set scene and show window
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Forum");
        primaryStage.show();
    }

 
}