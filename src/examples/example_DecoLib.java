package examples;

import DecoLib.tracker;
import java.util.Arrays;

public class example_DecoLib {
    public static void main (String[] args){
        double[] P = new double[] {0.8,0};
        //tissue partial pressure for breathing air for a long time at 1 bar ambient pressure
        double[][] cellPressures = new double[][] {P,P,P,P, P,P,P,P, P,P,P,P, P,P,P,P};
        //double[][] gasMixes = new double[][] {{18,45},{50,0},{100,0}};
        double[][] gasMixes = new double[][] {{12,65},{50,0},{100,0}};

        tracker tracker = new tracker(1,gasMixes,cellPressures,1, 6, new double[] {0.35,0.8});

        //tracker.bottom_segment(51, 45);
        tracker.bottom_segment(90, 30);
        
        int[][] ascent_profile = tracker.get_ascent_profile();
        for (int[] stop: ascent_profile) {
            System.out.println(Arrays.toString(stop));
        }
    }
}