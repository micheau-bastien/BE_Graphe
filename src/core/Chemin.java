package core;

import base.Openfile;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Bastien on 30/03/15.
 */
public class Chemin {
    private int VersionFormat;
    private int idCarte;
    private int nbNoeuds;
    private int numNoeudDepart;
    private int numNoeudArrivee;
    private int magicNumber;
    private ArrayList<Route> listeRoutes = new ArrayList<Route>() ;

    public Chemin(DataInputStream dis, Graphe graphe) throws IOException {
        this.magicNumber = dis.readInt();
        this.VersionFormat = dis.readInt();
        this.idCarte = dis.readInt();
        this.nbNoeuds = dis.readInt();
        if (this.idCarte == graphe.getIdcarte()){
            this.numNoeudDepart = dis.readInt();
            this.numNoeudArrivee = dis.readInt();
            int numAct = dis.readInt();
            int numSuiv;
            for (int numNoeudsParc = 1 ; numNoeudsParc < this.nbNoeuds ; numNoeudsParc ++){
                numSuiv = dis.readInt();
                listeRoutes.add(meilleureRoute(graphe.getListeNoeuds().get(numAct), graphe.getListeNoeuds().get(numSuiv)));
                numAct = numSuiv;
            }
        }else{
            System.out.println("Les cartes de correspondent pas !!!");
            // THROW EXCEPTION A FAIRE
        }
    }

    private Route meilleureRoute(Noeud depart, Noeud arrivee){
        int longueurMin = Integer.MAX_VALUE;
        Route bonneRoute= null;
        for (Route route : depart.getListeRoutes()){
            if (route.getNoeudDest() == arrivee.getNumNoeud()){
                System.out.println("Bonne destination");
                if (longueurMin > route.getLongueur()){
                    bonneRoute = route;
                    longueurMin = route.getLongueur();
                }
            }
        }
        System.out.println(bonneRoute);
        return bonneRoute;
    }

    public float tempsChemin() {
        float longueurCourante;
        float tempsTot = 0;
        int vitCourante;
        for (Route route : this.listeRoutes){
            longueurCourante = route.getLongueur();
            vitCourante = route.getDescripteur().vitesseMax();
            tempsTot += (longueurCourante/vitCourante)*60/1000;
        }
        return tempsTot;
    }

    public float longueurChemin() {
        float longueurTot = 0;
        float longueurCourante;
        for (Route route : this.listeRoutes){
            System.out.println("longueur route : " + route.getLongueur());
            longueurCourante = route.getLongueur();
            longueurTot += longueurCourante;
        }
        return longueurTot;
    }

    public String toString(){
        return "Chemin = De : " + this.numNoeudDepart + " à " + this.numNoeudArrivee;
    }
}
