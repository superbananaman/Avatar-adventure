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


	public void use(Player player) {
		//return "Opens " + getDoor();

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

}
