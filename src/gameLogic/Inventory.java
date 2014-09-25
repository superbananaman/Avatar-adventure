package gameLogic;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Inventory {

	private List<Item> items = new ArrayList<Item>();

	public Inventory(){

	}

	public void draw(Graphics g){

	}

	public void add(Item i){
		items.add(i);
	}

	public void remove(Item i){
		items.remove(i);
	}

}
