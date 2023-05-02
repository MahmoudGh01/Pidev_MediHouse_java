/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.tools;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Skymil
 */
public class MailAPI {
    public static void sendMail(String to,String sub,String msg,int code) throws MessagingException{
        
            Properties prop=new Properties();
            prop.put("mail.smtp.host","smtp.gmail.com");
             
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smpt.ssl.trust", "smtp.gmail.com");
            Session session=Session.getDefaultInstance(prop,new javax.mail.Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication("achref.chaabani@esprit.tn","201JMT3753");
                }
            });
            MimeMessage message=new MimeMessage(session);
        
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        
            message.setSubject(sub);
            //message.setText(msg);
            message.setContent("<div style=\"background-color:#3399FF; padding:20px;\">\n" +
"        <img src=\"file:C:\\Users\\chaab\\Desktop\\3A41\\S2\\web-java-mobile\\java\\MediHouse\\src\\edu\\MediHouse\\images\\logo2.png\" alt=\"logo\" width=\"100\"/>\n" +
"        <h2 style=\"color:#FFFFFF;\">Verified your account</h2>\n" +
"        <p style=\"color:#FFFFFF;\">"+msg+"</p>\n" +
"        <h3 style=\"color:#FFFFFF;\">"+code+"</h3>\n" +
"      </div>", "text/html");
            Transport.send(message);
            System.out.println("message sent");
        
        
        
    }
     public void sendEmail(String to , String sub , String body){
        //String to = emailToField.getText();
        //String from = emailFromField.getText();
        String host = "smtp.gmail.com";
       // final String username = emailFromField.getText();
       // final String password = emailPasswordField.getText();
//  from =username="sabri.krima@esprit.tn";
// password="203JMT2383";
        //setup mail server

        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session;
          session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                              return new PasswordAuthentication("ziedi.imene@esprit.tn", "201JFT44056");

              }
          });

        try{

            //create mail
            MimeMessage m = new MimeMessage(session);
            m.setFrom(new InternetAddress("Email"));
            m.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            m.setSubject(sub);
            m.setText(body);

            //send mail

            Transport.send(m);
         
            System.out.println("Message sent!");

        }   catch (MessagingException e){
            e.printStackTrace();
        }

    }
}
