package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

import Renderer.Tile;

public class Armour implements Item{

	private Tile tile;
	private int health;
	private String type;

	public Armour(Tile pos, String Type){
		tile = pos;
		type = Type;
		// if statements about what type of armour it is
	}


	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	public void equip(Player p){  //equipping armour gives health
		p.setHealth(health);
	}
}
