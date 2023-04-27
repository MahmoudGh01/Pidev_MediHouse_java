/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse.sprintjava.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import medihouse.sprintjava.entities.User;
import medihouse.sprintjava.tools.MyConnection;

/**
 *
 * @author user
 */
public class UserCRUD {

    public void ajouterUser(User c) {
        try {
            String requete = "INSERT INTO user(nom,email)Values(?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);

            pst.setString(1, c.getNom());
            pst.setString(2, c.getEmail());

            pst.executeUpdate();
            System.out.println("elemenT AJOUTEEEEE");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<User> listerUser() {
        List<User> mylist = new ArrayList();
        try {

            String requete = "SELECT *FROM user";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                User rdv = new User();

                rdv.setId(rs.getInt(1));
                rdv.setNom(rs.getString("nom"));
                rdv.setEmail(rs.getString("email"));

                mylist.add(rdv);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }

    public void deleteUser(User C) {
        String requete = "DELETE FROM user WHERE id=?";

        try {
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(requete);
            statement.setInt(1, C.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modifierUser(User c) {
        try {
            String requete = "UPDATE user SET nom = ?, email = ? WHERE id=?";

            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(3, c.getId());
            pst.setString(1, c.getNom());
            pst.setString(2, c.getEmail());
            pst.executeUpdate();
            System.out.println("user modifiée!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public User getUser(int Id) {
        User user = new User();
        try {
            String query = "SELECT * FROM user WHERE id = ?";
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
            statement.setInt(1, Id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("email"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return user;
    }

    public List<User> getUserByRole(String role) {
        List<User> mylist = new ArrayList();
        try {

            String requete = "SELECT * FROM user where role = '"+ role+"'";
            System.out.print(requete);
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                User rdv = new User();

                rdv.setId(rs.getInt(1));
                rdv.setNom(rs.getString("nom"));
                rdv.setEmail(rs.getString("email"));
                rdv.setRole(rs.getString("role"));

                mylist.add(rdv);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }

    public List<User> getUsersByName(String text) {
        List<User> mylist = new ArrayList();
        try {

            String requete = "SELECT *FROM user where id= %" + text + "%";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                User rdv = new User();

                rdv.setId(rs.getInt(1));
                rdv.setNom(rs.getString("nom"));
                rdv.setEmail(rs.getString("email"));

                mylist.add(rdv);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }

}
