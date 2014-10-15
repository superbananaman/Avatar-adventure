package gameLogic;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cartyschri
 */


public class Inventory implements Serializable{

	private List<Item> items = new ArrayList<Item>();
	public int selectedSpace = -1;


	public void add(Item i){
		items.add(i);
	}

	public void remove(Item i){
		items.remove(i);
	}

	public boolean has(Item i){
		for(Item item: items){
			if(i.equals(item)){
				return true;
			}
		}
		return false;
	}


	/**
	 *
	 * @param keyRoom the room that is trying to be opened
	 * @return true if the right key is in here
	 */
	public boolean hasKey(String keyRoom){
		for(Item i : items){
			if(i instanceof Key){
				if(((Key) i).getRoomName().equalsIgnoreCase(keyRoom)){
				return true;
				}
			}
		}
		return false;
	}
	public Item get(int num){
		if(items.size() < 0)return null;

		if(items.size() > num){
		return items.get(num);
		}
		return null;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getSelectedSpace() {
		return selectedSpace;
	}

	public void setSelectedSpace(int selectedSpace) {
		this.selectedSpace = selectedSpace;
	}

}
