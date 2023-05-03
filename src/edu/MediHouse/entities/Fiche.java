/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.entities;

import java.util.List;
import java.util.logging.Logger;


/**
 *
 * @author user
 */
public class Fiche {
    private int id,Age;
    private Users Patient;
    private String BloodType;
    List<RendezVous> rendezvouss;

    public Fiche(int Age, Users Patient, String BloodType) {
        this.Age = Age;
        this.Patient = Patient;
        this.BloodType = BloodType;
    }

    public Fiche(int id, int Age, Users Patient, String BloodType) {
        this.id = id;
        this.Age = Age;
        this.Patient = Patient;
        this.BloodType = BloodType;
    }
    


    public Fiche() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public String getBloodType() {
        return BloodType;
    }

    public void setBloodType(String BloodType) {
        this.BloodType = BloodType;
    }

    public List<RendezVous> getRendezvouss() {
        return rendezvouss;
    }

    public void setRendezvouss(List<RendezVous> rendezvouss) {
        this.rendezvouss = rendezvouss;
    }

    public Fiche(int id, int Age, Users Patient, String BloodType, List<RendezVous> rendezvouss) {
        this.id = id;
        this.Age = Age;
        this.Patient = Patient;
        this.BloodType = BloodType;
        this.rendezvouss = rendezvouss;
    }

    public Fiche(int Age, Users Patient, String BloodType, List<RendezVous> rendezvouss) {
        this.Age = Age;
        this.Patient = Patient;
        this.BloodType = BloodType;
        this.rendezvouss = rendezvouss;
    }

    public Users getPatient() {
        return Patient;
    }

    public void setPatient(Users Patient) {
        this.Patient = Patient;
    }
   
    @Override
    public String toString() {
        return  String.valueOf(id) ;
    }
    
    
}
