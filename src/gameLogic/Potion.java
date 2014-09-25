package gameLogic;

import java.awt.Graphics;

import javax.swing.text.Position;

public class Potion implements Item {

	private Position position;
	private String type;

	public Potion(Position pos, String Type){
		position = pos;
		type = Type;
	}

	public void draw(Graphics g) {


	}

}
