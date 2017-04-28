
public class Demo {
	private static boolean simThread = false, presThread = false, simInit = false, presInit = false;
	private static int bufferLen = 1;
	private static Earth e;
	private static Buffer b;

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-t":
				simInit = true;
				break;
			case "-r":
				presInit = true;
				break;
			case "-s":
				simThread = true;
				break;
			case "-p":
				presThread = true;
				break;
			case "-b":
				b = new Buffer(Integer.parseInt(args[++i]));
				break;
			}
		}

		e = new Earth(); // how to get size/time from cmd line?
		b = new Buffer();
		runSim();
	}

	private static void runSim() {

		if (simInit) {
			e.simInit(b);
		}else if(presInit){
			//pres signals sim
		}else{
			for(int i = 0; i < b.getLength(); i++)
				b.add(e.updateSim());
			//signal pres
		}
		

	}

}
