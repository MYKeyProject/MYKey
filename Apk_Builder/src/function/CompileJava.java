package function;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import configuration.Configurator;


public class CompileJava {
	public static void compileStart(String src) {
		ArrayList<Process> processList = new ArrayList<Process>();
		ArrayList<String> list = (ArrayList) getAllInnerDirectories(src);
		String command[] = {
				Configurator.jdk + File.separator + "javac",
//				"-verbose",
				"-source",
				"1.6",
				"-target",
				"1.6",
				"-d",
				Configurator.project + File.separator + "bin" + File.separator
						+ "classes",
				"-classpath",
				Configurator.sdk + File.separator + "platforms" + File.separator
						+ "android-15" + File.separator + "android.jar;"
						+ Configurator.project + File.separator + "bin"
						+ File.separator + "classes", "-sourcepath", src};

		int length = command.length;
		String args[] = new String[length + list.size()];
		for (int i = 0; i < args.length; i++) {
			if (i < length) {
				args[i] = command[i];
			} else {
				args[i] = list.get(i - length);
			}
		}

		Process p;
		Iterator it = list.iterator();
//		while (it.hasNext()) {
			String path = (String) it.next();
			args[length - 1] = path;

			try {
				for (int i = 0; i < args.length; i++) {
					System.out.print(args[i] + " ");
				}
				System.out.println();
				p = Runtime.getRuntime().exec(args);
//				ProcessControlThread pct = new ProcessControlThread(
//						p.getErrorStream(), p.getOutputStream());
//				pct.start();
//				pct = new ProcessControlThread(p.getErrorStream(),
//						p.getOutputStream());
//				pct.start();
				p.getErrorStream().close();
				p.getInputStream().close();
				processList.add(p);
			} catch (IOException e) {
				e.printStackTrace();
			}

//		}

		for (int i = 0; i < processList.size(); i++) {
			try {
				Process tmpProcess = processList.get(i);
				tmpProcess.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static ArrayList<String> getAllInnerDirectories(String src) {
		Stack<File> st = new Stack<File>();
		ArrayList<String> list = new ArrayList<String>();
		File dirs[];
		File file = new File(src);
		boolean hasJavaFile;

		System.out.println();
		st.push(file);
		while (!st.empty()) {
			file = st.pop();
			dirs = file.listFiles();
			hasJavaFile = false;
			for (File dir : dirs) {
				if (dir.isFile() && !hasJavaFile) {
					String fileName = dir.getName();
					if (!fileName.substring(fileName.lastIndexOf(".") + 1,
							fileName.length()).equals("java")) {
						continue;
					}
					hasJavaFile = true;
				} else if (dir.isDirectory()) {
					st.push(dir);
				}
			}
			if (hasJavaFile) {
				list.add(file.getAbsolutePath() + File.separator + "*.java");
			}
		}
		return list;
	}

}

/*
 * javac -verbose -source 1.6 -target 1.6 -d %PROJECT_PATH%\bin\classes
 * -classpath %SDK_PATH%
 * 
 * \platforms\android-15/android.jar;%PROJECT_PATH%\bin\classes -sourcepath
 * %PROJECT_PATH%
 * 
 * \gen %PROJECT_PATH%\gen\com\example\androidpractice\*.java
 */

