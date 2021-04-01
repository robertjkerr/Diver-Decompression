package examples;

import DecoLib.tracker;
import java.util.Arrays;

public class example_DecoLib {
    public static void main (String[] args){
        double[] P = new double[] {0.8,0};
        //tissue partial pressure for breathing air for a long time at 1 bar ambient pressure
        double[][] cellPressures = new double[][] {P,P,P,P, P,P,P,P, P,P,P,P, P,P,P,P};
        double[][] gasMixes = new double[][] {{21,35},{50,0}};
        //double[][] gasMixes = new double[][] {{12,65},{21,35},{35,25},{50,0},{100,0}};

        double[] GFs = new double[] {0.5,0.8};
        tracker tracker = new tracker(1,gasMixes,cellPressures,1, 6, GFs);

        tracker.bottom_segment(51, 45);
        //tracker.bottom_segment(90, 30);
        
        int[][] ascent_profile = tracker.get_ascent_profile();
        for (int[] stop: ascent_profile) {
            System.out.println(Arrays.toString(stop));
        }
    }
}