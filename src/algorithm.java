import tissues.compartments;

public class algorithm {
    //Decompression algorithm class
    //Determines the ascent profile for the current compartment status

    //Copy of compartments object 
    private compartments compartments;

    public algorithm (compartments compartments) {
        this.compartments = compartments.copy();
    }

    //Waits and advances time until the ascent ceiling changes
    private double wait (double dt) {
        double initCeiling = compartments.ceiling();
        double ceiling = compartments.ceiling();
        double t = 0;
        while (ceiling == initCeiling) {
            compartments.advT(dt);
            ceiling = compartments.ceiling();
            t++;}
        return t;
    }

    //Ascends compartments to new depth
    private void ascendToCeiling () {
        double newPAmb = compartments.ceiling()/10 + 1;
        compartments.changePAmb(newPAmb);
    }
    
    //Determines ascent profile. Simply ascends to ceiling, waits until ceiling changes, then ascends again
    public double[][] ascent_profile (double dt) {
        double depth = (compartments.pAmb-1)*10;
        depth = depth - depth%3;
        int depthSteps = (int) depth/3;
        //profile = {{depth,time},{depth,time}, ...}
        double[][] profile = new double[depthSteps][2];

        for (int i=0; i<depthSteps; i++) {
            ascendToCeiling();
            double stopDepth = compartments.ceiling();
            double stopTime = wait(dt);
            profile[i][1] = stopDepth;
            profile[i][1] = stopTime;
        }
        return profile;
    }
}
