package gameLogic;

import javax.swing.JOptionPane;
import javax.swing.text.Position;

public class Player implements Character{

	private Position position;
	private String nation;
	private String name;
	
	
	public Player(){
		name = chooseName();
		nation = chooseNation();
		
		
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
	
}
