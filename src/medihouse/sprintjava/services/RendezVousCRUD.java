/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse.sprintjava.services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import medihouse.sprintjava.entities.RendezVous;
import medihouse.sprintjava.tools.MyConnection;

/**
 *
 * @author user
 */
public class RendezVousCRUD {

    public void ajouterRendezVous(RendezVous c) {
        try {
            String requete = "INSERT INTO rendezvous(docteur,patient,date,local,fiche)Values(?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);

            pst.setInt(1, c.getDocteur().getId());
            pst.setInt(2, c.getPatient().getId());

            pst.setDate(3, (Date) c.getDate_RDV());
            pst.setString(4, c.getLocal());
            pst.setInt(5, c.getFiche().getId());
            pst.executeUpdate();
            System.out.println("elemenT AJOUTEEEEE");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Rendez-vous ajouté");
            alert.setHeaderText("Le rendez-vous a été ajouté avec succès.");
            alert.showAndWait();

        } catch ( SQLException ex) {
            //System.out.println(ex.getMessage());
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
        UserCRUD uc = new UserCRUD();
        FicheCRUD F = new FicheCRUD();
        try {

            String requete = "SELECT * FROM rendezvous";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                RendezVous rdv = new RendezVous();

                rdv.setId(rs.getInt(1));
                rdv.setDocteur(uc.getUser(rs.getInt("docteur")));
                rdv.setPatient(uc.getUser(rs.getInt("patient")));
                rdv.setDate_RDV(rs.getDate("date"));
                rdv.setLocal(rs.getString("local"));
                rdv.setFiche(F.getFicheById(rs.getInt("fiche")));

                mylist.add(rdv);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }

    public void deleteRendezVous(RendezVous C) {
        String requete = "DELETE FROM rendezvous WHERE id=?";

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
            String requete = "UPDATE rendezvous SET patient = ?, docteur = ?, date = ?,local= ? WHERE id=?";

            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(5, c.getId());
            pst.setInt(2, c.getDocteur().getId());
            pst.setInt(1, c.getPatient().getId());
            pst.setDate(3, (Date) c.getDate_RDV());
            pst.setString(4, c.getLocal());
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
        RendezVous x=new RendezVous();
        UserCRUD uc = new UserCRUD();
        
        
    try {
        String query = "SELECT * FROM user WHERE id = ?";
        PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
        statement.setInt(1, Id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            x = new RendezVous(resultSet.getInt("id"), uc.getUser(resultSet.getInt("Docteur")) , uc.getUser(resultSet.getInt("patient")),resultSet.getDate("date"),resultSet.getString("Local"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
        return x;
    }
     public List<RendezVous> listerRendezVousByFiche(int Id_Fiche) {
         
        List<RendezVous> mylist = new ArrayList();
        UserCRUD uc = new UserCRUD();
        FicheCRUD F = new FicheCRUD();
        try {

            String requete = "SELECT * FROM rendezvous where fiche ="+Id_Fiche;
            System.out.print(requete);
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                RendezVous rdv = new RendezVous();

                rdv.setId(rs.getInt(1));
                rdv.setDocteur(uc.getUser(rs.getInt("docteur")));
                rdv.setPatient(uc.getUser(rs.getInt("patient")));
                rdv.setDate_RDV(rs.getDate("date"));
                rdv.setLocal(rs.getString("local"));
                rdv.setFiche(F.getFicheById(Id_Fiche));

                mylist.add(rdv);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }
}
