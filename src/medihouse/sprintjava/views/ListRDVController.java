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
import java.sql.Date;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import medihouse.sprintjava.entities.RendezVous;
import medihouse.sprintjava.services.RendezVousCRUD;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ListRDVController implements Initializable {

    RendezVousCRUD R = new RendezVousCRUD();

    @FXML
    private Circle ProfilePic;
    @FXML
    private Label UserName;
    @FXML
    private Button btnsearch;
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
    private Button btnCalendar;
    @FXML
    private TableColumn<RendezVous, Integer> Fiche_Col;
    @FXML
    private TableColumn<RendezVous, Void> Action_Col;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ShowRendezVous();
    }

    private void ShowRendezVous() {
        RendezVousCRUD rdv = new RendezVousCRUD();

        ObservableList<RendezVous> list = FXCollections.observableArrayList(rdv.listerRendezVous());
        Id_Col.setCellValueFactory(new PropertyValueFactory<RendezVous, Integer>("id"));
        Fiche_Col.setCellValueFactory(new PropertyValueFactory<RendezVous, Integer>("fiche"));
       

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
        Callback<TableColumn<RendezVous, Void>, TableCell<RendezVous, Void>> cellFactory = new Callback<TableColumn<RendezVous, Void>, TableCell<RendezVous, Void>>() {
            @Override
            public TableCell<RendezVous, Void> call(final TableColumn<RendezVous, Void> param) {
                final TableCell<RendezVous, Void> cell = new TableCell<RendezVous, Void>() {
//                  
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {

                            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                            FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                            deleteIcon.setStyle(
                                    " -fx-cursor: hand ;"
                                    + "-glyph-size:28px;"
                                    + "-fx-fill:#ff1744;"
                            );
                            editIcon.setStyle(
                                    " -fx-cursor: hand ;"
                                    + "-glyph-size:28px;"
                                    + "-fx-fill:#00E676;"
                            );

                            editIcon.setOnMouseClicked((MouseEvent event) -> {

                                RendezVous rdv = Tbv.getSelectionModel().getSelectedItem();

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("Pick_date.fxml"));
                                try {
                                    loader.load();
                                } catch (IOException ex) {
                                    Logger.getLogger(ListRDVController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                Pick_dateController addStudentController = loader.getController();
                                addStudentController.setUpdate(true);
                                addStudentController.setTextField(rdv.getDocteur().getId(), rdv.getId(), rdv.getDocteur().getNom());
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();

                                ShowRendezVous();

                            });

                            deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                                RendezVous rdv = Tbv.getSelectionModel().getSelectedItem();
                                R.deleteRendezVous(rdv);
                                ShowRendezVous();

                            });

                            HBox managebtn = new HBox(editIcon, deleteIcon);
                            managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
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

    }

    @FXML
    private void btnsearch(ActionEvent event) {
        NewFXMain.setScene("Search_doctor");
    }

    @FXML
    private void btnCalendar(ActionEvent event) {
        NewFXMain.setScene("Fiche");
    }
}
