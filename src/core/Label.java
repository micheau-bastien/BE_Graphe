package core;

/**
 * Created by Bastien on 10/04/15.
 */
public class Label {

    private boolean marquage;
    private int cout ;
    private int numPere ;
    private int numSommetCourant;

    public Label(int numPere, int numSommetCourant, int cout) {
        this.numPere =  numPere ;
        this.numSommetCourant = numSommetCourant ;
        this.marquage = false ;
        this.cout = cout;
    }

    public Label(int numPere, int numSommetCourant) {
        this.numPere =  numPere ;
        this.numSommetCourant = numSommetCourant ;
        this.marquage = false ;
        this.cout = Integer.MAX_VALUE;
    }

    // SETTERS
    public void setCout(int cout){  this.cout = cout;   }

    // GETTERS
    public int getCout(){   return this.cout;   }
    public int getNumPere(){   return this.numPere;   }
    public int getNumSommetCourant(){   return this.numSommetCourant;   }
    public boolean isMarked(){    return this.marquage;   }

}
