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
		if(type == "average"){ // types of potions
			health = 200;
		}else if(type == "mega"){
			health = 500;
		}else if(type == "ultimate"){
			health = 1000;
		}
	}

	public void draw(Graphics g) {


	}

	public void use(Player p){
		p.setcurrentHealth(health);
	}


}
