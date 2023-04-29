/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import co.yogesh.Captcha;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.ServiceUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JFrame; 
/**
 * FXML Controller class
 *
 * @author chaab
 */
public class InterfaceLogineeController implements Initializable {

    @FXML
    private TextField tfemail;
    @FXML
    private PasswordField tfmdp;
    @FXML
    private CheckBox cbox;
 ServiceUser su=new ServiceUser();
    public static String iduserglobal;
    @FXML
    private ImageView captchagenerate;
    @FXML
    private TextField captchainput;
     private JLabel tempLabel;
     Captcha cap = new Captcha();
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tempLabel = new JLabel();
       
        cap.setImageCaptcha(tempLabel);
        captchagenerate.setImage(SwingFXUtils.toFXImage(FXMain.iconToImage(tempLabel.getIcon()),null));
         JFrame frame = new JFrame("JOptionPane showMessageDialog example");
                    frame.setAlwaysOnTop(true);
    }    

    @FXML
    private void checkbox(ActionEvent event) {
        
         if (cbox.isSelected()){
            tfmdp.setPromptText(tfmdp.getText());
            tfmdp.setText("");
            tfmdp.setDisable(true);
        }else {
            tfmdp.setText(tfmdp.getPromptText());
            tfmdp.setPromptText("");
            tfmdp.setDisable(false);
        }
    }

    @FXML
    private void gotoforgotpassword(MouseEvent event) {
        FXMain.setScene("FXMLforgotpassword");
             }

    @FXML
    private void gotosignup(MouseEvent event) {
         FXMain.setScene("Interfacesignup");
        
    }

    @FXML
    private void login(ActionEvent event) {
        

                 
        Users u=su.login(tfemail.getText(), tfmdp.getText());
        String access;
       
        if(u!=null){
            iduserglobal=u.getEmail();
            switch (u.getRoles()) {
                //interface admin
                case ROLE_ADMIN:
         if(cap.Validate(tempLabel, captchainput.getText()))
       {
           System.out.println("captcha valid");
           FXMain.setScene("InterfaceAdmin");
       }else
       {
           System.out.println("captcha invalid");
            Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Captcha");
         alert.setContentText("The captcha code entered is invalid.");
        alert.showAndWait();
        cap.setImageCaptcha(tempLabel);
        captchagenerate.setImage(SwingFXUtils.toFXImage(FXMain.iconToImage(tempLabel.getIcon()),null));

          
       }
                    
                    
                    break;
                case ROLE_PATIENT:
                    //interface Patient
                        if(cap.Validate(tempLabel, captchainput.getText()))
       {
           System.out.println("captcha valid");
          FXMain.setScene("InterfaceePatient");
       }else
       {
           System.out.println("captcha invalid");
            Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Captcha");
         alert.setContentText("The captcha code entered is invalid.");
        alert.showAndWait();
        cap.setImageCaptcha(tempLabel);
        captchagenerate.setImage(SwingFXUtils.toFXImage(FXMain.iconToImage(tempLabel.getIcon()),null));

          
       }
                    
                    break;
                case ROLE_DOCTOR:
                    //interface Doctor
                        if(cap.Validate(tempLabel, captchainput.getText()))
       {
           System.out.println("captcha valid");
          FXMain.setScene("InterfaceDoctor");
       }else
       {
           System.out.println("captcha invalid");
            Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Captcha");
         alert.setContentText("The captcha code entered is invalid.");
        alert.showAndWait();
        cap.setImageCaptcha(tempLabel);
        captchagenerate.setImage(SwingFXUtils.toFXImage(FXMain.iconToImage(tempLabel.getIcon()),null));

          
       }
                    
                    break;
                default:
                    //interface Para
                        if(cap.Validate(tempLabel, captchainput.getText()))
       {
           System.out.println("captcha valid");
           FXMain.setScene("InterfacePara");
       }else
       {
           System.out.println("captcha invalid");
            Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Captcha");
         alert.setContentText("The captcha code entered is invalid.");
        alert.showAndWait();
        cap.setImageCaptcha(tempLabel);
        captchagenerate.setImage(SwingFXUtils.toFXImage(FXMain.iconToImage(tempLabel.getIcon()),null));

          
       }
                    
                    break;
            }

        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("invalide");
            alert.setContentText("Email ou mot de passe invalide!!");
            alert.showAndWait();
        }
    }
    
}
