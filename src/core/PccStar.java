package core ;

import java.awt.*;
import java.io.* ;
import java.util.ArrayList;

import base.Readarg ;

public class PccStar extends Pcc {

    public PccStar(Graphe gr, PrintStream sortie, Readarg readarg) {
	super(gr, sortie, readarg) ;
    }
    public PccStar(Graphe gr, PrintStream sortie, Readarg readarg, int dep, int arr, boolean isTime) {  super(gr, sortie, readarg, dep, arr, isTime);   }



    public void run() {

	System.out.println("Run PCC-Star de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;

	// A vous d'implementer la recherche de plus court chemin A*
    }

    public ArrayList<Label> Dijkstra(){
        this.tasLabel.viderTas();
        this.listeLabel.clear();
        System.out.println("A-STAR : ");
        for (Noeud noeud : this.gr.getListeNoeuds()){
            this.listeLabel.add(null);
        }
        long tempsInit = System.currentTimeMillis();
        long duree;
        int nbTas = 0, nbDansTas = 0, nbDansTasMax = 0;

        ArrayList<Noeud> listeNoeud = this.gr.getListeNoeuds();
        float cout;
        int numCourant, numDest;

        // On défini le premier label à regarder et on l'ajoute au tas
        this.listeLabel.set(this.origine, new Label(null, this.origine, 0, true, pccTime));
        this.tasLabel.insert(this.listeLabel.get(this.origine));

        // Tant que non trouvé et que le tas n'est pas vide
        while (!this.tasLabel.isEmpty()){
            // On défini le label et ne noeud courant ET on supprime le minimum
            numCourant = this.tasLabel.deleteMin().getNumSommetCourant();
            nbDansTas--;
            if (numCourant == this.destination){
                //on renseigne ses attributs
                duree = System.currentTimeMillis() - tempsInit;
                if (pccTime){   System.out.print("Chemin de "+this.origine+" long : "+gr.getListeNoeuds().get(this.origine).getLongitude()+" | Lat : "+gr.getListeNoeuds().get(this.origine).getLatitude()+" à " + this.destination + " long : "+gr.getListeNoeuds().get(this.destination).getLongitude()+" | Lat : "+gr.getListeNoeuds().get(this.destination).getLatitude()+" trouvé en " + duree + "ms et en " + nbTas + " noeuds parcourus, le cout a été de " + this.listeLabel.get(numCourant).getCout() + "min avec un nombre de noeuds dans le tas max de "+nbDansTasMax+" et en passant par les noeuds ");   }
                else {  System.out.print("Chemin de "+this.origine +" long : "+gr.getListeNoeuds().get(this.origine).getLongitude()+" | Lat : "+gr.getListeNoeuds().get(this.origine).getLatitude()+" à " + this.destination + " long : "+gr.getListeNoeuds().get(this.destination).getLongitude()+" | Lat : "+gr.getListeNoeuds().get(this.destination).getLatitude()+" trouvé en " + duree + "ms et en " + nbTas + " noeuds parcourus, le cout a été de " + this.listeLabel.get(numCourant).getCout() + "m avec un nombre de noeuds dans le tas max de "+nbDansTasMax+" et en passant par les noeuds "); }
                return super.findBestWay(this.listeLabel.get(this.destination), Color.CYAN);
                // Sinon (pas encore trouvé)
            }
            // Pour toutes les routes qui découlent du noeud
            for (Route r : listeNoeud.get(numCourant).getListeRoutes()){
                if(pccTime){    cout = listeLabel.get(numCourant).getCout() + (60*r.getLongueur())/(1000*r.getVitMax()); }
                else {  cout = listeLabel.get(numCourant).getCout()+r.getLongueur(); }
                numDest = r.getNoeudDest();
                if(this.listeLabel.get(numDest) == null){
                    this.listeLabel.set(numDest, new Label(numCourant, numDest, Float.MAX_VALUE, false, pccTime));
                }
                if(cout < listeLabel.get(numDest).getCout()){
                    listeLabel.get(numDest).setNumPere(numCourant);
                    listeLabel.get(numDest).setCout(cout);
                    if(!tasLabel.inTas(listeLabel.get(numDest))){
                        nbDansTas ++;   nbTas ++;
                        if(nbDansTas > nbDansTasMax){
                            nbDansTasMax = nbDansTas;
                        }
                        this.listeLabel.get(numDest).setEstimation(listeNoeud.get(this.destination), listeNoeud.get(numDest));
                        this.tasLabel.insert(listeLabel.get(numDest));
                    }else{
                        tasLabel.update(listeLabel.get(numDest));
                    }
                }
            }
            // On marque le label courant pour dire qu'il a été traité et ne sera pas modifié
            this.listeLabel.get(numCourant).setMarked();
        }
        duree = System.currentTimeMillis() - tempsInit;
        System.out.println("Le chemin de "+this.origine+" à "+this.destination+" n'existe pas ! Nous avons trouvé en "+duree+"ms et "+nbTas+"sommets parcourus que le sommet ne mène à rien !");
        return new ArrayList<Label>();
    }

}
