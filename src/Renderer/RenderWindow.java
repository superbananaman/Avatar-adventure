package Renderer;

import gameLogic.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Renderer.*;

public class RenderWindow extends JPanel implements KeyListener {
/**
	 * Eclipse complains about adding this ?
	 */
	private static final long serialVersionUID = 1L;

private boolean firstTime = true;

BufferedImage bi = new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
Graphics2D big;

	JPanel panel = new JPanel();
	
	int width = 800;
	int height = 600;
	Rectangle area; 	

	Room room = new Room(30,30);
	Sprite currentSprite = new Sprite("Sprite",300,400);
	Tile[][] testtileset;
	Renderer renderer = new Renderer();
	private int offsetX=0;
	private int offsetY =0;

	private String currentRoom;
	

	public RenderWindow(String RoomName) {
		//super("Renderer Test");
		setBounds(0, 0, width, height);

		panel.setLayout(null);
		panel.setSize(height, width);

		addKeyListener(this);
		setVisible(true);		
		currentRoom=RoomName;
	}
	
private void setupTestRoom(String roomName) {
	room.setTileSet(renderer.parseTileSet(roomName)); 
	}

public void paint(Graphics g){
	update(g);
}
	public void update(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
	    //Draw Background once only
	    if (firstTime) {
	      int w = 2500;
	      int h = 2500;
	      area = new Rectangle(800,600);
	      bi = (BufferedImage) createImage(w, h);
	      big = bi.createGraphics();
	      setupTestRoom(currentRoom);
	      big.setColor(Color.black);
	      big.fillRect(0, 0, 2500, 2500);
	      renderer.drawLevel(big, room);
	      firstTime = false;
	    }

	    //Rpeatitive drawing tasks

	    g2.drawImage(bi,offsetX,offsetY, null);
	  	g2.setColor(Color.CYAN);
		renderer.drawSprite(g2,currentSprite);

	}

	public static void main(String args[]) {
		JPanel panel = new RenderWindow("Room1");
		JFrame canvas = new JFrame();
		canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.setContentPane(panel);
		canvas.setSize(800, 600);
		canvas.setVisible(true);
		panel.setFocusable(true);
		
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		//move dowwn map
			currentSprite.setFacing("Right");
			if(checkValidMove(currentSprite, currentSprite.getCurrentX(), currentSprite.getCurrentY(), room)){
			currentSprite.setCurrentX(currentSprite.getCurrentX() - 4); currentSprite.setCurrentY(currentSprite.getCurrentY() - 2);
			currentSprite.setStep(currentSprite.getStep() +1);			
			offsetX-=4; offsetY-=2;
			this.repaint();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			//move up map
			if(checkValidMove(currentSprite, currentSprite.getCurrentX(), currentSprite.getCurrentY(), room)){

			currentSprite.setFacing("Left");
			currentSprite.setCurrentX(currentSprite.getCurrentX() - 4); currentSprite.setCurrentY(currentSprite.getCurrentY() - 2);
			currentSprite.setStep(currentSprite.getStep() +1);
			offsetX+=4; offsetY+=2;
			this.repaint();
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
		//move right map
			if(checkValidMove(currentSprite, currentSprite.getCurrentX(), currentSprite.getCurrentY(), room)){

			currentSprite.setFacing("Up");
			currentSprite.setCurrentX(currentSprite.getCurrentX() + 4);  currentSprite.setCurrentY(currentSprite.getCurrentY() - 2);
			currentSprite.setStep(currentSprite.getStep() +1);
			offsetX-=4; offsetY+=2;
			this.repaint();
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		//move left map
			if(checkValidMove(currentSprite, currentSprite.getCurrentX(), currentSprite.getCurrentY(), room)){

			currentSprite.setFacing("Down");
			currentSprite.setCurrentX(currentSprite.getCurrentX() - 4); currentSprite.setCurrentY(currentSprite.getCurrentY() + 2);
			currentSprite.setStep(currentSprite.getStep() +1);
			offsetX+=4; offsetY-=2;
			this.repaint();
			}

		}
		
	}

	private boolean checkValidMove(Sprite currrentSprite, int xProposed, int yProposed, Room room) {
		System.out.println(currentSprite.getCurrentX()+" :  "+currentSprite.getCurrentY());
		int newX = currrentSprite.getCurrentX()+xProposed;
		int newY = currrentSprite.getCurrentX()+xProposed;
		//int cartesianX = (newX / 64 * 2) + (newY / 64 * 2);
		// int cartesianY = (newY / 32 * 2) - (newX / 32 * 2);
		int cartesianX = renderer.isoTo2D(new Point(newX,newY)).x;
		int cartesianY = renderer.isoTo2D(new Point(newX,newY)).y;
				 System.out.println("iso X: "+newX+" : "+(cartesianX/30)+" iso Y: "+newY+" : "+ (cartesianY/30));
		return true;
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}	
	
	public void changeRoom(String roomName){
		firstTime =false;
		currentRoom = roomName;
		this.repaint();
	}

}
 
