/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.services;

import edu.MediHouse.entities.Produit;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sabri
 */

public class ServiceProduit {
  Connection cnx;

    public ServiceProduit(Connection cnx) {
        this.cnx = cnx;
    }
    
   public void ajouter(Produit p) {
        try {
            String req = "INSERT INTO `produit`(`nomproduit`, `prix`, `quantityproduit`, `dispoproduit` ,`img` ,`color` ) VALUES ('"+p.getNomproduit()+"','"+p.getPrix()+"','"+p.getQuantityproduit()+"','"+p.getDispoproduit()+"','"+p.getImg()+"','"+p.getColor()+"')";
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.out.println("produit Ajouter avec succés");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    } 
   
           public void supprimer(int id) {
                try {
            String req = "DELETE FROM `produit` WHERE `id`="+id;
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.out.println("produit supprimer avec succés");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
           
                  public void modifier(Produit m) {
          
      try {
            String req="UPDATE `produit` SET `nomproduit`=?,`prix`=?,`quantityproduit`=?,`dispoproduit` =? ,`img`=? ,`color` =? WHERE `id`="+m.getId();
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, m.getNomproduit());
            st.setDouble(2, m.getPrix());
            st.setInt(3, m.getQuantityproduit());
            st.setInt(4, m.getDispoproduit());
            st.setString(5, m.getImg());
            st.setString(6, m.getColor());
            st.executeUpdate();
            System.out.println("produit Modifié avec succés");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
  }
                                    public void modifierQnt(int id , int qt) {
                                        System.out.println(qt);
          Produit p = getOne(id);
      try {
            String req="UPDATE `produit` SET `quantityproduit`=? WHERE `id`="+id;
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, p.getQuantityproduit() - qt);
          
            st.executeUpdate();
            System.out.println("produit Modifié avec succés");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
  }
                  
                      public ObservableList<Produit> getAll() {
         String rep = "SELECT * FROM `produit`";
        ObservableList<Produit> l = FXCollections.observableArrayList();
        Statement stm;
        try {
            stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(rep);

            while (rs.next()) {
                Produit m = new  Produit();
                m.setId(rs.getInt(1));
                m.setNomproduit(rs.getString("nomproduit"));
                m.setPrix(rs.getDouble("prix"));
                m.setQuantityproduit(rs.getInt("quantityproduit"));
                m.setDispoproduit(rs.getInt("dispoproduit"));
                m.setImg(rs.getString("img"));
                m.setColor(rs.getString("color"));
              

                l.add(m);
            
    }
             } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(l+"\n");
        return l;
    }
                      
                      
    public Produit getOne(int id) {
         String rep = "SELECT * FROM `produit` WHERE `id` ="+id;
        Statement stm;
        try {
            stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(rep);

            while (rs.next()) {
                 Produit m = new  Produit();
                m.setId(rs.getInt(1));
                m.setNomproduit(rs.getString(2));
                m.setPrix(rs.getDouble(3));
                m.setQuantityproduit(rs.getInt(4));
                m.setDispoproduit(rs.getInt(5));
                m.setImg(rs.getString(6));
                m.setColor(rs.getString(7));

                return m;
            
    }
             } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
        public int depliqName(String ch){
    ObservableList<Produit> l = getAll();
        for (Produit produit : l) {
            if(produit.getNomproduit().equals(ch))
                return 1;
        }
        return 0;
}
        
        

public void csv(){
    try {
        ObservableList<Produit> ll = getAll();
        PrintWriter pw= new PrintWriter(new File("Produis_table.csv"));
   StringBuilder sb=new StringBuilder();
      sb.append("Id");
    sb.append(",");
   sb.append("Nom");
   sb.append(",");
   sb.append("Disponibilite");
     sb.append(",");
   sb.append("Prix");
     sb.append(",");
   sb.append("");
     sb.append(",");
   sb.append("colr");
   sb.append("\n");
        for (Produit produit : ll) {
              sb.append(produit.getId());
   sb.append(",");
   sb.append(produit.getNomproduit());
   sb.append(",");
   sb.append(produit.getDispoproduit());
     sb.append(",");
   sb.append(produit.getPrix());
     sb.append(",");
   sb.append(produit.getQuantityproduit());
     sb.append(",");
   sb.append(produit.getColor());
   sb.append("\n");
        }
 
   
   pw.write(sb.toString());
   pw.close();
   System.out.println("finished");
   } catch (Exception e) {
      // TODO: handle exception
   }
    }
        
}

