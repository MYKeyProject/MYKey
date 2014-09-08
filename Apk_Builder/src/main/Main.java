package main;

import java.io.File;

import configuration.Configurator;
import function.CompileJava;
import function.ConvertToDex;
import function.CopyAndroidSupport;
import function.InstallAPKtoDevice;
import function.MakeFinalAPK;
import function.MakeKey;
import function.MakeR;
import function.MakeSignedAPK;
import function.MakeUnsignedAPK;


public class Main {
	public static void main(String[] args) throws InterruptedException {
		boolean isADB = false;
		String targs[] = { "program", "-apkname", "mTest", "-apkpath",
				"c:\\app" };
		args = targs;
		for (int i = 1; i < args.length; i++) {
			if (args[i].equals("-project")) {
				Configurator.setProjectPath(args[++i]);
				if (!(new File(args[i]).exists())) {
					System.out.println(args[i] + "Directory is not exist");
				}
			} else if (args[i].equals("-sdk")) {
				Configurator.setSdkPath(args[++i]);
				if (!(new File(args[i]).exists())) {
					System.out.println(args[i] + "Directory is not exist");
				}
			} else if (args[i].equals("-jdk")) {
				Configurator.setJdkPath(args[++i]);
				if (!(new File(args[i]).exists())) {
					System.out.println(args[i] + "Directory is not exist");
				}
			} else if (args[i].equals("-keyname")) {
				Configurator.setKeyName(args[++i]);
			} else if (args[i].equals("-keypass")) {
				Configurator.setKeyPassword(args[++i]);
			} else if (args[i].equals("-apkname")) {
				Configurator.setAPKName(args[++i]);
			} else if (args[i].equals("-apkpath")) {
				Configurator.setApkPath(args[++i]);
				if (!(new File(args[i]).exists())) {
					System.out.println(args[i] + "Directory is not exist");
				}
			} else if (args[i].equals("-install")) {
				isADB = true;
			} else {
				System.out.println("Check Option( -help : show options )");
				return;
			}
		}

		Configurator
				.setProjectPath("C:\\Users\\bjs\\Desktop\\haha\\AutomaticSoftkeyboardMaker\\missless");
		CopyAndroidSupport.copy(Configurator.androidSupport, Configurator.lib
				+ File.separator + "android-support-v4.jar");

		MakeKey.makeKey();
		MakeR.makeR();
		CompileJava.compileStart(Configurator.project + File.separator + "gen");
		Thread.sleep(2000);
		CompileJava.compileStart(Configurator.project + File.separator + "src");
		ConvertToDex.convertToDex();
		MakeUnsignedAPK.make();
		MakeSignedAPK.make();
		MakeFinalAPK.make();
		if (isADB) {
			InstallAPKtoDevice.install();
		}
		System.exit(0);
	}
}
