package core;

/**
 * Created by Bastien on 17/05/15.
 */
public class LabelStar extends Label {
    protected float estimation;
    protected boolean isTime;

    public LabelStar (Integer numPere, Integer numSommetCourant, float cout, boolean marked){
        super(numPere, numSommetCourant, cout, marked);
    }
    public LabelStar (Integer numPere, Integer numSommetCourant, float cout, boolean marked, float estimation, boolean isTime){
        super(numPere, numSommetCourant, cout, marked);
        this.isTime = isTime;
        if (this.isTime) {
            this.estimation = estimation*60/(130*1000);
        }else{
            this.estimation = estimation;
        }
    }

    public LabelStar (Integer numPere, Integer numSommetCourant, float cout, boolean marked, Noeud dep, Noeud arr, boolean isTime){
        super(numPere, numSommetCourant, cout, marked);
        this.isTime = isTime;
        this.setEstimation(arr,dep);
    }

    public void setEstimation(Noeud noeudDest, Noeud noeudDep){
        float estimBrut = (float)Graphe.distance(noeudDep.getLongitude(), noeudDep.getLatitude(), noeudDest.getLongitude(), noeudDest.getLatitude());
        if (this.isTime) {
            this.estimation = estimBrut*60/(130*1000);
        }else{
            this.estimation = estimBrut;
        }
    }

    public float getEstimation(){   return this.estimation;  }

    @Override
    public int compareTo(Label label)  {
        if (this.getCout()+this.getEstimation() > label.getCout()+label.getEstimation()){
            return 1;
        }else if (this.getCout()+this.getEstimation() == label.getCout()+label.getEstimation()){
            return 0;
        }else{
            return -1;
        }
    }
}
