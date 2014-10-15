package gameLogic;

import gui.ClientFrame;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.*;

import Renderer.Tile;

public class Room implements Serializable{

	//private List<Player> players = new ArrayList<Player>(); // the current players in this room
	private Tile spawnSpot; //spawn spots in a room
	private List<Monster> monsters = new ArrayList<Monster>();	// the current alive monsters in this room
	private List<Door> doors = new ArrayList<Door>();
	private List<Item> items = new ArrayList<Item>();
	private Point offSet;

	private Tile[][] TileSet; // the grid setup
	private String roomName;


	public Room(String roomname){
	roomName = roomname;


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

	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Tile getSpawnSpots() {
		return spawnSpot;
	}
	public void setSpawnSpots(Tile spawnspots) {
		spawnSpot= spawnspots;
	}

	public List<Door> getDoors() {
		return doors;
	}

	public void addDoors(Door door) {
		doors.add(door);
	}

	public Point getOffSet() {
		return offSet;
	}

	public void setOffSet(Point offSet) {
		this.offSet = offSet;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
