/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import edu.MediHouse.entities.Fiche;
import edu.MediHouse.services.FicheCRUD;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.ServiceUser;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;


/**
 * FXML Controller class
 *
 * @author user
 */
public class FicheController implements Initializable {

    @FXML
    private ChoiceBox<Users> CBPatient;
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
    private TextField TfAge;
    private TextField TfBldType;
    @FXML
    private ChoiceBox<String> CBloodType;
    @FXML
    private Circle profilepicture;
    @FXML
    private Label username;
Users u =new Users();
     ServiceUser su=new ServiceUser();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CBloodType.getItems().addAll("A+", "A-", "B+", "B-", "O+", "O-");
        ComboxClient();
        ShowFiche();
        u=su.getUserByEmail(InterfaceLogineeController.iduserglobal);
        Image im = new Image(u.getProfilePicture());
        ImagePattern pattern = new ImagePattern(im);
        profilepicture.setFill(pattern);
        profilepicture.setStroke(Color.SEAGREEN);
        profilepicture.setEffect(new DropShadow(20, Color.BLACK));
        username.setText(u.getNom().toUpperCase()+" "+u.getPrenom().toUpperCase());
          profilepicture.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            profile(event);
                  }
    });
    }

    @FXML
    private void btnAdd(ActionEvent event) {
        if (CBPatient.getSelectionModel().isEmpty() || CBloodType.getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Tous les champs sont obligatoires");
            alert.showAndWait();
            return;
        }
        LocalDate l = CBPatient.getSelectionModel().getSelectedItem().getDatenes().toLocalDate();
        int a = 0;
        a = (int) ChronoUnit.YEARS.between(l, LocalDate.now());
        FicheCRUD rdv = new FicheCRUD();
        Fiche r = new Fiche(a, CBPatient.getSelectionModel().getSelectedItem(), CBloodType.getValue());
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
        LocalDate l = CBPatient.getSelectionModel().getSelectedItem().getDatenes().toLocalDate();
        int a = 0;
        a = (int) ChronoUnit.YEARS.between(l, LocalDate.now());
        Fiche r = new Fiche(rv.getId(), a, CBPatient.getSelectionModel().getSelectedItem(), CBloodType.getValue());

        FicheCRUD rdv = new FicheCRUD();
        rdv.modifierFiche(r);
        ShowFiche();

    }

    private void ComboxClient() {
        ServiceUser P = new ServiceUser();

        ObservableList<Users> list = FXCollections.observableArrayList(P.afficher());

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
        if (rv != null) {
            CBPatient.setValue(rv.getPatient());
           
        }

    }

   

    @FXML
    private void Fiches(ActionEvent event) {
        FXMain.setScene("Fiche");
    }

    @FXML
    private void RDVD(ActionEvent event) {
        FXMain.setScene("ListRDV_1");
    }

    @FXML
    private void logout(ActionEvent event) {
         
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

    

    @FXML
    private void profile(MouseEvent event) {
        FXMain.setScene("ProfileDoctor");
    }

}
