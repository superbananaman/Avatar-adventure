package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Door implements Object {

	private Tile tile;
	private Room room;
	private Room nextRoom;

	public Door(Room Room, Room nextroom){
		room = Room;
		nextRoom = nextroom;
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

}
