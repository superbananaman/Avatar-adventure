package gameLogic;

import gui.ClientFrame;

import java.awt.Point;
import java.io.Serializable;
import java.util.*;

import javax.swing.JOptionPane;

import ClientServer.Slave;
import Renderer.RenderWindow;
import Renderer.Renderer;

public class Game implements Serializable {

	private List<Room> rooms = new ArrayList<Room>();
	private List<Player> totalPlayers = new ArrayList<Player>();
	private ClientFrame clientframe;
	private Renderer renderer = new Renderer();

	public Game(String UID, List<Player> players) {
		setUp();
		setTotalPlayers(players);
		clientframe = new ClientFrame(UID, players, this);

	}

	/**
	 * sets up the rooms and monsters
	 */

	public void setUp() {
		addRoom();
		setMonsterHealth(getTotalPlayers().size());

	}

	/**
	 *
	 * @param size
	 *            is the number of players. the monster health is proportional
	 *            to the number of players
	 */

	public void setMonsterHealth(int size) {
		for (Room r : rooms) {
			for (Monster m : r.getMonsters()) {
				m.setHealth(size);
			}
		}

	}

	/**
	 * makes all the rooms in the game and makes the tileset for each
	 */

	public void addRoom() {
		System.out.println("adding rooms");
		rooms.add(new Room("startroom"));
		rooms.add(new Room("room1"));
		rooms.add(new Room("room2"));
		rooms.add(new Room("room3"));
		rooms.add(new Room("bossroom"));
		for (Room r : rooms) {

			r.setTileSet(renderer.parseTileSet(r));
			System.out.println(r.getRoomName() + " ADDED");
		}
	}

	public List<Player> getTotalPlayers() {
		return totalPlayers;
	}

	public void setTotalPlayers(List<Player> totalPlayers) {
		this.totalPlayers = totalPlayers;
		for (Player p : totalPlayers) {
			p.setCurrentRoom(rooms.get(0));
		}
	}

	public Room getRoom(String name) {
		for (Room r : rooms) {
			System.out.println(r.getRoomName());
			if (r.getRoomName().equalsIgnoreCase(name)) {
				return r;
			}
		}
		return null;
	}

	public ClientFrame getClientFrame() {
		return clientframe;
	}

	public Room getCurrentRoom() {
		return totalPlayers.get(0).getCurrentRoom();
	}

	public void updateDeadPlayers(String name){
		Room currentroom = totalPlayers.get(0).getCurrentRoom();
		List<Player> players = totalPlayers;
		synchronized (players) {
			for(Player p: players){
				if(p.getUID().equals(name)){
					p.setCurrentHealth(500);
					p.getSprite().setCurrentX(1000000);
					p.getSprite().setCurrentY(1000000);
					Slave.sendToConsole("A player has died. Leave the room so they can respawn");

				}
			}
		}
	}



	public void updateDamageMonsters(Point location, int damage){
		Room currentroom = totalPlayers.get(0).getCurrentRoom();
		List<Monster> monsters = currentroom.getMonsters();
		synchronized (monsters) {
			for (Monster m : monsters) {
				if (m.getTile().getLocation().equals(location)) {
					m.takeDamage(damage);

					break;
				}
			}
		}

	}


	public void updateDeadMonsters(Point location) {
		Room currentroom = totalPlayers.get(0).getCurrentRoom();
		List<Monster> monsters = currentroom.getMonsters();
		synchronized (monsters) {
			for (Monster m : monsters) {
				if (m.getTile().getLocation().equals(location)) {
					currentroom.getMonsters().remove(m);
					m.getTile().setMonsterImage(null);
					if(m instanceof Boss && currentroom.getRoomName().equals("bossroom")){
						JOptionPane.showMessageDialog(clientframe,
								"Congratulations you have finished the game!");
						System.exit(0);
					}
					System.out.println("Monster is deleted");
					break;
				}
			}
		}

	}

}
