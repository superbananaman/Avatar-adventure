package ClientServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import gameLogic.Game;
import gameLogic.Player;
import gui.ServerConsoleWindow;

//import tests.*;

/**
 * Represents the Server of the game, contains a list of all the threads the
 * server has connected, but does not start the thread until all players are
 * connected.
 *
 * @author Brendan Smith, Matt Catsburg
 *
 */
public class Server extends Thread {

	private final int port;
	private int nclients;
	private static List<ClientThread> threads = new ArrayList<ClientThread>();

	// list of all streams to clients
	private static List<ObjectOutputStream> ooses = new ArrayList<ObjectOutputStream>();

	// private ServerFrame frame;
	private static ServerConsoleWindow frame;

	private static List<Player> players = new ArrayList<Player>();

	// private static List<Circle> players = new ArrayList<Circle>();
	// indicates whether all players have joined
	private static boolean allPlayers = false;
	// Instance of the game for the saving and loading
	private static Game game;

	public Server(int nclients, int port) {
		this.nclients = nclients;
		this.port = port;
	}

	/**
	 * Waits for all clients to accept then starts all threads simultaneously
	 *
	 */
	@Override
	public void run() {
		frame = new ServerConsoleWindow();
		try {
			frame.toConsole("The server is running on port " + port
					+ " on host address "
					+ InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.toConsole("Awaiting " + nclients + " connections");
		try {
			ServerSocket listener = new ServerSocket(port);
			try {
				while (true) {
					ClientThread ct = new ClientThread(listener.accept());
					threads.add(ct);
					ct.start();
					frame.toConsole("Accepted Connection from: "
							+ ct.socket.getLocalAddress());
					nclients--;
					if (nclients == 0) {
						// no more accepting run the game
						allPlayers = true;
						runGame();
						return;
					}
					frame.toConsole("Waiting for " + nclients
							+ " connection(s)");
				}
			} finally {
				// close the socket and exit program
				listener.close();
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called when all players have accepted, runs the game until all clients
	 * have disconnected and then closes the server.
	 */
	public void runGame() {
		frame.toConsole("All clients have joined, the game is now running");
		// while there is at least one connection
		int i = 0;
		// keep running
		while (!threads.isEmpty()) {
			Thread.yield();
		}
		frame.toConsole("All clients have disconnected, the game is over");
	}

	/**
	 * Receives input from a single client and sends that input to each client
	 * that is connected to the server
	 */
	private static class ClientThread extends Thread {

		public Socket socket;
		private ObjectInputStream in;
		private ObjectOutputStream out;

		public ClientThread(Socket s) {
			this.socket = s;
		}

		/**
		 * Waits for all the players to be sent from all other connected clients.
		 * After that is done, it reads any objects sent and sends it straight back to
		 * all clients.
		 */
		@Override
		public void run() {
			try {
				in = new ObjectInputStream(socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());
				// add the OutputStream to the list
				ooses.add(out);
				// while we are still waiting for clients to join
				// add the first player
				Object o = null;
				do {
					try {
						o = in.readObject();
					} catch (ClassNotFoundException e) {
						// object has been lost, ignore for now
						e.printStackTrace();
					}
					if (o == null) {
						return;
					}
					if (o instanceof Player) {
						players.add((Player) o);
						continue;
					}

					// frame.toConsole("PLAYERSSIZE:" + players.size());
					// TESTING
					// if (o instanceof Circle){
					// players.add((Circle)o);
					// }
				} while (!allPlayers);

				// all clients have accepted send players to slaves
				for (ObjectOutputStream oos : ooses) {
					for (Player p : players) {
						oos.writeObject(p);
					}
					// TESTING
					// for (Circle c : players){
					// oos.writeObject(c);
					// }
				}
				// send a string which indicated the game can begin
				for (ObjectOutputStream oos : ooses) {
					oos.writeObject(new String("GO!"));
				}
				// Added so the last object that has been read isn't lost
				synchronized (ooses) {
					for (ObjectOutputStream oos : ooses) {
						oos.writeObject(o);
					}
				}
				// so far all this loop does is read an object and
				// send it back out to all clients
				while (true) {
					// Object o = null;

					try {
						o = in.readObject();
						frame.toConsole(o.toString() + " has been sent through");
					} catch (ClassNotFoundException e) {
						System.out.println("An object was not sent through");
						e.printStackTrace();
					}
					// the object has not been sent, destroy connection for now
					if (o == null) {
						return;
					}
					// This fixes the StreamCorruptedException through
					// synchronization
					synchronized (ooses) {
						for (ObjectOutputStream oos : ooses) {
							oos.writeObject(o);
						}
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			} finally {
				// The client is going down! remove the ObjectoutputStream
				// from the list and close the socket
				if (out != null) {
					ooses.remove(out);
				}
				threads.remove(this);
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}