package fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class ArbreBinaireBin {
	
	static final String path = "src/data/stagiaires.bin";
	static RandomAccessFile raf;
	static int posRacine=0;
	
	
	
	public static void ajouterStagiaire(Stagiaire stagiaire) throws Exception {
		raf = new RandomAccessFile(path,"rw");
		raf.seek(posRacine);
		
		if(raf.length()==0) {
			NoeudBin.ecrireNvNoeud(stagiaire,raf);
		}else {
			NoeudBin.ajouterStagiaire(stagiaire,raf);
		}
		raf.close();
	}
	
	public static Stagiaire getStagiaire(int position) throws Exception{
		raf = new RandomAccessFile(path,"r");
		
		if(raf.length()==0) {
			raf.close();
			return null;
		}
		
		raf.seek(position+4);
		Stagiaire cible = NoeudBin.getStagiaire(raf);

		raf.close();
		return cible;
	}
	
	public static List<Stagiaire> recherche(Stagiaire criteres)throws Exception{
		raf = new RandomAccessFile(path,"r");
		List<Stagiaire> matches = new ArrayList<>();
		raf.seek(posRacine);
		
		if(raf.length()>0)
			NoeudBin.recherche(raf,matches,criteres);
		
		raf.close();
		return matches;
	}
	
	public static void rechercheRemplace(Stagiaire cible, Stagiaire miseAjour) throws Exception{
		raf = new RandomAccessFile(path,"rw");
		raf.seek(posRacine);
		
		if(raf.length()>0)
			NoeudBin.rechercheRemplace(raf,cible,miseAjour);
		
		raf.close();
	}
	
	public static void rechercheSupprime(Stagiaire cible) throws Exception{
		raf = new RandomAccessFile(path,"rw");
		raf.seek(posRacine);
		
		if(raf.length()>0)
			NoeudBin.rechercheSupprime(raf,cible);
		
		raf.close();
	}


	public static void printArbre() throws Exception {
		raf = new RandomAccessFile(path,"rw");
		raf.seek(posRacine);
		
		NoeudBin.printNoeuds(raf);
		raf.close();
	}
	
	
	
	
}
