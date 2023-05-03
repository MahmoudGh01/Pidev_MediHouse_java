/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.MediHouse.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import edu.MediHouse.entities.RendezVous;
import edu.MediHouse.services.RendezVousCRUD;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ChartController implements Initializable {
    RendezVousCRUD RC =new RendezVousCRUD();
    @FXML
    private LineChart<String, Integer> line;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Chart();
    }    
    
    private void Chart(){
    List<RendezVous> rendezVousList = RC.listerRendezVous();

// Créer un XYChart.Series pour stocker les données de rendez-vous
XYChart.Series<String, Integer> rendezVousSeries = new XYChart.Series<>();

// Ajouter les données de rendez-vous au XYChart.Series
for (RendezVous rendezVous : rendezVousList) {
    String date = rendezVous.getDate_RDV().toString();
    Integer nombreRendezVous = RC.getNombreRendezVous(rendezVous.getDate_RDV());
    System.out.println(nombreRendezVous);
    rendezVousSeries.getData().add(new XYChart.Data<>(date, nombreRendezVous));
}

// Créer un LineChart


// Ajouter le XYChart.Series au LineChart
line.getData().add(rendezVousSeries);
        
    }
}
