/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

/**
 *
 * @author chaab
 */
import java.io.StringWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class MailXMLGenerator {
    
    public static String generateMailXML(String code) {
        String xml = null;
        try {
            StringWriter stringWriter = new StringWriter();
            XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);
            // Entête XML
            xMLStreamWriter.writeStartDocument();
            // Racine
            xMLStreamWriter.writeStartElement("email");
            // Ajouter le logo
            xMLStreamWriter.writeStartElement("logo");
            xMLStreamWriter.writeAttribute("src", "file:///C:\\Users\\chaab\\Desktop\\3A41\\S2\\web-java-mobile\\java\\MediHouse\\src\\edu\\MediHouse\\images/footer-logo1.png");
            xMLStreamWriter.writeEndElement();
            // Ajouter le message
            xMLStreamWriter.writeStartElement("message");
            xMLStreamWriter.writeCharacters("Verified your account");
            xMLStreamWriter.writeEndElement();
            // Ajouter le code
            xMLStreamWriter.writeStartElement("code");
            xMLStreamWriter.writeCharacters(code);
            xMLStreamWriter.writeEndElement();
            // Fermer la balise racine
            xMLStreamWriter.writeEndElement();
            // Terminer le document
            xMLStreamWriter.writeEndDocument();
            xMLStreamWriter.flush();
            xMLStreamWriter.close();
            xml = stringWriter.getBuffer().toString();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return xml;
    }
}