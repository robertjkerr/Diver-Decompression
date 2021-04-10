package decolib;

import decolib.tissues.*;

public class tracker {
    /* Main package class */
    //Tissue compartment tracker
    //Amalgamates compartments and algorithm into one class
    public compartments compartments;
    private double dt;
    private int shallowStop;

    public double[][] gases;

    public tracker (double pAmb, double[][] gasMixes, double[][] cellPressures, 
                        double dt, int shallowStop, double[] GFs) {
        this.dt = dt;
        this.shallowStop = shallowStop;
        compartments_init compartments_init = new compartments_init();
        compartments = compartments_init.get_compartments(pAmb, gasMixes[0], cellPressures, GFs[0], GFs[1]);
        gases = gasMixes;
    }

    //Retrieves ascent profile from algorithm
    public int[][] get_ascent_profile () {
        return algorithm.ascent_profile(compartments, gases, dt, shallowStop);
    }

    //Loads tissues by setting depth constant for set time
    public void bottom_segment (double depth, double time) {
        compartments.changePAmb(depth/10 + 1);
        for (int i=0; i<time*60/dt; i++){
            compartments.advT(dt);}
        //System.out.println(compartments.realCeiling());
    }

    //Returns ascent ceiling
    public double get_ceiling() {
        return compartments.realCeiling();
    }
    
    //Gets no decompression limit from algorithm
    public double get_NDL() {
        return algorithm.NDL(compartments, dt);
    }
    
}