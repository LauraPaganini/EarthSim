/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: Event builder from the GUI
 * Date: 4/2/17
 */

package EarthHeatingGUI;

public class EarthHeatGUI_Event {
	private EarthHeatGUIPresentation sourse;
	
	public EarthHeatGUI_Event(EarthHeatGUIPresentation sourse){
		this.sourse = sourse;
	}
	
	public EarthHeatGUIPresentation getSourse(){
		return sourse;
	}
}
