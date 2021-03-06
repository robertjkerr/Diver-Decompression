package decolib;

import java.util.Arrays;

public abstract class planner {
	//API for JS/Java frontend/backend interaction.
	//Contains preset static methods in which bottom depth/time is submitted and the ascent profile is returned
	
	private static tracker new_tracker (double[][] gasMixes, double[] GFs) {
		//tissue partial pressure for breathing air for a long time at 1 bar ambient pressure
        double[] P = new double[] {0.8,0};
        double[][] cellPressures = new double[][] {P,P,P,P, P,P,P,P, P,P,P,P, P,P,P,P};

        //Initialise tracker
        tracker tracker = new tracker(1,gasMixes,cellPressures,1, 6, GFs);

		return tracker;
	}

	//Gets ascent profile
	public static int[][] deco_dive (double[][] gasMixes, double[] GFs, int[]... segments) {
		//Creates new tracker object
		tracker tracker = new_tracker(gasMixes, GFs);
		
		//Complete all bottom segments
		for (int[] segment: segments) {
			int depth = segment[0];
			int time = segment[1];
			tracker.bottom_segment(depth, time);
		}
		
		//get ascent profile
        int[][] ascent_profile = tracker.get_ascent_profile();

		return ascent_profile;
	}

	//Gets ascent profile and converts to string array
	public static String[] deco_dive_string(double[][] gasMixes, double[] GFs, int[]... segments) {
		int[][] ascent_profile = deco_dive(gasMixes, GFs, segments);
		int numStops = ascent_profile.length;
		String[] prof_string = new String[numStops];
		for (int i=0; i<numStops; i++) {
			prof_string[i] = Arrays.toString(ascent_profile[i]);} 
		return prof_string;
	}

	//Gets NDL for recreational dive
	public static double no_deco(int depth, double[][] gasMixes, double[] GFs) {
		tracker tracker = new_tracker(gasMixes, GFs);
		return tracker.get_NDL();
	}

	
}
