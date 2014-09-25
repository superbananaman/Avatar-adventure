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

	public void move(){

	}

}
