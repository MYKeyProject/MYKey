package generator.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import template.Key;

public class XMLGenerator {
	BufferedWriter XMLFileWriter;
	FileOutputStream XMLFile;

	int numOfKey = 0;
	int numOfRow = 0;
	int maxRow = 0;
	int maxCol = 0;
	double verticalRate = 0.0;
	double horizontalRate = 100.0;
	
	ArrayList<Key> keyArray[];
	
	
	/**
	 * XMLGenerator Constructor.
	 */
	@SuppressWarnings("unchecked")
	public XMLGenerator(int numOfKey, int numOfRow, int maxRow, int maxCol,
			double verticalRate, String xmlPath) {
		this.numOfKey = numOfKey;
		this.numOfRow = numOfRow;
		this.maxRow = maxRow;
		this.maxCol = maxCol;
		this.verticalRate = verticalRate;
		
		keyArray = new ArrayList[numOfRow];
		
		try {
			for (int idx = 0; idx < keyArray.length; idx++) {
				keyArray[idx] = new ArrayList<Key>();
			}
			XMLFile = new FileOutputStream(xmlPath + File.separator + "keyboard.xml");
			XMLFileWriter = new BufferedWriter(new OutputStreamWriter(XMLFile, "UTF-8"));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readKeyArray() {
		// read and make key array list
	}
	
	public void writeKeyLayoutInfo() {
		// write layout information
	}
	
	public void makeXML() {
		// XML File Creator
	}
}
