package base ;

/*
 * Ce programme propose de lancer divers algorithmes sur les graphes
 * a partir d'un menu texte, ou a partir de la ligne de commande (ou des deux).
 *
 * A chaque question posee par le programme (par exemple, le nom de la carte), 
 * la reponse est d'abord cherchee sur la ligne de commande.
 *
 * Pour executer en ligne de commande, ecrire les donnees dans l'ordre. Par exemple
 *   "java base.Launch insa 1 1 /tmp/sortie 0"
 * ce qui signifie : charge la carte "insa", calcule les composantes connexes avec une sortie graphique,
 * ecrit le resultat dans le fichier '/tmp/sortie', puis quitte le programme.
 */

import core.* ;
import java.io.* ;
import java.util.ArrayList;

public class Launch {

    private final Readarg readarg ;

    public Launch(String[] args) {
	this.readarg = new Readarg(args) ;
    }

    public void afficherMenu () {
	System.out.println () ;
	System.out.println (" MENU ") ;
	System.out.println () ;
	System.out.println ("0 - Quitter") ;
	System.out.println ("1 - Composantes Connexes") ;
	System.out.println ("2 - Plus court chemin standard (Click)") ;
	System.out.println ("3 - Plus court chemin A-star (Click)") ;
	System.out.println ("4 - Cliquer sur la carte pour obtenir un numero de sommet.") ;
	System.out.println ("5 - Charger un fichier de chemin (.path) et le verifier.") ;
	System.out.println ("6 - Test Unitaire.") ;
	System.out.println ("7 - Chemin") ;
	System.out.println ("8 - Covoiturage (Click)") ;
	System.out.println ("9 - FastTest") ;

	System.out.println () ;
    }

    public static void main(String[] args){
		Launch launch = new Launch(args) ;
		launch.go() ;
    }

