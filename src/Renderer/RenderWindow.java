package Renderer;
/**
 * @author parkerliam
 */
import gameLogic.*;
import gui.ClientFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import ClientServer.Slave;

public class RenderWindow extends JPanel implements KeyListener {
	/**
	 * Eclipse complains about adding this ?
	 */
	private static final long serialVersionUID = 1L;

	private boolean firstTime = true;

	BufferedImage bi = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
	Graphics2D big;

	JPanel panel = new JPanel();

	int width = 800;
	int height = 600;
	Rectangle area;

	Room room;

	Tile[][] testtileset;
	public Renderer renderer = new Renderer();
	public int offsetX = -620; // default offset values
	public int offsetY = 250;

	public int cameraOffsetX = 0;
	public int cameraOffsetY = 0;

	Sprite currentSprite = new Sprite("sprite", -60, 70);
	private List<Player> playersNonClient;
	private List<Player> players;
	private Player clientPlayer;
	private Game game;

	private int selectedSpace = -1;

	/**
	 *Constructor of RenderWindow, Takes the first room to create, the client player (this player) User id  and the players in the render window and the game
	 * @param roomName
	 * @param uID
	 * @param players
	 */
	public RenderWindow(String roomName, String uID, ArrayList<Player> players, Game game) {
		this.players = players;
		this.game = game;

		setBounds(0, 0, width, height);
		panel.setLayout(null);
		panel.setSize(height, width);

		for (Player player : players) { //Find which player in players is the client player
			if (player.getUID().equals(uID))
				this.clientPlayer = player;
		}
		System.out.println(clientPlayer.getUID());
		this.playersNonClient = players;
		// playersNonClient.remove(clientPlayer);
		addKeyListener(this);
		setVisible(true);
		room = game.getRoom(roomName); //set the room to start in
		setupPlayersInRoom(room);
	}

	/**
	 * Sets up the players location s and y in sprites to the new room @param currentRoom
	 *
	 * @param currentRoom
	 */
	private void setupPlayersInRoom(Room currentRoom) {
		offsetX = -620 + currentRoom.getOffSet().x;
		offsetY = 250 + currentRoom.getOffSet().y;
		Point spawn = room.getSpawnSpots().getLocation(); //finds the spawn in the room
		for (Player p : players) {
			if(!p.isAlive()){
				p.setCurrentHealth(100);
				p.setAlive(true);
				game.getClientFrame().updateInventory();
				firstTime = true;
				this.repaint();
			}
			p.setCurrentRoom(currentRoom);
			p.getSprite().setCurrentX(0);
			p.getSprite().setCurrentY(0);
			p.getSprite().setOffsetX(
					currentRoom.getSpawnSpots().getLocation().x);
			p.getSprite().setOffsetY(
					currentRoom.getSpawnSpots().getLocation().y);
		}

	}

