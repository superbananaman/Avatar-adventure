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
		// Arguements to create a server
		boolean server = serv; // playing server mode or not
		int nclients = 0;
		int port = askforPort(server);// get port
		String url = askforIP(server); // ip address
		if (serv) {
			server = serv;
			nclients = askforPlayers();
		}
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

	/**
	 * Creates the server and allows clients to join starts the
	 * game when the correct number of clients have joined.
	 * @param port to run the server on
	 * @param nclients that will be playing
	 */
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

	/**
	 * Connects the client to the server
	 * @param addr IP address to connect to
	 * @param port to connect to
	 * @throws IOException
	 */
	private static void runClient(String addr, int port) throws IOException {
		Socket s = new Socket(addr, port);
		System.out.println("AVATAR CLIENT CONNECTED TO " + addr + ":" + port);
		new Slave(s).run();
	}

	/**
	 * Checks whether or not there is at least one connection alive.
	 * @param connections
	 * @return whether there are still connections
	 */
	private static boolean atleastOneConnection(Master... connections) {
		for (Master m : connections) {
			if (m.isAlive()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Asks the host for the amount of players
	 * @return the amount of players to play
	 */
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

	/**
	 * Asks the user for a IP address to connect to
	 * @param isServer if the host is running a server
	 * @return the desried IP address
	 */
	public String askforIP(boolean isServer) {
		if(isServer){
		String address = JOptionPane
				.showInputDialog("Open console and type in 'ifconfig'. "
						+ "Enter the address next to where it says \"inet\"");
		while (address == null || address.length() < 7) {
			address = JOptionPane
					.showInputDialog("Invalid IP Address. Please enter again.");
		}
		return address;
		}
		else{
			String address = JOptionPane
					.showInputDialog("Enter the Host's IP Address");
			while (address == null || address.length() < 7) {
				address = JOptionPane
						.showInputDialog("Invalid IP Address. Please enter again.");
			}
			return address;
		}
	}

	/**
	 * Asks the user for a Port Number to connect to
	 * @param isServer if the host is running a server
	 * @return the desired port
	 */
	public int askforPort(boolean isServer) {
		if (isServer) {
			JTextField tf = new JTextField();
			String port = JOptionPane.showInputDialog(tf,
					"Enter a Port Number (e.g: 40700)");
			while (port == null || port.length() < 5 || port.length() > 5) {
				if (port == null) {
					System.exit(0);
				}
				port = JOptionPane
						.showInputDialog("Invalid Port number. Please enter again.");
			}
			return Integer.parseInt(port);
		}
		else{
			JTextField tf = new JTextField();
			String port = JOptionPane.showInputDialog(tf,
					"Enter the Host's Port Number");
			while (port == null || port.length() < 5 || port.length() > 5) {
				if (port == null) {
					System.exit(0);
				}
				port = JOptionPane
						.showInputDialog("Invalid Port number. Please enter again.");
			}
			return Integer.parseInt(port);
		}
	}
}
