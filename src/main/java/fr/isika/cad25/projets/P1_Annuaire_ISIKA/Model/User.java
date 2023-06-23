package fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model;

import java.io.RandomAccessFile;
public class User {
	
	static final int TAILLE_NOM = 20;
	static final int TAILLE_PRENOM = 20;
	static final int TAILLE_EMAIL = 30;
	static final int TAILLE_PASSWORD = 16;
	
	static final int TAILLE_USER = 2 +TAILLE_NOM*2+TAILLE_PRENOM*2+TAILLE_EMAIL*2+TAILLE_PASSWORD*2;
	
	
	char droit='u';
	String nom;
	String prenom;
	String email;
	String password;
	
	
	public User(String nom, String prenom, String email, String password) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void ecrireBin(RandomAccessFile raf) {
		try {
			String nomFormate = this.nom;
			String prenomFormate = this.prenom;
			String emailFormate = this.email;
			String passwordFormate = this.password;
		
			for(int i = this.nom.length(); i<TAILLE_NOM ;i++)
				nomFormate += " ";
		
			for(int i = this.prenom.length(); i<TAILLE_PRENOM ; i++)
				prenomFormate += " ";
		
			for(int i = this.email.length(); i<TAILLE_EMAIL;i++)
				emailFormate += " ";
		
			for(int i = this.password.length(); i < TAILLE_PASSWORD ; i++)
				passwordFormate += " ";
			
			raf.writeChars(emailFormate);
			raf.writeChars(nomFormate);
			raf.writeChars(prenomFormate);
			raf.writeChars(passwordFormate);
			raf.writeChar(droit);
		}
		catch(Exception e) {
			
		}
	}
		
	

}
