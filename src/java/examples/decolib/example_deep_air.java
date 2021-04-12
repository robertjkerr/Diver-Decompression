package examples.decolib;

import decolib.tracker;
import java.util.Arrays;

/*
Demonstrates decompression profile for a "deep air" dive to 45m
Uses two EAN mixes for decompression
*/

public class example_deep_air {
    public static void main (String[] args){
        
        //tissue partial pressure for breathing air for a long time at 1 bar ambient pressure
        double[] P = new double[] {0.8,0};
        double[][] cellPressures = new double[][] {P,P,P,P, P,P,P,P, P,P,P,P, P,P,P,P};

        //Set air as bottom gas and EAN50 for deco
        double[][] gasMixes = new double[][] {{21,0},{50,0}};

        //Initialise tracker
        double[] GFs = new double[] {0.5,0.8};
        tracker tracker = new tracker(1,gasMixes,cellPressures,1, 6, GFs);
        
        //51m for 30mins
        tracker.bottom_segment(45, 40);
        
        //Gets decompression ascent profile
        int[][] ascent_profile = tracker.get_ascent_profile();
        for (int[] stop: ascent_profile) {
            System.out.println(Arrays.toString(stop));
        }
    }
}
