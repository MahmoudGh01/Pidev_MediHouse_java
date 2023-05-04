/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;


import edu.MediHouse.entities.Produit;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.ServiceProduit;
import edu.MediHouse.services.ServiceUser;
import edu.MediHouse.tools.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class ListProduitsController implements Initializable {

    @FXML
    private TableView<Produit> TabMagasin;
    @FXML
    private TableColumn<Produit, Integer> IdProTab;
    @FXML
    private TableColumn<Produit, String> nomProTab;
    @FXML
    private TableColumn<Produit,Double> ProxProdTab;
    @FXML
    private TableColumn<Produit, Integer> QuanProdTab;
    @FXML
    private TableColumn<Produit, Integer> DisProTab;
    @FXML
    private TableColumn<Produit, String> ImgProTab;
    @FXML
    private TableColumn<Produit, String> ColorProdTab;
    @FXML
    private TextField searchProd;

    /**
     * Initializes the controller class.
     */
    Connection cnx = MyConnection.getInstance().getCnx();
  ServiceProduit sr = new ServiceProduit(cnx);
    @FXML
    private ImageView imageV;
    @FXML
    private Label Err;
    private Circle profilepicture;
    private Label Username;
     Users u =new Users();
     ServiceUser su=new ServiceUser();
    @FXML
    private Circle profilepicture1;
    @FXML
    private Label Username1;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
         AfficherProduit(sr.getAll());
           u=su.getUserByEmail(InterfaceLogineeController.iduserglobal);
        Image im = new Image(u.getProfilePicture());
        ImagePattern pattern = new ImagePattern(im);
        profilepicture1.setFill(pattern);
        profilepicture1.setStroke(Color.SEAGREEN);
        profilepicture1.setEffect(new DropShadow(20, Color.BLACK));
        Username1.setText(u.getNom().toUpperCase()+" "+u.getPrenom().toUpperCase());
           profilepicture1.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            profile(event);
                  }
    });
    }    

   

    @FXML
    private void SuppProdGo(ActionEvent event) throws IOException {
        try {
   FXMain.pr = TabMagasin.getSelectionModel().getSelectedItem();
                  if (FXMain.pr != null) {
                       FXMain.setScene("SupprimerProduit");
                
            }
                  else
                       Err.setText("Aucun Pro Selectionnée");
    
        } catch (Exception e) {
        }
    }
    @FXML
    private void ModifierProdGo(ActionEvent event) {
         try {
   FXMain.pr = TabMagasin.getSelectionModel().getSelectedItem();
                  if (FXMain.pr != null) {
                       FXMain.setScene("ModifeirProduit");
                 
            }
                  else
                       Err.setText("Aucun Pro Selectionnée");
    
        } catch (Exception e) {
        }
    }


            void AfficherProduit(ObservableList<Produit>  list){
           // ObservableList<Article>  list  = sa.getAll();
        IdProTab.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("id"));
        nomProTab.setCellValueFactory(new PropertyValueFactory<Produit, String>("nomproduit"));
        ProxProdTab.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix"));
        QuanProdTab.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantityproduit"));
        DisProTab.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("dispoproduit"));
        ImgProTab.setCellValueFactory(new PropertyValueFactory<Produit, String>("img"));
        ColorProdTab.setCellValueFactory(new PropertyValueFactory<Produit, String>("color"));
       
        TabMagasin.setItems(list);
        FilteredList<Produit> filteredData = new FilteredList<>(list, b -> true);
		// 2. Set the filter Predicate whenever the filter changes.
		searchProd.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(m -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (m.getNomproduit().toLowerCase().contains(lowerCaseFilter) ) {
					return true; }
                            
                                else if (m.getColor().toLowerCase().contains(lowerCaseFilter)) {
					return true;}
                                else if (m.getImg().toLowerCase().contains(lowerCaseFilter)) {
					return true;}
                         
				else return String.valueOf(m.getDispoproduit()).contains(lowerCaseFilter); // Does not match.           
	});
		});
                SortedList<Produit> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(TabMagasin.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		TabMagasin.setItems(sortedData);
    
    }

    @FXML
    private void imageget(ActionEvent event) {
                  try {
                  FXMain.pr = TabMagasin.getSelectionModel().getSelectedItem();
                  if (FXMain.pr != null) {
                      System.out.println(FXMain.pr);
                      Image img = new Image(FXMain.pr.getImg());
              imageV.setImage(img);
            }
                  else
                       Err.setText("Aucun Pro Selectionnée");
    
        } catch (Exception e) {
        }
        
    }

    private void goCommande(ActionEvent event) throws IOException {
          searchProd.getScene().getWindow().hide();
              Parent root = FXMLLoader.load(getClass().getResource("ListCommandes.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
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
    private void CsVCreate(ActionEvent event) {
        sr.csv();
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
           FXMain.setScene("ProfilePara");
    }

    @FXML
    private void ListeC(ActionEvent event) {
         FXMain.setScene("ListCommandes");
    }
    


   
}
