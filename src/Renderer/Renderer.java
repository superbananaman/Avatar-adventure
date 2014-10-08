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
import java.util.Scanner;

import javax.imageio.ImageIO;

import gameLogic.Item;
import gameLogic.Monster;
import gameLogic.Room;

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

	public void drawLevel(Graphics g, Room r) {
		Tile[][] ts = r.getTileSet();
		for (int i = 0; i < r.getWidth(); i++) {
			for (int j = 0;j <r.getHeight(); j++) {
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

	public Tile[][] parseTileSet(Room roomName){ System.out.println(roomName.getRoomName());
		Tile[][] ts = new Tile[30][30];
		try {
			FileReader roomFile = new FileReader("maps/"+roomName.getRoomName()+".txt");
			Scanner scan = new Scanner(roomFile);


			System.out.println(scan.next()+"  Loading");
			for(int i =0; i <30; i++){
				for(int j = 0; j <30; j++){
					Tile temp = null;
				int tile = scan.nextInt();
				switch(tile) {
					case 0:								temp = new Tile(empty); 		break;
					case 1: case 2: case 3:  			temp = new Tile(wall); temp.setWalkable(false); 	break;
					case 4:							 	temp = new Tile(floor);			break;
					case 5: 						 	temp = new Tile(Barrel);		break;
					case 6:								temp = new Tile(rug);			break;
					case 7: case 8:						temp = new Tile(crate);			break;
					case 9: case 10: case 11:
					case 12: case 13: case 14:			temp = new Tile(door);	temp.setWalkable(false);		break;

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
					//case 0:								temp = empty; 		break;
					//case 1:								temp = empty;		break;

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
					case 0:								temp = skelly; 		break;
					case 1:								temp = skelly;		break;

					}
					//monsters.add(new Monster(ts[i][j]));                                 LIAM FIX THIS PLEASE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					ts[i][j].setMonsterImage(temp);

				}
			}
			roomName.setMonsters(monsters);
			roomName.setItems(items);

		scan.close();

		} catch (IOException e) {
			System.out.println(e.toString());
		}

		return ts;
	}

	public void drawSprite(Graphics2D g2, Sprite testSprite) {
		g2.drawImage(getSpriteIso(testSprite.getStep() %10, testSprite.getFacing(), testSprite.getName()),400, 300,null);

	}
	public BufferedImage getSpriteIso(int x, String direction,String currentSpriteName) {
		BufferedImage spriteSheet = null;
		currentSpriteName = currentSpriteName.toLowerCase();
		try {
			spriteSheet = ImageIO.read(new File("sprite/"+currentSpriteName+direction+".png"));
		} catch (IOException e) {
			System.out.println(e.toString());
		}

		return spriteSheet.getSubimage(x*(spriteSheet.getWidth()/10), 0, spriteSheet.getWidth()/10, spriteSheet.getHeight());
	}

	public void rotate(Room room) {
		Tile[][] TileSet = room.getTileSet();
		Tile[][] rotate = new Tile[30][30];
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				rotate[i][29 - j] = TileSet[j][i];

			}
		}
		room.setTileSet(rotate);
	}

}
