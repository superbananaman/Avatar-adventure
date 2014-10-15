package gameLogic;

import java.awt.Graphics;

import Renderer.Tile;


/**
 * @author cartyschri
 */

public class Fruit implements Item{

	private Tile tile;
	private String type;
	private int health;

	public Fruit(Tile pos, String Type){
		tile = pos;
		setType(Type);
		if(getType().equals("Banana")){
			health = 200;
		}else if(getType().equals("Apple")){
			health = 300;
		}else if(getType().equals("Mango")){
			health = 400;
		}
	}


	public void use(Player player) {
		player.updateCurrentHealth(health);


	}


	public String getType() {
		return type;
	}

	public void setTile(Tile t) {
		this.tile = t;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getName() {
		return type;

	}


	public Tile getTile() {
		// TODO Auto-generated method stub
		return tile;
	}

	public boolean equals(Item other){
		if(other instanceof Fruit){
			return type.equals(other.getName());
		}
		return false;
	}

}
