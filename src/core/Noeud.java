package core;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

import base.Couleur;
import base.Utils;


public class Noeud {
	
	/*===  Format des noeuds ===

 [0-3]  = longitude sur 32 bits (à diviser par 1E6)
 [4-7]  = latitude  sur 32 bits (à diviser par 1E6)
 [8]    = Nombre de routes sortantes sur 8 bits

*/
	private float longitude;
	private float latitude;

	//private int nb_Successeurs;
	// private int numZone;
	private int numNoeud;
	private static int nbNoeud = 0;
	private ArrayList<Route> listeRoutes = new ArrayList<Route>();
	private ArrayList<Route> listeRoutesParent = new ArrayList<Route>();
	private int nbRoutes = 0;
	
	/* public Noeud(DataInputStream dis) throws IOException{
		this.longitude = ((float)dis.readInt ()) / 1E6f ;
		this.latitude = ((float)dis.readInt ()) / 1E6f ;
		this.nbRoutes = dis.readUnsignedByte() ;
	    
	}
	
	public Noeud(float longi,float lati,int nbR, ArrayList<Route> rtes){
		this.longitude = longi;
		this.latitude = lati;
		this.nbRoutes = nbR;
		this.listeRoutes = rtes;
	} */

	public Noeud(float longi,float lati, int nbR){
		this.numNoeud = nbNoeud;
		nbNoeud++;
		this.longitude = longi;
		this.latitude = lati;
        this.nbRoutes = nbR;
	}

	public void findRoutes(DataInputStream dis) throws IOException{
		Route route = new Route(dis);
		listeRoutes.add(route);
	}

	public void setRoutes(ArrayList<Route> listeRoutes){ this.listeRoutes = listeRoutes; }

	public void addRoute(Route route){ this.listeRoutes.add(route); }

	public int getNbRoutes(){ return this.nbRoutes; }

	public ArrayList<Route> getListeRoutes() { return this.listeRoutes; }

	public  float getLongitude(){
		return this.longitude;
	}

	public float getLatitude(){ return this.latitude; }

	public String toString(){ return "Noeud = Long : "+this.longitude+" Lat : "+this.latitude; }

	public int getNumNoeud(){ return this.numNoeud; }
}
