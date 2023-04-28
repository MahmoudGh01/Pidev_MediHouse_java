/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entites.Commande;
import Entites.MyListener;
import Entites.Produit;
import Services.ServiceProduit;
import Tools.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sabri
 */
public class ProduitsUserController implements Initializable {

    @FXML
    private VBox chosenFruitCard;
    @FXML
    private Label fruitNameLable;
    @FXML
    private Label fruitPriceLabel;
    @FXML
    private ImageView fruitImg;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    private Image image;
        private MyListener myListener;
    private List<Produit> produits = new ArrayList<>();
    @FXML
    private Button addC;
    @FXML
    private ComboBox<Integer> cccombo;
    @FXML
    private Label qtlabel;
    @FXML
    private Label nameUser;
    @FXML
    private TextField inputSerach;
    /**
     * Initializes the controller class.
     */
    private void setChosenFruit(Produit p) {
        nameUser.setText(Main.user);
        fruitNameLable.setText(p.getNomproduit());
        fruitPriceLabel.setText( p.getPrix()+"");
           String aa="Dispo";
        if(p.getQuantityproduit() == 0){
            aa = "Non Dispo";
        qtlabel.setText(aa +": Qt ="+p.getQuantityproduit());
        }
        else{
               aa = "Disponible";
        qtlabel.setText(aa +": Qt ="+p.getQuantityproduit());
}
  Image img = new Image(p.getImg());
  fruitImg.setImage(img);
        chosenFruitCard.setStyle("-fx-background-color: " + p.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }
    Connection cnx = DataSource.getInstance().getConnection();
  Services.ServiceProduit sr = new ServiceProduit(cnx);
          ObservableList<Produit> ml = sr.getAll();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
       cccombo.getItems().setAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
       cccombo.setValue(1);
        if (ml.size() > 0) {
            setChosenFruit(ml.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Produit fruit) {
                    setChosenFruit(fruit);
                    Main.pr = fruit;
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < ml.size(); i++) {
                if(ml.get(i).getQuantityproduit() == 0){
                     FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(ml.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                    
                GridPane.setMargin(anchorPane, new Insets(10));
                }
                else{
                
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(sr.getAll().get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

            
            }
                        FilteredList<Produit> filteredData = new FilteredList<>(sr.getAll(), b -> true);
		// 2. Set the filter Predicate whenever the filter changes.
		inputSerach.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(m -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (m.getNomproduit().toLowerCase().contains(lowerCaseFilter) ) {
					return true; }
                            
                              
                            
                         
				else return String.valueOf(m.getId()).contains(lowerCaseFilter); // Does not match.           
	});
		});
                SortedList<Produit> sortedData = new SortedList<>(filteredData);
		ml=sortedData;
                System.out.println(ml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AddToPannier(ActionEvent event) throws IOException {
        int exist =-1 ;
        if(Main.pr.getQuantityproduit() < cccombo.getValue())
        {
                Parent root = FXMLLoader.load(getClass().getResource("errModal.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show(); 
        }
        else{
            
            for (int i = 0; i < Main.produitsCommandes.size() ; i++) {
                    if(Main.produitsCommandes.get(i).getId()== Main.pr.getId()){
            exist = i;
                    }}
            if(exist != -1){
                            int dd = Main.produitsCommandes.get(exist).getQuantityproduit() + cccombo.getValue();

            Main.produitsCommandes.remove(exist);
            Main.pr.setQuantityproduit(dd);
            Main.produitsCommandes.add(Main.pr);
                    sr.modifierQnt(Main.pr.getId(),cccombo.getValue());
      Parent root = FXMLLoader.load(getClass().getResource("AddToPannier.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
            }else{
            Main.pr.setQuantityproduit(cccombo.getValue());
        Main.produitsCommandes.add(Main.pr);
            System.out.println(Main.pr.getQuantityproduit());
            System.out.println(cccombo.getValue());
        sr.modifierQnt(Main.pr.getId(),cccombo.getValue());
              Parent root = FXMLLoader.load(getClass().getResource("AddToPannier.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
        }}
        
    }

    @FXML
    private void Openpannier(ActionEvent event) throws IOException {
         grid.getScene().getWindow().hide();
              Parent root = FXMLLoader.load(getClass().getResource("Panier.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show(); 
    }

    @FXML
    private void OpenCommande(ActionEvent event) throws IOException {
           grid.getScene().getWindow().hide();
              Parent root = FXMLLoader.load(getClass().getResource("ListCommandes.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show(); 
    }

    @FXML
    private void openHist(ActionEvent event) throws IOException {
           grid.getScene().getWindow().hide();
              Parent root = FXMLLoader.load(getClass().getResource("Historique.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show(); 
    }

    @FXML
    private void search(ActionEvent event) {
           grid.getChildren().clear();
    grid.getColumnConstraints().clear();
    grid.getRowConstraints().clear();
        System.out.println(ml);
        
        
         int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < ml.size(); i++) {
                     FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(ml.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                    
                GridPane.setMargin(anchorPane, new Insets(10));
                
                

            
            }
                
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
