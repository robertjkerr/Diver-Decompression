package decolib.tissues;

public class cell {
	//Class for individual tissue compartment (cell)
	//Local subroutines find pressure differential (for both nitrogen and helium)
	// and ascent ceiling for an individual cell. Absolute ascent ceiling is max of all cells
	//Finds new cell inert gas pressures after a short time

	private double kN2;
	private double kHe;
	private double AN2;
	private double BN2;
	private double AHe;
	private double BHe;

	public double pAmb;
	public double[] gasMix; //{fN2,fHe}
	public double[] cellPres; //{pN2,pHe}
	public double realCeiling;
	public double GF;


	public cell (double GF, double pAmb, double[] gasMix, double[] cellPres, double halfTimeN2, double halfTimeHe, 
				double AN2, double BN2, double AHe, double BHe) {
		
		this.pAmb = pAmb;
		this.GF = GF;

		this.AN2 = AN2;
		this.BN2 = BN2;
		this.AHe = AHe;
		this.BHe = BHe;

		this.gasMix = gasMix;
		this.cellPres = cellPres;

		//Determining k values from half-times (in minutes)
		kN2 = 0.693147/(halfTimeN2*60);
		kHe = 0.693147/(halfTimeHe*60);
	}

	//Changes ambient pressure (i.e. depth change)
	public void changePAmb (double newPAmb) {
		pAmb = newPAmb;
	}
	
	//Finds pressure differential for both inert gases
	private double[] dPdt (){
		double pN2 = gasMix[0]*pAmb;
		double pHe = gasMix[1]*pAmb;
		//Basic eqn for determining pressure differential
		double dpN2 = kN2*(pN2 - cellPres[0]);
		double dpHe = kHe*(pHe - cellPres[1]);
		double[] dPdt = new double[] {dpN2,dpHe};
		return dPdt;
	}

	//Advances time for the tissue. Uses Schreiner equation.
	public void advT (double dt) {
		double[] dPdt = dPdt();
		double dpN2 = dPdt[0];
		double dpHe = dPdt[1];
		//Determines new cell pressure from pressure differential
		cellPres = new double[]{cellPres[0]+dpN2*dt, cellPres[1]+dpHe*dt};
	}

	//Changes gradient factor
	public void change_GF(double newGF) {
		GF = newGF;
	}

	//Determines ascent ceiling
	public double ceiling () {
		double A = ((AN2*cellPres[0])+(AHe*cellPres[1]))/(cellPres[0]+cellPres[1]);
		double B = ((BN2*cellPres[0])+(BHe*cellPres[1]))/(cellPres[0]+cellPres[1]);
		double ceiling = ((cellPres[0]+cellPres[1])-GF*A)*B/(B+GF*(1-B));

		realCeiling = (ceiling-1)*10;
		return realCeiling;
	}
}