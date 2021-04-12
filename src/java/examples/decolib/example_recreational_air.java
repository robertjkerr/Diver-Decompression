package examples.decolib;

import decolib.tracker;

/*
Basic example to demonstrate a 15m dive using air (EAN21) as the gas.
Gets No Decompression Limit
*/


public class example_recreational_air {
    public static void main (String[] args){
        
        //tissue partial pressure for breathing air for a long time at 1 bar ambient pressure
        double[] P = new double[] {0.8,0};
        double[][] cellPressures = new double[][] {P,P,P,P, P,P,P,P, P,P,P,P, P,P,P,P};
        
        //Set air as gas
        double[][] gasMixes = new double[][] {{21,0}};

        //Initialise tracker
        double[] GFs = new double[] {0.5,0.8};
        tracker tracker = new tracker(1,gasMixes,cellPressures,1, 6, GFs);

        //Get NDL for 15m
        tracker.bottom_segment(15,0);
        System.out.println(tracker.get_NDL());
    }
}
