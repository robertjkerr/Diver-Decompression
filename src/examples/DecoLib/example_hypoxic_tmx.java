package examples.DecoLib;

import DecoLib.tracker;
import java.util.Arrays;

/*
Demonstrates decompression profile for a heavy Hypoxic Tri-Mix dive to 110m
Uses two Triox and two Nitrox gases for travel/decompression
*/

public class example_hypoxic_tmx {
    public static void main (String[] args){
        
        //tissue partial pressure for breathing air for a long time at 1 bar ambient pressure
        double[] P = new double[] {0.8,0};
        double[][] cellPressures = new double[][] {P,P,P,P, P,P,P,P, P,P,P,P, P,P,P,P};

        //Set Tmx 10/80 and deco/travel gases
        double[][] gasMixes = new double[][] {{10,80},{21,35},{35,25},{50,0},{100,0}};

        //Initialise tracker
        double[] GFs = new double[] {0.5,0.8};
        tracker tracker = new tracker(1,gasMixes,cellPressures,1, 6, GFs);
        
        //110m for 20mins
        tracker.bottom_segment(110, 20);
        
        //Gets decompression ascent profile
        int[][] ascent_profile = tracker.get_ascent_profile();
        for (int[] stop: ascent_profile) {
            System.out.println(Arrays.toString(stop));
        }
    }
}
