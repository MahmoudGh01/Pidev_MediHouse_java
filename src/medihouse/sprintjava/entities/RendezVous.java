/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse.sprintjava.entities;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author user
 */
public class RendezVous {

    private int id;
    private Fiche fiche;
    private User Docteur;
    private User Patient;

    private Date Date_RDV;
    private String Local;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.Docteur);
        hash = 37 * hash + Objects.hashCode(this.Patient);
        hash = 37 * hash + Objects.hashCode(this.Date_RDV);
        hash = 37 * hash + Objects.hashCode(this.Local);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RendezVous other = (RendezVous) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.Local, other.Local)) {
            return false;
        }
        if (!Objects.equals(this.Docteur, other.Docteur)) {
            return false;
        }
        if (!Objects.equals(this.Patient, other.Patient)) {
            return false;
        }
        if (!Objects.equals(this.Date_RDV, other.Date_RDV)) {
            return false;
        }
        return true;
    }

    public RendezVous(int id, User Docteur, User Patient, Date Date_RDV, String Local) {
        this.id = id;
        this.Docteur = Docteur;
        this.Patient = Patient;
        this.Date_RDV = Date_RDV;
        this.Local = Local;
    }

    public RendezVous(User Docteur, User Patient, Date Date_RDV, String Local) {
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

    public RendezVous(int id, Fiche fiche, User Docteur, User Patient, Date Date_RDV, String Local) {
        this.id = id;
        this.fiche = fiche;
        this.Docteur = Docteur;
        this.Patient = Patient;
        this.Date_RDV = Date_RDV;
        this.Local = Local;
    }

    public RendezVous(Fiche fiche, User Docteur, User Patient, Date Date_RDV, String Local) {
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

    public User getDocteur() {
        return Docteur;
    }

    public void setDocteur(User Docteur) {
        this.Docteur = Docteur;
    }

    public User getPatient() {
        return Patient;
    }

    public void setPatient(User Patient) {
        this.Patient = Patient;
    }

    public Date getDate_RDV() {
        return Date_RDV;
    }

    public void setDate_RDV(Date Date_RDV) {
        this.Date_RDV = Date_RDV;
    }

    public String getLocal() {
        return Local;
    }

    public void setLocal(String Local) {
        this.Local = Local;
    }

}
