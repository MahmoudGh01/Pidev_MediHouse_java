/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.services;

import edu.MediHouse.entities.Commande;
import edu.MediHouse.entities.Produit;
import edu.MediHouse.views.FXMain;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import edu.MediHouse.tools.MailAPI;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 *
 * @author sabri
 * 
 */
public class ServiceCommande {
      Connection cnx;
    public ServiceCommande(Connection cnx) {
        this.cnx = cnx;
    }
    
     public void ajouter(Commande p , ObservableList<Produit> produitsCommandes) {
        try {
            
             double prix = 0;
             double ppp = 0;
           for (Produit produitsCommande : produitsCommandes) {
               prix = prix+produitsCommande.getPrix()*produitsCommande.getQuantityproduit();
              
            }
                        p.setPrix(prix);
                           ppp=prix;
           if(produitsCommandes.size() > 3){
               MailAPI sem = new MailAPI();
                ppp = p.getPrix() - (10 *p.getPrix()/100);
                 p.setPrix(ppp);
            sem.sendEmail("ziedi.imene@esprit.tn", "Remize sur votre commande", "Un remize de 10% sur votre commande a sera effecture le prix sera : "+ ppp 
           
            + "au lieu de  : "+ prix);
            
            }
            System.out.println(ppp);
            String req = "INSERT INTO `commande`(`datecommande`, `prix`, `qtcommande`, `color`) VALUES ('"+p.getDatecommande()+"','"+ ppp +"','"+p.getQtcommande()+"','"+FXMain.user+"')";
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
            ObservableList<Commande> cc = getAll();
           
            for (Produit produitsCommande : produitsCommandes) {
                Insert(cc.get(cc.size()-1).getId(), produitsCommande.getId());
            }
            System.out.println("nbr prd" + produitsCommandes.size());
            
            System.out.println("Commande Ajouter avec succés");
                     MailAPI sem = new MailAPI();
            sem.sendEmail("ziedi.imene@esprit.tn", "nouvelle Commande", "votre commande a etait pasee avec succc");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    } 
         public void Insert(Integer IdComm ,Integer IdProduit) {
        try {
           
            String req = "INSERT INTO `commande_produit`(`commande_id`, `produit_id`) VALUES ('"+IdComm+""+"','"+IdProduit+""+"')";
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.out.println("Commande Ajouter avec succés");
   
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    } 
   
           public void supprimer(int id) {
                try {
            String req = "DELETE FROM `commande` WHERE `id`="+id;
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
            System.out.println("commande supprimer avec succés");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
           
                  public void modifier(Commande m) {
          
      try {
            String req="UPDATE `commande` SET `datecommande`=?,`prix`=?,`qtcommande`=?,`color` =? ,`img`=? ,`color` =? WHERE `id`="+m.getId();
            PreparedStatement st = cnx.prepareStatement(req);
            st.setDate(1, m.getDatecommande());
            st.setDouble(2, m.getPrix());
            st.setInt(3, m.getQtcommande());
            st.setString(4, m.getUser());
            st.executeUpdate();
            System.out.println("commande Modifié avec succés");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
  }
                  
                      public ObservableList<Commande> getAll() {
         String rep = "SELECT * FROM `commande`";
        ObservableList<Commande> l = FXCollections.observableArrayList();
        Statement stm;
        try {
            stm = this.cnx.createStatement();
            ResultSet rs = stm.executeQuery(rep);

            while (rs.next()) {
                Commande m = new  Commande();
                m.setId(rs.getInt(1));
                m.setDatecommande(rs.getDate(2));
                m.setPrix(rs.getDouble(3));
                m.setQtcommande(rs.getInt(4));
                m.setUser(rs.getString(5));
              

                l.add(m);
            
    }
             } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(l+"\n");
        return l;
    }
                      
                   
                      public void pdf (Commande m , ObservableList<Produit> produitsCommandes ){
      try {
            // Création d'un document PDF
            Document document = new Document();
PdfWriter.getInstance(document, new FileOutputStream("Facture.pdf"));
document.open();

            // Ajout de contenu
            Paragraph paragraph = new Paragraph();
            paragraph.add("Commande num " + m.getId()+"\n");
            for (Produit p : produitsCommandes) {
              paragraph.add("produit : " + p.getNomproduit() + "Quantite = " + p .getQuantityproduit()+"\n");
          
          }
            
                        paragraph.add("prix totale " + FXMain.prix+" \n");

               document.add(paragraph); 
            // Fermeture du document
            document.close();

            System.out.println("Le document PDF a été créé avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
                      
                      
                      public String ExpirationDate (Commande m){
                          Calendar calendar = Calendar.getInstance();
                            calendar.setTime(m.getDatecommande());

                        // Ajouter 14 jours à la date de l'objet Calendar
                    calendar.add(Calendar.DATE, 14);
                          System.out.println(calendar.getTime());
                    Date d = new Date(System.currentTimeMillis());
                          System.out.println(m);
                          System.out.println(d);
                                if(calendar.getTime().before(d)==true){
                                        System.out.println("expirer");
                                        return "Expirer !!";
                                }
                                else
                                {
                                    System.out.println(" non expirer");
                                     return "Non Expirer !!";
                                }

                      }
}
