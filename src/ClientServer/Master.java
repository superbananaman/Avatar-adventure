package ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * A master connection receives events from a slave connection via a socket.
 * These events are registered with the world. The master connection is also
 * responsible for transmitting information to the slave about the current world
 * state.
 */
public class Master extends Thread {
	// private final Board board;
	private final int broadcastClock;
	private final int uid;
	private final Socket socket;

	public Master(Socket socket, int uid, int broadcastClock) { // pass in world
		// this.board = board; world
		this.broadcastClock = broadcastClock;
		this.socket = socket;
		this.uid = uid;
	}

	public void run() {
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(
					socket.getOutputStream());
			// First, write the period to the stream
			output.writeInt(uid);
			// output.writeInt(board.width()); world stuff???
			// output.writeInt(board.height());
			// output.write(board.wallsToByteArray());
			boolean exit = false;
			while (!exit) {
				try {

					if (input.available() != 0) {

						// read direction event from client.
						int dir = input.readInt();
						// switch(dir) {
						// case 1:
						// board.player(uid).moveUp();
						// break;
						// case 2:
						// board.player(uid).moveDown();
						// break;
						// case 3:
						// board.player(uid).moveRight();
						// break;
						// case 4:
						// board.player(uid).moveLeft();
						// break;
						// }
					}

					// Now, broadcast the state of the board to client
					// byte[] state = board.toByteArray(); world.toByteArray???
					// output.writeInt(state.length);
					// output.write(state);
					output.flush();
					Thread.sleep(broadcastClock);
				} catch (InterruptedException e) {
				}
			}
			socket.close(); // release socket ... v.important!
		} catch (IOException e) {
			System.err.println("PLAYER " + uid + " DISCONNECTED");
			// board.disconnectPlayer(uid);
		}
	}
}
