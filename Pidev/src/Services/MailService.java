/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author dell
 */
public class MailService {
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
