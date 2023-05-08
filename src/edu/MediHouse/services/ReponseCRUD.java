/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.services;

import edu.MediHouse.entities.Reply;
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
         public void ajouterReponse(Reply c) {
        try {
            String requete = "INSERT INTO reponse1(reclamation_id,reponse1_des)Values(?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);

           
            pst.setInt(1,c.getReclamation().getId());
                       pst.setString(2,c.getReponse());

            pst.executeUpdate();
            System.out.println("elemenT AJOUTEEEEE");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reponse ajouté");
            alert.setHeaderText("La Reponse a été ajouté avec succès.");
            alert.showAndWait();

        } catch ( SQLException ex) {
            System.out.println(ex.getMessage());
            // Afficher une alerte d'erreur si l'ajout de rendez-vous échoue
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de l'ajout de la Reponse.");
            alert.setContentText("Veuillez vérifier que toutes les entrées sont valides et réessayer.");
            alert.showAndWait();
        }
    }

    public List<Reply> listerReponse() {
        List<Reply> mylist = new ArrayList();
      
        try {

            String requete = "SELECT * FROM reponse1";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                Reply rdv = new Reply();

                rdv.setId(rs.getInt(1));
               
               
                rdv.setReclamation(RC.getReclamation(rs.getInt("reclamation_id")));
                 rdv.setReponse(rs.getString("reponse1_des"));

                mylist.add(rdv);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }

    public void deleteReponse(Reply C) {
        String requete = "DELETE FROM reponse1 WHERE id=?";

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

    public void modifierReponse(Reply c) {
        try {
            String requete = "UPDATE reponse1 SET reclamation_id=? , reponse1_des=? WHERE id=?";

            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(3, c.getId());
            pst.setString(2,c.getReponse());
            pst.setInt(1,c.getReclamation().getId());
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
    
    public Reply getReponse(int Id) {
        Reply x=new Reply();
      
        
        
    try {
        String query = "SELECT * FROM reponse1 WHERE id = ?";
        PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
        statement.setInt(1, Id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            x = new Reply(resultSet.getInt("id"), resultSet.getString("reponse1_des") ,RC.getReclamation(resultSet.getInt("reclamation_id")));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
        return x;
    }
    
}
