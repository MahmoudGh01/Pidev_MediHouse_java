/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import edu.MediHouse.entities.Role;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.ServiceUser;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
/**
 * FXML Controller class
 *
 * @author chaab
 */
public class InterfacesignupController implements Initializable  {
private static String profilePicture="";
   private static final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
     private static final String regex2 = "^(?=.*[0-9])"
                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=.*[@#$%^&+=])"
                       + "(?=\\S+$).{8,20}$";
   
     
    
     @FXML
    private TextField tfemail;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfnumtel;
     private Map<String, String> suggestionsMap = new HashMap<>();
    @FXML
    private TextField tfadresse;
    @FXML
    private RadioButton rbmale;
    @FXML
    private RadioButton rbfemale;
    @FXML
    private PasswordField tfmdp;
    @FXML
    private RadioButton rbdoctor;
    @FXML
    private RadioButton rbpatient;
    @FXML
    private RadioButton rbpara;
    ServiceUser su =new ServiceUser();
    private String cImageUrl = "";
    public static String emailsignup;
    @FXML
    private ProgressBar progressBarMDP;
    @FXML
    private Label labelMDP;
    @FXML
    private ImageView show;
    @FXML
    private ImageView hide;
    @FXML
    private DatePicker date;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        rbmale.setSelected(true);
        tfmdp.setOnKeyReleased(this::progres);
                configureAutoSuggestions();
                // Ajout d'un écouteur d'événements sur le champ tfAdresse
        tfadresse.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Récupération de l'adresse sélectionnée
                String selectedAdresse = tfadresse.getText();
                // Mise à jour du champ numtel avec l'indicatif téléphonique correspondant
                tfnumtel.setText(suggestionsMap.getOrDefault(selectedAdresse, ""));
            }
        });
        show.setOnMouseClicked(event -> showPassword());
        hide.setOnMouseClicked(event -> hidePassword());
       
         

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
        AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(tfadresse, suggestionsMap.keySet().toArray(new String[0]));

        // Définir le nombre minimum de caractères pour activer les suggestions
        autoCompletionBinding.setMinWidth(2);
    }
       
         
private TextField overlay;
    /*private void showPassword() {
      tfmdp.setPromptText(tfmdp.getText());
            tfmdp.setText("");
            tfmdp.setDisable(true);
              show.setVisible(false);
        hide.setVisible(true);
    }

     private void hidePassword() {
         tfmdp.setText(tfmdp.getPromptText());
            tfmdp.setPromptText("");
            tfmdp.setDisable(false);
            show.setVisible(true);
        hide.setVisible(false);
    }*/
     private void showPassword() {
     tfmdp.setPromptText(tfmdp.getText());
            tfmdp.setText("");
            tfmdp.setDisable(true);
    show.setVisible(false);
    hide.setVisible(true);
}

