/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse.sprintjava.views;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import medihouse.sprintjava.entities.RendezVous;
import medihouse.sprintjava.entities.User;
import medihouse.sprintjava.services.RendezVousCRUD;
import medihouse.sprintjava.services.UserCRUD;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ReserverController implements Initializable {

    UserCRUD uc = new UserCRUD();
    @FXML
    private ChoiceBox<User> CBDocteur;
    @FXML
    private ChoiceBox<User> CBPatient;
    @FXML
    private DatePicker picker;
    @FXML
    private TextField TfLocal;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<RendezVous> Tbv;
    @FXML
    private TableColumn<RendezVous, Integer> Id_Col;
    @FXML
    private TableColumn<RendezVous, String> Patient_Col;
    @FXML
    private TableColumn<RendezVous, String> Docteur_Col;
    @FXML
    private TableColumn<RendezVous, Date> Date_Col;
    @FXML
    private TableColumn<RendezVous, String> Local_Col;
    @FXML
    private Circle ProfilePic;
    @FXML
    private Label UserName;
    @FXML
    private Button btnsearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComboxClient();
        ShowRendezVous();
    }

    @FXML
    private void btnAdd(ActionEvent event) {
        if (CBDocteur.getSelectionModel().isEmpty() || CBPatient.getSelectionModel().isEmpty() || picker.getValue() == null || TfLocal.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Tous les champs sont obligatoires");
            alert.showAndWait();
            return;
        }

        if (picker.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("La Date doit etre valide");
            alert.showAndWait();
            return;
        }

        RendezVousCRUD rdv = new RendezVousCRUD();
        RendezVous r = new RendezVous(CBDocteur.getSelectionModel().getSelectedItem(), CBPatient.getSelectionModel().getSelectedItem(), Date.valueOf(picker.getValue()), TfLocal.getText());
        rdv.ajouterRendezVous(r);
        ShowRendezVous();
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        RendezVous rv = Tbv.getSelectionModel().getSelectedItem();
         if (Tbv.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun RendezVous sélectionné");
            alert.showAndWait();
            return;
        }
        RendezVousCRUD rdv = new RendezVousCRUD();
        rdv.deleteRendezVous(rv);
        ShowRendezVous();
    }

    @FXML
    private void btnUpdate(ActionEvent event) {
        RendezVous rv = Tbv.getSelectionModel().getSelectedItem();
        if (Tbv.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun RendezVous sélectionné");
            alert.showAndWait();
            return;
        }

        RendezVous r = new RendezVous(rv.getId(), CBDocteur.getSelectionModel().getSelectedItem(), CBPatient.getSelectionModel().getSelectedItem(), Date.valueOf(picker.getValue()), TfLocal.getText());

        RendezVousCRUD rdv = new RendezVousCRUD();
        rdv.modifierRendezVous(r);
        ShowRendezVous();

    }

    private void ComboxClient() {
        UserCRUD P = new UserCRUD();

        ObservableList<User> list = FXCollections.observableArrayList(P.listerUser());

        CBDocteur.setItems(list);
        CBPatient.setItems(list);

    }

    private void ShowRendezVous() {
        RendezVousCRUD rdv = new RendezVousCRUD();

        ObservableList<RendezVous> list = FXCollections.observableArrayList(rdv.listerRendezVous());
        Id_Col.setCellValueFactory(new PropertyValueFactory<RendezVous, Integer>("id"));
        //Patient_Col.setCellValueFactory(new PropertyValueFactory<RendezVous, String>("Name"));

        Patient_Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getNom()));
        Patient_Col.setCellFactory(column -> new TableCell<RendezVous, String>() {
            @Override
            protected void updateItem(String patient, boolean empty) {
                super.updateItem(patient, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(patient);
                }
            }
        });

        //Docteur_Col.setCellValueFactory(new PropertyValueFactory<RendezVous, String>("prix"));
        Docteur_Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocteur().getNom()));
        Docteur_Col.setCellFactory(column -> new TableCell<RendezVous, String>() {
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
        Date_Col.setCellValueFactory(new PropertyValueFactory<>("Date_RDV"));
        Date_Col.setCellFactory(column -> {
            return new TableCell<RendezVous, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                    }
                }
            };
        });

        Local_Col.setCellValueFactory(new PropertyValueFactory<RendezVous, String>("Local"));
        
        //System.out.print("test");
        Tbv.setItems(list);

    }

    @FXML
    private void tbC(MouseEvent event) {
        RendezVous rv = Tbv.getSelectionModel().getSelectedItem();
        //CBDocteur.getSelectionModel().select(rv.getDocteur());
        CBDocteur.setValue(rv.getDocteur());
        CBPatient.setValue(rv.getPatient());
        picker.setValue(rv.getDate_RDV().toLocalDate());
        TfLocal.setText(rv.getLocal());

    }

    @FXML
    private void btnsearch(ActionEvent event) {
        NewFXMain.setScene("Search_doctor");
    }
}
