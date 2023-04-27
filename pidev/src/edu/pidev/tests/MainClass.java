/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pidev.tests;

import edu.pidev.entities.question;
import edu.pidev.entities.reponse;
import edu.pidev.gui.adminGUI;
import edu.pidev.gui.forumGUI;
import edu.pidev.services.questionCRUD;
import edu.pidev.services.reponseCRUD;
import java.sql.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author DELL
 */
public class MainClass {
      public static void main(String[] args) {
     //   MyConnection mc = new MyConnection();
     Date date = new Date(System.currentTimeMillis()); 
    //  reponse r = new reponse();
  
    reponseCRUD rc = new reponseCRUD();
//   rc.addEntity2(r);
   //rc.deleteReponse("a");
    System.out.println(    rc.getResponsesByQuestId(0));
   // question q= new question("oooooo9", date);
    questionCRUD qc = new questionCRUD();
          System.out.println( qc.getQuestionIdByContent("ssss"));
    // System.out.println(qc.countQuestions());
  //qc.editQuestion(5, "d");
      // System.out.println(qc.display());
       
    //qc.deleteQuestion("9");


   // Application.launch(adminGUI.class, args);
     //Application.launch(forumGUI.class, args);
}


}