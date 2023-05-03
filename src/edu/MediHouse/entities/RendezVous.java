/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.entities;

import java.sql.Date;
import java.sql.Time;


/**
 *
 * @author user
 */
public class RendezVous {

    private int id;
    private Fiche fiche;
    private Users Docteur;
    private Users Patient;

    private Date Date_RDV;
    private String Local;
    private Time time;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public RendezVous(Date Date_RDV, Time time) {
        this.Date_RDV = Date_RDV;
        this.time = time;
    }

    public RendezVous(int id, Date Date_RDV, Time time) {
        this.id = id;
        this.Date_RDV = Date_RDV;
        this.time = time;
    }

    public RendezVous(Fiche fiche, Users Docteur, Users Patient, Date Date_RDV, String Local, Time time) {
        this.fiche = fiche;
        this.Docteur = Docteur;
        this.Patient = Patient;
        this.Date_RDV = Date_RDV;
        this.Local = Local;
        this.time = time;
    }

    public RendezVous(int id, Fiche fiche, Users Docteur, Users Patient, Date Date_RDV, String Local, Time time) {
        this.id = id;
        this.fiche = fiche;
        this.Docteur = Docteur;
        this.Patient = Patient;
        this.Date_RDV = Date_RDV;
        this.Local = Local;
        this.time = time;
    }

    public RendezVous(int id, Users Docteur, Users Patient, Date Date_RDV, String Local) {
        this.id = id;
        this.Docteur = Docteur;
        this.Patient = Patient;
        this.Date_RDV = Date_RDV;
        this.Local = Local;
    }

    public RendezVous(Users Docteur, Users Patient, Date Date_RDV, String Local) {
        this.Docteur = Docteur;
        this.Patient = Patient;
        this.Date_RDV = Date_RDV;
        this.Local = Local;
    }

    public RendezVous() {
    }

    @Override
    public String toString() {
        return "RendezVous{" + "id=" + id + ", fiche=" + fiche + ", Docteur=" + Docteur + ", Patient=" + Patient + ", Date_RDV=" + Date_RDV + ", Local=" + Local + '}';
    }

    public RendezVous(int id, Fiche fiche, Users Docteur, Users Patient, Date Date_RDV, String Local) {
        this.id = id;
        this.fiche = fiche;
        this.Docteur = Docteur;
        this.Patient = Patient;
        this.Date_RDV = Date_RDV;
        this.Local = Local;
    }

    public RendezVous(Fiche fiche, Users Docteur, Users Patient, Date Date_RDV, String Local) {
        this.fiche = fiche;
        this.Docteur = Docteur;
        this.Patient = Patient;
        this.Date_RDV = Date_RDV;
        this.Local = Local;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Fiche getFiche() {
        return fiche;
    }

    public void setFiche(Fiche fiche) {
        this.fiche = fiche;
    }

    public Users getDocteur() {
        return Docteur;
    }

    public void setDocteur(Users Docteur) {
        this.Docteur = Docteur;
    }

    public Users getPatient() {
        return Patient;
    }

    public void setPatient(Users Patient) {
        this.Patient = Patient;
    }

    public Date getDate_RDV() {
        return Date_RDV;
    }

    public void setDate_RDV(Date Date_RDV) {

        this.Date_RDV = Date_RDV;
    }

    public String getLocal() {
        return this.Docteur.getAdresse();
    }

    public void setLocal(String Local) {
        this.Local = this.Docteur.getAdresse();
    }

}
