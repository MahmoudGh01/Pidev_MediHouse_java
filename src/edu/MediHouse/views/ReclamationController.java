/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import static edu.MediHouse.entities.BadWords.checkWords;
import edu.MediHouse.entities.Reclamation;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.ReclamationCRUD;
import edu.MediHouse.services.ServiceUser;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
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
public class ReclamationController implements Initializable {

    Notifications no;
    private Circle ProfilePic;
    private Label UserName;
    @FXML
    private TableView<Reclamation> Tbv;
    @FXML
    private TableColumn<Reclamation, Integer> Id_Col;
    @FXML
    private TableColumn<Reclamation, String> Email_Col;
    @FXML
    private TableColumn<Reclamation, String> Sujet_Col;
    @FXML
    private TableColumn<Reclamation, String> Desc_Col;
    @FXML
    private TableColumn<Reclamation, Date> Date_Col;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField TfEmail;
    @FXML
    private ChoiceBox<String> CBSujet;
    @FXML
    private TextArea TaDesc;
 Users u =new Users();
     ServiceUser su=new ServiceUser();
    @FXML
    private Circle ProfilePic1;
    @FXML
    private Label UserName1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CBSujet.getItems().addAll("Patient", "Doctor", "Autres");
        ShowReclamation();
         // TODO
         u=su.getUserByEmail(InterfaceLogineeController.iduserglobal);
        Image im = new Image(u.getProfilePicture());
        ImagePattern pattern = new ImagePattern(im);
        ProfilePic1.setFill(pattern);
        ProfilePic1.setStroke(Color.SEAGREEN);
        ProfilePic1.setEffect(new DropShadow(20, Color.BLACK));
        UserName1.setText(u.getNom().toUpperCase()+" "+u.getPrenom().toUpperCase());
        ProfilePic1.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Profile1(event);
        }
    });
    }


    @FXML
    private void tbC(MouseEvent event) {
        Reclamation rv = Tbv.getSelectionModel().getSelectedItem();
        //CBDocteur.getSelectionModel().select(rv.getDocteur());

        CBSujet.setValue(rv.getSujet());
        TfEmail.setText(rv.getEmail());
        TaDesc.setText(rv.getDescription());
    }

    @FXML
    private void btnAdd(ActionEvent event) {
        int nb = 0;
        if (CBSujet.getSelectionModel().isEmpty() || TfEmail.getText().isEmpty() || TaDesc.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Tous les champs sont obligatoires");
            alert.showAndWait();
            return;
        }
        if (!TfEmail.getText().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Email invalide");
            alert.setContentText("Veuillez saisir un email valide");
            alert.showAndWait();
            return;
        }
        if (checkWords(TaDesc.getText()).equals("false")) {
            nb = 0;
            ReclamationCRUD rdv = new ReclamationCRUD();
            Reclamation r = new Reclamation(TfEmail.getText(), CBSujet.getValue(), TaDesc.getText(), Date.valueOf(LocalDate.now()));
            rdv.ajouterReclamation(r);
            ShowReclamation();
            no = Notifications.create()
                    .title("Reclamation Ajoutée")
                    .text("Reclamation envoyée ")
                    .graphic(null)
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(6));
            no.showInformation();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Worning !! ");
            alert.setContentText("vous ne pouvez pas ajouter une reclamation avec ces mots ! ");
            alert.show();
            nb = nb + 1;
            if (nb >= 3) {
                alert.setTitle("Worning !! ");
                alert.setContentText("vous avez passer le nombre max de tentative");
            }
        }

    }

    @FXML
    private void btnDelete(ActionEvent event) {
        Reclamation rv = Tbv.getSelectionModel().getSelectedItem();
        if (Tbv.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune Reclamation sélectionné");
            alert.showAndWait();
            return;
        }

        ReclamationCRUD rdv = new ReclamationCRUD();
        rdv.deleteReclamation(rv);
        ShowReclamation();
        no = Notifications.create()
                .title("Reclamation Supprimer")
                .text("Reclamation Supprimer ")
                .graphic(null)
                .position(Pos.TOP_CENTER)
                .hideAfter(Duration.seconds(6));
        no.showInformation();

    }

    @FXML
    private void btnUpdate(ActionEvent event) {
        Reclamation rv = Tbv.getSelectionModel().getSelectedItem();
        if (Tbv.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune Reclamation sélectionné");
            alert.showAndWait();
            return;
        }
        if (checkWords(TaDesc.getText()).equals("false")) {
            Reclamation r = new Reclamation(rv.getId(), TfEmail.getText(), CBSujet.getValue(), TaDesc.getText(), Date.valueOf(LocalDate.now()));

            ReclamationCRUD rdv = new ReclamationCRUD();
            rdv.modifierReclamation(r);
            ShowReclamation();
            no = Notifications.create()
                    .title("Reclamation modifée")
                    .text("Reclamation modifée ")
                    .graphic(null)
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(6));
            no.showInformation();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Worning !! ");
            alert.setContentText("vous ne pouvez pas ajouter une reclamation avec ces mots ! ");
            alert.show();

        }

    }

    private void ShowReclamation() {
        ReclamationCRUD rdv = new ReclamationCRUD();

        ObservableList<Reclamation> list = FXCollections.observableArrayList(rdv.listerReclamation());
        Id_Col.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("id"));
        //Patient_Col.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("Name"));

        Email_Col.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("Email"));
        Sujet_Col.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("Sujet"));
        Desc_Col.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("Description"));
        Date_Col.setCellValueFactory(new PropertyValueFactory<>("date"));
        Date_Col.setCellFactory(column -> {
            return new TableCell<Reclamation, Date>() {
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
        //System.out.print("test");
        Tbv.setItems(list);

    }

   @FXML
   private void RechMed(ActionEvent event) {
        FXMain.setScene("Search_doctor");
    }

    @FXML
    private void parapharmacie(ActionEvent event) {
        FXMain.setScene("ProduitsUser");
    }
    
    @FXML
    private void forum(ActionEvent event) {
        FXMain.setScene("pi");
    }

    @FXML
    private void reclamation(ActionEvent event) {
       FXMain.setScene("Reclamation");
    }

    @FXML
    private void RDVP(ActionEvent event) {
       FXMain.setScene("ListRDV");
    }
   @FXML
    private void Profile1(MouseEvent event) {
        FXMain.setScene("ProfilePatient");
    }

    @FXML
    private void Logout(ActionEvent event) {
            Stage stage;
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
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
