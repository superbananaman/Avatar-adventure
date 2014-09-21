import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ClientServer.Slave;
import ClientServer.Master;

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
			runServer(port, nclients);

		}
		// run in client mode
		else if(url != null){
			// TODO run as client
			try {
				runClient(url, port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// run single player
		else{
			// TODO create the board
			// TODO run single player game
		}

	}

	private static void runServer(int port, int nclients) {
		//ClockThread clk = new ClockThread(gameClock,game,null);

		// Listen for connections
		System.out.println("AVATAR SERVER LISTENING ON PORT " + port);
		System.out.println("AVATAR SERVER AWAITING " + nclients + " CLIENTS");
		try {
			Master[] connections = new Master[nclients];
			// Now, we await connections.
			ServerSocket ss = new ServerSocket(port);
			while (1 == 1) {
				// 	Wait for a socket
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());
				//int uid = game.registerPacman();
				connections[--nclients] = new Master(s);
				connections[nclients].start();
				if(nclients == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");

					System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
					return; // done
				}
			}
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		}
	}

	private static void runClient(String addr, int port) throws IOException {
		Socket s = new Socket(addr,port);
		System.out.println("PACMAN CLIENT CONNECTED TO " + addr + ":" + port);
		new Slave(s).run();
	}

}
