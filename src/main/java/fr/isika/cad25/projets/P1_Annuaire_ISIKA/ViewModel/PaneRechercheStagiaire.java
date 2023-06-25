package fr.isika.cad25.projets.P1_Annuaire_ISIKA.ViewModel;

import java.util.List;

import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.*;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.ViewModel.CompoStag;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.print.ImprimerStagiaires;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaneRechercheStagiaire extends BorderPane{
	
	private TextField tfNom;
	private TextField tfPrenom;
	private TextField tfDepartement;
	private ChoiceBox<String> cbParcours;
	private TextField tfNumPromo;
	private ChoiceBox<String> cbContratPro;
	private TextField tfAnnee;
	private ToggleButton rechercheAvancee;

	private HBox criteresAvancees;
	private VBox operationsAdmin;
	
	private Button searchButton;
	private Button bAjouterStagiaire;
	private Button bMAJstagiaire;
	private Button bSupprimerStagiaire;
	private Button imprimerStagiaires;
	
	private TableView<Stagiaire> resultats;
	ObservableList<Stagiaire> observableStagiaires;
	private Stagiaire selectedStagiaire;

	public PaneRechercheStagiaire() throws Exception{
		super();
		
		Label enTete = new Label("Bienvenue");
		VBox centre = new VBox(10);
		GridPane criteres = setCriteres();
		
		operationsAdmin = setOperationsAdmin();
		resultats=setResultats();
		
		centre.getChildren().add(criteres);
		centre.getChildren().add(resultats);
		
		setTop(enTete);
		setCenter(centre);
		setRight(operationsAdmin);
		
		//setEvents

	}
	
	public ChoiceBox<String> setContratPro() {
		ChoiceBox<String> cb = new ChoiceBox<String>();
		
		cb.getItems().addAll("","O","N");
		cb.getSelectionModel().select(0);		
		
		return cb;
	}

	public VBox setOperationsAdmin() {
		VBox vbox = new VBox(15);
		bAjouterStagiaire = setAjouterStagiaire();
		bMAJstagiaire = setMAJStagiaire();
		bSupprimerStagiaire = setSupprimerStagiaire();
		searchButton = setSearchButton();
		imprimerStagiaires = setPrintList();
		
		bMAJstagiaire.setDisable(true);
		bSupprimerStagiaire.setDisable(true);
		
		vbox.getChildren().addAll(searchButton,new Label(),bAjouterStagiaire,bMAJstagiaire,bSupprimerStagiaire, imprimerStagiaires);
		vbox.setPadding(new Insets(0,10,0,5));
		
		return vbox;
	}
	
	public Button setAjouterStagiaire() {
	Image iconSearch = new Image("file:src/icons/account-plus.png");
		
		ImageView ivIconSearch = new ImageView(iconSearch);
		ivIconSearch.setFitWidth(20);
		ivIconSearch.setFitHeight(20);
		
		Button iconButton = new Button();
		iconButton.setGraphic(ivIconSearch);
		
		Tooltip restrictionTooltip = new Tooltip("Ajouter un stagiaire");
		Tooltip.install(iconButton,restrictionTooltip);
		
		iconButton.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		try {
        			if(selectedStagiaire!=null) {
        				Scene sceneAjouter = new Scene(new PaneAjoutStagiaire());
        				Stage stage = (Stage) PaneRechercheStagiaire.this.getScene().getWindow();
        				stage.setScene(sceneAjouter);
        			}
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        });
		
		return iconButton;	
	}
	
	public Button setMAJStagiaire() {
		Image iconSearch = new Image("file:src/icons/account-sync.png");
			
			ImageView ivIconSearch = new ImageView(iconSearch);
			ivIconSearch.setFitWidth(20);
			ivIconSearch.setFitHeight(20);
			
			Button iconButton = new Button();
			iconButton.setGraphic(ivIconSearch);
			
			Tooltip restrictionTooltip = new Tooltip("Mettre à jour un stagiaire");
			Tooltip.install(iconButton,restrictionTooltip);
			
			iconButton.setOnAction(new EventHandler<ActionEvent>() {
	        	
	        	@Override
	        	public void handle(ActionEvent event) {
	        		try {
	        			Scene sceneAjouter = new Scene(new PaneMajStagiaire(selectedStagiaire));
						Stage stage = (Stage) PaneRechercheStagiaire.this.getScene().getWindow();
						stage.setScene(sceneAjouter);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        });
			
			return iconButton;	
	}
	
	public Button setSupprimerStagiaire() {
		Image iconSearch = new Image("file:src/icons/delete.png");
			
			ImageView ivIconSearch = new ImageView(iconSearch);
			ivIconSearch.setFitWidth(20);
			ivIconSearch.setFitHeight(20);
			
			Button iconButton = new Button();
			iconButton.setGraphic(ivIconSearch);
			
			Tooltip restrictionTooltip = new Tooltip("Supprimer un stagiaire");
			Tooltip.install(iconButton,restrictionTooltip);
			
			iconButton.setOnAction(new EventHandler<ActionEvent>() {
	        	
	        	@Override
	        	public void handle(ActionEvent event) {
	        		try {
						ArbreBinaireBin.rechercheSupprime(selectedStagiaire);
						launchSearch();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        });
			
			return iconButton;	
	}
	
	public Button setPrintList() {
		Image iconSearch = new Image("file:src/icons/printer.png");
			
			ImageView ivIconSearch = new ImageView(iconSearch);
			ivIconSearch.setFitWidth(20);
			ivIconSearch.setFitHeight(20);
			
			Button iconButton = new Button();
			iconButton.setGraphic(ivIconSearch);
			
			Tooltip restrictionTooltip = new Tooltip("Imprimer la liste de stagiaires affichés");
			Tooltip.install(iconButton,restrictionTooltip);
			
			iconButton.setOnAction(new EventHandler<ActionEvent>() {
	        	
	        	@Override
	        	public void handle(ActionEvent event) {
	        		try {
						ImprimerStagiaires.exportToPdf(observableStagiaires);;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        });
			
			return iconButton;	
	}
	
	public Button setSearchButton() {
		Image iconSearch = new Image("file:src/icons/account-search-outline.png");
		
		ImageView ivIconSearch = new ImageView(iconSearch);
		ivIconSearch.setFitWidth(20);
		ivIconSearch.setFitHeight(20);
		
		Button iconButton = new Button();
		iconButton.setGraphic(ivIconSearch);
		iconButton.setPrefHeight(60);
		
		iconButton.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		try {
					launchSearch();
				} catch (Exception e) {


					e.printStackTrace();
				}
        	}
        });
		
		return iconButton;
	}
	
	public void launchSearch() throws Exception {
		boolean switchSearch = true;
		
		String nom="";
		String prenom="";
		String departement="";
		String parcours="";
		short numPromo=(short)-1;
		char contratPro='v';
		short annee=(short)-1;
		
		
		if(tfNom.getText().matches("[A-Za-zéèê\\-' ]*")) {
			nom = tfNom.getText().toLowerCase();
		}else {
			switchSearch = false;
		}
		
		if(tfPrenom.getText().matches("[A-Za-zéèê\\-' ]*")) {
			prenom = tfPrenom.getText().toLowerCase();
		}else {
			switchSearch = false;
		}
		
		if(rechercheAvancee.isSelected()) {
			if(tfDepartement.getText().matches("\\d*") || tfDepartement.getText().length()==0) {
				departement = tfDepartement.getText();
			}else {
				switchSearch = false;
			}
		
			parcours = cbParcours.getValue();
		
			if(tfNumPromo.getText().matches("\\d*") || tfNumPromo.getText().length()==0) {
				try {
					numPromo = Short.parseShort(tfNumPromo.getText());
				} catch (NumberFormatException e) {
					numPromo =(short)-1;
				}
			}else {
				switchSearch = false;
			}

			contratPro = cbContratPro.getValue().length()==0
							?'v':cbContratPro.getValue().charAt(0);
		
			if(tfAnnee.getText().matches("\\d*") || tfAnnee.getText().length()==0) {
				try {
					annee = Short.parseShort(tfAnnee.getText());
				} catch (NumberFormatException e) {
					annee = (short)-1;
				}
			}else {
				switchSearch = false;
			}
		}
		
		if (switchSearch) {
			Stagiaire criteres = new Stagiaire(nom,prenom,departement,parcours,numPromo,contratPro,annee);
			List<Stagiaire> matches = ArbreBinaireBin.recherche(criteres);
		
			observableStagiaires.clear();
			observableStagiaires.addAll(matches);
		}
	}	
	
	public ToggleButton setToggleRecherche() {
		ToggleButton tb = new ToggleButton("Recherche Simple");
		
		//tb.setOn
		tb.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		if(tb.isSelected()) {
        			tb.setText("Recherche Avancée");
        			criteresAvancees.setDisable(false);
        		}else {
        			tb.setText("Recherche Simple");
        			criteresAvancees.setDisable(true);
        		}
        		
        	}
        });
		
		return tb;
	}
	
	
	public GridPane setCriteres() {
		GridPane gp = new GridPane();
		criteresAvancees = new HBox();
		HBox hboxDepartement = new HBox();
		HBox hboxParcours = new HBox();
		HBox hboxContrat = new HBox(10);
		HBox hboxAnnee = new HBox();
		
		rechercheAvancee = setToggleRecherche();
		
		Label lStagiaire = new Label("Stagiaire   ");
		tfNom = CompoStag.setStringTf("Nom...");
		tfPrenom = CompoStag.setStringTf("Prenom...");
		
		Label lDepartement = new Label("Departement   ");
		tfDepartement = CompoStag.setCodePostalTf("ex : 44");
		
		Label lParcours = new Label("Parcours");
		cbParcours = CompoStag.setParcours();
		tfNumPromo = CompoStag.setNumPromo("N° promo");
		
		Label lContratPro = new Label("Contrat Pro?");
		cbContratPro = setContratPro();
		
		Label lAnnee = new Label("Annee graduation");
		tfAnnee = CompoStag.setAnneeTf("");
		
		hboxDepartement.getChildren().addAll(lDepartement,tfDepartement);
		hboxParcours.getChildren().addAll(lParcours,cbParcours,tfNumPromo);
		hboxContrat.getChildren().addAll(lContratPro,cbContratPro);
		hboxAnnee.getChildren().addAll(lAnnee,tfAnnee);
		criteresAvancees.getChildren().addAll(hboxDepartement,hboxParcours,hboxContrat,hboxAnnee);
		
		gp.addRow(0, lStagiaire, tfNom, tfPrenom, rechercheAvancee);
		gp.add(criteresAvancees,0,1,4,1);
		
		criteresAvancees.setDisable(true);
		
		return gp;
	}
	
	public TableView setResultats() throws Exception {
		observableStagiaires = FXCollections.observableArrayList(ArbreBinaireBin.recherche(Stagiaire.criteresVide()));
		TableView<Stagiaire> tv = new TableView<>(observableStagiaires);
		
		TableColumn<Stagiaire, String> colNom = new TableColumn<>("Nom");
		colNom.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("nom"));
		
		TableColumn<Stagiaire, String> colPrenom = new TableColumn<>("Prenom");
		colPrenom.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("prenom"));
		
		TableColumn<Stagiaire, String> colDepartement = new TableColumn<>("Departement");
		colDepartement.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("departement"));
		
		TableColumn<Stagiaire, String> colParcours = new TableColumn<>("Parcours");
		colParcours.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("parcours"));
		
		TableColumn<Stagiaire, String> colNumPromo = new TableColumn<>("N° Promo");
		colNumPromo.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("numPromo"));
		
		TableColumn<Stagiaire, String> colContratPro = new TableColumn<>("Contrat pro?");
		colContratPro.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("contratPro"));
		
		TableColumn<Stagiaire, String> colAnnee = new TableColumn<>("Annee");
		colAnnee.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("annee"));
		
		
		tv.getColumns().addAll(colNom, colPrenom, colDepartement, colParcours,colNumPromo,colContratPro,colAnnee);
		
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		tv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Stagiaire>() {
			
			@Override
			public void changed(ObservableValue<? extends Stagiaire> observableValue, Stagiaire oldValue, Stagiaire newValue) {
				if(newValue == null) {
					bMAJstagiaire.setDisable(true);
					bSupprimerStagiaire.setDisable(true);
				
				}else{
					bMAJstagiaire.setDisable(false);
					bSupprimerStagiaire.setDisable(false);
					
					selectedStagiaire = newValue;
				}
			}
		});
		
		return tv;
	}
	
	

}
