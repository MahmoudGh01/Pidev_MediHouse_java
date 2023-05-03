/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.services;

import edu.MediHouse.entities.reponse1;
import edu.MediHouse.tools.MyConnection;
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
public class reponse1CRUD {
     public void addEntity2(reponse1 t) {
        try {
            String requete="INSERT INTO reponse (reponse,date_pub,likes,dislikes,id_question,id_user)"
                    + "VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1, t.getRep_contenu());
            pst.setDate(2, t.getRep_date_pub());
            pst.setInt(3, t.getLikes());
            pst.setInt(4, t.getDislikes());
            pst.setInt(5, t.getQuest_id());
            pst.setInt(6, t.getId_user());
            pst.executeUpdate();
            System.out.println("Success!");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
                                    
    }
      public void deleteReponse(String partialString) {
    try {
        String requete = "DELETE FROM reponse WHERE reponse LIKE ?";
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
        String requete = "DELETE FROM reponse WHERE id = ? OR reponse = ? OR date_pub = ?";
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
        String requete = "SELECT id FROM question WHERE id_question= " + questionId;
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
public List<reponse1> getResponsesByQuestId(int quest_id) {
    List<reponse1> responseList = new ArrayList<>();
    
    try {
        String query = "SELECT * FROM reponse WHERE id_question	 = ?";
        PreparedStatement stmt = MyConnection.getInstance().getCnx().prepareStatement(query);
        stmt.setInt(1, quest_id);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            reponse1 r = new reponse1();
            r.setId(rs.getInt("id"));
            r.setRep_contenu(rs.getString("reponse"));
            r.setRep_date_pub(rs.getDate("date_pub"));
            r.setLikes(rs.getInt("likes"));
            r.setDislikes(rs.getInt("dislikes"));
            r.setQuest_id(rs.getInt("id_question"));
            r.setId_user(rs.getInt("id_user"));
            responseList.add(r);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    
    return responseList;
}


public void editReponse(int id, String newContent) {
    try {
        String requete = "UPDATE reponse SET reponse = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, newContent);
        pst.setInt(2, id);
        pst.executeUpdate();
        System.out.println("reponse updated successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    
    public List<reponse1> display() {
        List<reponse1> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM reponse";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                reponse1 p = new reponse1();
                p.setId(rs.getInt(1));
                p.setRep_contenu(rs.getString("reponse"));
                p.setRep_date_pub(rs.getDate("date_pub"));
                p.setLikes(rs.getInt("likes"));
                p.setDislikes(rs.getInt("dislikes"));
                p.setQuest_id(rs.getInt("id_question"));
                p.setId_user(rs.getInt("id_user"));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
    
}
