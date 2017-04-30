/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: This runs the diffusion and applies the initial sun radiation
 * Date: 4/2/17
 */

package diffusion;

import sun.Sun;
import diffusionBase.Diffusion_Base;
/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: This class inherits from the DiffusionBase and implements the
 * items specific to it: TPDOHP = Text, primitive, double, object
 * Date: 3/3/2017
 */
import earthGrid.Grid;

public class Calc extends Diffusion_Base{
	
	Grid oldPlate;
	Grid newPlate;
	
	Sun sun;
	
	public void run() {
		//Threaded run
		StartSimulation();
	}
	
	public void initialize(int Width, int Height) throws Exception{
		super.initialize(Width, Height);
		
		//Initialize all global variables
		oldPlate = new Grid(Width + 2, Height + 2);
		newPlate = new Grid(Width + 2, Height + 2);
		
		oldPlate.WrapGrid();
		newPlate.WrapGrid();
	}
	
	public void StartSimulation(){
		_stop = false;
		//Go for the width of the plate which is the full 360 degree of the earth
		for(int i = 0; i < this.oldPlate.width - 2; i++){
			ActivateSun(this.sun);
			StartSimulationSingle(this.sun, i);
			 if(CheckStop()){
				 break;
			 }
		}
		fireSimulationCompleteEvent();
	}
	
	public void StartSimulationSingle(Sun sun, int Iteration){
		fireGridStartedEvent();
		SetupDonePoint(sun, Iteration, this.newPlate.height);
		IncreaseAllTempearatureBySun(sun);
		calcTemps();
	}
		
	public void calcTemps(){
		 // Loop until exit criteria are met, updating each newPlate cell from the
		 // average temperatures of the corresponding neighbors in oldPlate
		 do{
			 for (int x = 1; x <= this.Width; x++) {
				 for (int y = 1; y <= this.Height; y++) {
				 // Compute lattice point temperature as average of neighbors
				 newPlate.SetValue(x, y, (oldPlate.GetValue(x + 1, y) + oldPlate.GetValue(x - 1, y) +
						 		   oldPlate.GetValue(x, y + 1) + oldPlate.GetValue(x, y - 1)) / 4.0);
				 }
			 }
			 // Swap the plates and continue
			 swap(oldPlate, newPlate);
			 CheckPause();
			 if(CheckStop()){
				 break;
			 }
		 }while(! done());
		 fireGridCompleteEvent();
	}
	
	/*
	 * This is the loop variable check to see if the calcTemps is done
	 */
	protected boolean done(){
		maxTries = maxTries - 1;
		if(maxTries == 0){
			maxTries = MAX_TRIES;
			return true;
		}
		else if(this.newPlate.GetCell(this.centerPointX, this.centerPointY).GetTemperature() < this.StoppingTemp){
			return true;
		}
		else{
			return false;
		}
	}
		
	protected void swap(Grid oldPlate, Grid newPlate){
		Grid tempPlate = new Grid(this.Width + 2, this.Height + 2);
		tempPlate.deepCopyFrom(oldPlate);
		
		this.oldPlate = newPlate;
		this.newPlate = tempPlate;
		
		newPlate.WrapGrid();
	}
		
	public double GetTemperatureDouble(int X, int Y){
		return newPlate.GetValue(X, Y);
	}
	
	/*
	 * (non-Javadoc)
	 * @see Diffusion_Base#GetTemperature(int, int)
	 */
	public String GetTemperature(int X, int Y){
		double NearestTwoDec = (double) Math.round(newPlate.GetValue(X + 1, Y + 1) * 100) / 100;
		return Double.toString(NearestTwoDec);
	}
	
	public void ActivateSun(Sun sun){
		this.IncreaseAllTempearatureBySun(sun);
		//Get the old plate the copy of the new plate to kick off the diffusion
		this.oldPlate.deepCopyFrom(newPlate);
	}
	
	public void SetSun(Sun sun){
		//Externally setup sun
		this.sun = sun;
	}
	
	public void IncreaseTempearatureBySun(int X, int Y, Sun sun){
		this.newPlate.GetCell(X, Y).IncreaseTemperatureByAngle(sun.GetRadiationTemperature(), sun.GetAngleX(), sun.GetAngleY());
	}
	
	public void IncreaseAllTempearatureBySun(Sun sun){
		for (int x = 1; x < newPlate.width; x++) {
			 for (int y = 1; y < newPlate.height - 1; y++) {
				 IncreaseTempearatureBySun(x,y,sun);
			 }
		 }
		newPlate.WrapGrid();
	}
	
	
	public Grid GetGrid(){
		return newPlate;
	}
}
