package gameLogic;


import java.io.Serializable;
import java.util.*;



public class Game implements Serializable{

	private List<Room> rooms = new ArrayList<Room>();
	private List<Player> totalPlayers = new ArrayList<Player>();

	public Game(){
		setUp();
	}

	public void setUp(){

		//addRoom();

	}

/*	public void addRoom() {
		rooms.add(new Room(800, 600));
		rooms.get(rooms.size()-1).updatePlayers(players);
	}*/

	public void addPlayers() {
		//Player player = new Player();
		//totalPlayers.add(player);

	}

}
