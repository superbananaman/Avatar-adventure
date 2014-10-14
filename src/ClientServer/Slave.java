package ClientServer;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Renderer.Sprite;
import Renderer.Tile;
import tests.Circle;
import gameLogic.Game;
import gameLogic.Item;
import gameLogic.Player;
import gui.*;

/**
 * A slave connection receives information about the current state of the board
 * and relays that into the local copy of the board. The slave connection also
 * notifies the clientThread running in the server that a key has been pressed
 * or a message has been sent.
 *
 * @author Brendan Smith, Matt Catsburg
 */
public class Slave extends Thread {

	private Socket socket;

	private static String uid;
	// the ClientPlayer
	private Player player;
	private static List<Player> players = new ArrayList<Player>();

	private static Game game;

	private static ObjectOutputStream out;
	private ObjectInputStream in;

	private ClientFrame frame;

	public Slave(String address, int port, String charName, String nation) {
		try {
			socket = new Socket(address, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		uid = charName;
		System.out.println("Creating player with: " + charName);
		player = new Player(charName);
	}

	/**
	 * Connects to the server then enters the processing loop.
	 */
	public void run() {
		try {
			// Make connection and initialize streams
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			// send the player made to the server
			System.out.println(player.getUID() + " Has Been Written out");
			out.writeObject(player);
			// receives player from server
			while (true) {
				Object o = in.readObject();
				//System.out.println(o.toString() + " has been read");
				if (o instanceof Player) {
					Player p = (Player)o;
					System.out.println(p.getUID() + " has been added");
					players.add(p);
				}
				// TESTING
				else if (o instanceof Circle) {

				}
				// all players have accepted, can start
				else if (o instanceof String) {
					break;
				}
			}

			// wait for response from server then start frame
			// game = new Game(player, players);
			// frame = new ClientFrame(game);
			System.out.println("FRAME: Player: " + player.getUID() + " Players: " + players.size());
			frame = new ClientFrame(uid, players);
			frame.setVisible(true);

			// while the game is running, recieves info
			// on the current state of the game
			while (true) {
				Object o = in.readObject();
				// received either a movement object or a message
				if (o instanceof UIDObjectPair) {
					UIDObjectPair pair = (UIDObjectPair) o;
					String playerUID = pair.getUID();
					Object ob = pair.getObject();
					// A player had pressed a key
					if (ob instanceof KeyEvent){
						KeyEvent ke = (KeyEvent)ob;
						if (ke.getKeyCode() == KeyEvent.VK_P || ke.getKeyCode() == KeyEvent.VK_O){
							frame.updateInventory();
						}
						for (Player p : players){
							if (p.getUID().equals(playerUID)){
								frame.getRenderWindow().receiveKeyEvent((KeyEvent) ob, p);
							}
						}
					}
					// A player has sent a message
					else if (ob instanceof String) {
						String message = (String) ob;
						frame.toConsole(playerUID, message);
					}
					// A player has picked up or dropped an item
					else if (ob instanceof Item){
						//TODO Send the item to the game so it can be picked up
					}
				}
				// if the item has been picked up or dropped
				else if (o instanceof String){
					String command = (String)o;
					//UIDObjectPair itemPair = (UIDObjectPair)in.readObject();

					if (command.equals("Pickup")){
						// TODO Pickup item
						UIDObjectPair tileLocationPair = (UIDObjectPair)in.readObject();
					}
					else if(command.equals("Drop")){
						// TODO Drop item
						UIDObjectPair ItemNamePair  = (UIDObjectPair)in.readObject();
						UIDObjectPair tileLocationPair = (UIDObjectPair)in.readObject();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the player with the UID given in the parameter
	 * @param uid
	 * @return
	 */
	public Player player(String uid){
		for (Player p : players){
			if (p.getUID().equals(uid)){
				return p;
			}
		}
		return null;
	}



	/**
	 * Sends a message to the server to be sent to all other clients playing the
	 * game, static so any class can send a message to the server
	 *
	 * @param m
	 *            the message to be sent
	 */
	public static void sendMessage(String m) {
		UIDObjectPair message = new UIDObjectPair(uid, m);
		try {
			out.writeObject(message);
		} catch (IOException e) {
			// something went wrong, ignore it for now
			e.printStackTrace();
		}
	}


	/**
	 * Send a keyEvent to the server to send back to all clients
	 * @param e
	 */
	public static void sendKeyEvent(KeyEvent e){
		try {

			//System.out.println("UID : " + uid);
			out.writeObject(new UIDObjectPair(uid, e));
//			int code = e.getKeyCode();
//			if (code == KeyEvent.VK_UP) {
//				out.writeObject(new UIDObjectPair(uid, new Integer(1)));
//			} else if (code == KeyEvent.VK_DOWN) {
//				out.writeObject(new UIDObjectPair(uid, new Integer(2)));
//			} else if (code == KeyEvent.VK_LEFT) {
//				out.writeObject(new UIDObjectPair(uid, new Integer(3)));
//			} else if (code == KeyEvent.VK_RIGHT) {
//				out.writeObject(new UIDObjectPair(uid, new Integer(4)));
//			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	/**
	 * Sends a String to the server which represents that an item has been picked up,
	 * then sends the item being picked up and a location that represents the position
	 * of the tile
	 * @param i
	 * @param location
	 */
	public static void sendPickupItem(Point location){
		try {
			out.writeObject(new String("Pickup"));
		//out.writeObject(new UIDObjectPair(uid, i));
			out.writeObject(new UIDObjectPair(uid, location));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Sends a String to the server which represents that an item has been picked up,
	 * then sends the item being picked up and a location that represents the position
	 * of the tile
	 * @param i
	 * @param location
	 */
	public static void sendDropItem(String name, Point location){
		try {
			out.writeObject(new String("Drop"));
			out.writeObject(new UIDObjectPair(uid, name));
			out.writeObject(new UIDObjectPair(uid, location));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * When a player picks up an item, sends that item to
	 * the server and sends it back to all clients to pick up that item
	 *
	 */
	public static void sendItem(Item i){
		try {
			out.writeObject(new UIDObjectPair(uid, i));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the current player of this client
	 */
//	public static Player getCurrent() {
//		return player;
//	}

	public static List<Player> getPlayers() {
		return players;
	}

	/**
	 * Receive a player and update that player in the list
	 * to the given player
	 * @param player
	 */
	public static void sendPlayer(Player player){
		for (Player p : players){
			if (p.equals(player)){
				p = player;
			}
		}
	}

}