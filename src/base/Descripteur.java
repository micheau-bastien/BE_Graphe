package base ;

import java.io.* ;

/**
 *   Classe representant un descripteur de chemin, i.e,
 *   le nom et le type de la voie, la vitesse max, etc.
 */
public class Descripteur {

    // Type de la voie.
    // Voir http://wiki.openstreetmap.org/wiki/Highway_tag_usage

    /*
     * motorway       -> 'a'
     * trunk          -> 'b'
     * primary        -> 'c'
     * secondary      -> 'd'
     * motorway_link  -> 'e'
     * trunk_link     -> 'f'
     * primary_link   -> 'g'
     * secondary_link -> 'h'
     * tertiary       -> 'i'
     * residential    -> 'j'
     * unclassified   -> 'k'
     * road           -> 'l'
     * living_street  -> 'm'
     * service        -> 'n'
     * roundabout     -> 'o'
     *
     * Celui-ci n'est pas une route :
     * coastline      -> 'z'
    */


    private char type ;

    private boolean sensUnique ;

    // Vitesse max en hm/h
    private int vitMax ;

    private String nom ;

    public boolean isSensUnique() { return sensUnique ; }

    /** @return Vitesse maximale sur cette voie, en km/h */
    public int vitesseMax() { return vitMax ; }

    /** @return Le nom de la voie (e.g. Rue Casimir, Avenue du Ptit Kawa) */
    public String getNom() { return nom ; }

    public char getType() { return type ; }

    public Descripteur (DataInputStream dis) throws IOException {
		this.type = (char)dis.readUnsignedByte() ;
		int x = dis.readUnsignedByte() ;
		this.sensUnique = (x & 0x80) > 0 ;
		this.vitMax = (x & 0x7F) * 5 ;
		this.nom = dis.readUTF() ;
    }

	public int getVitMax(){
		return this.vitMax;
	}
    public String toString() {
		String oneway = "" ;
		
		if (this.sensUnique) { oneway = " (oneway) " ; }
		return showType() + " : " + nom + " " + oneway + vitMax + " km/h max " ;
    }

    public String showType() {
	String result = "unknown" ;
	switch (this.type) {
	case 'a': result = "autoroute" ; break ;
	case 'b':
	case 'c':
	case 'd':
	case 'e':
	case 'f':
	case 'g':
	case 'h':
	case 'i':
	case 'j':
	case 'k':
	case 'l':
	case 'm':
	case 'n':
	case 'o': result = "route" ; break ;
	case 'z': result = "cote" ; break ;
	default : break ;
	}

	return result ;
    }

}
