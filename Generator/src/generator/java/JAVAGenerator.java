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

package generator.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class JAVAGenerator {
	FileOutputStream javaFile;
	BufferedWriter javaFileWriter;
	
	HashMap<String, Integer> keyMap; // for fastest indexing
	
	ArrayList<String> stringCodeList;
	ArrayList<String> stringLabelList;	
	
	/**
	 * JAVAGenerator Constructor.
	 */
	public JAVAGenerator(String javaPath) {
		keyMap = new HashMap<String, Integer>();
		stringCodeList = new ArrayList<String>();
		stringLabelList = new ArrayList<String>();
		
		try {
			javaFile = new FileOutputStream(javaPath + File.separator
					+ "KeyMap.java");
			javaFileWriter = new BufferedWriter(new OutputStreamWriter(
					javaFile, "UTF-8"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * read Phoneme information in 'key table'
	 * @param phonemeInfoString
	 * @param stringKeyInfo
	 */
	public void readPhonemeTable(String phonemeInfoString, String stringKeyInfo) {
		String[] readLine;
		int phoneme = 0;
		String[] keyCode = null;

		readLine = phonemeInfoString.split("\n");
		for (int idx = 0; idx < readLine.length; idx++) {
			String[] tmpStr = readLine[idx].split("\t");
			phoneme = Integer.parseInt(tmpStr[0]);

			keyCode = new String[tmpStr.length - 1];

			for (int num = 0; num < tmpStr.length - 1; num++) {
				keyCode[num] = tmpStr[num + 1];

			}
			makeKeyMap(keyCode, phoneme);
		}

		readLine = stringKeyInfo.split("\n");
		for (int idx = 0; idx < readLine.length; idx++) {
			String[] tmpStr = readLine[idx].split("\t");
			String stringCode = tmpStr[0];
			if (stringCode.equals(""))
				break; // if not exist string key.
			String stringLabel = tmpStr[1];
			makeStringKeyList(stringCode, stringLabel);
		}
		// make java file method
	}
	
	public void makeStringKeyList(String stringKeyCode, String stringKeyLabel) {
		stringCodeList.add(stringKeyCode);
		stringLabelList.add(stringKeyLabel);
	}
	
	/**
	 * make HashMap(Key : key code, value : phoneme)
	 */
	public void makeKeyMap(String[] keyCode, int phoneme) {
		// ...?
	}
}
