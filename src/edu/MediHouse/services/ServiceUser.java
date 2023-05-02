/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.services;
import edu.MediHouse.entities.Role;
import edu.MediHouse.entities.Users;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import edu.MediHouse.tools.BCryptPass;
import edu.MediHouse.tools.MyConnection;
import java.sql.PreparedStatement;

/**
 *
 * @author chaab
 */
public class ServiceUser implements IServiceUser<Users>{
    Connection cnx;
    public ServiceUser(){
        cnx=MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Users u) {
        
        try {
            String requete = "INSERT into user(email,roles,password,nom,prenom,genre,telephone,adresse,profilepicture,date_naissance) values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
           pst.setString(1,u.getEmail());
           pst.setString(2,u.getRoles().toString()); 
           pst.setString(3,BCryptPass.cryptMDP(u.getPassword()));
           pst.setString(4,u.getNom());
           pst.setString(5,u.getPrenom());
           pst.setString(6,u.getGenre());
           pst.setString(7,u.getTelephone());
           pst.setString(8,u.getAdresse());
           pst.setString(9,u.getProfilePicture());   
           pst.setDate(10,u.getDatenes());

           
          
           pst.executeUpdate();
           System.out.println("User ajouteé avec succées !");
          
                    
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(int id, Users t) {
        
        try {
            String query="UPDATE `user` SET "
                    + "`email`='"+t.getEmail()+"',"
                    + "`password`='"+BCryptPass.cryptMDP(t.getPassword())+"',"
                    + "`nom`='"+t.getNom()+"',"
                    + "`prenom`='"+t.getPrenom()+"',"                    
                    + "`telephone`='"+t.getTelephone()+"',"
                    + "`adresse`='"+t.getAdresse()+"',"   
                    + "`profilepicture`='"+t.getProfilePicture()+"'WHERE id="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    

    @Override
    public void supprimer(int id) {
        try {
            String query="DELETE FROM `user` WHERE id="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Users> afficher() {
        
        List<Users>myList= new ArrayList();
        
        try {
            String requete = "SELECT * FROM user";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()){
                Users c = new Users();
                c.setId(rs.getInt("id"));
                c.setEmail(rs.getString("email"));                
               String roles = rs.getString("roles");
            if (roles != null) {
                if (roles.equals("ROLE_DOCTOR")) {
                    c.setRoles(Role.ROLE_DOCTOR);
                } else if (roles.equals("ROLE_PATIENT")) {
                    c.setRoles(Role.ROLE_PATIENT);
                } else if (roles.equals("ROLE_PARA")) {
                    c.setRoles(Role.ROLE_PARA);
                } else if (roles.equals("ROLE_ADMIN")) {
                    c.setRoles(Role.ROLE_ADMIN);
                }
            }
            
                c.setPassword(rs.getString("password"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));               
                c.setGenre(rs.getString("genre"));
                c.setTelephone(rs.getString("telephone"));
                c.setAdresse(rs.getString("adresse"));
                c.setProfilePicture(rs.getString("profilepicture"));                
                c.setDatenes(rs.getDate("date_naissance"));

              
                myList.add(c); 
            }
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList ;
        
        
    }
   

    /*private List<Users> listeUtilisateurs = new ArrayList<>(); // Déclaration d'une liste en mémoire pour stocker les utilisateurs
private List<Users> listeUtilisateurs;
     public List<Users> rechercher(String searchTerm) {
        List<Users> resultList = new ArrayList<>(); // Liste pour stocker les résultats de recherche
        // Effectuer la recherche dans votre source de données (par exemple, base de données) en fonction du terme de recherche
        // Ajouter les utilisateurs correspondants dans la liste des résultats
        // Par exemple, supposons que vous avez une liste d'utilisateurs appelée 'listeUtilisateurs' comme source de données
        for (Users user : listeUtilisateurs) {
            if (user.getNom().toLowerCase().contains(searchTerm.toLowerCase()) // Recherche par nom
                    || user.getPrenom().toLowerCase().contains(searchTerm.toLowerCase()) // Recherche par prénom
                    || user.getEmail().toLowerCase().contains(searchTerm.toLowerCase()) // Recherche par email
                    || user.getAdresse().toLowerCase().contains(searchTerm.toLowerCase()) // Recherche par adresse
                    || user.getTelephone().toLowerCase().contains(searchTerm.toLowerCase()) // Recherche par téléphone
                    || user.getGenre().toLowerCase().contains(searchTerm.toLowerCase()) // Recherche par genre
                    || user.getRoles().toString().toLowerCase().contains(searchTerm.toLowerCase())) { // Recherche par rôle
                resultList.add(user); // Ajouter l'utilisateur dans la liste des résultats
            }
        }
        return resultList; // Retourner la liste des résultats de recherche
    }*/
    
    public List<Users> afficherParRole(Role roles) {
        
        List<Users> myList= new ArrayList();
        
        try {
            String requete = "SELECT * FROM user WHERE roles = roles";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Users c = new Users();
                c.setId(rs.getInt("id"));
                c.setEmail(rs.getString("email"));                
                c.setRoles(Role.valueOf1(rs.getString("roles")));
                c.setPassword(rs.getString("password"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));               
                c.setGenre(rs.getString("genre"));
                c.setTelephone(rs.getString("telephone"));
                c.setAdresse(rs.getString("adresse"));
                c.setProfilePicture(rs.getString("profilepicture"));
                myList.add(c); 
            }
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList ;
        
    }
    
    public Users getById(int id){
        Users c=new Users();
        try {
            String query="SELECT * FROM `user` where id="+id;
            Statement st=cnx.createStatement();
            ResultSet rs=st.executeQuery(query);
            if(rs.next()){
                
                c.setId(rs.getInt("id"));
                c.setEmail(rs.getString("email"));                
                c.setRoles(Role.valueOf(rs.getString("role")));
                c.setPassword(rs.getString("password"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));               
                c.setGenre(rs.getString("genre"));
                c.setTelephone(rs.getString("telephone"));
                c.setAdresse(rs.getString("adresse"));
                c.setProfilePicture(rs.getString("profilepicture"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    
    
    public Users login(String email,String mdp){
        return afficher().stream().filter(
                u->u.getEmail().equals(email)
        ).filter(u->BCryptPass.checkPass(mdp, u.getPassword())).findFirst().orElse(null);
        
    }
    public boolean checkuser(String email){
        return afficher().stream().filter(
                u->u.getEmail().equals(email)).findFirst().isPresent();
    }
    
    
    public Users getUserByEmail(String email){
        return afficher().stream().filter(
                u->u.getEmail().equals(email)).findFirst().orElse(null);
    }
  
    
    public List<Users> triUserparNom(){
        return afficher().stream().sorted((u1,u2)->u1.getNom().compareTo(u2.getNom())).collect(Collectors.toList());
    }
}
