package decolib;

import java.util.Arrays;

public abstract class planner {
	//API for JS/Java frontend/backend interaction.
	//Contains preset static methods in which bottom depth/time is submitted and the ascent profile is returned
	
	private static tracker new_tracker (double[][] gasMixes) {
		//tissue partial pressure for breathing air for a long time at 1 bar ambient pressure
        double[] P = new double[] {0.8,0};
        double[][] cellPressures = new double[][] {P,P,P,P, P,P,P,P, P,P,P,P, P,P,P,P};

        //Initialise tracker
        double[] GFs = new double[] {0.5,0.8};
        tracker tracker = new tracker(1,gasMixes,cellPressures,1, 6, GFs);

		return tracker;
	}

	//Gets ascent profile
	public static int[][] deco_dive (int depth, int time, double[][] gasMixes) {
		//Creates new tracker object
		tracker tracker = new_tracker(gasMixes);
		
		//bottom segment and get ascent profile
		tracker.bottom_segment(45, 40);
        int[][] ascent_profile = tracker.get_ascent_profile();

		return ascent_profile;
	}

	//Gets ascent profile and converts to string array
	public static String[] deco_dive_string(int depth, int time) {//, double[][] gasMixes) {
		double[][] gasMixes = new double[][] {{21,00},{50,0}};
		int[][] ascent_profile = deco_dive(depth, time, gasMixes);
		int numStops = ascent_profile.length;
		String[] prof_string = new String[numStops];
		for (int i=0; i<numStops; i++) {
			prof_string[i] = Arrays.toString(ascent_profile[i]);} 
		
		return prof_string;
	}

	//Gets NDL for recreational dive
	public static double no_deco(int depth, double[][] gasMixes) {
		tracker tracker = new_tracker(gasMixes);
		return tracker.get_NDL();
	}

	/*
	//Sample deco dive
	public static String[] sample () {
		double[][] gasMixes = new double[][] {{21,0},{50,0}};
		return deco_dive_string(45,40,gasMixes);
	}*/
}
