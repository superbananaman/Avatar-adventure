package gameLogic;

import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.JOptionPane;
import javax.swing.text.Position;

import ClientServer.Slave;
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
		inventory = new Inventory();
		sprite = new Sprite("sprite",-60,70);
		maxHealth = 500;
		currentHealth = 500;


	}

	public String getUID(){
		return name;
	}

	public void setName(String n){
		this.name = n;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
	/**
	 *
	 * @param tile that the player is on
	 */

	public void pickUp(Tile tile) {
		 if (isAlive) {
		 if (tile.hasItem()) { // has item returns true if it is
			 System.out.println("Item picked up" + tile.getItem().getName());

		 inventory.add(tile.getItem());
		 Slave.sendPickupItem(tile.getLocation());
		 }


		 }
	}

	/**
	 *
	 * @param i the item that is being dropped
	 *
	 * @param tile that is being dropped on to
	 */

	public void dropItem(Item i ,Tile tile) {
		if(i != null){
			if(i instanceof Armour){
				if(((Armour) i).isEquipped()){
					((Armour) i).unequip(this);
				}
			}
			Slave.sendDropItem(i.getName() ,tile.getLocation());
			inventory.remove(i);
			System.out.println("Removed item");
		}
	}

	/**
	 *
	 * @param h
	 *            is the health/damage. damage will be negative, so will be
	 *            subtracted
	 */

	public void updateCurrentHealth(int h) {
		if ((currentHealth + h) < maxHealth) {
			currentHealth += h;
		} else if ((currentHealth + h) > maxHealth) {
			currentHealth = maxHealth;
		} else if ((currentHealth + h) < 0) {
			currentHealth = 0;
			Die();
		}

	}

	public void Die() {
		setAlive(false);
		System.out.println("Player Died");

	}

	public void addmaxHealth(int h) {
			maxHealth = maxHealth + h;
	}

	public int attack1() {
		if (isAlive) {
			return 350;
		}
		return 0;
	}

	public int attack2() {
		if (isAlive) {
			return 1000;
		}
		return 0;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void setSprite(Sprite s){
		this.sprite = s;
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
		if (p.getUID().equals(name)){
			return true;
		}
		return false;

	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth += currentHealth;
	}
}