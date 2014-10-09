package gameLogic;

import java.awt.Graphics;

import Renderer.Tile;

public class Fruit implements Item{

	private Tile tile;
	private String type;
	private int health;

	public Fruit(Tile pos, String Type){
		tile = pos;
		setType(Type);
		if(getType() == "Banana"){
			health = 200;
		}else if(getType() == "Apple"){
			health = 300;
		}else if(getType() == "Mango"){
			health = 400;
		}
	}


	public void use(Player player) {
		player.setcurrentHealth(health);

	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

}
