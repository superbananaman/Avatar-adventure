package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame {

	JPanel pane;

	int buttonWidth = 300;
	int buttonHeight = 50;
	int fromTop = 125;
	int spacing = 8;
	int width = 550;
	int height = 707;

	public MainFrame(){
		super("Frame 1");

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("background/betterBackground.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentPane(new JLabel(new ImageIcon(img)));

		setBounds(100,100,width,height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		Container con = this.getContentPane();

		pane = new JPanel();
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
		b.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent evt) {
		           new AboutFrame();
				}});
		numButtons++;
		pane.add(b);

		pane.setOpaque(false);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e){

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
