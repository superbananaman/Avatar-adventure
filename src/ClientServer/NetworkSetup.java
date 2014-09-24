package ClientServer;

import gameLogic.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ClientServer.Slave;
import ClientServer.Master;

public class NetworkSetup {

	private Game game;

	public NetworkSetup(boolean serv) {
		// Commmand line arguments
		boolean server = false; // playing server mode or not
		int nclients = 0;
		int port = askforPort();; // default
		String url = askforIP(); // ip address
		if(serv){
			server = serv;
			nclients = askforPlayers();
		}

//		for (int i = 0; i != args.length; ++i) {
//			if (args[i].startsWith("-")) {
//				String arg = args[i];
//				if (arg.equals("-help")) {
//					// TODO print out options
//					System.exit(0);
//				} else if (arg.equals("-server")) {
//					server = true;
//					nclients = Integer.parseInt(args[++i]);
//				} else if (arg.equals("-connect")) {
//					url = args[++i];
//				} else if (arg.equals("-port")) {
//					port = Integer.parseInt(args[++i]);
//				}
//			}
//		}

		// TODO add checks to test validity
		// run in server mode
		if (server) {
			// TODO create the board
			// TODO run the server
			runServer(port, nclients);

		}
		// run in client mode
		else if (url != null) {
			// TODO run as client
			try {
				runClient(url, port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// run single player
		else {
			// TODO create the board
			// TODO run single player game
		}
		System.exit(0);
	}

	private static void runServer(int port, int nclients) {
		// ClockThread clk = new ClockThread(gameClock,game,null);

		// Listen for connections
		System.out.println("AVATAR SERVER LISTENING ON PORT " + port);
		System.out.println("AVATAR SERVER AWAITING " + nclients + " CLIENTS");
		try {
			Master[] connections = new Master[nclients];
			// Now, we await connections.
			ServerSocket ss = new ServerSocket(port);
			while (1 == 1) {
				// Wait for a socket
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: "
						+ s.getInetAddress());
				// int uid = game.registerPacman();
				connections[--nclients] = new Master(s);
				connections[nclients].start();
				if (nclients == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					while (atleastOneConnection(connections)) {
						// Stay open
					}
					System.out
							.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
					return; // done
				}
			}
		} catch (IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		}
	}

	private static void runClient(String addr, int port) throws IOException {
		Socket s = new Socket(addr, port);
		System.out.println("AVATAR CLIENT CONNECTED TO " + addr + ":" + port);
		new Slave(s).run();
	}

	/**
	 * Check whether or not there is at least one connection alive.
	 *
	 * @param connections
	 * @return
	 */
	private static boolean atleastOneConnection(Master... connections) {
		for (Master m : connections) {
			if (m.isAlive()) {
				return true;
			}
		}
		return false;
	}

	public int askforPlayers() {
		String players = JOptionPane.showInputDialog("How Many Players (1-4)?");
		if (players == null) {
			return (-1);
		}
		int number = 5;
		if (players.length() == 1) {

			try {
				number = Integer.parseInt(players);
			} catch (NumberFormatException e1) {
				number = 5;
			}
		}
		while (number > 4 || number < 1) {
			players = JOptionPane
					.showInputDialog("Not A Valid Number. How Many Players (1-4)?");
			if (players == null) {
				return (-1);
			}
			try {
				number = Integer.parseInt(players);
			} catch (NumberFormatException e1) {
				number = 5;
			}
		}
		System.out.println(number);
		return number;
	}
	public String askforIP(){
		String address = JOptionPane
				.showInputDialog
				("Open console and type in 'ifconfig'. "
						+ "On the second line of random stuff, next to the word inet, there is a 4 digit address. "
						+ "Enter the address.");
		while(address == null || address.length() < 7) {
			address = JOptionPane
					.showInputDialog
					("Invalid IP Address. Please enter again.");
		}
		return address;
	}
	public int askforPort(){
		JTextField tf = new JTextField("40700");
		String port = JOptionPane.showInputDialog(tf,"Enter a Port Number (default: 40700)");
		while(port == null || port.length() < 5 || port.length() > 5) {
			port = JOptionPane
					.showInputDialog
					("Invalid Port number. Please enter again.");
		}
		return Integer.parseInt(port);
	}
}
