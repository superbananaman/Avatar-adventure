package tests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ClientServer.*;

public class ClientTest implements MouseListener {

	ObjectInputStream in;
	ObjectOutputStream out;
	JFrame frame = new JFrame("Circles");
	JPanel canvas;
	List<Circle> circles = new ArrayList<Circle>();
	//Circle circle;

	public ClientTest() {
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
		canvas.addMouseListener(this);

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
			//g.setColor(Color.RED);
			for (Circle circ : circles) {
				circ.draw(g);
			}
			// System.out.println(circles.size());
//		if (circle != null){
//		circle.draw(g);
			canvas.repaint();
//	}
		}
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

		// Process all messages from server, according to the protocol.
		while (true) {

			// System.out.println("client stuck in run");
			// System.out.println("about to read line");
			Circle c = (Circle) in.readObject();
			System.out.println(c.x + " " + c.y);
			circles.add(c);
			//circle = c;

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
			Server s = new Server(9003);
		}
		else if(n == 1){
			ClientTest client = new ClientTest();
			client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.frame.setVisible(true);
			client.run();
		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		// circles.add(new Circle(e.getX(), e.getY()));
		try {
			System.out.println("writing object");
			out.writeObject(new Circle(e.getX(), e.getY(), Color.RED));
		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}



	public void mouseClicked(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}