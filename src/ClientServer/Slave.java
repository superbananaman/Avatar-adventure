package ClientServer;

/**

 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import tests.Circle;
import gameLogic.Player;
import gui.*;

/**
 * A slave connection receives information about the current state of the board
 * and relays that into the local copy of the board. The slave connection also
 * notifies the clientThread running in the server that a key has been pressed or
 * a message has been sent.
 * @author Brendan Smith, Matt Catsburg
 */
public class Slave implements KeyListener {

	private Socket socket;

	private static String uid;
	// the player that this person chose
	private Player player;
	private List<Player> players = new ArrayList<Player>();
	
	
	
	private static ObjectOutputStream out;
	private ObjectInputStream in;

	private ClientFrame frame;
	
	public Slave(String address, int port, String charName, String nation){
		try{
			socket = new Socket(address, port);
		}catch (IOException e){
			e.printStackTrace();
		}
		uid = charName;
		
		// TODO Create Player
		try {
			run();
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

	/**
	 * Connects to the server then enters the processing loop.
	 */
	public void run() throws IOException, ClassNotFoundException {

		// Make connection and initialize streams
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());

		// send the player made to the server
		out.writeObject(player);
		// receives player from server
		while (true){
			Object o = in.readObject();
			if (o instanceof Player){
				players.add((Player) o);
			}
			//TESTING
			else if (o instanceof Circle){
				
			}
			// all players have accepted, can start
			else if (o instanceof String){
				break;
			}
		}
		
		// wait for response from server then start frame
		frame = new ClientFrame();
		frame.setVisible(true);

		// while the game is running, recieves info
		// on the current state of the game
		while (true) {
			Object o = in.readObject();			
			// received either a movement object or a message
			if (o instanceof UIDObjectPair){
				UIDObjectPair pair = (UIDObjectPair)o;
				String playerUID = pair.getUID();
				Object ob = pair.getObject();
				// if integer is sent through, means the player has moved
				if (ob instanceof Integer){
					Integer i = (Integer)ob;
					if (i.equals(1)){
						//TODO Move player up
					}
					else if (i.equals(2)){
						//TODO Move player down
					}
					else if (i.equals(3)){
						//TODO Move player left
					}
					else if (i.equals(4)){
						//TODO Move player right
					}
				}
				else if (ob instanceof String){
					String message = (String)ob;
					//TODO Give message to Textfield in clientframe to show
				}
			}
		}
	}

	/**
	 * Sends a message to the server to be sent to all other clients
	 * playing the game, static so any class can send a message to the
	 * server
	 * @param m the message to be sent
	 */
	public static void sendMessage(String m){
		UIDObjectPair message = new UIDObjectPair(uid, m);
		try {		
			out.writeObject(message);
		} catch (IOException e) {
			//something went wrong, ignore it for now
			//TODO
			e.printStackTrace();
		}
	}

	public void keyPressed(KeyEvent e) {
		// send an event to the server to write to all clients
		try {			
			int code = e.getKeyCode();
			if (code == KeyEvent.VK_UP) {
				out.writeObject(new UIDObjectPair(uid, new Integer(1)));
			} else if (code == KeyEvent.VK_DOWN) {
				out.writeObject(new UIDObjectPair(uid, new Integer(2)));
			} else if (code == KeyEvent.VK_LEFT) {
				out.writeObject(new UIDObjectPair(uid, new Integer(3)));
			} else if (code == KeyEvent.VK_RIGHT) {
				out.writeObject(new UIDObjectPair(uid, new Integer(4)));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
