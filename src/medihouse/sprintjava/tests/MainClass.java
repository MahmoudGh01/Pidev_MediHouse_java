/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse.sprintjava.tests;


import java.sql.Date;
import medihouse.sprintjava.entities.RendezVous;
import medihouse.sprintjava.services.RendezVousCRUD;

/**
 *
 * @author user
 */
public class MainClass {
    public static void main(String[] args) {
     //   MyConnection mc = new MyConnection();
     


    
        RendezVous p;
        Date aujourdhui = null;
        //p = new RendezVous("Dr Mahmoud", "Mr Amine",aujourdhui,"Ariana");
        RendezVousCRUD pcd = new RendezVousCRUD();
        //pcd.ajouterRendezVous(p);
      
        System.out.println(pcd.listerRendezVous());
    }
}