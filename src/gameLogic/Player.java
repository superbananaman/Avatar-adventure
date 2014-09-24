package gameLogic;

import javax.swing.JOptionPane;
import javax.swing.text.Position;

public class Player implements Character{

	private Position position;
	private String nation;
	
	
	public Player(){
		nation = chooseNation();
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
