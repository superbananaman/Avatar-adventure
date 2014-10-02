package ClientServer;

/**
 * @author Brendan Smith, Matt Catsburg
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Slave implements KeyListener {

	Socket socket;

//	BufferedReader in;
//	PrintWriter out;
//	JFrame frame = new JFrame("Chatter");
//	JTextField textField = new JTextField(40);
//	JTextArea messageArea = new JTextArea(8, 40);

	ObjectOutputStream out;
	ObjectInputStream in;

	public Slave(Socket s) {

		socket = s;

		// Layout GUI
//		textField.setEditable(false);
//		messageArea.setEditable(false);
//		frame.getContentPane().add(textField, "North");
//		frame.getContentPane().add(new JScrollPane(messageArea), "Center");
//		frame.pack();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//
//		// Add Listeners
//		textField.addActionListener(new ActionListener() {
//			/**
//			 * Responds to pressing the enter key in the textfield by sending
//			 * the contents of the text field to the server. Then clear the text
//			 * area in preparation for the next message.
//			 */
//			public void actionPerformed(ActionEvent e) {
//				out.println(textField.getText());
//				textField.setText("");
//			}
//		});


		try {
			run();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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


		while (true) {
			Object o = in.readObject();
			// represents a key being pressed or uid
			if (o instanceof Integer){
				Integer i = (Integer)o;
				if (i.equals(1)){
					//Move player up
				}
				else if (i.equals(2)){
					//Move player down
				}
				else if (i.equals(3)){
					//Move player left
				}
				else if (i.equals(4)){
					//Move player right
				}
				// the uid
				else{
					//uid = i;
				}
			}


//			String line = in.readLine();
//			if (line.startsWith("SUBMITNAME")) {
//				out.println(getName());
//			} else if (line.startsWith("NAMEACCEPTED")) {
//				textField.setEditable(true);
//			} else if (line.startsWith("MESSAGE")) {
//				messageArea.append(line.substring(8) + "\n");
//			}
		}
	}



	public void keyPressed(KeyEvent e) {

		// send an event to the server to write to all clients
		try {
			//first send the uid of the player being moved
			//TODO get players uid
			int code = e.getKeyCode();
			if (code == KeyEvent.VK_UP) {
				out.writeObject(new Integer(1));
			} else if (code == KeyEvent.VK_DOWN) {
				out.writeInt(2);
			} else if (code == KeyEvent.VK_LEFT) {
				out.writeInt(3);
			} else if (code == KeyEvent.VK_RIGHT) {
				out.writeInt(4);
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
