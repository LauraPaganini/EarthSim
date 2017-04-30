package diffusion;
import static org.junit.Assert.*;

import org.junit.Test;

import sun.Sun;
import earthGrid.Grid;


public class CalcTest {

	@Test //Test initial plate temps. This tests the Grid class as well
	public void testInitialPlateTemps() {
		Calc c = new Calc();
		try {
			c.initialize(10, 20);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//note row/column 0/4 is the edges - we are checking the edges
		//top
		assertTrue( c.GetTemperatureDouble(0, 1) == 15);
		//bottom
		assertTrue( c.GetTemperatureDouble(10, 1) == 15);
		//left
		assertTrue( c.GetTemperatureDouble(1, 0) == 15);
		//right
		assertTrue( c.GetTemperatureDouble(1, 10) == 15);
	}
	
	@Test //Test swapping
	public void testSwapping() {
		Calc c = new Calc();
		try {
			c.initialize(20, 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//note row/column 0/4 is the edges - we are checking the edges
		//Make sure the new plate (it's returned on get temp) is correct
		//top
		c.ActivateSun(new Sun(0, 0, 5));	
		
		c.OutputToConsole();
		
		assertFalse( c.GetTemperatureDouble(1, 1) == 15);
		//bottom
		assertFalse( c.GetTemperatureDouble(20, 1) == 15);
		//right
		assertFalse( c.GetTemperatureDouble(1, 10) == 15);
		
		c.ActivateSun(new Sun(0, 0, 5));
		
		c.OutputToConsole();
		
		//blow away one of the plates so swapping can be done properly
		c.oldPlate = new Grid(c.Width + 2, c.Height + 2);
		
		c.swap(c.oldPlate, c.newPlate);
		
		//note row/column 0/4 is the edges - we are checking the edges
		//Make sure the new plate (it's returned on get temp) is not correct
		//top
		assertTrue( c.GetTemperatureDouble(0, 1) == 15);
		//bottom (it's still 0)
		assertTrue( c.GetTemperatureDouble(4, 1) == 15);
		//left
		assertTrue( c.GetTemperatureDouble(1, 0) == 15);
		//right
		assertTrue( c.GetTemperatureDouble(1, 4) == 15);
	}
}
