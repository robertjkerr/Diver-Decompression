package DecoLib;

import DecoLib.tissues.*;
import java.util.Arrays;

public class tracker {
    /* Main package class */
    //Tissue compartment tracker
    //Amalgamates compartments and algorithm into one class
    public compartments compartments;
    private double dt;

    public double[][] gases;

    public tracker (double pAmb, double[][] gasMixes, double[][] cellPressures, 
                        double dt, double GFLo, double GFHi) {
        this.dt = dt;
        compartments_init compartments_init = new compartments_init();
        compartments = compartments_init.get_compartments(pAmb, gasMixes[0], cellPressures, GFLo, GFHi);
        this.gases = gasMixes;
    }

    //Retrieves ascent profile from algorithm
    public int[][] get_ascent_profile () {
        algorithm algorithm = new algorithm(compartments, dt, gases);
        return algorithm.ascent_profile();
    }

    //Loads tissues by setting depth constant for set time
    public void bottom_segment (double depth, double time) {
        compartments.changePAmb(depth/10 + 1);
        for (int i=0; i<time/dt; i++){
            compartments.advT(time);}
    }

    /*
    //Returns ascent ceiling
    public double get_ceiling() {
        return compartments.realCeiling();
    }
    */

    //Gets no decompression limit from algorithm
    /*
    public double get_NDL() {
        algorithm algorithm = new algorithm(compartments, dt, gases);
        return algorithm.NDL();
    }
    */
}