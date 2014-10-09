package gameLogic;

import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.JOptionPane;
import javax.swing.text.Position;

import Renderer.Sprite;
import Renderer.Tile;

public class Player implements Character, Serializable {

	private Sprite sprite;
	private String nation;
	private String name; // does nothing right now
	private Room currentRoom;
	private Inventory inventory;
	private int maxHealth;
	private int currentHealth;
	private boolean isAlive = true;

	public Player(String name) {
		this.name = name;
		setInventory(new Inventory());
		sprite = new Sprite("sprite", 0, 0);
		setmaxHealth(500);
		setcurrentHealth(500);

	}

	public String getUID(){
		return name;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
	/**
	 *
	 * @param tile
	 */

	public void pickUp(Tile tile) {
		 if (isAlive) {
		 if (sprite.getTile().hasItem()) { // has item returns true if it is

		 inventory.add(sprite.getTile().getItem());
		 }
		 sprite.getTile().removeItem();
		 }
	}

	public void dropItem(Item i) {
		getInventory().remove(i);
		sprite.getTile().setItem(i);
	}

	/**
	 *
	 * @param h
	 *            is the health/damage. damage will be negative, so will be
	 *            subtracted
	 */

	public void setcurrentHealth(int h) {
		if ((currentHealth + h) < maxHealth) {
			currentHealth = currentHealth + h;
		} else if ((currentHealth + h) > maxHealth) {
			currentHealth = maxHealth;
		} else if ((currentHealth + h) < 0) {
			currentHealth = 0;
			Die();
		}

	}

	public void Die() {
		isAlive = false;

	}

	public void setmaxHealth(int h) {
		if (isAlive) {
			maxHealth = maxHealth + h;
		}
	}

	public int attack1() {
		if (isAlive) {
			return 200;
		}
		return 0;
	}

	public int attack2() {
		if (isAlive) {
			return 350;
		}
		return 0;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Player)){
			return false;
		}
		Player p = (Player) obj;
		if (p.getUID().equals(getUID())){
			return true;
		}
		return false;

	}
}