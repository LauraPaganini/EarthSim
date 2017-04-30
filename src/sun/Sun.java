/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: Sun position angles and temperature
 * Date: 4/2/17
 */

package sun;

public class Sun {
	double AngleX;
	double AngleY;
	double RadiationTemperature;
	
	public Sun(double AngleX, double AngleY, double RadiationTemperature){
		this.AngleX = AngleX;
		this.AngleY = AngleY;
		this.RadiationTemperature = RadiationTemperature;
	}

	public double GetAngleX(){
		return this.AngleX;
	}
	
	public double GetAngleY(){
		return this.AngleY;
	}
	
	public double GetRadiationTemperature(){
		return this.RadiationTemperature;
	}
	
	public void SetRadiationTemperature(double RadiationTemperature){
		this.RadiationTemperature = RadiationTemperature;
	}
}
