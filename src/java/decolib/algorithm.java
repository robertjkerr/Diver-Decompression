package decolib;

import decolib.tissues.compartments;

public abstract class algorithm {
    //Decompression algorithm class
    //Determines the ascent profile for the current compartment status

    private static double dt;
    private static compartments compartments;
    //public double totalTime;

    public static int[] liveGas () {
        double[] gas = compartments.gasMix;
        int O2 = (int) ((1-gas[0]-gas[1])*100);
        int He = (int) (gas[1]*100);
        return new int[] {O2,He};
    }

    //Waits and advances time until the ascent ceiling changes
    private static double waitAtDepth () {
        double initCeiling = compartments.ceiling();
        double ceiling = compartments.ceiling();
        double t = 0;
        while (ceiling == initCeiling && compartments.realCeiling() > 0) {
            compartments.advT(dt);
            ceiling = compartments.ceiling();
            t=t+dt;}

        //totalTime = totalTime + t;
        //Account (conservatively) for ascent time (extra 1 min at depth)
        for (int i=0; i<60/dt; i++) {
            compartments.advT(dt);
            //totalTime = totalTime + dt;
        }    
        return t;
    }

    //Checks if a richer gas can be switched to (and switches gas if possible)
    private static void checkGas (double[][] gases) {
        double[] richGas = gases[0];
        for (double[] gas: gases){
            if (gas[0]>richGas[0] && compartments.pO2(gas)<=1.61) { //max pO2 1.61 to account for error
                richGas = gas;}}
        compartments.switchGas(richGas);
    }
    
    //Appends int[] element to an existing int[][] array
    private static int[][] append(int[][] inArr, int[] elem) {
        int[][] outArr = new int[inArr.length+1][2];
        for (int i=0; i<inArr.length +1; i++) {
            if (i<inArr.length) {
                outArr[i] = inArr[i];}
            else {
                outArr[i] = elem;}}
        return outArr;
    }

    //Determines ascent profile. Simply ascends to ceiling, waits until ceiling changes, then ascends again
    public static int[][] ascent_profile (compartments inCompartments, double[][] gases, double dtIn, int shallowStop) {
        dt = dtIn;
        compartments = inCompartments.copy();

        compartments.set_GFgrad(compartments.ceiling());
        int[][] profile = new int[][] {};
        double stopDepth = compartments.ceiling();
        double stopTime;

        //Account for ascent time (wait at bottom)
        double bottomDepth = (compartments.pAmb-1)*10;
        double ascentTime = (bottomDepth-stopDepth)*60/9;
        for (int i=0; i<ascentTime/dt; i++) {
            compartments.advT(dt);
            //totalTime = totalTime + dt;
        }

        while (compartments.realCeiling() > 0) {
            compartments.ascendToCeiling();
            checkGas(gases);
            stopDepth = compartments.ceiling();
            stopTime = waitAtDepth();
            profile = append(profile, new int[] 
                        {(int) stopDepth, 
                        (int) (stopTime/60 - (stopTime/60)%1) + 1,
                        liveGas()[0]
                        });
        }
        return profile;
    }

    //Determines no deco limit by pushing time at depth until ceiling != 0
    public static double NDL (compartments inCompartments, double dtIn) {
        dt = dtIn;
        compartments = inCompartments.copy();

        double NDL = 0;
        while (compartments.ceiling() == 0){
            compartments.advT(dt);
            NDL = NDL + dt;}
        //Returns NDL rounded up since it is rather conservative anyway
        return (int) (NDL/60 + (1-(NDL/60)%1));
    }
    
}