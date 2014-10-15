package gameLogic;


import gui.ClientFrame;

import java.io.Serializable;
import java.util.*;

import Renderer.RenderWindow;



public class Game implements Serializable{

	private List<Room> rooms = new ArrayList<Room>();
	private List<Player> totalPlayers = new ArrayList<Player>();
	private ClientFrame clientframe;


	public Game(String UID ,List<Player> players){
		setTotalPlayers(players);
		setUp();
		clientframe = new ClientFrame(UID, players, this);
		clientframe.setVisible(true);

	}

	public void setUp(){
		setMonsterHealth(getTotalPlayers().size());
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
		for(Room r: rooms){
			r.setTileSet(clientframe.getRenderWindow().renderer.parseTileSet(r));
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
			if(r.getRoomName().equals(name)){
				return r;
			}
		}
		return null;
	}

	public ClientFrame getClientFrame(){
		return clientframe;
	}
}
