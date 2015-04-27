package core;

import java.util.Comparator;

/**
 * Created by Bastien on 10/04/15.
 */
public class Label implements Comparable<Label> {

    private boolean marquage;
    private Integer cout ;
    private Integer numPere ;
    private Integer numSommetCourant;

    public Label(Integer numPere, Integer numSommetCourant, Integer cout) {
        this.numPere =  numPere ;
        this.numSommetCourant = numSommetCourant ;
        this.marquage = false ;
        this.cout = cout;
    }

    public Label(Integer numPere, Integer numSommetCourant) {
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

    //TOSTRING
    public String toString(){
        return "Label - Courant : "+this.numSommetCourant+" - Parent : "+this.numPere+" - Cout : "+this.cout + " - Mark : "+this.isMarked();
    }

    // SETTERS
    public void setCout(Integer cout){  this.cout = cout;   }
    public void setMarked() {   this.marquage = true;   }
    public void setNumPere(Integer numPere) {  this.numPere = numPere;  }

    // GETTERS
    public Integer getCout(){   return this.cout;   }
    public Integer getNumPere(){   return this.numPere;   }
    public Integer getNumSommetCourant(){   return this.numSommetCourant;   }
    public boolean isMarked(){    return this.marquage;   }

}
