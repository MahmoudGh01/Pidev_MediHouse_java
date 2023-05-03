/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.mail.MessagingException;
import edu.MediHouse.entities.Fiche;
import edu.MediHouse.entities.RendezVous;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.FicheCRUD;
import edu.MediHouse.services.RendezVousCRUD;
import edu.MediHouse.services.ServiceUser;
import edu.MediHouse.tools.MailAPI;


/**
 * FXML Controller class
 *
 * @author user
 */
public class Pick_dateController implements Initializable {
    
    ServiceUser su =new ServiceUser();
    Users u =new Users();
    @FXML
    private Button btnAdd;
    @FXML
    private DatePicker picker;
 
    @FXML
    private Label Doc_Name;
    int Id_Docteur,Id_RDV;
    //RendezVousCRUD RD =new RendezVousCRUD();
    ServiceUser UC = new ServiceUser();
    private boolean update;
    @FXML
    private ChoiceBox<String> mm;
    @FXML
    private ChoiceBox<String> hh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         u=su.getUserByEmail(InterfaceLogineeController.iduserglobal);
       mm.getItems().addAll("00","15","30","45");
       hh.getItems().addAll("08","09","10","12","13","14","15","16","17");
    }

    @FXML
    private void btnAdd(ActionEvent event) throws MessagingException {
        if (picker.getValue() == null ) {
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
        
        System.out.print(Time.valueOf(hh.getValue()+":"+mm.getValue()+":00"));
        
        FicheCRUD f = new FicheCRUD();

        RendezVousCRUD rdv = new RendezVousCRUD();
        RendezVous r;

        
        if (!update){
            r = new RendezVous((Fiche) f.getFicheByPatient(u.getId()), UC.getById(Id_Docteur), UC.getUserByEmail(u.getEmail()), Date.valueOf(picker.getValue()), UC.getById(Id_Docteur).getAdresse() ,Time.valueOf(hh.getValue()+":"+mm.getValue()+":00"));
        rdv.ajouterRendezVous(r);
        MailAPI M =new MailAPI();
       // M.sendMail("gharbi714@gmail.com","hh", "test",r);
    }else{
          
            
         r = new RendezVous(Id_RDV,(Fiche) f.getFicheByPatient(u.getId()), UC.getById(Id_Docteur),  UC.getUserByEmail(u.getEmail()), Date.valueOf(picker.getValue()), UC.getById(Id_Docteur).getAdresse());
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
