/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: This class is the base class to calculate diffusion
 * Date: 4/2/17
 */
package diffusionBase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import sun.Sun;

public class Diffusion_Base implements Runnable {
	private Set<Diffusion_Base_Listener>Listeners = new Set<Diffusion_Base_Listener>() {
		
		ArrayList<Diffusion_Base_Listener> ListenerList = new ArrayList<Diffusion_Base_Listener>();
		
		@Override
		public <T> T[] toArray(T[] a) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object[] toArray() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public boolean retainAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean remove(Object o) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public Iterator<Diffusion_Base_Listener> iterator() {
			Iterator<Diffusion_Base_Listener> iteratorList = new Iterator<Diffusion_Base_Listener>() {
				int Counter = 0;
				@Override
				public boolean hasNext() {
					// TODO Auto-generated method stub
					return Counter < ListenerList.size();
				}

				@Override
				public Diffusion_Base_Listener next() {
					// TODO Auto-generated method stub
					Counter  = Counter + 1;
					return ListenerList.get(Counter - 1);
				}
			};
			// TODO Auto-generated method stub
			return (Iterator<Diffusion_Base_Listener>) iteratorList;
		}
		
		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean containsAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean contains(Object o) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void clear() {
			// TODO Auto-generated method stub
			ListenerList.clear();
		}
		
		@Override
		public boolean addAll(Collection<? extends Diffusion_Base_Listener> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean add(Diffusion_Base_Listener e) {
			ListenerList.add(e);
			return true;
		}
	};
	
	protected final int MAX_TRIES = Integer.MAX_VALUE;
	final int MIN_TEMP = 0;
	final int MAX_TEMP = 100;
	final int MIN_WIDTH = 5;
	final int MIN_HEIGHT = 5;
	final double DBLE_HALF_HEIGHT = 90;
	public final int DEFAULT_TEMP = 15;
	public final double DONE_POINT = .5; //When the point that is hit by the sun is this % of the sun's temp, stop
	
	public int Width; 
	public int Height;
	protected int maxTries;
	protected int centerPointX = 0;
	protected int centerPointY = 0;
	protected double StoppingTemp = 0;
	protected boolean _pause  = false;
	protected boolean _stop  = false;

	@Override
	public void run() {
        throw new UnsupportedOperationException("Must implement");
	}
	
	/*
	 * This initializes all of the local global variables to the input from the user
	 */
	public void initialize(int width, int height) throws Exception{
		_stop = false;
		CheckConstraints(width, height);
		//X will move around depending on the sun
		this.Width = width;
		this.Height = height;
		
		//Setup max tries
		maxTries = MAX_TRIES;
	}
	
	/*
	 * This makes sure the constraints are followed in the input
	 */
	protected void CheckConstraints(int Width, int Height) throws Exception{
		if(Width < MIN_WIDTH){
			throw new Exception("Width must be greater than " + MIN_WIDTH);
		}
		if(Height < MIN_HEIGHT){
			throw new Exception("Height must be greater than " + MIN_HEIGHT);
		}

	}
	
	/*
	 * This will output the results to the console
	 */
	public void OutputToConsole(){
		for(int y = 0; y < this.Height; y++){
			System.out.println(GetStringByLine(y));
		}
		System.out.println("");
	}
	
	public String GetStringByLine(int Y){
		StringBuilder stringBuilder = new StringBuilder();
		for(int x = 0; x < this.Width; x++){
			stringBuilder.append(GetTemperature(x, Y) + " ");
		}
		return stringBuilder.toString();
	}
	
	/*
	 * This is the loop variable check to see if the calcTemps is done
	 */
	protected boolean done(){
        throw new UnsupportedOperationException("Must implement");
	}
	
	public int StepsToComplete(){
		return MAX_TRIES - maxTries;
	}
	
	/*
	 * You must override this in the inherited class
	 * 
	 * This should initialize the plates with all of the appropriate edge temperatures
	 */
	protected void SetupInitialPlateTemps(){
        throw new UnsupportedOperationException("Must implement");
	}
	
	/*
	 * You must override this in the inherited class
	 * 
	 * This will do the diffusion math on the plates
	 */
	public void calcTemps(){
        throw new UnsupportedOperationException("Must implement");
	}
	
	/*
	 * You must override this in the inherited class
	 * 
	 * This will get the temperature as a string
	 */
	public String GetTemperature(int Row, int Column){
        throw new UnsupportedOperationException("Must implement");
	}
	
	/*
	 * You must override this in the inherited class
	 * 
	 * This will get the temperature as a double
	 */
	public double GetTemperatureDouble(int Row, int Column){
        throw new UnsupportedOperationException("Must implement");
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
	
	public synchronized void addListener(Diffusion_Base_Listener listener){
		Listeners.add(listener);
	}
	
	public synchronized void removeListener(Diffusion_Base_Listener listener){
		Listeners.remove(listener);
	}
	
	protected synchronized void fireGridCompleteEvent(){
		Diffusion_Base_Event e = new Diffusion_Base_Event(this);
		for(Diffusion_Base_Listener Listener : Listeners){
			Listener.GridComplete(e);
		}
	}
	
	protected synchronized void fireSimulationCompleteEvent(){
		Diffusion_Base_Event e = new Diffusion_Base_Event(this);
		for(Diffusion_Base_Listener Listener : Listeners){
			Listener.SimulationComplete(e);
		}
	}
	
	protected synchronized void fireGridStartedEvent(){
		Diffusion_Base_Event e = new Diffusion_Base_Event(this);
		for(Diffusion_Base_Listener Listener : Listeners){
			Listener.GridStarted(e);
		}
	}

	protected void SetupDonePoint(Sun sun, int IterationNumber, int height){
		//Y is already defined
		centerPointX = IterationNumber;
		int RealHeight = height - 2;
		centerPointY = RealHeight - (int) (((double)(sun.GetAngleY() / DBLE_HALF_HEIGHT) * RealHeight / 2) + (RealHeight / 2)) ;
		StoppingTemp = DEFAULT_TEMP + (sun.GetRadiationTemperature() * DONE_POINT);
	}
}
;