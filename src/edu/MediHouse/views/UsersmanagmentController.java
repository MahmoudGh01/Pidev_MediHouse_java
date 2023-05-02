/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import edu.MediHouse.entities.Role;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.BooleanTableCell;
import edu.MediHouse.services.ServiceUser;
import edu.MediHouse.tools.MyConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;

/**
 * FXML Controller class
 *
 * @author chaab
 */
public class UsersmanagmentController implements Initializable {

    @FXML
    private Circle ProfilePic1;
    @FXML
    private Label UserName1;
    @FXML
    private TableView<Users> tvuser;
    @FXML
    private TableColumn<Users, String> cnom;
    @FXML
    private TableColumn<Users, String> cprenom;
    @FXML
    private TableColumn<Users, String> cemail;
    @FXML
    private TableColumn<Users, String> cadresse;
    @FXML
    private TableColumn<Users, String> ctelephone;
    @FXML
    private TableColumn<Users, Role> crole;
Users u =new Users();
     ServiceUser su=new ServiceUser();
    
     ObservableList<Users> data=FXCollections.observableArrayList();
    @FXML
    private TableColumn<Users, String> cgenre;
    @FXML
    private TableColumn<Users, Boolean> Cact;
     @FXML
    private TextField searchBox;
    @FXML
    private TableColumn<Users, Date> cdate;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
       @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Initialize table columns
        cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cadresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        ctelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        crole.setCellValueFactory(new PropertyValueFactory<>("roles"));
        cgenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        Cact.setCellValueFactory(new PropertyValueFactory<>("act"));
        cdate.setCellValueFactory(new PropertyValueFactory<>("Datenes"));

        // Load all users data
        loadData();

      

        // Initialize user info
        u = su.getUserByEmail(InterfaceLogineeController.iduserglobal);
        Image im = new Image(u.getProfilePicture());
        ImagePattern pattern = new ImagePattern(im);
        ProfilePic1.setFill(pattern);
        ProfilePic1.setStroke(Color.SEAGREEN);
        ProfilePic1.setEffect(new DropShadow(20, Color.BLACK));
        UserName1.setText("ADMIN " + u.getNom().toUpperCase() + " " + u.getPrenom().toUpperCase());
//searchBox.search;
search();
  ProfilePic1.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            profile(event);
                  }
    });
    }
