/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import static edu.MediHouse.entities.BadWords.checkWords;
import edu.MediHouse.entities.Reclamation;
import edu.MediHouse.entities.Dhia;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.ReclamationCRUD;
import edu.MediHouse.services.ReponseCRUD;
import edu.MediHouse.services.ServiceUser;
import static edu.MediHouse.views.InterfaceAdminController.generatePseudo;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
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
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<Dhia> Tbv;
    @FXML
    private TableColumn<Dhia, Integer> Id_Col;
    @FXML
    private TableColumn<Dhia, String> Email_Col;
    @FXML
    private TableColumn<Dhia, String> Sujet_Col;
    @FXML
    private TextArea TaReponse;
    @FXML
    private ChoiceBox<Reclamation> CBReclamation;
    @FXML
    private Circle ProfilePic12;
    @FXML
    private Label UserName12;
Users u =new Users();
     ServiceUser su=new ServiceUser();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComboxClient();
        ShowReponse();
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


    private void Reclamation_btn(ActionEvent event) {
        FXMain.setScene("Reclamation");
    }


    @FXML
    private void tbC(MouseEvent event) {
        Dhia rv = Tbv.getSelectionModel().getSelectedItem();
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
            Dhia r = new Dhia(TaReponse.getText(), CBReclamation.getValue());
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

        Dhia rv = Tbv.getSelectionModel().getSelectedItem();
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
        Dhia rv = Tbv.getSelectionModel().getSelectedItem();
        if (Tbv.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune Reponse sélectionné");
            alert.showAndWait();
            return;
        }

        Dhia r = new Dhia(rv.getId(), TaReponse.getText(), CBReclamation.getValue());
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

        ObservableList<Dhia> list = FXCollections.observableArrayList(rdv.listerReponse());
        Id_Col.setCellValueFactory(new PropertyValueFactory<Dhia, Integer>("id"));
        //Patient_Col.setCellValueFactory(new PropertyValueFactory<Reponse, String>("Name"));

        //Email_Col.setCellValueFactory(new PropertyValueFactory<Reponse, Reclamation>("Email"));
        Email_Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReclamation().getEmail()));
        Email_Col.setCellFactory(column -> new TableCell<Dhia, String>() {
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

        Sujet_Col.setCellValueFactory(new PropertyValueFactory<Dhia, String>("Reponse"));

        //System.out.print("test");
        Tbv.setItems(list);

    }

    private void ComboxClient() {
        ReclamationCRUD P = new ReclamationCRUD();

        ObservableList<Reclamation> list = FXCollections.observableArrayList(P.listerReclamation());

        CBReclamation.setItems(list);

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
