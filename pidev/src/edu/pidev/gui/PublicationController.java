/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pidev.gui;

import edu.pidev.entities.question;
import edu.pidev.services.questionCRUD;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class PublicationController implements Initializable {

    @FXML
    private TextArea ques;
    private int id_ques;

    PublicationController(String parameter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
     public void ques(String message) {
        this.ques.setText(message);
    }

    questionCRUD qc = new questionCRUD();

    public void setId_ques(int id_ques) {
        this.id_ques = id_ques;
    }
    
    @FXML
    private Button repondre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   }

    @FXML
    private void repondre(ActionEvent event) {
    }
    
    public void display(int msg){
        List<question> questions = qc.display();   
      for (question q : questions) {
      
      if (id_ques==q.getId()){
          ques("zzzzzz");
          
          
      }
        
    }  
        
        
        
    }
    
    
}
