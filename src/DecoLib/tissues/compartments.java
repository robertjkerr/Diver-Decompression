package DecoLib.tissues;

public class compartments {
    //Collective class for all tissue compartments in the body
    //Subroutines allow all cells to advance time, and change ambient pressure across the compartment array

    private double[] halfLivesN2;
    private double[] halfLivesHe;
    private double[] AValsN2;
    private double[] AValsHe;
    private double[] BValsN2;
    private double[] BValsHe;
    private int n;
    private double GFgrad;
    private boolean firstStop = true;
    private double GFLo;
    private double GFHi;

    public cell[] cells;
    public double[] liveGas;
    public double[] gasMix; //{fN2,fHe}
    public double pAmb;
    
    //Enter gas mix as {%O2,%He}
    public compartments (double GFLo, double GFHi, double pAmb, double[] gasMix, double[][] cellPressures, 
                        double[] halfLivesN2, double[] halfLivesHe, 
                        double[] AValsN2, double[] AValsHe, 
                        double[] BValsN2, double[] BValsHe) {

        this.pAmb = pAmb;
        this.GFHi = GFHi;
        this.GFLo = GFLo;
        
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
            cells[i] = new cell(GFLo, pAmb, this.gasMix, cellPressures[i], halfLivesN2[i], halfLivesHe[i],
                                AValsN2[i], BValsN2[i], AValsHe[i], BValsHe[i]);}
    }

    //Advances time for all compartments. R is rate of change of depth.
    public void advT (double dt) {
        for (cell cell: cells) {
            cell.advT(dt);
        }
    }

    //Changes ambient pressure for all tissues
    public void changePAmb (double newPAmb) {
        pAmb = newPAmb;
        if (firstStop == false) {
            change_GF();}

        for (cell cell: cells) {
            cell.changePAmb(pAmb);}
    }

    //Finds the true (most aggressive) ceiling
    public double realCeiling () {
        double ceiling = 0;
        for (cell cell: cells) {
            if (cell.ceiling() > ceiling) {
                ceiling = cell.ceiling();}}
        return ceiling;
    }

    //Gets the rounded (more conservative) ceiling
    public double ceiling () {
        double ceiling = realCeiling();
        ceiling = ceiling + (3-ceiling%3);
        return ceiling;
    }

    //Returns a copy of compartments objects to project ahead for the algorithm
    public compartments copy () {
        double[][] cellPressures = new double[n][2];
        double[] newGas = new double[] {(1-gasMix[0]-gasMix[1])*100, gasMix[1]*100};

        for (int i=0; i<n; i++) {
            cellPressures[i] = cells[i].cellPres;}
        compartments copyObj = new compartments(GFLo, GFHi, pAmb, newGas, cellPressures, halfLivesN2, 
                                                halfLivesHe, AValsN2, AValsHe, BValsN2, BValsHe);
        return copyObj;
    }
    
    //Changes gas being breathed
    public void switchGas (double[] newGas) {
        gasMix = new double[] {(100-newGas[0]-newGas[1])/100, newGas[1]/100};
        liveGas = gasMix;
        for (cell cell: cells) {
            cell.gasMix = gasMix;}
    }
    
    //Changes Gradient Factor appropriate to GF line and depth
    private void change_GF () {
        double newGF = GFgrad*(pAmb-1.3)+GFHi;
        for (cell cell: cells) {
            cell.change_GF(newGF);}
    }

    //Sets the slope of the gradient factor line
    public void set_GFgrad (double firstStopDepth) {
        firstStop = false;
        double firstCeil = firstStopDepth/10 + 1;
        GFgrad = (GFHi-GFLo)/(1-firstCeil);
    }

    //Ascends to conservative ceiling
    public void ascendToCeiling () {
        double newPAmb = ceiling()/10 +1;
        changePAmb(newPAmb);
    }

    //Get pO2 of a gas {%O2,%He}
    public double pO2 (double[] gas) {
        double pO2 = pAmb * gas[0]/100;
        return pO2;
    }
}