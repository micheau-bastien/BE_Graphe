package core ;

import java.io.* ;
import java.util.ArrayList;

import base.Readarg ;

public class Pcc extends Algo {

    // Numero des sommets origine et destination
    protected int zoneOrigine ;
    protected int origine ;
    protected int zoneDestination ;
    protected int destination ;
    protected ArrayList<Label> listeLabel = new ArrayList<Label>();
    protected BinaryHeap<Label> tasLabel = new BinaryHeap<Label>();

    public Pcc(Graphe gr, PrintStream sortie, Readarg readarg) {
        super(gr, sortie, readarg) ;
        this.zoneOrigine = gr.getZone () ;
        this.origine = readarg.lireInt ("Numero du sommet d'origine ? ") ;
        // Demander la zone et le sommet destination.
        this.zoneOrigine = gr.getZone () ;
        this.destination = readarg.lireInt ("Numero du sommet destination ? ");
    }

    public void createLabelTable (Graphe gr){
        for (Noeud noeud : gr.getListeNoeuds()){
            this.listeLabel.set(noeud.getNumNoeud(), new Label(-1, noeud.getNumNoeud()));
        }
    }

    public void Dijkstra (Graphe gr, int numCourant, int cout){
        ArrayList<Noeud> listeNoeud = gr.getListeNoeuds();
        Noeud noeudCourant = listeNoeud.get(numCourant);
        int coutMin = Integer.MAX_VALUE;
        int numParent = 0;

        for (Route route : noeudCourant.getListeRoutes()){

        }
    }

    private boolean inTas(int numCherche){
        boolean result = false;

        return result;
    }

    public Label getLabel(int numNoeud){    return this.listeLabel.get(numNoeud);   }

    public void run() {
        System.out.println("Run PCC de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;
        // A vous d'implementer la recherche de plus court chemin.
    }

}
