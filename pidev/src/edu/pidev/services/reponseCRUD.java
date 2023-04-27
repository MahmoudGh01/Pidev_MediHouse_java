/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pidev.services;

import edu.pidev.entities.reponse;
import edu.pidev.tools.MyConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class reponseCRUD {
     public void addEntity2(reponse t) {
        try {
            String requete="INSERT INTO reponse (rep_contenu,	rep_date_pub,likes,dislikes,quest_id)"
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1, t.getRep_contenu());
            pst.setDate(2, t.getRep_date_pub());
            pst.setInt(3, t.getLikes());
            pst.setInt(4, t.getDislikes());
            pst.setInt(5, t.getQuest_id());
            pst.executeUpdate();
            System.out.println("Success!");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
                                    
    }
      public void deleteReponse(String partialString) {
    try {
        String requete = "DELETE FROM reponse WHERE rep_contenu LIKE ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, "%" + partialString + "%");
        pst.executeUpdate();
        System.out.println("Row(s) deleted successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

      public void editLIKE(int id, Integer like) {
    try {
        String requete = "UPDATE reponse SET likes = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, like);
        pst.setInt(2, id);
        pst.executeUpdate();
        System.out.println("Question updated successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
public void editDISLIKE(int id, Integer like) {
    try {
        String requete = "UPDATE reponse SET dislikes = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, like);
        pst.setInt(2, id);
        pst.executeUpdate();
        System.out.println("Question updated successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void deleteReponse2(String m) {
    try {
        String requete = "DELETE FROM reponse WHERE id = ? OR rep_contenu = ? OR rep_date_pub = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);

        try {
            int id = Integer.parseInt(m);
            pst.setInt(1, id);
            pst.setString(2, null);
            pst.setDate(3, null);
        } catch (NumberFormatException e) {
            pst.setInt(1, 0);
            pst.setString(2, m);

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date rep_date_pub = new Date(dateFormat.parse(m).getTime());
                pst.setDate(3, rep_date_pub);
            } catch (ParseException pe) {
                pst.setDate(3, null);
                System.out.println("Invalid value, nothing deleted.");
                return;
            }
        }

        pst.executeUpdate();
        System.out.println("Row deleted successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public int quest_id(int questionId) {
    int hidden = 0;
    try {
        String requete = "SELECT id FROM question WHERE quest_id= " + questionId;
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        if (rs.next()) {
            hidden = rs.getInt("id");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return hidden;
}
public List<reponse> getResponsesByQuestId(int quest_id) {
    List<reponse> responseList = new ArrayList<>();
    
    try {
        String query = "SELECT * FROM reponse WHERE quest_id = ?";
        PreparedStatement stmt = MyConnection.getInstance().getCnx().prepareStatement(query);
        stmt.setInt(1, quest_id);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            reponse r = new reponse();
            r.setId(rs.getInt("id"));
            r.setRep_contenu(rs.getString("rep_contenu"));
            r.setRep_date_pub(rs.getDate("rep_date_pub"));
            r.setLikes(rs.getInt("likes"));
            r.setDislikes(rs.getInt("dislikes"));
            r.setQuest_id(rs.getInt("quest_id"));
            responseList.add(r);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    
    return responseList;
}


public void editReponse(int id, String newContent) {
    try {
        String requete = "UPDATE reponse SET ques_contenu = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, newContent);
        pst.setInt(2, id);
        pst.executeUpdate();
        System.out.println("reponse updated successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    
    public List<reponse> display() {
        List<reponse> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM reponse";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                reponse p = new reponse();
                p.setId(rs.getInt(1));
                p.setRep_contenu(rs.getString("rep_contenu"));
                p.setRep_date_pub(rs.getDate("rep_date_pub"));
                p.setLikes(rs.getInt("likes"));
                p.setDislikes(rs.getInt("dislikes"));
                p.setQuest_id(rs.getInt("quest_id"));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
    
}
