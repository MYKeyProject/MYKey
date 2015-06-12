/* 
 * Copyright (C) 2008-2009 The Android Open Source Project 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package generator.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import generator.template.Key;

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

	// they has layout information
	ArrayList<Key> firstKeyArray[];
	ArrayList<Key> secondKeyArray[];

	String totalVerticalGap = "0%p"; // verticalGap of keyboard
	String totalHorizontalGap = "0%p"; // horizontalGap of keyboard

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
			shiftXMLFile = new FileOutputStream(xmlPath + File.separator
					+ "mykey_keyboard.xml");
			shiftXMLFileWriter = new BufferedWriter(new OutputStreamWriter(
					shiftXMLFile, "UTF-8"));
			notShiftXMLFile = new FileOutputStream(xmlPath + File.separator
					+ "mykey_shift_keyboard.xml");
			notShiftXMLFileWriter = new BufferedWriter(new OutputStreamWriter(
					notShiftXMLFile, "UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * key Table Read method
	 * 
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
				 * instantiation keyCode, keyWidth, keyHeight, xPos, yPos,
				 * keyLabel, keyIcon keyEdgeFlags, isRepeatable
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
		Key prevKey = null;
		Key currentKey = null;
		boolean isRowStart;

		try {
			for (int row = 0; row < numOfRow; row++) {
				int rowLength = firstKeyArray[row].size();
				for (int element = 0; element < rowLength; element++) {
					isRowStart = false;

					currentKey = firstKeyArray[row].get(element);
					if (element == 0) {
						isRowStart = true;
					} else {
						prevKey = firstKeyArray[row].get(element - 1);
					}

					// start of line(Row)
					if (isRowStart) {
						double rowHeight = 0;

						// last row of keyboard
						if (row == numOfRow - 1) {
							rowHeight = maxRow - currentKey.getYPos();
						} else {
							rowHeight = firstKeyArray[row + 1].get(element)
									.getYPos() - currentKey.getYPos();
						}
						rowHeight = (verticalRate / maxRow) * rowHeight;
						rowHeight = Double.parseDouble(String.format("%.2f",
								rowHeight));

						shiftXMLFileWriter.append("\t<Row android:keyHeight=\""
								+ rowHeight + "%p\">");
						shiftXMLFileWriter.newLine();
					}

					shiftXMLFileWriter.append("\t\t<Key android:codes=\"");
					int[] keyCode = currentKey.getKeyCode();
					for (int codeNum = 0; codeNum < keyCode.length; codeNum++) {
						shiftXMLFileWriter.append(Integer
								.toString(keyCode[codeNum]));
						if (codeNum < keyCode.length - 1) {
							shiftXMLFileWriter.append(",");
						}
					}

					double keyWidth = ((horizontalRate / maxCol) * currentKey
							.getKeyWidth());
					keyWidth = Double.parseDouble(String.format("%.2f",
							keyWidth));
					shiftXMLFileWriter.append("\" android:keyWidth=\""
							+ keyWidth + "%p\" ");

					double keyHeight = ((verticalRate / maxRow) * currentKey
							.getKeyHeight());
					keyHeight = Double.parseDouble(String.format("%.2f",
							keyHeight));
					shiftXMLFileWriter.append("android:keyHeight=\""
							+ keyHeight + "%p\" ");

					if (!currentKey.getKeyLabel().equals("!no")) {
						shiftXMLFileWriter.append("android:keyLabel=\""
								+ currentKey.getKeyLabel() + "\" ");
					}
					if (!currentKey.getKeyIcon().equals("!no")) {
						shiftXMLFileWriter.append("android:keyIcon=\""
								+ currentKey.getKeyIcon() + "\" ");
					}

					if (currentKey.getKeyEdgeFlags() == RIGHT) {
						shiftXMLFileWriter
								.append("android:keyEdgeFlags=\"right\" ");
					}

					if (currentKey.getKeyEdgeFlags() == LEFT) {
						shiftXMLFileWriter
								.append("android:keyEdgeFlags=\"left\" ");
					}

					double horizontalGap = 0;
					// if first key of each row
					if (isRowStart && (currentKey.getXPos() != 0)) {
						horizontalGap = ((horizontalRate / maxCol) * (double) currentKey
								.getXPos());
						horizontalGap = Double.parseDouble(String.format(
								"%.2f", horizontalGap));
					}

					// another key
					if (!isRowStart
							&& ((currentKey.getXPos() - prevKey.getXPos()) != prevKey
									.getKeyWidth())) {
						horizontalGap = (horizontalRate / maxCol)
								* ((currentKey.getXPos() - prevKey.getXPos()) - prevKey
										.getKeyWidth());
					}

					shiftXMLFileWriter.append("android:horizontalGap=\""
							+ horizontalGap + "%p\" ");

					if (currentKey.getIsRepeatable() == TRUE) {
						shiftXMLFileWriter.append("android:isRepeatable=\"true"
								+ "\" ");
					}
					shiftXMLFileWriter.append(" />");
					shiftXMLFileWriter.newLine();

					// end of line(end)
					if (element == rowLength - 1) {
						shiftXMLFileWriter.append("\t</Row>");
						shiftXMLFileWriter.newLine();
					}
				}
			}

			// second LayoutInfo
			for (int row = 0; row < numOfRow; row++) {
				int rowLength = secondKeyArray[row].size();
				for (int element = 0; element < rowLength; element++) {
					isRowStart = false;

					currentKey = secondKeyArray[row].get(element);
					if (element == 0) {
						isRowStart = true;
					} else {
						prevKey = secondKeyArray[row].get(element - 1);
					}

					// start of line(Row)
					if (isRowStart) {
						double rowHeight = 0;

						// last row of keyboard
						if (row == numOfRow - 1) {
							rowHeight = maxRow - currentKey.getYPos();
						}

						else {
							rowHeight = secondKeyArray[row + 1].get(element)
									.getYPos() - currentKey.getYPos();
						}
						rowHeight = (verticalRate / maxRow) * rowHeight;
						rowHeight = Double.parseDouble(String.format("%.2f",
								rowHeight));

						notShiftXMLFileWriter
								.append("\t<Row android:keyHeight=\""
										+ rowHeight + "%p\">");
						notShiftXMLFileWriter.newLine();
					}

					notShiftXMLFileWriter.append("\t\t<Key android:codes=\"");
					int[] keyCode = currentKey.getKeyCode();
					for (int codeNum = 0; codeNum < keyCode.length; codeNum++) {
						notShiftXMLFileWriter.append(Integer
								.toString(keyCode[codeNum]));
						if (codeNum < keyCode.length - 1) {
							notShiftXMLFileWriter.append(",");
						}
					}

					double keyWidth = ((horizontalRate / maxCol) * currentKey
							.getKeyWidth());
					keyWidth = Double.parseDouble(String.format("%.2f",
							keyWidth));
					notShiftXMLFileWriter.append("\" android:keyWidth=\""
							+ keyWidth + "%p\" ");

					double keyHeight = ((verticalRate / maxRow) * currentKey
							.getKeyHeight());
					keyHeight = Double.parseDouble(String.format("%.2f",
							keyHeight));
					notShiftXMLFileWriter.append("android:keyHeight=\""
							+ keyHeight + "%p\" ");

					if (!currentKey.getKeyLabel().equals("!no")) {
						notShiftXMLFileWriter.append("android:keyLabel=\""
								+ currentKey.getKeyLabel() + "\" ");
					}
					if (!currentKey.getKeyIcon().equals("!no")) {
						notShiftXMLFileWriter.append("android:keyIcon=\""
								+ currentKey.getKeyIcon() + "\" ");
					}

					if (currentKey.getKeyEdgeFlags() == RIGHT) {
						notShiftXMLFileWriter
								.append("android:keyEdgeFlags=\"right\" ");
					}

					if (currentKey.getKeyEdgeFlags() == LEFT) {
						notShiftXMLFileWriter
								.append("android:keyEdgeFlags=\"left\" ");
					}

					double horizontalGap = 0;
					// if first key of each row
					if (isRowStart && (currentKey.getXPos() != 0)) {
						horizontalGap = ((horizontalRate / maxCol) * (double) currentKey
								.getXPos());
						horizontalGap = Double.parseDouble(String.format(
								"%.2f", horizontalGap));
					}

					// another key
					if (!isRowStart
							&& ((currentKey.getXPos() - prevKey.getXPos()) != prevKey
									.getKeyWidth())) {
						horizontalGap = (horizontalRate / maxCol)
								* ((currentKey.getXPos() - prevKey.getXPos()) - prevKey
										.getKeyWidth());
					}

					notShiftXMLFileWriter.append("android:horizontalGap=\""
							+ horizontalGap + "%p\" ");

					if (currentKey.getIsRepeatable() == TRUE) {
						notShiftXMLFileWriter
								.append("android:isRepeatable=\"true" + "\" ");
					}
					notShiftXMLFileWriter.append(" />");
					notShiftXMLFileWriter.newLine();

					// end of line(end)
					if (element == rowLength - 1) {
						notShiftXMLFileWriter.append("\t</Row>");
						notShiftXMLFileWriter.newLine();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * make XML File
	 */
	public void makeXML() {
		try {
			// XML File Start (Header)
			shiftXMLFileWriter
					.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			shiftXMLFileWriter.newLine();
			shiftXMLFileWriter
					.append("<Keyboard xmlns:android=\"http://schemas.android.com/apk/res/android\"");
			shiftXMLFileWriter.append(" android:horizontalGap=\""
					+ totalHorizontalGap + "\"");
			shiftXMLFileWriter.append(" android:verticalGap=\""
					+ totalVerticalGap + "\">");
			shiftXMLFileWriter.newLine();

			notShiftXMLFileWriter
					.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			notShiftXMLFileWriter.newLine();
			notShiftXMLFileWriter
					.append("<Keyboard xmlns:android=\"http://schemas.android.com/apk/res/android\"");
			notShiftXMLFileWriter.append(" android:horizontalGap=\""
					+ totalHorizontalGap + "\"");
			notShiftXMLFileWriter.append(" android:verticalGap=\""
					+ totalVerticalGap + "\">");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
