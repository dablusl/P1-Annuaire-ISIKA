package fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class UsersIO {
	public static void getAllUsers() {
		// POUR TESTER LE BON STOCKAGE DE DONNEES DANS LE .BIN USER
		try {
			RandomAccessFile raf = new RandomAccessFile("src/data/users.bin", "r");
			int nbUsers = (int) raf.length() / User.TAILLE_USER;
			System.out.println("nbUsers inscrits : " + nbUsers);
			raf.seek(0);// debut de tableau
			for (int i = 0; i < nbUsers; i++) {
				String prenom = "";
				String nom = "";
				String email = "";
				String password = "";
				char droit;
				for (int k = 0; k < User.TAILLE_EMAIL; k++)
					email += raf.readChar();
				for (int k = 0; k < User.TAILLE_PRENOM; k++)
					prenom += raf.readChar();
				for (int k = 0; k < User.TAILLE_NOM; k++)
					nom += raf.readChar();
				for (int k = 0; k < User.TAILLE_PASSWORD; k++)
					password += raf.readChar();
				email = email.trim();
				nom = nom.trim();
				prenom = prenom.trim();
				System.out.println("User\t" + i);
				System.out.println("email\t: " + email);
				System.out.println("nom\t: " + prenom + " " + nom.toUpperCase());
				System.out.println("msp\t: ********");
				droit = raf.readChar();
				if (droit == 'u')
					System.out.println("Droits : Utilisateur\n\n");
			}
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean verifierAuthentification(String email, String password) {
		try {
			RandomAccessFile raf = new RandomAccessFile("src/data/users.bin", "r");
			raf.seek(0);
			while (raf.getFilePointer() < raf.length()) {
				User user = UsersIO.lireBin(raf);
				System.out.println(user.getEmail() + " et " + user.getPassword());
				if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
					raf.close();
					return true; // L'authentification est réussie
				}
				
			}
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false; // L'adresse e-mail ou le mot de passe est incorrect
	}
	public static User lireBin(RandomAccessFile raf) throws IOException {
		String email = "";
		String prenom = "";
		String nom = "";
		String password = "";
		char droit;
		for (int i = 0; i < User.TAILLE_EMAIL; i++) {
			email += raf.readChar();
		}
		for (int i = 0; i < User.TAILLE_PRENOM; i++) {
			prenom += raf.readChar();
		}
		for (int i = 0; i < User.TAILLE_NOM; i++) {
			nom += raf.readChar();
		}
		for (int i = 0; i < User.TAILLE_PASSWORD; i++) {
			password += raf.readChar();
		}
		email = email.trim();
		nom = nom.trim();
		prenom = prenom.trim();
		password = password.trim();
		droit = raf.readChar();
		return new User(nom, prenom, email, password);
	}
	public static int ecrireUsers(User user) {
		try {
			RandomAccessFile raf = new RandomAccessFile("src/data/users.bin", "rw");
			if (raf.length() == 0) {
				user.ecrireBin(raf);
			} else {
				// trouver ou on va inserer notre nouvelle donnee
				int nbUsers = (int) raf.length() / User.TAILLE_USER;
				int positionInsertion = trouverPositionDinsertion(raf, user.email, 0, nbUsers - 1);
				if (positionInsertion == -1)
					return -1; // doublon
				// taille des donees en octets
				byte[] buffer = new byte[(int) (raf.length() - positionInsertion)];
				raf.seek(positionInsertion);
				raf.read(buffer);
				raf.seek(positionInsertion);
				user.ecrireBin(raf);
				raf.write(buffer);
			}
			raf.close();
			return 1; // all is gut, stagiaire ecris
		} catch (Exception e) {
		}
		return 0;// all is un-gut, stagiaire n'a pa ete ecris
	}
	public static int trouverPositionDinsertion(RandomAccessFile raf, String email, int inf, int sup) {
		try {
			// recherche binaire ou recherche dichotomique
			if (inf > sup)
				return inf * User.TAILLE_USER;
			int mid = (sup + inf) / 2;
			int byteSpot = mid * User.TAILLE_USER;
			raf.seek(byteSpot);
			String currentEmail = "";
			for (int i = 0; i < User.TAILLE_EMAIL; i++)
				currentEmail += raf.readChar();
			int compareVal = currentEmail.trim().compareTo(email);
			// System.out.println(currentEmail);
			if (compareVal == 0) {
				System.err.println("DOUBLON");
				return -1;
			} else if (compareVal < 0) {
				return trouverPositionDinsertion(raf, email, mid + 1, sup);
			} else {
				return trouverPositionDinsertion(raf, email, inf, sup - 1);
			}
		} catch (IOException e) {
			System.err.println("ça a peté");
			return -2;
		}
	}
	
	
	
	
}
