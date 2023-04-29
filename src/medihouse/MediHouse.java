/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse;

import com.twilio.rest.chat.v1.service.User;
import edu.MediHouse.entities.Role;
import edu.MediHouse.entities.Users;
import edu.MediHouse.services.ServiceUser;
import java.io.IOException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author chaab
 */
public class MediHouse {
     
    
        
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServiceUser su=new ServiceUser();
       //Users u =new Users("aaa","achref","chaabaniachref21ss2@gmail.com","1236547",Role.ROLE_PATIENT,"img1","ariana","55676837","homme");
        //su.ajouter(u);
        //System.out.println(su.login("chaabaniachref21ss2@gmail.com", "1236547"));
        //su.modifier(47, u);
      // System.out.println(su.afficherParRole(Role.ROLE_ADMIN));
       //su.getUserByEmail("chaabaniachref21ss2@gmail.com");
       System.out.println(su.afficher());
      // su.supprimer(32);
    }
   
  
    
}
