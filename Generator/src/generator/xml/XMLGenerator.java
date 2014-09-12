package generator.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import template.Key;

public class XMLGenerator {
	/*
	 * constant variable for flag?
	 */
	final int FALSE = 0;
	final int TRUE = 1;
	final int RIGHT = 3;
	final int LEFT = 2;
	
	BufferedWriter shiftXMLFileWriter;
	FileOutputStream shiftXMLFile;
	BufferedWriter notShiftXMLFileWriter;
	FileOutputStream notShiftXMLFile;

	int numOfKey = 0;
	int numOfRow = 0;
	int maxRow = 0;
	int maxCol = 0;
	double verticalRate = 0.0;
	double horizontalRate = 100.0;

	ArrayList<Key> firstKeyArray[];
	ArrayList<Key> secondKeyArray[];

	String totalVerticalGap = "0%p";
	String totalHorizontalGap = "0%p";

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
		

		firstKeyArray = new ArrayList[numOfRow];
		secondKeyArray = new ArrayList[numOfRow];

		try {
			for (int idx = 0; idx < firstKeyArray.length; idx++) {
				firstKeyArray[idx] = new ArrayList<Key>();
				secondKeyArray[idx] = new ArrayList<Key>();
			}
			shiftXMLFile = new FileOutputStream(xmlPath + File.separator + "mykey_keyboard.xml");
			shiftXMLFileWriter = new BufferedWriter(new OutputStreamWriter(shiftXMLFile,"UTF-8"));
			notShiftXMLFile = new FileOutputStream(xmlPath + File.separator + "mykey_shift_keyboard.xml");
			notShiftXMLFileWriter = new BufferedWriter(new OutputStreamWriter(notShiftXMLFile,"UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * key Table Read method
	 * @param firstLayoutInfo
	 * @param secondLayoutInfo
	 */
	public void readKeyArray(String firstLayoutInfo, String secondLayoutInfo) {
		String[] readLine = null;
		String[] tmpStr = null;
		String[] tmpKeyCode = null;
		int rowCount = 0; // row counting variable

		// attribute of each key.
		int[] keyCode = null;
		int keyWidth = 0;
		int keyHeight = 0;
		
		// Point notion.
		int xPos = 0;
		int yPos = 0;

		String keyLabel = null;
		String keyIcon = null;
		int keyEdgeFlags = 0;
		int isRepeatable = 0;

		try {
			readLine = firstLayoutInfo.split("\n");

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

				xPos = Integer.parseInt(tmpStr[3]);
				yPos = Integer.parseInt(tmpStr[4]);
				
				keyLabel = tmpStr[5];
				keyIcon = tmpStr[6];

				keyEdgeFlags = Integer.parseInt(tmpStr[7]);
				isRepeatable = Integer.parseInt(tmpStr[8]);

				rowCount = Integer.parseInt(tmpStr[9]);

				/*
				 * instantiation
				 * keyCode, keyWidth, keyHeight, xPos, yPos, keyLabel, keyIcon
				 * keyEdgeFlags, isRepeatable
				 */
				Key keyInfo = new Key(keyCode, keyWidth, keyHeight, xPos, yPos,
						keyLabel, keyIcon, keyEdgeFlags, isRepeatable);

				firstKeyArray[rowCount - 1].add(keyInfo);
			}
			// another XML create code
			readLine = secondLayoutInfo.split("\n");

			for (int idx = 0; idx < readLine.length; idx++) {
				tmpStr = readLine[idx].split("\t");

				tmpKeyCode = tmpStr[0].split(",");
				keyCode = new int[tmpKeyCode.length];
				for (int codeNum = 0; codeNum < tmpKeyCode.length; codeNum++) {
					keyCode[codeNum] = Integer.parseInt(tmpKeyCode[codeNum]);
				}
				keyWidth = Integer.parseInt(tmpStr[1]);
				keyHeight = Integer.parseInt(tmpStr[2]);

				xPos = Integer.parseInt(tmpStr[3]);
				yPos = Integer.parseInt(tmpStr[4]);

				keyLabel = tmpStr[5];
				keyIcon = tmpStr[6];

				keyEdgeFlags = Integer.parseInt(tmpStr[7]);
				isRepeatable = Integer.parseInt(tmpStr[8]);

				rowCount = Integer.parseInt(tmpStr[9]);
				/*
				 * keyCode, keyWidth, keyHeight, xPos, yPos, keyLabel, keyIcon
				 * keyEdgeFlags, isRepeatable
				 */
				Key keyInfo = new Key(keyCode, keyWidth, keyHeight, xPos, yPos,
						keyLabel, keyIcon, keyEdgeFlags, isRepeatable);

				secondKeyArray[rowCount - 1].add(keyInfo);
			}
			makeXML();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * write detail key information
	 */
	public void writeKeyLayoutInfo() {
		
	}

	/**
	 * make XML File
	 */
	public void makeXML() {
		try {
		// XML File Start (Header)
			shiftXMLFileWriter.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			shiftXMLFileWriter.newLine();
			shiftXMLFileWriter
					.append("<Keyboard xmlns:android=\"http://schemas.android.com/apk/res/android\"");
			shiftXMLFileWriter.append(" android:horizontalGap=\""
					+ totalHorizontalGap + "\"");
			shiftXMLFileWriter.append(" android:verticalGap=\"" + totalVerticalGap
					+ "\">");
			shiftXMLFileWriter.newLine();
			
			
			notShiftXMLFileWriter.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			notShiftXMLFileWriter.newLine();
			notShiftXMLFileWriter
					.append("<Keyboard xmlns:android=\"http://schemas.android.com/apk/res/android\"");
			notShiftXMLFileWriter.append(" android:horizontalGap=\""
					+ totalHorizontalGap + "\"");
			notShiftXMLFileWriter.append(" android:verticalGap=\"" + totalVerticalGap
					+ "\">");
			notShiftXMLFileWriter.newLine();

			writeKeyLayoutInfo(); // write detail information of each key

			shiftXMLFileWriter.append("</Keyboard>");
			shiftXMLFileWriter.newLine();

			shiftXMLFileWriter.flush();
			shiftXMLFile.close();
			shiftXMLFileWriter.close();
			
			notShiftXMLFileWriter.append("</Keyboard>");
			notShiftXMLFileWriter.newLine();

			notShiftXMLFileWriter.flush();
			notShiftXMLFile.close();
			notShiftXMLFileWriter.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
