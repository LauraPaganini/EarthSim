/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: This class the entry point to the EarthSim demo
 * Date: 4/2/17
 */
package controller;

public class Demo {
	public static void main(String[] args) {
		try {
			EarthSim e = new EarthSim();
			e.Demo(args);
			while(e != null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
