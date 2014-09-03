package main;
import generator.tablereader.TableReader;

public class Main {
	public static void main(String[] args) {
		String tablePath = "table"; // table file name is 'table'
		String xmlPath = "."; // XML file path start with '.'
		String javaPath = "."; // Java file path start with '.'
		
		// call TableReader Constructor
		new TableReader(tablePath, xmlPath, javaPath);
	}
}
