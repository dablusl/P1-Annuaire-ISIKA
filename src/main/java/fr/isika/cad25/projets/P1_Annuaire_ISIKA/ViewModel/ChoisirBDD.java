package fr.isika.cad25.projets.P1_Annuaire_ISIKA.ViewModel;

import java.io.File;
import java.io.IOException;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.AnnuaireIO;
import fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class ChoisirBDD extends Application{
	
	public static User user;
	
	//Dans une méthode main, on lance le thread JavaFX Application
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	//On rédéfinit la méthode start de la classe Application
	@Override
	public void start( final Stage stage) throws Exception{
		//On instancie une VBox:
		//HBox root = new HBox(20);
		HBox myHBox1 = new HBox ();
		HBox myHBox2 = new HBox (20);
		
		
		//On instancie un bouton et un label...
		final Label label = new Label("\n \n Voulez-vous importer?");
		Button btn1 = new Button("Oui");
		Button btn2 = new Button("Non");
		Button btn3 = new Button("Annuler");
		
		
		myHBox1.getChildren().add(label);
		myHBox2.getChildren().addAll(btn1,btn2,btn3);
	
//		root.getChildren().addAll(label,btn1,btn2);
//		root.setAlignment(Pos.CENTER);
	     StackPane maStack = new StackPane();
	     maStack.getChildren().addAll(myHBox1,myHBox2);
	   
	     myHBox1.setAlignment(Pos.TOP_CENTER);
	     myHBox2.setAlignment(Pos.CENTER);
	   
		//Styling
		label.setStyle("-fx-font: normal bold 20px 'serif' ");
		btn1.setStyle("-fx-font: normal bold 15px 'blue' ; -fx-cursor: hand");
		btn2.setStyle("-fx-font: normal bold 15px 'blue' ; -fx-cursor: hand");
		btn3.setStyle("-fx-font: normal bold 15px 'blue' ; -fx-cursor: hand");

		btn3.setVisible(true);
		btn3.setTextFill(Color.DARKGREEN);
		
		Scene scene = new Scene(maStack,400,200);
		stage.setScene(scene);
		scene.setFill(Color.DARKBLUE);
		stage.setTitle("Confirmation");
		
		stage.show();
		btn1.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				String desktopPath = System.getProperty("user.home");
				File initDir = new File(desktopPath);
				fileChooser.setInitialDirectory(initDir);
				File f = fileChooser.showOpenDialog(stage.getOwner());
				if (f != null){
					label.setText(f.getAbsolutePath());
				}
				System.out.println(f.getAbsolutePath());
				AnnuaireIO.initAnnuaire(f.getAbsolutePath());
				openPaneRechercheStagiaire(arg0);
				
			}
			private void openPaneRechercheStagiaire(ActionEvent arg0) {
				try
				{
					Scene scene2 = new Scene(new PaneRechercheStagiaire(user));
					Stage secondStage= (Stage)((Node)(arg0.getSource())).getScene().getWindow();
					secondStage.setScene(scene2);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		btn2.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				//System.out.println("non");
				String filePath = "src/data/stagiaires.bin";
				// Create the File object
				File file = new File(filePath);
				try {
					// Create the file
					boolean isFileCreated = file.createNewFile();
				
					if (isFileCreated)
					{
						System.out.println("Empty .bin file created successfully.");
					}
					else
					{
						System.out.println("File already exists.");
					}
					openPaneRechercheStagiaire(arg0);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			private void openPaneRechercheStagiaire(ActionEvent arg0) {
				try
				{
					Scene scene2 = new Scene(new PaneRechercheStagiaire(user));
					Stage secondStage= (Stage)((Node)(arg0.getSource())).getScene().getWindow();
					secondStage.setScene(scene2);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
          btn3.setOnAction(new EventHandler<ActionEvent>(){
			
	        public void handle(ActionEvent event) {
	  
	 
		Stage secondStage= (Stage)((Node)(event.getSource())).getScene().getWindow();
		secondStage.close();
		//System.out.println("Annuler");	
			}
		
		});
		
	}
}