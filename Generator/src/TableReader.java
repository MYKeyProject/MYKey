import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TableReader {
	FileInputStream tableFile;
	BufferedReader tableReader;
	String xmlPath; // XML file path
	String javaPath; // java file path

	/**
	 * Table Reader Constructor Set and Initialize variable
	 * 
	 * @param tablePath
	 * @param xmlPath
	 * @param javaPath
	 */
	public TableReader(String tablePath, String xmlPath, String javaPath) {
		try {
			if ((tableFile = new FileInputStream(tablePath)) == null) {
				// Error : tableFile not Found
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
		String layoutInfo = "";
		String stringKeyInfo = "";
		String keyInfo = "";
		boolean isStringKeyInfo = false;
		boolean isLayoutInfo = false;
		boolean isPhonemeInfo=false;
		
		// read table
		try {
			while((readLine = tableReader.readLine()) != null) {
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
