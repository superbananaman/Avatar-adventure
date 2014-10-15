package gui;
/**
 * @author Devlin Mahoney
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

import ClientServer.Server;
import ClientServer.Slave;


public class ClientOptions extends JFrame implements ActionListener{
	int squareSize = 25;
	int width = 14*squareSize;
	int height = 9*squareSize;

	JPanel pane = new JPanel();

	JTextField addressText;
	JTextField nameText;
	JTextField portText;

	String address = "";
	String name = "";

	public ClientOptions(){
		super("Client Options");
		setBounds(100,100,width,height);
		setResizable(false);
		Container con = this.getContentPane();
		pane.setLayout(null);
		pane.setSize(width,height);
		con.add(pane);

		JLabel jl = new JLabel("Address:");
		jl.setLocation(2*squareSize, squareSize);
		jl.setSize(squareSize*4, squareSize);
		pane.add(jl);

		jl = new JLabel("Char Name:");
		jl.setLocation(2*squareSize, squareSize*3);
		jl.setSize(squareSize*4, squareSize);
		pane.add(jl);

		jl = new JLabel("Port:");
		jl.setLocation(2*squareSize, squareSize*2);
		jl.setSize(squareSize*4, squareSize);
		pane.add(jl);

		addressText = new JTextField();
		addressText.setLocation(squareSize*6, squareSize);
		addressText.setSize(squareSize*6,squareSize);
		pane.add(addressText);

		portText = new JTextField();
		portText.setLocation(squareSize*6,squareSize*2);
		portText.setSize(squareSize*6,squareSize);
		pane.add(portText);

		nameText = new JTextField();
		nameText.setLocation(squareSize*6,squareSize*3);
		nameText.setSize(squareSize*6,squareSize);
		pane.add(nameText);

		JButton startButton = new JButton("Join");
		startButton.setLocation(3*squareSize,5*squareSize);
		startButton.setSize(8*squareSize,2*squareSize);
		startButton.addActionListener(this);
		pane.add(startButton);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Join")){

			if (nameText.getText().isEmpty()){

				JOptionPane.showMessageDialog(this, "Please enter a name", "Name error", JOptionPane.ERROR_MESSAGE);
				return;
			}
//			try{
//			int num = Integer.parseInt(portText.getText());
				Slave s;
				try {
					s = new Slave(InetAddress.getLocalHost().getHostAddress(), 14141, nameText.getText(), null);
					s.start();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				}


				this.setVisible(false);
				this.setEnabled(false);

//			} catch (NumberFormatException nfe) {
//
//			}
		}
	}
}
