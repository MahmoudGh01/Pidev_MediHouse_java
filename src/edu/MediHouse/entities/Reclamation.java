/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.entities;

import java.sql.Date;

/**
 *
 * @author user
 */
public class Reclamation {
    
    private int id;
    private String email,sujet,description;
    private Date date;

    public Reclamation() {
    }

    public Reclamation(int id, String email, String sujet, String description, Date date) {
        this.id = id;
        this.email = email;
        this.sujet = sujet;
        this.description = description;
        this.date = date;
    }

    public Reclamation(String email, String sujet, String description, Date date) {
        this.email = email;
        this.sujet = sujet;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return email ;
    }
    
    
    
    
}