private void hidePassword() {
    tfmdp.setText(tfmdp.getPromptText());
            tfmdp.setPromptText("");
            tfmdp.setDisable(false);
    show.setVisible(true);
    hide.setVisible(false);
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
            profilePicture= file.getName();
            System.out.println(profilePicture);
            Image image = new Image(TempprofilePicture);
            
            cImageUrl = TempprofilePicture;
        }
    }

    @FXML
    private void signup(ActionEvent event) {
         String erreur=controleDeSaisie();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        else {
            Users u=new Users();
            u.setNom(tfnom.getText());
            u.setPrenom(tfprenom.getText());
            u.setEmail(tfemail.getText());
            u.setProfilePicture(cImageUrl);            
            u.setPassword(tfmdp.getText());
             // Mettre en place l'auto-suggestion pour le TextField tfadresse
        
            u.setAdresse(tfadresse.getText());
            u.setTelephone(tfnumtel.getText());
            u.setGenre(rbmale.isSelected() ? "male" : "female");
            u.setRoles(rbdoctor.isSelected() ? Role.ROLE_DOCTOR : rbpara.isSelected() ? Role.ROLE_PARA : rbpatient.isSelected() ? Role.ROLE_PATIENT : Role.ROLE_ADMIN);
            u.setDatenes(Date.valueOf(date.getValue()));
            
            su.ajouter(u);
            emailsignup=u.getEmail();
            FXMain.setScene("Verfication");

        }
    }

     public String controleDeSaisie(){
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
				")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(tfemail.getText());
        String erreur="";
         if (cImageUrl.equals("")) {
             erreur+="-select a picture\n";
           
        }
        if(su.checkuser(tfemail.getText())){
            erreur+="-email existe deja\n";
        }
        // Vérifier que la date de naissance est valide
    LocalDate selectedDate = date.getValue();
    LocalDate limitDate = LocalDate.of(2004, 12, 31);
    if (selectedDate != null && selectedDate.isAfter(limitDate)) {
        erreur += "- Date de naissance refuser age 18 ans\n";
    }
        if(tfnom.getText().trim().isEmpty()){
            erreur+="-nom vide\n";
        }
        if(tfprenom.getText().trim().isEmpty()){
            erreur+="-prenom vide\n";
        }
        // Vérifier que l'adresse e-mail est valide
    String email = tfemail.getText().trim();
    if (!email.contains("@") || (!email.endsWith(".com") && !email.endsWith(".fr") && !email.endsWith(".tn"))) {
        erreur += "- Adresse e-mail invalide\n";
    }

        if(tfadresse.getText().trim().isEmpty()){
            erreur+="-adresse vide\n";
        }
        if (tfnumtel.getText().trim().isEmpty()) {
        erreur += "-numtel vide\n";
    } else {
        String phonePattern = "^[+]?[0-9]{10,13}$"; // Pattern to match valid phone numbers
        if (!tfnumtel.getText().trim().matches(phonePattern)) {
            erreur += "-numtel invalide\n";
        }
    }
        if(tfmdp.getText().trim().isEmpty()){
            erreur+="-mdp vide\n";
        }
       
        
        if(!matcher.find()){
            erreur+="-email incorrect\n";
        }
        if(tfmdp.getText().length()<4){
        erreur+="-mot de passe doit etre sup a 4 char\n";
    }
    Pattern mdpPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    Matcher mdpMatcher = mdpPattern.matcher(tfmdp.getText());
    if (!mdpMatcher.matches()) {
        erreur+="-mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial et avoir une longueur d'au moins 8 caractères\n";
    }
        return erreur;
    }
     
     
     private int checkPasswordStrength(String password) {
    // Cette fonction retourne un score de 0 à 4 en fonction de la difficulté du mot de passe
    int score = 0;
    Pattern mdpPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    Matcher mdpMatcher = mdpPattern.matcher(password);
    if (mdpMatcher.matches()) {
        score = 4;
    } else {
        if (password.length() >= 8) {
            score += 1;
        }
        if (password.matches(".*\\d.*")) {
            score += 1;
        }
        if (password.matches(".*[A-Z].*")) {
            score += 1;
        }
        if (password.matches("^(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            score += 1;
        }
    }
    return score;
}
    
    @FXML
    private void gotologin(MouseEvent event) {
        FXMain.setScene("InterfaceLogin");
    }

    @FXML
    private void progres(KeyEvent event) {
          String password = tfmdp.getText();
        int score = checkPasswordStrength(password);
        progressBarMDP.setProgress(score / 4.0);
        if (score < 2) {
            progressBarMDP.setStyle("-fx-accent: red;");
            labelMDP.setText("Mot de passe faible");
        } else if (score < 4) {
            progressBarMDP.setStyle("-fx-accent: orange;");
            labelMDP.setText("Mot de passe moyen");
        } else {
            progressBarMDP.setStyle("-fx-accent: green;");
            labelMDP.setText("Mot de passe fort");
        }
    }
    
}
