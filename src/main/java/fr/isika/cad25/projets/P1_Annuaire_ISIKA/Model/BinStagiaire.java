package fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model;

import java.io.IOException;
import java.io.RandomAccessFile;

public class BinStagiaire {
	
	public static Stagiaire lireStagiaire(RandomAccessFile raf) throws IOException {
		return new Stagiaire(	lireNom(raf),
								lirePrenom(raf),
								lireDepartement(raf),
								lireParcours(raf),
								lireNumPromo(raf),
								lireContratPro(raf),
								lireAnnee(raf));		
	}
	
	public static String lireNom(RandomAccessFile raf) throws IOException {
		String nom = "";
		
		for(int i = 0; i<Stagiaire.TAILLE_NOM;i++) 
			nom+=raf.readChar(); 
		
		return nom.trim();		
	}
	
	public static String lirePrenom(RandomAccessFile raf) throws IOException {
		String prenom = "";
		
		for(int i = 0; i<Stagiaire.TAILLE_PRENOM;i++) 
			prenom+=raf.readChar(); 
		
		return prenom.trim();		
	}
	
	public static String lireDepartement(RandomAccessFile raf) throws IOException {
		String cp= "";
		
		for(int i = 0; i<Stagiaire.TAILLE_CP;i++) 
			cp+=raf.readChar(); 
		
		return cp.trim();		
	}

	public static String lireParcours(RandomAccessFile raf) throws IOException {
		String parcours= "";
		
		for(int i = 0; i<Stagiaire.TAILLE_PARCOURS;i++) 
			parcours+=raf.readChar(); 
		
		return parcours.trim();		
	}
	
	public static short lireNumPromo(RandomAccessFile raf) throws IOException {
		return raf.readShort();
	}
	
	public static char lireContratPro(RandomAccessFile raf) throws IOException {
		return raf.readChar();
	}
	
	public static short lireAnnee(RandomAccessFile raf) throws IOException {
		return raf.readShort();
	}
	
	
}
