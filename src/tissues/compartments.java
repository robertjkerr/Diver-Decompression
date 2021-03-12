package tissues;

public class compartments {
    //Collective class for all tissue compartments in the body
    //Subroutines allow all cells to advance time, and change ambient pressure across the compartment array

    public cell[] cells;
    private double[] halfLivesN2;
    private double[] halfLivesHe;
    private double[] AValsN2;
    private double[] AValsHe;
    private double[] BValsN2;
    private double[] BValsHe;
    private double[] gasMix;
    private int n;
    public double pAmb;

    public compartments (double pAmb, double[] gasMix, double[][] cellPressures, 
                        double[] halfLivesN2, double[] halfLivesHe, 
                        double[] AValsN2, double[] AValsHe, 
                        double[] BValsN2, double[] BValsHe) {

        this.pAmb = pAmb;
        
        this.halfLivesN2 = halfLivesN2;
        this.halfLivesHe = halfLivesHe;
        this.AValsN2 = AValsN2;
        this.BValsN2 = BValsN2;
        this.AValsHe = AValsHe;
        this.BValsHe = BValsHe;

        n = halfLivesN2.length;
        this.gasMix = gasMix;
        //Initialises all the tissues
        cells = new cell[n];
        for (int i=0; i<n; i++) {
            cells[i] = new cell(pAmb, gasMix, cellPressures[i], halfLivesN2[i], halfLivesHe[i],
                                AValsN2[i], BValsN2[i], AValsHe[i], BValsHe[i]);}
    }

    //Advances time for all compartments
    public void advT (double dt) {
        for (cell cell: cells) {
            cell.advT(dt);}
    }

    //Changes ambient pressure for all tissues
    public void changePAmb (double pAmb) {
        this.pAmb = pAmb;
        for (cell cell: cells) {
            cell.changePAmb(pAmb);}
    }

    public double ceiling () {
        double ceiling = 0;
        for (cell cell: cells) {
            if (cell.ceiling() > ceiling) {
                ceiling = cell.ceiling();}}

        ceiling = 3 - ceiling%3 + ceiling;
        return ceiling;
    }

    //Returns a copy of compartments objects to project ahead for the algorithm
    public compartments copy () {
        double[][] cellPressures = new double[n][2];
        for (int i=0; i<n; i++) {
            cellPressures[i] = cells[i].cellPres;}
        compartments copyObj = new compartments(pAmb, gasMix, cellPressures, halfLivesN2, halfLivesHe, 
                                                AValsN2, AValsHe, BValsN2, BValsHe);
        return copyObj;
    }
}
