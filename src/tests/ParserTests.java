package tests;

import static org.junit.Assert.*;
import gameLogic.Armour;
import gameLogic.Boss;
import gameLogic.Fruit;
import gameLogic.Item;
import gameLogic.Key;
import gameLogic.Monster;
import gameLogic.Player;
import gameLogic.Room;
import gameLogic.Skeleton;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;

import Database.XMLParser;
import Database.XMLWriter;
import Renderer.Sprite;
import Renderer.Tile;

public class ParserTests {
	public List<Player> play = new ArrayList<Player>();
	private BufferedImage ArmorLegs = getIso(64*5,64*3);
	private BufferedImage KeyRoom2 = getIso(64*2,64*2);
	private BufferedImage Mango = getIso(64,64*3);
	private BufferedImage floor = getIso(0,64*5);
	public List<Player> xmlPlayers = new ArrayList<Player>();

	public void setUpEnvironment(){
		Item key1 = new Key(new Tile(KeyRoom2,new Point(10,10),true),"Room2");
		Item legArmour = new Armour(new Tile(ArmorLegs,new Point(20,10),true),"ArmorLegs");
		Item mango = new Fruit(new Tile(Mango,new Point(20,15),true),"Mango");
		Monster skelly = new Skeleton(new Tile(floor,new Point(5,5),true));
		Monster baws = new Boss(new Tile(floor,new Point(8,8),true));

		play.add(new Player("Swifty"));
		play.add(new Player("Mattdawg"));
		play.add(new Player("CC"));
		play.add(new Player("DevDawg"));

		play.get(0).setNation("water");
		play.get(0).setMaxHealth(1500);
		play.get(0).setCurrentHealth(1500);
		play.get(0).getInventory().add(key1);
		play.get(0).setCurrentRoom(new Room("bossroom"));
		play.get(0).getCurrentRoom().getMonsters().add(skelly);
		play.get(0).getCurrentRoom().getMonsters().add(baws);
		play.get(0).setSprite(new Sprite(play.get(0).getUID(),0,0));

		play.get(1).setNation("earth");
		play.get(1).setMaxHealth(700);
		play.get(1).setCurrentHealth(500);
		play.get(1).getInventory().add(legArmour);
		play.get(1).setCurrentRoom(new Room("room2"));
		play.get(1).getCurrentRoom().getMonsters().add(skelly);
		play.get(1).setSprite(new Sprite(play.get(1).getUID(),0,1));

		play.get(2).setNation("air");
		play.get(2).setMaxHealth(500);
		play.get(2).setCurrentHealth(500);
		play.get(2).getInventory().add(mango);
		play.get(2).setCurrentRoom(new Room("room1"));
		play.get(2).setSprite(new Sprite(play.get(2).getUID(),0,2));

		play.get(3).setNation("fire");
		play.get(3).setMaxHealth(1000);
		play.get(3).setCurrentHealth(800);
		play.get(3).setCurrentRoom(new Room("startroom"));
		play.get(3).setSprite(new Sprite(play.get(3).getUID(),0,3));
		play.get(3).setAlive(false);

		XMLWriter write = new XMLWriter("testFile",play);
		XMLParser parse = new XMLParser("testFile");
		xmlPlayers = parse.readFromXML();

	//General Player field tests
	}
	@Test
	public void GetValidName_1(){
		setUpEnvironment();
		assertTrue(xmlPlayers.get(0).getUID().equals(play.get(0).getUID()));
	}
	@Test
	public void GetValidName_2(){
		setUpEnvironment();
		assertTrue(xmlPlayers.get(3).getUID().equals(play.get(3).getUID()));
	}
	@Test
	public void GetInvalidName_1(){
		setUpEnvironment();
		assertFalse(xmlPlayers.get(2).getUID().equals(play.get(1).getUID()));
	}
	@Test
	public void GetValidHealth_1(){
		setUpEnvironment();
		int x = xmlPlayers.get(0).getCurrentHealth();
		int oldX = play.get(0).getCurrentHealth();
		assertTrue(x == oldX);
	}
	@Test
	public void GetValidHealth_2(){
		setUpEnvironment();
		xmlPlayers.get(0).setCurrentHealth(2);
		int x = xmlPlayers.get(1).getCurrentHealth();
		int oldX = play.get(1).getCurrentHealth();
		assertTrue(x == oldX);
	}
	@Test
	public void GetInvalidHealth_1(){
		setUpEnvironment();
		int x = xmlPlayers.get(0).getCurrentHealth();
		int oldX = play.get(1).getCurrentHealth();
		assertFalse(x == oldX);
	}
	@Test
	public void GetValidMaxHealth_1(){
		setUpEnvironment();
		int x = xmlPlayers.get(2).getMaxHealth();
		int y = play.get(2).getMaxHealth();
		assertTrue(x == y);
	}
	@Test
	public void GetInvalidMaxHealth_1(){
		setUpEnvironment();
		int x = xmlPlayers.get(2).getMaxHealth();
		int y = play.get(3).getMaxHealth();
		assertFalse(x == y);
	}
	@Test
	public void GetValidAlive_1(){
		setUpEnvironment();
		boolean x = xmlPlayers.get(3).isAlive();
		boolean y = play.get(3).isAlive();
		assertTrue(x == y);
	}
	@Test
	public void GetInvalidAlive_1(){
		setUpEnvironment();
		boolean x = xmlPlayers.get(3).isAlive();
		boolean y = play.get(2).isAlive();
		assertFalse(x == y);
	}

