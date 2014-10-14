package Renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import gameLogic.Armour;
import gameLogic.Door;
import gameLogic.Fruit;
import gameLogic.Item;
import gameLogic.Key;
import gameLogic.Monster;
import gameLogic.Player;
import gameLogic.Room;
import gameLogic.Skeleton;

public class Renderer {
//BufferedImages for ISO tiles
	final BufferedImage empty= getIso(64,64*2);
	final BufferedImage wall= getIso(0,0);
	final BufferedImage floor = getIso(0,64*5);
	final BufferedImage Barrel = getIso(64*9,64*5);
	final BufferedImage rug = getIso(64*4,64*0);
	final BufferedImage door = getIso(64*1,64*1);
	final BufferedImage crate = getIso(64*3,64*5);
	final BufferedImage skelly = getIso(64*4,64*1);
	final BufferedImage boss1 = getIso(64*2,64*2);
	public final BufferedImage Banana = getIso(64*3,64*2);
	public final BufferedImage Apple = getIso(64*4,64*2);
	public final BufferedImage Mango = getIso(64,64*3);
	public final BufferedImage RedPot = getIso(64*2,64*3);
	public final BufferedImage ArmorHead = getIso(64*3,64*3);
	public final BufferedImage ArmorChest = getIso(64*4,64*3);
	public final BufferedImage ArmorLegs = getIso(64*5,64*3);
	public final BufferedImage KeyRoom2 = getIso(64*2,64*2);
	public final BufferedImage KeyRoom3 = getIso(64*2,64*2);
	public final BufferedImage KeyRoom4 = getIso(64*2,64*2);
	public final BufferedImage KeyRoom5 = getIso(64*2,64*2);



	/**Draws each Tile on the graphics pane from the tileset inside the Room r
	 * @param g
	 * @param r
	 */
	public void drawLevel(Graphics g, Room r) {
		Tile[][] ts = r.getTileSet();
		for (int i = 0; i < 30; i++) {
			for (int j = 0;j <30; j++) {
				placetile(ts[i][j], twoDToIso(new Point(j,i)),g);
			}
		}
	}

	/**draws the tile and point isoPoint on Graphics g
	 * @param tile
	 * @param isoPoint
	 * @param g
	 */
	private void placetile(Tile tile, Point isoPoint, Graphics g) {
	if(tile != null){
		g.drawImage(tile.getBackGroundImage(), isoPoint.x+1100, isoPoint.y+10, null);

		if(tile.getPickUpImage()!=null)
			g.drawImage(tile.getPickUpImage(), isoPoint.x+1100, isoPoint.y+10, null);

		if(tile.getMonsterImage()!=null)
			g.drawImage(tile.getMonsterImage(), isoPoint.x+1100, isoPoint.y+10, null);


	}
	else
		throw new Error("ERROR  TILE  PLACE   NULL");

	}

	/** Returns Point pt in 2D form on a cartesian grid
	 * @param pt
	 * @return
	 */
	public Point isoTo2D(Point pt) {
		Point tempPt = new Point(0, 0);
		tempPt.x = (pt.x / 32 + pt.y / 16)  /2;
		tempPt.y = (pt.y / 16 -(pt.x / 32)) /2;
		return (tempPt);
	}
	/** Returns Point pt in cartesian form to an isometric point on screen
	 * @param pt
	 * @return
	 */
	public Point twoDToIso(Point pt) {
		Point tempPt = new Point(0, 0);
		tempPt.x = ((pt.x - pt.y)*32);
		tempPt.y = ((pt.x + pt.y)*16);
		return (tempPt);
	}

