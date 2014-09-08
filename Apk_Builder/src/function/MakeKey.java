package function;

import java.io.File;
import java.io.IOException;

import configuration.Configurator;


public class MakeKey {
	public static void makeKey() {
		String args[] = {
				Configurator.jdk + File.separator + "keytool",
				"-genkeypair",
				"-validity",
				"10000",
				"-dname",
				"\"CN=cn, OU=ou, O=o, L=l, S=s, C=c\"",
				"-keystore",
				Configurator.bin + File.separator + Configurator.keyName
						+ ".keystore", "-storepass", Configurator.keyPassword,
				"-keypass", Configurator.keyPassword, "-alias", Configurator.keyName,
				"-keyalg", "RSA", "-v" };
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
