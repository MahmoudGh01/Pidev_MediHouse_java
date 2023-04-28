/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entites.Produit;
import Services.ServiceProduit;
import Tools.DataSource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author sabri
 */
public class ModifeirProduitController implements Initializable {

    @FXML
    private TextField NomProduit;
    @FXML
    private TextField QuantProduit;
    @FXML
    private TextField PrixProduit;
    @FXML
    private Label ErrNom;
    @FXML
    private Label ErrQnt;
    @FXML
    private Label Errprix;
    @FXML
    private ImageView imageV;
    @FXML
    private TextField imageText;
    @FXML
    private Label ErrImage;
    @FXML
    private ColorPicker ColorProdut;
 Connection cnx = DataSource.getInstance().getConnection();
  Services.ServiceProduit sr = new ServiceProduit(cnx);
              String imageeget = "pas d'image";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageeget = Main.pr.getImg();
              NomProduit.setText(Main.pr.getNomproduit());
      PrixProduit.setText(Main.pr.getPrix()+"");
      QuantProduit.setText(Main.pr.getQuantityproduit()+"");
      imageText.setText(Main.pr.getImg());
  Image img = new Image(Main.pr.getImg());
              imageV.setImage(img);  
            init();
      
    }    
    public void init(){
            ErrNom.setVisible(false);
            ErrImage.setVisible(false);
            ErrQnt.setVisible(false);
            Errprix.setVisible(false);
       
    }

    @FXML
    private void Enregistremagasin(ActionEvent event) throws IOException {
                       init();
        
        int x = 0;
        try {
            Double.parseDouble(PrixProduit.getText());
        } catch (Exception e) {
             Errprix.setVisible(true);
              Errprix.setText("Invalide numero");
        }
        
          try {
            Integer.parseInt(QuantProduit.getText());
        } catch (Exception e) {
             ErrQnt.setVisible(true);
              ErrQnt.setText("Invalide numero");
        }
       if (NomProduit.getText().equals("")){
              ErrNom.setVisible(true);
              ErrNom.setText("Champ Obligatoire");
        x=1;
        }
       
                        if (imageText.getText().equals("")){
              ErrImage.setVisible(true);
              ErrImage.setText("Champ Obligatoire");
        x=1;
        }   

                   if (x == 0 )
                   {
               System.out.println("aaaaaaaaa");
        System.out.println(ColorProdut.getValue().toString());
                       Produit r = new Produit(Main.pr.getId(),NomProduit.getText(),Double.parseDouble(PrixProduit.getText()),Integer.parseInt(QuantProduit.getText()),1, imageeget,"#"+ColorProdut.getValue().toString().subSequence(2, 8));
                       sr.modifier(r);
           ErrImage.getScene().getWindow().hide();
              Parent root = FXMLLoader.load(getClass().getResource("ListProduits.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
                   }
    }


    @FXML
    private void file(ActionEvent event) {
          FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick a banner file!");
        fileChooser.setInitialDirectory(new File("\\"));
        Stage stage = new Stage();
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
        new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),   
        new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(stage);
        try {
        BufferedImage bufferedImage = ImageIO.read(file);
        Image img = SwingFXUtils.toFXImage(bufferedImage, null);
            System.out.println(file.getAbsolutePath());
            imageeget = file.toURI().toURL().toString();
                        System.out.println(imageeget);

            imageV.setImage(img);
//            Image image = new Image(file.getAbsolutePath());
            imageText.setText( file.getAbsolutePath().toString());
       //     image_modifier1.setImage(image);
        } catch (IOException ex) {
        System.out.println("could not get the image");
        }
    }



    @FXML
    private void retourAjout(ActionEvent event) throws IOException {
              ErrImage.getScene().getWindow().hide();
              Parent root = FXMLLoader.load(getClass().getResource("ListProduits.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }

  
    
}
