package core;

import java.util.Comparator;

/**
 * Created by Bastien on 10/04/15.
 */
public class Label implements Comparable<Label> {

    private boolean marquage;
    private float cout ;
    private Integer numPere ;
    private Integer numSommetCourant;
    protected float estimation = 0;
    protected boolean isTime;

    public Label(Integer numPere, Integer numSommetCourant, float cout, boolean marked, boolean isTime) {
        this.numPere =  numPere ;
        this.numSommetCourant = numSommetCourant ;
        this.marquage = marked ;
        this.cout = cout;
        this.isTime = isTime;
    }

    public Label(Integer numPere, Integer numSommetCourant, float cout, boolean marked) {
        this.numPere =  numPere ;
        this.numSommetCourant = numSommetCourant ;
        this.marquage = marked ;
        this.cout = cout;
    }

    public int compareTo(Label label)  {
        if (this.getCout()+this.getEstimation() > label.getCout()+label.getEstimation()){
            return 1;
        }else if (this.getCout()+this.getEstimation() == label.getCout()+label.getEstimation()){
            if (this.getEstimation() > label.getEstimation()){
                return  1;
            }else if (this.getEstimation() == label.getEstimation()) {
                return 0;
            }else{
                return -1;
            }
        }else{
            return -1;
        }
    }

    public float getEstimation(){   return this.estimation; }

    public void setEstimation(Noeud noeudDest, Noeud noeudDep){
        float estimBrut = (float)Graphe.distance(noeudDep.getLongitude(), noeudDep.getLatitude(), noeudDest.getLongitude(), noeudDest.getLatitude());
        if (this.isTime) {
            this.estimation = (estimBrut/(1000*130/60));
        }else{
            this.estimation = estimBrut;
        }
    }

    //TOSTRING
    public String toString(){
        return "Label - Courant : "+this.numSommetCourant+" - Parent : "+this.numPere+" - Cout : "+this.cout + " - Mark : "+this.isMarked() + " || Estimation : "+this.getEstimation();
    }

    // SETTERS
    public void setCout(float cout){  this.cout = cout;   }
    public void setMarked() {   this.marquage = true;   }
    public void setNumPere(Integer numPere) {  this.numPere = numPere;  }


    // GETTERS
    public float getCout(){   return this.cout;   }
    public Integer getNumPere(){   return this.numPere;   }
    public Integer getNumSommetCourant(){   return this.numSommetCourant;   }
    public boolean isMarked(){    return this.marquage;   }

}
