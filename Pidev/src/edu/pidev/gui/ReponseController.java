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
public class ReponseController implements Initializable {


private int quest_id;
    private Label qsd;
    @FXML
    private Label username;
    @FXML
    private Button ref;
    @FXML
    private Button repBTN;
    @FXML
    private TextField questi;
    @FXML
    private Label username1;
    @FXML
    private ImageView userlabel;
    @FXML
    private Label username2;

    public void setQuesti(String questi) {
        this.questi.setText(questi);
    }

    public void setUsername1(String username1) {
        this.username1.setText(username1);
    }


    public void setQsd(Label qsd) {
        this.qsd = qsd;
    }
    

    public void setQuest_id(int i) {
        this.quest_id = i;
    }
    

@FXML
private VBox questionContainer;
private VBox reponsecontainer;
   questionCRUD qc = new questionCRUD();
    reponseCRUD rc = new reponseCRUD();
    @FXML
    private Circle profilepicture;
    @FXML
    private ScrollBar scroll;
    private TextField recherche;
@Override
public void initialize(URL url, ResourceBundle rb) {
    System.out.println("ssssssss"+questi.getText());
    scroll.valueProperty().addListener((observable, oldValue, newValue) -> {
    // Calculate the new vertical scroll position of the questionContainer
    double scrollPosition = (questionContainer.getHeight() - scroll.getHeight()) * newValue.doubleValue();
    
    // Set the vertical scroll position of the questionContainer
    questionContainer.setLayoutY(-scrollPosition);

});

    displayrep();
}



private void deletereponse(String content) {
    
    
     Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Delete reponse");
    alert.setHeaderText("Are you sure you want to delete this reponse?");
    alert.setContentText("This action cannot be undone.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
  rc.deleteReponse(content);
        displayrep();
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
        displayrep();
    }
}

    public void setUsername2(String username2) {
        this.username2.setText(username2);
    }
  

private void likeQuestion(question q) {
   q.setLikes(q.getLikes()+1);
}

private void dislikeQuestion(question q) {
  q.incrementDislikes();
}

public void displayrep() {
    // Clear existing questions
    questionContainer.getChildren().clear();

    List<reponse> questions = rc.display();
    if (questions != null && !questions.isEmpty()) {
        for (reponse q : questions) {
            HBox questionBox = new HBox();
            VBox questionDetails = new VBox();

            // Add user icon and label
            Image userIcon = new Image("file:///C:/Users/DELL/Downloads/use.png");
            ImageView userImageView = new ImageView(userIcon);
            userImageView.setFitWidth(40);
            userImageView.setFitHeight(40);
            int anon=qc.hidden(q.getId());
            
            /*************** log hide_name **********/

ref.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
ref.setOnMouseEntered(e -> {
    ref.setStyle("-fx-background-color: #0062cc;" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});

ref.setOnMouseExited(e -> {
    ref.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});
repBTN.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
repBTN.setOnMouseEntered(e -> {
    repBTN.setStyle("-fx-background-color: #0062cc;" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});

repBTN.setOnMouseExited(e -> {
    repBTN.setStyle(" -fx-background-color: #007bff; /* Blue color */\n" +
"    -fx-text-fill: white; /* White text color */\n" +
"    -fx-font-size: 16px; /* Font size */\n" +
"    -fx-background-radius: 20px; /* Rounded corners */");
});
          String anonym="";
            if(anon==1){ 
            anonym="anonymous";}
                if(anon==0){          anonym="user";}
            Label userLabel = new Label(anonym+" posted this on " + q.getRep_date_pub());
userLabel.setPrefWidth(500); // or any other value that suits your needs
questionDetails.setPrefWidth(1000); // or any other value that suits your needs

            HBox userBox = new HBox();
            
            userBox.getChildren().addAll(userImageView, userLabel);

            // Add question text area
            TextArea questionTextArea = new TextArea(q.getRep_contenu());
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
                String text=q.getRep_contenu();
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





            HBox buttonBox = new HBox();
            buttonBox.setSpacing(10);
            buttonBox.getChildren().addAll(likeButton, dislikeButton,twitterBTN,fbButton);
            dislikeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

            likeButton.setOnAction(e -> {
                rc.editLIKE(q.getId(), q.getLikes()+1);
                likeButton.setText("👍 " + q.getLikes());
                dislikeButton.setDisable(true); 
                displayrep();// Disable the dislike button
            });
            dislikeButton.setOnAction(e -> {
  rc.editDISLIKE(q.getId(), q.getDislikes()+1);
  dislikeButton.setText("👎 " + q.getDislikes());
                likeButton.setDisable(true); // Disable the like button
            displayrep();});

            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");
            deleteButton.setPrefWidth(200);
            deleteButton.setOnAction(event -> deletereponse(q.getRep_contenu()));

            Button editButton = new Button("Edit");
            editButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            editButton.setPrefWidth(200);
            editButton.setOnAction(event -> editreponse(q));

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
            displayrep();
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
            displayrep();
        }
    }
}


    
    
 

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
            Button helloButton = new Button("Hello");
helloButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-weight: bold;");

            HBox buttonBox = new HBox();
            buttonBox.setSpacing(10);
            buttonBox.getChildren().addAll(likeButton, dislikeButton,helloButton);
            dislikeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

            likeButton.setOnAction(e -> {
                qc.editLIKE(q.getId(), q.getLikes()+1);
                likeButton.setText("👍 " + q.getLikes());
                dislikeButton.setDisable(true); 
                displayrep();// Disable the dislike button
            });
            dislikeButton.setOnAction(e -> {
  qc.editDISLIKE(q.getId(), q.getDislikes()+1);
  dislikeButton.setText("👎 " + q.getDislikes());
                likeButton.setDisable(true); // Disable the like button
            displayrep();});

            Button deleteButton2 = new Button("Delete");
            deleteButton2.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");
            deleteButton2.setPrefWidth(200);
            deleteButton2.setOnAction(e-> deleteQuestion(q.getQues_contenu()));
            /**** check with mr ******
            deleteButton.setOnAction(event -> deletereponse(q.getQues_contenu()));
            **** check with mr ******/

            Button editButton = new Button("Edit");
            editButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            editButton.setPrefWidth(200);

            HBox deleteEditBox = new HBox();
            deleteEditBox.setSpacing(10); // add some spacing between buttons
            deleteEditBox.getChildren().addAll(deleteButton2, editButton);
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
            
            
            
            
            
 questionTextArea.setOnMouseClicked(e -> {
  
     

     
      try {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("addGUI.fxml"));
        Parent root = loader.load();
     PublicationController publicationController = loader.getController();
     publicationController.display(q.getId());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
});                                 //CHECK WITH MR , CANT PASS VARIABLE 


        }
    } else {
        Label label = new Label("il n'a pas de questions avec ce contenu");
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

    @FXML
    private void ref(ActionEvent event) {
        
        //refresh
        displayrep();
    }

    @FXML
    private void rep(ActionEvent event) {
        
        //add reponse
          try {
            Parent root = FXMLLoader.load(getClass().getResource("addreponse.fxml")); // Load the FXML file for the second window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addreponse.fxml"));
            AddreponseController addreponseController = loader.getController();
 
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show(); 
        } catch (IOException ex) {
            Logger.getLogger(PiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }
    

