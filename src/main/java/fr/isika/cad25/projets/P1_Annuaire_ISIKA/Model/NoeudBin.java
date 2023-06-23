package fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class NoeudBin {
	//fonctionnalités:
	//ajouter un stagiaire 		OK
	//get Stagiaire avec pos 	OK
	//functionnalité recherchje 
	
	//??
	
	static final int TAILLE_NOEUD = Stagiaire.TAILLE_STAGIAIRE + 3 * 4;
	static final int POS_RELATIV_FILS_GAUCHE = TAILLE_NOEUD - 12;
	static final int POS_RELATIV_FILS_DROIT = TAILLE_NOEUD-8;
	static final int POS_RELATIV_FRERE = TAILLE_NOEUD-4;

	public int posStagiaire;
	
	public Stagiaire stagiaire;
	public int posFilsGauche;
	public int posFilsDroit;
	public int posFrere;
	
	public NoeudBin(int posStagiaire, Stagiaire stagiaire, int posFilsGauche, int posFilsDroit, int posFrere) {
		this.posStagiaire = posStagiaire;
		this.stagiaire = stagiaire;
		this.posFilsGauche = posFilsGauche;
		this.posFilsDroit = posFilsDroit;
		this.posFrere = posFrere;
	}
	
	public static void ecrireNvNoeud(Stagiaire stagiaire, RandomAccessFile raf) throws Exception{
		//raf.writeInt((int)raf.getFilePointer());
		stagiaire.ecrireBin(raf);
		raf.writeInt(-1);
		raf.writeInt(-1);
		raf.writeInt(-1);
	}
	
	public static Stagiaire getStagiaire(RandomAccessFile raf) throws Exception{
		Stagiaire cible = BinStagiaire.lireStagiaire(raf);
		
		return cible;
	}
	
	public static void ajouterStagiaire(Stagiaire stagiaire, RandomAccessFile raf) throws Exception{
		NoeudBin currentNoeud = lireNoeud(raf);
		int nomComparaison = currentNoeud.stagiaire.getNom().trim().compareTo(stagiaire.getNom());
		
		if(nomComparaison == 0) {
			if(currentNoeud.posFrere ==-1) {
				currentNoeud.setLien(raf,(int)raf.length(),POS_RELATIV_FRERE);
				raf.seek(raf.length());
				ecrireNvNoeud(stagiaire,raf);
			}else {
				raf.seek(currentNoeud.posFrere);
				ajouterStagiaire(stagiaire,raf);				
			}
		}else if(nomComparaison>0) {
			if(currentNoeud.posFilsGauche == -1) {
				currentNoeud.setLien(raf,(int)raf.length(),POS_RELATIV_FILS_GAUCHE);
				raf.seek(raf.length());
				ecrireNvNoeud(stagiaire,raf);
			}else {
				raf.seek(currentNoeud.posFilsGauche);
				ajouterStagiaire(stagiaire,raf);
			}
		}else {
			if(currentNoeud.posFilsDroit == -1) {
				currentNoeud.setLien(raf,(int)raf.length(),POS_RELATIV_FILS_DROIT);
				raf.seek(raf.length());
				ecrireNvNoeud(stagiaire,raf);
			}else {
				raf.seek(currentNoeud.posFilsDroit);
				ajouterStagiaire(stagiaire,raf);
			}
		}		
	}
	
	public static NoeudBin lireNoeud(RandomAccessFile raf) throws Exception {
		NoeudBin noeud = new NoeudBin( (int)raf.getFilePointer(),
										BinStagiaire.lireStagiaire(raf),
										raf.readInt(),
										raf.readInt(),
										raf.readInt());
		return noeud;
	}
	
	public void setLien(RandomAccessFile raf, int position, int posRelativ) throws Exception {
		raf.seek(this.posStagiaire+posRelativ);
		raf.writeInt(position);
	}
	
	public static void recherche(RandomAccessFile raf, List<Stagiaire> matches, Stagiaire criteres) throws Exception{
		NoeudBin noeud = lireNoeud(raf);
		
		boolean switchGauche = true;
		boolean switchDroit = true;
		boolean switchFrere = true;

		if(!noeud.stagiaire.getNom().matches("^"+criteres.getNom()+".*")) {
			switchGauche = noeud.stagiaire.getNom().compareTo(criteres.getNom()) >= 0? true:false;
			switchDroit = noeud.stagiaire.getNom().compareTo(criteres.getNom()) <= 0? true:false;
			switchFrere = false;
		}
				
		if(noeud.posFilsGauche!=-1 && switchGauche) {
			raf.seek(noeud.posFilsGauche);
			recherche(raf,matches,criteres);
		}
		
		if(noeud.stagiaire.doMatch(criteres)) 
			matches.add(noeud.stagiaire);
		
		if(noeud.posFrere!=-1 && switchFrere) {
			raf.seek(noeud.posFrere);
			recherche(raf,matches,criteres);
		}
		
		if(noeud.posFilsDroit!=-1 && switchDroit) {
			raf.seek(noeud.posFilsDroit);
			recherche(raf,matches,criteres);
		}
		
	}
	
	public static void rechercheRemplace(RandomAccessFile raf, Stagiaire cible, Stagiaire miseAjour) throws Exception{
		NoeudBin noeud = lireNoeud(raf);
				
		if(noeud.stagiaire.doMatch(cible)) { 
			raf.seek(noeud.posStagiaire);
			miseAjour.ecrireBin(raf);
			
		}else if(noeud.posFilsGauche!=-1 && noeud.stagiaire.getNom().compareTo(cible.getNom()) > 0) {
			raf.seek(noeud.posFilsGauche);
			rechercheRemplace(raf,cible,miseAjour);
			
		}else if(noeud.posFrere!=-1 && noeud.stagiaire.getNom().compareTo(cible.getNom()) == 0) {
			raf.seek(noeud.posFrere);
			rechercheRemplace(raf,cible,miseAjour);
			
		}else if(noeud.posFilsDroit!=-1 && noeud.stagiaire.getNom().compareTo(cible.getNom()) < 0) {
			raf.seek(noeud.posFilsDroit);
			rechercheRemplace(raf,cible,miseAjour);
		}
	
	}
	
	public static int rechercheSupprime(RandomAccessFile raf, Stagiaire cible) throws Exception{
		NoeudBin noeud = lireNoeud(raf);
				
		if(noeud.stagiaire.doMatch(cible)) { 
			raf.seek(noeud.posStagiaire);
			return supprimerRacine(raf);
			
			
		} else if(noeud.posFilsGauche!=-1 && noeud.stagiaire.getNom().compareTo(cible.getNom()) > 0) {
			raf.seek(noeud.posFilsGauche);
			noeud.posFilsGauche = rechercheSupprime(raf,cible);
			noeud.setLien(raf,noeud.posFilsGauche,POS_RELATIV_FILS_GAUCHE);
			
		}else if(noeud.posFrere!=-1 && noeud.stagiaire.getNom().compareTo(cible.getNom()) == 0) {
			raf.seek(noeud.posFrere);
			noeud.posFrere = rechercheSupprime(raf,cible);
			noeud.setLien(raf,noeud.posFrere,POS_RELATIV_FRERE);
			
		}else if(noeud.posFilsDroit!=-1 && noeud.stagiaire.getNom().compareTo(cible.getNom()) < 0) {
			raf.seek(noeud.posFilsDroit);
			noeud.posFilsDroit = rechercheSupprime(raf,cible);
			noeud.setLien(raf,noeud.posFilsDroit,POS_RELATIV_FILS_DROIT);
		}
		
		return noeud.posStagiaire;

	} 
	
	public static int supprimerRacine(RandomAccessFile raf)throws Exception{
		NoeudBin noeud = lireNoeud(raf);
		
		if(noeud.posFilsGauche == -1)
			return noeud.posFilsDroit;
		
		if(noeud.posFilsDroit == -1)
			return noeud.posFilsGauche;
		
		raf.seek(noeud.posFilsDroit);
		Stagiaire nouvelleRacine = remplacant(raf);
		
		raf.seek(noeud.posStagiaire);
		nouvelleRacine.ecrireBin(raf);
		
		raf.seek(noeud.posFilsDroit);
		noeud.posFilsDroit = rechercheSupprime(raf,nouvelleRacine);
		noeud.setLien(raf,noeud.posFilsDroit,POS_RELATIV_FILS_DROIT);
		
		return noeud.posStagiaire;
	}	
	
	public static Stagiaire remplacant(RandomAccessFile raf) throws Exception{
		NoeudBin noeud = lireNoeud(raf);
		
		if(noeud.posFilsGauche == -1) {
			return noeud.stagiaire;
		}
		
		raf.seek(noeud.posFilsGauche);
		return remplacant(raf);
	}
	
	public static void printNoeuds(RandomAccessFile raf) throws Exception{
		NoeudBin noeud = lireNoeud(raf);
		
		if(noeud.posFilsGauche != -1){
			raf.seek(noeud.posFilsGauche);
			printNoeuds(raf);
		}		
		System.out.println(noeud.posStagiaire + "\t" + 
							noeud.stagiaire.getNom().toUpperCase()+"\t\t\t" + 
							noeud.stagiaire.getPrenom());
		
		if(noeud.posFrere != -1) {
			raf.seek(noeud.posFrere);
			printNoeuds(raf);
		}
		
		if(noeud.posFilsDroit != -1){
			raf.seek(noeud.posFilsDroit);
			printNoeuds(raf);
		}
		
	}
	
}