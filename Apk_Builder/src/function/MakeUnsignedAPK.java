package function;

import java.io.File;
import java.io.IOException;

import main.ErrorFrame;
import configuration.Configurator;


public class MakeUnsignedAPK {
	public static void make() {
		try {
			String[] args = {
					Configurator.sdk + File.separator + "platform-tools"
							+ File.separator + "aapt",
					"package",
					"-v",
					"-f",
					"-M",
					Configurator.project + File.separator + "AndroidManifest.xml",
					"-S",
					Configurator.res,
					"-I",
					Configurator.sdk + File.separator + "platforms"
							+ File.separator + "android-15" + File.separator
							+ "android.jar", "-F",
					Configurator.bin + File.separator + Configurator.unsignedApkName,
					Configurator.bin };
			for (int i = 0; i < args.length; i++) {
				System.out.print(args[i] + " ");
			}
			System.out.println();
			Process p = Runtime.getRuntime().exec(args);	
			ProcessControlThread pct = new ProcessControlThread(p);
			pct.start();
			p.waitFor();
		} catch (IOException e) {
			new ErrorFrame("Copy Operation cannot execute");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// C:\sdk\platform-tools\aapt package -v -f -M
	// C:\Users\諛곗쭊�떇\Desktop\�봽濡쒖젥�듃\AutomaticSoftkeyboardMaker\android_�봽濡쒖젥�듃\AndroidManifest.xml
	// -S C:\Users\諛곗쭊�떇\Desktop\�봽濡쒖젥�듃\AutomaticSoftkeyboardMaker\android_�봽濡쒖젥�듃\res
	// -I C:\sdk\platforms\android-15\android.jar -F
	// C:\Users\諛곗쭊�떇\Desktop\�봽濡쒖젥�듃\AutomaticSoftkeyboardMaker\android_�봽濡쒖젥�듃\bin\test.unsigned.apk
	// C:\Users\諛곗쭊�떇\Desktop\�봽濡쒖젥�듃\AutomaticSoftkeyboardMaker\android_�봽濡쒖젥�듃\bin
}
