package base ;

import javax.swing.* ;
import java.awt.* ;
import java.awt.image.* ;

/**
 *   Cette implementation de la classe Dessin produit vraiment un affichage
 *   (au contraire de la classe DessinInvisible).
 */

public class DessinVisible extends Dessin {
    
    private final ImagePanel panel ;
    private final Graphics2D gr ;

    private float long1 ;
    private float long2 ;
    private float lat1 ;
    private float lat2 ;
    private final float width ;
    private final float height ;

    private boolean bb_is_set ;

    /**
     *  Cree et affiche une nouvelle fenetre de dessin.
     */
    public DessinVisible (int largeur, int hauteur) {
	JFrame frame = new JFrame ("Visualisation de graphe");
	BufferedImage img = new BufferedImage (largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR) ;
	this.gr    = img.createGraphics() ;
	this.panel = new ImagePanel () ;

	this.panel.setImage(img) ;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(largeur + 45 , hauteur + 45);
	
	this.bb_is_set = false ;
	
	this.width = largeur ;
	this.height = hauteur ;

	this.long1 = (float)0.0 ;
	this.long2 = (float)largeur ;
	this.lat1  = (float)0.0 ;
	this.lat2  = (float)hauteur ;

	frame.setContentPane(this.panel) ;  
        frame.setVisible(true);

	this.setColor (Color.white) ;
	gr.fillRect(0,0,largeur,hauteur) ;
	this.repaint() ;
    }

    private void repaint() {
	panel.repaint() ;
    }

    public void setWidth (int width) {
	this.gr.setStroke(new BasicStroke(width)) ;
    }

    public void setColor (Color col) {
	this.gr.setColor (col) ;
    }

    public void setBB (double long1, double long2, double lat1, double lat2) {	
	this.long1 = (float)long1 ;
	this.long2 = (float)long2 ;
	this.lat1 = (float)lat1 ;
	this.lat2 = (float)lat2 ;
	this.bb_is_set = true ;
    }

    private int projx(float lon) {
	return (int)(width * (lon - this.long1) / (this.long2 - this.long1)) ;
    }

    private int projy(float lat) {
	return (int)(height * (1 - (lat - this.lat1) / (this.lat2 - this.lat1))) ;
    }

    private void checkBB() {
	if (!this.bb_is_set) {
	    throw new Error("Classe DessinVisible : vous devez invoquer la methode setBB avant de dessiner.") ;
	}
    }

    public void drawLine (float long1, float lat1, float long2, float lat2) {
	this.checkBB() ;
	int x1 = this.projx(long1) ;
	int x2 = this.projx(long2) ;
	int y1 = this.projy(lat1) ;
	int y2 = this.projy(lat2) ;

	gr.drawLine(x1, y1, x2, y2) ;
	this.repaint() ;
    }

    public void drawPoint (float lon, float lat, int width) {
	this.checkBB() ;
	int x = this.projx(lon) - width / 2 ;
	int y = this.projy(lat) - width / 2 ;
	gr.fillOval (x, y, width, width) ;
	this.repaint() ;
    }

    public void putText (float lon, float lat, String txt) {
	this.checkBB() ;
	int x = this.projx(lon) ;
	int y = this.projy(lat) ;
	gr.drawString (txt, x, y) ;
	this.repaint() ;	
    }

    public boolean waitClick() { 
	return this.panel.waitClick () ;
    }

    public float getClickLon() {
	float x = this.panel.getXClick() ;
	return (this.long1 + (x / width) * (this.long2 - this.long1)) ;
    }

    public float getClickLat() {
	float y = this.panel.getYClick() ;
	return (this.lat1 + (1 - (y / height)) * (this.lat2 - this.lat1)) ;
    }

}
