package function;

import java.io.IOException;

import main.ErrorFrame;


public class CopyAndroidSupport {
	public static void copy(String src, String dest) {
		try {
			String[] args = { "CMD", "/C", "COPY", "/Y", src, dest };
			for (int i = 0; i < args.length; i++) {
				System.out.print(args[i] + " ");
			}
			System.out.println();
			Process p = Runtime.getRuntime().exec(args);
//			ProcessControlThread pct = new ProcessControlThread(p);
//			pct.start();
			p.getErrorStream().close();
			p.getInputStream().close();
			p.waitFor();
		} catch (InterruptedException | IOException e) {
			new ErrorFrame("Copy Operation cannot execute");
		}
	}
}
