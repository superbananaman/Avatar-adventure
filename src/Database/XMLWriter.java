package Database;

import org.jdom2.Document;
import org.jdom2.Element;

public class XMLWriter {

	private String fileName;

	public XMLWriter(String filename){
		fileName = filename;
	}

	public void writeToXML(){
		Element game = new Element("game");
		Document doc = new Document(game);
	}
}
