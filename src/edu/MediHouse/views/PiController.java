/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import edu.MediHouse.entities.Users;
import edu.MediHouse.entities.question;
import edu.MediHouse.entities.reponse1;
import edu.MediHouse.services.ServiceUser;
import edu.MediHouse.services.questionCRUD;
import edu.MediHouse.services.reponse1CRUD;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
    static int que;
    int badWordCount = 0;
    int BAN_DURATION = 60;
    private ScheduledExecutorService scheduler;
    ServiceUser U =new ServiceUser();
    Users u = new Users();
    @FXML
    private VBox questionContainer;
    private VBox reponsecontainer;
    questionCRUD qc = new questionCRUD();
    reponse1CRUD rc = new reponse1CRUD();
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
    public static Vector<Integer> myVector = new Vector<Integer>();
    public static Vector<String> myVector2 = new Vector<String>();
    @FXML
    private ChoiceBox<String> categ;

    private String[] field = {"Médecine générale", "Médecine dentaire", "Pédiatrie", "Médecine interne", " Je ne sais pas"};
    @FXML
    private Button catego;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        u=U.getUserByEmail(InterfaceLogineeController.iduserglobal);
        categ.setStyle("-fx-background-color: #ffffff; -fx-font-size: 14px; -fx-font-family: Arial; -fx-text-fill: #333333; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-padding: 5px; -fx-pref-width: 200px;");
        catego.setStyle("-fx-background-color: #2196F3; -fx-border-color: #1565C0; -fx-border-radius: 5px; -fx-padding: 8px 12px; -fx-graphic: url('confirm-icon.png'); -fx-background-size: 20px; -fx-background-position: center;");

        categ.getItems().addAll(field);
        categ.getSelectionModel().selectFirst();
        scroll.valueProperty().addListener((observable, oldValue, newValue) -> {/////////SCROLLL/////////////////////////////////////////////////////////
            // Calculate the new vertical scroll position of the questionContainer
            double scrollPosition = (questionContainer.getHeight() - scroll.getHeight()) * newValue.doubleValue();

            // Set the vertical scroll position of the questionContainer
            questionContainer.setLayoutY(-scrollPosition);

        });
        display();
    }

    public static String translate(String text, String lang) {
        String langPair = "";
        if (lang == "عربية") {
            langPair = "fr|ar";
        }
        if (lang == "english") {
            langPair = "fr|en";
        }
        if (lang == "中国人") {
            langPair = "fr|zh-CN";
        }
        if (lang == "español") {
            langPair = "fr|es";
        }
        String encodedText = URLEncoder.encode(text);

        try {
            URL url = new URL("https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + langPair.split("\\|")[0] + "&tl=" + langPair.split("\\|")[1] + "&dt=t&q=" + encodedText);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();

            String translatedText = parseGoogleTranslateResponse(response.toString());
            return translatedText;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String parseGoogleTranslateResponse(String response) {
        String[] parts = response.split("\",\"");
        String translation = parts[0].substring(4);
        return translation;
    }

    final boolean[] likePressed = {false};
    final boolean[] dislikePressed = {false};

    private void deletereponse(String content) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete reponse");
        alert.setHeaderText("confirmation?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
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
        alert.setTitle("supprimer Question");
        alert.setHeaderText("confirmation?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
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
                myVector.add(q.getId());
                myVector2.add(q.getQues_contenu());
                HBox questionBox = new HBox();
                VBox questionDetails = new VBox();

                // Add user icon and label
                Image userIcon = new Image("file:///C:/Users/DELL/Downloads/use.png");
                ImageView userImageView = new ImageView(userIcon);
                userImageView.setFitWidth(40);
                userImageView.setFitHeight(40);
                int anon = qc.hidden(q.getId());

                /**
                 * ************* log hide_name *********
                 */
                rech.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                        + "    -fx-text-fill: white; /* White text color */\n"
                        + "    -fx-font-size: 16px; /* Font size */\n"
                );
                rech.setOnMouseEntered(e -> {
                    rech.setStyle("-fx-background-color: #0062cc;"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n");
                });
                ImageView icon = new ImageView(new Image("file:/C:/Users/DELL/Pictures/icon2.png"));

                // Set the size of the icon
                icon.setFitHeight(45);
                icon.setFitWidth(50);

                // Set the icon as the graphic for the button
                rech.setGraphic(icon);
                rech.setOnMouseExited(e -> {
                    rech.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n");
                });
                refresh.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                        + "    -fx-text-fill: white; /* White text color */\n"
                        + "    -fx-font-size: 16px; /* Font size */\n"
                        + "    -fx-background-radius: 20px; /* Rounded corners */");
                refresh.setOnMouseEntered(e -> {
                    refresh.setStyle("-fx-background-color: #0062cc;"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });

                refresh.setOnMouseExited(e -> {
                    refresh.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });
                ADDbtn.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                        + "    -fx-text-fill: white; /* White text color */\n"
                        + "    -fx-font-size: 16px; /* Font size */\n"
                        + "    -fx-background-radius: 20px; /* Rounded corners */");
                ADDbtn.setOnMouseEntered(e -> {
                    ADDbtn.setStyle("-fx-background-color: #0062cc;"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });

                ADDbtn.setOnMouseExited(e -> {
                    ADDbtn.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });
                String anonym = "";
                if (anon == 1) {
                    anonym = "anonymous";
                }
                if (anon == 0) {
                    anonym = "user";
                }
                Label userLabel = new Label(anonym + " a publie le " + q.getQues_date_pub());
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
                Button twitterBTN = new Button("Partager sur twitter");
                twitterBTN.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-weight: bold;");
                twitterBTN.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            // create the Twitter share link with pre-filled content
                            String text = q.getQues_contenu();
                            String encodedText = URLEncoder.encode(text, "UTF-8");
                            String shareLink = "https://twitter.com/intent/tweet?text=" + encodedText;
                            try {
                                // open the link in the default browser
                                Desktop.getDesktop().browse(new URI(shareLink));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(PiController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                Button fbButton = new Button("Share on Facebook");
                Button translate = new Button("Traduction");
                fbButton.setStyle("-fx-background-color: #3B5998; -fx-text-fill: white; -fx-font-weight: bold;");
                fbButton.setOnAction(event -> shareHelloOnFacebook());
                ChoiceBox<String> tran = new ChoiceBox<>();
                tran.getItems().addAll("english", "عربية", "español", "中国人");
                tran.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");
                translate.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");

                tran.setValue(tran.getItems().get(0));
                if (tran.getValue() == "english") {
                    translate.setText("Translate");

                }
                if (tran.getValue() == "中国人") {
                    translate.setText("翻译");

                }
                if (tran.getValue() == "español") {
                    translate.setText("Traducir");

                }
                if (tran.getValue() == "عربية") {
                    translate.setText("ترجمة");

                }

                translate.setOnAction(event -> {
                    String text = translate(q.getQues_contenu(), tran.getValue());
                    questionTextArea.setText(text);

                });

                Button rep = new Button("Reponse");
                rep.setStyle("-fx-background-color: #3B5998; -fx-text-fill: white; -fx-font-weight: bold;");
                /*rep.setOnAction(event -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("reponse1.fxml"));
        Parent root = loader.load();
        
        ReponseController controller = loader.getController();
        controller.setQuesti(q.getQues_contenu());
        controller.setUsername2("user a publié");
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
});*/
                rep.setOnAction(e -> {
                    // create a new window to display the full content
                    Stage stage = new Stage();
                    VBox vbox = new VBox();
                    vbox.setPadding(new Insets(20));
                    vbox.setSpacing(10);
                    vbox.setStyle("-fx-background-color: ADD8E6;");
                    Image image = new Image("file:C:/Users/DELL/Downloads/logo2.png");

// Create an ImageView to display the image
                    ImageView imageView = new ImageView(image);

// Set the position of the ImageView to top left corner
                    VBox.setMargin(imageView, new Insets(10, 0, 0, 10));
                    vbox.setPrefSize(400, 300);

// Add the ImageView to the VBox
                    vbox.getChildren().add(imageView);

// add the labels to the VBox
                    // create an HBox to hold the author and date information
                    HBox infoBox = new HBox();
                    infoBox.setSpacing(10);

                    Label dateLabel = new Label("Date de Publication: " + q.getQues_date_pub().toString());

                    dateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13pt;");

                    infoBox.getChildren().addAll(dateLabel);
                    vbox.getChildren().add(infoBox);

                    TextField contentLabel = new TextField(q.getQues_contenu());
                    contentLabel.setEditable(false);

                    vbox.getChildren().add(contentLabel);

                    Label repLabel = new Label("Il ya " + "reponse");
                    repLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

                    repLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13pt;");

                    repLabel.setWrapText(true);
                    vbox.getChildren().add(repLabel);

// add the likes and dislikes HBox to the main VBox
                    int idpub = q.getId();
                    List<reponse1> commentaires = null;
                    reponse1CRUD commentaireService = new reponse1CRUD();

                    commentaires = commentaireService.getResponsesByQuestId(q.getId());
                    for (reponse1 com : commentaires) {
                        ImageView userIcons = new ImageView("file:C:/Users/DELL/Downloads/use.png");
                        userIcons.setFitHeight(20); // Set the height of the icon
                        userIcons.setFitWidth(20); // Set the width of the icon

// Create a Label object for the "user a repondu" text
                        Label info = new Label("user a repondu le " + com.getRep_date_pub() + ":");
                        info.setGraphic(userIcons);
                        TextField commentaireLabel = new TextField(com.getRep_contenu());
                        Button delete = new Button("supprimer");
                        Button edit = new Button("modifiez");
                        delete.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");

                        edit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        Button tr = new Button("Traduction");
                        ChoiceBox<String> traan = new ChoiceBox<>();
                        traan.getItems().addAll("english", "عربية", "español", "中国人");
                        traan.setValue(traan.getItems().get(0));
                        traan.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");
                        tr.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");

                        if (traan.getValue() == "english") {
                            tr.setText("Translate");

                        }
                        if (traan.getValue() == "中国人") {
                            tr.setText("翻译");

                        }
                        if (traan.getValue() == "español") {
                            tr.setText("Traducir");

                        }
                        if (traan.getValue() == "عربية") {
                            tr.setText("ترجمة");

                        }

                        tr.setOnAction(a -> {
                            String text = translate(com.getRep_contenu(), traan.getValue());
                            commentaireLabel.setText(text);

                        });
                        HBox buttonBox1 = new HBox();
                        delete.setOnAction(event -> {
                            deletereponse(com.getRep_contenu());
                        });
                        edit.setOnAction(event -> {
                            editreponse(com);
                        });
                        buttonBox1.setSpacing(10);

                        buttonBox1.getChildren().addAll(delete, edit, traan, tr);
                        commentaireLabel.setEditable(false);
                        vbox.getChildren().add(info);
                        vbox.getChildren().add(commentaireLabel);
                        vbox.getChildren().add(buttonBox1);

                    }

// create a label for the commentaires
                    Label commentairesLabel = new Label("Vous pouvez ajoutez une reponse !");
                    commentairesLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
                    vbox.getChildren().add(commentairesLabel);

// display the commentaires
                    // create a text field for adding a new comment
                    TextField commentaireField = new TextField();
                    commentaireField.setPromptText("Ajouter une reponse");
                    vbox.getChildren().add(commentaireField);

                    Button submitButton = new Button("Reponse");

                    submitButton.setStyle("-fx-background-color: #00ABFF; -fx-text-fill: white;");

                    submitButton.setOnAction(event -> {
                        reponse1CRUD qcd = new reponse1CRUD();
                        Date date = new Date(System.currentTimeMillis());
                        reponse1 r = new reponse1();
                        String content = commentaireField.getText();
                        // Check for empty content
                        if (content.isEmpty()) {
                            Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
                            emptyAlert.setTitle("Error");
                            emptyAlert.setHeaderText("reponse ne peut pas etre vide");
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
                                    submitButton.setDisable(true); // Disable post button
                                    long banEndTime = System.currentTimeMillis() + BAN_DURATION * 1000;
                                    scheduler = Executors.newScheduledThreadPool(1);

                                    // Schedule task to update post button text every second
                                    scheduler.scheduleAtFixedRate(() -> {
                                        long remainingTime = banEndTime - System.currentTimeMillis();
                                        if (remainingTime <= 0) {
                                            // Enable post button and reset bad word count
                                            Platform.runLater(() -> {
                                                submitButton.setDisable(false);
                                                submitButton.setText("publier");
                                                badWordCount = 0;
                                            });
                                            scheduler.shutdown();
                                            return;
                                        }
                                        // Update post button text with remaining time
                                        Platform.runLater(() -> {
                                            submitButton.setText("banni(e) pour " + remainingTime / 1000 + "s");
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

                        r.setQuest_id(q.getId());
                        r.setRep_contenu(content);
                        r.setRep_date_pub(date);
                        r.setId_user(u.getId());//////////////////////////////////////////////***********************************************/////////////////////////
                        // Create a confirmation alert
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("confirmation?");

                        // Show the alert and wait for the user's response
                        Optional<ButtonType> result = alert.showAndWait();

                        // If the user confirms, add the question to the database and close the window
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            qcd.addEntity2(r);

                            // Close the window
                            Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage1.close();
                        }
                    });

                    HBox buttonBox = new HBox();
                    buttonBox.setSpacing(10);
                    buttonBox.getChildren().addAll(submitButton);
                    vbox.getChildren().add(buttonBox);

                    // add the vbox to the scene and show the window
                    Scene scene = new Scene(vbox, 500, 600);
                    stage.setScene(scene);
                    stage.show();

                });

                HBox buttonBox = new HBox();
                buttonBox.setSpacing(10);
                buttonBox.getChildren().addAll(rep, likeButton, dislikeButton, twitterBTN, translate, tran);
                dislikeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

                likeButton.setOnAction(e -> {
                    if (!likePressed[0]) {
                        if (dislikePressed[0]) {
                            qc.editDISLIKE(q.getId(), q.getDislikes() - 1);
                            dislikeButton.setText("👎 " + q.getDislikes());
                            dislikePressed[0] = false;
                        }
                        qc.editLIKE(q.getId(), q.getLikes() + 1);
                        likeButton.setText("👍 " + q.getLikes());
                        dislikeButton.setDisable(true);
                        likePressed[0] = true;
                    } else {
                        qc.editLIKE(q.getId(), q.getLikes() - 1);
                        likeButton.setText("👍 " + q.getLikes());
                        likePressed[0] = false;
                    }
                    display();
                });

                dislikeButton.setOnAction(e -> {
                    if (!dislikePressed[0]) {
                        if (likePressed[0]) {
                            qc.editLIKE(q.getId(), q.getLikes() - 1);
                            likeButton.setText("👍 " + q.getLikes());
                            likePressed[0] = false;
                        }
                        qc.editDISLIKE(q.getId(), q.getDislikes() + 1);
                        dislikeButton.setText("👎 " + q.getDislikes());
                        likeButton.setDisable(true);
                        dislikePressed[0] = true;
                    } else {
                        qc.editDISLIKE(q.getId(), q.getDislikes() - 1);
                        dislikeButton.setText("👎 " + q.getDislikes());
                        dislikePressed[0] = false;
                    }
                    display();
                });

                Button deleteButton = new Button("supprimer");
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

                /**
                 * **********************************
                 */
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
        q.setLikes(q.getLikes() + 1);
    }

    private void dislikeQuestion(question q) {
        q.incrementDislikes();
    }

    private void editQuestion(question q) {
        TextInputDialog dialog = new TextInputDialog(q.getQues_contenu());
        dialog.setTitle("Edit Question");
        dialog.setHeaderText("modifiez votre question:");
        dialog.setContentText("Question:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty()) {
            String edited = result.get();

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "confirmation?");
            Optional<ButtonType> confirmResult = confirmation.showAndWait();
            if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
                qc.editQuestion(q.getId(), edited);
                display();
            }
        }
    }

    private void editrep(reponse1 q) {
        TextInputDialog dialog = new TextInputDialog(q.getRep_contenu());
        dialog.setTitle("Edit reponse");
        dialog.setHeaderText("modifiez votre question:");
        dialog.setContentText("Question:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty()) {
            String edited = result.get();

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "confirmation?");
            Optional<ButtonType> confirmResult = confirmation.showAndWait();
            if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
                rc.editReponse(q.getId(), edited);
                display();
            }
        }
    }

    private void editreponse(reponse1 q) {
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

        String filter = recherche.getText();
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
                int anon = qc.hidden(q.getId());

                /**
                 * ************* log hide_name *********
                 */
                rech.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                        + "    -fx-text-fill: white; /* White text color */\n"
                        + "    -fx-font-size: 16px; /* Font size */\n"
                );
                rech.setOnMouseEntered(e -> {
                    rech.setStyle("-fx-background-color: #0062cc;"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n");
                });
                ImageView icon = new ImageView(new Image("file:/C:/Users/DELL/Pictures/icon2.png"));

                // Set the size of the icon
                icon.setFitHeight(45);
                icon.setFitWidth(50);

                // Set the icon as the graphic for the button
                rech.setGraphic(icon);
                rech.setOnMouseExited(e -> {
                    rech.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n");
                });
                refresh.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                        + "    -fx-text-fill: white; /* White text color */\n"
                        + "    -fx-font-size: 16px; /* Font size */\n"
                        + "    -fx-background-radius: 20px; /* Rounded corners */");
                refresh.setOnMouseEntered(e -> {
                    refresh.setStyle("-fx-background-color: #0062cc;"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });

                refresh.setOnMouseExited(e -> {
                    refresh.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });
                ADDbtn.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                        + "    -fx-text-fill: white; /* White text color */\n"
                        + "    -fx-font-size: 16px; /* Font size */\n"
                        + "    -fx-background-radius: 20px; /* Rounded corners */");
                ADDbtn.setOnMouseEntered(e -> {
                    ADDbtn.setStyle("-fx-background-color: #0062cc;"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });

                ADDbtn.setOnMouseExited(e -> {
                    ADDbtn.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });
                String anonym = "";
                if (anon == 1) {
                    anonym = "anonymous";
                }
                if (anon == 0) {
                    anonym = "user";
                }
                Label userLabel = new Label(anonym + " a publié le  " + q.getQues_date_pub());
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
                        String text = q.getQues_contenu();
                        String shareLink = "https://twitter.com/intent/tweet?text=" + text;

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

                Button rep = new Button("Reponse");
                rep.setStyle("-fx-background-color: #3B5998; -fx-text-fill: white; -fx-font-weight: bold;");

                rep.setOnAction(e -> {
                    // create a new window to display the full content
                    Stage stage = new Stage();
                    VBox vbox = new VBox();
                    vbox.setPadding(new Insets(20));
                    vbox.setSpacing(10);
                    vbox.setStyle("-fx-background-color: ADD8E6;");
                    Image image = new Image("file:C:/Users/DELL/Downloads/logo2.png");

// Create an ImageView to display the image
                    ImageView imageView = new ImageView(image);

// Set the position of the ImageView to top left corner
                    VBox.setMargin(imageView, new Insets(10, 0, 0, 10));
                    vbox.setPrefSize(400, 300);

// Add the ImageView to the VBox
                    vbox.getChildren().add(imageView);

// add the labels to the VBox
                    // create an HBox to hold the author and date information
                    HBox infoBox = new HBox();
                    infoBox.setSpacing(10);

                    Label dateLabel = new Label("Date de Publication: " + q.getQues_date_pub().toString());

                    dateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13pt;");

                    infoBox.getChildren().addAll(dateLabel);
                    vbox.getChildren().add(infoBox);

                    TextField contentLabel = new TextField(q.getQues_contenu());
                    contentLabel.setEditable(false);

                    vbox.getChildren().add(contentLabel);

                    Label repLabel = new Label("Il ya " + "reponse");
                    repLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

                    repLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13pt;");

                    repLabel.setWrapText(true);
                    vbox.getChildren().add(repLabel);

// add the likes and dislikes HBox to the main VBox
                    int idpub = q.getId();
                    List<reponse1> commentaires = null;
                    reponse1CRUD commentaireService = new reponse1CRUD();

                    commentaires = commentaireService.getResponsesByQuestId(q.getId());
                    for (reponse1 com : commentaires) {
                        ImageView userIcons = new ImageView("file:C:/Users/DELL/Downloads/use.png");
                        userIcons.setFitHeight(20); // Set the height of the icon
                        userIcons.setFitWidth(20); // Set the width of the icon

// Create a Label object for the "user a repondu" text
                        Label info = new Label("user a repondu le " + com.getRep_date_pub() + ":");
                        info.setGraphic(userIcons);
                        TextField commentaireLabel = new TextField(com.getRep_contenu());
                        Button delete = new Button("supprimer");
                        Button edit = new Button("modifiez");
                        delete.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");

                        edit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

                        HBox buttonBox1 = new HBox();
                        delete.setOnAction(a -> {
                            deletereponse(com.getRep_contenu());
                        });
                        edit.setOnAction(a -> {
                            editreponse(com);
                        });
                        buttonBox1.setSpacing(10);
                        Button translate = new Button("Traduction");
                        ChoiceBox<String> tran = new ChoiceBox<>();
                        tran.getItems().addAll("english", "عربية", "español", "中国人");
                        tran.setValue(tran.getItems().get(0));
                        tran.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");
                        translate.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");

                        if (tran.getValue() == "english") {
                            translate.setText("Translate");

                        }
                        if (tran.getValue() == "中国人") {
                            translate.setText("翻译");

                        }
                        if (tran.getValue() == "español") {
                            translate.setText("Traducir");

                        }
                        if (tran.getValue() == "عربية") {
                            translate.setText("ترجمة");

                        }

                        translate.setOnAction(a -> {
                            String text = translate(com.getRep_contenu(), tran.getValue());
                            commentaireLabel.setText(text);

                        });
                        buttonBox1.getChildren().addAll(delete, edit, translate, tran);
                        commentaireLabel.setEditable(false);
                        vbox.getChildren().add(info);
                        vbox.getChildren().add(commentaireLabel);
                        vbox.getChildren().add(buttonBox1);

                    }

// create a label for the commentaires
                    Label commentairesLabel = new Label("Vous pouvez ajoutez une reponse !");
                    commentairesLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
                    vbox.getChildren().add(commentairesLabel);

// display the commentaires
                    // create a text field for adding a new comment
                    TextField commentaireField = new TextField();
                    commentaireField.setPromptText("Ajouter une reponse");
                    vbox.getChildren().add(commentaireField);

                    Button submitButton = new Button("Reponse");

                    submitButton.setStyle("-fx-background-color: #00ABFF; -fx-text-fill: white;");

                    submitButton.setOnAction(a -> {
                        reponse1CRUD qcd = new reponse1CRUD();
                        Date date = new Date(System.currentTimeMillis());
                        reponse1 r = new reponse1();
                        String content = commentaireField.getText();
                        // Check for empty content
                        if (content.isEmpty()) {
                            Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
                            emptyAlert.setTitle("Error");
                            emptyAlert.setHeaderText("reponse ne peut pas etre vide");
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
                                    submitButton.setDisable(true); // Disable post button
                                    long banEndTime = System.currentTimeMillis() + BAN_DURATION * 1000;
                                    scheduler = Executors.newScheduledThreadPool(1);

                                    // Schedule task to update post button text every second
                                    scheduler.scheduleAtFixedRate(() -> {
                                        long remainingTime = banEndTime - System.currentTimeMillis();
                                        if (remainingTime <= 0) {
                                            // Enable post button and reset bad word count
                                            Platform.runLater(() -> {
                                                submitButton.setDisable(false);
                                                submitButton.setText("publier");
                                                badWordCount = 0;
                                            });
                                            scheduler.shutdown();
                                            return;
                                        }
                                        // Update post button text with remaining time
                                        Platform.runLater(() -> {
                                            submitButton.setText("banni(e) pour " + remainingTime / 1000 + "s");
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

                        r.setQuest_id(q.getId());
                        r.setRep_contenu(content);
                        r.setRep_date_pub(date);
                        r.setId_user(u.getId());/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        // Create a confirmation alert
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("confirmation");

                        // Show the alert and wait for the user's response
                        Optional<ButtonType> result = alert.showAndWait();

                        // If the user confirms, add the question to the database and close the window
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            qcd.addEntity2(r);

                            // Close the window
                            Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage1.close();
                        }
                    });

                    HBox buttonBox = new HBox();
                    buttonBox.setSpacing(10);
                    buttonBox.getChildren().addAll(submitButton);
                    vbox.getChildren().add(buttonBox);

                    // add the vbox to the scene and show the window
                    Scene scene = new Scene(vbox);
                    stage.setScene(scene);
                    stage.show();

                });

                Button translate = new Button("Traduction");
                ChoiceBox<String> tran = new ChoiceBox<>();
                tran.getItems().addAll("english", "عربية", "español", "中国人");
                tran.setValue(tran.getItems().get(0));
                tran.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");
                translate.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");

                if (tran.getValue() == "english") {
                    translate.setText("Translate");

                }
                if (tran.getValue() == "中国人") {
                    translate.setText("翻译");

                }
                if (tran.getValue() == "español") {
                    translate.setText("Traducir");

                }
                if (tran.getValue() == "عربية") {
                    translate.setText("ترجمة");

                }

                translate.setOnAction(a -> {
                    String text = translate(q.getQues_contenu(), tran.getValue());
                    questionTextArea.setText(text);

                });
                HBox buttonBox = new HBox();
                buttonBox.setSpacing(10);
                buttonBox.getChildren().addAll(rep, likeButton, dislikeButton, twitterBTN, translate, tran);
                dislikeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

                likeButton.setOnAction(e -> {
                    if (!likePressed[0]) {
                        if (dislikePressed[0]) {
                            qc.editDISLIKE(q.getId(), q.getDislikes() - 1);
                            dislikeButton.setText("👎 " + q.getDislikes());
                            dislikePressed[0] = false;
                        }
                        qc.editLIKE(q.getId(), q.getLikes() + 1);
                        likeButton.setText("👍 " + q.getLikes());
                        dislikeButton.setDisable(true);
                        likePressed[0] = true;
                    } else {
                        qc.editLIKE(q.getId(), q.getLikes() - 1);
                        likeButton.setText("👍 " + q.getLikes());
                        likePressed[0] = false;
                    }
                    display();
                });

                dislikeButton.setOnAction(e -> {
                    if (!dislikePressed[0]) {
                        if (likePressed[0]) {
                            qc.editLIKE(q.getId(), q.getLikes() - 1);
                            likeButton.setText("👍 " + q.getLikes());
                            likePressed[0] = false;
                        }
                        qc.editDISLIKE(q.getId(), q.getDislikes() + 1);
                        dislikeButton.setText("👎 " + q.getDislikes());
                        likeButton.setDisable(true);
                        dislikePressed[0] = true;
                    } else {
                        qc.editDISLIKE(q.getId(), q.getDislikes() - 1);
                        dislikeButton.setText("👎 " + q.getDislikes());
                        dislikePressed[0] = false;
                    }
                    display();
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

                /**
                 * **********************************
                 */
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

    @FXML
    private void categor(ActionEvent event) {

        // Clear existing questions
        questionContainer.getChildren().clear();

        List<question> questions = qc.catego(categ.getValue());
        if (questions != null && !questions.isEmpty()) {
            for (question q : questions) {
                myVector.add(q.getId());
                myVector2.add(q.getQues_contenu());
                HBox questionBox = new HBox();
                VBox questionDetails = new VBox();

                // Add user icon and label
                Image userIcon = new Image("file:///C:/Users/DELL/Downloads/use.png");
                ImageView userImageView = new ImageView(userIcon);
                userImageView.setFitWidth(40);
                userImageView.setFitHeight(40);
                int anon = qc.hidden(q.getId());

                /**
                 * ************* log hide_name *********
                 */
                rech.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                        + "    -fx-text-fill: white; /* White text color */\n"
                        + "    -fx-font-size: 16px; /* Font size */\n"
                );
                rech.setOnMouseEntered(e -> {
                    rech.setStyle("-fx-background-color: #0062cc;"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n");
                });
                ImageView icon = new ImageView(new Image("file:/C:/Users/DELL/Pictures/icon2.png"));

                // Set the size of the icon
                icon.setFitHeight(45);
                icon.setFitWidth(50);

                // Set the icon as the graphic for the button
                rech.setGraphic(icon);
                rech.setOnMouseExited(e -> {
                    rech.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n");
                });
                refresh.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                        + "    -fx-text-fill: white; /* White text color */\n"
                        + "    -fx-font-size: 16px; /* Font size */\n"
                        + "    -fx-background-radius: 20px; /* Rounded corners */");
                refresh.setOnMouseEntered(e -> {
                    refresh.setStyle("-fx-background-color: #0062cc;"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });

                refresh.setOnMouseExited(e -> {
                    refresh.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });
                ADDbtn.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                        + "    -fx-text-fill: white; /* White text color */\n"
                        + "    -fx-font-size: 16px; /* Font size */\n"
                        + "    -fx-background-radius: 20px; /* Rounded corners */");
                ADDbtn.setOnMouseEntered(e -> {
                    ADDbtn.setStyle("-fx-background-color: #0062cc;"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });

                ADDbtn.setOnMouseExited(e -> {
                    ADDbtn.setStyle(" -fx-background-color: #007bff; /* Blue color */\n"
                            + "    -fx-text-fill: white; /* White text color */\n"
                            + "    -fx-font-size: 16px; /* Font size */\n"
                            + "    -fx-background-radius: 20px; /* Rounded corners */");
                });
                String anonym = "";
                if (anon == 1) {
                    anonym = "anonymous";
                }
                if (anon == 0) {
                    anonym = "user";
                }
                Label userLabel = new Label(anonym + " a publie le " + q.getQues_date_pub());
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
                Button twitterBTN = new Button("Partager sur twitter");
                twitterBTN.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-weight: bold;");
                twitterBTN.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            // create the Twitter share link with pre-filled content
                            String text = q.getQues_contenu();
                            String encodedText = URLEncoder.encode(text, "UTF-8");
                            String shareLink = "https://twitter.com/intent/tweet?text=" + encodedText;
                            try {
                                // open the link in the default browser
                                Desktop.getDesktop().browse(new URI(shareLink));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(PiController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                Button fbButton = new Button("Share on Facebook");
                fbButton.setStyle("-fx-background-color: #3B5998; -fx-text-fill: white; -fx-font-weight: bold;");

                Button rep = new Button("Reponse");
                rep.setStyle("-fx-background-color: #3B5998; -fx-text-fill: white; -fx-font-weight: bold;");
                /*rep.setOnAction(event -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("reponse1.fxml"));
        Parent root = loader.load();
        
        ReponseController controller = loader.getController();
        controller.setQuesti(q.getQues_contenu());
        controller.setUsername2("user a publié");
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
});*/
                rep.setOnAction(e -> {
                    // create a new window to display the full content
                    Stage stage = new Stage();
                    VBox vbox = new VBox();
                    vbox.setPadding(new Insets(20));
                    vbox.setSpacing(10);
                    vbox.setStyle("-fx-background-color: ADD8E6;");
                    Image image = new Image("file:C:/Users/DELL/Downloads/logo2.png");

// Create an ImageView to display the image
                    ImageView imageView = new ImageView(image);

// Set the position of the ImageView to top left corner
                    VBox.setMargin(imageView, new Insets(10, 0, 0, 10));
                    vbox.setPrefSize(400, 300);

// Add the ImageView to the VBox
                    vbox.getChildren().add(imageView);

// add the labels to the VBox
                    // create an HBox to hold the author and date information
                    HBox infoBox = new HBox();
                    infoBox.setSpacing(10);

                    Label dateLabel = new Label("Date de Publication: " + q.getQues_date_pub().toString());

                    dateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13pt;");

                    infoBox.getChildren().addAll(dateLabel);
                    vbox.getChildren().add(infoBox);

                    TextField contentLabel = new TextField(q.getQues_contenu());
                    contentLabel.setEditable(false);

                    vbox.getChildren().add(contentLabel);

                    Label repLabel = new Label("Reponse");
                    repLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

                    repLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13pt;");

                    repLabel.setWrapText(true);
                    vbox.getChildren().add(repLabel);

// add the likes and dislikes HBox to the main VBox
                    int idpub = q.getId();
                    List<reponse1> commentaires = null;
                    reponse1CRUD commentaireService = new reponse1CRUD();

                    commentaires = commentaireService.getResponsesByQuestId(q.getId());
                    for (reponse1 com : commentaires) {
                        ImageView userIcons = new ImageView("file:C:/Users/DELL/Downloads/use.png");
                        userIcons.setFitHeight(20); // Set the height of the icon
                        userIcons.setFitWidth(20); // Set the width of the icon

// Create a Label object for the "user a repondu" text
                        Label info = new Label("user a repondu le " + com.getRep_date_pub() + ":");
                        info.setGraphic(userIcons);
                        TextField commentaireLabel = new TextField(com.getRep_contenu());
                        Button delete = new Button("supprimer");
                        Button edit = new Button("modifiez");
                        delete.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");

                        edit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        Button translate = new Button("Traduction");
                        ChoiceBox<String> tran = new ChoiceBox<>();
                        tran.getItems().addAll("english", "عربية", "español", "中国人");
                        tran.setValue(tran.getItems().get(0));
                        tran.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");
                        translate.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");

                        if (tran.getValue() == "english") {
                            translate.setText("Translate");

                        }
                        if (tran.getValue() == "中国人") {
                            translate.setText("翻译");

                        }
                        if (tran.getValue() == "español") {
                            translate.setText("Traducir");

                        }
                        if (tran.getValue() == "عربية") {
                            translate.setText("ترجمة");

                        }

                        translate.setOnAction(a -> {
                            String text = translate(com.getRep_contenu(), tran.getValue());
                            commentaireLabel.setText(text);

                        });
                        HBox buttonBox1 = new HBox();
                        delete.setOnAction(a -> {
                            deletereponse(com.getRep_contenu());
                        });
                        edit.setOnAction(a -> {
                            editreponse(com);
                        });
                        buttonBox1.setSpacing(10);

                        buttonBox1.getChildren().addAll(delete, edit, tran, translate);
                        commentaireLabel.setEditable(false);
                        vbox.getChildren().add(info);
                        vbox.getChildren().add(commentaireLabel);
                        vbox.getChildren().add(buttonBox1);

                    }

// create a label for the commentaires
                    Label commentairesLabel = new Label("Vous pouvez ajoutez une reponse !");
                    commentairesLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
                    vbox.getChildren().add(commentairesLabel);

// display the commentaires
                    // create a text field for adding a new comment
                    TextField commentaireField = new TextField();
                    commentaireField.setPromptText("Ajouter une reponse");
                    vbox.getChildren().add(commentaireField);

                    Button submitButton = new Button("Repondre");

                    submitButton.setStyle("-fx-background-color: #00ABFF; -fx-text-fill: white;");

                    submitButton.setOnAction(a -> {
                        reponse1CRUD qcd = new reponse1CRUD();
                        Date date = new Date(System.currentTimeMillis());
                        reponse1 r = new reponse1();
                        String content = commentaireField.getText();
                        // Check for empty content
                        if (content.isEmpty()) {
                            Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
                            emptyAlert.setTitle("Error");
                            emptyAlert.setHeaderText("reponse ne peut pas etre vide");
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
                                    submitButton.setDisable(true); // Disable post button
                                    long banEndTime = System.currentTimeMillis() + BAN_DURATION * 1000;
                                    scheduler = Executors.newScheduledThreadPool(1);

                                    // Schedule task to update post button text every second
                                    scheduler.scheduleAtFixedRate(() -> {
                                        long remainingTime = banEndTime - System.currentTimeMillis();
                                        if (remainingTime <= 0) {
                                            // Enable post button and reset bad word count
                                            Platform.runLater(() -> {
                                                submitButton.setDisable(false);
                                                submitButton.setText("publier");
                                                badWordCount = 0;
                                            });
                                            scheduler.shutdown();
                                            return;
                                        }
                                        // Update post button text with remaining time
                                        Platform.runLater(() -> {
                                            submitButton.setText("banni(e) pour " + remainingTime / 1000 + "s");
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

                        r.setQuest_id(q.getId());
                        r.setRep_contenu(content);
                        r.setRep_date_pub(date);
                        r.setId_user(u.getId());///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        // Create a confirmation alert
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("confirmation");

                        // Show the alert and wait for the user's response
                        Optional<ButtonType> result = alert.showAndWait();

                        // If the user confirms, add the question to the database and close the window
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            qcd.addEntity2(r);

                            // Close the window
                            Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage1.close();
                        }
                    });

                    HBox buttonBox = new HBox();
                    buttonBox.setSpacing(10);
                    buttonBox.getChildren().addAll(submitButton);
                    vbox.getChildren().add(buttonBox);

                    // add the vbox to the scene and show the window
                    Scene scene = new Scene(vbox);
                    stage.setScene(scene);
                    stage.show();

                });

                Button translate = new Button("Traduction");
                ChoiceBox<String> tran = new ChoiceBox<>();
                tran.getItems().addAll("english", "عربية", "español", "中国人");
                tran.setValue(tran.getItems().get(0));
                tran.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");
                translate.setStyle("-fx-background-color: #C2DFFF; -fx-font-size: 14px;");

                if (tran.getValue() == "english") {
                    translate.setText("Translate");

                }
                if (tran.getValue() == "中国人") {
                    translate.setText("翻译");

                }
                if (tran.getValue() == "español") {
                    translate.setText("Traducir");

                }
                if (tran.getValue() == "عربية") {
                    translate.setText("ترجمة");

                }

                translate.setOnAction(a -> {
                    String text = translate(q.getQues_contenu(), tran.getValue());
                    questionTextArea.setText(text);

                });

                HBox buttonBox = new HBox();
                buttonBox.setSpacing(10);
                buttonBox.getChildren().addAll(rep, likeButton, dislikeButton, twitterBTN, translate, tran);
                dislikeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

                likeButton.setOnAction(e -> {
                    if (!likePressed[0]) {
                        if (dislikePressed[0]) {
                            qc.editDISLIKE(q.getId(), q.getDislikes() - 1);
                            dislikeButton.setText("👎 " + q.getDislikes());
                            dislikePressed[0] = false;
                        }
                        qc.editLIKE(q.getId(), q.getLikes() + 1);
                        likeButton.setText("👍 " + q.getLikes());
                        dislikeButton.setDisable(true);
                        likePressed[0] = true;
                    } else {
                        qc.editLIKE(q.getId(), q.getLikes() - 1);
                        likeButton.setText("👍 " + q.getLikes());
                        likePressed[0] = false;
                    }
                    display();
                });

                dislikeButton.setOnAction(e -> {
                    if (!dislikePressed[0]) {
                        if (likePressed[0]) {
                            qc.editLIKE(q.getId(), q.getLikes() - 1);
                            likeButton.setText("👍 " + q.getLikes());
                            likePressed[0] = false;
                        }
                        qc.editDISLIKE(q.getId(), q.getDislikes() + 1);
                        dislikeButton.setText("👎 " + q.getDislikes());
                        likeButton.setDisable(true);
                        dislikePressed[0] = true;
                    } else {
                        qc.editDISLIKE(q.getId(), q.getDislikes() - 1);
                        dislikeButton.setText("👎 " + q.getDislikes());
                        dislikePressed[0] = false;
                    }
                    display();
                });

                Button deleteButton = new Button("supprimer");
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

                /**
                 * **********************************
                 */
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
