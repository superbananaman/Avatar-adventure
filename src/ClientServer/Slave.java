package ClientServer;

import java.awt.Point;
import java.awt.event.KeyEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import ClientServer.UIDObjectPair.Operation;

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

	private Player player; // the ClientPlayer
	private static List<Player> players = new ArrayList<Player>();

	private static Game game; // current state of the game

	private static ObjectOutputStream out;
	private ObjectInputStream in;


	public Slave(String address, int port, String charName, String nation) {
		try {
			socket = new Socket(address, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		uid = charName;
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

			WaitFrame waitframe = new WaitFrame();
			waitframe.setVisible(true);

			// send the player made to the server
			waitframe.toConsole(player.getUID() + " has joined the server");
			out.writeObject(player);
			// receives player from server
			while (true) {
				Object o = in.readObject();
				if (o instanceof Player) {
					Player p = (Player)o;
					players.add(p);
				}
				// TESTING
				else if (o instanceof Circle) {

				}
				else if (o instanceof String){
					waitframe.toConsole("A client has Connected!");
				}
				// all players have accepted, can start
				else if (o instanceof UIDObjectPair) {
					UIDObjectPair message = (UIDObjectPair)o;
					waitframe.toConsole(message.getUID(), (String)message.getObject());
				}
				// represents that all players have accepted
				else if (o instanceof Integer){
					break;
				}
			}

			// wait for response from server then start frame

			sleepThread(waitframe);
			waitframe.stopMusic();
			waitframe.dispose();

			game = new Game(uid, players);

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
					if (pair.getOp().equals(Operation.KeyEvent)){
						for (Player p : players){
							if (p.getUID().equals(playerUID)){
								game.getClientFrame().getRenderWindow().receiveKeyEvent((KeyEvent) ob, p);
							}
						}
					}
					// A player has sent a message
					else if (pair.getOp().equals(Operation.Message)) {
						String message = (String) ob;
						game.getClientFrame().toConsole(playerUID, message);
					}
					// received the selected inventory space of a player
					else if (pair.getOp().equals(Operation.SpaceSelected)){
						Integer i = (Integer) ob;
						for (Player p : players){
							if (p.getUID().equals(playerUID)){
								p.getInventory().selectedSpace = i;
							}
						}
					}
					// received a dead monster message
					else if (pair.getOp().equals(Operation.Monster)){
						game.updateDeadMonsters((Point) ob);
					}
					// received a dead player message
					else if (pair.getOp().equals(Operation.DeadPlayer)){
						//game.
					}
					// received a damage message
					else if (pair.getOp().equals(Operation.Damage)){
						game.updateDamageMonsters((Point)pair.getObject2(), (Integer)pair.getObject());
					}

				}
				// if the item has been picked up or dropped
				else if (o instanceof String){
					String message = (String)o;
					//game.getClientFrame().toConsole(message);

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sleeps the thread for 5 seconds and then begins the game,
	 * the count down is recorded on the waitframe
	 * @param waitframe
	 */
	private void sleepThread(WaitFrame waitframe) {
		int count = 5;
		while (count > 0){
			waitframe.toConsole("The Game is Starting in " + count);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count--;
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
		UIDObjectPair message = new UIDObjectPair(Operation.Message, uid, m);
		try {
			out.writeObject(message);
		} catch (IOException e) {
			// something went wrong, ignore it for now
			e.printStackTrace();
		}
	}

	/**
	 * Sends a message to the console withough a given uid
	 * @param m Represents an event occurring in the game
	 */
	public static void sendToConsole(String m){
		try {
			out.writeObject(m);
		} catch (IOException e) {
			// something went wrong, ignore it for now
			e.printStackTrace();
		}
	}

	/**
	 * sends the monster which indicates that it is dead
	 * @param location the point the monster is on
	 */
	public static void sendMonster(Point location){
		try {
			out.writeObject(new UIDObjectPair(Operation.Monster, uid, location));
		} catch (IOException e) {
			// something went wrong, ignore it for now
			e.printStackTrace();
		}
	}

	/**
	 * sends that a monster has attacked a player to the server
	 * @param health the amount of health lost
	 * @param loc the location of the monster
	 */
	public static void sendMonsterAttack(int health, Point loc){
		try {
			out.writeObject(new UIDObjectPair(Operation.Damage, uid, new Integer(health), loc));
		} catch (IOException e) {
			// something went wrong, ignore it for now
			e.printStackTrace();
		}
	}

	/**
	 * Sent when a player in the game has died
	 * @param deadPlayer
	 */
	public static void sendDeadPlayer(String deadPlayer){
		try {
			out.writeObject(new UIDObjectPair(Operation.DeadPlayer, uid, deadPlayer));
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
			out.writeObject(new UIDObjectPair(Operation.KeyEvent, uid, e));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Sends the item the client player has selected to all other clients
	 * @param selected
	 */
	public static void sendSelectedSpace(int selected){
		try{
			out.writeObject(new UIDObjectPair(Operation.SpaceSelected, uid, (Integer) selected));
		}catch (IOException e) {

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
	public static void sendPickupItem(Point location){
		try {
			out.writeObject(new String("Pickup"));
		//out.writeObject(new UIDObjectPair(uid, i));
			out.writeObject(new UIDObjectPair(Operation.Pickup, uid, location));
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
			out.writeObject(new UIDObjectPair(Operation.Drop, uid, name));
			out.writeObject(new UIDObjectPair(Operation.Drop,uid, location));
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
			out.writeObject( new UIDObjectPair(Operation.Pickup,uid, i));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * @return returns all players in the game
	 */
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