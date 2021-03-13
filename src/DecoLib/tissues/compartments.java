package DecoLib.tissues;

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
    private double[] gasMix; //{fN2,fHe}
    public double[] liveGas;
    private int n;
    public double pAmb;

    //Enter gas mix as {%fO2,%fHe}
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
        this.liveGas = gasMix;
        this.gasMix = new double[] {(100-gasMix[0]-gasMix[1])/100, gasMix[1]/100};
        //Initialises all the tissues
        cells = new cell[n];
        for (int i=0; i<n; i++) {
            cells[i] = new cell(pAmb, this.gasMix, cellPressures[i], halfLivesN2[i], halfLivesHe[i],
                                AValsN2[i], BValsN2[i], AValsHe[i], BValsHe[i]);}
    }

    //Advances time for all compartments. R is rate of change of depth.
    public void advT (double dt) {
        for (cell cell: cells) {
            cell.advT(dt);}
    }

    //Changes ambient pressure for all tissues
    public void changePAmb (double newPAmb) {
        pAmb = newPAmb;
        for (cell cell: cells) {
            cell.changePAmb(pAmb);}
    }

    //Finds the (conservative) ascent ceiling of all the tissue cells
    public double ceiling () {
        double ceiling = 0;
        for (cell cell: cells) {
            if (cell.ceiling() > ceiling) {
                ceiling = cell.ceiling();}}
        ceiling = ceiling - ceiling%3;
        return ceiling;
    }

    //Gets the absolute ceiling
    public double realCeiling () {
        double ceiling = 0;
        for (cell cell: cells) {
            cell.ceiling();
            if (cell.realCeiling > ceiling) {
                ceiling = cell.realCeiling;}}
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

    //Changes gas being breathed
    public void switchGas (double[] newGas) {
        this.gasMix = new double[] {(100-newGas[0]-newGas[1])/100, newGas[1]/100};
        for (cell cell: cells) {
            cell.gasMix = this.gasMix;
        }
    }

    public double getPAmb () {
        return pAmb;
    }
}
