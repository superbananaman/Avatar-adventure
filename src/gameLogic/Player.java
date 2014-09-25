package gameLogic;

import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.text.Position;

import Renderer.Tile;

public class Player implements Character{

	private Tile tile;
	private String nation;
	private String name;
	private Room currentRoom;
	private Inventory inventory;
	private int health;


	public Player(){

		name = chooseName();
		nation = chooseNation();
		inventory = new Inventory();

	}


	private String chooseName() {
		 return JOptionPane.showInputDialog("What is your name?");
	}


	private String chooseNation() {
		String[] Nations = {"Fire", "Earth", "Water", "Air" };
		String nation = (String) JOptionPane.showInputDialog(
				null, "Which Nation do you choose?",
				"Birthplace", JOptionPane.QUESTION_MESSAGE,
				null, // Use// default// icon
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

	public void draw(Graphics g){

	}

	public void move(String direction){
		if(direction == "R"){

		}else if(direction == "L"){

		}else if(direction == "U"){

		}else if(direction == "D"){

		}
	}


	public void setHealth(int h) { //adds the health onto current health. taking damage will be adding negative health
		health = health + h;

	}

}
