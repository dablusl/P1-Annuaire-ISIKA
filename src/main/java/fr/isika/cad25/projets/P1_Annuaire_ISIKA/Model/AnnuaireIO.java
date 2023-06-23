package fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.RandomAccessFile;

public class AnnuaireIO {

	public static void initAnnuaire(String path) {
		donVersBin(path);
	}

	public static void donVersBin(String path) {
		
		try {
			
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);

			while (br.ready())
				ArbreBinaireBin.ajouterStagiaire(readTextStagiaire(br));

			br.close();

		} catch (Exception e) {
			System.err.println("fichier incomplet - chargement du fichier interrompu");
		}
	}

	private static Stagiaire readTextStagiaire(BufferedReader br) {
		Stagiaire stagiaire = null;

		try {
			String nom = br.readLine().toLowerCase();
			String prenom = br.readLine().toLowerCase();

			String departement = br.readLine();

			try {
				int intCP = Integer.parseInt(departement);
				if (departement.length() != 2)
					departement = "  ";
			} catch (Exception e) {
				departement = "  ";
			}

			String[] promoInfo = br.readLine().split(" ");

			String parcours = promoInfo[0];
			short numPromo = Short.parseShort(promoInfo[1]);
			char contratPro = 'N';

			if (promoInfo.length == 3)
				contratPro = 'O';

			short annee = Short.parseShort(br.readLine());

			stagiaire = new Stagiaire(nom, prenom, departement, parcours, numPromo, contratPro, annee);

			br.readLine();
		} catch (Exception e) {
		}

		return stagiaire;
	}

	public static void getAllStagiaires() {
		// POUR TESTER LE BON STOCKAGE DE DONNEES DANS LE .BIN USER
		try {

			RandomAccessFile raf = new RandomAccessFile("src/data/Stagiaires.bin", "r");
			int nbStagiaires = (int) raf.length() / NoeudBin.TAILLE_NOEUD;

			System.out.println("Pour info\nTaille Stagiaire : " + Stagiaire.TAILLE_STAGIAIRE);
			System.out.println("Noeud : " + NoeudBin.TAILLE_NOEUD);

			System.out.println("Stagiaires inscrits : " + nbStagiaires);

			raf.seek(0);// debut de tableau

			for (int i = 0; i < 500; i++) {

				String prenom = "";
				String nom = "";
				String departement = "";
				String parcours = "";
				Short numPromo;
				char contratPro;
				Short annee;
				int pointeurGauche;
				int pointeurDroit;
				int pointeurFrere;
				
				raf.readInt();
				for (int k = 0; k < Stagiaire.TAILLE_NOM; k++)
					nom += raf.readChar();

				for (int k = 0; k < Stagiaire.TAILLE_PRENOM; k++)
					prenom += raf.readChar();

				for (int k = 0; k < Stagiaire.TAILLE_CP; k++)
					departement += raf.readChar();

				for (int k = 0; k < Stagiaire.TAILLE_PARCOURS; k++)
					parcours += raf.readChar();

				numPromo = raf.readShort();
				contratPro = raf.readChar();
				annee = raf.readShort();
				pointeurGauche = raf.readInt();
				pointeurDroit = raf.readInt();
				pointeurFrere = raf.readInt();

				nom = nom.trim();
				prenom = prenom.trim();

				System.out.println("\nStagiare\t" + i);
				System.out.println("Pos bin\t\t: " + i*NoeudBin.TAILLE_NOEUD);

				System.out.println("nom\t\t: " + nom.toUpperCase() + " " + prenom);
				System.out.println("CP\t\t: " + departement);
				System.out.println("Parcours\t: " + parcours + " " + numPromo);
				System.out.println("Contrat Pro\t: " + contratPro);
				System.out.println("Annee\t\t: " + annee);

				System.out.println("fils Guache\t: " + pointeurGauche + "\nfils Droit\t: " + pointeurDroit);
				System.out.println("frere\t:"+ pointeurFrere);

			}
			raf.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
