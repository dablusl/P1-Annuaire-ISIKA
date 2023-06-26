package fr.isika.cad25.projets.P1_Annuaire_ISIKA.ViewModel;

import java.util.regex.Pattern;

import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.User;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.UsersIO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CreationDeCompte {
	private Stage stage;
	private TextField txtNom;
	private TextField txtPrenom;
	private TextField txtEmail;
	private PasswordField txtMdp;
	private PasswordField txtVerifMdp;
	public TextField getTxtNom() {
		return txtNom;
	}
	public void setTxtNom(TextField txtNom) {
		this.txtNom = txtNom;
	}
	public TextField getTxtPrenom() {
		return txtPrenom;
	}
	public void setTxtPrenom(TextField txtPrenom) {
		this.txtPrenom = txtPrenom;
	}
	public TextField getTxtEmail() {
		return txtEmail;
	}
	public void setTxtEmail(TextField txtEmail) {
		this.txtEmail = txtEmail;
	}
	public CreationDeCompte(Stage stage) {
		this.stage = stage;
	}
	private boolean isEmailValid(String email) {
		String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}
	public void show() {
		BorderPane root = new BorderPane();
		StackPane topPane = new StackPane();
		StackPane centerPane = new StackPane();
		GridPane gridPane = new GridPane();
		Label labelCreation = new Label("Création de compte");
		Label labelNom = new Label("Nom : ");
		Label labelPrenom = new Label("Prenom : ");
		Label labelEmail = new Label("Adresse mail : ");
		Label labelMdp = new Label("Mot de passe : ");
		Label labelVerifMdp = new Label("Retaper le mot de passe : ");
		Label labelInvalideEmail = new Label();
		Label labelInvalideMDP = new Label();
		txtNom = new TextField();
		txtPrenom = new TextField();
		txtEmail = new TextField();
		txtMdp = new PasswordField();
		txtVerifMdp = new PasswordField();
		Button bConfirmer = new Button("Confirmer");
		bConfirmer.setOnAction(e -> {
			String prenom = txtPrenom.getText();
			String nom = txtNom.getText();
			String email = txtEmail.getText();
			String password = txtMdp.getText();
			String verifPwd = txtVerifMdp.getText();
			// Créez un nouvel objet User avec les informations saisies
			User user = new User(nom, prenom, email, password);
			/*
			user.setPrenom(prenom);
			user.setNom(nom);
			user.setEmail(email);
			user.setPassword(password);
			*/
			if (!isEmailValid(email)) {
				// Afficher un message d'erreur ou effectuer une action appropriée
				System.out.println("Adresse e-mail invalide");
				labelInvalideEmail.setText("Format email incorrect, veuiller reformuler.");
				return;
			}
			if (password.isEmpty()) {
				labelInvalideMDP.setText("Veuillez remplir les champs de mot de passe.");
				return;
			}
			if (!password.equals(verifPwd)) {
				// Les mots de passe ne correspondent pas
				// Afficher un message d'erreur
				labelInvalideMDP.setText("Les mots de passe ne correspondent pas, veuillez retaper.");
				// Effacer les champs de mot de passe
				txtMdp.clear();
				txtVerifMdp.clear();
				return;
			}
			// Appelez la méthode ecrireUsers de la classe UsersIO pour stocker
			// l'utilisateur dans le fichier binaire
			int resultat = UsersIO.ecrireUsers(user);		
		InscriptionEnregistree inscriptionEnregistree = new InscriptionEnregistree(stage, this);
		inscriptionEnregistree.show();
		});
		labelCreation.setFont(new Font(30));
		gridPane.add(labelNom, 0, 0);
		gridPane.add(labelPrenom, 0, 1);
		gridPane.add(labelEmail, 0, 2);
		gridPane.add(labelMdp, 0, 3);
		gridPane.add(labelVerifMdp, 0, 4);
		gridPane.add(txtNom, 1, 0);
		gridPane.add(txtPrenom, 1, 1);
		gridPane.add(txtEmail, 1, 2);
		gridPane.add(txtMdp, 1, 3);
		gridPane.add(txtVerifMdp, 1, 4);
		gridPane.add(bConfirmer, 2, 6);
		gridPane.add(labelInvalideEmail, 0, 7);
		gridPane.add(labelInvalideMDP, 0, 8);
		gridPane.setHgap(10); // Espacement horizontal entre les colonnes
		gridPane.setVgap(15); // Espacement vertical entre les lignes
		topPane.getChildren().add(labelCreation);
		centerPane.getChildren().add(gridPane);
		root.setTop(topPane);
		root.setCenter(centerPane);
		centerPane.setPadding(new Insets(10)); // espacer le centerPane de la topPane de 10 pixels
		Scene scene = new Scene(root, 500, 435);
		stage.setScene(scene);
		stage.show();
	}
}
