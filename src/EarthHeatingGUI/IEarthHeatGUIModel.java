/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: Model for the GUI
 * Date: 4/2/17
 */
package EarthHeatingGUI;
import earthGrid.Grid;

public interface IEarthHeatGUIModel {
	EarthHeatGUI CreateAndShowForm();
	void SetupPresentation(EarthHeatGUIPresentation Presentation);
	void UpdateGrid(Grid grid);
	void UpdateAvarageTime(double Time);
	void UpdateTotalTime(double Time);
	void BlankTotalTime();
	void SetSimulationIterationCount(int Iteration, int TotalIteration);
	double GetIterationTime();
	double GetSunAngleY();
	void SetPresentationIterationCount(int Iteration, int TotalIteration);
	void EnableDisableRate(boolean Enable);
	void SetAllValuesForTemperature(double SunTemp, double DefaultTemp, double MultValueForSun);
}
