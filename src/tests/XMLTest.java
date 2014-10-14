package tests;

import gameLogic.Armour;
import gameLogic.Fruit;
import gameLogic.Inventory;
import gameLogic.Item;
import gameLogic.Key;
import gameLogic.Player;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Database.XMLWriter;
import Renderer.Tile;


public class XMLTest {

	public static List<Player> play = new ArrayList<Player>();
	private static BufferedImage ArmorLegs = getIso(64*5,64*3);
	private static BufferedImage KeyRoom2 = getIso(64*2,64*2);
	private static BufferedImage Mango = getIso(64,64*3);

	public static void main(String[] args){
		test();
		new XMLWriter("testFile",play);
	}

	public static void test(){
		Item key1 = new Key(new Tile(KeyRoom2,new Point(10,10),true),"Room2");
		Item legArmour = new Armour(new Tile(ArmorLegs,new Point(20,10),true),"ArmorLegs");
		Item mango = new Fruit(new Tile(Mango,new Point(20,15),true),"Mango");

		play.add(new Player("Swifty"));
		play.add(new Player("Mattdawg"));
		play.add(new Player("CC"));
		play.add(new Player("DevDawg"));

		play.get(0).setNation("water");
		play.get(0).setMaxHealth(1500);
		play.get(0).setCurrentHealth(1500);
		play.get(0).getInventory().add(key1);

		play.get(1).setNation("earth");
		play.get(1).setMaxHealth(700);
		play.get(1).setCurrentHealth(500);
		play.get(1).getInventory().add(legArmour);

		play.get(2).setNation("air");
		play.get(2).setMaxHealth(500);
		play.get(2).setCurrentHealth(500);
		play.get(2).getInventory().add(mango);

		play.get(3).setNation("fire");
		play.get(3).setMaxHealth(1000);
		play.get(3).setCurrentHealth(800);
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
