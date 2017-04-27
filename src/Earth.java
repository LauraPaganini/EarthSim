
public class Earth {

	private int d, sunLon = 0, timeInterval; // KILOMETERS
	private static int DEFAULT_SPACING = 15, DEFAULT_TIME_INTERVAL = 1, EARTH_CIRCUMFERENCE = 40075;
	private static double INITIAL_TEMP = 15, SUN_DISTANCE = (4.49 * Math.pow(10, 8)), spacing;
	private Cell[][] oldGlobe, newGlobe;

	public Earth(int spacing, int timeInterval) {
		this.timeInterval = timeInterval;
		this.d = 180 / spacing;
		initializeGlobe();
	}

	public Earth() {
		new Earth(DEFAULT_SPACING, DEFAULT_TIME_INTERVAL);
	}

	private void initializeGlobe() {
		int tLat = 90;
		newGlobe = new Cell[d][d];
		for (int i = 0; i < d; i++) {
			for (int j = 0; j < d; j++) {
				newGlobe[i][j] = new Cell(INITIAL_TEMP, tLat, tLat - 180 / d, j * (180 / d),
						(j * (180 / d) + (180 / d)), (double) (EARTH_CIRCUMFERENCE) / (2 * d));
			}
			tLat -= 180 / d;
		}

		for (int i = 0; i < d; i++) {
			for (int j = 0; j < d; j++) {

				newGlobe[i][j].setNeighbors(newGlobe[((i - 1) % d > 0) ? (i - 1) % d : i + (d / 2) % d][j],
						newGlobe[(i + 1) % d][j], newGlobe[i][((j - 1) % d > 0) ? (j - 1) % d : d - 1],
						newGlobe[i][(j + 1) % d]);// check north and west of
													// globe[0][0]
				newGlobe[i][j].calculateArea();
			}
		}
	}

	public Cell[][] updateSim() {
		double cellAverages, sunTemp, cellDist, sunOffset;
		Cell thisCell;
		oldGlobe = newGlobe;

		for (int i = 0; i < d; i++) {
			for (int j = 0; j < d; j++) {
				thisCell = oldGlobe[i][j];
				cellAverages = (thisCell.getTemp() + thisCell.north.getTemp() + thisCell.south.getTemp()
						+ thisCell.west.getTemp() + thisCell.east.getTemp()) / 5;
				// sunTemp = sunHeat * cellAreaProportion * sunAngleDifference
				// total cooling BALANCES solar heating

				if (Math.cos(Math.toRadians(sunLon - thisCell.rLon)) > .5) {
					// sunny side
					// RIGHT AND TOP
					sunOffset = (Math.abs(sunLon - thisCell.rLon) + (Math.abs(thisCell.tLat)))
							* (EARTH_CIRCUMFERENCE / d);
					cellDist = Math.sqrt(Math.pow(SUN_DISTANCE, 2) + Math.pow(sunOffset, 2));

					sunTemp = 25 * cellDist * (1 / SUN_DISTANCE);

					newGlobe[i][j].setTemp(sunTemp * .75 + cellAverages * .25);
				} else {
					// cooling, .4 is our cooling constant
					newGlobe[i][j].setTemp(.4 * thisCell.getTemp() * .75 + cellAverages * .25);
				}
			}

		}
		// move sun
		sunLon += 360 * (timeInterval / 1440.0);
		return newGlobe;
	}
	
	public void simInit(Buffer b){
		for(int i = 0; i < b.buffer.length; i++)
			b.buffer[i] = this.updateSim();
		//signal pres
		
	}
}