	/**
	 * The default method called each time the window is to be painted, Draws
	 * the background then calls update(g) to draw the level + sprites
	 *
	 * @param Graphics
	 */
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 2500, 2500);
		update(g);
	}

	/**
	 * Called everytime by paint this method on first time round draws the background from the rooms tileset using a painters alogirthm onto a buffered image
	 * then draws the buffered image onto the view window. Lastly renderers the sprite onto the view window
	 *
	 */
	public void update(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Draw Background once only
		if (firstTime) {
			bi = (BufferedImage) createImage(2500, 2000);
			big = bi.createGraphics();
			big.setColor(Color.black);
			big.fillRect(0, 0, 2500, 2000);
			renderer.drawLevel(big, room);
			firstTime = false;
		}

		// Repetitive drawing tasks
		g2.drawImage(bi, offsetX, offsetY, null);
		renderer.drawSprite(g2, playersNonClient, clientPlayer);
		// renderer.drawSpriteClientPlayer(g2, clientPlayer);
	}

	/**
	 * Sends the key pressed on the renderwindow to the server so the server can send the event back to all players
	 * @param KeyEvent e
	 *
	 */
	public void keyPressed(KeyEvent e) {
		Slave.sendKeyEvent(e);
	}

	/**
	 * Receives the key event from slave and process it for the specific player in the game
	 * updating the player fields and sprites
	 *
	 * @param e
	 * @param player
	 */
	public void receiveKeyEvent(KeyEvent e, Player player) {
		Player currentPlayer = null;

		for (Player p : players) {
			if (player.equals(p))
				currentPlayer = player;
		}
		if (currentPlayer == null)
			throw new Error("Player not found");

		currentSprite = currentPlayer.getSprite();
		// find sprite

		/**
		 *  For Moving: take the direction, check if the move is valid (not into a wall etc) then update the sprites x and y values
		 *  then if the player moved is the client player move the map to simulate the camera panning across.
		 *
		 */
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			currentSprite.setFacing("Right");
			if (checkValidMove(currentSprite, 4, 2, room)) {
				currentSprite.addCurrentX(4);
				currentSprite.addCurrentY(2);
				currentSprite.step();
				if (currentPlayer.equals(clientPlayer)) {
					cameraOffsetX = 4;
					cameraOffsetY = 2;
					offsetX -= 4;
					offsetY -= 2;
				}
				this.repaint();
				if (currentPlayer.equals(clientPlayer)) {
					currentSprite.addOffsetX(cameraOffsetX);
					currentSprite.addOffsetY(cameraOffsetY);
					this.repaint();
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			// move up map
			if (checkValidMove(currentSprite, -4, -2, room)) {

				currentSprite.setFacing("Left");
				currentSprite.addCurrentX(-4);
				currentSprite.addCurrentY(-2);
				currentSprite.step();
				if (currentPlayer.equals(clientPlayer)) {
					cameraOffsetX = -4;
					cameraOffsetY = -2;
					offsetX += 4;
					offsetY += 2;
				}
				this.repaint();
				if (currentPlayer.equals(clientPlayer)) {
					currentSprite.addOffsetX(cameraOffsetX);
					currentSprite.addOffsetY(cameraOffsetY);
					this.repaint();
				}
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			// move right map
			if (checkValidMove(currentSprite, 4, -2, room)) {

				currentSprite.setFacing("Up");
				currentSprite.addCurrentX(4);
				currentSprite.addCurrentY(-2);
				currentSprite.step();
				if (currentPlayer.equals(clientPlayer)) {
					cameraOffsetX = 4;
					cameraOffsetY = -2;
					offsetX -= 4;
					offsetY += 2;
				}
				this.repaint();
				if (currentPlayer.equals(clientPlayer)) {
					currentSprite.addOffsetX(cameraOffsetX);
					currentSprite.addOffsetY(cameraOffsetY);
					this.repaint();
				}
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			// move left map
			if (checkValidMove(currentSprite, -4, 2, room)) {

				currentSprite.setFacing("Down");
				currentSprite.addCurrentX(-4);
				currentSprite.addCurrentY(2);
				currentSprite.step();
				if (currentPlayer.equals(clientPlayer)) {
					cameraOffsetX = -4;
					cameraOffsetY = 2;
					offsetX += 4;
					offsetY -= 2;
				}
				// test++; System.out.println(test);
				this.repaint();
				if (currentPlayer.equals(clientPlayer)) {
					currentSprite.addOffsetX(cameraOffsetX);
					currentSprite.addOffsetY(cameraOffsetY);
					this.repaint();
				}
			}

		}
		/**
		 *  For rotating: calls the renderer to rotate the tiles in the given room
		 *  calls firsttime true to repaint the background
		 */
		if (e.getKeyCode() == KeyEvent.VK_R) {
			// rotate room 90 degrees clockwise
			room.setTileSet(renderer.rotate(room));
			firstTime = true;
			this.repaint();

		}
		/**
		 *  For changing room: calls the change room method on the currentplayer
		 */
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {

			changeRoom(currentPlayer);
			this.repaint();
		}
		/**
		 *  For pickiing up: finds where the player is and checks if there is an item close/on the tile and calls pickup method on player if it finds an item
		 */
		if (e.getKeyCode() == KeyEvent.VK_P) {
			Tile itemTile = null;
			Point playerPoint = renderer.isoTo2D(new Point(currentSprite.getCurrentX(), currentSprite.getCurrentY())); playerPoint.x += room.getSpawnSpots().getLocation().x; playerPoint.y += room.getSpawnSpots().getLocation().y;
			for(Item i : room.getItems()){
				Point location = i.getTile().getLocation();
				if(location.distance(playerPoint) < 2){
					itemTile = i.getTile();
					room.getItems().remove(i);
					i.getTile().setPickUpImage(null);
					currentPlayer.pickUp(itemTile);
					break;

				}

			}
			game.getClientFrame().updateInventory();
			firstTime=true;
			this.repaint();
		}
		/**
		 *  For dropping: finds the player position and gives that tile an item / replaces that item, sets the item image  and changes the boolean on tile has item, takes it out of the current player inventory
		 */
		if (e.getKeyCode() == KeyEvent.VK_O) {
			selectedSpace = currentPlayer.getInventory().getSelectedSpace();
			Point playerPoint = renderer.isoTo2D(new Point(currentSprite.getCurrentX(), currentSprite.getCurrentY())); playerPoint.x += room.getSpawnSpots().getLocation().x; playerPoint.y += room.getSpawnSpots().getLocation().y;
			if (selectedSpace != -1) {

				Item item = currentPlayer.getInventory().get(selectedSpace);
				if(item != null){
				currentPlayer.dropItem(item,room.getTileSet()[playerPoint.y][playerPoint.x]);

				item.setTile(room.getTileSet()[playerPoint.y][playerPoint.x]);
				room.getTileSet()[playerPoint.y][playerPoint.x].setItem(item);
				room.getItems().add(item);

				String name = item.getName();

				BufferedImage temp = null;
				if(name.equals("Apple")){
					temp = renderer.Apple;
	        	}else if(name.equals("Banana")){
	        		temp = renderer.Banana;
	        	}else if(name.equals("Mango")){
	        		temp = renderer.Mango;
	        	}else if(name.equals("RedPot")){
	        		temp = renderer.RedPot;
	        	}else if(name.equals("ArmorHead")){
	        		temp = renderer.ArmorHead;
	        	}else if(name.equals("ArmorChest")){
	        		temp = renderer.ArmorChest;
	        	}else if(name.equals("ArmorLegs")){
	        		temp = renderer.ArmorLegs;
	        	}else {
	        		temp = renderer.KeyRoom2;
	        	}
				room.getTileSet()[playerPoint.y][playerPoint.x].setPickUpImage(temp);

			}
			firstTime = true;
			this.repaint();
			currentPlayer.getInventory().setSelectedSpace(-1);
			game.getClientFrame().updateInventory();
			}
		}
		/**
		 *  For equipping: finds the selected item in the inventory and updates the players max health with the item stats
		 */

		if(e.getKeyCode() == KeyEvent.VK_E) {
			if(currentPlayer.getInventory().getSelectedSpace() == -1 || currentPlayer.getInventory().getSelectedSpace() < currentPlayer.getInventory().getItems().size()){
				Item selectedItem = currentPlayer.getInventory().get(currentPlayer.getInventory().getSelectedSpace());
				if(selectedItem instanceof Armour){

					selectedItem.use(currentPlayer);
					game.getClientFrame().updateInventory();
				}
				else if(selectedItem instanceof Fruit || selectedItem instanceof Potion){
					selectedItem.use(currentPlayer);
					currentPlayer.getInventory().remove(selectedItem);
					game.getClientFrame().updateInventory();
				}
			}
		}

		/**
		 *  For attacking: finds the monster the player is on / close to and hits the monster with the players attack, the monster then hits back  and updates the players current health
		 */
		if(e.getKeyCode() == KeyEvent.VK_K) {
			Point playerPoint = renderer.isoTo2D(new Point(currentSprite.getCurrentX(), currentSprite.getCurrentY())); playerPoint.x += room.getSpawnSpots().getLocation().x; playerPoint.y += room.getSpawnSpots().getLocation().y;
			for(Monster m : room.getMonsters()){
				Point location = m.getTile().getLocation();
				if(location.distance(playerPoint) < 2){

					//m.takeDamage(currentPlayer.attack1());
					currentPlayer.updateCurrentHealth(-(m.attack()));
					if(currentPlayer.equals(clientPlayer)){
						Slave.sendMonsterAttack(currentPlayer.attack1(), m.getTile().getLocation());
					}
					game.getClientFrame().updateInventory();

					firstTime = true;
					this.repaint();
					break;
				}
			}
			game.getClientFrame().updateInventory();
			firstTime = true;
			this.repaint();
		}

		if(e.getKeyCode() == KeyEvent.VK_L) {

		}


		/*
		 * for(int i=0; i <playersNonClient.size();i++){
		 * System.out.print("Player:"+i+"  "+player.getUID()+"  "+
		 * playersNonClient
		 * .get(i).getSprite().getCurrentX()+"   :   "+playersNonClient
		 * .get(i).getSprite().getCurrentY()+" to TILE[x][y]  ->  ");
		 * System.out.
		 * println(getTile(playersNonClient.get(i).getSprite().getCurrentX
		 * (),playersNonClient.get(i).getSprite().getCurrentY()).getLocation());
		 *
		 * }
		 */

		// Slave.sendPlayer(currentPlayer);

	}

	/**
	 * Returns if the tile the sprite is about to walk on is walkable
	 *
	 * @param currentSprite
	 * @param x
	 * @param y
	 * @param room
	 * @return
	 */
	private boolean checkValidMove(Sprite currentSprite, int x, int y, Room room) {
		Point playerPoint = renderer.isoTo2D(new Point(currentSprite.getCurrentX() + x + 20, currentSprite.getCurrentY() + y + 20));
		playerPoint.x += room.getSpawnSpots().getLocation().x;
		playerPoint.y += room.getSpawnSpots().getLocation().y;
		if (playerPoint.x > 29 || playerPoint.y > 29 || playerPoint.x < 0
				|| playerPoint.y < 0) {
			return false;
		}
		Tile[][] proposedTile = room.getTileSet();
		return proposedTile[playerPoint.y][playerPoint.x].isWalkable();

	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void repaintBackground(){
		firstTime = true;
		this.repaint();
	}

	/**
	 * Changes the room, Checks if player is near a door then if the player has
	 * the key to unlock the door
	 */
	public void changeRoom(Player currentPlayer) {
		String nextRoom = null;
		for (Door door : room.getDoors()) {
			Point doorPoint = door.getTile().getLocation();
			Point playerPoint = renderer.isoTo2D(new Point(currentSprite
					.getCurrentX(), currentSprite.getCurrentY()));
			playerPoint.x += room.getSpawnSpots().getLocation().x;
			playerPoint.y += room.getSpawnSpots().getLocation().y;

			/*System.out.println("Checking door in "
					+ door.getRoom().getRoomName() + " to "
					+ door.getNextRoom() + "\t  " + doorPoint.toString() + "  "
					+ playerPoint.toString() + "\t"
					+ doorPoint.distance(playerPoint));*/
			boolean hasKey = (currentPlayer.getInventory().hasKey(door.getNextRoom())|| door.getNextRoom().equals("room1") || door.getNextRoom().equals("startroom"));

			if (doorPoint.distance(playerPoint) < 3 && hasKey) {
				nextRoom = door.getNextRoom();
				room = game.getRoom(nextRoom);
				//System.out.println("Changing to room : " + nextRoom);
				break;
			}
		}
		if (nextRoom != null) {
			//System.out.println("Changing rooom now");
			firstTime = true;
			game.getClientFrame().toConsole("Changed room to " + nextRoom);
			this.setupPlayersInRoom(room);
			this.repaint();
		}
	}

	/**
	 * Returns the tile on x,y
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(int x, int y) {
		// Add the screen offset to get map position on canvas

		Point cart = renderer.isoTo2D(new Point(x, y));
		// Add the spawn offset
		cart.x += room.getSpawnSpots().getLocation().x;
		cart.y += room.getSpawnSpots().getLocation().y;

		return room.getTileSet()[cart.y][cart.x];
	}

}
