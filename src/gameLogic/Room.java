package gameLogic;

import java.awt.Graphics;
import java.util.*;

import Renderer.Tile;

public class Room {

	private int height;
	private int width;

	private List<Player> players = new ArrayList<Player>(); // the current players in this room
	private List<Monster> monsters = new ArrayList<Monster>();	// the current alive monsters in this room
	private List<Item> items = new ArrayList<Item>(); // the items on the floor of this room
	private List<Object> walls = new ArrayList<Object>(); // the walls and doors in the room
	private Tile[][] TileSet; // the grid setup

	public Room(int Height, int Width){
	setHeight(Height);
	setWidth(Width);
	setUp();

	}
	public void setUp() { //needs to have some parsing or something


	}
	public void updatePlayers(List<Player> p){ //gets called when players enter the room
		players.addAll(p);
	}


	public void draw(Graphics g){
		for(Player p: players){
			p.draw(g);
		}
		for(Item i: items){
			i.draw(g);
		}
		for(Monster m: monsters){
			m.draw(g);
		}
		for(Object o: walls){
			o.draw(g);
		}
	}
	public int getHeight() {
		return height;
	}
	private void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	private void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the tileSet
	 */
	public Tile[][] getTileSet() {
		return TileSet;
	}
	/**
	 * @param tileSet the tileSet to set
	 */
	public void setTileSet(Tile[][] tileSet) {
		TileSet = tileSet;
	}
}
