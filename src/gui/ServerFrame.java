package gui;
/**
 * The window for starting up an avatar adventure Server
 * @author Devlin Mahoney
 */
import java.awt.*;
import javax.swing.*;


//Implement a layout manager instead of using setSize

public class ServerFrame extends JFrame{

	int squareSize = 25;
	int width = 12*squareSize;
	int height = 9*squareSize;

	JPanel pane = new JPanel();

	String port = "";
	String save = "";

	public ServerFrame(){
		super("Server Options");
		setBounds(100,100,width,height);
		setResizable(false);
		Container con = this.getContentPane();
		pane.setLayout(null);
		pane.setSize(width,height);
		con.add(pane);

		JLabel jl = new JLabel("Port:");
		jl.setLocation(2*squareSize, squareSize);
		jl.setSize(squareSize*3, squareSize);
		pane.add(jl);

		jl = new JLabel("Save:");
		jl.setLocation(2*squareSize, squareSize*3);
		jl.setSize(squareSize*3, squareSize);
		pane.add(jl);

		JTextField portText = new JTextField();
		portText.setLocation(squareSize*5, squareSize);
		portText.setSize(squareSize*5,squareSize);
		pane.add(portText);

		JTextField saveText = new JTextField();
		saveText.setLocation(squareSize*5, squareSize*3);
		saveText.setSize(squareSize*5,squareSize);
		pane.add(saveText);

		JButton startButton = new JButton("Start");
		startButton.setLocation(2*squareSize,5*squareSize);
		startButton.setSize(8*squareSize,2*squareSize);
		pane.add(startButton);

		setVisible(true);
	}
}
