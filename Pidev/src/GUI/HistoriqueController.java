/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entites.Commande;
import Entites.Produit;
import Services.ServiceCommande;
import Tools.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class HistoriqueController implements Initializable {

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
 Connection cnx = DataSource.getInstance().getConnection();
  Services.ServiceCommande sr = new ServiceCommande(cnx);
    @FXML
    private TextField searchProd;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Commande> cmm = sr.getAll();
               ObservableList<Commande> l =  FXCollections.observableArrayList();
       
        for (Commande commande : cmm) {
            System.out.println(commande);
            if(commande.getUser().equals(Main.user) == true)
                l.add(commande);
        }
        
        AfficherCommande(l);
    }    
      void AfficherCommande(ObservableList<Commande>  list){
           // ObservableList<Article>  list  = sa.getAll();
        IdComTab.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("id"));
        DateComTab.setCellValueFactory(new PropertyValueFactory<Commande, Date>("datecommande"));
        prixComTab.setCellValueFactory(new PropertyValueFactory<Commande, Double>("prix"));
        QuanCommTab.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("qtcommande"));
        ColorProdTab.setCellValueFactory(new PropertyValueFactory<Commande, String>("user"));

       
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

    @FXML
    private void retourPannier(ActionEvent event) throws IOException {
           searchProd.getScene().getWindow().hide();
              Parent root = FXMLLoader.load(getClass().getResource("ProduitsUser.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show(); 
    }
}
