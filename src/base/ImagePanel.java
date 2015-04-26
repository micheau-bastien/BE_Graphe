package base ;

import javax.swing.* ;
import java.awt.* ;
import java.awt.event.* ;

/**
 *  Classe sans interet.
 */

public class ImagePanel extends JPanel implements MouseListener {

    static final long serialVersionUID = 0xdad1c001 ;

    private Image image;

    public ImagePanel() {
	super() ;
	this.addMouseListener(this) ;
    }
    
    public void setImage(Image image) {
	this.image = image;
    }
    
    public void paintComponent(Graphics g) {
	if(image != null) {
	    g.drawImage(image, 0, 0, this);
	}
    }
    
    public Dimension getPreferredSize() {
	int w, h;
	Dimension result ;

	if(image == null) {
	    result = new Dimension(0, 0);
	}
	else {
	    w = image.getWidth(null);
	    h = image.getHeight(null);
	    result = new Dimension(w > 0 ? w : 0, h > 0 ? h : 0);
	}

	return result ;
    }

    private int XClick ;
    private int YClick ;
    
    private boolean ClickIsHere ;

    // We use a dummy object for synchronization, to avoid interferences with the GUI system.
    private String lock = new String("") ;

    // Mouse Listener
    public void mouseEntered(MouseEvent e) { } ;
    public void mouseExited(MouseEvent e) { } ;
    public void mousePressed(MouseEvent e) { } ;
    public void mouseReleased(MouseEvent e) { } ;

    public void mouseClicked(MouseEvent e) {
	this.ClickIsHere = true ;
	this.XClick = e.getX() ;
	this.YClick = e.getY() ;

	synchronized (lock) {
	    lock.notify() ;
	}
    }

    /**
     *  Waits for a click. Returns true if a click really occured.
     */
    public boolean waitClick() {
	this.ClickIsHere = false ;
	
	try {
	    synchronized (lock) {
		// We wait until we are awaken by a click.
		while (!this.ClickIsHere) {
		    lock.wait() ;
		}
	    }
	    return true ;

	} catch (Exception e) {
	    return false ;
	}
    }

    public int getXClick() { return XClick ; }
    public int getYClick() { return YClick ; }
}
