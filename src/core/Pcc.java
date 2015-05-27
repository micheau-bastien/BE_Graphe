package core ;

import java.awt.*;
import java.io.* ;
import java.util.ArrayList;
import java.util.HashMap;

import base.Couleur;
import base.Readarg ;

public class Pcc extends Algo {

    // Numero des sommets origine et destination



    protected int zoneOrigine ;
    protected int zoneDestination ;

    protected int origine ;
    protected int destination ;

    protected ArrayList<Label> listeLabel = new ArrayList<Label>();
    protected BinaryHeap<Label> tasLabel = new BinaryHeap<Label>();
    protected Boolean pccTime = false;
    protected Graphe gr;

    public Pcc(Graphe gr, PrintStream sortie, Readarg readarg) {
        super(gr, sortie, readarg) ;
        this.gr = gr;
        this.zoneOrigine = gr.getZone() ;
    }

    public Pcc(Graphe gr, PrintStream sortie, Readarg readarg, int dep, int arr, boolean isTime) {
        super(gr, sortie, readarg) ;
        System.out.println();
        this.gr = gr;
        this.origine = dep;
        this.destination = arr;
        this.pccTime = isTime;
        this.zoneOrigine = gr.getZone() ;
        ArrayList<Label> CheminPlucCourt = Dijkstra();
    }

    public ArrayList<Label> Dijkstra (){
        this.tasLabel.viderTas();
        this.listeLabel.clear();
        System.out.println("Dijkstra : ");
        tasLabel.viderTas();
        this.listeLabel.clear();
        // On prend le temps initial du programme (Estimation de performance)
        long tempsInit = System.currentTimeMillis(), duree;
        int nbTas = 0, nbDansTas = 0, nbDansTasMax = 0, numCourant, numDest;
        float cout;
        for (Noeud noeud : this.gr.getListeNoeuds()){
            this.listeLabel.add(null);
        }

        // On défini le premier label à regarder et on l'ajoute au tas
        this.listeLabel.set(this.origine, new Label(null, this.origine, 0, true, pccTime));
        this.tasLabel.insert(this.listeLabel.get(this.origine));
        nbDansTas++;

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
                return findBestWay(this.listeLabel.get(this.destination), Color.CYAN);
                // Sinon (pas encore trouvé)
            }
            // Pour toutes les routes qui découlent du noeud
            for (Route r : this.gr.getListeNoeuds().get(numCourant).getListeRoutes()){
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
        System.out.println("Le chemin de " + this.origine +" long : "+gr.getListeNoeuds().get(this.origine).getLongitude()+" | Lat : "+gr.getListeNoeuds().get(this.origine).getLatitude()+" à " + this.destination + " long : "+gr.getListeNoeuds().get(this.destination).getLongitude()+" | Lat : "+gr.getListeNoeuds().get(this.origine).getLatitude()+ " n'existe pas ! Nous avons trouvé en " + duree + "ms et " + nbTas + "sommets parcourus que le sommet ne mène à rien !");
        return new ArrayList<Label>();
    }

    protected ArrayList<Label> findBestWay(Label fin, Color couleur){
        ArrayList<Label> listeFinal = new ArrayList<Label>();
        listeFinal.add(fin);
        if (fin.getNumPere() != null) {
            Label courant = this.listeLabel.get(fin.getNumPere());
            while (courant.getNumPere() != null) {
                listeFinal.add(courant);
                courant = this.listeLabel.get(courant.getNumPere());
            }
        }
        gr.getDessin().setColor(couleur);
        float longitudeDep, latitudeDep, longitudeArr, latitudeArr;
        for (Label l : listeFinal){
            longitudeArr = gr.getListeNoeuds().get(l.getNumSommetCourant()).getLongitude();
            latitudeArr = gr.getListeNoeuds().get(l.getNumSommetCourant()).getLatitude();
            if (l.getNumPere() != null) {
                longitudeDep = gr.getListeNoeuds().get(l.getNumPere()).getLongitude();
                latitudeDep = gr.getListeNoeuds().get(l.getNumPere()).getLatitude();
            }else{
                longitudeDep = longitudeArr;
                latitudeDep = latitudeArr;
            }
            //System.out.println("A afficher "+l.getNumPere()+ " : "+longitudeArr+"     "+ latitudeArr +" "+l.getNumSommetCourant()+"    "+ longitudeDep+"     "+latitudeDep);
            gr.getDessin().drawLine(longitudeDep, latitudeDep, longitudeArr, latitudeArr) ;
        }
        Chemin chemin = new Chemin(listeFinal.size(), this.destination, this.origine, LabelToRoad(listeFinal));
        return listeFinal;
    }

    // Fonction crée par erreur de conception initiale
    protected ArrayList<Route> LabelToRoad (ArrayList<Label> listeLabel){
        ArrayList<Route> listeRoutes = new ArrayList<Route>();
        for (Label label : listeLabel){
            if (label.getNumPere() != null) {
                ArrayList<Route> listeRouteLabel = this.gr.getListeNoeuds().get(label.getNumPere()).getListeRoutes();
                for (Route route : listeRouteLabel) {
                    if (route.getNoeudDest() == label.getNumSommetCourant()) {
                        listeRoutes.add(route);
                    }
                }
            }
        }
        return listeRoutes;
    }

    public void run() {
        System.out.println("Run PCC de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;
        // A vous d'implementer la recherche de plus court chemin.
    }

    public Graphe revertGr (){
        Graphe revert = this.gr;
        for (Noeud noeud : gr.getListeNoeuds()){
            for (Route route : noeud.getListeRoutes()){
                if (route.getDescripteur().isSensUnique()){

                }
            }
        }
        return  revert;
    }

}
