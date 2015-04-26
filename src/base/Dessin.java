package base ;

/**
 *   Classe abstraite pour dessiner a l'ecran.
 *   Deux implementations : une sous-classe DessinVisible qui dessine vraiment a l'ecran
 *   et une sous-classe DessinInvisible qui ne dessine rien (pour ne pas ralentir les tests avec l'affichage).
 */

import java.awt.* ;

public abstract class Dessin {

    protected Dessin() {} ;

    /**
     *  Fixe la largeur du crayon en pixel.
     */
    public void setWidth (int width) { }

    /**
     *  Fixe la couleur du crayon.
     */
    public void setColor (Color col) { }

    /**
     *  Indique les bornes de la fenetre graphique.
     *  Le calcul des coordonnees en pixel se fera automatiquement
     *  a l'appel des methodes drawLine et autres.
     *
     *  @param long1 longitude du bord gauche
     *  @param long2 longitude du bord droit
     *  @param lat1 latitude du bord bas
     *  @param lat2 latitude du bord haut
     *  
     */
    public void setBB (double long1, double long2, double lat1, double lat2) { }

    /**
     *  Trace un segment.
     *  @param long1 longitude du premier point
     *  @param lat1 latitude du premier point
     *  @param long2 longitude du second point
     *  @param lat2 latitude du second point
     */
    public void drawLine (float long1, float lat1, float long2, float lat2) { }

    /**
     *  Trace un point.
     *  @param lon longitude du point
     *  @param lat latitude du point
     *  @param width grosseur du point
     */
    public void drawPoint (float lon, float lat, int width) { }

    /**
     *  Ecrit du texte a la position indiquee.
     *  @param lon longitude du point ou positionner le texte.
     *  @param lat latitude du point ou positionner le texte.
     *  @param txt le texte a ecrire.
     */
    public void putText (float lon, float lat, String txt) { }

    /**
     *  Attend un clic de souris sur l'image. 
     *  Les coordonnees sont ensuite obtenues avec getClickLon et getClickLat
     *  @return true si un clic a bien ete obtenu, false sinon (en cas d'interruption forcee par exemple)
     */
    public boolean waitClick() { return false ; }

    // Dans la version abstraite, renvoie toujours 0.0
    public float getClickLon() { return 0.0f ; } ;
    public float getClickLat() { return 0.0f ; } ;
}
