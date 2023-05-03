/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;


/**
 * FXML Controller class
 *
 * @author user
 */
public class Search_doctorController implements Initializable {

    ServiceUser UC = new ServiceUser();
    @FXML
    private Circle ProfilePic;

    @FXML
    private TableView<Users> Tbv;
    @FXML
    private TableColumn<Users, Integer> Id_Col;
    @FXML
    private TableColumn<Users, String> Name_Col;

    private GridPane grdPn;
    @FXML
    private TableColumn<Users, ImageView> Img_Col;
    @FXML
    private TableColumn<Users, String> Email_Col;
    @FXML
    private TableColumn<Users, Void> Action_Col;
    @FXML
    private Label UserName;
    @FXML
    private Button btnsearch;
    @FXML
    private Button RDVBtn;
    @FXML
    private TextField TfSearch;
    @FXML
    private Button btnFiche;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ShowUsers();
    }

    @FXML
    private void btnsearch(ActionEvent event) {

    }

    private void ShowUsers() {
        ServiceUser rdv = new ServiceUser();

        ObservableList<Users> list = FXCollections.observableArrayList(rdv.afficher());
        Id_Col.setCellValueFactory(new PropertyValueFactory<Users, Integer>("id"));
        Name_Col.setCellValueFactory(new PropertyValueFactory<Users, String>("Nom"));
        Email_Col.setCellValueFactory(new PropertyValueFactory<Users, String>("email"));
        Img_Col.setCellValueFactory(new PropertyValueFactory<Users, ImageView>("Image"));

        Callback<TableColumn<Users, Void>, TableCell<Users, Void>> cellFactory = new Callback<TableColumn<Users, Void>, TableCell<Users, Void>>() {
            @Override
            public TableCell<Users, Void> call(final TableColumn<Users, Void> param) {
                final TableCell<Users, Void> cell = new TableCell<Users, Void>() {
//                  
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {

                            FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.CALENDAR);

                            editIcon.setStyle(
                                    " -fx-cursor: hand ;"
                                    + "-glyph-size:28px;"
                                    + "-fx-fill:#00E676;"
                            );

                            editIcon.setOnMouseClicked((MouseEvent event) -> {

                                Users Docteur = Tbv.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("Pick_date.fxml"));
                                try {
                                    loader.load();
                                } catch (IOException ex) {
                                    Logger.getLogger(Search_doctorController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                Pick_dateController addStudentController = loader.getController();
                                addStudentController.setUpdate(false);
                                addStudentController.setTextField(Docteur.getId(), 0, Docteur.getNom());
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
        TableColumn<Users, String> Img_Col = new TableColumn<>("Image");
        Img_Col.setPrefWidth(100);
        Img_Col.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));

        Img_Col.setCellFactory(column -> {
            return new TableCell<Users, String>() {
                private final ImageView imageView = new ImageView();

                {
                    setGraphic(imageView);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }

                @Override
                protected void updateItem(String imageUrl, boolean empty) {
                    super.updateItem(imageUrl, empty);
                    if (imageUrl == null || empty) {
                        imageView.setImage(null);
                    } else {
                        Image image = new Image(imageUrl);
                        imageView.setImage(image);
                    }
                }
            };
        });

//        Img_Col.setCellFactory(column -> {
//            return new TableCell<Users, ImageView>() {
//                @Override
//                protected void updateItem(ImageView item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (item == null || empty) {
//                        setGraphic(null);
//                    } else {
//                        Users user = getTableView().getItems().get(getIndex());
//                        if (user != null && user.getProfilePicture() != null) {
//                            try {
//
//                                ImageView imageView = user.getProfilePicture();
//                                imageView.setFitHeight(50);
//                                imageView.setFitWidth(50);
//                                setGraphic(imageView);
//                            } catch (Exception e) {
//                                // Handle the exception if the image cannot be loaded
//                                e.printStackTrace();
//                            }
//                        } else {
//                            setGraphic(null);
//                        }
//                    }
//                }
//            };
//        });
        Tbv.setItems(list);
    }

    @FXML
    private void RDVBtn(ActionEvent event) {
        FXMain.setScene("ListRDV");
    }

    @FXML
    private void TfSearch(ActionEvent event) {

//        ObservableList<User> list = FXCollections.observableArrayList(UC.getUsersByName(TfSearch.getText()));
//        Id_Col.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
//        Name_Col.setCellValueFactory(new PropertyValueFactory<User, String>("Nom"));
//        Email_Col.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
//        Img_Col.setCellValueFactory(new PropertyValueFactory<User, ImageView>("Image"));
//
//        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
//            @Override
//            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
//                final TableCell<User, Void> cell = new TableCell<User, Void>() {
////                  
//                    @Override
//                    public void updateItem(Void item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//
//                            FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
//
//                            editIcon.setStyle(
//                                    " -fx-cursor: hand ;"
//                                    + "-glyph-size:28px;"
//                                    + "-fx-fill:#00E676;"
//                            );
//
//                            editIcon.setOnMouseClicked((MouseEvent event) -> {
//
//                                Users Docteur = Tbv.getSelectionModel().getSelectedItem();
//                                FXMLLoader loader = new FXMLLoader();
//                                loader.setLocation(getClass().getResource("Pick_date.fxml"));
//                                try {
//                                    loader.load();
//                                } catch (IOException ex) {
//                                    Logger.getLogger(Search_doctorController.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//
//                                Pick_dateController addStudentController = loader.getController();
//
//                                addStudentController.setTextField(Docteur.getId(), Docteur.getNom());
//                                Parent parent = loader.getRoot();
//                                Stage stage = new Stage();
//                                stage.setScene(new Scene(parent));
//                                stage.initStyle(StageStyle.UTILITY);
//                                stage.show();
//
//                            });
//
//                            HBox managebtn = new HBox(editIcon);
//                            managebtn.setStyle("-fx-alignment:center");
//                            // HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
//                            HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
//
//                            setGraphic(managebtn);
//
//                            setText(null);
//
//                        }
//                    }
//                };
//                return cell;
//            }
//        };
//
//        Action_Col.setCellFactory(cellFactory);
//
//        Img_Col.setCellFactory(column -> {
//            return new TableCell<User, ImageView>() {
//                @Override
//                protected void updateItem(ImageView item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (item == null || empty) {
//                        setGraphic(null);
//                    } else {
//                        Users user = getTableView().getItems().get(getIndex());
//                        if (user != null && user.getImage() != null) {
//                            try {
//
//                                ImageView imageView = user.getImage();
//                                imageView.setFitHeight(50);
//                                imageView.setFitWidth(50);
//                                setGraphic(imageView);
//                            } catch (Exception e) {
//                                // Handle the exception if the image cannot be loaded
//                                e.printStackTrace();
//                            }
//                        } else {
//                            setGraphic(null);
//                        }
//                    }
//                }
//            };
//        });
//
//        Tbv.setItems(list);
    }

    @FXML
    private void btnFiche(ActionEvent event) {
        FXMain.setScene("Fiche");
    }

}
