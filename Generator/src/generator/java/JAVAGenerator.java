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
		
	}
	
	public void makeStringKeyList() {
		// ..?
	}
	
	/**
	 * make HashMap(Key : key code, value : phoneme)
	 */
	public void makeKeyMap(String[] keyCode, int phoneme) {
		// ...?
	}
}
