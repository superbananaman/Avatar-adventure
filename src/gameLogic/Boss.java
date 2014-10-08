package gameLogic;

import java.awt.Graphics;

import Renderer.Tile;

public class Boss implements Monster{

	private Tile tile;
	private int health;


	public Boss(Tile tile, int players){
		setTile(tile);
		health = players*5000;
	}


	public Tile getTile() {
		return tile;
	}


	public void setTile(Tile tile) {
		this.tile = tile;
	}


	public int getHealth() {
		return health;
	}


	public void setHealth(int numofPlayers) {
		health = numofPlayers*5000;
	}


	public int attack(){
		return 1000; // attacks all players
	}
	public int ulti(){
		return 9001;
	}


	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

}
