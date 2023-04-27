/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pidev.entities;

import java.sql.Date;

/**
 *
 * @author DELL
 */
public class question {
       private int id;
       private String categorie;
private int hide_name;


    public void setHide_name(int hide_name) {
        this.hide_name = hide_name;
    }

    public int getHide_name() {
        return hide_name;
    }
    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    private String ques_contenu;
    private Date ques_date_pub;

    public int getLikes() {
        return likes;
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
    @Override
    public String toString() {
        return "question{" + "id=" + id + ", ques_contenu=" + ques_contenu + ", ques_date_pub=" + ques_date_pub + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQues_contenu() {
        return ques_contenu;
    }

    public void setQues_contenu(String ques_contenu) {
        this.ques_contenu = ques_contenu;
    }

    public Date getQues_date_pub() {
        return ques_date_pub;
    }

    public void setQues_date_pub(Date ques_date_pub) {
        this.ques_date_pub = ques_date_pub;
    }

    public question() {
    }

    public question(String ques_contenu, Date ques_date_pub) {
        this.ques_contenu = ques_contenu;
        this.ques_date_pub = ques_date_pub;
    }

}
