package edu.pidev.gui;

import edu.pidev.entities.question;
import edu.pidev.services.questionCRUD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController {

    @FXML
    private TableView<question> questionTable;
    @FXML
    private TableColumn<question, Integer> idColumn;
    @FXML
    private TableColumn<question, String> contentColumn;
    @FXML
    private TableColumn<question, String> dateColumn;
    @FXML
    private TableColumn<question, String> categoryColumn;
    @FXML
    private TableColumn<question, Integer> likesColumn;
    @FXML
    private TableColumn<question, Integer> dislikesColumn;

    private questionCRUD qc = new questionCRUD();

    public void initialize() {
        // Initialize the table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("ques_contenu"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("ques_date_pub"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        likesColumn.setCellValueFactory(new PropertyValueFactory<>("likes"));
        dislikesColumn.setCellValueFactory(new PropertyValueFactory<>("dislikes"));

        // Populate the table with data
        ObservableList<question> questionList = FXCollections.observableArrayList(qc.display());
        questionTable.setItems(questionList);
    }
}
