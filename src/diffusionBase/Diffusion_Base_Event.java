/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: Event builder from the diffusion class
 * Date: 4/2/17
 */

package diffusionBase;

public class Diffusion_Base_Event {
	private Diffusion_Base sourse;
	
	public Diffusion_Base_Event(Diffusion_Base sourse){
		this.sourse = sourse;
	}
	
	public Diffusion_Base getSourse(){
		return sourse;
	}
}
