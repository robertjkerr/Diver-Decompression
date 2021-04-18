package decolib.tissues;

public class compartments_init {
    //Class for containing Buhlmann ZHL-16C tissue compartment data
    //Contains half-lives, A and B values for both inert gases

    public double[] halfLivesN2;
    public double[] AValsN2;
    public double[] BValsN2;
    public double[] halfLivesHe;
    public double[] AValsHe;
    public double[] BValsHe;
    public double GFLo;
    public double GFHi;

    public compartments_init () {
        halfLivesN2 = new double[] {4, 8, 12, 18, 27, 38, 54, 77, 109, 146, 187, 239, 305, 390, 498, 635};
        AValsN2 = new double[] {1.23, 1, 0.86, 0.76, 0.62, 0.5, 0.44, 0.4, 0.38, 0.35, 0.33, 0.31, 0.28, 0.26, 0.25, 0.23};
        BValsN2 = new double[] {0.51, 0.65, 0.72, 0.78, 0.81, 0.84, 0.87, 0.89, 0.9, 0.92, 0.93, 0.94, 0.94, 0.95, 0.96, 0.97};
        halfLivesHe = new double[] {1.51, 3.02, 4.72, 6.99, 10.21, 14.48, 20.53, 29.11, 41.20, 55.19, 70.69, 90.34, 115.29, 147.42, 188.24, 240.03};
        AValsHe = new double[] {1.74, 1.38, 1.19, 1.05, 0.92, 0.82, 0.73, 0.65, 0.6, 0.55, 0.53, 0.52, 0.52, 0.52, 0.52, 0.51};
        BValsHe = new double[] {0.42, 0.57, 0.65, 0.72, 0.76, 0.8, 0.83, 0.86, 0.88, 0.89, 0.9, 0.91, 0.91, 0.92, 0.92, 0.93};
    }

    public compartments get_compartments (double pAmb, double[] gasMix, double[][] cellPressures, double GFLo, double GFHi) {
        return new compartments(GFLo, GFHi, pAmb, gasMix, cellPressures, halfLivesN2, halfLivesHe, AValsN2, AValsHe, BValsN2, BValsHe);
    }
    
}
