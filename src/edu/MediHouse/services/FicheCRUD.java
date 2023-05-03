/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import edu.MediHouse.entities.Fiche;
import edu.MediHouse.entities.Users;
import edu.MediHouse.tools.MyConnection;


/**
 *
 * @author user
 */
public class FicheCRUD {

    public Fiche ajouterFiche(Fiche c) {
        
    ResultSet rs =null;
        
        try {
            String requete = "INSERT INTO Fiche(age,Blood_Type,patient_id)Values(?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            //rs = pst.getGeneratedKeys();
            
            
            pst.setInt(1, c.getAge());
            pst.setString(2, c.getBloodType());
            pst.setInt(3, c.getPatient().getId());

             pst.executeUpdate();
        rs = pst.getGeneratedKeys();

        if (rs.next()) {
            int Id = rs.getInt(1);
            
            c.setId(Id);
            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Fiche ajouté");
            alert.setHeaderText("La Fiche a été ajouté avec succès.");
            alert.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Afficher une alerte d'erreur si l'ajout de rendez-vous échoue
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de l'ajout de La Fiche.");
            alert.setContentText("Veuillez vérifier que toutes les entrées sont valides et réessayer.");
            alert.showAndWait();
        }
         
        

        
            
        return c;
    }

    public List<Fiche> listerFiche() {
        List<Fiche> mylist = new ArrayList();
        ServiceUser UC = new ServiceUser();
        try {

            String requete = "SELECT *FROM Fiche";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                Fiche fiche = new Fiche();
                fiche.setId(rs.getInt(1));
                fiche.setAge(rs.getInt("Age"));
                fiche.setPatient(UC.getById(rs.getInt("patient_id")));
                fiche.setBloodType(rs.getString("Blood_Type"));

                mylist.add(fiche);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }

    public void deleteFiche(Fiche C) {
        String requete = "DELETE FROM Fiche WHERE id=?";

        try {
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(requete);
            statement.setInt(1, C.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modifierFiche(Fiche c) {
        try {
            String requete = "UPDATE Fiche SET Age = ?, Blood_Type = ? WHERE id=?";

            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(3, c.getId());
            pst.setInt(1, c.getAge());
            pst.setString(2, c.getBloodType());
            pst.executeUpdate();
            System.out.println("Fiche modifiée!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   public Fiche  getFicheByPatient(int Id_Patient){
   Fiche fiche = new Fiche();
   ServiceUser UC = new ServiceUser();
   Users patient = UC.getById(Id_Patient);
   
   int a = 0;
        a = (int) ChronoUnit.YEARS.between(patient.getDatenes().toLocalDate(), LocalDate.now());
   
        try {

            String requete = "SELECT *FROM Fiche where patient_id="+Id_Patient;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                
                fiche.setId(rs.getInt(1));
                fiche.setAge(rs.getInt("Age"));
                fiche.setPatient(UC.getById(rs.getInt("patient_id")));
                fiche.setBloodType(rs.getString("Blood_Type"));

               

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        if(fiche.getId()==0){
            fiche.setPatient(patient);
            fiche.setAge(a);
        fiche=ajouterFiche(fiche);
        }
        
        
        
        return fiche;
   
   
   
   }

    public Fiche getFicheById(int ID_Fiche) {
         Fiche fiche = new Fiche();
   ServiceUser UC = new ServiceUser();
        try {

            String requete = "SELECT *FROM Fiche where id="+ID_Fiche;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                
                fiche.setId(rs.getInt(1));
                fiche.setAge(rs.getInt("Age"));
                fiche.setPatient(UC.getById(rs.getInt("patient_id")));
                fiche.setBloodType(rs.getString("Blood_Type"));

               

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return fiche;
   
   
       
    }

}
