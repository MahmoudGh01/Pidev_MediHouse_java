/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.entities;

import java.sql.Date;

/**
 *
 * @author sabri
 */
public class Commande {
    private int id ;
    private Date datecommande ;          
    private double prix ;
    private int qtcommande ;
    private String user ;
    
            
    
    public Commande() {
    }

    public Commande(int id, Date datecommande, double prix, int qtcommande, String user) {
        this.id = id;
        this.datecommande = datecommande;
        this.prix = prix;
        this.qtcommande = qtcommande;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatecommande() {
        return datecommande;
    }

    public void setDatecommande(Date datecommande) {
        this.datecommande = datecommande;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQtcommande() {
        return qtcommande;
    }

    public void setQtcommande(int qtcommande) {
        this.qtcommande = qtcommande;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", datecommande=" + datecommande + ", prix=" + prix + ", qtcommande=" + qtcommande + ", user=" + user + '}';
    }
    
    
}
