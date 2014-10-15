package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Armour implements Item{

	private Tile tile;
	private int health;
	private String type;
	private boolean equipped = false;

	public Armour(Tile pos, String type){
		setTile(pos);
		setType(type);
		health = 700;

	}
	/**
	 *
	 * @param p - the player that the armour is being unequipped from
	 */

	public void unequip(Player p){
		if(equipped){
		p.addmaxHealth((-health));
		p.setCurrentHealth((-health));
		setEquipped(false);
		}
	}

	/**
	 * equips the armour
	 */

	public void use(Player p){  //equipping armour gives health
		if(!equipped){
		p.addmaxHealth(health);
		p.setCurrentHealth(health);
		setEquipped(true);
		}
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

	public boolean isEquipped() {
		return equipped;
	}

	public void setEquipped(boolean equipped) {
		this.equipped = equipped;
	}

}
