package gameLogic;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable{

	private List<Item> items = new ArrayList<Item>();


	public Inventory(){

	}

	public void draw(Graphics g){

	}

	public void add(Item i){
		if(getItems().size()<10){


		getItems().add(i);
		}
	}

	public void remove(Item i){
		getItems().remove(i);
	}

	public boolean has(Item i){
		for(Item item: getItems()){
			if(i == item){
				return true;
			}
		}
		return false;
	}

	public boolean hasKey(String keyRoom){
		for(Item i : getItems()){
			if(i instanceof Key){
				if(((Key) i).getRoomName().equalsIgnoreCase(keyRoom)){
				return true;
				}
			}
		}
		return false;
	}
	public Item get(int num){
		return getItems().get(num);
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
