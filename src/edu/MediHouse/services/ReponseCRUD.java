/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.services;

import edu.MediHouse.entities.Reponse;
import edu.MediHouse.tools.MyConnection;
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
public class ReponseCRUD {
    
    
    ReclamationCRUD RC =new ReclamationCRUD();
         public void ajouterReponse(Reponse c) {
        try {
            String requete = "INSERT INTO reponse(reponse1_des,reclamation_id)Values(?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);

            pst.setString(1,c.getReponse());
           
            pst.setInt(2,c.getReclamation().getId());
           
            pst.executeUpdate();
            System.out.println("elemenT AJOUTEEEEE");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reponse ajouté");
            alert.setHeaderText("La Reponse a été ajouté avec succès.");
            alert.showAndWait();

        } catch ( SQLException ex) {
            //System.out.println(ex.getMessage());
            // Afficher une alerte d'erreur si l'ajout de rendez-vous échoue
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de l'ajout de la Reponse.");
            alert.setContentText("Veuillez vérifier que toutes les entrées sont valides et réessayer.");
            alert.showAndWait();
        }
    }

    public List<Reponse> listerReponse() {
        List<Reponse> mylist = new ArrayList();
      
        try {

            String requete = "SELECT * FROM reponse";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                Reponse rdv = new Reponse();

                rdv.setId(rs.getInt(1));
                rdv.setReponse(rs.getString("reponse1_des"));
               
                rdv.setReclamation(RC.getReclamation(rs.getInt("reclamation_id")));
                

                mylist.add(rdv);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }

    public void deleteReponse(Reponse C) {
        String requete = "DELETE FROM reponse WHERE id=?";

        try {
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(requete);
            statement.setInt(1, C.getId());
            statement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reponse supprimé");
            alert.setHeaderText("Le Reponse a été supprimé avec succès.");
            alert.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Afficher une alerte d'erreur si l'ajout de rendez-vous échoue
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de la suppression de la Reponse.");
            alert.setContentText("Veuillez vérifier que toutes les entrées sont valides et réessayer.");
            alert.showAndWait();
        }
    }

    public void modifierReponse(Reponse c) {
        try {
            String requete = "UPDATE reponse SET reponse1_des=? , reclamation_id=? WHERE id=?";

            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(3, c.getId());
            pst.setString(1,c.getReponse());
            pst.setInt(2,c.getReclamation().getId());
            pst.executeUpdate();
            System.out.println("Reponse modifiée!");
            
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reponse ajouté");
            alert.setHeaderText("Le Reponse a été modifié avec succès.");
            alert.showAndWait();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Afficher une alerte d'erreur si l'ajout de rendez-vous échoue
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de la modification de la Reponse.");
            alert.setContentText("Veuillez vérifier que toutes les entrées sont valides et réessayer.");
            alert.showAndWait();
        }
    }
    
    public Reponse getReponse(int Id) {
        Reponse x=new Reponse();
      
        
        
    try {
        String query = "SELECT * FROM reponse WHERE id = ?";
        PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
        statement.setInt(1, Id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            x = new Reponse(resultSet.getInt("id"), resultSet.getString("reponse1_des") ,RC.getReclamation(resultSet.getInt("reclamation_id")));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
        return x;
    }
    
}
