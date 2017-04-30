package controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class EarthSimTest {

	@Test
	public void CheckBufferSize() {
		String[] args = new String[5];
		args[0] = "-s"; //simulation threaded
		args[1] = "-p"; //presentation threaded
		args[2] = "-r"; //Presentation Driven	
		args[3] = "-b"; //buffer
		args[4] = "10"; //buffer size
		
		EarthSim db = new EarthSim();
		boolean thrown = false;
		try {
			db.Demo(args);
			db.SetupSimulation(10, 10, 10);
		} catch (Exception e) {
			thrown = true;
		}
		assertTrue(db.BufferSize() == 10);
		assertTrue(thrown == false);
	}
	
	@Test
	 public void ParseNormalNoBuffer() {
		String[] args = new String[3];
		args[0] = "-s";
		args[1] = "-p";
		args[2] = "-t";
		
		EarthSim db = new EarthSim();
		boolean thrown = false;
		try {
			db.parseInput(args);
		} catch (Exception e) {
			thrown = true;
		}
		assertTrue(db.BufferSize() == 1);
		assertTrue(thrown == false);
	}
	
	@Test
	public void test() {
		//This is really not a JUnitTest, more of a demo
		String[] args = new String[5];
		args[0] = "-s"; //simulation threaded
		args[1] = "-p"; //presentation threaded
		args[2] = "-t"; //Simulation Driven
		//args[2] = "-r"; //Presentation Driven	
		args[3] = "-b"; //buffer
		args[4] = "10"; //buffer size
		
		EarthSim db = new EarthSim();
		try {
			db.Demo(args);
			Thread.sleep(10000000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Time per sim seconds: " + db.GetAveragePerSimulation() / 1000000000);
		System.out.println("Time total seconds: " + db.GetTotalTime() / 1000000000);
		assertTrue(db.isPresentationDriven == false);
		assertTrue(db.isSimulationDriven == true);
		assertTrue(db.isThreadPresenation == true);
		assertTrue(db.isThreadSimulation == true);
		assertTrue(db.BufferSize() == 50);
	}
}
