/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse.sprintjava.views;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.mail.MessagingException;
import medihouse.sprintjava.entities.Fiche;
import medihouse.sprintjava.entities.RendezVous;
import medihouse.sprintjava.services.FicheCRUD;
import medihouse.sprintjava.services.RendezVousCRUD;
import medihouse.sprintjava.services.UserCRUD;
import medihouse.sprintjava.tools.MailAPI;

/**
 * FXML Controller class
 *
 * @author user
 */
public class Pick_dateController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private DatePicker picker;
    @FXML
    private TextField TfLocal;
    @FXML
    private Label Doc_Name;
    int Id_Docteur,Id_RDV;
    //RendezVousCRUD RD =new RendezVousCRUD();
    UserCRUD UC = new UserCRUD();
    private boolean update;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnAdd(ActionEvent event) throws MessagingException {
        if (picker.getValue() == null || TfLocal.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Tous les champs sont obligatoires");
            alert.showAndWait();
            return;
        }

        if (picker.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("La Date doit etre valide");
            alert.showAndWait();
            return;
        }
        
        FicheCRUD f = new FicheCRUD();

        RendezVousCRUD rdv = new RendezVousCRUD();
        RendezVous r;

        
        if (!update){
            r = new RendezVous((Fiche) f.getFicheByPatient(Id_Docteur), UC.getUser(Id_Docteur), UC.getUser(9), Date.valueOf(picker.getValue()), TfLocal.getText());
        rdv.ajouterRendezVous(r);
        MailAPI M =new MailAPI();
        M.sendMail("gharbi714@gmail.com","hh", "test");
    }else{
          
            
         r = new RendezVous(Id_RDV,(Fiche) f.getFicheByPatient(9), UC.getUser(Id_Docteur), UC.getUser(9), Date.valueOf(picker.getValue()), TfLocal.getText());
         rdv.modifierRendezVous(r);
        }
    
    }

    void setTextField(int ID,int Id_R ,String name) {
        
        Id_RDV=Id_R;
        Id_Docteur = ID;
        Doc_Name.setText(name);

    }

    void setUpdate(boolean b) {
      this.update=b;
     
    }

}
