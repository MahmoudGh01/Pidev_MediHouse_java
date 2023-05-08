/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.entities;

import java.sql.Date;

/**
 *
 * @author chaab
 */
public class Users {
    private int id ; 
    private String nom ;
    private String prenom;
    private String email ; 
    private String password;
    private Role roles;
    private String profilePicture;
    private String adresse;  
    private String telephone;   
    private String genre;
    private Boolean act;
    private Date Datenes;

    public Date getDatenes() {
        return Datenes;
    }

    public void setDatenes(Date Datenes) {
        this.Datenes = Datenes;
    }

    
    public Users() {
    }

    public Boolean getAct() {
        return act;
    }

    public void setAct(boolean act) {
        this.act = act;
    }

    public Users(String nom, String prenom, String email, String password, Role roles, String profilePicture, String adresse, String telephone, String genre, Boolean act, Date Datenes) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.profilePicture = profilePicture;
        this.adresse = adresse;
        this.telephone = telephone;
        this.genre = genre;
        this.act = act;
        this.Datenes = Datenes;
    }

    public Users( String nom, String prenom, String email, String password, Role roles, String profilePicture, String adresse, String telephone, String genre) {
        
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.profilePicture = profilePicture;
        this.adresse = adresse;
         this.telephone = telephone;
          this.genre = genre;
           
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

   

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        return nom + prenom ;
    }

   
 

   
  
}
