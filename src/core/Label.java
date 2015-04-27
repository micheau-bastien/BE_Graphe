package core;

import java.util.Comparator;

/**
 * Created by Bastien on 10/04/15.
 */
public class Label implements Comparable<Label> {

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

    public int compareTo(Label label) {
        if (this.cout > label.getCout()){
            return 1;

        }else if (this.getCout() == label.getCout()){
            return 0;
        }else{
            return -1;
        }
    }




    // SETTERS
    public void setCout(int cout){  this.cout = cout;   }

    // GETTERS
    public int getCout(){   return this.cout;   }
    public int getNumPere(){   return this.numPere;   }
    public int getNumSommetCourant(){   return this.numSommetCourant;   }
    public boolean isMarked(){    return this.marquage;   }

}
