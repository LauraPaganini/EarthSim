/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: This is the central controller to the earth simulation.
 * Date: 4/2/17
 */

package controller;

import java.util.ArrayList;

import EarthHeatingGUI.EarthHeatGUIPresentation;
import EarthHeatingGUI.EarthHeatGUI_Event;
import EarthHeatingGUI.EarthHeatGUI_Listener;
import sun.Sun;
import diffusion.Calc;
import diffusionBase.Diffusion_Base_Event;
import diffusionBase.Diffusion_Base_Listener;
import earthGrid.Grid;

public class EarthSim implements Diffusion_Base_Listener, EarthHeatGUI_Listener{
	final String ThreadSimulation = "-s";
	final String ThreadPresenation = "-p";
	final String SimulationDriven = "-t";
	final String PresentationDriven = "-r";
	final String Buffer = "-b";
	
	protected boolean isThreadSimulation = false;
	protected boolean isThreadPresenation = false;
	protected boolean isSimulationDriven = true;
	protected boolean isPresentationDriven = false;
	protected int BufferSize = 1;
	protected int SimulationsLeft = 0;
	protected int SimulationsTotal = 0;
	protected boolean isSimulationRunning = false;
	
	protected Thread simulationThread = null;
	protected Thread presentationThread = null;
	
	protected Sun sun;
	protected double sunTemp;
	
	protected long startTime = 0;
	protected long endTime = 0;
	protected long IterationStartTime = 0;
	protected long IterationFinishTime = 0;
	protected double IterationAverageTime = 0;
	protected int IterationTimeCount = 0;
	
	protected Calc objCalc = null;
	EarthHeatGUIPresentation Presentation = null;
	protected ArrayList<Grid> CompletedList = new ArrayList<Grid>();
	
	public void Demo(String[] args) throws Exception{
		parseInput(args);
		Presentation = new EarthHeatGUIPresentation();
		Presentation.EnableDisableRate(isPresentationDriven);
		Presentation.addListener(this);
		if(isThreadPresenation){
			presentationThread = new Thread(Presentation);
			presentationThread.start();
		}
		else{
			Presentation.StartGUI();
		}
	}
	
	/*
	 * This will parse the input and call the initialization of the simulation
	 */
	public void parseInput(String args[]) throws Exception{
		String strReturn = GetStringFromArgs(args, Buffer);
		if(strReturn != ""){
			BufferSize = Integer.parseInt(strReturn);
		}

		isThreadSimulation = FindStringFromArgs(args, ThreadSimulation);
		isThreadPresenation = FindStringFromArgs(args, ThreadPresenation);
		//Turn off presentation driven if simulation driven is on
		if(FindStringFromArgs(args, SimulationDriven)){
			isPresentationDriven = false;
			isSimulationDriven = true;
		}
		//Turn off simulation driven if presentation driven is on
		if(FindStringFromArgs(args, PresentationDriven)){
			isPresentationDriven = true;
			isSimulationDriven = false;
		}
	}
	
	/*
	 * This parses the args from the input. It will return the value which is located
	 * next to the arg that was send in
	 */
	protected String GetStringFromArgs(String args[], String strInput){
		String strReturn = "";
		for(int i = 0; i < args.length; i++){
			if(args[i].contains(strInput)){
				strReturn = args[i + 1];
				break;
			}
		}
		return strReturn;	
	}
	
	
	protected boolean FindStringFromArgs(String args[], String strInput){
		for(int i = 0; i < args.length; i++){
			if(args[i].contains(strInput)){
				return true;
			}
		}
		return false;	
	}
	
	public boolean IsThreadedSimulation(){
		return this.isThreadSimulation;
	}
	
	public boolean IsThreadedPresination(){
		return this.isThreadPresenation;
	}
	
	public boolean IsSimulationDriven(){
		return this.isSimulationDriven;
	}
	
	public boolean IsPresentationDriven(){
		return this.isPresentationDriven;
	}
	
	public int BufferSize(){
		return this.BufferSize;
	}
	
	protected int GetSimulationsLeft(){
		return SimulationsLeft;
	}
	
