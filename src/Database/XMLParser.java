package Database;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLParser {

	private String fileName;

	public XMLParser(String filename){
		fileName = filename;
	}

	public void readFromXML(){}


	public Document useSAXParser(String filename) throws JDOMException, IOException {
	// creating JDOM document from SAX parser
	SAXBuilder saxBuilder = new SAXBuilder();
	return saxBuilder.build(new File(filename));
	}
}
