/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pidev.services;
import edu.pidev.entities.question;
import edu.pidev.interfaces.EntityCRUD;
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
public class questionCRUD {
     public void addEntity2(question t) {
        try {
            String requete="INSERT INTO question (ques_contenu,ques_date_pub,categorie,likes,dislikes,hide_name)"
                    + "VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1, t.getQues_contenu());
            pst.setDate(2, t.getQues_date_pub());
            pst.setString(3, t.getCategorie());
            pst.setInt(4, t.getLikes());
            pst.setInt(5, t.getDislikes());
            pst.setInt(6, t.getHide_name());
            pst.executeUpdate();
            System.out.println("Success!");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
                                    
    }

    
      public void deleteQuestion(String partialString) {
    try {
        String requete = "DELETE FROM question WHERE ques_contenu LIKE ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, "%" + partialString + "%");
        pst.executeUpdate();
        System.out.println("Row(s) deleted successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
     


public void deletequestion2(String m) {
    try {
        String requete = "DELETE FROM question WHERE id = ? OR ques_contenu = ? OR ques_date_pub = ?";
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
        System.out.println("Row(s) deleted successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
public void editQuestion(int id, String newContent) {
    try {
        String requete = "UPDATE question SET ques_contenu = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, newContent);
        pst.setInt(2, id);
        pst.executeUpdate();
        System.out.println("Question updated successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void editLIKE(int id, Integer like) {
    try {
        String requete = "UPDATE question SET likes = ? WHERE id = ?";
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
        String requete = "UPDATE question SET dislikes = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, like);
        pst.setInt(2, id);
        pst.executeUpdate();
        System.out.println("Question updated successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
public void edit_hide_name(int id, Integer like) {
    try {
        String requete = "UPDATE question SET hide_name = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, like);
        pst.setInt(2, id);
        pst.executeUpdate();
        System.out.println("Question updated successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public int hidden(int questionId) {
    int hidden = 0;
    try {
        String requete = "SELECT hide_name FROM question WHERE id = " + questionId;
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        if (rs.next()) {
            hidden = rs.getInt("hide_name");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return hidden;
}

public int getQuestionIdByContent(String ques_content) {
    int questionId = -1; // Initialize to a default value in case no question is found
    
    try {
        String query = "SELECT id FROM question WHERE ques_contenu = ?";
        PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
        statement.setString(1, ques_content);
        ResultSet result = statement.executeQuery();
        
        if (result.next()) {
            questionId = result.getInt("id");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    
    return questionId;
}




    public int countQuestions() {
    int count = 0;
    try {
        String requete = "SELECT COUNT(*) FROM question";
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return count;
}

    public List<question> filter(String filterString) {
    List<question> myList = new ArrayList<>();
    try {
        String requete = "SELECT * FROM question WHERE ques_contenu LIKE '%" + filterString + "%'";
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        while(rs.next()){
            question p = new question();
            p.setId(rs.getInt(1));
            p.setQues_contenu(rs.getString("ques_contenu"));
            p.setQues_date_pub(rs.getDate("ques_date_pub"));
            p.setCategorie(rs.getString("categorie"));
            p.setLikes(rs.getInt("likes"));
            p.setDislikes(rs.getInt("dislikes"));
            myList.add(p);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return myList;
}

    public List<question> display() {
        List<question> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM question";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                question p = new question();
                p.setId(rs.getInt(1));
                p.setQues_contenu(rs.getString("ques_contenu"));
                p.setQues_date_pub(rs.getDate("ques_date_pub"));
                p.setCategorie(rs.getString("categorie"));
                p.setLikes(rs.getInt("likes"));
                p.setDislikes(rs.getInt("dislikes"));

                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }









}
