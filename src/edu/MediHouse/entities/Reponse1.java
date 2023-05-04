/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.entities;

import java.sql.Date;

/**
 *
 * @author DELL
 */
public class Reponse1 {
     private int id;
    private String rep_contenu;
    private Date rep_date_pub;
    private int quest_id;
    private int id_quest;
      public int getLikes() {
        return likes;
    }
private int id_user;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    public int getId_quest() {
        return id_quest;
    }

    public void setId_quest(int id_quest) {
        this.id_quest = id_quest;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
private int likes;
private int dislikes;
   public void incrementLikes() {
        this.likes++;
    }

    public void incrementDislikes() {
        this.dislikes++;
    }

    public Reponse1(String rep_contenu, Date rep_date_pub) {
        this.rep_contenu = rep_contenu;
        this.rep_date_pub = rep_date_pub;
    }

    public int getQuest_id() {
        return quest_id;
    }

    public void setQuest_id(int quest_id) {
        this.quest_id = quest_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRep_contenu() {
        return rep_contenu;
    }

    public void setRep_contenu(String rep_contenu) {
        this.rep_contenu = rep_contenu;
    }

    public Date getRep_date_pub() {
        return rep_date_pub;
    }

    public Reponse1(String rep_contenu, Date rep_date_pub, int likes, int dislikes) {
        this.rep_contenu = rep_contenu;
        this.rep_date_pub = rep_date_pub;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public void setRep_date_pub(Date rep_date_pub) {
        this.rep_date_pub = rep_date_pub;
    }

    @Override
    public String toString() {
        return "reponse{" + "id=" + id + ", rep_contenu=" + rep_contenu + ", rep_date_pub=" + rep_date_pub + ", quest_id=" + quest_id + ", id_quest=" + id_quest + ", likes=" + likes + ", dislikes=" + dislikes + '}';
    }

  

    public Reponse1() {
    }
    
    
    
    
}
