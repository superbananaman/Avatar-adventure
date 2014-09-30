package gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	JPanel pane = new JPanel();

	int buttonWidth = 300;
	int buttonHeight = 50;
	int fromTop = 125;
	int spacing = 8;
	int width = 500;
	int height = 550;

	public MainFrame(){
		super("Frame 1");
		setBounds(100,100,width,height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		Container con = this.getContentPane();
		pane.setLayout(null);
		pane.setSize(height,width);
		con.add(pane);

		int numButtons = 0;
		JButton b = new JButton("Client");
		b.setMnemonic(KeyEvent.VK_C);
		b.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent evt) {
	           new ClientOptions();
		}});
		b.setLocation((width-buttonWidth)/2,fromTop+buttonHeight*numButtons+spacing*numButtons);
		b.setSize(buttonWidth,buttonHeight);
		numButtons++;
		pane.add(b);

		b = new JButton("Server");
		b.setMnemonic(KeyEvent.VK_S);
		b.setLocation((width-buttonWidth)/2,fromTop+buttonHeight*numButtons+spacing*numButtons);
		b.setSize(buttonWidth,buttonHeight);
		b.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent evt) {
	           new ServerFrame();
			}});
		numButtons++;
		pane.add(b);

		b = new JButton("About");
		b.setLocation((width-buttonWidth)/2,fromTop+buttonHeight*numButtons+spacing*numButtons);
		b.setSize(buttonWidth,buttonHeight);
		numButtons++;
		pane.add(b);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e){

	}

	public static void main(String args[]) {
		new MainFrame();
	}
}