// Load all users data into the table
    private void loadData() {
        List<Users> usersList = su.afficher();
        data.clear();
        data.addAll(usersList);
        tvuser.setItems(data);
    }

    // Search users by search term and display the results in the table
   
   
    private void Profile(ActionEvent event) {
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

    @FXML
    private void Dashboard(ActionEvent event) {
        FXMain.setScene("Dashbord");
    }

    @FXML
    private void reclamation(ActionEvent event) {
        FXMain.setScene("Rec");
    }

    @FXML
    private void forum(ActionEvent event) {
        FXMain.setScene("Forum");
    }

    @FXML
    private void Users(ActionEvent event) {
         FXMain.setScene("Usersmanagment");
    }

    @FXML
    private void RDV(ActionEvent event) {
        FXMain.setScene("RDV");
    }

    

    @FXML
    private void resfreshuser() {
        
           data.clear();
        data=FXCollections.observableArrayList(su.afficher());
       
        cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cadresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        ctelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        cgenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        crole.setCellValueFactory(new PropertyValueFactory<>("roles"));
        cdate.setCellValueFactory(new PropertyValueFactory<>("Datenes"));

        tvuser.setItems(data);
    }

    @FXML
    private void remove(ActionEvent event) {
          if (tvuser.getSelectionModel().getSelectedItem() != null) {
        int id = tvuser.getSelectionModel().getSelectedItem().getId();
        
        // Create a confirmation dialog box
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet User?");
        Optional<ButtonType> result = alert.showAndWait();
        
        // If the user confirms the deletion, call the supprimer() method and refresh the list
        if (result.isPresent() && result.get() == ButtonType.OK) {
            su.supprimer(id);
            resfreshuser();
        }
    } else {
        // Display an error message if no item is selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un Utilisateur");
        alert.showAndWait();
    }
    }
    void search() {
        Users p = new Users();
        cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cadresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        ctelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        cgenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        crole.setCellValueFactory(new PropertyValueFactory<>("roles"));
        cdate.setCellValueFactory(new PropertyValueFactory<>("Datenes"));
        
        
       
        tvuser.setItems(data);
       
        FilteredList<Users> filteredData = new FilteredList<>(data, b -> true);
       
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Users us) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (us.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (us.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
                                else if (us.getAdresse().indexOf(lowerCaseFilter)!=-1){
				     return true;
                                }
                                else if (us.getAdresse().indexOf(lowerCaseFilter)!=-1){
				     return true;
                                }else if (us.getAdresse().toUpperCase().indexOf(lowerCaseFilter.toUpperCase()) != -1){
                                    return true; // Filter matches address.
} 
                                else if (us.getEmail().indexOf(lowerCaseFilter)!=-1){
				     return true;
                                }
                                
                                
                               
                                else if (String.valueOf(us.getDatenes()).indexOf(lowerCaseFilter)!=-1){
				     return true;
                                }
                                else if (String.valueOf(us.getTelephone()).indexOf(lowerCaseFilter)!=-1){
				     return true;
                                }
                                
                                
                               
				     else  
				    	 return false; // Does not match.
			});
		});
        SortedList<Users> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvuser.comparatorProperty());
        tvuser.setItems(sortedData);
   }
   /* if (tvuser.getSelectionModel().getSelectedItem() != null) {
        int id = tvuser.getSelectionModel().getSelectedItem().getId();
        
        // Create a confirmation dialog box
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet User?");
        Optional<ButtonType> result = alert.showAndWait();
        
        // If the user confirms the deletion, call the supprimer() method and refresh the list
        if (result.isPresent() && result.get() == ButtonType.OK) {
                 String requete="UPDATE user  SET activate = 0 WHERE id =?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
             pst.setInt(1, id);
            pst.executeUpdate();
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Users details");
            
 
alert.setHeaderText("Users details");
alert.setContentText("This user is banned!");
 
alert.showAndWait();
            resfreshuser();
        }
    } else {
        // Display an error message if no item is selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un Utilisateur");
        alert.showAndWait();
    }*/
    
     @FXML
    private void Activate(ActionEvent event) throws SQLException  {
         if (tvuser.getSelectionModel().getSelectedItem() != null) {
        int id = tvuser.getSelectionModel().getSelectedItem().getId();
        
        // Create a confirmation dialog box
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir Activer cet User?");
        Optional<ButtonType> result = alert.showAndWait();
        
        // If the user confirms the deletion, call the supprimer() method and refresh the list
        if (result.isPresent() && result.get() == ButtonType.OK) {
             String requete="UPDATE user  SET activate = 1 WHERE id =?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
             pst.setInt(1, id);
            pst.executeUpdate();
              Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Users details");
           alert2.setHeaderText("Users details");
        alert2.setContentText("This user session is activated ");
        alert2.showAndWait();
            resfreshuser();
        }
    } else {
        // Display an error message if no item is selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un Utilisateur");
        alert.showAndWait();
    }
    }


    @FXML
    private void desactivate(ActionEvent event) throws SQLException {
         if (tvuser.getSelectionModel().getSelectedItem() != null) {
        int id = tvuser.getSelectionModel().getSelectedItem().getId();
        
        // Create a confirmation dialog box
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir ban cet User?");
        Optional<ButtonType> result = alert.showAndWait();
        
        // If the user confirms the deletion, call the supprimer() method and refresh the list
        if (result.isPresent() && result.get() == ButtonType.OK) {
                 String requete="UPDATE user  SET activate = 0 WHERE id =?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
             pst.setInt(1, id);
            pst.executeUpdate();
              Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Users details");
            alert2.setHeaderText("Users details");
            alert2.setContentText("This user is banned!");
            alert2.showAndWait();
            resfreshuser();
        }
    } else {
        // Display an error message if no item is selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un Utilisateur");
        alert.showAndWait();
    }
    }

    @FXML
    private void profile(MouseEvent event) {
        FXMain.setScene("ProfileAdmin");
    }
}
