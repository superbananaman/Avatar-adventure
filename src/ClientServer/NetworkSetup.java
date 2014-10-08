package ClientServer;
/**
 * @author Brendan Smith, Matt Catsburg
 */
import gameLogic.Game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ClientServer.Slave;
import ClientServer.Master;

public class NetworkSetup {

	private Game game;

	//Sever constructor
	public NetworkSetup(int nclients, int port) {
		runServer(port, nclients);
		System.exit(0);
	}

	//Client constructor
	public NetworkSetup(String address, int port){
		//run in client mode
		try {
			runClient(address, port);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * Create a server amd allow clients to join
	 * @param port the game the port is running on
	 */
	private static void runServer(int port){
		Server s = new Server(2, port);
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
					System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
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
	private static void runClient(String addr, int port) throws IOException, ClassNotFoundException {
		Socket s = new Socket(addr, port);
		System.out.println("AVATAR CLIENT CONNECTED TO " + addr + ":" + port);
		//new Slave(s).run();
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
}
