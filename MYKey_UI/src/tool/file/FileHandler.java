package tool.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*

 Derby - Class org.apache.derby.iapi.util.PropertyUtil

 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to you under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

public class FileHandler {
	private static final int BUFFER_SIZE = 4096 * 4;

	/**
	 * Copy a directory and all of its contents.
	 */
	public static boolean copyDirectory(File from, File to) {
		return copyDirectory(from, to, (byte[]) null, (String[]) null);
	}

	public static boolean copyDirectory(String from, String to) {
		return copyDirectory(new File(from), new File(to));
	}

	/**
	 * @param filter
	 *            - array of names to not copy.
	 */
	public static boolean copyDirectory(File from, File to, byte[] buffer,
			String[] filter) {
		//
		// System.out.println("copyDirectory("+from+","+to+")");

		if (from == null)
			return false;
		if (!from.exists())
			return true;
		if (!from.isDirectory())
			return false;

		if (to.exists()) {
			// System.out.println(to + " exists");
			return false;
		}
		if (!to.mkdirs()) {
			// System.out.println("can't make" + to);
			return false;
		}

		String[] list = from.list();

		// Some JVMs return null for File.list() when the
		// directory is empty.
		if (list != null) {

			if (buffer == null)
				buffer = new byte[BUFFER_SIZE]; // reuse this buffer to copy
												// files

			nextFile: for (int i = 0; i < list.length; i++) {

				String fileName = list[i];

				if (filter != null) {
					for (int j = 0; j < filter.length; j++) {
						if (fileName.equals(filter[j]))
							continue nextFile;
					}
				}

				File entry = new File(from, fileName);

				// System.out.println("\tcopying entry " + entry);

				if (entry.isDirectory()) {
					if (!copyDirectory(entry, new File(to, fileName), buffer,
							filter))
						return false;
				} else {
					if (!copyFile(entry, new File(to, fileName), buffer))
						return false;
				}
			}
		}
		return true;
	}

	public static boolean copyFile(File from, File to) {
		return copyFile(from, to, (byte[]) null);
	}

	public static boolean copyFile(String from, String to) {
		return copyFile(new File(from), new File(to));
	}

	public static boolean copyFile(File from, File to, byte[] buf) {
		if (buf == null)
			buf = new byte[BUFFER_SIZE];

		//
		// System.out.println("Copy file ("+from+","+to+")");
		FileInputStream from_s = null;
		FileOutputStream to_s = null;

		try {
			from_s = new FileInputStream(from);
			to_s = new FileOutputStream(to);

			for (int bytesRead = from_s.read(buf); bytesRead != -1; bytesRead = from_s
					.read(buf))
				to_s.write(buf, 0, bytesRead);

			from_s.close();
			from_s = null;

			to_s.getFD().sync(); // RESOLVE: sync or no sync?
			to_s.close();
			to_s = null;
		} catch (IOException ioe) {
			return false;
		} finally {
			if (from_s != null) {
				try {
					from_s.close();
				} catch (IOException ioe) {
				}
			}
			if (to_s != null) {
				try {
					to_s.close();
				} catch (IOException ioe) {
				}
			}
		}

		return true;
	}
	public static void deleteRecursive(File path) {
		if(!path.exists()){
			return;
		}
		File[] c = path.listFiles();
		System.out.println("Cleaning out folder:" + path.toString());
		for (File file : c) {
			if (file.isDirectory()) {
				System.out.println("Deleting file:" + file.toString());
				deleteRecursive(file);
				file.delete();
			} else {
				file.delete();
			}
		}
		path.delete();
	}

	public static void deleteRecursive(String path) {
		File root = new File(path);
		deleteRecursive(root);
	}
	
	public static File findFileWithoutExtension(String directory, String fileName){
		File dir = new File(directory);
		if(!dir.isDirectory()){
			return null;
		}else{
			File[] c = dir.listFiles();
			for(File file : c){
				if(file.getName().startsWith(fileName)){
					return file;
				}
			}
			return null;
		}
	}
	  public static String findExtensionName(String path) { 
	        
	        String fullPath = path;
	        
	        int firstIndex = 0;
	        
	        while(fullPath.indexOf('.') != -1) {
	            firstIndex = fullPath.indexOf('.');
	            fullPath = fullPath.substring(firstIndex+1);
	        }
	        
	        return fullPath;
	    }
}
