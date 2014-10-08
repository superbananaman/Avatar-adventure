package gameLogic;


import java.io.Serializable;
import java.util.*;



public class Game implements Serializable{

	private List<Room> rooms = new ArrayList<Room>();
	private List<Player> totalPlayers = new ArrayList<Player>();

	public Game(List<Player> players){
		totalPlayers = players;
		setUp();
	}

	public void setUp(){
		setMonsterHealth(totalPlayers.size());
		addRoom();

	}

public void setMonsterHealth(int size) {
		for(Room r: rooms){
			for(Monster m: r.getMonsters()){
				m.setHealth(size);
			}
		}

	}

	public void addRoom() {
		rooms.add(new Room("Starting"));
		rooms.add(new Room("Room1"));
		rooms.add(new Room("Room2"));
		rooms.add(new Room("Room3"));
		rooms.add(new Room("Bossroom"));
	}


}
