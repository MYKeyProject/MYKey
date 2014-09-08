package generator.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class JAVAGenerator {
	FileOutputStream javaFile;
	BufferedWriter javaFileWriter;
	
	/**
	 * JAVAGenerator Constructor.
	 */
	public JAVAGenerator(String javaPath) {
		try {
			javaFile = new FileOutputStream(javaPath + File.separator
					+ "KeyMap.java");
			javaFileWriter = new BufferedWriter(new OutputStreamWriter(
					javaFile, "UTF-8"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readPhonemeTable(String phonemeInfoString, String stringKeyInfo) {
		
	}
}
