package ClientServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import tests.*;


/**
 *
 * @author Brendan Smith, Matt Catsburg
 *
 */
public class Server {

	private final int port;
	// list of all streams to clients
	public static List<ObjectOutputStream> ooses = new ArrayList<ObjectOutputStream>();

	public Server(int port) {
		this.port = port;
		try {
			runServer();
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
				ClientThread ct =  new ClientThread(listener.accept());
				ct.start();
				System.out.println("Accepted Connection from: " + ct.socket.getLocalAddress());
			}
		} finally {
			// socket must close
			listener.close();
		}
	}

	/**
	 * responsible for dealing with a single client
	 *
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
                //add the OutputStream to the list
        		ooses.add(out);

        		while (true){
        			Object o = null;
        			try {
						o = in.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
        			// the object has not been sent, destroy connection for now
        			if (o == null){
        				return;
        			}
        			//TESTING
        			if (o instanceof Circle){
        				Circle c = (Circle)o;
        				for (ObjectOutputStream oos : ooses) {
                            oos.writeObject(c);
                        }
        			}
        			// if the object received is an int the player has pressed a key
        			if (o instanceof Integer){
        				// the moved player's uid
        				//int uid  = (Integer) o;
        				// read an int from the slave
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
                try {
                    socket.close();
                } catch (IOException e) {
                }
			}
		}

	}
}
