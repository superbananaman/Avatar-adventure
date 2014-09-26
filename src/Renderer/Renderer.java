package Renderer;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import gameLogic.Room;

public class Renderer {
//BufferedImages for ISO tiles
	private BufferedImage empty= getIso(64*2,0);
	private BufferedImage wall= getIso(0,0);
	private BufferedImage floor = getIso(0,64*5);
	private BufferedImage Barrel = getIso(64*3,64*5);
	private BufferedImage rug = getIso(64*9,64*5);
	private BufferedImage door = getIso(64*9,65*3);
	
			
			
	public void drawLevel(Graphics g, Room r) {
		Tile[][] ts = r.getTileSet(); if(ts ==null) System.out.println("ERROR ts = null");
		for (int i = 0; i < r.getWidth()-1; i++) {
			for (int j = r.getHeight()-1; j >= 0; j--) {
				// Tile tile = t;

				int x;
				int y;
				placetile(ts[i][j], new Point(x = (j * 64 / 2) + (i * 64 / 2),
						y = (i * 32 / 2) - (j * 32 / 2)), g);

			}
		}

	}

	private void placetile(Tile tile, Point isoPoint, Graphics g) {if(tile != null)
		g.drawImage(tile.getBackGroundImage(), isoPoint.x, isoPoint.y + 500, null);
	else
		System.out.println("DFgdfgDFG");

	}

	public Point isoTo2D(Point pt) {
		Point tempPt = new Point(0, 0);
		tempPt.x = (2 * pt.y + pt.x) / 2;
		tempPt.y = (2 * pt.y - pt.x) / 2;
		return (tempPt);
	}

	public Point twoDToIso(Point pt) {
		Point tempPt = new Point(0, 0);
		tempPt.x = pt.x - pt.y;
		tempPt.y = (pt.x + pt.y) / 2;
		// System.out.println("Converted "+pt.x+" "+pt.y
		// +" to "+tempPt.x+" "+tempPt.y);
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
	
	public Tile[][] parseTileSet(String roomName){
		Tile[][] ts = new Tile[30][30];
		try {
			FileReader roomFile = new FileReader(roomName+".txt");
			Scanner scan = new Scanner(roomFile);
			
			
			System.out.println(scan.next()+"  Loading");
			for(int i =0; i <30; i++){
				for(int j = 0; j <30; j++){
					Tile temp = null;
				int tile = scan.nextInt();
				switch(tile) {					
					case 0:								temp = new Tile(empty); 		break;
					case 1: case 2: case 3:  			temp = new Tile(wall);			break;
					case 4:							 	temp = new Tile(floor);			break;
					case 5: 						 	temp = new Tile(Barrel);		break;
					case 6: case 7: case 8: 		 	temp = new Tile(rug);			break;
					case 9: case 10: case 11: 
					case 12: case 13: case 14:			temp = new Tile(door);			break;					
				}
				ts[i][j] = temp;
				}
			}
			System.out.println(scan.next()+"  Loading");
			for(int i =0; i <30; i++){
				for(int j = 0; j <30; j++){

				scan.nextInt();

				}
			}
			System.out.println(scan.next()+"  Loading");
			for(int i =0; i <30; i++){
				for(int j = 0; j <29; j++){
					Tile temp = null;
				scan.nextInt();
				}
			}
			
		scan.close();
			
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		
		return ts;
	}
}
