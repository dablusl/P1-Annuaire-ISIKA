package fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model;

import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stagiaire {
	
	//short 		2 x bytes
	//char  		2 x bytes
	//int			4 x bytes
	//boolean		1 x bit
	
	public static final int TAILLE_NOM = 25;
	public static final int TAILLE_PRENOM = 25;
	public static final int TAILLE_CP=2;
	public static final int TAILLE_PARCOURS = 6;
	
	static final int TAILLE_STAGIAIRE = TAILLE_NOM*2+TAILLE_PRENOM*2+TAILLE_CP*2+TAILLE_PARCOURS*2+2+2+2 ;
	
	private String nom;
	private String prenom;
	private String departement;
	private String parcours;
	private short numPromo;
	private char contratPro; //O/N
	private short annee;
	
	public Stagiaire() {};
	
	public Stagiaire(String nom, String prenom, String departement, String parcours, short numPromo,char contratPro,
			short annee) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.parcours = parcours;
		this.numPromo = numPromo;
		this.contratPro = contratPro;
		this.annee = annee;
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
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public String getParcours() {
		return parcours;
	}
	public void setParcours(String parcours) {
		this.parcours = parcours;
	}
	public short getNumPromo() {
		return numPromo;
	}
	public void setNumPromo(short numPromo) {
		this.numPromo = numPromo;
	}
	public char getContratPro() {
		return contratPro;
	}
	public void setContratPro(char contratPro) {
		this.contratPro = contratPro;
	}
	public short getAnnee() {
		return annee;
	}
	public void setAnnee(short annee) {
		this.annee = annee;
	}
	@Override
	public String toString() {
		return "Stagiaire [nom=" + nom + ", prenom=" + prenom + ", departement=" + departement + ", parcours="
				+ parcours + ", numPromo=" + numPromo + ", contratPro=" + contratPro + ", annee=" + annee + "]";
	}
	
	public int compareTo(Stagiaire stag) {
		
		if(this.getNom().compareTo(stag.getNom())!=0) 
			return this.getNom().compareTo(stag.getNom());
		
		if(this.getPrenom().compareTo(stag.getPrenom())!=0) 
			return this.getPrenom().compareTo(stag.getPrenom());
		
		if(this.getParcours() == "AI") return -1;
		
		return 1;
		
	}
	
	public void ecrireBin(RandomAccessFile raf) {
		try {
			
			String nomFormate = this.nom;
			String prenomFormate = this.prenom;
			String parcoursFormate = this.parcours;
			
			for(int i = this.nom.length() ; i<TAILLE_NOM;i++)
				nomFormate +=" ";
			
			for(int i = this.prenom.length() ; i<TAILLE_PRENOM;i++)
				prenomFormate +=" ";
			
			for(int i = this.parcours.length() ; i<TAILLE_PARCOURS;i++)
				parcoursFormate +=" ";
			
			if(nomFormate.length()>TAILLE_NOM) nomFormate = nomFormate.substring(0,TAILLE_NOM);
			if(prenomFormate.length()>TAILLE_PRENOM) prenomFormate = prenomFormate.substring(0,TAILLE_PRENOM);			
			
			raf.writeChars(nomFormate);
			raf.writeChars(prenomFormate);
			raf.writeChars(this.departement);
			raf.writeChars(parcoursFormate);
			raf.writeShort(this.numPromo);
			raf.writeChar(this.contratPro);
			raf.writeShort(this.annee);
		}
		catch(Exception e) {
			
		}
	
	}
	
	public boolean doMatch(Stagiaire criteres) {
	
		if(!compare(this.nom,criteres.getNom())) return false;
		
		if(!compare(this.prenom,criteres.getPrenom())) return false;
		if(!compare(this.departement,criteres.getDepartement())) return false;
		if(!compare(this.parcours,criteres.getParcours())) return false;
		
		String critereNumPromo = criteres.getNumPromo()==-1?"":criteres.getNumPromo()+"";
		if(!compare(this.numPromo+"",critereNumPromo)) return false;
		
		String critereContratPro = criteres.getContratPro() == 'v'?"":criteres.getContratPro()+"";
		if(!compare(this.contratPro+"",critereContratPro)) return false;
		
		String critereAnnee = criteres.getAnnee()==-1?"":criteres.getAnnee()+"";
		if(!compare(this.annee+"",critereAnnee)) return false;
		 
		return true;
	}
	
	public boolean compare(String cible, String critere) {
		if(critere == "") return true;
		if(critere=="Al") System.out.println(cible + "<=" + critere);
		Pattern pattern = Pattern.compile("^"+critere+".*");
		Matcher matcher =  pattern.matcher(cible);
		
		return matcher.matches();
	}
	
	public static Stagiaire criteresVide() {
		Stagiaire stagiaire = new Stagiaire("","","","",(short)-1,'x',(short)-1);
		return stagiaire;
	}
	
	public String pdfFormatString() {
		String s ="";
		
		String fNom ="| " + this.nom;
		for(int i=fNom.length();i<2+TAILLE_NOM+1;i++)
			fNom += " ";
		
		String fPrenom ="| " + this.prenom;
		for(int i=fPrenom.length();i<2+TAILLE_NOM+1;i++)
			fPrenom += " ";
		
		String fDepartement ="| " + this.departement;
		for(int i=fDepartement.length();i<2+TAILLE_CP+1;i++)
			fDepartement += " ";
		
		String fParcours="| " + this.parcours;
		for(int i=fParcours.length();i<11;i++)
			fParcours += " ";
		
		String fNumPromo="| " + this.numPromo;
		for(int i=fNumPromo.length();i<11;i++)
			fNumPromo += " ";
		
		String fContratPro="| " + this.contratPro;
		for(int i=fContratPro.length();i<9;i++)
			fContratPro += " ";
		
		String fAnnee="| " + this.annee;
		for(int i=fAnnee.length();i<8;i++)
			fAnnee += " ";
		
		s = fNom + fPrenom + fDepartement + fParcours + fNumPromo + fContratPro + fAnnee;
		
		return s;
	}
	
	public static String enteteTableauStagiaire() {
		String s = "";
		
		String enteteNom ="| NOM                       ";
		String entetePrenom ="| PRENOM                    ";
		String enteteDepartement ="| CP ";
		String enteteParcours="| PARCOURS ";
		String enteteNumPromo="| N° PROMO ";
		String enteteContratPro="| C-PRO? ";
		String enteteAnnee="| ANNEE |";
		
		s= enteteNom + entetePrenom + enteteDepartement + enteteParcours + enteteNumPromo + enteteContratPro + enteteAnnee;
		
		return s;
	}

}

