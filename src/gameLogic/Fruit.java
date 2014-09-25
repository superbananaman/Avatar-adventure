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
		if(type == "Banana"){
			health = 200;
		}else if(type == "Apple"){
			health = 300;
		}else if(type == "Mango"){
			health = 400;
		}
	}


	public void draw(Graphics g) {

	}

}
