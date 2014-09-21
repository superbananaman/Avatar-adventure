package ClientServer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

/**
 * A master connection receives events from a slave connection via a socket.
 * These events are registered with the world. The master connection is also
 * responsible for transmitting information to the slave about the current world
 * state.
 */
public class Master extends Thread {
	// private final Board board;
	//private final int broadcastClock;
	//private final int uid;

    /**
     * The set of all names of clients in the chat room.  Maintained
     * so that we can check that new clients are not registering name
     * already in use.
     */
    private static HashSet<String> names = new HashSet<String>();

    /**
     * The set of all the print writers for all the clients.  This
     * set is kept so we can easily broadcast messages.
     */
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

	private final Socket socket;
    private String name;
    //private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

	public Master(Socket socket) { // pass in world
		// this.board = board; world
		//this.broadcastClock = broadcastClock; taken out not sure what its for
		this.socket = socket;
		//this.uid = uid; Maybe later when board is created
	}

//	public void run() {
//		try {
//			DataInputStream input = new DataInputStream(socket.getInputStream());
//			DataOutputStream output = new DataOutputStream(
//					socket.getOutputStream());
//			// First, write the period to the stream
//			//output.writeInt(uid);
//			// output.writeInt(board.width()); world stuff???
//			// output.writeInt(board.height());
//			// output.write(board.wallsToByteArray());
//			boolean exit = false;
//			while (!exit) {
//				try {
//
//					if (input.available() != 0) {
//
//						// read direction event from client.
//						int dir = input.readInt();
//						// switch(dir) {
//						// case 1:
//						// board.player(uid).moveUp();
//						// break;
//						// case 2:
//						// board.player(uid).moveDown();
//						// break;
//						// case 3:
//						// board.player(uid).moveRight();
//						// break;
//						// case 4:
//						// board.player(uid).moveLeft();
//						// break;
//						// }
//					}
//
//					// Now, broadcast the state of the board to client
//					// byte[] state = board.toByteArray(); world.toByteArray???
//					// output.writeInt(state.length);
//					// output.write(state);
//					output.flush();
//					Thread.sleep(5);
//				} catch (InterruptedException e) {
//				}
//			}
//			socket.close(); // release socket ... v.important!
//		} catch (IOException e) {
//			//System.err.println("PLAYER " + uid + " DISCONNECTED");
//			System.err.println("PLAYER DISCONNECTED");
//			// board.disconnectPlayer(uid);
//		}
//	}
	 public void run() {
         try {

             // Create character streams for the socket.
             in = new BufferedReader(new InputStreamReader(
                 socket.getInputStream()));
             out = new PrintWriter(socket.getOutputStream(), true);

             // Request a name from this client.  Keep requesting until
             // a name is submitted that is not already used.  Note that
             // checking for the existence of a name and adding the name
             // must be done while locking the set of names.
             while (true) {
                 out.println("SUBMITNAME");
                 name = in.readLine();
                 if (name == null) {
                     return;
                 }
                 synchronized (names) {
                     if (!names.contains(name)) {
                         names.add(name);
                         break;
                     }
                 }
             }

             // Now that a successful name has been chosen, add the
             // socket's print writer to the set of all writers so
             // this client can receive broadcast messages.
             out.println("NAMEACCEPTED");
             writers.add(out);

             // Accept messages from this client and broadcast them.
             // Ignore other clients that cannot be broadcasted to.
             while (true) {
                 String input = in.readLine();
                 if (input == null) {
                     return;
                 }
                 for (PrintWriter writer : writers) {
                     writer.println("MESSAGE " + name + ": " + input);
                 }
             }
         } catch (IOException e) {
             System.out.println(e);
         } finally {
             // This client is going down!  Remove its name and its print
             // writer from the sets, and close its socket.
             if (name != null) {
                 names.remove(name);
             }
             if (out != null) {
                 writers.remove(out);
             }
             try {
                 socket.close();
             } catch (IOException e) {
             }
         }
     }
 }
