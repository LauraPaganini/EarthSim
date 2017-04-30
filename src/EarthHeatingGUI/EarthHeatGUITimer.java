/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: The timer for the presentation layer when it is presentation driven
 * Date: 4/2/17
 */

package EarthHeatingGUI;

public class EarthHeatGUITimer implements Runnable, EarthHeatGUI_Listener{
	protected EarthHeatGUIPresentation Presentation;
	protected int Iterations;
	long TimeDuration;
	boolean _pause = false;
	boolean _stop = false;
	
	@Override
	public void run(){
		_stop = false;
		Presentation.SetPresentationRunningFlag(true);
		for(int i = 0; i < this.Iterations; i++){
			long tEnd = System.currentTimeMillis() + this.TimeDuration;
			//Wait till the time has elapsed
			while(tEnd - System.currentTimeMillis() > 0){}
			Presentation.RaisePresentationNeedsAGridEvent();
			Presentation.SetPresentationCountLabel(i + 1, this.Iterations);
			this.CheckPause();
			if(this.CheckStop()){
				break;
			}
		}
		Presentation.SetPresentationRunningFlag(false);
	}

	public EarthHeatGUITimer(EarthHeatGUIPresentation Presentation){
		this.Presentation = Presentation;
	}
	
	public void SetIterations(int Iterations){
		this.Iterations = Iterations;
	}
	
	public void SetTimeMilliSeconds(long TimeDuration){
		this.TimeDuration = TimeDuration;
	}
	

	public void Pause(){
		_pause = true;
	}
	
	public void Resume(){
		_pause = false;
	}
	
	public void Stop(){
		_stop = true;
	}
	
	protected void CheckPause(){
		while(_pause){
			try {
				Thread.sleep(0);
				if(_stop){
					return;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected boolean CheckStop(){
		if(_stop){
			return true;
		}
		return _stop;
	}

	@Override
	public void Run(EarthHeatGUI_Event e) {
		//Don't care
	}

	@Override
	public void Stop(EarthHeatGUI_Event e) {
		_stop = true;
		
	}

	@Override
	public void Pause(EarthHeatGUI_Event e) {
		_pause = true;
		
	}

	@Override
	public void Resume(EarthHeatGUI_Event e) {
		_pause = false;
		
	}

	@Override
	public void UpdateGridSize(EarthHeatGUI_Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateSunTemperature(EarthHeatGUI_Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PresentationNeedsAGrid(EarthHeatGUI_Event e) {
		// TODO Auto-generated method stub
		
	}
}
