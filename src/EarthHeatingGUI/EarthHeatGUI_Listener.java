/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: Events from the GUI
 * Date: 4/2/17
 */

package EarthHeatingGUI;

public interface EarthHeatGUI_Listener {
	public void Run(EarthHeatGUI_Event e);
	public void Stop(EarthHeatGUI_Event e);
	public void Pause(EarthHeatGUI_Event e);
	public void Resume(EarthHeatGUI_Event e);
	public void UpdateGridSize(EarthHeatGUI_Event e);
	public void UpdateSunTemperature(EarthHeatGUI_Event e);
	public void PresentationNeedsAGrid(EarthHeatGUI_Event e);
}
