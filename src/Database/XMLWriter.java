package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import gameLogic.Game;
import gameLogic.Item;
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
