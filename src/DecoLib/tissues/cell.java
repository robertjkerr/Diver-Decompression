package DecoLib.tissues;

//import java.lang.Math;

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
		//double t = (pAmb-newPAmb)*60/3;
		//advT(t,3/60);
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

	/*
	private double[] ppGases () {
		double pN2 = pAmb * gasMix[0];
		double pHe = pAmb * gasMix[1];
		double pO2 = pAmb * (1-gasMix[0]-gasMix[1]);
		return new double[] {pN2, pHe, pO2};
	}
	*/

	//Advances time for the tissue. Assumes constant dP/dt for a short time
	public void advT (double dt) {
		double[] dPdt = dPdt();
		double dpN2 = dPdt[0];
		double dpHe = dPdt[1];
		//Determines new cell pressure from pressure differential
		cellPres = new double[]{cellPres[0]+dpN2*dt, cellPres[1]+dpHe*dt};

		//Schreiner
		//cellPres[0] = ppGases()[0] + R*(t - 1/kN2) - (ppGases()[0] - cellPres[0] - R/kN2)*Math.exp(-kN2*t);
		//cellPres[1] = ppGases()[1] + R*(t - 1/kHe) - (ppGases()[1] - cellPres[1] - R/kHe)*Math.exp(-kHe*t);

	}

	//Determines ascent ceiling
	public double ceiling () {
		double A = ((AN2*cellPres[0])+(AHe*cellPres[1]))/(cellPres[0]+cellPres[1]);
		double B = ((BN2*cellPres[0])+(BHe*cellPres[1]))/(cellPres[0]+cellPres[1]);
		double ceiling = ((cellPres[0]+cellPres[1])-A)*B;
		realCeiling = (ceiling-1)*10;
		ceiling = realCeiling+3;
		if (ceiling > 3 && ceiling < 6) {
			ceiling = 6;
		}
		return ceiling;
	}
}