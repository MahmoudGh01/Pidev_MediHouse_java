/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import static edu.MediHouse.entities.BadWords.checkWords;
import edu.MediHouse.entities.Reclamation;
import edu.MediHouse.entities.Reponse;
import edu.MediHouse.services.ReclamationCRUD;
import edu.MediHouse.services.ReponseCRUD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ReponseController implements Initializable {

    Notifications no;
    @FXML
    private Circle ProfilePic;
    @FXML
    private Label UserName;
    @FXML
    private Button btnRDV;
    @FXML
    private Button Reclamation_btn;
    @FXML
    private Button btnsearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<Reponse> Tbv;
    @FXML
    private TableColumn<Reponse, Integer> Id_Col;
    @FXML
    private TableColumn<Reponse, String> Email_Col;
    @FXML
    private TableColumn<Reponse, String> Sujet_Col;
    @FXML
    private TextArea TaReponse;
    @FXML
    private ChoiceBox<Reclamation> CBReclamation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComboxClient();
        ShowReponse();
    }

    @FXML
    private void btnRDV(ActionEvent event) {
    }

    @FXML
    private void Reclamation_btn(ActionEvent event) {
        FXMain.setScene("Reclamation");
    }

    @FXML
    private void btnsearch(ActionEvent event) {
    }

    @FXML
    private void tbC(MouseEvent event) {
        Reponse rv = Tbv.getSelectionModel().getSelectedItem();
        //CBDocteur.getSelectionModel().select(rv.getDocteur());

        CBReclamation.setValue(rv.getReclamation());

        TaReponse.setText(rv.getReponse());
    }

    @FXML
    private void btnAdd(ActionEvent event) {

        if (CBReclamation.getSelectionModel().isEmpty() || TaReponse.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Tous les champs sont obligatoires");
            alert.showAndWait();
            return;
        }
        if (checkWords(TaReponse.getText()).equals("false")) {
            ReponseCRUD rdv = new ReponseCRUD();
            Reponse r = new Reponse(TaReponse.getText(), CBReclamation.getValue());
            rdv.ajouterReponse(r);
            ShowReponse();

            no = Notifications.create()
                    .title("Reponse Ajoutée")
                    .text("Reponse envoyée ")
                    .graphic(null)
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(6));
            no.showInformation();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Worning !! ");
            alert.setContentText("vous ne pouvez pas ajouter une reponse avec ces mots ! ");
            alert.show();

        }
    }

    @FXML
    private void btnDelete(ActionEvent event) {

        Reponse rv = Tbv.getSelectionModel().getSelectedItem();
        if (Tbv.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune Reponse sélectionné");
            alert.showAndWait();
            return;
        }

        ReponseCRUD rdv = new ReponseCRUD();
        rdv.deleteReponse(rv);
        ShowReponse();
        no = Notifications.create()
                .title("Reponse supprimer")
                .text("Reponse supprimer ")
                .graphic(null)
                .position(Pos.TOP_CENTER)
                .hideAfter(Duration.seconds(6));
        no.showInformation();

    }

    @FXML
    private void btnUpdate(ActionEvent event) {
        Reponse rv = Tbv.getSelectionModel().getSelectedItem();
        if (Tbv.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune Reponse sélectionné");
            alert.showAndWait();
            return;
        }

        Reponse r = new Reponse(rv.getId(), TaReponse.getText(), CBReclamation.getValue());
        if (checkWords(TaReponse.getText()).equals("false")) {
            ReponseCRUD rdv = new ReponseCRUD();
            rdv.modifierReponse(r);
            ShowReponse();
            no = Notifications.create()
                    .title("Reponse modifiée")
                    .text("Reponse modifiée ")
                    .graphic(null)
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(6));
            no.showInformation();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Worning !! ");
            alert.setContentText("vous ne pouvez pas ajouter une reponse avec ces mots ! ");
            alert.show();

        }

    }

    private void ShowReponse() {
        ReponseCRUD rdv = new ReponseCRUD();

        ObservableList<Reponse> list = FXCollections.observableArrayList(rdv.listerReponse());
        Id_Col.setCellValueFactory(new PropertyValueFactory<Reponse, Integer>("id"));
        //Patient_Col.setCellValueFactory(new PropertyValueFactory<Reponse, String>("Name"));

        //Email_Col.setCellValueFactory(new PropertyValueFactory<Reponse, Reclamation>("Email"));
        Email_Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReclamation().getEmail()));
        Email_Col.setCellFactory(column -> new TableCell<Reponse, String>() {
            @Override
            protected void updateItem(String docteur, boolean empty) {
                super.updateItem(docteur, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(docteur);
                }
            }
        });

        Sujet_Col.setCellValueFactory(new PropertyValueFactory<Reponse, String>("Reponse"));

        //System.out.print("test");
        Tbv.setItems(list);

    }

    private void ComboxClient() {
        ReclamationCRUD P = new ReclamationCRUD();

        ObservableList<Reclamation> list = FXCollections.observableArrayList(P.listerReclamation());

        CBReclamation.setItems(list);

    }

}
