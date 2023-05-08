/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.ServiceUser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.ProcessBuilder.Redirect.to;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author chaab
 */
public class ProfilePatientController implements Initializable {
private static String profilepictures="";
    private Circle profilepicture;
    private Label username;
    @FXML
    private TextField TFname;
    @FXML
    private TextField TFlastname;
    @FXML
    private TextField TFemail;
    @FXML
    private PasswordField TFpassword;
    @FXML
    private TextField TFadresse;
     private Map<String, String> suggestionsMap = new HashMap<>();
    @FXML
    private Button modifbtn;
    @FXML
    private TextField TFnultel;
    @FXML
    private Button changepicbtn;
    Users u =new Users();
    ServiceUser su=new ServiceUser();
    private String cImageUrl = "";
    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private Circle ProfilePic;
    @FXML
    private Label UserName;
    //  @FXML
   // private DatePicker dpdate;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         u=su.getUserByEmail(InterfaceLogineeController.iduserglobal);
        UserName.setText(u.getNom().toUpperCase()+" "+u.getPrenom().toUpperCase());
        Image im = new Image(u.getProfilePicture());
        ImagePattern pattern = new ImagePattern(im);
        ProfilePic.setFill(pattern);
        ProfilePic.setStroke(Color.SEAGREEN);
        ProfilePic.setEffect(new DropShadow(20, Color.BLACK));
        
        TFname.setText(u.getNom());
        TFlastname.setText(u.getPrenom());
        TFadresse.setText(u.getAdresse());
        TFemail.setText(u.getEmail());          
        TFnultel.setText(u.getTelephone());
        
                    configureAutoSuggestions();
                // Ajout d'un écouteur d'événements sur le champ tfAdresse
        TFadresse.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Récupération de l'adresse sélectionnée
                String selectedAdresse = TFadresse.getText();
                // Mise à jour du champ numtel avec l'indicatif téléphonique correspondant
                TFnultel.setText(suggestionsMap.getOrDefault(selectedAdresse, ""));
            }
        });
       ProfilePic.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            profile(event);
                  }
    });
    }   
    
 // Méthode pour configurer les suggestions automatiques
       private void configureAutoSuggestions() {
        // Remplir le tableau de suggestions avec les indicatifs téléphoniques correspondants
        suggestionsMap.put("Tunis-Ariana", "+216");       
        suggestionsMap.put("Tunis-Nabeul", "+216");
        suggestionsMap.put("Tunis-le Kef", "+216");
        suggestionsMap.put("Tunis-Sousse", "+216");
        suggestionsMap.put("Tunis-Sfax", "+216");
        suggestionsMap.put("Tunis-Gabes", "+216");
        suggestionsMap.put("Tunis-Bizerte", "+216");      
        suggestionsMap.put("Tunis-Tabarka", "+216");
        suggestionsMap.put("Tunis-Mahdia", "+216");
        suggestionsMap.put("Tunis-Marsa", "+216");
        suggestionsMap.put("Tunis-Monastir", "+216"); 
        suggestionsMap.put("Tunis-Matmata", "+216");
        
        suggestionsMap.put("France-Paris", "+33");       
        suggestionsMap.put("France-Marseille", "+33");
        suggestionsMap.put("France-Lyon", "+33");
        suggestionsMap.put("France-Nice", "+33");
        suggestionsMap.put("France-Montpellier", "+33");
        suggestionsMap.put("France-Strasbourg", "+33");
        suggestionsMap.put("France-Lille", "+33");
        suggestionsMap.put("France-Toulous", "+33");
        suggestionsMap.put("France-Nantes", "+33");
        suggestionsMap.put("France-Bordeaux", "+33");
        suggestionsMap.put("France-Angers", "+33");
        suggestionsMap.put("France-Reims", "+33");

        suggestionsMap.put("Etats-unis-New York", "+1");        
        suggestionsMap.put("Etats-unis-Atlanta", "+1");
        suggestionsMap.put("Etats-unis-Los Angeles", "+1");
        suggestionsMap.put("Etats-unis-San Francisco", "+1");
        suggestionsMap.put("Etats-unis-Washington", "+1");
        suggestionsMap.put("Etats-unis-Philadelphie", "+1");
        suggestionsMap.put("Etats-unis-Seattle", "+1");
        suggestionsMap.put("Etats-unis-Miami", "+1");
        suggestionsMap.put("Etats-unis-Houston", "+1");
        suggestionsMap.put("Etats-unis-Las Vegas", "+1");
        suggestionsMap.put("Etats-unis-Saint-Louis", "+1");
        suggestionsMap.put("Etats-unis-San jose", "+1");

        

        // Création d'un objet AutoCompletionBinding pour gérer les suggestions automatiques
        AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(TFadresse, suggestionsMap.keySet().toArray(new String[0]));

        // Définir le nombre minimum de caractères pour activer les suggestions
        autoCompletionBinding.setMinWidth(2);
    }
       
 private void profile(ActionEvent event) {
         FXMain.setScene("ProfilePatient");
 
    }
