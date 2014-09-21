public class Main {

	public static void main(String[] args) {
		// Commmand line arguments
		boolean server = false; // playing server mode or not
		int nclients = 0;
		int port = 32768; // default
		String url = null; // ip address

		for (int i = 0; i != args.length; ++i) {
			if (args[i].startsWith("-")) {
				String arg = args[i];
				if (arg.equals("-help")) {
					// TODO print out options
					System.exit(0);
				} else if (arg.equals("-server")) {
					server = true;
					nclients = Integer.parseInt(args[++i]);
				} else if (arg.equals("-connect")) {
					url = args[++i];
				} else if (arg.equals("-port")) {
					port = Integer.parseInt(args[++i]);
				}
			}
		}

		// TODO add checks to test validity

		// run in server mode
		if (server){
			// TODO create the board
			// TODO run the server

		}
		// run in client mode
		else if(url != null){
			// TODO run as client
		}
		// run single player
		else{
			// TODO create the board
			// TODO run single player game
		}

	}

}
