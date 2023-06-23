package fr.isika.cad25.projets.P1_Annuaire_ISIKA.ViewModel;

import java.util.List;

import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.ArbreBinaireBin;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.Stagiaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaneAjoutStagiaire extends VBox {

	private final static int TAILLE_NOM = Stagiaire.TAILLE_NOM;
	private final static int TAILLE_PRENOM = Stagiaire.TAILLE_PRENOM;
	private final static int TAILLE_CP = Stagiaire.TAILLE_CP;

	private TextField tfNom;
	private TextField tfPrenom;
	private TextField tfDepartement;
	private ChoiceBox<String> cbParcours;
	private TextField tfNumPromo;
	private CheckBox ckbContratPro;
	private TextField tfAnnee;
	
	private Label lWarningPrenom;
	private Label lWarningNNom;
	private Label lWarningCP;
	private Label lWarningParcours;
	private Label lWarningAnnee;

	private Button bAjouter;
	private Button bRetour;

	public PaneAjoutStagiaire() throws Exception {
		super();
		getChildren().add(new Label("AJOUTER STAGIAIRE"));
		getChildren().add(setContenu());
		
		HBox buttons = new HBox(15);
		bAjouter = setBtnAjouter();
		bRetour = setBtnRetour();
		buttons.getChildren().addAll(bAjouter,bRetour);
		
		getChildren().add(buttons);
	}

	public Button setBtnAjouter() {
		Button btn = new Button("Ajouter");

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					launchAjout();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		return btn;
	}

	public void launchAjout() throws Exception{
		boolean switchCompliance = true;

		String nom = "";
		String prenom = "";
		String departement = "";
		String parcours = "";
		short numPromo = (short) -1;
		char contratPro = 'v';
		short annee = (short) -1;

		if (tfNom.getText().matches("[A-Za-zéèê\\-' ]+")) {
			nom = tfNom.getText().toLowerCase();
		} else {
			switchCompliance = false;
		}

		if (tfPrenom.getText().matches("[A-Za-zéèê\\-' ]+")) {
			prenom = tfPrenom.getText().toLowerCase();
		} else {
			switchCompliance = false;
		}

		if (tfDepartement.getText().matches("\\d+")){
			departement = tfDepartement.getText();
		} else {
			switchCompliance = false;
		}

		parcours = cbParcours.getValue();

		if (tfNumPromo.getText().matches("\\d+")) {
			try {
				numPromo = Short.parseShort(tfNumPromo.getText());
			} catch (NumberFormatException e) {
				System.err.println("Defaut numPromo regex ?");
				switchCompliance= false;
			}
		} else {
			switchCompliance= false;
		}

		contratPro = ckbContratPro.isSelected()?'O':'N';

		if (tfAnnee.getText().matches("\\d+")) {
			try {
				annee = Short.parseShort(tfAnnee.getText());
			} catch (NumberFormatException e) {
				System.err.println("Defaut annee regex ?");
				switchCompliance = false;
			}
		} else {
			switchCompliance = false;
		}

		if (switchCompliance) {
			//doublons????
			Stagiaire nouveauStagiaire = new Stagiaire(nom, prenom, departement, parcours, numPromo, contratPro, annee);
			
			List<Stagiaire> matches = ArbreBinaireBin.recherche(nouveauStagiaire);
			if(matches.size()>0) {
				System.err.println("doublon");
			}else {
				ArbreBinaireBin.ajouterStagiaire(nouveauStagiaire);
				System.out.println("Stagiaire ajouté");
				//effacer tous les champs
			}
			//observableStagiaires.clear();
			//observableStagiaires.addAll(matches);
		}else {
			//actualisation label warning,
			System.err.println("Pas d'ajout car pas de respect de contraintes");
		}

	}

	public Button setBtnRetour() {
		Button btn = new Button("retour");
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		try {
        			Scene sceneRecherche = new Scene(new PaneRechercheStagiaire());
					Stage stage = (Stage) PaneAjoutStagiaire.this.getScene().getWindow();
					stage.setScene(sceneRecherche);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
		
		return btn;
	
	}

	public GridPane setContenu() {
		GridPane gp = new GridPane();

		Label lNom = new Label("nom");
		tfNom = CompoStag.setStringTf("");
		gp.addRow(0, lNom, tfNom);

		Label lPrenom = new Label("prenom");
		tfPrenom = CompoStag.setStringTf("");
		gp.addRow(1, lPrenom, tfPrenom);

		Label lDepartement = new Label("departement");
		tfDepartement = CompoStag.setCodePostalTf("ex : 44");
		gp.addRow(2, lDepartement, tfDepartement);

		HBox hboxParcours = new HBox();
		Label lParcours = new Label("parcours");
		cbParcours = CompoStag.setParcours();

		Label lnumPromo = new Label("n°");
		tfNumPromo = CompoStag.setNumPromo("");

		Label lContratPro = new Label("Contrat Pro?");
		ckbContratPro = new CheckBox();

		hboxParcours.getChildren().addAll(lParcours, cbParcours, lnumPromo, tfNumPromo, lContratPro, ckbContratPro);
		gp.add(hboxParcours, 0, 3, 2, 1);

		Label lAnnee = new Label("Annee Graduation");
		tfAnnee = CompoStag.setNumPromo("");

		gp.addRow(4, lAnnee, tfAnnee);

		return gp;
	}

}
