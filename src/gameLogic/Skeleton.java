package gameLogic;

import Renderer.Tile;

public class Skeleton {

	private Tile tile;
	private int health;


	public Skeleton(Tile tile, int players){
		setTile(tile);
		health = players*400;

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


	public void setHealth(int health) {
		this.health = health;
	}

	public int throwBones(){
		return 100; // to all players
	}

	public int bite(){
		return 150;
	}

}

