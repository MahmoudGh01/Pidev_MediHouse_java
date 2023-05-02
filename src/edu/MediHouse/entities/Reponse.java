/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.entities;

/**
 *
 * @author user
 */
public class Reponse {
    private int id;
    private String reponse;
    private Reclamation reclamation;

    public Reponse() {
    }

    public Reponse(int id, String reponse, Reclamation reclamation) {
        this.id = id;
        this.reponse = reponse;
        this.reclamation = reclamation;
    }

    public Reponse(String reponse, Reclamation reclamation) {
        this.reponse = reponse;
        this.reclamation = reclamation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Reponse{" + "id=" + id + ", reponse=" + reponse + ", reclamation=" + reclamation + '}';
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }
    
    
}
