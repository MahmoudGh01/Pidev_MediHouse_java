/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse.sprintjava.views;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author user
 */
public class CalendarController implements Initializable {

    @FXML
    private Circle ProfilePic;
    @FXML
    private Label UserName;
    @FXML
    private WebView webv;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Calendar();
        } catch (MalformedURLException ex) {
            Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Profile(ActionEvent event) {
    }

    @FXML
    private void Logout(ActionEvent event) {
    }
    private void Calendar() throws MalformedURLException {
        
WebEngine engine =    webv.getEngine();

File file =new File("fullCalendar.html");
System.out.print(file.getAbsolutePath());
engine.load(file.toURI().toURL().toString());
    }
    
}
