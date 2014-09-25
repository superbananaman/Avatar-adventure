package tests;

import gameLogic.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Renderer.*;

public class RenderTests extends JFrame implements KeyListener {
private boolean firstTime = true;

BufferedImage bi = new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
Graphics2D big;

	JPanel panel = new JPanel();
	
	int width = 800;
	int height = 600;
	Rectangle area; 	

	Room r = new Room(12, 12);
	Renderer renderer = new Renderer();

	// Lander test sprite
	private int[] landerXS = { 11, 13, 27, 29, 30, 26, 37, 40, 40, 30, 30, 33,
			24, 21, 24, 16, 19, 16, 7, 0, 0, 10, 10, 3, 14, 10 };
	private int[] landerYS = { 5, 0, 0, 5, 20, 20, 35, 35, 40, 40, 35, 35, 20,
			20, 25, 25, 20, 20, 35, 35, 40, 40, 35, 35, 20, 20 };
	private Polygon lander;

	public RenderTests() {
		super("Renderer Test");
		setBounds(0, 0, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();
		panel.setLayout(null);
		panel.setSize(height, width);
		contentPane.add(panel);
		addKeyListener(this);
		setVisible(true);

		lander = new Polygon(landerXS, landerYS, landerYS.length);
		lander.translate(300, 150);

	}
public void paint(Graphics g){
	update(g);
}
	public void update(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;

	    if (firstTime) {
	      int w = 800;
	      int h = 600;
	      area = new Rectangle(800,600);
	      bi = (BufferedImage) createImage(w, h);
	      big = bi.createGraphics();
	      renderer.drawLevel(big, r);
	      firstTime = false;
	    }


	    

	    g2.drawImage(bi, 0, 0, this);
	  
		// super.paint(g);
		g2.setColor(Color.CYAN);
		g2.fillPolygon(lander);

	}

	public static void main(String args[]) {
		new RenderTests();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			lander.translate(10, 5);
			this.repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			lander.translate(-10, -5);
			this.repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			lander.translate(10, -5);
			this.repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			lander.translate(-10, 5);
			this.repaint();
		}

	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
