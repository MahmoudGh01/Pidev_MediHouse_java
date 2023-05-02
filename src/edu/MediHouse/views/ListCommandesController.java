
package edu.MediHouse.views;


import edu.MediHouse.entities.Commande;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.ServiceCommande;
import edu.MediHouse.services.ServiceUser;
import edu.MediHouse.tools.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sabri
 */
public class ListCommandesController implements Initializable {

    @FXML
    private TableView<Commande> TabCommande;
    @FXML
    private TableColumn<Commande, Integer> IdComTab;
    @FXML
    private TableColumn<Commande, Date> DateComTab;
    @FXML
    private TableColumn<Commande, Double> prixComTab;
    @FXML
    private TableColumn<Commande, Integer> QuanCommTab;
    @FXML
    private TableColumn<Commande, String> ColorProdTab;
    @FXML
    private TextField searchProd;
    @FXML
    private Label Err;
 Connection cnx = MyConnection.getInstance().getCnx();
  ServiceCommande sr = new ServiceCommande(cnx);
    @FXML
    private Label ExpireLabel;
    @FXML
    private Circle profilepicture;
    @FXML
    private Label Username;
    Users u =new Users();
     ServiceUser su=new ServiceUser();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherCommande(sr.getAll());
        
          u=su.getUserByEmail(InterfaceLogineeController.iduserglobal);
        Image im = new Image(u.getProfilePicture());
        ImagePattern pattern = new ImagePattern(im);
        profilepicture.setFill(pattern);
        profilepicture.setStroke(Color.SEAGREEN);
        profilepicture.setEffect(new DropShadow(20, Color.BLACK));
        Username.setText(u.getNom().toUpperCase()+" "+u.getPrenom().toUpperCase());
           profilepicture.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            profile(event);
                  }
    });
    }    

    @FXML
    private void SuppProdGo(ActionEvent event) {
         try {
   FXMain.cm = TabCommande.getSelectionModel().getSelectedItem();
                  if (FXMain.cm != null) {
                      FXMain.setScene("SupprimerCommande");
                  
            }
                  else
                       Err.setText("Aucun Pro Selectionnée");
    
        } catch (Exception e) {
        }
    }

            void AfficherCommande(ObservableList<Commande>  list){
           // ObservableList<Article>  list  = sa.getAll();
        IdComTab.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("id"));
        DateComTab.setCellValueFactory(new PropertyValueFactory<Commande, Date>("datecommande"));
        prixComTab.setCellValueFactory(new PropertyValueFactory<Commande, Double>("prix"));
        QuanCommTab.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("qtcommande"));
        ColorProdTab.setCellValueFactory(new PropertyValueFactory<Commande, String>("User"));

       
        TabCommande.setItems(list);
        FilteredList<Commande> filteredData = new FilteredList<>(list, b -> true);
		// 2. Set the filter Predicate whenever the filter changes.
		searchProd.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(m -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (m.getUser().toLowerCase().contains(lowerCaseFilter) ) {
					return true; }
                            
                                else if (m.getUser().toLowerCase().contains(lowerCaseFilter)) {
					return true;}
                            
                         
				else return String.valueOf(m.getId()).contains(lowerCaseFilter); // Does not match.           
	});
		});
                SortedList<Commande> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(TabCommande.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		TabCommande.setItems(sortedData);
    
    }

    private void goProducts(ActionEvent event) throws IOException {
                     FXMain.setScene("ListProduits");
        
    }

    private void goUserspace(ActionEvent event) throws IOException {
                  searchProd.getScene().getWindow().hide();
              Parent root = FXMLLoader.load(getClass().getResource("ProduitsUser.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }

    @FXML
    private void pdfCreate(ActionEvent event) {
           FXMain.cm = TabCommande.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void expireed(ActionEvent event) {
           FXMain.cm = TabCommande.getSelectionModel().getSelectedItem();

        
      String ch =  sr.ExpirationDate(FXMain.cm);
      ExpireLabel.setText(ch);
    }

   

    private void profile(ActionEvent event) {
        FXMain.setScene("ProfilePara");
    }

    @FXML
    private void TabBord(ActionEvent event) {
        FXMain.setScene("ListProduits");
    }

    @FXML
    private void AddProduit(ActionEvent event) {
        FXMain.setScene("AjoutProduit");
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
           FXMain.setScene("ProfilePara");
    }

    @FXML
    private void ListeC(ActionEvent event) {
         FXMain.setScene("ListCommandes");
    }
}
