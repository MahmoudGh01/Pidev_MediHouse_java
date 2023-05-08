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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import edu.MediHouse.entities.RendezVous;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.RendezVousCRUD;
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
public class ListRDVController_1 implements Initializable {

    RendezVousCRUD R = new RendezVousCRUD();
    //My variables:
    RendezVous r = R.getLastRendezVousTime();

    Boolean isIt = false;
    Timer timer = new Timer();
    TimerTask task;
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
    private TableColumn<RendezVous, Integer> Fiche_Col;
    @FXML
    private TableColumn<RendezVous, Void> Action_Col;
    @FXML
    private Label timeLeft =new Label();
    @FXML
    private Button Chart;
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
        // System.out.println(R.getRendezVous(16));
        Countdown();
        ShowRendezVous();
        //Stage stage = (Stage) timeLeft.getScene().getWindow();
//        stage.setOnCloseRequest(e -> {
//            timer.cancel();
//            task.cancel();
//        });

    }

    private void ShowRendezVous() {
        RendezVousCRUD rdv = new RendezVousCRUD();

        ObservableList<RendezVous> list = FXCollections.observableArrayList(rdv.listerRendezVous());
        Id_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        Fiche_Col.setCellValueFactory(new PropertyValueFactory<>("fiche"));

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
//Date_Col.setCellValueFactory(new PropertyValueFactory<>("date"));
        Local_Col.setCellValueFactory(new PropertyValueFactory<>("Local"));
        Callback<TableColumn<RendezVous, Void>, TableCell<RendezVous, Void>> cellFactory = (final TableColumn<RendezVous, Void> param) -> {
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
                                Logger.getLogger(ListRDVController_1.class.getName()).log(Level.SEVERE, null, ex);
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
        };

        Action_Col.setCellFactory(cellFactory);

        //System.out.print("test");
        Tbv.setItems(list);

    }

    @FXML
    private void tbC(MouseEvent event) {

    }

    private void btnsearch(ActionEvent event) {
        FXMain.setScene("Search_doctor");
    }

    private void btnCalendar(ActionEvent event) {
        FXMain.setScene("Fiche");
    }

// ...
    private void Countdown() {
        // new timer

        task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime targetDateTime = LocalDateTime.of(r.getDate_RDV().toLocalDate(), r.getTime().toLocalTime());

                    Duration remainingDuration;
                    if (now.isBefore(targetDateTime)) {
                        remainingDuration = Duration.between(now, targetDateTime);
                    } else {
                        remainingDuration = Duration.between(targetDateTime, now);
                    }

                    long days = remainingDuration.toDays();
                    long hours = remainingDuration.toHours() % 24;
                    long minutes = remainingDuration.toMinutes() % 60;
                    long seconds = remainingDuration.getSeconds() % 60;

                    String timeLeftString = String.format("   %02d %02d:%02d:%02d", days, hours, minutes, seconds);

                    timeLeft.setText(timeLeftString);
                });

            }
        };

        timer.scheduleAtFixedRate(task, 1000, 1000); // schedule the task to run every second
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel(); // stop the timer
        }
    }

    @FXML
    private void Chart(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Chart.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Search_doctorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
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
     @FXML
    private void profile(MouseEvent event) {
        FXMain.setScene("ProfileDoctor");
    }
    


}
