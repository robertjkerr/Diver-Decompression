package examples;

import DecoLib.tracker;
import java.util.Arrays;

public class example_DecoLib {
    public static void main (String[] args){
        double[] P = new double[] {0.6,0.2};
        double[][] cellPressures = new double[][] {P,P,P,P, P,P,P,P, P,P,P,P, P,P,P,P};
        //double[][] gasMixes = new double[][] {{21,0}};
        double[][] gasMixes = new double[][] {{12,65},{35,25},{50,0},{100,0}};

        tracker tracker = new tracker(1,gasMixes,cellPressures,1, 0.3,0.85);

        tracker.bottom_segment(90, 30);

        //System.out.println(tracker.get_NDL());
        
        
        int[][] ascent_profile = tracker.get_ascent_profile();
        for (int[] stop: ascent_profile) {
            System.out.println(Arrays.toString(stop));
        }
    }
}