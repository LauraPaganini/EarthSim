/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: This is the data in the grid. It holds the position (angles) on the earth
 * Date: 4/7/2017
 */

package earthGrid;

public class GridCell {
	double AngleX;
	double AngleY;
	double Temperature;
	
	public GridCell(double AngleX, double AngleY, double IntialTemperature){
		this.AngleX = AngleX;
		this.AngleY = AngleY;
		this.Temperature = IntialTemperature;
	}
	
	public void SetTemperature(double Temperature){
		this.Temperature = Temperature;
	}

	public void IncreaseTemperatureByAngle(double Temperature, double AngleX, double AngleY){
		//Get the cosines of the angle from the sun
		double AngleXToCalulate =  Math.cos(Math.toRadians(AngleX - this.AngleX));
		double calcAngleY = this.AngleY;
		//Invert the Y when X is negative
		if(AngleXToCalulate < 0){
			calcAngleY = -calcAngleY;
		}
		double AngleYToCalulate = Math.cos(Math.toRadians(AngleY - calcAngleY));
		double IncreaseAmount = 0;
		
		//Make sure that both are positive before increasing the temperature
		IncreaseAmount = (AngleXToCalulate * AngleYToCalulate);

		//Apply the temperature
		this.Temperature = this.Temperature + (Temperature * IncreaseAmount);
	}
	
	public double GetTemperature(){
		return this.Temperature;
	}
	
	public String GetRounded2DecimalsTemperature(){
		//Add one due to the edges being added on the rows and columns.
		double NearestTwoDec = (double) Math.round(GetTemperature() * 100) / 100;
		return Double.toString(NearestTwoDec);
	}
}
