package tissues;

public class cell {
	//Class for individual tissue compartment
	//Local subroutines find pressure differential (for both nitrogen and helium)
	//Finds new cell inert gas pressures after a short time

	private double kN2;
	private double kHe;
	private double AN2;
	private double BN2;
	private double AHe;
	private double BHe;
	public double pAmb;
	public double[] gasMix; //[fN2,fHe]
	public double[] cellPres; //[pN2,pHe]


	public cell (double pAmb, double[] gasMix, double[] cellPres, double halfTimeN2, double halfTimeHe, 
				double AN2, double BN2, double AHe, double BHe) {
		
		this.pAmb = pAmb;

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

	public void changePAmb (double newPAmb) {
		//Changes ambient pressure (i.e. depth change)
		pAmb = newPAmb;
	}

	//Finds pressure differential for both inert gases
	private double[] dPdt (){
		double pN2 = gasMix[0]*pAmb;
		double pHe = gasMix[1]*pAmb;
		//Basic eqn for determining pressure differential
		double dpN2 = kN2*(pN2 - cellPres[0]);
		double dpHe = kHe*(pHe - cellPres[1]);
		double[] dPdt = {dpN2,dpHe};
		return dPdt;
	}

	//Advances time for the tissue. Assumes constant dP/dt for a short time
	public void advT (double dt) {
		double[] dPdt = dPdt();
		double dpN2 = dPdt[0];
		double dpHe = dPdt[1];
		//Determines new cell pressure from pressure differential
		cellPres = new double[]{cellPres[0]+dpN2*dt, cellPres[1]+dpHe*dt};
	}

	public double ceiling () {
		double A = ((AN2*cellPres[0])+(AHe*cellPres[1]))/(cellPres[0]+cellPres[1]);
		double B = ((BN2*cellPres[0])+(BHe*cellPres[1]))/(cellPres[0]+cellPres[1]);
		double ceiling = ((cellPres[0]+cellPres[1])-A)*B;
		return ceiling;
	}
}