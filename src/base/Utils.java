package base ;

import java.io.* ;

/**
 *   Fonctions accessoires dont vous n'avez pas a vous servir directement.
 */
public class Utils {
    	
    // Verifie que l'octet lu est bien l'octet attendu
    public static void checkByte(int val, DataInputStream dis) throws IOException {
	int octet = dis.readUnsignedByte () ;
	if (octet != val) {
	    System.out.println("checkByte error : octet attendu = " + val + "   octet lu = " + octet) ;
	    System.exit(1) ;
	}
    }
    
    public static int read24bits(DataInputStream dis) throws IOException {
	int x = dis.readUnsignedShort() ;
	return (x << 8) | dis.readUnsignedByte() ;
    }

    // Calibrer la sortie graphique en fonction de la carte
    // Vous pouvez modifier les coordonnees pour ameliorer le rendu.
    public static void calibrer(String nomCarte, Dessin dessin) {

	if (nomCarte.startsWith("insa")) {
	    // L'INSA
	    dessin.setBB (1.462, 1.473, 43.567, 43.5744) ;
	}
	else if (nomCarte.startsWith("paris")) {
	    // Ile de la Cité, Paris
	    dessin.setBB (2.329, 2.372, 48.839, 48.867) ;
	}
	else if (nomCarte.startsWith("mayot")) {
	    // Mayotte
	    dessin.setBB (44.5, 45.5, -13.25, -12.25) ;
	}
	else if (nomCarte.startsWith("reuni")) {
	    // La Réunion
	    dessin.setBB (55.0, 56.0, -21.5, -20.5) ;
	}
	else if (nomCarte.startsWith("midip")) {
	    dessin.setBB (-0.6, 3.8, 42.2, 45.3) ;
	}
	else if (nomCarte.startsWith("franc")) {
	    dessin.setBB (-5.2, 10.0, 41.0, 51.5) ;
	}
	else if (nomCarte.startsWith("pfranc")) {
	    dessin.setBB (-5.2, 10.0, 41.0, 51.5) ;
	}
	else if (nomCarte.startsWith("fract") || nomCarte.startsWith("carr")) {
	    dessin.setBB (-0.05, 1.05, -0.05, 1.05) ;
	}
	else {
	    dessin.setBB (-20.0, 50.0, 20.0, 70.0) ;
	}
    }

    public static void checkVersion(int magic, int magic_attendu, int version, int version_attendue, String nomFichier, String nom_format) {
	if (magic != magic_attendu) {
	    System.out.println("Le fichier " + nomFichier + " n'est pas au format " + nom_format) ;
	    System.exit(1) ;
	}
	
	if (version != version_attendue) {
	    if (version > version_attendue) {
		System.out.println("La classe Graphe doit etre mise a jour pour lire le fichier " + nomFichier) ;
	    } 
	    else {
		System.out.println("Le fichier " + nomFichier + " est dans un format trop ancien") ;
	    }
	    System.out.println(" (version Graphe.java = " + version_attendue + " (pour la lecture des " + nom_format + "), version de " + nomFichier + 
			       " = " + version) ;
	    System.exit(1) ;
	}
    }

}
