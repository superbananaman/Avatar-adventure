package gameLogic;

import java.awt.Graphics;

import javax.swing.JDialog;

import ClientServer.Slave;
import Renderer.Tile;

public class Boss implements Monster {

	private Tile tile;
	private int health;
	private String name;
	private boolean isAlive = true;

	public Boss(Tile tile) {
		setTile(tile);
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
		health = numofPlayers * 1500;
	}

	/**
	 *
	 * @return an attack that hits all players
	 */
	public int attack() {
		return 400;
	}

	/**
	 *
	 * @return one hits players
	 */
	public int ulti() {
		return 9001;
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
		Slave.sendMonster(tile.getLocation());

	}

}
