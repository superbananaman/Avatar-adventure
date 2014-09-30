package gui;
/**
 * @author Devlin Mahoney
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class ClientOptions extends JFrame{
	int squareSize = 25;
	int width = 14*squareSize;
	int height = 9*squareSize;

	JPanel pane = new JPanel();

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

		JTextField addressText = new JTextField();
		addressText.setLocation(squareSize*6, squareSize);
		addressText.setSize(squareSize*6,squareSize);
		pane.add(addressText);

		JTextField nameText = new JTextField();
		nameText.setLocation(squareSize*6,squareSize*3);
		nameText.setSize(squareSize*6,squareSize);
		pane.add(nameText);

		JButton startButton = new JButton("Join");
		startButton.setLocation(3*squareSize,5*squareSize);
		startButton.setSize(8*squareSize,2*squareSize);
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new ClientFrame();
			}
		});
		pane.add(startButton);

		setVisible(true);
	}
}
