/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: The GUIs presentation layer
 * Date: 4/2/17
 */

package EarthHeatingGUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import earthGrid.Grid;

public class EarthHeatGUIPresentation implements Runnable {
	private Set<EarthHeatGUI_Listener>Listeners = new Set<EarthHeatGUI_Listener>() {
		
		ArrayList<EarthHeatGUI_Listener> ListenerList = new ArrayList<EarthHeatGUI_Listener>();
		
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
		public Iterator<EarthHeatGUI_Listener> iterator() {
			Iterator<EarthHeatGUI_Listener> iteratorList = new Iterator<EarthHeatGUI_Listener>() {
				int Counter = 0;
				@Override
				public boolean hasNext() {
					// TODO Auto-generated method stub
					return Counter < ListenerList.size();
				}

				@Override
				public EarthHeatGUI_Listener next() {
					// TODO Auto-generated method stub
					Counter  = Counter + 1;
					return ListenerList.get(Counter - 1);
				}
			};
			// TODO Auto-generated method stub
			return (Iterator<EarthHeatGUI_Listener>) iteratorList;
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
		public boolean addAll(Collection<? extends EarthHeatGUI_Listener> c) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean add(EarthHeatGUI_Listener e) {
			ListenerList.add(e);
			return true;
		}
	};
	
	protected IEarthHeatGUIModel frmEarthHeatGUI = null;
	protected boolean PresentationTimerActive = false;
	protected boolean _EnableDisableRate = true;
	
	@Override
	public void run() {
		//Really just sits and runs if we want it threaded
		StartGUI();
	}
	
	public void StartGUI(){
		//Open GUI, create the listener instance
		frmEarthHeatGUI = new EarthHeatGUI().CreateAndShowForm();
		frmEarthHeatGUI.SetupPresentation(this);
		SendEnableDisableRate(_EnableDisableRate);
	}
	
	double SunTemperature = 5;
	int Width = 0;
	int Height = 0;
	
	public void SetSunTemperature(double SunTemperature){
		this.SunTemperature = SunTemperature;
		RaiseUpdateSunTemperatureEvent();
	}
	
	public double GetSunTemperature(){
		return this.SunTemperature;
	}
	
	public void SetGridSize(int Width, int Height){
		this.Width = Width;
		this.Height = Height;		
		RaiseUpdateGridSizeEvent();
	}
	public int GetGridSizeWidth(){
		return this.Width;	
	}
	public int GetGridSizeHeight(){
		return this.Height;	
	}
	
	public synchronized void RaiseRunEvent(){
		//Block the run if the presentation is still working
		if(!this.PresentationTimerActive){
			frmEarthHeatGUI.BlankTotalTime();
			EarthHeatGUI_Event e = new EarthHeatGUI_Event(this);
			for(EarthHeatGUI_Listener Listener : Listeners){
				Listener.Run(e);	
			}
		}
	}
	
	public synchronized void RaiseStopEvent(){
		EarthHeatGUI_Event e = new EarthHeatGUI_Event(this);
		for(EarthHeatGUI_Listener Listener : Listeners){
			Listener.Stop(e);	
		}
	}
	
	public synchronized void RaisePauseEvent(){
		EarthHeatGUI_Event e = new EarthHeatGUI_Event(this);
		for(EarthHeatGUI_Listener Listener : Listeners){
			Listener.Pause(e);	
		}
	}
	
	public synchronized void RaiseResumeEvent(){
		EarthHeatGUI_Event e = new EarthHeatGUI_Event(this);
		for(EarthHeatGUI_Listener Listener : Listeners){
			Listener.Resume(e);	
		}
	}

	public synchronized void RaiseUpdateSunTemperatureEvent(){
		EarthHeatGUI_Event e = new EarthHeatGUI_Event(this);
		for(EarthHeatGUI_Listener Listener : Listeners){
			Listener.UpdateSunTemperature(e);	
		}
	}
	
	public synchronized void RaiseUpdateGridSizeEvent(){
		EarthHeatGUI_Event e = new EarthHeatGUI_Event(this);
		for(EarthHeatGUI_Listener Listener : Listeners){
			Listener.UpdateGridSize(e);	
		}
	}
	
	public synchronized void RaisePresentationNeedsAGridEvent(){
		EarthHeatGUI_Event e = new EarthHeatGUI_Event(this);
		for(EarthHeatGUI_Listener Listener : Listeners){
			Listener.PresentationNeedsAGrid(e);	
		}
	}
	
	public synchronized void addListener(EarthHeatGUI_Listener listener){
		Listeners.add(listener);
	}
	
	public synchronized void removeListener(EarthHeatGUI_Listener listener){
		Listeners.remove(listener);
	}

	public void UpdateTotalTime(double TotalTime){
		frmEarthHeatGUI.UpdateTotalTime(TotalTime);
	}
	
	public void UpdateGrid(Grid grid){
		frmEarthHeatGUI.UpdateGrid(grid);
	}
	
	public void UpdateAvarageTime(double AverageTime){
		this.frmEarthHeatGUI.UpdateAvarageTime(AverageTime);
	}
	
	public void SetSimIterationCountLabel(int Iteration, int TotalIteration, boolean UpdatePresentation){
		this.frmEarthHeatGUI.SetSimulationIterationCount(Iteration, TotalIteration);
		if(UpdatePresentation){
			SetPresentationCountLabel(Iteration, TotalIteration);
		}
	}
	
	public void SetPresentationCountLabel(int Iteration, int TotalIteration){
		this.frmEarthHeatGUI.SetPresentationIterationCount(Iteration, TotalIteration);
	}
	
	public void StartPresentationDrivenSimulation(int Iterations){
		EarthHeatGUITimer Timer = new EarthHeatGUITimer(this);
		Timer.SetIterations(Iterations);
		Timer.SetTimeMilliSeconds((long)(this.frmEarthHeatGUI.GetIterationTime() * 1000));
		this.removeListener(Timer);
		this.addListener(Timer);
		Thread TimerThread = new Thread(Timer);
		TimerThread.start();
	}
	
	public void SetPresentationRunningFlag(boolean PresentationTimerActive){
		this.PresentationTimerActive = PresentationTimerActive;
	}
	
	public void SetAllValuesForTemperature(double SunTemp, double DefaultTemp, double MultValueForSun){
		this.frmEarthHeatGUI.SetAllValuesForTemperature(SunTemp, DefaultTemp, MultValueForSun);
	}
	
	public double GetSunAngleY(){
		return this.frmEarthHeatGUI.GetSunAngleY();
	}
	
	public void EnableDisableRate(boolean Enable){
		_EnableDisableRate  = Enable;
	}
	
	protected void SendEnableDisableRate(boolean Enable){
		this.frmEarthHeatGUI.EnableDisableRate(Enable);
	}
}
