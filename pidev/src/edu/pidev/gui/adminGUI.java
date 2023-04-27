package edu.pidev.gui;

import edu.pidev.entities.question;
import edu.pidev.services.questionCRUD;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
public class adminGUI extends Application {
    private questionCRUD qCRUD;

    @Override
    public void start(Stage primaryStage) {
        qCRUD = new questionCRUD();

        primaryStage.setTitle("Forum Admin Panel");

        // Add logo
        Image logoImage = new Image("file:C:/Users/DELL/Downloads/Capture.PNG");
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitHeight(100);
        logoView.setFitWidth(250);

        // Add title text
        Text titleText = new Text("Manage Questions");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleText.setFill(Color.WHITE);

        // Add background color
        BorderPane mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: #2E4053;");
        // Add questions table
        TableView<question> table = new TableView<>();
        table.setEditable(true);

        TableColumn<question, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<question, String> contentCol = new TableColumn<>("Question");
        contentCol.setCellValueFactory(new PropertyValueFactory<>("ques_contenu"));

        TableColumn<question, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("ques_date_pub"));

        TableColumn<question, String> sa = new TableColumn<>("Categorie");
        sa.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        
        TableColumn<question, Integer> likesCol = new TableColumn<>("Likes");
        likesCol.setCellValueFactory(new PropertyValueFactory<>("likes"));

        TableColumn<question, Integer> dislikesCol = new TableColumn<>("Dislikes");
        dislikesCol.setCellValueFactory(new PropertyValueFactory<>("dislikes"));

        table.getColumns().addAll(idCol, contentCol, dateCol, sa ,likesCol, dislikesCol);

        // Populate questions table
        ObservableList<question> questions = FXCollections.observableArrayList(qCRUD.display());
        table.setItems(questions);
  // Add delete button
        Button deleteBtn = new Button("Delete Selected");
        deleteBtn.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 16px;");
        deleteBtn.setOnAction(e -> {
            List<question> selectedQuestions = table.getSelectionModel().getSelectedItems();
            selectedQuestions.forEach(q -> {
                qCRUD.deletequestion2(String.valueOf(q.getId()));
                questions.remove(q);
            });
        });

        // Add refresh button
        Button refreshBtn = new Button("Refresh List");
        refreshBtn.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 16px;");
        refreshBtn.setOnAction(e -> {
            questions.clear();
            questions.addAll(qCRUD.display());
        });

   // Create BarChart
CategoryAxis xAxis = new CategoryAxis();
xAxis.setLabel("Number of Rows");

NumberAxis yAxis = new NumberAxis();
yAxis.setLabel("Count");
yAxis.setTickUnit(1); // Set tick unit to 1

BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
chart.setTitle("stats");

// Add data
XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
dataSeries.setName("Number of questions");
dataSeries.getData().add(new XYChart.Data<>("", questions.size()));

chart.getData().add(dataSeries);
chart.setPrefSize(100, 300); 

   // Add buttons to HBox
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(refreshBtn, deleteBtn);
        // Add components to main pane
        HBox logoBox = new HBox(logoView);
        logoBox.setPadding(new Insets(10, 10, 10, 10));
        mainPane.setTop(new VBox(logoBox, titleText));
        mainPane.setCenter(table);
        mainPane.setBottom(buttonBox);
        mainPane.setRight(chart);
        mainPane.setPadding(new Insets(20));

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
