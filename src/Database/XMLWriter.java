package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import gameLogic.Game;
import gameLogic.Item;
import gameLogic.Monster;
import gameLogic.Player;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLWriter {

	private String fileName;
	private List<Player> currentPlayers;

	public XMLWriter(String filename, List<Player> players) {
		fileName = filename;
		currentPlayers = players;
		writeToXML();
	}

	/**
	 * Loops through the players in the game saving their
	 * states, as well as the rooms, monsters etc.
	 */
	public void writeToXML() {
		try {
			Element game = new Element("game");
			Document doc = new Document(game);
			//loops through all the players in the game
			for (Player p : currentPlayers) {
				Element player = new Element("player");
				player.addContent(new Element("nation").setText(p.getNation()));
				player.addContent(new Element("name").setText(p.getUID()));
				player.addContent(new Element("maxHealth").setText(Integer.toString(p.getMaxHealth())));
				player.addContent(new Element("currentHealth").setText(Integer.toString(p.getCurrentHealth())));
				player.addContent(new Element("alive").setText(Boolean.toString(p.isAlive())));

				Element sprite = new Element("sprite");
				sprite.addContent(new Element("spriteName").setText(p.getSprite().getName()));
				sprite.addContent(new Element("spriteX").setText(Integer.toString(p.getSprite().getCurrentX())));
				sprite.addContent(new Element("spriteY").setText(Integer.toString(p.getSprite().getCurrentY())));
				player.addContent(sprite);

				//Loops through the room the player is in
				Element rm = new Element("room");
				rm.addContent(new Element("roomName").setText(p.getCurrentRoom().getRoomName()));
				Element monst = new Element("monsters");
				for(Monster m : p.getCurrentRoom().getMonsters()){
					Element monster = new Element("monster");
					monster.addContent(new Element("monsterType").setText(m.getName()));
					monster.addContent(new Element("positionX").setText(Double.toString(m.getTile().getLocation().getX())));
					monster.addContent(new Element("positionY").setText(Double.toString(m.getTile().getLocation().getY())));
					monster.addContent(new Element("playerCount").setText(Integer.toString(currentPlayers.size())));
					monst.addContent(monster);
				}
				rm.addContent(monst);
				player.addContent(rm);
				Element inv = new Element("inventory");
				//for each player goes through their inventory
				for (Item item : p.getInventory().getItems()) {
					inv.addContent(new Element("item").setText(item.getName()));
				}
				player.addContent(inv);
				doc.getRootElement().addContent(player);
			}
			XMLOutputter xmlOutput = new XMLOutputter();

			//Displays the output with nice formatting
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(fileName));
			System.out.println("File Saved!");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}
}
