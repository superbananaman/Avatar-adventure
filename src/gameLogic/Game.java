package gameLogic;


import gui.ClientFrame;

import java.io.Serializable;
import java.util.*;

import Renderer.RenderWindow;
import Renderer.Renderer;



public class Game implements Serializable{

	private List<Room> rooms = new ArrayList<Room>();
	private List<Player> totalPlayers = new ArrayList<Player>();
	private ClientFrame clientframe;
	private Renderer renderer = new Renderer();

	public Game(String UID ,List<Player> players){
		setUp();
		setTotalPlayers(players);
		clientframe = new ClientFrame(UID, players, this);



	}

	public void setUp(){
		addRoom();
		setMonsterHealth(getTotalPlayers().size());


	}

public void setMonsterHealth(int size) {
		for(Room r: rooms){
			for(Monster m: r.getMonsters()){
				m.setHealth(size);
			}
		}

	}

	public void addRoom() {
		System.out.println("adding rooms");
		rooms.add(new Room("startroom"));
		rooms.add(new Room("room1"));
		rooms.add(new Room("room2"));
		rooms.add(new Room("room3"));
		rooms.add(new Room("bossroom"));
		for(Room r: rooms){

			r.setTileSet(renderer.parseTileSet(r));
			System.out.println(r.getRoomName()+" ADDED");
		}
	}

	public List<Player> getTotalPlayers() {
		return totalPlayers;
	}

	public void setTotalPlayers(List<Player> totalPlayers) {
		this.totalPlayers = totalPlayers;
	}

	public Room getRoom(String name){
		for(Room r: rooms){
			System.out.println(r.getRoomName());
			if(r.getRoomName().equalsIgnoreCase(name)){
				return r;
			}
		}
		return null;
	}

	public ClientFrame getClientFrame(){
		return clientframe;
	}
}
