package ClientServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import gameLogic.Game;
import tests.Player;
import gui.ServerConsoleWindow;
import tests.*;

/**
 *
 * @author Brendan Smith, Matt Catsburg
 *
 */
public class Server {

	private final int port;
	private int nclients;
	private static List<ClientThread> threads = new ArrayList<ClientThread>();

	// list of all streams to clients
	private static List<ObjectOutputStream> ooses = new ArrayList<ObjectOutputStream>();

	//private ServerFrame frame;
	private ServerConsoleWindow frame;

	private static List<Player> players = new ArrayList<Player>();

	//private static List<Circle> players = new ArrayList<Circle>();
	// indicates whether all players have joined
	private static boolean allPlayers = false;
	//Instance of the game for the saving and loading
	private static Game game;

//	public Server(int port) {
//		this.port = port;
//		try {
//			runServer();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public Server(int nclients, int port) {
		frame = new ServerConsoleWindow();
		this.nclients = nclients;
		this.port = port;
		frame.toConsole("The server is running");
		frame.toConsole("Awaiting " + nclients + " connections");
		try {
			ServerSocket listener = new ServerSocket(port);
			try {
				while (true){
					ClientThread ct = new ClientThread(listener.accept());
					threads.add(ct);
					ct.start();
					frame.toConsole("Accepted Connection from: "
							+ ct.socket.getLocalAddress());
					nclients--;
					if(nclients == 0){
						//no more accepting run the game
						allPlayers = true;
						runGame();
						return;
					}
					frame.toConsole("Waiting for " + nclients + " connection(s)");
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
	 * Runs the Server
	 *
	 * @throws IOException
	 */
	public void runServer() throws IOException {
		System.out.println("The Server is Running");
		ServerSocket listener = new ServerSocket(port);
		try {
			while (true) {
				ClientThread ct = new ClientThread(listener.accept());
				ct.start();
				System.out.println("Accepted Connection from: "
						+ ct.socket.getLocalAddress());
			}
		} finally {
			// socket must close
			listener.close();
		}
	}

	public void runGame(){

		frame.toConsole("All clients have joined, the game is now running");
		// while there is at least one connection
		//game = new Game(players);
		while(!threads.isEmpty()){
			//keep running
		}
		frame.toConsole("All clients have disconnected, the game is over");
	}

	/**
	 * Receives input from a single client and sends that input to
	 * each client that is connected to the server
	 */
	private static class ClientThread extends Thread {

		public Socket socket;
		private ObjectInputStream in;
		private ObjectOutputStream out;

		public ClientThread(Socket s) {
			this.socket = s;
		}

		@Override
		public void run() {
			try {
				in = new ObjectInputStream(socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());
				// add the OutputStream to the list
				ooses.add(out);
				// while we are still waiting for clients to join
				do{
					Object o = null;
					try{
						o = in.readObject();
					} catch (ClassNotFoundException e){
						// object has been lost, ignore for now
						e.printStackTrace();
					}
					if (o == null){
						return;
					}
					if (o instanceof Player){
						players.add((Player)o);
					}
					//TESTING
//					if (o instanceof Circle){
//						players.add((Circle)o);
//					}
				}while(!allPlayers);

				// all clients have accepted send players to slaves
				for (ObjectOutputStream oos : ooses){
					for (Player p : players){
						oos.writeObject(p);
					}
					//TESTING
//					for (Circle c : players){
//						oos.writeObject(c);
//					}
				}
				// send a string which indicated the game can begin
				for (ObjectOutputStream oos : ooses){
					oos.writeObject(new String("GO!"));
				}
				// so far all this loop does is read an object and
				// send it back out to all clients
				while (true) {
					Object o = null;
					try {
						o = in.readObject();
					} catch (ClassNotFoundException e) {
						System.out.println("An object was not sent through");
						e.printStackTrace();
					}
					// the object has not been sent, destroy connection for now
					if (o == null) {
						return;
					}
					// This fixes the StreamCorruptedException through synchronization
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