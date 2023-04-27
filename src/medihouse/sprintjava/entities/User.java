/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse.sprintjava.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author user
 */
public class User {

    private int id;
    private String Nom, email, role;
    private ImageView Image;
   

    

    public ImageView getImage() {
        Image Img = new Image(getClass().getResourceAsStream("../images/patient2.jpg"));
        Image = new ImageView(Img);
        //System.out.print();
        return Image;
    }

    public void setImage(ImageView Image) {
        //Image user1Image = new Image(getClass().getResourceAsStream("../images/patient1.jpg"));
        this.Image = Image;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(int id, String Nom, String email) {
        this.id = id;
        this.Nom = Nom;
        this.email = email;
    }

    public User() {
    }

    public User(String Nom, String email) {
        this.Nom = Nom;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public User(String Nom, String email, String role) {
        this.Nom = Nom;
        this.email = email;
        this.role = role;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return Nom ;
    }

}
