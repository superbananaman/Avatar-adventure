package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Key implements Item{

	private Tile tile;
	private Door door;

	public Key(Tile pos, Door door){
		tile = pos;
		setDoor(door);
	}


	public void use(Player player) {
		//return "Opens " + getDoor();

	}

	public Door getDoor() {
		return door;
	}

	public void setDoor(Door door) {
		this.door = door;
	}
}
