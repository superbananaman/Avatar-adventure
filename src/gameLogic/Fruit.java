package gameLogic;

import java.awt.Graphics;

import Renderer.Tile;

public class Fruit implements Item{

	private Tile tile;
	private String type;
	private int health;

	public Fruit(Tile pos, String Type){
		tile = pos;
		type = Type;
	}


	public void draw(Graphics g) {

	}

}
