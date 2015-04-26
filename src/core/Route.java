package core;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import base.Couleur;
import base.Descripteur;
import base.Utils;
import sun.security.krb5.internal.crypto.Des;

public class Route {
/*
 * 
 *  [0]      = Numéro de zone du noeud destination (8 bits)
 [1-3]    = Numéro du noeud destination, dans la zone donnée (24 bits, big endian)
 [4-6]    = Numéro de descripteur (24 bits)
 [7-8]    = Longueur de l'arête (16 bits), en mètres, prenant en compte tous les segments.
 [9-10]   = Nombre de segments (16 bits), éventuellement 0.
 [11-...] = Segments
 */
	private int zoneDest;
	private int noeudDest;
	private int numDescr;
	private Descripteur descripteur;
	private int longueur;
	private int nbSegments;
	private ArrayList<Segment> segments = new ArrayList<Segment>();
	public static int nbRoutes = 0;

	// ATTENTON CE CONSTRUCTEUR EST FOIREUX !!!
	public Route(DataInputStream dis) throws IOException{
		this.zoneDest = dis.readUnsignedByte() ;
	    // numero de noeud du successeur
	    this.noeudDest = Utils.read24bits(dis) ;
	    // descripteur de l'arete
	    this.numDescr = Utils.read24bits(dis) ;
	    // longueur de l'arete en metres
	    this.longueur  = dis.readUnsignedShort() ;
	    // Nombre de segments constituant l'arete
	    this.nbSegments = dis.readUnsignedShort() ;
	    nbRoutes++ ;
	    // Chaque segment est dessine'
	    for (int i = 0 ; i < nbSegments ; i++) {
			Segment segment = new Segment(dis);
	    }
	}

	public Route(int zoneDest, int noeudDest, Descripteur descripteur, int longueur, int nbSegments, ArrayList<Segment> segments){
		this.zoneDest = zoneDest;
		this.noeudDest = noeudDest;
		this.descripteur = descripteur;
		this.longueur = longueur;
		this.nbSegments = nbSegments;
		this.segments = segments;
	}

	public int getNumDescr(){
		return this.numDescr;
	}
	public String toString(){
		return "Route = Longueur : " + this.longueur + "Dest : " + this.noeudDest;
	}
	public int getNoeudDest(){ return this.noeudDest; }
	public Descripteur getDescripteur(){ return this.descripteur; }
	public int getLongueur(){ return this.longueur; }
}
