package function;

import java.io.File;
import java.io.IOException;

import configuration.Configurator;


public class InstallAPKtoDevice {
	public static void install(){
		String[] args = {
				Configurator.sdk + File.separator + "platform-tools" + File.separator + "adb",
				"install",
				Configurator.apkPath + File.separator + Configurator.apkName
		};
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
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
