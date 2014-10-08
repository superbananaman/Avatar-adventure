package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Door {

	private Tile tile;
	private Room room;
	private Room nextRoom;
	private Key Key;


	public Door(Room Room, Room nextroom){
		setRoom(Room);
		setNextRoom(nextroom);
		setTile(getTile());
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub

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
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Room getNextRoom() {
		return nextRoom;
	}

	public void setNextRoom(Room nextRoom) {
		this.nextRoom = nextRoom;
	}

	public Key getKey() {
		return Key;
	}

	public void setKey(Key key) {
		Key = key;
	}

}
