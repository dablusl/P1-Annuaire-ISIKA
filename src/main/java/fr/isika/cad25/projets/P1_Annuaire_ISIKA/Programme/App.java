package fr.isika.cad25.projets.P1_Annuaire_ISIKA.Programme;


import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Login;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.ViewModel.PaneAjoutStagiaire;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.ViewModel.PaneRechercheStagiaire;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
	
	public static void main(String[] args) {
		launch(); 
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Login login = new Login(stage);
		login.show();
	}
}