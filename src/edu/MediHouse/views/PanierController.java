/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import edu.MediHouse.entities.Commande;
import edu.MediHouse.entities.Produit;
import edu.MediHouse.services.ServiceCommande;
import edu.MediHouse.services.ServiceProduit;
import edu.MediHouse.tools.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sabri
 */
public class PanierController implements Initializable {

    @FXML
    private TableView<Produit> TabMagasin;
    @FXML
    private TableColumn<Produit, Integer> IdProTab;
    @FXML
    private TableColumn<Produit, String> nomProTab;
    @FXML
    private TableColumn<Produit, Double> ProxProdTab;
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
    ServiceCommande sc = new ServiceCommande(cnx);
    @FXML
    private Label Err;
    @FXML
    private Label prixlabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        CalculePrix();
        System.out.println(FXMain.produitsCommandes);
        AfficherProduit(FXMain.produitsCommandes);
    }

    void AfficherProduit(ObservableList<Produit> list) {
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
                if (m.getNomproduit().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (m.getColor().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (m.getImg().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return String.valueOf(m.getDispoproduit()).contains(lowerCaseFilter); // Does not match.           
                }
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
    private void SuppProdGo(ActionEvent event) {
        try {
            FXMain.pn = TabMagasin.getSelectionModel().getSelectedItem();
            if (FXMain.pn != null) {
                FXMain.produitsCommandes.remove(FXMain.pn);
                CalculePrix();
            } else {
                Err.setText("Aucun Pro Selectionnée");
            }

        } catch (Exception e) {
        }
    }

    @FXML
    private void PasseCommande(ActionEvent event) throws IOException {

        Commande c = new Commande(0, new Date(System.currentTimeMillis()), 1, 1, "");
        sc.ajouter(c, FXMain.produitsCommandes);
        searchProd.getScene().getWindow().hide();
         FXMain.setScene("SuccCommande");
        
    }

    @FXML
    private void retourPannier(ActionEvent event) throws IOException {
        searchProd.getScene().getWindow().hide();
         FXMain.setScene("ProduitsUser");
        

    }

    public void CalculePrix() {
        Double ppp = 0.0;
        for (Produit p : FXMain.produitsCommandes) {
            ppp = ppp + p.getPrix() * p.getQuantityproduit();
        }
        FXMain.prix = ppp;
        prixlabel.setText(ppp + "");
    }

}