	//Tests for Player Sprite
	@Test
	public void GetValidSpriteName_1(){
		setUpEnvironment();
		String x = xmlPlayers.get(0).getSprite().getName();
		String y = play.get(0).getSprite().getName();
		assertTrue(x.equals(y));
	}
	@Test
	public void GetInvalidSpriteName_1(){
		setUpEnvironment();
		String x = xmlPlayers.get(0).getSprite().getName();
		String y = play.get(1).getSprite().getName();
		assertFalse(x.equals(y));
	}
	@Test
	public void GetValidSpritePos_1(){
		setUpEnvironment();
		int posX = xmlPlayers.get(1).getSprite().getCurrentX();
		int oldX = play.get(1).getSprite().getCurrentX();
		int posY = xmlPlayers.get(1).getSprite().getCurrentY();
		int oldY = play.get(1).getSprite().getCurrentY();
		assertTrue((posX == oldX)&(posY == oldY));
	}
	@Test
	public void GetInvalidSpritePos_1(){
		setUpEnvironment();
		int posX = xmlPlayers.get(1).getSprite().getCurrentX();
		int oldX = play.get(0).getSprite().getCurrentX();
		int posY = xmlPlayers.get(1).getSprite().getCurrentY();
		int oldY = play.get(0).getSprite().getCurrentY();
		assertFalse((posX == oldX)&(posY == oldY));
	}

	//Check Current room for the player
	@Test
	public void GetValidRoom_1(){
		setUpEnvironment();
		Room one = xmlPlayers.get(0).getCurrentRoom();
		Room two = play.get(0).getCurrentRoom();
		assertTrue(one.getRoomName().equals(two.getRoomName()));
	}
	@Test
	public void GetInvalidRoom_1(){
		setUpEnvironment();
		Room one = xmlPlayers.get(0).getCurrentRoom();
		Room two = play.get(1).getCurrentRoom();
		assertFalse(one.getRoomName().equals(two.getRoomName()));
	}
	@Test
	public void GetValidMonster_1(){
		setUpEnvironment();
		List<Monster> one = xmlPlayers.get(0).getCurrentRoom().getMonsters();
		List<Monster> two = play.get(0).getCurrentRoom().getMonsters();
		assertTrue(one.get(0).getName().equals(two.get(0).getName()));
	}
	@Test
	public void GetInvalidMonster_1(){
		setUpEnvironment();
		List<Monster> one = xmlPlayers.get(0).getCurrentRoom().getMonsters();
		List<Monster> two = play.get(0).getCurrentRoom().getMonsters();
		assertFalse(one.get(0).getName().equals(two.get(1).getName()));
	}
	@Test
	public void GetValidMonsterPos_1(){
		setUpEnvironment();
		List<Monster> one = xmlPlayers.get(0).getCurrentRoom().getMonsters();
		List<Monster> two = play.get(0).getCurrentRoom().getMonsters();
		Point pos = one.get(1).getTile().getLocation();
		Point oldPos = two.get(1).getTile().getLocation();
		assertTrue(pos.equals(oldPos));
	}
	@Test
	public void GetInvalidMonsterPos_1(){
		setUpEnvironment();
		List<Monster> one = xmlPlayers.get(0).getCurrentRoom().getMonsters();
		List<Monster> two = play.get(0).getCurrentRoom().getMonsters();
		Point pos = one.get(1).getTile().getLocation();
		Point oldPos = two.get(0).getTile().getLocation();
		assertFalse(pos.equals(oldPos));
	}

	//Players Inventory
	@Test
	public void GetValidItems_1(){
		setUpEnvironment();
		List<Item> one = xmlPlayers.get(0).getInventory().getItems();
		List<Item> two = play.get(0).getInventory().getItems();
		String x = one.get(0).getName();
		String y = two.get(0).getName();
		assertTrue(x.equals(y));
	}
	@Test
	public void GetInvalidItems_1(){
		setUpEnvironment();
		List<Item> one = xmlPlayers.get(0).getInventory().getItems();
		List<Item> two = play.get(2).getInventory().getItems();
		String x = one.get(0).getName();
		String y = two.get(0).getName();
		assertFalse(x.equals(y));
	}

	public static BufferedImage getIso(int x, int y) {
		BufferedImage tilesheet = null;
		try {
			tilesheet = ImageIO.read(new File("iso.png"));
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return tilesheet.getSubimage(x, y, 64, 64);
	}
}