	/**Returns the subimage on the tile sheet at x,y
	 * @param x
	 * @param y
	 * @return
	 */
	public BufferedImage getIso(int x, int y) {
		BufferedImage tilesheet = null;
		try {
			tilesheet = ImageIO.read(new File("iso.png"));
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return tilesheet.getSubimage(x, y, 64, 64);
	}

	/**Populates the rooms tilesheet through a parses from room.txt
	 * @param room
	 * @return
	 */
	public Tile[][] parseTileSet(Room room){ System.out.println(room.getRoomName());
		Tile[][] ts = new Tile[30][30];
		try {
			FileReader roomFile = new FileReader("maps/"+room.getRoomName()+".txt");
			Scanner scan = new Scanner(roomFile);
			System.out.println(scan.next()+"  Loading");
			room.setOffSet(new Point(scan.nextInt(),scan.nextInt()));

			System.out.println(scan.next()+"  Loading");
			for(int i =0; i <30; i++){
				for(int j = 0; j <30; j++){
					Tile temp = null;
				int tile = scan.nextInt();
				Point p = new Point(j,i);
				switch(tile) {
					case 0:								temp = new Tile(empty,p,false); 												break;
					case 1: case 2: case 3:  			temp = new Tile(wall,p,true);													break;
					case 4:							 	temp = new Tile(floor,p,true);													break;
					case 5: 						 	temp = new Tile(Barrel,p,true);													break;
					case 6:								temp = new Tile(rug,p,true);													break;
					case 7: case 8:						temp = new Tile(crate,p,true);													break;
					case 9:								temp = new Tile(door,p,false); room.addDoors(new Door(room, "bossroom",temp));	break;
					case 10:							temp = new Tile(door,p,false); room.addDoors(new Door(room, "startroom",temp));	break;
					case 11:							temp = new Tile(door,p,false); room.addDoors(new Door(room, "room1",temp));		break;
					case 12:							temp = new Tile(door,p,false); room.addDoors(new Door(room, "room2",temp));		break;
					case 13:							temp = new Tile(door,p,false); room.addDoors(new Door(room, "room4",temp));		break;
					case 99:							temp = new Tile(floor,p,true); room.setSpawnSpots(temp);  						break;

				}
				ts[i][j] = temp;
				}
			}
			System.out.println(scan.next()+"  Loading");
			ArrayList<Item> items = new ArrayList<Item>();
			for(int i =0; i <30; i++){
				for(int j = 0; j <30; j++){
					BufferedImage temp = null;
					Item tempItem =null;
					int tile = scan.nextInt();
					switch(tile) {
					case 0: tempItem = new Fruit(ts[i][j], "Banana");		temp = Banana;		break;
					case 1: tempItem = new Fruit(ts[i][j], "Apple");		temp = Apple;		break;
					case 2: tempItem = new Fruit(ts[i][j], "Mango");		temp = Mango;		break;
					case 3: tempItem = new Fruit(ts[i][j], "RedPot");		temp = RedPot;		break;
					case 4: tempItem = new Armour(ts[i][j], "ArmorHead");	temp = ArmorHead;	break;
					case 5: tempItem = new Armour(ts[i][j], "ArmorChest");	temp = ArmorChest;	break;
					case 6: tempItem = new Armour(ts[i][j], "ArmorLegs");	temp = ArmorLegs;	break;
					case 7: tempItem = new Key(ts[i][j], "Room2");		temp = KeyRoom2;	break;
					case 8: tempItem = new Key(ts[i][j], "Room3");		temp = KeyRoom3;	break;
					case 9: tempItem = new Key(ts[i][j], "Room4");		temp = KeyRoom4;	break;
					case 10: tempItem = new Key(ts[i][j],"bossroom");		temp = KeyRoom5;	break;
					}
					items.add(tempItem);
					ts[i][j].setPickUpImage(temp);
					ts[i][j].setItem(tempItem);

				}
			}
			System.out.println(scan.next()+"  Loading "); //Loading monsters
			ArrayList<Monster> monsters = new ArrayList<Monster>();
			for(int i =0; i <30; i++){
				for(int j = 0; j <30; j++){
					BufferedImage temp = null;
					int tile = scan.nextInt();
					switch(tile) {
					case 0:								temp = boss1;  	break; // TODO add new monster
					case 1:								temp = skelly; monsters.add(new Skeleton(ts[i][j])); break;

					}

					ts[i][j].setMonsterImage(temp);

				}
			}
			room.setMonsters(monsters);

		scan.close();

		} catch (IOException e) {
			System.out.println(e.toString());
		}

		return ts;
	}

	/**
	 * Takes all the players on the screen and sends to correct Draw method
	 * @param g2
	 * @param players
	 * @param clientPlayer
	 */
	public void drawSprite(Graphics2D g2, List<Player> players, Player clientPlayer) {
		for(Player player : players){
			if(player.equals(clientPlayer))
				drawSpriteClientPlayer(g2, player);
			else
				drawSpriteNonClientPlayer(g2, player,clientPlayer.getSprite());
		}
	}
	/**Draws the sprite with client offsets
	 * @param g2
	 * @param player
	 */
	public void drawSpriteClientPlayer(Graphics2D g2, Player player) {
			Sprite sprite = player.getSprite();
			g2.drawImage(getSpriteIso(sprite.getStep()%10, sprite.getFacing(), sprite.getName()),400,300,null);


	}
	/** Draws the sprite with non client offsetts
  	 * @param g2
	 * @param player
	 * @param c
	 */
	public void drawSpriteNonClientPlayer(Graphics2D g2, Player player,Sprite c) {

			Sprite sprite = player.getSprite();
			g2.drawImage(getSpriteIso(sprite.getStep()%10, sprite.getFacing(), sprite.getName()),sprite.getCurrentX()+400-c.getOffsetX(), sprite.getCurrentY()+300-c.getOffsetY(),null);
			//System.out.print((sprite.getCurrentX()+463-67-c.getOffsetX())+"  ;  "+( sprite.getCurrentY()+227+70-c.getOffsetY()));


	}

	/** Returns the image of the sprite according to step,direction and name
	 * @param x
	 * @param direction
	 * @param currentSpriteName
	 * @return
	 */
	public BufferedImage getSpriteIso(int x, String direction,String currentSpriteName) {
		BufferedImage spriteSheet = null;
		try {
			spriteSheet = ImageIO.read(new File("sprite/"+currentSpriteName+direction+".png"));
		} catch (IOException e) {
			System.out.println(e.toString());
		}

		return spriteSheet.getSubimage(x*(spriteSheet.getWidth()/10), 0, spriteSheet.getWidth()/10, spriteSheet.getHeight());
	}

	/** Rotates the tileset and returns the rotated tileset
	 * @param room
	 * @return
	 */
	public Tile[][] rotate(Room room) {
		Tile[][] TileSet = room.getTileSet();
		Tile[][] rotate = new Tile[30][30];
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				rotate[i][29-j] = TileSet[j][i];

			}
		}
			return rotate;
		}

}
