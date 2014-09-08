package function;

import java.io.File;
import java.io.IOException;

import main.ErrorFrame;
import configuration.Configurator;


public class MakeFinalAPK {
	public static void make() {
		try {
			String[] args = {
					Configurator.sdk + File.separator + "tools" + File.separator
							+ "zipalign", "-v", "-f", "4",
					Configurator.bin + File.separator + Configurator.signedApkName,
					Configurator.apkPath + File.separator + Configurator.apkName };
			for (int i = 0; i < args.length; i++) {
				System.out.print(args[i] + " ");
			}
			System.out.println();
			Process p = Runtime.getRuntime().exec(args);		
			ProcessControlThread pct = new ProcessControlThread(p);
			pct.start();
			p.waitFor();
		} catch (InterruptedException | IOException e) {
			new ErrorFrame("Copy Operation cannot execute");
		}
	}
}
