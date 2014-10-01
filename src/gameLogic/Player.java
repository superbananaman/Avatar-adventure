package gameLogic;

import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.text.Position;

import Renderer.Tile;

public class Player implements Character {

	private Tile tile;
	private String nation;
	private String name;
	private Room currentRoom;
	private Inventory inventory;
	private int maxHealth;
	private int currentHealth;
	private boolean isAlive = true;

	public Player() {

		name = chooseName();
		nation = chooseNation();
		inventory = new Inventory();

	}

	private String chooseName() {
		return JOptionPane.showInputDialog("What is your name?");
	}

	private String chooseNation() {
		String[] Nations = { "Fire", "Earth", "Water", "Air" };
		String nation = (String) JOptionPane.showInputDialog(null,
				"Which Nation do you choose?", "Birthplace",
				JOptionPane.QUESTION_MESSAGE, null, // Use// default// icon
				Nations, // Array of choices
				Nations[0]); // Initial choice
		return nation;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}

	public void draw(Graphics g) {

	}

	/**
	 *
	 * @param tile
	 */

	public void pickUp(Tile tile) {
		if (isAlive) {
			if (tile.hasItem()) { // has item returns true if it is occupied
									// with an item
				inventory.add(tile.getItem());
			}
			tile.removeItem();
		}
	}

	public void dropItem(Item i) {
		inventory.remove(i);
		tile.setItem(i);
	}

	/**
	 *
	 * @param h is the health/damage. damage will be negative, so will be subtracted
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

	public void attack() {
		if (isAlive) {

		}
	}

}
