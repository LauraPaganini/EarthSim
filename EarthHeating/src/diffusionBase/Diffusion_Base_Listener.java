/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: Events from the diffusion class
 * Date: 4/2/17
 */

package diffusionBase;

public interface Diffusion_Base_Listener {
	public void GridComplete(Diffusion_Base_Event e);
	public void GridStarted(Diffusion_Base_Event e);
	public void SimulationComplete(Diffusion_Base_Event e);
}
