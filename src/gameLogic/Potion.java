package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Potion implements Item {

	private Tile tile;
	private String type;
	private int health;

	public Potion(Tile pos, String Type){
		tile = pos;
		type = Type;
		health = 500;

	}


	/**
	 * heals the player
	 */
	public void use(Player p){
		p.updateCurrentHealth(health);
	}

	public String getName() {
		return type;
	}

	public Tile getTile() {
		return tile;
	}
	public void setTile(Tile t) {
		this.tile = t;
	}

	public boolean equals(Item other){
		if(other instanceof Potion){
			return type.equals(other.getName());
		}
		return false;
	}
}
