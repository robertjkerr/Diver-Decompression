package examples.DecoLib;

import DecoLib.tracker;
import java.util.Arrays;

/*
Demonstrates decompression profile for a Normoxic Tri-Mix dive to 60m
Uses standard gases for bottom and deco
*/

public class example_normoxic_tmx {
    public static void main (String[] args){
        
        //tissue partial pressure for breathing air for a long time at 1 bar ambient pressure
        double[] P = new double[] {0.8,0};
        double[][] cellPressures = new double[][] {P,P,P,P, P,P,P,P, P,P,P,P, P,P,P,P};

        //Set Tmx 18/45 as bottom gas and EAN50 and Oxygen for deco
        double[][] gasMixes = new double[][] {{18,45},{50,0},{100,0}};

        //Initialise tracker
        double[] GFs = new double[] {0.5,0.8};
        tracker tracker = new tracker(1,gasMixes,cellPressures,1, 6, GFs);
        
        //60m for 30mins
        tracker.bottom_segment(60, 30);
        
        //Gets decompression ascent profile
        int[][] ascent_profile = tracker.get_ascent_profile();
        for (int[] stop: ascent_profile) {
            System.out.println(Arrays.toString(stop));
        }
    }
}
