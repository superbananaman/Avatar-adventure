package gameLogic;

import java.awt.Graphics;

import Renderer.Tile;

public class Boss implements Monster{

	private Tile tile;
	private int health;
	private String name;


	public Boss(Tile tile, int players){
		setTile(tile);
		health = players*5000;
		setName("Boss");
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

	/**
	 *
	 * @return an attack that hits all players
	 */
	public int attack(){
		return 1000;
	}

	/**
	 *
	 * @return one hits players
	 */
	public int ulti(){
		return 9001;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


}
