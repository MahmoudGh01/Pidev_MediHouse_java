/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.services;

import edu.MediHouse.entities.Reclamation;
import edu.MediHouse.tools.MyConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author user
 */
public class ReclamationCRUD {
     public void ajouterReclamation(Reclamation c) {
        try {
            String requete = "INSERT INTO reclamation(email,sujet,descreption,date_reclamation)Values(?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);

            pst.setString(1,c.getEmail());
            pst.setString(2, c.getSujet());

            pst.setString(3, c.getDescription());
            pst.setDate(4, (Date)c.getDate());
           
            pst.executeUpdate();
            System.out.println("elemenT AJOUTEEEEE");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reclamation ajouté");
            alert.setHeaderText("La Reclamation a été ajouté avec succès.");
            alert.showAndWait();

        } catch ( SQLException ex) {
            //System.out.println(ex.getMessage());
            // Afficher une alerte d'erreur si l'ajout de rendez-vous échoue
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de l'ajout de la Reclamation.");
            alert.setContentText("Veuillez vérifier que toutes les entrées sont valides et réessayer.");
            alert.showAndWait();
        }
    }

    public List<Reclamation> listerReclamation() {
        List<Reclamation> mylist = new ArrayList();
      
        try {

            String requete = "SELECT * FROM reclamation";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                Reclamation rdv = new Reclamation();

                rdv.setId(rs.getInt(1));
                rdv.setEmail(rs.getString("email"));
                rdv.setSujet(rs.getString("sujet"));
                rdv.setDescription(rs.getString("descreption"));
                rdv.setDate(rs.getDate("date_reclamation"));
                

                mylist.add(rdv);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }

    public void deleteReclamation(Reclamation C) {
        String requete = "DELETE FROM reclamation WHERE id=?";

        try {
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(requete);
            statement.setInt(1, C.getId());
            statement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reclamation ajouté");
            alert.setHeaderText("Le Reclamation a été supprimé avec succès.");
            alert.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Afficher une alerte d'erreur si l'ajout de rendez-vous échoue
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de la suppression de la reclamation.");
            alert.setContentText("Veuillez vérifier que toutes les entrées sont valides et réessayer.");
            alert.showAndWait();
        }
    }

    public void modifierReclamation(Reclamation c) {
        try {
            String requete = "UPDATE reclamation SET email = ?, sujet = ?, descreption = ?,date_reclamation= ? WHERE id=?";

            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(5, c.getId());
            pst.setString(1,c.getEmail());
            pst.setString(2, c.getSujet());

            pst.setString(3, c.getDescription());
            pst.setDate(4,c.getDate());
            pst.executeUpdate();
            System.out.println("Reclamation modifiée!");
            
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reclamation ajouté");
            alert.setHeaderText("La Reclamation a été modifié avec succès.");
            alert.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Afficher une alerte d'erreur si l'ajout de rendez-vous échoue
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de la modification de la Reclamation.");
            alert.setContentText("Veuillez vérifier que toutes les entrées sont valides et réessayer.");
            alert.showAndWait();
        }
    }
    
    public Reclamation getReclamation(int Id) {
        Reclamation x=new Reclamation();
      
        
        
    try {
        String query = "SELECT * FROM reclamation WHERE id = ?";
        PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
        statement.setInt(1, Id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            x = new Reclamation(resultSet.getInt("id"), resultSet.getString("email") , resultSet.getString("descreption"), resultSet.getString("sujet"),resultSet.getDate("date_reclamation"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
        return x;
    }
}
