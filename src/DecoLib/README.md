Decompression library utilising Buhlmann ZHL-16C with gradient factors.

Use tracker class.

Example:

import DecoLib.tracker;
...
...
//Setup tissue compartment (cell) pressures
double[] P = new double[] {0.6,0.2}; //PPN2, PPHe
double[][] cellPressures = new double[][] {P,P,P,P, P,P,P,P, P,P,P,P, P,P,P,P};
//Setup gas mixes (FO2, FHe)
double[][] gasMixes = new double[][] {{12,65},{35,25},{50,0},{100,0}};
//Initialise tracker object (last two args GFLo and GFHi respectively)
tracker tracker = new tracker(1,gasMixes,cellPressures,1,0.3,0.85);
//90 metres for 30 mins
tracker.bottom_segment(90, 30);
//Returns stops and times
int[][] ascent_profile = tracker.get_ascent_profile();
