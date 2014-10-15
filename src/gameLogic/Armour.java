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
	public void unequip(Player p){
		p.addmaxHealth((-health));
		setEquipped(false);
	}

	public void use(Player p){  //equipping armour gives health
		if(!equipped){
			System.out.println("equipped");
			System.out.println(p.getMaxHealth());
		p.addmaxHealth(health);
		System.out.println(p.getMaxHealth());
		//p.setCurrentHealth(health);
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
