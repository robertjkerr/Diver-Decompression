package DecoLib.tissues;

public class compartments_init {
    //Class for containing Buhlmann ZHL-16C tissue compartment data
    //Contains half-lives, A and B values for both inert gases

    public double[] halfLivesN2;
    public double[] AValsN2;
    public double[] BValsN2;
    public double[] halfLivesHe;
    public double[] AValsHe;
    public double[] BValsHe;

    public compartments_init () {
        halfLivesN2 = new double[] {44,8,12.5,18.5,27,38.3,54.3,77,
                                    109,146,187,239,305,390,498,635};
        AValsN2 = new double[] {1.2599,1,0.8618,0.7562,0.6667,0.5933,0.5282,0.4701,
                                0.4187,0.3798,0.3497,0.3223,0.2971,0.2737,0.2523,0.2327};
        BValsN2 = new double[] {0.505,0.6514,0.7222,0.7725,0.8125,0.8434,0.8693,0.891,
                                0.9092,0.9222,0.9319,0.9403,0.9477,0.9544,0.9602,0.9653};
        halfLivesHe = new double[] {1.5,3,4.7,7,10.2,14.5,20.5,29.1,
                                    41.1,55.1,70.6,90.2,115.1,147.2,187.9,239.6};
        AValsHe = new double[] {1.7435,1.3838,1.1925,1.0465,0.9226,0.8211,0.7309,0.6506,
                                0.5794,0.5256,0.484,0.446,0.4112,0.3788,0.3492,0.322};
        BValsHe = new double[] {0.1911,0.4295,0.5446,0.6265,0.6917,0.742,0.7841,0.8195,
                                0.8491,0.8703,0.886,0.8997,0.9118,0.9226,0.9321,0.9404};
    }

    public compartments get_compartments (double pAmb, double[] gasMix, double[][] cellPressures) {
        return new compartments(pAmb, gasMix, cellPressures, halfLivesN2, halfLivesHe, AValsN2, AValsHe, BValsN2, BValsHe);
    }
    
}
