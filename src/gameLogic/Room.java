package gameLogic;

import gui.ClientFrame;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.*;

import Renderer.Tile;

public class Room implements Serializable{

	private int height;
	private int width;

	//private List<Player> players = new ArrayList<Player>(); // the current players in this room
	private List<Tile> spawnSpots = new ArrayList<Tile>(); //spawn spots in a room
	private List<Monster> monsters = new ArrayList<Monster>();	// the current alive monsters in this room
	private List<Item> items = new ArrayList<Item>(); // the items on the floor of this room
	private List<Object> walls = new ArrayList<Object>(); // the walls and doors in the room
	private Tile[][] TileSet; // the grid setup
	private String roomName;


	public Room(String roomname){
//	setHeight(Height);
//	setWidth(Width);
	roomName = roomname;
	setUp();

	}
	public void setUp() { //needs to have some parsing or something


	}



//	public void draw(Graphics g){
//		for(Player p: players){
//			p.draw(g);
//		}
//		for(Item i: getItems()){
//			i.draw(g);
//		}
//		for(Monster m: getMonsters()){
//			m.draw(g);
//		}
//
//	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the tileSet
	 */
	public Tile[][] getTileSet() {
		return TileSet;
	}
	/**
	 * @param the tileSet to set
	 */
	public void setTileSet(Tile[][] tileSet) {
		TileSet = tileSet;
	}
	public List<Monster> getMonsters() {
		return monsters;
	}
	public void setMonsters(List<Monster> monsters) {
		this.monsters = monsters;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public List<Tile> getSpawnSpots() {
		return spawnSpots;
	}
	public void setSpawnSpots(Tile spawnspots) {
		spawnSpots.add(spawnspots);
	}

}
