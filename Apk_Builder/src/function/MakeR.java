package function;

import java.io.File;
import java.io.IOException;

import configuration.Configurator;


public class MakeR {
	public static void makeR() {
		String[] args = {
				Configurator.sdk + File.separator + "platform-tools"
						+ File.separator + "aapt",
				"p",
				"-v",
				"-f",
				"-m",
				"-M",
				Configurator.project + File.separator + "AndroidManifest.xml",
				"-S",
				Configurator.res,
				"-I",
				Configurator.sdk + File.separator + "platforms" + File.separator
						+ "android-15" + File.separator + "android.jar", "-J",
				Configurator.project + File.separator + "gen",
				"--generate-dependencies" };
		try {
			for (int i = 0; i < args.length; i++) {
				System.out.print(args[i] + " ");
			}
			System.out.println();
			Process p;
			p = Runtime.getRuntime().exec(args);
			
//			ProcessControlThread pct = new ProcessControlThread(p);
//			pct.start();
			p.getErrorStream().close();
			p.getInputStream().close();
			p.waitFor();
//			pct.interrupt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
