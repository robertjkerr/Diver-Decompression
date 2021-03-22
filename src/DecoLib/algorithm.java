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

    public double[] liveGas () {
        return compartments.liveGas;
    }

    public double pO2 () {
        double pAmb = compartments.getPAmb();
        double pO2 = pAmb * liveGas()[0];
        return pO2;
    }

    //Waits and advances time until the ascent ceiling changes
    private double waitAtDepth () {
        double initCeiling = compartments.ceiling();
        double ceiling = compartments.ceiling();
        double t = 0;
        while (ceiling == initCeiling && ceiling > 0) {
            compartments.advT(dt);
            ceiling = compartments.ceiling();
            t=t+dt;}
        return t;
    }

    //Ascends compartments to new depth
    private void ascendToCeiling () {
        double newPAmb;
        if (compartments.ceiling() < 3){
            newPAmb = (compartments.ceiling())/10 + 1;
        }
        else {
            newPAmb = (compartments.ceiling()+3)/10 + 1;
        }
        
        compartments.changePAmb(newPAmb);
    }

    //Checks if a richer gas can be switched to
    private void checkGas () {
        double[] richMix = liveGas();
        for (double[] gas: gases) {
            if (gas[0]>richMix[0] && pO2()<1.6) {
                richMix = gas;
                compartments.switchGas(richMix);
            }}
    }
    
    //Determines ascent profile. Simply ascends to ceiling, waits until ceiling changes, then ascends again
    public int[][] ascent_profile () {
        double depth = (compartments.pAmb-1)*10;
        depth = depth - depth%3;
        //Gets number of deco stops
        
        ascendToCeiling();
        compartments.set_GFgrad(compartments.ceiling());

        //Gets stop times
        int[][] profile = new int[][] {};
        double stopDepth = compartments.ceiling();

        while (stopDepth > 6) {
            ascendToCeiling();
            //Switch to richer gas if possible
            checkGas();
            stopDepth = compartments.ceiling();
            double stopTime = waitAtDepth();
            profile = append(profile, new int[] {(int) stopDepth, 
                        (int) (stopTime/60 - (stopTime/60)%1) + 1});
        }

        return profile;
    }

    //Determines no deco limit by pushing time at depth until ceiling != 0
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

    private int[][] append(int[][] inArr, int[] elem) {
        int[][] outArr = new int[inArr.length+1][2];
        for (int i=0; i<inArr.length +1; i++) {
            if (i<inArr.length) {
                outArr[i] = inArr[i];
            }
            else {
                outArr[i] = elem;
            }
        }
        return outArr;
    }
}
