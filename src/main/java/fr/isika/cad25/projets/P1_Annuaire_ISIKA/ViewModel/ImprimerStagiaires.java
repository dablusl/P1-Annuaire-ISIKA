package fr.isika.cad25.projets.P1_Annuaire_ISIKA.ViewModel;


import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.Stagiaire;
import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.FileOutputStream;
import java.io.IOException;


public class ImprimerStagiaires {
	
	static String filePath = "C:\\Users\\gonza\\OneDrive\\Desktop\\export.pdf";
	
	public static void exportToPdf(ObservableList<Stagiaire> stagiaires) {
		System.out.println("Chantier, import PDFbox reussi");
	}

}
