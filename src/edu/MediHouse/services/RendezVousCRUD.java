/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import edu.MediHouse.entities.RendezVous;
import edu.MediHouse.entities.RendezVous;
import edu.MediHouse.entities.Users;
import edu.MediHouse.tools.MyConnection;

/**
 *
 * @author user
 */
public class RendezVousCRUD {

    ServiceUser uc = new ServiceUser();
    FicheCRUD F = new FicheCRUD();
    RendezVous x = new RendezVous();

    public void ajouterRendezVous(RendezVous c) {
        try {
            String requete = "INSERT INTO rendez_vous(docteur_id,patient_id,date,local,fiche_id,time,statut)Values(?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);

            pst.setInt(1, c.getDocteur().getId());
            pst.setInt(2, c.getPatient().getId());

            pst.setDate(3, (Date) c.getDate_RDV());
            pst.setString(4, c.getLocal());
            pst.setInt(5, c.getFiche().getId());
            pst.setTime(6, c.getTime());
            pst.setString(7, "Confirmé");
            pst.executeUpdate();
            System.out.println("elemenT AJOUTEEEEE");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Rendez-vous ajouté");
            alert.setHeaderText("Le rendez-vous a été ajouté avec succès.");
            alert.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Afficher une alerte d'erreur si l'ajout de rendez-vous échoue
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de l'ajout du rendez-vous.");
            alert.setContentText("Veuillez vérifier que toutes les entrées sont valides et réessayer.");
            alert.showAndWait();
        }
    }

    public List<RendezVous> listerRendezVous() {
        List<RendezVous> mylist = new ArrayList();
        ServiceUser uc = new ServiceUser();
        FicheCRUD F = new FicheCRUD();
        try {

            String requete = "SELECT * FROM rendez_vous";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                RendezVous rdv = new RendezVous();

                rdv.setId(rs.getInt(1));
                rdv.setDocteur(uc.getById(rs.getInt("docteur_id")));
                rdv.setPatient(uc.getById(rs.getInt("patient_id")));
                rdv.setDate_RDV(rs.getDate("date"));
                rdv.setLocal(rs.getString("local"));
                rdv.setFiche(F.getFicheById(rs.getInt("fiche_id")));
                rdv.setTime(rs.getTime("time"));

                mylist.add(rdv);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }

    public void deleteRendezVous(RendezVous C) {
        String requete = "DELETE FROM rendez_vous WHERE id=?";

        try {
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(requete);
            statement.setInt(1, C.getId());
            statement.executeUpdate();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Rendez-vous ajouté");
            alert.setHeaderText("Le rendez-vous a été supprimé avec succès.");
            alert.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Afficher une alerte d'erreur si l'ajout de rendez-vous échoue
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de la suppression du rendez-vous.");
            alert.setContentText("Veuillez vérifier que toutes les entrées sont valides et réessayer.");
            alert.showAndWait();
        }
    }

    public void modifierRendezVous(RendezVous c) {
        try {
            String requete = "UPDATE rendez_vous SET patient_id = ?, docteur_id = ?, date = ?,local= ?,time=? WHERE id=?";

            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(6, c.getId());
            pst.setInt(2, c.getDocteur().getId());
            pst.setInt(1, c.getPatient().getId());
            pst.setDate(3, (Date) c.getDate_RDV());
            pst.setString(4, c.getLocal());
            pst.setTime(5, c.getTime());
            pst.executeUpdate();
            System.out.println("rendezvous modifiée!");

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Rendez-vous ajouté");
            alert.setHeaderText("Le rendez-vous a été modifié avec succès.");
            alert.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Afficher une alerte d'erreur si l'ajout de rendez-vous échoue
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de la modification du rendez-vous.");
            alert.setContentText("Veuillez vérifier que toutes les entrées sont valides et réessayer.");
            alert.showAndWait();
        }
    }

    public RendezVous getRendezVous(int Id) {
         RendezVous rdv = new RendezVous();
        try {
            String query = "SELECT * FROM rendez_vous WHERE id = ?";
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
            statement.setInt(1, Id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
               

                rdv.setId(rs.getInt(1));
                rdv.setDocteur(uc.getById(rs.getInt("docteur_id")));
                rdv.setPatient(uc.getById(rs.getInt("patient_id")));
                rdv.setDate_RDV(rs.getDate("date"));
                rdv.setLocal(rs.getString("local"));
                rdv.setFiche(F.getFicheById(rs.getInt("fiche_id")));
                rdv.setTime(rs.getTime("time"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rdv;
    }

    public List<RendezVous> listerRendezVousByFiche(int Id_Fiche) {

        List<RendezVous> mylist = new ArrayList();
    
        try {

            String requete = "SELECT * FROM rendez_vous where fiche_id =" + Id_Fiche;
            System.out.print(requete);
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                RendezVous rdv = new RendezVous();

                rdv.setId(rs.getInt(1));
                rdv.setDocteur(uc.getById(rs.getInt("docteur_id")));
                rdv.setPatient(uc.getById(rs.getInt("patient_id")));
                rdv.setDate_RDV(rs.getDate("date"));
                rdv.setLocal(rs.getString("local"));
                rdv.setFiche(F.getFicheById(Id_Fiche));
                rdv.setTime(rs.getTime("time"));
                mylist.add(rdv);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }

    public RendezVous getLastRendezVousTime() {
        long daysRemaining = 0;
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        RendezVous r = new RendezVous();
        try {
            String query = "SELECT MIN(date),time FROM rendez_vous WHERE date > CURDATE()";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                Date nextDate = rs.getDate(1);
                Time nextTime = rs.getTime("time");
                r.setTime(nextTime);
                r.setDate_RDV(nextDate);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return r;
    }

    public Integer getNombreRendezVous(Date d) {
        int y = 0;
        try {

            String requete = "SELECT count(*) FROM rendez_vous WHERE date = ?";
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(requete);
            statement.setDate(1, d);
            //statement.executeQuery();
            ResultSet rs = statement.executeQuery();
            if (rs.next()) { // Check if ResultSet has rows
                y = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RendezVousCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return y;
    }

}
