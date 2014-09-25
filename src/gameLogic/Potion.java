package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Potion implements Item {

	private Tile tile;
	private String type;

	public Potion(Tile pos, String Type){
		tile = pos;
		type = Type;
	}

	public void draw(Graphics g) {


	}

}
