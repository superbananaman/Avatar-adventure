package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import gameLogic.Armour;
import gameLogic.Boss;
import gameLogic.Door;
import gameLogic.Fruit;
import gameLogic.Game;
import gameLogic.Key;
import gameLogic.Player;
import gameLogic.Room;

import org.junit.*;

public class GameLogicTests {

	@Test
	public void testArmourUnequipping(){
		Player player = new Player("Awesome");
		int max1 = player.getMaxHealth();
		Armour armour = new Armour(null, "Legs");
		armour.unequip(player);
		int max2 = player.getMaxHealth();
		assertTrue(max1 == max2);
	}
	@Test
	public void testArmourUnequipping2(){
		Player player = new Player("Awesome");
		Armour armour = new Armour(null, "Legs");
		armour.use(player);
		int max1 = player.getMaxHealth();
		armour.unequip(player);
		int max2 = player.getMaxHealth();
		assertTrue(max1 > max2);
	}
	@Test
	public void testBossHealth(){
		Boss boss = new Boss(null);
		boss.setHealth(3);
		assertTrue(boss.getHealth() == 4500);
	}


	@Test
	public void testBossHealth2(){
		Boss boss = new Boss(null);
		boss.setHealth(4);
		assertTrue(boss.getHealth() != 4500);
	}
	@Test
	public void testBossAttack(){
		Boss boss = new Boss(null);
		assertTrue(boss.attack() == 400);
	}
	@Test
	public void testBossTakeDamage(){
		Boss boss = new Boss(null);
		boss.setHealth(4);
		boss.takeDamage(5999);
		assertTrue(boss.getHealth() == 1);
	}
	@Test
	public void testDoor(){
		Player player = new Player("Awesome");
		Door door = new Door(null, null, null);
		Key key = new Key(null, "test");
		door.setKey(key);
		player.getInventory().add(key);
		assertTrue(door.use(player));



	}

	@Test
	public void testFruit(){
		Player player = new Player("Awesome");
		Fruit fruit = new Fruit(null, "Mango");
		fruit.use(player);
		assertTrue(player.getCurrentHealth() == 500);
	}


	@Test
	public void testGame(){
		List<Player> players =  new ArrayList<Player>();
		Player player = new Player("Awesome");

		players.add(player);
		players.add(new Player("Bob"));
		players.add(new Player("Christy"));
		Game game = new Game("Awesome", players);

		List<Room> rooms = new ArrayList<Room>();
		rooms.add(new Room("startroom"));
		game.setRooms(rooms);
		System.out.println(rooms.size());
		game.setTotalPlayers(players);

		assertTrue(game.getTotalPlayers().size() == 0);
	}

	@Test
	public void testGame2(){

	}

	@Test
	public void testGame3(){

	}

	@Test
	public void testGame4(){

	}

	@Test
	public void testGame5(){

	}













}
