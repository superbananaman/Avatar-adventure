package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Door {

	private Tile tile;
	private Room currentRoom;
	private String nextRoom;
	private Key Key;


	public Door(Room Room, String nextroom){
		setRoom(Room);
		setNextRoom(nextroom);
		setTile(getTile());
	}

	public boolean use(Player player){
		if(player.getInventory().has(getKey()) == true){
			return true;
		}
	return false;
	}


	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public Room getRoom() {
		return currentRoom;
	}

	public void setRoom(Room room) {
		this.currentRoom = room;
	}

	public String getNextRoom() {
		return nextRoom;
	}

	public void setNextRoom(String nextRoom) {
		this.nextRoom = nextRoom;
	}

	public Key getKey() {
		return Key;
	}

	public void setKey(Key key) {
		Key = key;
	}

}
