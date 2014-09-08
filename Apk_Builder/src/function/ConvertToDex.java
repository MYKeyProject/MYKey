package function;

import java.io.File;
import java.io.IOException;

import main.ErrorFrame;
import configuration.Configurator;


public class ConvertToDex {
	public static void convertToDex() {
		try {
			String[] args = {
					Configurator.sdk + File.separator + "platform-tools"
							+ File.separator + "dx.bat",
					"--dex",
					"--verbose",
					"--output=" + Configurator.bin + File.separator
							+ "classes.dex",
					Configurator.bin + File.separator + "classes",
					Configurator.lib };
			for (int i = 0; i < args.length; i++) {
				System.out.print(args[i] + " ");
			}
			System.out.println();
			Process p = Runtime.getRuntime().exec(args);
			ProcessControlThread pct = new ProcessControlThread(p);
			pct.start();
			p.waitFor();
		} catch (IOException e) {
			new ErrorFrame("Program has problem when ConvertToDex");
		} catch (InterruptedException e) {

		}
	}
}

// C:\sdk\platform-tools\dx --dex --verbose
// --output=C:\Users\諛곗쭊�떇\Desktop\�봽濡쒖젥�듃\AutomaticSoftkeyboardMaker\android_�봽濡쒖젥�듃\bin\classes.dex
// C:\Users\諛곗쭊�떇\Desktop\�봽濡쒖젥�듃\AutomaticSoftkeyboardMaker\android_�봽濡쒖젥�듃\bin\classes
// C:\Users\諛곗쭊�떇\Desktop\�봽濡쒖젥�듃\AutomaticSoftkeyboardMaker\android_�봽濡쒖젥�듃\libs