    public void go() {

	try {
	    System.out.println ("**") ;
	    System.out.println ("** Programme de test des algorithmes de graphe.");
	    System.out.println ("**") ;
	    System.out.println () ;

	    // On obtient ici le nom de la carte a utiliser.
	    String nomcarte = this.readarg.lireString ("Nom du fichier .map a utiliser ? ") ;
	    DataInputStream mapdata = Openfile.open (nomcarte) ;

	    boolean display = (1 == this.readarg.lireInt ("Voulez-vous une sortie graphique (0 = non, 1 = oui) ? ")) ;	    
	    Dessin dessin = (display) ? new DessinVisible(800,600) : new DessinInvisible() ;

	    Graphe graphe = new Graphe(nomcarte, mapdata, dessin) ;

	    // Boucle principale : le menu est accessible 
	    // jusqu'a ce que l'on quitte.
	    boolean continuer = true ;
	    int choix ;
	    
	    while (continuer) {
		this.afficherMenu () ;
		choix = this.readarg.lireInt ("Votre choix ? ") ;
		
		// Algorithme a executer
		Algo algo = null ;
		
		// Le choix correspond au numero du menu.
		switch (choix) {
			case 0 : continuer = false ; break ;

			case 1 : algo = new Connexite(graphe, this.fichierSortie (), this.readarg) ; break ;
		
			case 2 :
				boolean isTime = (readarg.lireInt("En temps (1) ou en distance(0 ou autre) ? ") ==1);
				System.out.println("Choix du départ : ");
				int depart = graphe.getNumNoeudFromClick();
				System.out.println("Choix de l'arrivée : ");
				int arrivee = graphe.getNumNoeudFromClick();
				algo = new Pcc(graphe, this.fichierSortie (), this.readarg, depart, arrivee, isTime) ;
				break ;
		
			case 3 :
				boolean isTimeStar = (readarg.lireInt("En temps (1) ou en distance(0 ou autre) ? ") ==1);
				System.out.println("Choix du départ : ");
				int departStar = graphe.getNumNoeudFromClick();
				System.out.println("Choix de l'arrivée : ");
				int arriveeStar = graphe.getNumNoeudFromClick();
				algo = new Pcc(graphe, this.fichierSortie (), this.readarg, departStar, arriveeStar, isTimeStar) ;
				break ;

			case 4 : graphe.situerClick() ; break ;

			case 5 :
		    String nom_chemin = this.readarg.lireString ("Nom du fichier .path contenant le chemin ? ") ;
		    graphe.verifierChemin(Openfile.open (nom_chemin), nom_chemin) ;
				break ;

			case 6 : System.out.println("Nombre successeur moyen : " + nbMoySucc(graphe)); break;

			case 7 :
				String nomChemin = this.readarg.lireString("Nom du fichier de chemin a utiliser ? ") ;
				DataInputStream cheminData = Openfile.open(nomChemin) ;
				Chemin chemin = new Chemin(cheminData, graphe);
				System.out.println("Temps du trajet  : " + chemin.tempsChemin());
				System.out.println("Longueur du trajet : " + chemin.longueurChemin());
				break;
			case 8 :
				int vitPiet = readarg.lireInt("Vitesse du piéton ? ");
				System.out.println();
				System.out.println("Choix du départ de la voiture : ");
				int voiture = graphe.getNumNoeudFromClick();
				System.out.println("Choix du départ du piéton : ");
				int pieton = graphe.getNumNoeudFromClick();
				System.out.println("Choix de la destination : ");
				int arrive = graphe.getNumNoeudFromClick();
				System.out.println();
				System.out.println("Calcul du covoiturage : ");

				algo = new Covoiturage(graphe, this.fichierSortie(), this.readarg, voiture, pieton, arrive, vitPiet );
				break;
			case 9 :
				System.out.println () ;
				System.out.println ("           FastTest ") ;
				System.out.println () ;
				System.out.println ("          0 - Quitter") ;
				System.out.println ("          1 - Dijkstra") ;
				System.out.println ("          2 - A-Star") ;
				System.out.println ("          3 - Covoiturage") ;
				int choixTest = this.readarg.lireInt ("Votre choix ? ") ;
				switch (choixTest) {
					case 0 : 	continuer = false ; break ;
					case 1 :	algo = new Pcc(graphe, this.fichierSortie (), this.readarg, readarg.lireInt("Départ ? "), readarg.lireInt("Arrivee ? "), (readarg.lireInt("Temps (1) ou distance (Autre) ? ")==1));	break ;
					case 2 :	algo = new PccStar(graphe, this.fichierSortie (), this.readarg, readarg.lireInt("Départ ? "), readarg.lireInt("Arrivee ? "), (readarg.lireInt("Temps (1) ou distance (Autre) ? ")==1));	break ;
					case 3 :	algo = new Covoiturage(graphe, this.fichierSortie (), this.readarg, readarg.lireInt("Voiture ? "), readarg.lireInt("Pieton ? "), readarg.lireInt("Destination ? "), readarg.lireInt("Vitesse pieton ? "));	break ;
				}
				break;

		default:
		    System.out.println ("Choix de menu incorrect : " + choix) ;
		    System.exit(1) ;
		}
		
		if (algo != null) { algo.run() ; }
	    }
	    
	    System.out.println ("Programme terminé.") ;
	    System.exit(0) ;
	    
	    
	} catch (Throwable t) {
	    t.printStackTrace() ;
	    System.exit(1) ;
	}
    }

    // Ouvre un fichier de sortie pour ecrire les reponses
    public PrintStream fichierSortie () {
		PrintStream result = System.out ;

		String nom = "oiu";

		if ("".equals(nom)) { nom = "/dev/null" ; }

		try { result = new PrintStream(nom) ; }
		catch (Exception e) {
			System.err.println("Erreur a l'ouverture du fichier " + nom) ;
			System.exit(1) ;
		}

		return result ;
    }

	public float nbMoySucc(Graphe graphe){
		float sum = 0;
		float compteur = 0;
		float moy;
		for (Noeud noeud : graphe.getListeNoeuds()){
			compteur++;
			sum = sum + noeud.getNbRoutes();
			moy = sum/compteur;
			System.out.println("debug dans boucle noeud. Compteur : " + compteur + " nbR : "+noeud.getNbRoutes() + " Sum : " + sum + "Moy : " + moy);
		}
		return sum/compteur;
	}
}
