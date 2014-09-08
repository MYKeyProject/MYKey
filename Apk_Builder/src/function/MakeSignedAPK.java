package function;

import java.io.File;
import java.io.IOException;

import main.ErrorFrame;
import configuration.Configurator;


public class MakeSignedAPK {
	public static void make() {
		try {
			String[] args = {
					Configurator.jdk + File.separator + "jarsigner",
					"-verbose",
					"-keystore",
					Configurator.bin + File.separator + Configurator.keyName
							+ ".keystore", "-storepass", Configurator.keyPassword,
					"-keypass", Configurator.keyPassword, "-signedjar",
					Configurator.bin + File.separator + Configurator.signedApkName,
					Configurator.bin + File.separator + Configurator.unsignedApkName,
					Configurator.keyName, };
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

// C:\Program Files\Java\jdk1.7.0_13\bin\jarsigner -verbose -keystore
// C:\Users\諛곗쭊�떇\Desktop\�봽濡쒖젥�듃\AutomaticSoftkeyboardMaker\android_�봽濡쒖젥�듃\bin\TestKey.keystore
// -storepass 123456 -keypass 123456 -signedjar
// C:\Users\諛곗쭊�떇\Desktop\�봽濡쒖젥�듃\AutomaticSoftkeyboardMaker\android_�봽濡쒖젥�듃\bin\test.signed.apk
// C:\Users\諛곗쭊�떇\Desktop\�봽濡쒖젥�듃\AutomaticSoftkeyboardMaker\android_�봽濡쒖젥�듃\bin\test.unsigned.apk
// TestKey