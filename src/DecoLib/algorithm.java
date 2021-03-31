package DecoLib;

import DecoLib.tissues.compartments;

public class algorithm {
    //Decompression algorithm class
    //Determines the ascent profile for the current compartment status

    //Copy of compartments object 
    private compartments compartments;
    private double dt;
    private double[][] gases;

    public algorithm (compartments compartments, double dt, double[][] gases) {
        this.compartments = compartments.copy();
        this.dt = dt;
        this.gases = gases;
    }

    public int[] liveGas () {
        double[] gas = compartments.gasMix;
        int O2 = (int) ((1-gas[0]-gas[1])*100);
        int He = (int) (gas[1]*100);
        return new int[] {O2,He};
    }

    //Waits and advances time until the ascent ceiling changes
    private double waitAtDepth () {
        double initCeiling = compartments.ceiling();
        double ceiling = compartments.ceiling();
        double t = 0;
        while (ceiling == initCeiling && compartments.realCeiling() > 0) {
            compartments.advT(dt);
            ceiling = compartments.ceiling();
            t=t+dt;}
        return t;
    }

    //Ascends compartments to new depth
    private void ascendToCeiling () {
        double newPAmb = compartments.ceiling()/10 +1;
        compartments.changePAmb(newPAmb);
    }

    //Get pO2 of a gas {%O2,%He}
    public double pO2 (double[] gas) {
        double pAmb = compartments.pAmb;
        double pO2 = pAmb * gas[0]/100;
        return pO2;
    }

    //Checks if a richer gas can be switched to (and switches gas if possible)
    private void checkGas () {
        double[] richGas = gases[0];
        for (double[] gas: gases){
            if (gas[0]>richGas[0] && pO2(gas)<=1.61) { //max pO2 1.61 to account for error
                richGas = gas;}}
        compartments.switchGas(richGas);
    }
    
    //Determines ascent profile. Simply ascends to ceiling, waits until ceiling changes, then ascends again
    public int[][] ascent_profile () {
        compartments.set_GFgrad(compartments.ceiling());
        int[][] profile = new int[][] {};
        double stopDepth = compartments.ceiling();
        double stopTime;

        while (compartments.realCeiling() > 0) {
            ascendToCeiling();
            checkGas();
            stopDepth = compartments.ceiling();
            stopTime = waitAtDepth();
            profile = append(profile, new int[] 
                        {(int) stopDepth, 
                        (int) (stopTime/60 - (stopTime/60)%1) + 1
                        //liveGas()[0]
                        });
        }
        return profile;
    }

    //Determines no deco limit by pushing time at depth until ceiling != 0
    /*
    public double NDL () {
        double NDL = 0;
        if (compartments.ceiling() == 0) {
            double ceiling = 0;
            while (ceiling == 0) {
                compartments.advT(dt);
                NDL = NDL + dt;
                ceiling = compartments.ceiling();}
        }
        return NDL/60;
    }
    */

    //Appends int[] element to an existing int[][] array
    private int[][] append(int[][] inArr, int[] elem) {
        int[][] outArr = new int[inArr.length+1][2];
        for (int i=0; i<inArr.length +1; i++) {
            if (i<inArr.length) {
                outArr[i] = inArr[i];}
            else {
                outArr[i] = elem;}}
        return outArr;
    }
}
