/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import edu.MediHouse.entities.Users;
import edu.MediHouse.entities.question;
import edu.MediHouse.entities.Reponse1;
import edu.MediHouse.services.ServiceUser;
import edu.MediHouse.services.questionCRUD;
import edu.MediHouse.services.Reponse1CRUD;
import static edu.MediHouse.views.InterfaceAdminController.generatePseudo;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    Reponse1CRUD rc = new Reponse1CRUD();
    ObservableList<question> questions = FXCollections.observableArrayList(qc.display());
    @FXML
    private Button Deletebtn;
    @FXML
    private Button stat;
    @FXML
    private Circle ProfilePic12;
    @FXML
    private Label UserName12;
Users u =new Users();
     ServiceUser su=new ServiceUser();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        test();
         u=su.getUserByEmail(InterfaceLogineeController.iduserglobal);
        Image im = new Image(u.getProfilePicture());
        ImagePattern pattern = new ImagePattern(im);
        ProfilePic12.setFill(pattern);
        ProfilePic12.setStroke(Color.SEAGREEN);
        ProfilePic12.setEffect(new DropShadow(20, Color.BLACK));
       // String pseudo = generatePseudo(u.getNom(), u.getPrenom());
        UserName12.setText(generatePseudo(u.getNom(), u.getPrenom()));
          ProfilePic12.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            profile(event);
                  }
    });

    }

    public void test() {
        stat.setStyle("-fx-background-color: #3F51B5; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        id.setCellValueFactory(new PropertyValueFactory<question, Integer>("id"));
        question.setCellValueFactory(new PropertyValueFactory<question, String>("ques_contenu"));
        date_publication.setCellValueFactory(new PropertyValueFactory<question, Date>("ques_date_pub"));
        likes.setCellValueFactory(new PropertyValueFactory<question, Integer>("likes"));
        dislikes.setCellValueFactory(new PropertyValueFactory<question, Integer>("dislikes"));
        category.setCellValueFactory(new PropertyValueFactory<question, String>("categorie"));
        category.setCellFactory(column -> {
            return new TableCell<question, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setTooltip(null);
                    } else {
                        setText(item);
                        Tooltip tooltip = new Tooltip(item);
                        setTooltip(tooltip);
                    }
                }
            };
        });
        question.setCellFactory(column -> {
            return new TableCell<question, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setTooltip(null);
                    } else {
                        setText(item);
                        Tooltip tooltip = new Tooltip(item);
                        setTooltip(tooltip);
                    }
                }
            };
        });

        // Add the Response column and button to the TableView
        TableColumn<question, Void> responseCol = new TableColumn<>("Response");
        responseCol.setPrefWidth(100);
        Callback<TableColumn<question, Void>, TableCell<question, Void>> cellFactory = new Callback<TableColumn<question, Void>, TableCell<question, Void>>() {
            @Override
            public TableCell<question, Void> call(final TableColumn<question, Void> param) {

                final TableCell<question, Void> cell = new TableCell<question, Void>() {
                    private final Button btn = new Button("voir");

                    @Override
                    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {

                        return super.buildEventDispatchChain(tail); //To change body of generated methods, choose Tools | Templates.
                    }

                    {
                        btn.setStyle("-fx-background-color: #00ABFF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");

                        btn.setOnAction((ActionEvent event) -> {
                            question question = getTableView().getItems().get(getIndex());
                            int questionId = question.getId(); // Get the question ID

                            // Create a new stage for the table view
                            Stage stage = new Stage();
                            stage.setTitle("Responses");

                            // Create the table view
                            TableView<Reponse1> tableView = new TableView<>();

                            // Create the columns for the table view
                            TableColumn<Reponse1, Integer> idColumn = new TableColumn<>("ID");
                            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

                            TableColumn<Reponse1, String> contenuColumn = new TableColumn<>("Contenu");
                            contenuColumn.setCellValueFactory(new PropertyValueFactory<>("rep_contenu"));

                            TableColumn<Reponse1, Date> dateColumn = new TableColumn<>("Date");
                            dateColumn.setCellValueFactory(new PropertyValueFactory<>("rep_date_pub"));

                            TableColumn<Reponse1, Integer> likesColumn = new TableColumn<>("Likes");
                            likesColumn.setCellValueFactory(new PropertyValueFactory<>("likes"));

                            TableColumn<Reponse1, Integer> dislikesColumn = new TableColumn<>("Dislikes");
                            dislikesColumn.setCellValueFactory(new PropertyValueFactory<>("dislikes"));

                            TableColumn<Reponse1, Integer> questColumn = new TableColumn<>("Question ID");
                            questColumn.setCellValueFactory(new PropertyValueFactory<>("quest_id"));

                            // Add the columns to the table view
                            tableView.getColumns().addAll(idColumn, contenuColumn, dateColumn, likesColumn, dislikesColumn, questColumn);

                            // Get the list of responses and add them to the table view
                            List<Reponse1> responses = rc.display();
                            ObservableList<Reponse1> data = FXCollections.observableArrayList();
                            for (Reponse1 e : responses) {
                                if (e.getQuest_id() == questionId) {
                                    data.add(e);
                                }
                            }

                            tableView.setItems(data);
                            if (data.isEmpty()) {
                                tableView.setPlaceholder(new Label("pas de reponse pour ce question"));
                            }
                            // Add a delete button to the scene
                            Button deleteButton = new Button("Delete");
                            deleteButton.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 16px;");

                            deleteButton.setOnAction(e -> {
                                // Get the selected item
                                Reponse1 selectedResponse = tableView.getSelectionModel().getSelectedItem();
                                if (selectedResponse == null) {
                                    // Show an alert if nothing is selected
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("No Item Selected");
                                    alert.setHeaderText("Please select an item to delete.");
                                    alert.showAndWait();
                                } else {
                                    // Ask for confirmation before deleting the item
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirm Deletion");
                                    alert.setHeaderText("Are you sure you want to delete the selected item?");
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        // Remove the selected item from the list and the table view
                                        rc.deleteReponse(selectedResponse.getRep_contenu());
                                        responses.remove(selectedResponse);
                                        tableView.getItems().remove(selectedResponse);
                                    }
                                }
                            });

                            // Create a VBox to hold the table view and the delete button
                            VBox vbox = new VBox(10, tableView, deleteButton);
                            vbox.setAlignment(Pos.CENTER_RIGHT);
                            // Set the VBox as the content of the new window
                            Scene scene = new Scene(vbox);
                            stage.setScene(scene);

                            // Show the new window
                            stage.show();
                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        responseCol.setCellFactory(cellFactory);
        data_ques.getColumns().add(responseCol);

        data_ques.setItems(questions);
        Deletebtn.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        stat.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
    }

    @FXML
    private void Delete(ActionEvent event) {
        List<question> selectedQuestions = data_ques.getSelectionModel().getSelectedItems();
        if (selectedQuestions.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("erreur");
            alert.setHeaderText(null);
            alert.setContentText("aucun question selectionné.");
            alert.showAndWait();
        } else {
            Alert confirm = new Alert(AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation Dialog");
            confirm.setHeaderText(null);
            if (selectedQuestions.size() == 1) {
                confirm.setContentText("confirmation?");
            } else {
                confirm.setContentText("confirmation?");
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

    @FXML
    private void stat(ActionEvent event) {
        try {
            stat_bar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stat_bar() {
        int numQuestions = qc.display().size();
        int numResponses = rc.display().size();

        // Create a bar chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);

        // Set the data for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Questions", numQuestions));
        series.getData().add(new XYChart.Data<>("Responses", numResponses));
        chart.getData().add(series);

        // Create a new stage to display the chart
        Stage stage = new Stage();
        stage.setTitle("Question/Response Statistics");

        // Create a pane to hold the chart
        BorderPane pane = new BorderPane();
        pane.setCenter(chart);

        // Display the stage and wait for it to close
        Scene scene = new Scene(pane, 400, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void stat_pie() {
        // Get the number of questions and responses
        int numQuestions = qc.display().size();
        int numResponses = rc.display().size();

        // Create the data for the pie chart
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Questions (" + numQuestions + ")", numQuestions),
                        new PieChart.Data("Responses (" + numResponses + ")", numResponses));

        // Create the pie chart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Question-Response Ratio");

        // Display the numbers inside the pie chart
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
                    e -> {
                        // Get the percentage of the data
                        double percentage = (data.getPieValue() / (numQuestions + numResponses)) * 100.0;

                        // Create a label with the percentage
                        Tooltip tooltip = new Tooltip(String.format("%.2f%%", percentage));
                        Tooltip.install(data.getNode(), tooltip);
                    });
        }

        // Create the scene for the pie chart
        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().add(pieChart);

        // Create the new window for the pie chart
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Statistics");
        stage.show();
    }

       @FXML
    private void Dashboard(ActionEvent event) {
        // FXMain.setScene("Dashbord");
    }

    @FXML
    private void reclamation(ActionEvent event) {
         FXMain.setScene("Reponse");
    }

    @FXML
    private void forum(ActionEvent event) {
         FXMain.setScene("adminPOV");
    }

    @FXML
    private void Users(ActionEvent event) {
         FXMain.setScene("Usersmanagment");
    }

    @FXML
    private void RDV(ActionEvent event) {
     //    FXMain.setScene("RDV");
    }

    @FXML
    private void profile(MouseEvent event) {
         FXMain.setScene("ProfileAdmin");
    }
        @FXML
    private void Logout(ActionEvent event) {
        
         
        Stage stage;
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Déconnexion");
    alert.setHeaderText("Vous êtes sur le point de vous déconnecter");
    alert.setContentText("Voulez-vous vous déconnecter "+u.getEmail()+"?");
    ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
    alert.getButtonTypes().setAll(okButton, cancelButton);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == okButton) {
         FXMain.setScene("InterfaceLogin");
        
    }
        
    }

}
