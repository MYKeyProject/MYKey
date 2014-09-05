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
			XMLFile = new FileOutputStream(xmlPath + File.separator
					+ "keyboard.xml");
			XMLFileWriter = new BufferedWriter(new OutputStreamWriter(XMLFile,
					"UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readKeyArray(String layoutInfo) {
		String[] readLine = null;
		String[] tmpStr = null;
		String[] tmpKeyCode = null;
		int rowCount = 0; // row counting variable

		// attribute of each key.
		int[] keyCode = null;
		int keyWidth = 0;
		int keyHeight = 0;

		String keyLabel = null; 
		String keyIcon = null;
		int keyEdgeFlags = 0;
		int isRepeatable = 0;

		try {
			readLine = layoutInfo.split("\n");

			for (int idx = 0; idx < readLine.length; idx++) {
				tmpStr = readLine[idx].split("\t");
				tmpKeyCode = tmpStr[0].split(",");
				keyCode = new int[tmpKeyCode.length];
				for (int numOfKeyCode = 0; numOfKeyCode < tmpKeyCode.length; numOfKeyCode++) {
					keyCode[numOfKeyCode] = Integer
							.parseInt(tmpKeyCode[numOfKeyCode]);
				}
				keyWidth = Integer.parseInt(tmpStr[1]);
				keyHeight = Integer.parseInt(tmpStr[2]);

				keyLabel = tmpStr[3];
				keyIcon = tmpStr[4];

				keyEdgeFlags = Integer.parseInt(tmpStr[5]);
				isRepeatable = Integer.parseInt(tmpStr[6]);

				rowCount = Integer.parseInt(tmpStr[7]);

				// instantiation
				Key keyInfo = new Key(keyCode, keyWidth, keyHeight, keyLabel,
						keyIcon, keyEdgeFlags, isRepeatable);
				
				keyArray[rowCount-1].add(keyInfo);
			}
			makeXML();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeKeyLayoutInfo() {
		// write layout information
	}

	public void makeXML() {
		// XML File Creator
	}
}
