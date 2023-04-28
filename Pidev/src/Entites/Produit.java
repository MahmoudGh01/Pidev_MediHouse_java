/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;

/**
 *
 * @author sabri
 */
public class Produit {
    private int id ;
    private String nomproduit ;          
    private double prix ;
    private int quantityproduit ;
    private int dispoproduit ;
    private String img ;
    private String color ;

    public Produit() {
    }

    public Produit(String nomproduit, double prix, int quantityproduit, int dispoproduit, String img, String color) {
        this.nomproduit = nomproduit;
        this.prix = prix;
        this.quantityproduit = quantityproduit;
        this.dispoproduit = dispoproduit;
        this.img = img;
        this.color = color;
    }

    public Produit(int id, String nomproduit, double prix, int quantityproduit, int dispoproduit, String img, String color) {
        this.id = id;
        this.nomproduit = nomproduit;
        this.prix = prix;
        this.quantityproduit = quantityproduit;
        this.dispoproduit = dispoproduit;
        this.img = img;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomproduit() {
        return nomproduit;
    }

    public void setNomproduit(String nomproduit) {
        this.nomproduit = nomproduit;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantityproduit() {
        return quantityproduit;
    }

    public void setQuantityproduit(int quantityproduit) {
        this.quantityproduit = quantityproduit;
    }

    public int getDispoproduit() {
        return dispoproduit;
    }

    public void setDispoproduit(int dispoproduit) {
        this.dispoproduit = dispoproduit;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nomproduit=" + nomproduit + ", prix=" + prix + ", quantityproduit=" + quantityproduit + ", dispoproduit=" + dispoproduit + ", img=" + img + ", color=" + color + '}';
    }
    
    
}
