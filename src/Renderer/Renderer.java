package Renderer;

import java.awt.Graphics;
import java.awt.Point;

import gameLogic.Room;

public class Renderer {

	
	
	public void drawLevel(Graphics G, Room r){
		Tile[][] tileSet = r.getTileSet();
	
		for (int i; i>r.getWidth-1; i++){
			 for (int j; j>r.getHeight-1; j++){
			  int x = j * 64;
			  int y = i * 64;
			  Tile tile = tileSet[i][j];
			  placetile(tile, twoDToIso(new Point(x, y)));
			 }
		}
	}

	
	
	private void placetile(Tile tile, Point twoDToIso) {
		// TODO Auto-generated method stub
		
	}



	public Point isoTo2D(Point pt){
		  Point tempPt = new Point(0, 0);
		  tempPt.x = (2 * pt.y + pt.x) / 2;
		  tempPt.y = (2 * pt.y - pt.x) / 2;
		  return(tempPt);
		}

	public Point twoDToIso(Point pt){
		  Point tempPt = new Point(0, 0);
		  tempPt.x = pt.x - pt.y;
		  tempPt.y = (pt.x + pt.y) / 2;
		  return(tempPt);
		}
}
