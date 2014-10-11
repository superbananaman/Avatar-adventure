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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ClientServer.Slave;
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

	Room room;

	Tile[][] testtileset;
	Renderer renderer = new Renderer();
	public int offsetX=-620;
	public int offsetY =250;
	Sprite currentSprite = new Sprite("sprite",-60,70);
	private List<Player> players;
	private Player clientPlayer;


	public RenderWindow(String RoomName, Player clientPlayer, ArrayList<Player> players) {
		setBounds(0, 0, width, height);
		this.players = players;
		panel.setLayout(null);
		panel.setSize(height, width);
		clientPlayer = clientPlayer;

		addKeyListener(this);
		setVisible(true);
		room = new Room(RoomName);
		setupTileset(room);
	}

private void setupTileset(Room currentRoom) {
	room.setTileSet(renderer.parseTileSet(currentRoom));
	}

public void paint(Graphics g){
	g.setColor(Color.black);
	g.fillRect(0, 0, 2500, 2500);
	update(g);
}
	public void update(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
	    //Draw Background once only
	    if (firstTime) {
	      bi = (BufferedImage) createImage(2500,2000);
	      big = bi.createGraphics();
	      big.setColor(Color.black);
	      big.fillRect(0, 0, 2500, 2000);
	      renderer.drawLevel(big, room);
	      firstTime = false;
	    }

	    //Repetitive drawing tasks
	    g2.drawImage(bi,offsetX,offsetY, null);
		renderer.drawSprite(g2,players);

	}

	public void keyPressed(KeyEvent e) {
		Slave.sendKeyEvent(e);
	}
	
	public void receiveKeyEvent(KeyEvent e, Player player){ 
		Player currentPlayer = null;
		for(Player play : players){
			if(player.equals(play))
				currentPlayer = player;		
		}
		if(currentPlayer == null)
			throw new Error("Player not found");
		
		Sprite currentSprite = currentPlayer.getSprite();
		// find sprite
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {			
			currentSprite.setFacing("Right");
			if(checkValidMove(currentSprite, 4, 2, room)){
			currentSprite.addCurrentX(4); currentSprite.addCurrentY(2);
			currentSprite.step();System.out.println("checking right");
			//if(currentPlayer.equals(clientPlayer)){
			offsetX-=4; offsetY-=2;
			//}
			this.repaint();
			}
		}
			
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			//move up map
			if(checkValidMove(currentSprite, -4, -2, room)){

			currentSprite.setFacing("Left");
			currentSprite.addCurrentX(-4); currentSprite.addCurrentY(-2);
			currentSprite.step();
			//if(currentPlayer.equals(clientPlayer)){
			offsetX+=4; offsetY+=2;
			//}
			this.repaint();
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
		//move right map
			if(checkValidMove(currentSprite, 4, -2, room)){

			currentSprite.setFacing("Up");
			currentSprite.addCurrentX(4); currentSprite.addCurrentY(-2);
			currentSprite.step();
			//if(currentPlayer.equals(clientPlayer)){
			offsetX-=4; offsetY+=2;
			//}
			this.repaint();
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		//move left map
			if(checkValidMove(currentSprite, -4, 2, room)){

			currentSprite.setFacing("Down");
			currentSprite.addCurrentX(-4); currentSprite.addCurrentY(2);
			currentSprite.step();
			//if(currentPlayer.equals(clientPlayer)){
			offsetX+=4; offsetY-=2;
			//}
			//test++; System.out.println(test);
			this.repaint();
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_R ) {
		//rotate room 90 degrees clockwise
			room.setTileSet(renderer.rotate(room));
			firstTime =true;
			this.repaint();
			}




	}

	private boolean checkValidMove(Sprite currrentSprite, int x, int y, Room room) {
		Point cartesian = renderer.isoTo2D(new Point(currrentSprite.getCurrentX()+x,currentSprite.getCurrentY()+y));
		Tile[][] proposedTile = room.getTileSet();
		
		//System.out.println("Iso "+currentSprite.getCurrentX()+"  :  "+currentSprite.getCurrentY()+"  to   "+cartesian.x+"   :   "+cartesian.y);
		//	proposedTile[cartesian.y][cartesian.x].isWalkable();
		return true;

		}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void changeRoom(String roomName){
		firstTime =true;
		room = new Room(roomName);
		this.repaint();
	}
	
	public Tile getTile(int x, int y){
		x+=offsetX; y+=offsetY;
		Point cart = renderer.isoTo2D(new Point(x,y));
	return room.getTileSet()[cart.y][cart.x];
	}
}

