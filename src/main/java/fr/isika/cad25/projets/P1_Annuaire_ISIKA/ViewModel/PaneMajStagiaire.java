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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaneMajStagiaire extends VBox{
	
	private Stagiaire cible;
	
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

	private Button bActualiser;
	private Button bRetour;
	
	
	public PaneMajStagiaire(Stagiaire stagiaire) throws Exception {
		super();
		this.cible = stagiaire;
		
		getChildren().add(new Label("MISE-A-JOUR STAGIAIRE"));
		getChildren().add(setContenu());
		
		HBox buttons = new HBox(15);
		bActualiser = setBtnActualiser();
		bRetour = setBtnRetour();
		buttons.getChildren().addAll(bActualiser,bRetour);
		
		getChildren().add(buttons);
	}

	public Button setBtnActualiser() {
		Button btn = new Button("Actualiser");

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					launchMiseAJour();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		return btn;
	}

	public void launchMiseAJour() throws Exception{
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
			Stagiaire miseAjour = new Stagiaire(nom, prenom, departement, parcours, numPromo, contratPro, annee);
			System.out.println(cible.toString());
			System.out.println(miseAjour.toString());
			ArbreBinaireBin.rechercheRemplace(this.cible,miseAjour);
					
		}else {
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
					Stage stage = (Stage) PaneMajStagiaire.this.getScene().getWindow();
					stage.setScene(sceneRecherche);
					
				} catch (Exception e) {
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
		tfNom.setText(this.cible.getNom());
		tfNom.setDisable(true);
		gp.addRow(0, lNom, tfNom);

		Label lPrenom = new Label("prenom");
		tfPrenom = CompoStag.setStringTf("");
		tfPrenom.setText(this.cible.getPrenom());
		gp.addRow(1, lPrenom, tfPrenom);

		Label lDepartement = new Label("departement");
		tfDepartement = CompoStag.setCodePostalTf("ex : 44");
		tfDepartement.setText(this.cible.getDepartement());
		gp.addRow(2, lDepartement, tfDepartement);

		HBox hboxParcours = new HBox();
		Label lParcours = new Label("parcours");
		cbParcours = CompoStag.setParcours();
		cbParcours.getSelectionModel().select(this.cible.getParcours());

		Label lnumPromo = new Label("n°");
		tfNumPromo = CompoStag.setNumPromo("");
		tfNumPromo.setText(this.cible.getNumPromo()+"");
		
		Label lContratPro = new Label("Contrat Pro?");
		ckbContratPro = new CheckBox();
		ckbContratPro.setSelected(this.cible.getContratPro()=='O'?true:false);

		hboxParcours.getChildren().addAll(lParcours, cbParcours, lnumPromo, tfNumPromo, lContratPro, ckbContratPro);
		gp.add(hboxParcours, 0, 3, 2, 1);

		Label lAnnee = new Label("Annee Graduation");
		tfAnnee = CompoStag.setNumPromo("");
		tfAnnee.setText(this.cible.getAnnee()+"");

		gp.addRow(4, lAnnee, tfAnnee);

		return gp;
	}

}
