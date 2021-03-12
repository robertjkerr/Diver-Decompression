package DecoLib;

import DecoLib.tissues.compartments;

public class algorithm {
    //Decompression algorithm class
    //Determines the ascent profile for the current compartment status

    //Copy of compartments object 
    private compartments compartments;
    private double dt;

    public algorithm (compartments compartments, double dt) {
        this.compartments = compartments.copy();
        this.dt = dt;
    }

    //Waits and advances time until the ascent ceiling changes
    private double waitAtDepth () {
        double initCeiling = compartments.ceiling();
        double ceiling = compartments.ceiling();
        double t = 0;
        while (ceiling == initCeiling && ceiling > 2) {
            compartments.advT(dt);
            ceiling = compartments.ceiling();
            t=t+dt;}
        return t;
    }

    //Ascends compartments to new depth
    private void ascendToCeiling () {
        double newPAmb = compartments.ceiling()/10 + 1;
        compartments.changePAmb(newPAmb);
    }
    
    //Determines ascent profile. Simply ascends to ceiling, waits until ceiling changes, then ascends again
    public double[][] ascent_profile () {
        double depth = (compartments.pAmb-1)*10;
        depth = depth - depth%3;
        //Gets number of deco stops
        int depthSteps = (int) compartments.ceiling()/3;
        //profile = {{depth,time},{depth,time}, ...}
        double[][] profile = new double[depthSteps][2];

        //Gets stop times
        for (int i=0; i<depthSteps; i++) {
            ascendToCeiling();
            double stopDepth = compartments.ceiling();
            double stopTime = waitAtDepth();
            profile[i][0] = stopDepth;
            profile[i][1] = stopTime/60;
        }
        return profile;
    }
}
