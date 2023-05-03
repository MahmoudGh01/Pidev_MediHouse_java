/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import edu.MediHouse.entities.Commande;
import edu.MediHouse.services.ServiceCommande;
import edu.MediHouse.tools.MyConnection;
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
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    Connection cnx = MyConnection.getInstance().getCnx();
    ServiceCommande sr = new ServiceCommande(cnx);
    @FXML
    private TextField searchProd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Historique");
        ObservableList<Commande> cmm = sr.getAll();
        ObservableList<Commande> l = FXCollections.observableArrayList(cmm);

//        for (Commande commande : cmm) {
//            System.out.println(commande);
//            if (commande.getUser().equals(FXMain.user) == true) {
//                l.add(commande);
//            }
//        }

        AfficherCommande(l);
    }

    void AfficherCommande(ObservableList<Commande> list) {
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
                if (m.getUser().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (m.getUser().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return String.valueOf(m.getId()).contains(lowerCaseFilter); // Does not match.           
                }
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
        FXMain.setScene("ProduitsUser");

    }
}
