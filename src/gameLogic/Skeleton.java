package gameLogic;

import java.awt.Graphics;

import Renderer.Tile;

public class Skeleton implements Monster{

	private Tile tile;
	private int health;


	public Skeleton(Tile tile){
		setTile(tile);


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
		health = numofPlayers*400;
	}

	public int throwBones(){
		return 100; // to all players
	}

	public int bite(){
		return 150;
	}


	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

}

