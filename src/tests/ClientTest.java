package tests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ClientServer.*;

public class ClientTest implements KeyListener {

	ObjectInputStream in;
	static ObjectOutputStream out;
	JFrame frame = new JFrame("Circles");
	JPanel canvas;
	List<Circle> circles = new ArrayList<Circle>();
	Circle current;

	public ClientTest(Circle c) {
		current = c;
		//startClient();
	}

	public void startClient(){
		// Layout GUI

				canvas = new JPanel() {
					@Override
					public void paintComponent(Graphics g) {
						super.paintComponent(g);
						// Must add or else it will lag
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						drawCircle(g);
						// System.out.println("hey");
					}
				};
				canvas.setSize(500, 500);
				frame.add(canvas);
				frame.setSize(500, 500);
				frame.setResizable(false);
				frame.addKeyListener(this);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);


	}
	/**
	 * Prompt for and return the address of the server.
	 */
	private String getServerAddress() {
		return JOptionPane.showInputDialog(frame,
				"Enter IP Address of the Server:", "Welcome to the Chatter",
				JOptionPane.QUESTION_MESSAGE);
	}

	private void drawCircle(Graphics g) {
		synchronized (circles) {
			// g.setColor(Color.RED);
			for (Circle circ : circles) {
				circ.draw(g);
			}
			// System.out.println(circles.size());
			// if (circle != null){
			// circle.draw(g);
			canvas.repaint();
			// }
		}
	}

	public Circle circle(String uid) {
		for (Circle c : circles) {
			if (c.uid.equals(uid)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Connects to the server then enters the processing loop.
	 *
	 * @throws ClassNotFoundException
	 */
	private void run() throws IOException, ClassNotFoundException {

		// Make connection and initialize streams
		String serverAddress = getServerAddress();
		Socket socket = new Socket(serverAddress, 9003);

		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());

		// send the Clients uid
		System.out.println("(Client)about to write uid");
		out.writeObject((Circle)current);
		System.out.println("(Client)Written uid");
		// Process all messages from server, according to the protocol.

		boolean canstart = false;
		//wait for response for server to
		while (!canstart){
			System.out.println("in canstart");
			Object o = in.readObject();
			System.out.println(o.toString());
			if (o instanceof Circle){
				System.out.println("CIRCLE ADDED");
				Circle c = (Circle) o; 
				System.out.println(c.x + " " + c.y);
				circles.add(c);
			}
			if (o instanceof String){
				System.out.println("breaking loop");
				break;
			}
		}
		startClient();
		String uid = "";
		while (true) {
			Object o = in.readObject();
//			if (o instanceof Circle){
//				System.out.println("Circle has been added");
//				Circle c = (Circle) o;
//				System.out.println(c.x + " " + c.y);
//				circles.add(c);
//			}
//			if (o instanceof Integer){
//				Integer i = (Integer)o;
//				if (i.equals(1)){
//					circle(uid).moveVertical(-10);
//				}
//				else if (i.equals(2)){
//					circle(uid).moveVertical(10);
//				}
//				else if (i.equals(3)){
//					circle(uid).moveHorizontal(-10);
//				}
//				else if (i.equals(4)){
//					circle(uid).moveHorizontal(10);
//				}
//				// the uid
//				else{
//					System.out.println("Setting uid");
//					uid = i;
//				}
			if (o instanceof UIDObjectPair){
				UIDObjectPair i = (UIDObjectPair)o;
				uid = i.getUID();
				int move = (Integer)i.getObject();
				if (move == 1){
					circle(uid).moveVertical(-10);
				}
				else if (move == 2){
					circle(uid).moveVertical(10);
				}
				else if (move == 3){
					circle(uid).moveHorizontal(-10);
				}
				else if (move == 4){
					circle(uid).moveHorizontal(10);
				}
//				// the uid
//				else{
//					System.out.println("Setting uid");
//					uid = i;
//				}
			}


			// circle = c;

		}
	}



	/**
	 * Runs the client as an application with a closeable frame.
	 */
	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Object[] options = { "Server", "Client" };
		int n = JOptionPane.showOptionDialog(f,
				"Would you like green eggs and ham?", "A Silly Question",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, // the titles of buttons
				options[0]); // default button title
		if (n == 0){
			Server s = new Server(2, 9003);
		}
		else if(n == 1){
			//ClientTest client = new ClientTest(new Circle(200, 200, "a", Color.RED));
			//ClientTest client = new ClientTest(new Circle(10, 10, "b", Color.BLUE));
			//ClientTest client = new ClientTest(new Circle(300, 200, "c", Color.CYAN));
//			client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			client.frame.setVisible(true);
			//client.run();
			Slave s = new Slave(InetAddress.getLocalHost().getHostAddress(), 9003, "matt",null);
		}
		
		
		
//		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		client.frame.setVisible(true);
//		client.run();
	}

	// @Override
	// public void mousePressed(java.awt.event.MouseEvent e) {
	// // TODO Auto-generated method stub
	// // circles.add(new Circle(e.getX(), e.getY()));
	// try {
	// System.out.println("writing object");
	// out.writeObject(new Circle(e.getX(), e.getY(), Color.RED));
	// } catch (IOException e1) {
	//
	// e1.printStackTrace();
	// }
	// }

	
	public void keyTyped(KeyEvent e) {
	}

	
	public void keyPressed(KeyEvent e) {
		try {
			//System.out.println(circles.size());
			int code = e.getKeyCode();
			//System.out.println("sending int");
			if (code == KeyEvent.VK_UP) {
				out.writeObject(new UIDObjectPair(current.uid, new Integer(1)));				
			}
			else if(code == KeyEvent.VK_DOWN){
				out.writeObject(new UIDObjectPair(current.uid, new Integer(2)));
			}
			else if(code == KeyEvent.VK_LEFT){
				out.writeObject(new UIDObjectPair(current.uid, new Integer(3)));
			}
			else if(code == KeyEvent.VK_RIGHT){
				out.writeObject(new UIDObjectPair(current.uid, new Integer(4)));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	
	public void keyReleased(KeyEvent e) {
	}
}