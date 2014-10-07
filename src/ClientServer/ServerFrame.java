package ClientServer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerFrame extends JFrame{
	
	private JPanel buttons;
	private TextArea messageArea;
	
	public ServerFrame(){
		super("Server");
		buttons = new JPanel();
		buttons.setSize(200, 600);
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		
		messageArea = new TextArea(20, 40);
		messageArea.setEditable(false);
		// add save and load buttons
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO SAVE THE GAME				
			}
		});
		JButton load = new JButton("Load");
		save.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO LOAD THE GAME				
			}
		});
		buttons.add(save);
		buttons.add(load);
		
		
		//buttons.setBackground(Color.BLUE);
		this.add(messageArea);
		this.add(buttons, BorderLayout.EAST);
		
		// set up the frame
		this.setSize(500, 500);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	public void appendMessage(String text){
		messageArea.append(text + "\n");
	}
	
//	public static void main(String[] args) {
//		//new ServerFrame();
//		Server s = new Server(2, 9003);
//	}
}
