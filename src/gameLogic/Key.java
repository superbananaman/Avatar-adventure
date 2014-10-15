package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Key implements Item{

	private Tile tile;
	private String roomName;

	public Key(Tile pos, String name){
		tile = pos;
		roomName = name;
	}

	public String getRoomName() {
		return roomName;
	}


	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	public String getName() {
		return roomName;
	}


	public Tile getTile() {
		return tile;
	}


	public void setTile(Tile t) {
		this.tile = t;
	}

	public boolean equals(Item other){
		if(other instanceof Key){
			return roomName.equals(other.getName());
		}
		return false;
	}

	public void use(Player player) {
		// TODO Auto-generated method stub

	}
}
