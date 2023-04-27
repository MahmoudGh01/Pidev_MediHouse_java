/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medihouse.sprintjava.views;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import medihouse.sprintjava.entities.Fiche;
import medihouse.sprintjava.entities.RendezVous;
import medihouse.sprintjava.services.FicheCRUD;
import medihouse.sprintjava.services.RendezVousCRUD;
import medihouse.sprintjava.services.UserCRUD;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ShowFicheController implements Initializable {

    @FXML
    private Label Age;
    @FXML
    private Label Bloodtype;
    @FXML
    private Label Patient_name;
    @FXML
    private TableView<RendezVous> Tbv;
    @FXML
    private TableColumn<RendezVous, Integer> Id_Col;
    @FXML
    private TableColumn<RendezVous, Date> Date_Col;
    @FXML
    private TableColumn<RendezVous, String> Local_Col;
    @FXML
    private Button btnPDF;
    
    private int ID_Fiche;
    
    private FicheCRUD F;
    private UserCRUD U;

    public ShowFicheController(int ID) {
        this.ID_Fiche = ID;
        this.F = new FicheCRUD();
        this.U = new UserCRUD();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("ID_Fiche: " + ID_Fiche);
        ShowRendezVous();
    }

    private void ShowRendezVous() {
        RendezVousCRUD rdv = new RendezVousCRUD();
        Fiche f = F.getFicheById(ID_Fiche);
        ObservableList<RendezVous> list = FXCollections.observableArrayList(rdv.listerRendezVousByFiche(f.getId()));
        Id_Col.setCellValueFactory(new PropertyValueFactory<RendezVous, Integer>("id"));

        Date_Col.setCellValueFactory(new PropertyValueFactory<>("Date_RDV"));
        Date_Col.setCellFactory(column -> {
            return new TableCell<RendezVous, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                    }
                }
            };
        });

        Local_Col.setCellValueFactory(new PropertyValueFactory<RendezVous, String>("Local"));

        Tbv.setItems(list);
        Age.setText(String.valueOf(f.getAge()) );
        Bloodtype.setText(f.getBloodType());
        Patient_name.setText(f.getPatient().getNom());
        
    }

    @FXML
    private void btnPDF(ActionEvent event) {
        Fiche f = F.getFicheById(ID_Fiche);
        pdf(f);
      
    }
    private void pdf (Fiche f){
          try {
        // Open the existing PDF file using PdfReader
        PdfReader reader = new PdfReader("new_document.pdf");

        // Create a PdfStamper object from the PdfReader and a FileOutputStream
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("new_fiche.pdf"));

        // Access the fields in the PDF using the AcroFields object
        AcroFields form = stamper.getAcroFields();

        // Fill in the fields with the desired values using the setField method
        form.setField("Patient_name", f.getPatient().getNom());
        form.setField("Age", String.valueOf(f.getAge()));
        form.setField("BloodType", f.getBloodType());
       

        // Create a PdfPTable object from the TableView data
        float tableWidth = (PageSize.A4.getWidth() - 72f * 2); // width of page minus left/right margin
        PdfPTable pdfTable = new PdfPTable(Tbv.getColumns().size());
        pdfTable.setWidthPercentage(100);
        pdfTable.setTotalWidth(tableWidth);
        pdfTable.getDefaultCell().setPadding(3);
        pdfTable.getDefaultCell().setBorderWidth(1);
        pdfTable.getDefaultCell().setBorderColor(BaseColor.BLACK);
        pdfTable.getDefaultCell().setBackgroundColor(new BaseColor(225, 225, 225));
        pdfTable.getDefaultCell().setBackgroundColor(new BaseColor(150, 150, 150));
        pdfTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        addTableHeader(pdfTable);
        addRows(pdfTable, Tbv.getItems());

        // Add the table to the first page of the PDF
        PdfContentByte content = stamper.getOverContent(1);
        pdfTable.writeSelectedRows(0, -1, content.getPdfDocument().getPageSize().getWidth() / 10, content.getPdfDocument().getPageSize().getHeight() / 2, content);

        // Close the PdfStamper object to finalize the filled PDF
        stamper.close();
//        printPDF("BL_imprime.pdf");
        System.out.println("Le fichier BL_imprime.pdf a été généré avec succès !");
    } catch (Exception e) {
        System.out.println("Erreur lors de la génération du fichier PDF : " + e.getMessage());
    }
    
    }
    private void addTableHeader(PdfPTable table) {
    for (TableColumn column : Tbv.getColumns()) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(1);
        header.setPhrase(new Phrase(column.getText(), new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        table.addCell(header);
    }
}

private void addRows(PdfPTable table, ObservableList<RendezVous> data) {
    for (RendezVous produit : data) {
        for (TableColumn column : Tbv.getColumns()) {
            Object cellValue = column.getCellData(produit);
            table.addCell(new Phrase(cellValue == null ? "" : cellValue.toString(), new Font(Font.FontFamily.HELVETICA, 10)));
        }
    }
}
}
