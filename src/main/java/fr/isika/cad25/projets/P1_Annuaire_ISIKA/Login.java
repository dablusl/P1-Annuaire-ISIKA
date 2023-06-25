package fr.isika.cad25.projets.P1_Annuaire_ISIKA;

import java.io.File;
import java.io.IOException;

import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.User;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.UsersIO;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.ViewModel.PaneRechercheStagiaire;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
public class Login {
	private static Stage stage;
	private TextField txtEmail;
	private PasswordField txtMdp;
	
	public Login(Stage stage) {
		this.stage = stage;
	}
	
	public void show() {
		BorderPane root = new BorderPane();
		StackPane topPane = new StackPane();
		topPane.setPrefSize(800, 75);
		StackPane centerPane = new StackPane();
		centerPane.setPrefSize(400, 200);
		StackPane bottomPane = new StackPane();
		bottomPane.setPrefSize(400, 250);
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(20);
		HBox hEmail = new HBox();
		hEmail.setAlignment(Pos.CENTER);
		HBox hMdp = new HBox();
		hMdp.setAlignment(Pos.CENTER);
		HBox hPremiereCo = new HBox();
		hPremiereCo.setAlignment(Pos.CENTER);
		Button bValider = new Button("Valider");
		Button bPremiereCo = new Button("Première connexion ? S'incrire");
		bValider.setOnAction(e -> {
			String email = txtEmail.getText();
			String password = txtMdp.getText();
			
			//changer methode pour un qui renvoie le user
			boolean authentifie = UsersIO.verifierAuthentification(email, password);
			User user = new User("zakaria","abdel",email,password);
			
			
			if (authentifie) {
				
				String filePath = "src/data/stagiaires.bin";
				File file = new File(filePath);
				
				try {
	
				
					if (!file.exists())
					{	
						Stage currentStage = (Stage)Login.stage.getScene().getWindow();
						ChoisirBDD.user = user;
						ChoisirBDD choisirBDD = new ChoisirBDD();
						choisirBDD.start(currentStage);
					}
					else
					{
						Scene scene2 = new Scene(new PaneRechercheStagiaire(user));
						Stage secondStage= (Stage)Login.stage.getScene().getWindow();
						secondStage.setScene(scene2);;
						secondStage.show();
					}
					
				}
				catch (Exception el) {
					el.printStackTrace();
				}
				
				
				
				
			} else {
				// Authentification échouée, afficher un message d'erreur				
			}
			
		});
		bPremiereCo.setOnAction(e -> {
			CreationDeCompte creationdecompte = new CreationDeCompte(stage);
			creationdecompte.show();
		});
		Label labelLogin = new Label("Login");
		// modification de la taille de police
		labelLogin.setFont(new Font(30));
		Label labelEmail = new Label("Adresse mail : ");
		txtEmail = new TextField();
		Label labelMdp = new Label("Mot de passe : ");
		txtMdp = new PasswordField();
		hEmail.getChildren().addAll(labelEmail, txtEmail);
		hMdp.getChildren().addAll(labelMdp, txtMdp);
		hPremiereCo.getChildren().add(bPremiereCo);
		vbox.getChildren().addAll(hEmail, hMdp, bValider);
		centerPane.getChildren().add(vbox);
		root.setCenter(vbox);
		bottomPane.getChildren().add(hPremiereCo);
		root.setBottom(bottomPane);
		topPane.getChildren().add(labelLogin);
		root.setTop(topPane);
		Scene scene = new Scene(root, 400, 335);
		stage.setScene(scene);
		stage.show();
	}
}
