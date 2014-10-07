package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Armour implements Item{

	private Tile tile;
	private int health;
	private String type;
	private String material;

	public Armour(Tile pos, String material, String type){
		setTile(pos);
		setType(type);
		setMaterial(material);
		if(material == "Iron"){
			health = 200;
		}else if(material == "Steel"){
			health = 600;
		}else if(material == "Hero"){
			health = 1200;
		}
		// if statements about what type of armour it is
	}


	public void draw(Graphics g) {
		// TODO Auto-generated method stub

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


	public String getMaterial() {
		return material;
	}


	public void setMaterial(String material) {
		this.material = material;
	}


}
