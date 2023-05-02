/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;


import edu.MediHouse.entities.MyListener;
import edu.MediHouse.entities.Produit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author sabri
 */
public class ItemController implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLable;
    @FXML
    private ImageView img;
    private Label disqlab;
    @FXML
    private VBox gridc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(produit);
    }

    private Produit produit;
    private MyListener myListener;

    public void setData(Produit p, MyListener myListener) {
        this.produit = p;
        this.myListener = myListener;
        nameLabel.setText(produit.getNomproduit());
        priceLable.setText("" + produit.getPrix());
     if(p.getQuantityproduit() == 0){
     gridc.setStyle("-fx-background-color: " + "#d6cccc" + ";\n" +
                "    -fx-background-radius: 30;");
             }
  Image img1 = new Image(p.getImg());
   img.setImage(img1);
    }
    
}
