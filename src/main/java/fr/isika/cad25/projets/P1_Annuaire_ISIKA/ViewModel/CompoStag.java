package fr.isika.cad25.projets.P1_Annuaire_ISIKA.ViewModel;

import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.Stagiaire;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class CompoStag {
	
	private final static int TAILLE_NOM = Stagiaire.TAILLE_NOM;
	private final static int TAILLE_PRENOM = Stagiaire.TAILLE_PRENOM;
	private final static int TAILLE_CP = Stagiaire.TAILLE_CP;
	
	public static ChoiceBox<String> setParcours() {
		ChoiceBox<String> cb = new ChoiceBox<String>();
		
		cb.getItems().addAll("","AI","CDA","ATOD","BOBI","AROBAS");
		cb.getSelectionModel().select(0);		
		
		return cb;
	}
	
	public static TextField setStringTf(String prompt) {
		TextField tf = new TextField();
		tf.setPromptText(prompt);
		tf.setPrefWidth(200);
		
		Tooltip restrictionTooltip = new Tooltip("Seulement lettres, - et ' permis.\nEvitez les caractères spéciaux");
		Tooltip.install(tf,restrictionTooltip);
		
		
		tf.textProperty().addListener(new ChangeListener<String>() {
			
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("[A-Za-zéèê\\-' ]*")) {
		            tf.setStyle("-fx-text-fill: red;");
		        }else {
		        	tf.setStyle("-fx-text-fill: black;");
		        }
		        
		        if(newValue.length() > TAILLE_PRENOM) 
		        	tf.setText(newValue.substring(0,TAILLE_PRENOM));
		        
		    }
		});
		
		return tf;
	}
	
	public static TextField setNumPromo(String prompt) {
		TextField tf = new TextField();
		tf.setPromptText(prompt);
		tf.setPrefWidth(70);
		
		Tooltip restrictionTooltip = new Tooltip("saisir entier positif");
		Tooltip.install(tf,restrictionTooltip);
		
		tf.textProperty().addListener(new ChangeListener<String>() {
			
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		    	
		        if (!newValue.matches("\\d*")) {
		            tf.setStyle("-fx-text-fill: red;");
		        }else {
		        	tf.setStyle("-fx-text-fill: black;");
		        }

		    }
		});
		
		return tf;
	}
	
	public static TextField setCodePostalTf(String prompt) {
		TextField tf = new TextField();
		tf.setPromptText(prompt);
		tf.setPrefWidth(50);
		
		Tooltip restrictionTooltip = new Tooltip("2 chiffres");
		Tooltip.install(tf,restrictionTooltip);
		
		tf.textProperty().addListener(new ChangeListener<String>() {
			
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		    	
		        if (!newValue.matches("\\d*")) {
		            tf.setStyle("-fx-text-fill: red;");
		        }else {
		        	tf.setStyle("-fx-text-fill: black;");
		        }
		        
		        if(newValue.length() > TAILLE_CP) 
		        	tf.setText(newValue.substring(0,TAILLE_CP));
		    }
		});
		
		return tf;
	}
	
	public static TextField setAnneeTf(String prompt) {
		TextField tf = new TextField();
		tf.setPromptText(prompt);
		tf.setPrefWidth(80);
		
		tf.textProperty().addListener(new ChangeListener<String>() {
			
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		    	
		        if (!newValue.matches("\\d*")) {
		            tf.setStyle("-fx-text-fill: red;");
		        }else {
		        	tf.setStyle("-fx-text-fill: black;");
		        }
		    }
		});
		
		return tf;
		
	}
}
