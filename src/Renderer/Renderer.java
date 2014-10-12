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
	private BufferedImage empty= getIso(64,64*2);
	private BufferedImage wall= getIso(0,0);
	private BufferedImage floor = getIso(0,64*5);
	private BufferedImage Barrel = getIso(64*9,64*5);
	private BufferedImage rug = getIso(64*4,64*0);
	private BufferedImage door = getIso(64*1,64*1);
	private BufferedImage crate = getIso(64*3,64*5);
	private BufferedImage skelly = getIso(64*4,64*1);
	private BufferedImage boss1 = getIso(64*5,64*1);
	private BufferedImage Banana = getIso(64*5,64*1);
	private BufferedImage Apple = getIso(64*5,64*1);
	private BufferedImage Mango = getIso(64*5,64*1);
	private BufferedImage RedPot = getIso(64*5,64*1);
	private BufferedImage ArmorHead = getIso(64*5,64*1);
	private BufferedImage ArmorChest = getIso(64*5,64*1);
	private BufferedImage ArmorLegs = getIso(64*5,64*1);
	private BufferedImage KeyRoom2 = getIso(64*5,64*1);
	private BufferedImage KeyRoom3 = getIso(64*5,64*1);
	private BufferedImage KeyRoom4 = getIso(64*5,64*1);
	private BufferedImage KeyRoom5 = getIso(64*5,64*1);
	
	

	public void drawLevel(Graphics g, Room r) {
		Tile[][] ts = r.getTileSet();
		for (int i = 0; i < 30; i++) {
			for (int j = 0;j <30; j++) {
				placetile(ts[i][j], twoDToIso(new Point(j,i)),g);
			}
		}
	}

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

	public Point isoTo2D(Point pt) {
		Point tempPt = new Point(0, 0);
		tempPt.x = (pt.x / 32 + pt.y / 16)  /2;
		tempPt.y = (pt.y / 16 -(pt.x / 32)) /2;
		return (tempPt);
	}
	public Point twoDToIso(Point pt) {
		Point tempPt = new Point(0, 0);
		tempPt.x = ((pt.x - pt.y)*32);
		tempPt.y = ((pt.x + pt.y)*16);
		return (tempPt);
	}

	public BufferedImage getIso(int x, int y) {
		BufferedImage tilesheet = null;
		try {
			tilesheet = ImageIO.read(new File("iso.png"));
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return tilesheet.getSubimage(x, y, 64, 64);
	}

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
				Point p = new Point(i,j);
				switch(tile) {
					case 0:								temp = new Tile(empty,p); 		break;
					case 1: case 2: case 3:  			temp = new Tile(wall,p); temp.setWalkable(false); 	break;
					case 4:							 	temp = new Tile(floor,p);			break;
					case 5: 						 	temp = new Tile(Barrel,p);		break;
					case 6:								temp = new Tile(rug,p);			break;
					case 7: case 8:						temp = new Tile(crate,p);			break;
					case 9: case 10: case 11:
					case 12: case 13: case 14:			temp = new Tile(door,p); room.addDoors(new Door(room, ""));	temp.setWalkable(false);		break;
					case 99:							temp = new Tile(floor,p); room.setSpawnSpots(temp);  break;

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
					case 10: tempItem = new Key(ts[i][j],"Room5");		temp = KeyRoom5;	break;
					}
					items.add(tempItem);
					ts[i][j].setPickUpImage(temp);

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
					ts[i][j].setWalkable(false);

				}
			}
			room.setMonsters(monsters);

		scan.close();

		} catch (IOException e) {
			System.out.println(e.toString());
		}

		return ts;
	}

	public void drawSprite(Graphics2D g2, List<Player> players, Player clientPlayer) {
		for(Player player : players){
			if(player.equals(clientPlayer))
				drawSpriteClientPlayer(g2, player);			
			else
				drawSpriteNonClientPlayer(g2, player,clientPlayer.getSprite());		
		}
	}
	public void drawSpriteClientPlayer(Graphics2D g2, Player player) {		
			Sprite sprite = player.getSprite();
			g2.drawImage(getSpriteIso(sprite.getStep()%10, sprite.getFacing(), sprite.getName()),400,300,null);
			
		
	}
	public void drawSpriteNonClientPlayer(Graphics2D g2, Player player,Sprite c) {
		
			Sprite sprite = player.getSprite();
			g2.drawImage(getSpriteIso(sprite.getStep()%10, sprite.getFacing(), sprite.getName()),sprite.getCurrentX()+463-c.getOffsetX(), sprite.getCurrentY()+227-c.getOffsetY(),null);
			
		
	}

	public BufferedImage getSpriteIso(int x, String direction,String currentSpriteName) {
		BufferedImage spriteSheet = null;
		try {
			spriteSheet = ImageIO.read(new File("sprite/"+currentSpriteName+direction+".png"));
		} catch (IOException e) {
			System.out.println(e.toString());
		}

		return spriteSheet.getSubimage(x*(spriteSheet.getWidth()/10), 0, spriteSheet.getWidth()/10, spriteSheet.getHeight());
	}

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
