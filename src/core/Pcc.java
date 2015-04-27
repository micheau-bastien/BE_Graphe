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
        this.zoneOrigine = gr.getZone() ;
        this.origine = readarg.lireInt("Numero du sommet d'origine ? ") ;
        // Demander la zone et le sommet destination.
        this.zoneOrigine = gr.getZone () ;
        this.destination = readarg.lireInt("Numero du sommet destination ? ");
        createLabelTable(gr);
        showBestWay(gr, this.origine, this.destination);
    }

    public void createLabelTable (Graphe gr){
        for (Noeud noeud : gr.getListeNoeuds()){
            this.listeLabel.add(new Label(-1, noeud.getNumNoeud()));
        }
    }

    public ArrayList<Label> Dijkstra (Graphe gr, int numCourant, int cout, int numDest){
        Label labelCourant = this.listeLabel.get(numCourant);
        ArrayList<Noeud> listeNoeud = gr.getListeNoeuds();
        Noeud noeudCourant = listeNoeud.get(numCourant);
        int coutMin = Integer.MAX_VALUE;
        Integer numParent = null;
        boolean trouve = false;
        Label labelFinal = new Label(0, numDest, Integer.MAX_VALUE);
        ArrayList<Label> termine = new ArrayList<Label>();
        tasLabel.print();
        labelCourant.setCout(0);
        this.tasLabel.insert(labelCourant);
        tasLabel.print();
        Label Hist = labelCourant;

        while (!this.tasLabel.isEmpty() && trouve==false) {
            for (Route route : noeudCourant.getListeRoutes()) {
                Label labelDest = this.listeLabel.get(route.getNoeudDest());
                System.out.println("       !!!!!!"+labelDest);
                if (labelDest.getNumSommetCourant() == numDest) {
                    trouve = true;
                    labelFinal.setCout(cout + route.getLongueur());
                    labelFinal.setMarked();
                    labelFinal.setNumPere(numCourant);
                    termine.add(labelFinal);
                } else {
                    if (termine.contains(labelDest)) {
                        if (labelDest.getCout() + route.getLongueur() <= coutMin) {
                            coutMin = labelDest.getCout() + route.getLongueur();
                            numParent = labelDest.getNumSommetCourant();
                        }
                    } else if (this.tasLabel.inTas(labelDest)) {
                        if (cout + route.getLongueur() <= labelDest.getCout()) {
                            Label labelAncien = labelDest;
                            labelDest.setCout(cout + route.getLongueur());
                            Label labelNouveau = labelDest;
                            this.tasLabel.replace(labelAncien, labelNouveau);
                            this.tasLabel.update(labelNouveau);
                        }
                    } else {
                        labelDest.setCout(cout + route.getLongueur());
                        this.tasLabel.insert(labelDest);
                    }
                }
            }

            labelCourant.setMarked();
            labelCourant.setNumPere(numParent);
            termine.add(labelCourant);
            System.out.println(labelCourant);
            this.tasLabel.replace(Hist, labelCourant);
            while (this.tasLabel.findMin().isMarked()){
                this.tasLabel.deleteMin();
            }
            this.tasLabel.delete(labelCourant);
            tasLabel.printSorted();
            noeudCourant = listeNoeud.get(this.tasLabel.findMin().getNumSommetCourant());
            Hist = this.listeLabel.get(noeudCourant.getNumNoeud());
            System.out.println(noeudCourant.getNumNoeud() +"                         " + Hist+"Noeud : "+noeudCourant.getNumNoeud());
            //break;
        }
        if(this.tasLabel.isEmpty()){
            System.out.println("IMPOSSIBLE !");
        }
        return findBestWay(termine, labelFinal);
    }

    private ArrayList<Label> findBestWay(ArrayList<Label> datas, Label fin){
        ArrayList<Label> chemin = new ArrayList<Label>();
        chemin.add(fin);
        Label courant = this.listeLabel.get(fin.getNumPere());
        while (courant.getNumPere() == null){
            chemin.add(courant);
            courant = this.listeLabel.get(courant.getNumPere());
        }
        return chemin;
    }

    public void showBestWay(Graphe gr, int numCourant, int numDest){
        ArrayList<Label> chemin = Dijkstra(gr, numCourant, 0, numDest);
        for (Label l : chemin){
            System.out.println(l);
        }
    }

    public Label getLabel(int numNoeud){    return this.listeLabel.get(numNoeud);   }

    public void run() {
        System.out.println("Run PCC de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;
        // A vous d'implementer la recherche de plus court chemin.
    }

}
