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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author sabri
 */
public class AjoutProduitController implements Initializable {
 Connection cnx = MyConnection.getInstance().getCnx();
  ServiceProduit sr = new ServiceProduit(cnx);
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
        String imageeget = "pas d'image";
    @FXML
    private ColorPicker ColorProdut;
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
        // TODO
        init();
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
    public void init(){
            ErrNom.setVisible(false);
            ErrImage.setVisible(false);
            ErrQnt.setVisible(false);
            Errprix.setVisible(false);
       
    }
    @FXML
    private void Enregistremagasin(ActionEvent event) throws IOException {
               init();
                System.out.println(ColorProdut.getValue().toString().subSequence(2, 7));

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
       if (sr.depliqName( NomProduit.getText()) == 1){
              ErrNom.setVisible(true);
              ErrNom.setText("Nom Existe !! ");
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
     
                       Produit r = new Produit(NomProduit.getText(),Double.parseDouble(PrixProduit.getText()),Integer.parseInt(QuantProduit.getText()),1, imageeget,"#"+ColorProdut.getValue().toString().subSequence(2, 8));
                       sr.ajouter(r);
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
