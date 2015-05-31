package core;

import base.Readarg;

import java.awt.*;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Created by Bastien on 22/05/15.
 */
public class Covoiturage extends Pcc{
    private Noeud rencontre;
    private ArrayList<Label> cheminVoiture = new ArrayList<Label>(), cheminPieton = new ArrayList<Label>(), cheminRencontre = new ArrayList<Label>();
    private float coutMaxPieton = 0;
    private int destination;
    private int vitessePieton = 4;
    private float maxMarchePieton = 20;

    public Covoiturage(Graphe gr, PrintStream sortie, Readarg readarg, int voiture, int pieton, int destination){
        super(gr, sortie, readarg);
        this.destination = destination;
        runCovoit(voiture, pieton);
    }
    public Covoiturage(Graphe gr, PrintStream sortie, Readarg readarg, int voiture, int pieton, int destination, int vitessePieton){
        super(gr, sortie, readarg);
        this.destination = destination;
        this.vitessePieton = vitessePieton;
        System.out.println("Calcul du covoiturage : "+voiture+"  "+pieton+"  "+destination);
        runCovoit(voiture, pieton);
    }

    // Ici il aurait fallut incorporer des exceptions dans tous les Dijkstra, le risque d'avoir des null pointer error ne sont pas nulles et ne sont pas vérifiées...
    // Il doit être possible d'optimiser grandement la réalisation de l'algorithme en évitant de créer plusieurs arrayList<Label>, encore un point améliorable si nous avions plus temps.
    private void runCovoit(int voiture, int pieton){
        System.out.println("Chemin voiture-pieton : ");
        for (Label label : this.Dijkstra(voiture, pieton, false)) {
            this.cheminVoiture.add(label);
        }
        System.out.println("Chemin voiture-pieton fini. ");
        System.out.println("Chemin pieton-voiture : ");
        for (Label label : this.Dijkstra(pieton, voiture, true)) {
            this.cheminPieton.add(label);
        }
        this.pccTime = true;
        if(cheminPieton == cheminVoiture){
            System.out.print("Erreur 512 : Je ne suis pas une théière mais je t'ai bien eut ! CheminPieton et Chemin voiture sont les mêmes");
        }
        System.out.println("Calcul du point de rencontre. ");
        for(Label labelVoiture : this.cheminVoiture){
            if (labelVoiture != null) {
                Label labelPieton = this.cheminPieton.get(labelVoiture.getNumSommetCourant());
                if ( labelPieton != null){
                    if (labelPieton.getCout() <= labelVoiture.getCout()) {
                        if (labelPieton.getCout() > coutMaxPieton && labelPieton.getCout() < this.maxMarchePieton){
                            this.coutMaxPieton = labelPieton.getCout();
                            this.rencontre = this.gr.getListeNoeuds().get(labelPieton.getNumSommetCourant());
                        }
                    }
                }
            }
        }
        if (this.rencontre == null){
            this.rencontre = this.gr.getListeNoeuds().get(pieton);
        }
        System.out.println("Calcul du chemin Voiture-rencontre : ");
        for (Label label :  this.Dijkstra(voiture, this.rencontre.getNumNoeud(), false)){
            this.cheminVoiture.add(label);
        }
        for (Label label :  this.Dijkstra(pieton, this.rencontre.getNumNoeud(), true)){
            this.cheminPieton.add(label);
        }
        for (Label label :  this.Dijkstra(rencontre.getNumNoeud(), destination, false)){
            this.cheminRencontre.add(label);
        }

        System.out.println("Rencontre : "+rencontre.getNumNoeud());
        this.listeLabel = this.cheminRencontre;
        findBestWay(this.listeLabel.get(destination), Color.CYAN);
        this.listeLabel = this.cheminVoiture;
        findBestWay(this.listeLabel.get(rencontre.getNumNoeud()), Color.GREEN);
        this.listeLabel = this.cheminPieton;
        findBestWay(this.listeLabel.get(rencontre.getNumNoeud()), Color.BLACK);
    }

    public ArrayList<Label> Dijkstra(int origine, int destinationChemin, boolean isPieton){
        System.out.println("A-Star covoiturage");
        System.out.println("Calcul du covoiturage : "+origine+"  "+destinationChemin+"  "+this.destination);

        this.tasLabel.viderTas();
        this.listeLabel.clear();
        for (Noeud noeud : this.gr.getListeNoeuds()) {
            this.listeLabel.add(null);
        }
        long tempsInit = System.currentTimeMillis();
        long duree;
        int nbTas = 0, nbDansTas = 0, nbDansTasMax = 0;

        ArrayList<Noeud> listeNoeud = this.gr.getListeNoeuds();
        float cout;
        int numCourant, numDest;

        // On défini le premier label à regarder et on l'ajoute au tas
        this.listeLabel.set(origine, new Label(null, origine, 0, true, pccTime));
        this.tasLabel.insert(this.listeLabel.get(origine));

        // Tant que non trouvé et que le tas n'est pas vide
        while (!this.tasLabel.isEmpty()){
            // On défini le label et ne noeud courant ET on supprime le minimum
            numCourant = this.tasLabel.deleteMin().getNumSommetCourant();
            nbDansTas--;
            if (numCourant == destinationChemin){
                //on renseigne ses attributs
                duree = System.currentTimeMillis() - tempsInit;
                System.out.println("Chemin de " + origine + " à " + destinationChemin + " trouvé en " + duree + "ms et en " + nbTas + " noeuds parcourus, le cout a été de " + this.listeLabel.get(numCourant).getCout() + "s avec un nombre de noeuds dans le tas max de " + nbDansTasMax + " et en passant par les noeuds ");
                return this.listeLabel;
            }
            // Pour toutes les routes qui découlent du noeud
            for (Route r : listeNoeud.get(numCourant).getListeRoutes()){
                numDest = r.getNoeudDest();
                gr.getDessin().setColor(Color.pink);
                float longitudeDep, latitudeDep, longitudeArr, latitudeArr;
                longitudeArr = gr.getListeNoeuds().get(numDest).getLongitude();
                latitudeArr = gr.getListeNoeuds().get(numDest).getLatitude();
                longitudeDep = gr.getListeNoeuds().get(numCourant).getLongitude();
                latitudeDep = gr.getListeNoeuds().get(numCourant).getLatitude();
                gr.getDessin().drawLine(longitudeDep, latitudeDep, longitudeArr, latitudeArr) ;
                float estimBrut = (float)Graphe.distance(this.gr.getListeNoeuds().get(numCourant).getLongitude(), this.gr.getListeNoeuds().get(numCourant).getLatitude(), this.gr.getListeNoeuds().get(this.destination).getLongitude(), this.gr.getListeNoeuds().get(this.destination).getLatitude());
                if(isPieton){    cout = listeLabel.get(numCourant).getCout() + (60*(r.getLongueur()))/(1000*vitessePieton); }
                else {  cout = listeLabel.get(numCourant).getCout() + (60*(r.getLongueur()))/(1000*r.getVitMax()); }
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
                        this.listeLabel.get(numDest).setEstimation(listeNoeud.get(destinationChemin), listeNoeud.get(numDest));
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
        System.out.println("Le chemin de "+origine+" à "+destinationChemin+" n'existe pas ! Nous avons trouvé en "+duree+"ms et "+nbTas+"sommets parcourus que le sommet ne mène à rien !");
        return new ArrayList<Label>();
    }
}
