/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pidev.gui;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import edu.pidev.entities.question;
import edu.pidev.entities.reponse;
import edu.pidev.services.questionCRUD;
import edu.pidev.services.reponseCRUD;
import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class PiController implements Initializable {

    @FXML
    private Button ADDbtn;


@FXML
private VBox questionContainer;
private VBox reponsecontainer;
   questionCRUD qc = new questionCRUD();
    reponseCRUD rc = new reponseCRUD();
    @FXML
    private Button refresh;
    @FXML
    private Circle profilepicture;
    @FXML
    private Label username;
    @FXML
    private ScrollBar scroll;
    @FXML
    private TextField recherche;
    @FXML
    private Button rech;
@Override
public void initialize(URL url, ResourceBundle rb) {
    scroll.valueProperty().addListener((observable, oldValue, newValue) -> {
    // Calculate the new vertical scroll position of the questionContainer
    double scrollPosition = (questionContainer.getHeight() - scroll.getHeight()) * newValue.doubleValue();
    
    // Set the vertical scroll position of the questionContainer
    questionContainer.setLayoutY(-scrollPosition);

});

 display();
}

   final boolean[] likePressed = { false };
final boolean[] dislikePressed = { false };


private void deletereponse(String content) {
    
    
     Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Delete reponse");
    alert.setHeaderText("Are you sure you want to delete this reponse?");
    alert.setContentText("This action cannot be undone.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
  rc.deleteReponse(content);
        display();
    }
}

public void shareHelloOnFacebook() {
    String content = "hello";
    String encodedContent = "";

    try {
        encodedContent = URLEncoder.encode(content, "UTF-8");
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }

    String shareLink = "https://www.facebook.com/sharer/sharer.php?u=" + encodedContent;

    try {
        Desktop.getDesktop().browse(new URI(shareLink));
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void deleteQuestion(String content) {
    
    
     Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Delete Question");
    alert.setHeaderText("Are you sure you want to delete this question?");
    alert.setContentText("This action cannot be undone.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
  qc.deleteQuestion(content);
        display();
    }
}
   @FXML
public void display() {
    // Clear existing questions
    questionContainer.getChildren().clear();

    List<question> questions = qc.display();
    if (questions != null && !questions.isEmpty()) {
        for (question q : questions) {
            HBox questionBox = new HBox();
            VBox questionDetails = new VBox();

            // Add user icon and label
            Image userIcon = new Image("file:///C:/Users/DELL/Downloads/use.png");
            ImageView userImageView = new ImageView(userIcon);
            userImageView.setFitWidth(40);
            userImageView.setFitHeight(40);
            int anon=qc.hidden(q.getId());
            
            /*************** log hide_name **********/
rech.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" 
);
rech.setOnMouseEntered(e -> {
    rech.setStyle("-fx-background-color: #0062cc;" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n");
});
ImageView icon = new ImageView(new Image("file:/C:/Users/DELL/Pictures/icon2.png"));

        // Set the size of the icon
        icon.setFitHeight(45);
        icon.setFitWidth(50);

        // Set the icon as the graphic for the button
        rech.setGraphic(icon);
rech.setOnMouseExited(e -> {
    rech.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" );
});
refresh.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
refresh.setOnMouseEntered(e -> {
    refresh.setStyle("-fx-background-color: #0062cc;" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});

refresh.setOnMouseExited(e -> {
    refresh.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});
ADDbtn.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
ADDbtn.setOnMouseEntered(e -> {
    ADDbtn.setStyle("-fx-background-color: #0062cc;" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});

ADDbtn.setOnMouseExited(e -> {
    ADDbtn.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});
          String anonym="";
            if(anon==1){ 
            anonym="anonymous";}
                if(anon==0){          anonym="user";}
            Label userLabel = new Label(anonym+" posted this on " + q.getQues_date_pub());
userLabel.setPrefWidth(500); // or any other value that suits your needs
questionDetails.setPrefWidth(1000); // or any other value that suits your needs

            HBox userBox = new HBox();
            
            userBox.getChildren().addAll(userImageView, userLabel);

            // Add question text area
            TextArea questionTextArea = new TextArea(q.getQues_contenu());
            questionTextArea.setEditable(false);
            questionTextArea.setWrapText(true);

            // Add like and dislike buttons
            Button likeButton = new Button("👍 " + q.getLikes());
            likeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

            Button dislikeButton = new Button("👎 " + q.getDislikes());
            Button twitterBTN = new Button("share on twitter");
twitterBTN.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-weight: bold;");
 twitterBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // create the Twitter share link with pre-filled content
                String text=q.getQues_contenu();
                String shareLink = "https://twitter.com/intent/tweet?text="+text;

                try {
                    // open the link in the default browser
                    Desktop.getDesktop().browse(new URI(shareLink));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
 Button fbButton = new Button("Share on Facebook");
fbButton.setStyle("-fx-background-color: #3B5998; -fx-text-fill: white; -fx-font-weight: bold;");
fbButton.setOnAction(event -> shareHelloOnFacebook());


Button rep=new Button("Reponse");
rep.setOnAction(event -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("reponse.fxml"));
        Parent root = loader.load();
        ReponseController controller = loader.getController();
        controller.setQuesti(q.getQues_contenu());
        controller.setUsername2(userLabel.getText());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
});



            HBox buttonBox = new HBox();
            buttonBox.setSpacing(10);
            buttonBox.getChildren().addAll(rep,likeButton, dislikeButton,twitterBTN,fbButton);
            dislikeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

            likeButton.setOnAction(e -> {
    if (!likePressed[0]) {
        if (dislikePressed[0]) {
            qc.editDISLIKE(q.getId(), q.getDislikes()-1);
            dislikeButton.setText("👎 " + q.getDislikes());
            dislikePressed[0] = false;
        }
        qc.editLIKE(q.getId(), q.getLikes()+1);
        likeButton.setText("👍 " + q.getLikes());
        dislikeButton.setDisable(true);
        likePressed[0] = true;
    } else {
        qc.editLIKE(q.getId(), q.getLikes()-1);
        likeButton.setText("👍 " + q.getLikes());
        likePressed[0] = false;
    }display();
});

dislikeButton.setOnAction(e -> {
    if (!dislikePressed[0]) {
        if (likePressed[0]) {
            qc.editLIKE(q.getId(), q.getLikes()-1);
            likeButton.setText("👍 " + q.getLikes());
            likePressed[0] = false;
        }
        qc.editDISLIKE(q.getId(), q.getDislikes()+1);
        dislikeButton.setText("👎 " + q.getDislikes());
        likeButton.setDisable(true);
        dislikePressed[0] = true;
    } else {
        qc.editDISLIKE(q.getId(), q.getDislikes()-1);
        dislikeButton.setText("👎 " + q.getDislikes());
        dislikePressed[0] = false;
    }display();
});


            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");
            deleteButton.setPrefWidth(200);
            deleteButton.setOnAction(event -> deleteQuestion(q.getQues_contenu()));

            Button editButton = new Button("Edit");
            editButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            editButton.setPrefWidth(200);
            editButton.setOnAction(event -> editQuestion(q));

            HBox deleteEditBox = new HBox();
            deleteEditBox.setSpacing(10); // add some spacing between buttons
            deleteEditBox.getChildren().addAll(deleteButton, editButton);
            deleteEditBox.setAlignment(Pos.CENTER_RIGHT);
            deleteEditBox.setPadding(new Insets(0, 10, 0, 0)); // move the buttons to the right

            // Add all the components to the question details
            questionDetails.getChildren().addAll(userBox, questionTextArea, buttonBox);
            questionDetails.setAlignment(Pos.CENTER_LEFT);
questionDetails.setPrefWidth(1000); // or any other value that suits your needs
            // Add the question details to the question box
            questionBox.getChildren().addAll(questionDetails, deleteEditBox);
            questionBox.setPadding(new Insets(0, 0, 0, 200));
            questionBox.setHgrow(questionDetails, Priority.ALWAYS);
            // Add the question box to the question container 
        
  questionContainer.setPrefWidth(1200);
            questionContainer.getChildren().add(questionBox);

           
            /*************************************/
            
            
            
            



        }
    } else {
        Label label = new Label("il n'a pas de questions");
label.setStyle("-fx-padding: 10px;"); // add some padding to the label

// Create a StackPane layout and add the label to it
StackPane root = new StackPane(label);

// Set the alignment of the StackPane to center
root.setAlignment(Pos.CENTER);

// Create a border for the label
Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
label.setBorder(border);

questionContainer.getChildren().add(root);


    }
}





private void likeQuestion(question q) {
   q.setLikes(q.getLikes()+1);
}

private void dislikeQuestion(question q) {
  q.incrementDislikes();
}





private void editQuestion(question q) {
    TextInputDialog dialog = new TextInputDialog(q.getQues_contenu());
    dialog.setTitle("Edit Question");
    dialog.setHeaderText("Edit the question below:");
    dialog.setContentText("Question:");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent() && !result.get().isEmpty()) {
        String edited = result.get();
        
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit this question?");
        Optional<ButtonType> confirmResult = confirmation.showAndWait();
        if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
            qc.editQuestion(q.getId(), edited);
            display();
        }
    }
}

private void editreponse(reponse q) {
    TextInputDialog dialog = new TextInputDialog(q.getRep_contenu());
    dialog.setTitle("Edit reponse");
    dialog.setHeaderText("Edit the reponse below:");
    dialog.setContentText("reponse:");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent() && !result.get().isEmpty()) {
        String edited = result.get();
        
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit this reponse?");
        Optional<ButtonType> confirmResult = confirmation.showAndWait();
        if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
            rc.editReponse(q.getId(), edited);
            display();
        }
    }
}


    
    
    @FXML
    private void ADDques(ActionEvent event) {
        
        
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("addGUI.fxml")); // Load the FXML file for the second window
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show(); 
        } catch (IOException ex) {
            Logger.getLogger(PiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void filter(ActionEvent event) {
    // Clear existing questions
    
  String filter=  recherche.getText();
    questionContainer.getChildren().clear();

    List<question> questions = qc.filter(filter);
  
    if (questions != null && !questions.isEmpty()) {
        for (question q : questions) {
            HBox questionBox = new HBox();
            VBox questionDetails = new VBox();

            // Add user icon and label
            Image userIcon = new Image("file:///C:/Users/DELL/Downloads/use.png");
            ImageView userImageView = new ImageView(userIcon);
            userImageView.setFitWidth(40);
            userImageView.setFitHeight(40);
            int anon=qc.hidden(q.getId());
            
            /*************** log hide_name **********/
rech.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" 
);
rech.setOnMouseEntered(e -> {
    rech.setStyle("-fx-background-color: #0062cc;" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n");
});
ImageView icon = new ImageView(new Image("file:/C:/Users/DELL/Pictures/icon2.png"));

        // Set the size of the icon
        icon.setFitHeight(45);
        icon.setFitWidth(50);

        // Set the icon as the graphic for the button
        rech.setGraphic(icon);
rech.setOnMouseExited(e -> {
    rech.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" );
});
refresh.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
refresh.setOnMouseEntered(e -> {
    refresh.setStyle("-fx-background-color: #0062cc;" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});

refresh.setOnMouseExited(e -> {
    refresh.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});
ADDbtn.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
ADDbtn.setOnMouseEntered(e -> {
    ADDbtn.setStyle("-fx-background-color: #0062cc;" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});

ADDbtn.setOnMouseExited(e -> {
    ADDbtn.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});
          String anonym="";
            if(anon==1){ 
            anonym="anonymous";}
                if(anon==0){          anonym="user";}
            Label userLabel = new Label(anonym+" posted this on " + q.getQues_date_pub());
userLabel.setPrefWidth(500); // or any other value that suits your needs
questionDetails.setPrefWidth(1000); // or any other value that suits your needs

            HBox userBox = new HBox();
            
            userBox.getChildren().addAll(userImageView, userLabel);

            // Add question text area
            TextArea questionTextArea = new TextArea(q.getQues_contenu());
            questionTextArea.setEditable(false);
            questionTextArea.setWrapText(true);

            // Add like and dislike buttons
            Button likeButton = new Button("👍 " + q.getLikes());
            likeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

            Button dislikeButton = new Button("👎 " + q.getDislikes());
            Button twitterBTN = new Button("share on twitter");
twitterBTN.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-weight: bold;");
 twitterBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // create the Twitter share link with pre-filled content
                String text=q.getQues_contenu();
                String shareLink = "https://twitter.com/intent/tweet?text="+text;

                try {
                    // open the link in the default browser
                    Desktop.getDesktop().browse(new URI(shareLink));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
 Button fbButton = new Button("Share on Facebook");
fbButton.setStyle("-fx-background-color: #3B5998; -fx-text-fill: white; -fx-font-weight: bold;");
fbButton.setOnAction(a -> shareHelloOnFacebook());


Button rep=new Button("Reponse");
rep.setOnAction(a -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("reponse.fxml"));
        Parent root = loader.load();
        ReponseController controller = loader.getController();
        controller.setQuesti(q.getQues_contenu());
        controller.setUsername2(userLabel.getText());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
});



            HBox buttonBox = new HBox();
            buttonBox.setSpacing(10);
            buttonBox.getChildren().addAll(rep,likeButton, dislikeButton,twitterBTN,fbButton);
            dislikeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

            likeButton.setOnAction(e -> {
    if (!likePressed[0]) {
        if (dislikePressed[0]) {
            qc.editDISLIKE(q.getId(), q.getDislikes()-1);
            dislikeButton.setText("👎 " + q.getDislikes());
            dislikePressed[0] = false;
        }
        qc.editLIKE(q.getId(), q.getLikes()+1);
        likeButton.setText("👍 " + q.getLikes());
        dislikeButton.setDisable(true);
        likePressed[0] = true;
    } else {
        qc.editLIKE(q.getId(), q.getLikes()-1);
        likeButton.setText("👍 " + q.getLikes());
        likePressed[0] = false;
    }display();
});

dislikeButton.setOnAction(e -> {
    if (!dislikePressed[0]) {
        if (likePressed[0]) {
            qc.editLIKE(q.getId(), q.getLikes()-1);
            likeButton.setText("👍 " + q.getLikes());
            likePressed[0] = false;
        }
        qc.editDISLIKE(q.getId(), q.getDislikes()+1);
        dislikeButton.setText("👎 " + q.getDislikes());
        likeButton.setDisable(true);
        dislikePressed[0] = true;
    } else {
        qc.editDISLIKE(q.getId(), q.getDislikes()-1);
        dislikeButton.setText("👎 " + q.getDislikes());
        dislikePressed[0] = false;
    }display();
});


            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");
            deleteButton.setPrefWidth(200);
            deleteButton.setOnAction(a -> deleteQuestion(q.getQues_contenu()));

            Button editButton = new Button("Edit");
            editButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            editButton.setPrefWidth(200);
            editButton.setOnAction(a -> editQuestion(q));

            HBox deleteEditBox = new HBox();
            deleteEditBox.setSpacing(10); // add some spacing between buttons
            deleteEditBox.getChildren().addAll(deleteButton, editButton);
            deleteEditBox.setAlignment(Pos.CENTER_RIGHT);
            deleteEditBox.setPadding(new Insets(0, 10, 0, 0)); // move the buttons to the right

            // Add all the components to the question details
            questionDetails.getChildren().addAll(userBox, questionTextArea, buttonBox);
            questionDetails.setAlignment(Pos.CENTER_LEFT);
questionDetails.setPrefWidth(1000); // or any other value that suits your needs
            // Add the question details to the question box
            questionBox.getChildren().addAll(questionDetails, deleteEditBox);
            questionBox.setPadding(new Insets(0, 0, 0, 200));
            questionBox.setHgrow(questionDetails, Priority.ALWAYS);
            // Add the question box to the question container 
        
  questionContainer.setPrefWidth(1200);
            questionContainer.getChildren().add(questionBox);

           
            /*************************************/
            
            
            
            



        }
    } else {
        Label label = new Label("il n'a pas de questions");
label.setStyle("-fx-padding: 10px;"); // add some padding to the label

// Create a StackPane layout and add the label to it
StackPane root = new StackPane(label);

// Set the alignment of the StackPane to center
root.setAlignment(Pos.CENTER);

// Create a border for the label
Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
label.setBorder(border);

questionContainer.getChildren().add(root);


    }
}      
    }
    

