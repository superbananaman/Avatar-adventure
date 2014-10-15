package Database;

import gameLogic.Armour;
import gameLogic.Boss;
import gameLogic.Fruit;
import gameLogic.Game;
import gameLogic.Inventory;
import gameLogic.Item;
import gameLogic.Monster;
import gameLogic.Player;
import gameLogic.Room;
import gameLogic.Skeleton;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import Renderer.Sprite;
import Renderer.Tile;

public class XMLParser {

	private String fileName;

	public XMLParser(String filename){
		fileName = filename;
		//readFromXML();
	}

	/**
	 * Reads the given XML file to load a saved game.
	 */
	public List<Player> readFromXML(){
		try{
			Document jdomDoc = useSAXParser(fileName);
			Element root = jdomDoc.getRootElement();
			List<Element> playerListElements = root.getChildren("player");
			List<Player> playerList = new ArrayList<Player>();
			//Loading each of the players in the game
			for (Element playerElement : playerListElements){
				Player player = new Player("");
				player.setInventory(new Inventory());
				player.setName(playerElement.getChildText("name"));
				player.setMaxHealth(Integer.parseInt(playerElement.getChildText("maxHealth")));
				player.setCurrentHealth(Integer.parseInt(playerElement.getChildText("currentHealth")));
				player.setAlive(playerElement.getChildText("alive").equals("true"));

				//Loading a players sprite
				List<Element> sprite = playerElement.getChild("sprite").getChildren();
				String spriteName = sprite.get(0).getValue();
				int spriteX = Integer.parseInt(sprite.get(1).getValue());
				int spriteY = Integer.parseInt(sprite.get(2).getValue());
				player.setSprite(new Sprite(spriteName,spriteX,spriteY));

				//Reads the room the player is in, including everything in it
				List<Element> rooms = playerElement.getChild("room").getChildren();
				String roomName = rooms.get(0).getText();
				player.setCurrentRoom(new Room(roomName));

				//Reads the monsters inside of the current room
				List<Element> monsters = rooms.get(1).getChildren();
				for(Element m : monsters){
					List<Element> monster = m.getChildren();
					String monsterName = monster.get(0).getText();
					int posX = (int)Double.parseDouble(monster.get(1).getText());
					int posY = (int)Double.parseDouble(monster.get(2).getText());
					Point p = new Point(posX,posY);
					if(monsterName.equals("Boss")){
						player.getCurrentRoom().getMonsters().add(new Boss(new Tile(null,p,true)));
					}
					else{
						player.getCurrentRoom().getMonsters().add(new Skeleton(new Tile(null,p,true)));
					}
				}

				//Reads the players inventory
				List<Element> playInv = playerElement.getChild("inventory").getChildren();
				for(Element inv : playInv){
					if(inv.getValue().equals("Mango")){
						player.getInventory().add(new Fruit(new Tile(null,null,true),"Mango"));
					}
					else if(inv.getValue().equals("Apple")){
						player.getInventory().add(new Fruit(new Tile(null,null,true),"Apple"));
					}
					else if(inv.getValue().equals("Banana")){
						player.getInventory().add(new Fruit(new Tile(null,null,true),"Banana"));
					}
					else if(inv.getValue().equals("ArmorLegs")){
						player.getInventory().add(new Armour(new Tile(null,null,true),"ArmorLegs"));
					}
					else if(inv.getValue().equals("ArmorChest")){
						player.getInventory().add(new Armour(new Tile(null,null,true),"ArmorChest"));
					}
					else if(inv.getValue().equals("ArmorHead")){
						player.getInventory().add(new Armour(new Tile(null,null,true),"ArmorHead"));
					}
					else if(inv.getValue().equals("Room2")){
						player.getInventory().add(new Armour(new Tile(null,null,true),"Room2"));
					}
					else if(inv.getValue().equals("Room3")){
						player.getInventory().add(new Armour(new Tile(null,null,true),"Room3"));
					}
					else if(inv.getValue().equals("bossroom")){
						player.getInventory().add(new Armour(new Tile(null,null,true),"bossroom"));
					}
				}
				playerList.add(player);
			}

			//print all the player information for debugging purposes
//			for (Player e : playerList){
//				System.out.println("Player Name: "+e.getUID());
//				System.out.println("Sprite Name: "+e.getSprite().getName());
//				System.out.println("Room: "+e.getCurrentRoom().getRoomName());
//				System.out.println("===Inventory===");
//				for(Item i : e.getInventory().getItems()){
//					System.out.println(i.getName());
//				}
//				System.out.println("===============");
//				System.out.println("===Monsters===");
//				for(Monster m : e.getCurrentRoom().getMonsters()){
//					System.out.println(m.getName());
//				}
//				System.out.println("==============");
//				System.out.println("");
//			}
			return playerList;
			}catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * @param filename to read game from
	 * @return a jdom document
	 * @throws JDOMException
	 * @throws IOException
	 */
	public Document useSAXParser(String filename) throws JDOMException, IOException {
	// creating JDOM document from SAX parser
	SAXBuilder saxBuilder = new SAXBuilder();
	return saxBuilder.build(new File(filename));
	}
}
