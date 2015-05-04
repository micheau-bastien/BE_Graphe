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

    public ArrayList<Label> cheminCourt(Graphe gr, int numCourant, int numDest){
        ArrayList<Noeud> listeNoeud = gr.getListeNoeuds();

        Label labelFinal = new Label(0, numDest, Integer.MAX_VALUE);
        Noeud noeudCourant, noeudDest;
        Label labelCourant = new Label(null, numCourant, 0), labelDest;

        boolean trouve = false;
        labelCourant.setMarked();
        this.tasLabel.insert(labelCourant);

        while (!trouve && !this.tasLabel.isEmpty()){
            labelCourant = this.tasLabel.deleteMin();
            noeudCourant = listeNoeud.get(labelCourant.getNumSommetCourant());

            for (Route r : noeudCourant.getListeRoutes()){
                noeudDest = listeNoeud.get(r.getNoeudDest());
                labelDest = this.listeLabel.get(r.getNoeudDest());
                if (noeudDest.getNumNoeud() == numDest){
                    trouve = true;
                    labelFinal.setCout(labelCourant.getCout() + r.getLongueur());
                    labelFinal.setMarked();
                    labelFinal.setNumPere(labelCourant.getNumSommetCourant());
                    this.listeLabel.set(labelFinal.getNumSommetCourant(), labelFinal);
                }else {
                    if (labelDest.isMarked()) {
                    } else if (this.tasLabel.inTas(labelDest)) {
                        if (labelCourant.getCout() + r.getLongueur() <= labelDest.getCout()) {
                            Label labelAncien = labelDest;
                            labelDest.setCout(labelCourant.getCout() + r.getLongueur());
                            labelDest.setNumPere(labelCourant.getNumSommetCourant());
                            Label labelNouveau = labelDest;
                            this.tasLabel.replace(labelAncien, labelNouveau);
                            this.tasLabel.update(labelNouveau);
                            this.listeLabel.set(labelNouveau.getNumSommetCourant(), labelNouveau);
                        }
                    } else {
                        labelDest.setCout(labelCourant.getCout() + r.getLongueur());
                        labelDest.setNumPere(labelCourant.getNumSommetCourant());
                        this.tasLabel.insert(labelDest);
                        this.listeLabel.set (labelDest.getNumSommetCourant(), labelDest);
                    }
                }
            }
            System.out.println("LE NUM PARENT DE "+ labelCourant.getNumSommetCourant() +" EST : " + labelCourant.getNumPere());
            labelCourant.setMarked();
            System.out.println(labelCourant);
            this.listeLabel.set(labelCourant.getNumSommetCourant(), labelCourant);
        }
        if(this.tasLabel.isEmpty()){
            System.out.println("IMPOSSIBLE !");
            return listeLabel;
        }else {
            return findBestWay(labelFinal);
        }
    }

    private ArrayList<Label> findBestWay(Label fin){
        ArrayList<Label> chemin = new ArrayList<Label>();
        chemin.add(fin);
        Label courant = this.listeLabel.get(fin.getNumPere());
        //System.out.println(this.listeLabel + "                   " + courant);
        while (courant.getNumPere() != null){
            chemin.add(courant);
            System.out.println(courant.getNumSommetCourant() + "            " + courant.getNumPere());
            courant = this.listeLabel.get(courant.getNumPere());
        }
        System.out.println("Chemin : " + chemin);
        return chemin;
    }

    public void showBestWay(Graphe gr, int numCourant, int numDest){
        ArrayList<Label> chemin = cheminCourt(gr, numCourant, numDest);
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
