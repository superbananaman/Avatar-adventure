package gameLogic;

import java.awt.Graphics;

import ClientServer.Slave;
import Renderer.Tile;

public class Skeleton implements Monster {

	private Tile tile;
	private String name;
	private int health;
	private boolean isAlive = true;

	public Skeleton(Tile tile) {
		setTile(tile);
		setName("Skeleton");

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
		health = numofPlayers * 400;
	}

	public int attack() {
		return 200; // to all players
	}

	public int bite() {
		return 150;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void takeDamage(int damage) {
		health = health - damage;
		if (health <= 0) {
			Die();
		}


	}

	public void Die() {
		isAlive = false;
		System.out.println("Monster died");
		Slave.sendMonster(tile.getLocation());

	}

}
