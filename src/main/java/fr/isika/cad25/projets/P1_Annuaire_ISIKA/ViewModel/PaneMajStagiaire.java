package fr.isika.cad25.projets.P1_Annuaire_ISIKA.ViewModel;

import java.util.List;

import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.ArbreBinaireBin;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.Stagiaire;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PaneMajStagiaire extends VBox{
	
	private User user;
	private Stagiaire cible;
	
	private TextField tfNom;
	private TextField tfPrenom;
	private TextField tfDepartement;
	private ChoiceBox<String> cbParcours;
	private TextField tfNumPromo;
	private CheckBox ckbContratPro;
	private TextField tfAnnee;
	
	private Label lWarningPrenom;
	private Label lWarningCP;
	private Label lWarningParcours;
	private Label lWarningAnnee;

	private Button bActualiser;
	private Button bRetour;
	
	
	public PaneMajStagiaire(User user, Stagiaire stagiaire) throws Exception {
		super();
		this.cible = stagiaire;
		this.user = user;
		
		setAlignment(Pos.CENTER);
		
		getChildren().add(mainIcon());
		getChildren().add(setContenu());
		
		HBox buttons = new HBox(15);
		bActualiser = setBtnActualiser();
		bRetour = setBtnRetour();
		
		buttons.getChildren().addAll(bActualiser,bRetour);
		buttons.setAlignment(Pos.CENTER);
		
		getChildren().add(buttons);
		setPadding(new Insets(20, 0, 20, 0));
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
			ArbreBinaireBin.rechercheRemplace(this.cible,miseAjour);
			versRechercheStagiaire();
					
		}else {
			setWarnings();
		}

	}
	
	public ImageView mainIcon() {
		ImageView iv = new ImageView();
		Image icon = new Image("file:src/icons/account-sync.png");

		iv.setImage(icon);
		iv.setFitWidth(50);
		iv.setFitHeight(50);

		return iv;
	}

	public Button setBtnRetour() {
		Button btn = new Button("retour");
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		try {
        			versRechercheStagiaire();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        });
		
		return btn;
	
	}
	
	public void versRechercheStagiaire() throws Exception {
		Scene sceneRecherche = new Scene(new PaneRechercheStagiaire(user));
		Stage stage = (Stage) PaneMajStagiaire.this.getScene().getWindow();
		stage.setScene(sceneRecherche);
	}
	
	public void eraseWarnings() {
		lWarningPrenom = new Label("");
		lWarningCP = new Label("");
		lWarningParcours = new Label("");
		lWarningAnnee = new Label("");
	}

	public void setWarnings() {
		lWarningPrenom.setText("!");
		lWarningPrenom.setTextFill(Color.RED);
		lWarningCP.setText("!");
		lWarningCP.setTextFill(Color.RED);
		lWarningParcours.setText("!");
		lWarningParcours.setTextFill(Color.RED);
		lWarningAnnee.setText("!");
		lWarningAnnee.setTextFill(Color.RED);
	}

	public GridPane setContenu() {
		GridPane gp = new GridPane();
		eraseWarnings();

		Label lNom = new Label("nom");
		tfNom = CompoStag.setStringTf("");
		tfNom.setText(this.cible.getNom());
		tfNom.setDisable(true);
		gp.addRow(0, lNom, tfNom);

		Label lPrenom = new Label("prenom");
		tfPrenom = CompoStag.setStringTf("");
		tfPrenom.setText(this.cible.getPrenom());
		gp.addRow(1, lPrenom, tfPrenom,lWarningPrenom);

		Label lDepartement = new Label("departement");
		tfDepartement = CompoStag.setCodePostalTf("ex : 44");
		tfDepartement.setText(this.cible.getDepartement());
		gp.addRow(2, lDepartement, tfDepartement,lWarningCP);

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

		hboxParcours.getChildren().addAll(lParcours, cbParcours, lnumPromo, tfNumPromo, lContratPro, ckbContratPro,lWarningParcours);
		hboxParcours.setPadding(new Insets(10, 0, 10, 0));
		hboxParcours.setAlignment(Pos.CENTER);
		
		gp.add(hboxParcours, 0, 3, 2, 1);

		Label lAnnee = new Label("Annee Graduation");
		tfAnnee = CompoStag.setNumPromo("");
		tfAnnee.setText(this.cible.getAnnee()+"");

		gp.addRow(4, lAnnee, tfAnnee,lWarningAnnee);
		gp.setPadding(new Insets(20, 20, 20, 20));

		return gp;
	}

}
