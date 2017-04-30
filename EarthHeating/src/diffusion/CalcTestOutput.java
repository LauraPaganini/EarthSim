package diffusion;
import static org.junit.Assert.*;

import org.junit.Test;

import sun.Sun;

public class CalcTestOutput {
	
	@Test
	public void test() {
		Calc c = new Calc();
		int Iterations = 50;
		try {
			//c.initialize(1440, 180);
			c.initialize(Iterations, 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i = 0; i < 180; i++){
			c.ActivateSun(new Sun(i, 0, 10));
			c.calcTemps();
		}
		System.out.println(c.StepsToComplete());
		c.OutputToConsole();
	}

	@Test
	public void testOuput() {
		Calc c = new Calc();
		int Iterations = 10;
		
		try {
			//c.initialize(1440, 180);
			c.initialize(Iterations, 180);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.sun = new Sun(0, 0, 20);
		c.StartSimulation();
		//c.ActivateSun(new Sun(0, 0, 20));
		c.OutputToConsole();
		//c.calcTemps();
		assertTrue(c.sun.GetAngleX() == 0);
		assertTrue(c.sun.GetAngleY() == 0);
		assertTrue(c.sun.GetRadiationTemperature() == 20);
	}
}
	
