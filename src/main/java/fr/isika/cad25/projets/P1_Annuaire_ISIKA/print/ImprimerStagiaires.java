package fr.isika.cad25.projets.P1_Annuaire_ISIKA.print;

import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.Stagiaire;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class ImprimerStagiaires {

	private static String filePath;

	public static void exportToPdf(ObservableList<Stagiaire> stagiaires) throws Exception {
		String directoryPath = setPDFPath();
		if (directoryPath == null) {
			System.out.println("No directory selected.");
			return;
		}

		filePath = directoryPath + File.separator + "stagiaires.pdf";
		pdfWriting(stagiaires);
	}

	private static void pdfWriting(ObservableList<Stagiaire> stagiaires) throws Exception {
	    PDDocument document = new PDDocument();
	    PDPage page = createNewPage(document);
	    PDPageContentStream contentStream = new PDPageContentStream(document, page);

	    PDType1Font font = PDType1Font.COURIER;
	    int fontSize = 8;
	    int margin = 50;
	    int yPosition = setStartingYPosition(page, margin, fontSize);

	    contentStream.setFont(font, fontSize);
	    writeHeader(contentStream, Stagiaire.enteteTableauStagiaire(), margin, yPosition);
	    yPosition -= fontSize + 5;

	    for (Stagiaire stagiaire : stagiaires) {
	        if (yPosition < margin) {
	            contentStream.close();
	            page = createNewPage(document);
	            contentStream = new PDPageContentStream(document, page);
	            contentStream.setFont(font, fontSize);
	            yPosition = setStartingYPosition(page, margin, fontSize);
	        }

	        String s = stagiaire.pdfFormatString();
	        writeContentLine(contentStream, s, margin, yPosition);
	        yPosition -= fontSize + 3;
	    }

	    contentStream.close();
	    document.save(filePath);
	    document.close();
	}

	private static PDPage createNewPage(PDDocument document) {
	    PDPage page = new PDPage();
	    document.addPage(page);
	    return page;
	}

	private static int setStartingYPosition(PDPage page, int margin, int fontSize) {
	    return (int) (page.getMediaBox().getHeight() - margin - fontSize);
	}

	private static void writeHeader(PDPageContentStream contentStream, String header, int margin, int yPosition) throws IOException {
	    contentStream.beginText();
	    contentStream.newLineAtOffset(margin, yPosition);
	    contentStream.showText(header);
	    contentStream.endText();
	}

	private static void writeContentLine(PDPageContentStream contentStream, String line, int margin, int yPosition) throws IOException {
	    contentStream.beginText();
	    contentStream.newLineAtOffset(margin, yPosition);
	    contentStream.showText(line);
	    contentStream.endText();
	}


	private static String setPDFPath() {
		Stage stage = new Stage();
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select Folder");

		File selectedDirectory = directoryChooser.showDialog(stage);
		if (selectedDirectory != null) {
			return selectedDirectory.getAbsolutePath();
		} else {
			return null;
		}
	}
}
