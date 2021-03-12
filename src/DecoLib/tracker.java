package DecoLib;

import DecoLib.tissues.*;

public class tracker {
    //Tissue compartment tracker
    //Amalgamates compartments and algorithm into one class


    private algorithm algorithm;
    private compartments compartments;
    private double dt;

    public tracker (double pAmb, double[] gasMix, double[][] cellPressures, double dt) {
        this.dt = dt;
        compartments_init compartments_init = new compartments_init();
        compartments = compartments_init.get_compartments(pAmb, gasMix, cellPressures);
        
    }

    public double[][] get_ascent_profile () {
        algorithm = new algorithm(compartments, dt);
        return algorithm.ascent_profile();
    }

    //Loads tissues by setting depth constant for set time
    //Simulates bottom segment of dive
    public void bottom_segment (double depth, double time) {
        compartments.changePAmb(depth/10 + 1);
        for (int i=0; i<time*60/dt; i++){
            compartments.advT(dt);}
    }

    public double get_ceiling() {
        return compartments.ceiling();
    }

    
}