	public void RunSimulation(){
		this.IterationTimeCount = 0;
		isSimulationRunning = true;
		this.startTime = System.nanoTime();
		objCalc.removeListener(this);
		objCalc.addListener(this);
		objCalc.SetSun(this.sun);
		Presentation.SetAllValuesForTemperature(this.sun.GetRadiationTemperature(), objCalc.DEFAULT_TEMP, objCalc.DONE_POINT);
		//This activates the timer on the presentation layer
		if(isPresentationDriven){
			Presentation.StartPresentationDrivenSimulation(this.SimulationsTotal);
		}
		//Fire up the threads if applicable	
		if(IsThreadedSimulation()){
			try {
				simulationThread = new Thread(objCalc);
				simulationThread.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			objCalc.StartSimulation();
		}
	}
	
	public void SetupSimulation(int GridX, int GridY, double sunTemp){
		//https://www.youtube.com/watch?v=qw0zZAte66A
		CompletedList.clear();
		objCalc = new Calc();
		try {
			objCalc.initialize(GridX, GridY);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.SimulationsLeft = GridX;
		this.SimulationsTotal = GridX;
		
		this.sun = new Sun(0, Presentation.GetSunAngleY(), sunTemp);
		this.sunTemp = sunTemp;
	}

	@Override
	public void GridComplete(Diffusion_Base_Event e) {
		//Add the grid to the buffer, then spin again if the buffer is less than
		//it was set out to be
		this.IterationFinishTime = System.nanoTime();
		
		//Decrement the simulations left
		this.SimulationsLeft = this.SimulationsLeft - 1;
		this.sun = new Sun(((double)this.SimulationsTotal - (double)this.SimulationsLeft) / (double)this.SimulationsTotal * 360, this.sun.GetAngleY(), this.sunTemp);
		objCalc.SetSun(this.sun);
		Grid GridCopier = objCalc.GetGrid().deepCopy();
		
		if(IsSimulationDriven()){
			//Send it to the presentation layer
			Presentation.UpdateGrid(GridCopier);
		}
		else{
			while(CompletedListSize() >= BufferSize){
				//Wait until the buffer is available to add to
				try {
					Thread.sleep(0);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			CompletedList.add(GridCopier);
		}
			

		this.IterationTimeCount = this.IterationTimeCount + 1;
		if(this.IterationTimeCount == 1){
			this.IterationAverageTime = this.IterationFinishTime - this.IterationStartTime;
		}
		else{
			this.IterationAverageTime = ((this.IterationAverageTime * (IterationTimeCount - 1)) + (this.IterationFinishTime - this.IterationStartTime)) / IterationTimeCount;	
		}
		this.Presentation.UpdateAvarageTime(this.IterationAverageTime / 1000000000);
		this.Presentation.SetSimIterationCountLabel(this.IterationTimeCount, this.SimulationsTotal, this.isSimulationDriven);
	}

	@Override
	public void GridStarted(Diffusion_Base_Event e) {
		this.IterationStartTime = System.nanoTime();
	}
	
	public double GetTotalTime(){
		return	this.endTime - this.startTime;
	}
	
	public double GetAveragePerSimulation(){
		return this.IterationAverageTime;
	}

	@Override
	public void Run(EarthHeatGUI_Event e) {
		if(!isSimulationRunning){
			RunSimulation();
		}
	}

	@Override
	public void Stop(EarthHeatGUI_Event e) {
		objCalc.Stop();
	}

	@Override
	public void Pause(EarthHeatGUI_Event e) {
		objCalc.Pause();
	}

	@Override
	public void Resume(EarthHeatGUI_Event e) {
		objCalc.Resume();
	}

	@Override
	public void UpdateGridSize(EarthHeatGUI_Event e) {
		if(!isSimulationRunning){
			SetupSimulation(e.getSourse().GetGridSizeWidth(), e.getSourse().GetGridSizeHeight(), e.getSourse().GetSunTemperature());			
		}
	}

	@Override
	public void UpdateSunTemperature(EarthHeatGUI_Event e) {
		if(!isSimulationRunning){
			this.sunTemp = e.getSourse().GetSunTemperature();
			SetupSimulation(e.getSourse().GetGridSizeWidth(), e.getSourse().GetGridSizeHeight(), e.getSourse().GetSunTemperature());	
		}
	}

	@Override
	public void SimulationComplete(Diffusion_Base_Event e) {
		this.endTime = System.nanoTime();
		Presentation.UpdateTotalTime(GetTotalTime() / 1000000000);
		isSimulationRunning = false;
	}
	
	protected int CompletedListSize(){
		synchronized (CompletedList) {
			return CompletedList.size();
		}
	}

	@Override
	public void PresentationNeedsAGrid(EarthHeatGUI_Event e) {
		while(CompletedListSize() <= 0){
			try {
				Thread.sleep(0);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//Send it to the presentation layer	
		Presentation.UpdateGrid(CompletedList.remove(0));
		CompletedList.trimToSize();
	}
	
}
