package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Armour implements Item{

	private Tile tile;
	private int health;
	private String type;

	public Armour(Tile pos, String type){
		setTile(pos);
		setType(type);

	}

	public void use(Player p){  //equipping armour gives health
		p.setmaxHealth(health);
	}


	public Tile getTile() {
		return tile;
	}


	public void setTile(Tile tile) {
		this.tile = tile;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return type;
	}

	public boolean equals(Item other){
		if(other instanceof Armour){
			return type.equals(other.getName());
		}
		return false;
	}

}
