/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse.sprintjava.views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import medihouse.sprintjava.entities.Fiche;
import medihouse.sprintjava.entities.User;
import medihouse.sprintjava.services.FicheCRUD;
import medihouse.sprintjava.services.UserCRUD;

/**
 * FXML Controller class
 *
 * @author user
 */
public class FicheController implements Initializable {

    @FXML
    private Circle ProfilePic;
    @FXML
    private Label UserName;
    @FXML
    private Button btnsearch;
    @FXML
    private ChoiceBox<User> CBPatient;
    @FXML
    private TableView<Fiche> Tbv;
    @FXML
    private TableColumn<Fiche, Integer> Id_Col;
    @FXML
    private TableColumn<Fiche, String> Patient_Col;
    @FXML
    private TableColumn<Fiche, Integer> Age_Col;
    @FXML
    private TableColumn<Fiche, String> BldType_Col;
    @FXML
    private TableColumn<Fiche, Void> Action_Col;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField TfAge;
    @FXML
    private TextField TfBldType;
    @FXML
    private Button btnRDV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComboxClient();
        ShowFiche();
    }

    @FXML
    private void btnAdd(ActionEvent event) {
        if (CBPatient.getSelectionModel().isEmpty() || TfAge.getText().isEmpty() || TfBldType.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Tous les champs sont obligatoires");
            alert.showAndWait();
            return;
        }

        FicheCRUD rdv = new FicheCRUD();
        Fiche r = new Fiche(Integer.parseInt(TfAge.getText()), CBPatient.getSelectionModel().getSelectedItem(), TfBldType.getText());
        rdv.ajouterFiche(r);
        ShowFiche();
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        Fiche rv = Tbv.getSelectionModel().getSelectedItem();
        if (Tbv.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune Fiche sélectionné");
            alert.showAndWait();
            return;
        }
        FicheCRUD rdv = new FicheCRUD();
        rdv.deleteFiche(rv);
        ShowFiche();
    }

    @FXML
    private void btnUpdate(ActionEvent event) {
        Fiche rv = Tbv.getSelectionModel().getSelectedItem();
        if (Tbv.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune Fiche sélectionné");
            alert.showAndWait();
            return;
        }

        Fiche r = new Fiche(rv.getId(), Integer.parseInt(TfAge.getText()), CBPatient.getSelectionModel().getSelectedItem(), TfBldType.getText());

        FicheCRUD rdv = new FicheCRUD();
        rdv.modifierFiche(r);
        ShowFiche();

    }

    private void ComboxClient() {
        UserCRUD P = new UserCRUD();

        ObservableList<User> list = FXCollections.observableArrayList(P.getUserByRole("Patient"));

        CBPatient.setItems(list);

    }

    private void ShowFiche() {
        FicheCRUD rdv = new FicheCRUD();

        ObservableList<Fiche> list = FXCollections.observableArrayList(rdv.listerFiche());
        Id_Col.setCellValueFactory(new PropertyValueFactory<Fiche, Integer>("id"));
        //Patient_Col.setCellValueFactory(new PropertyValueFactory<Fiche, String>("Name"));

        Patient_Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getNom()));
        Patient_Col.setCellFactory(column -> new TableCell<Fiche, String>() {
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

        Age_Col.setCellValueFactory(new PropertyValueFactory<Fiche, Integer>("Age"));
        BldType_Col.setCellValueFactory(new PropertyValueFactory<Fiche, String>("BloodType"));
        Callback<TableColumn<Fiche, Void>, TableCell<Fiche, Void>> cellFactory = new Callback<TableColumn<Fiche, Void>, TableCell<Fiche, Void>>() {
            @Override
            public TableCell<Fiche, Void> call(final TableColumn<Fiche, Void> param) {
                final TableCell<Fiche, Void> cell = new TableCell<Fiche, Void>() {
//                  
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {

                            FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EYE);

                            editIcon.setStyle(
                                    " -fx-cursor: hand ;"
                                    + "-glyph-size:28px;"
                                    + "-fx-fill:#00E676;"
                            );

                            editIcon.setOnMouseClicked((MouseEvent event) -> {

                                Fiche Docteur = Tbv.getSelectionModel().getSelectedItem();
                                //System.out.println(Docteur.getId());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowFiche.fxml"));
                                ShowFicheController addController = new ShowFicheController(Docteur.getId());
                                loader.setController(addController);
                                try {
                                    loader.load();
                                } catch (IOException ex) {
                                    Logger.getLogger(FicheController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();

                            });

                            HBox managebtn = new HBox(editIcon);
                            managebtn.setStyle("-fx-alignment:center");
                            // HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                            HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                            setGraphic(managebtn);

                            setText(null);

                        }
                    }
                };
                return cell;
            }
        };

        Action_Col.setCellFactory(cellFactory);
        //System.out.print("test");
        Tbv.setItems(list);

    }

    @FXML
    private void tbC(MouseEvent event) {
        Fiche rv = Tbv.getSelectionModel().getSelectedItem();
        //CBDocteur.getSelectionModel().select(rv.getDocteur());

        CBPatient.setValue(rv.getPatient());
        TfAge.setText(String.valueOf(rv.getAge()));
        TfBldType.setText(rv.getBloodType());

    }

    @FXML
    private void btnsearch(ActionEvent event) {
        NewFXMain.setScene("Search_doctor");
    }

    @FXML
    private void btnRDV(ActionEvent event) {
        NewFXMain.setScene("ListRDV");
    }

}
