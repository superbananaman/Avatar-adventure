package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import gameLogic.Armour;
import gameLogic.Boss;
import gameLogic.Door;
import gameLogic.Fruit;
import gameLogic.Game;
import gameLogic.Inventory;
import gameLogic.Key;
import gameLogic.Player;
import gameLogic.Potion;
import gameLogic.Room;
import gameLogic.Skeleton;

import org.junit.*;

import Renderer.Renderer;
import Renderer.Tile;

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
		assertTrue(boss.attack() == 200);
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
		//game.setRooms(rooms);



		assertTrue(game.getTotalPlayers().size() == players.size());
	}

	@Test
	public void testInventory(){
		Inventory inventory = new Inventory();
		Key key = new Key(null, "Awesome");
		inventory.add(key);
		inventory.remove(key);
		inventory.add(key);
		assertTrue(inventory.has(key));
	}

	@Test
	public void testPlayer(){
		Player player = new Player("Awesome");
		Tile tile = new Tile(null, null, true);
		player.pickUp(tile);


	}

	@Test
	public void testPlayerAttack1(){
		Player player = new Player("Awesome");
		assertTrue(player.attack1() == 350);
	}

	@Test
	public void testPlayerAttack2(){
		Player player = new Player("Awesome");
		assertTrue(player.attack2() == 1000);
	}

		@Test
		public void testPlayerUpdateHealth(){
			Player player = new Player("Awesome");
			player.updateCurrentHealth(1000);
			assertTrue(player.getCurrentHealth() == 500);


		}

		@Test
		public void testPlayerUpdateHealth2(){
			Player player = new Player("Awesome");
			player.setCurrentHealth(100);
			player.setMaxHealth(120);
			player.updateCurrentHealth(21);
			assertTrue(player.getCurrentHealth() == 120);
		}


		@Test
		public void testPotion(){
			Player player = new Player("Awesome");
			player.setMaxHealth(1000);
			player.setCurrentHealth(499);
			Potion potion = new Potion(null, "Awesome");
			potion.use(player);
			assertTrue(player.getCurrentHealth() == 999);
		}
		@Test
		public void testSkeletonHealth(){
			Skeleton skeleton = new Skeleton(null);
			skeleton.setHealth(3);
			assertTrue(skeleton.getHealth() == 1200);
		}


		@Test
		public void testSkeletonHealth2(){
			Skeleton skeleton = new Skeleton(null);
			skeleton.setHealth(4);
			assertTrue(skeleton.getHealth() != 4500);
		}
		@Test
		public void testSkeletonAttack(){
			Skeleton skeleton = new Skeleton(null);
			assertTrue(skeleton.attack() == 100);
		}
		@Test
		public void testSkeletonTakeDamage(){
			Skeleton skeleton = new Skeleton(null);
			skeleton.setHealth(4);
			skeleton.takeDamage(1599);
			assertTrue(skeleton.getHealth() == 1);
		}








}
