package generator.tablereader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TableReader {
	FileInputStream tableFile;
	BufferedReader tableReader;

	int maxRow = 0; // Number of Keyboard row.
	int maxCol = 0; // Number of keyboard column.
	double verticalRate = 0; // Height rate of Keyboard.
	int numOfKey = 0; // Number of all keys.
	String xmlPath; // XML file path.
	String javaPath; // Java file path.

	/**
	 * Table Reader Constructor Set and Initialize variable.
	 * @param tablePath
	 * @param xmlPath
	 * @param javaPath
	 */
	public TableReader(String tablePath, String xmlPath, String javaPath) {
		try {
			if ((tableFile = new FileInputStream(tablePath)) == null) {
				// Error : tableFile not Found.
				return;
			}

			this.xmlPath = xmlPath;
			this.javaPath = javaPath;
			tableReader = new BufferedReader(new InputStreamReader(tableFile,
					"UTF-8"));

			/*
			 * need function for readTable readTable();
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * read Table for XML and Java generate modifying... 2014.09.03
	 */
	public void readTable() {
		String readLine = "";
		String layoutInfo = ""; // Keyboard layout information.
		String stringKeyInfo = ""; // String key information.
		String keyInfo = ""; // information of each key.
		boolean isStringKeyInfo = false; // String key flag variable.
		boolean isLayoutInfo = false; // layout information flag variable.
		boolean isPhonemeInfo = false; // phoneme information flag variable.

		int numOfXML = 0; // Number of XML File.
		int numOfRow = 0; // Number of key line.

		// read table
		try {
			while ((readLine = tableReader.readLine()) != null) {
				/*
				 * MaxRow Check. It is start of new XML File.
				 */
				if (readLine.equals("MaxRow")) {
					readLine = tableReader.readLine();
					maxRow = Integer.parseInt(readLine);
					numOfRow = 0;
					numOfXML++; // XML File Counting
					isLayoutInfo = false;
					isPhonemeInfo = false;
				}

				/*
				 * #issue if or else if ?
				 */
				
				// MaxColumn Check. It is use to calculate height of key.
				if (readLine.equals("MaxCol")) {
					readLine = tableReader.readLine();
					maxCol = Integer.parseInt(readLine);
				}
				
				// VerticalRate Check. Height of Keyboard.
				if (readLine.equals("VerticalRate")) {
					readLine = tableReader.readLine();
					verticalRate = Double.parseDouble(readLine);
				}
				
				// Start of Layout Information.
				if (readLine.equals("LayoutTable")) {
					isLayoutInfo = true;
					readLine = "";
					continue;
				}
				
				// Start of Phoneme Information.
				if (readLine.equals("PhonemeTable")) {
					isLayoutInfo = false;
					isPhonemeInfo = true;
					readLine = "";
					continue;
				}
				
				// Start of String Key Information.
				if (readLine.equals("StringKey")) {
					isStringKeyInfo = true;
					isLayoutInfo = false;
					isPhonemeInfo = false;
					readLine = "";
					continue;
				}
				
				/*
				 * 1 : Layout Information Write.
				 * 2 : Phoneme Information Write.
				 * 3 : String Key Information Write.
				 * Plus, Constructor for XML and JAVA Generator
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
