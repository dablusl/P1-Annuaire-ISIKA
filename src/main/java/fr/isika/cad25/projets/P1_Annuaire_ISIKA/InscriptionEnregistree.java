package fr.isika.cad25.projets.P1_Annuaire_ISIKA;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class InscriptionEnregistree {
	private Stage stage;
	private CreationDeCompte creationDeCompte;
	public InscriptionEnregistree(Stage stage, CreationDeCompte creationDeCompte) {
		this.stage = stage;
		this.creationDeCompte = creationDeCompte;
	}
	public void show() {
		VBox root = new VBox(30);
		root.setAlignment(Pos.CENTER);
		Label labelConfirmation = new Label();
		Button bRetourAccueil = new Button("Retour à la page de connexion");
		bRetourAccueil.setOnAction(e -> {
			Login login = new Login(stage);
			login.show();
		});
		TextField txtNom = creationDeCompte.getTxtNom();
		TextField txtPrenom = creationDeCompte.getTxtPrenom();
		
		//TextField txtEmail = creationDeCompte.getTxtEmail();
		String nom = txtNom.getText();
		String prenom = txtPrenom.getText();
		
		
		labelConfirmation.setText("Bonjour " + nom + " " + prenom + ". Votre inscription est validée. Veuillez retourner sur la page Login pour vous connecter ! ");
		root.getChildren().addAll(labelConfirmation, bRetourAccueil);
		Scene scene = new Scene(root, 800, 400);
		stage.setScene(scene);
		stage.show();
	}
}
