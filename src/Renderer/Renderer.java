package Renderer;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameLogic.Room;

public class Renderer {

	public void drawLevel(Graphics g, Room r) {
		Tile[][] tileSet = r.getTileSet();
		Tile t = new Tile(null);
		/*
		 * for (int i = 0; i<r.getWidth(); i++){ for (int j=0; j<r.getHeight();
		 * j++){ int x = j * 32; int y = i * 32; Tile tile = t; //Tile tile =
		 * tileSet[i][j]; placetile(tile, twoDToIso(new Point(x, y)),g);
		 */
		t.setImg(getIso(0, 64 * 5));
		for (int i = 0; i < r.getWidth(); i++) {
			for (int j = r.getHeight(); j >= 0; j--) {
				// Tile tile = t;

				int x;
				int y;
				placetile(t, new Point(x = (j * 64 / 2) + (i * 64 / 2),
						y = (i * 32 / 2) - (j * 32 / 2)), g);

			}
		}
		Tile t1 = new Tile(null);
		t1.setImg(getIso(0,0));
		for (int i = 0; i < r.getWidth(); i++) {
			for (int j = r.getHeight(); j >= 0; j--) {
				// Tile tile = t;

				int x;
				int y;
				if(i==0||j==0||i==r.getHeight()-1||j==r.getWidth())
				placetile(t1, new Point(x = (j * 64 / 2) + (i * 64 / 2),
						y = (i * 32 / 2) - (j * 32 / 2)), g);

			}
		}
		

	}

	private void placetile(Tile tile, Point isoPoint, Graphics g) {
		g.drawImage(tile.getImg(), isoPoint.x, isoPoint.y + 200, null);

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

}
