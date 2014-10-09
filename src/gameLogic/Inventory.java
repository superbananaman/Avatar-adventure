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
		if(items.size()<10){


		items.add(i);
		}
	}

	public void remove(Item i){
		items.remove(i);
	}

	public boolean has(Item i){
		for(Item item: items){
			if(i == item){
				return true;
			}
		}
		return false;
	}
	public Item get(int num){
		return items.get(num);
	}

}
