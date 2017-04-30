package earthGrid;

import static org.junit.Assert.*;

import org.junit.Test;

public class GridTest {

	@Test
	public void testInitialization() {
		Grid g = new Grid(10, 20);
		assertTrue(g.GetValue(0, 1) == 15);
	}
	
	@Test
	public void testWrap() {
		Grid g = new Grid(20, 10);
		g.SetValue(1, 1, 50);
		g.WrapGrid();
		assertTrue(g.GetValue(19, 1) == 50);
	}
}