@FXML
private void QrCode(ActionEvent event) {
        
    // Generate QR code with user's information
    String text = String.format("Nom : %s%nPrénom : %s%nEmail : %s%nAdresse : %s%nTéléphone : %s", 
                                u.getNom(), u.getPrenom(), u.getEmail(), u.getAdresse(), u.getTelephone());
    int width = 300;
    int height = 300;
    String format = "png";
    File file = new File("C:\\Users\\user\\Documents\\NetBeansProjects\\Pidev_MediHouse_java\\src\\edu\\MediHouse\\images/","qrcode.png");
    try {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        qrCodeImageView.setImage(image);
    } catch (WriterException ex) {
        ex.printStackTrace();
    }
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
   private void RechMed(ActionEvent event) {
        FXMain.setScene("Search_doctor");
    }

    @FXML
    private void parapharmacie(ActionEvent event) {
        FXMain.setScene("ProduitsUser");
    }
    
    @FXML
    private void forum(ActionEvent event) {
        FXMain.setScene("pi");
    }

    @FXML
    private void reclamation(ActionEvent event) {
       FXMain.setScene("Reclamation");
    }

    @FXML
    private void RDVP(ActionEvent event) {
       FXMain.setScene("ListRDV");
    }

    @FXML
    private void modifClicked(ActionEvent event) {
        String erreur=controleDeSaisie();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        else {
         u=su.getUserByEmail(InterfaceLogineeController.iduserglobal);
            u.setNom(TFname.getText());
            u.setPrenom(TFlastname.getText());
            u.setEmail(TFemail.getText());           
            u.setPassword(TFpassword.getText());
            u.setAdresse(TFadresse.getText());
            u.setTelephone(TFnultel.getText()); 
            //u.setDatenes(Date.valueOf(dpdate.getValue()));
            if(cImageUrl.length()>0){
            u.setProfilePicture(cImageUrl);
            }
            else{u.setProfilePicture(u.getProfilePicture());}
            su.modifier(u.getId(),u);
        }
    }
    
    
    
    @FXML
    private void uploadsiguppic(ActionEvent event) {
         FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("C:\\Users\\user\\Documents\\NetBeansProjects\\Pidev_MediHouse_java\\src\\edu\\MediHouse\\images"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String TempprofilePicture = file.toURI().toString();
            System.out.println(TempprofilePicture);
            profilepictures= file.getName();
            System.out.println(profilepictures);
            Image image;
             image = new Image(TempprofilePicture);
            
            cImageUrl = TempprofilePicture;
            Image images = new Image(TempprofilePicture);
            ImagePattern pattern = new ImagePattern(image);
            profilepicture.setFill(pattern);
            profilepicture.setStroke(Color.SEAGREEN);
            profilepicture.setEffect(new DropShadow(20, Color.BLACK));
        }
    }
    
    
    public String controleDeSaisie(){
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
				")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(TFemail.getText());
        String erreur="";
         
        
        
        if(TFname.getText().trim().isEmpty()){
            erreur+="-nom vide\n";
        }
        if(TFlastname.getText().trim().isEmpty()){
            erreur+="-prenom vide\n";
        }
        // Vérifier que l'adresse e-mail est valide
    String email = TFemail.getText().trim();
    if (!email.contains("@") || (!email.endsWith(".com") && !email.endsWith(".fr") && !email.endsWith(".tn"))) {
        erreur += "- Adresse e-mail invalide\n";
    }

        if(TFadresse.getText().trim().isEmpty()){
            erreur+="-adresse vide\n";
        }
        if (TFnultel.getText().trim().isEmpty()) {
        erreur += "-numtel vide\n";
    } else {
        String phonePattern = "^[+]?[0-9]{10,13}$"; // Pattern to match valid phone numbers
        if (!TFnultel.getText().trim().matches(phonePattern)) {
            erreur += "-numtel invalide\n";
        }
    }
        if(TFpassword.getText().trim().isEmpty()){
            erreur+="-mdp vide\n";
        }
       
        
        if(!matcher.find()){
            erreur+="-email incorrect\n";
        }
        if(TFpassword.getText().length()<4){
        erreur+="-mot de passe doit etre sup a 4 char\n";
    }
    Pattern mdpPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    Matcher mdpMatcher = mdpPattern.matcher(TFpassword.getText());
    if (!mdpMatcher.matches()) {
        erreur+="-mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial et avoir une longueur d'au moins 8 caractères\n";
    }
        return erreur;
    }

    private void profile(MouseEvent event) {
         FXMain.setScene("ProfilePatient");
    }

    @FXML
    private void Profile1(MouseEvent event) {
        FXMain.setScene("ProfilePatient");
    }
   



    
}
