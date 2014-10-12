package Database;

import gameLogic.Inventory;
import gameLogic.Player;
import gameLogic.Room;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLParser {

	private String fileName;

	public XMLParser(String filename){
		fileName = filename;
	}

	/**
	 * Reads the given XML file to load a saved game.
	 */
	public void readFromXML(){
		try{
			Document jdomDoc = useSAXParser(fileName);
			Element root = jdomDoc.getRootElement();
			List<Element> playerListElements = root.getChildren("player");
			List<Player> playerList = new ArrayList<Player>();
			for (Element playerElement : playerListElements){
				Player player = new Player("");
				player.setInventory(new Inventory());
				player.setNation(playerElement.getChildText("nation"));
				player.setName(playerElement.getChildText("name"));
				//player.setCurrentRoom(new Room(playerElement.getChildText("room")));
				player.setmaxHealth(Integer.parseInt(playerElement.getChildText("maxHealth")));
				player.setcurrentHealth(Integer.parseInt(playerElement.getChildText("currentHealth")));
				player.setAlive(playerElement.getChildText("alive").equals("true"));
			}
			//print all the player information
			for (Player e : playerList){
				System.out.println(e);
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
	}


	public Document useSAXParser(String filename) throws JDOMException, IOException {
	// creating JDOM document from SAX parser
	SAXBuilder saxBuilder = new SAXBuilder();
	return saxBuilder.build(new File(filename));
	}
}
