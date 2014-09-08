package configuration;

import java.io.File;
import java.nio.file.Paths;

public class Configurator {
	public static String currentPath = Paths.get("").toAbsolutePath().toString();
	public static String sdk = "C:" + File.separator + "sdk";
	public static String jdk = "C:" + File.separator + "Program Files" + File.separator + "Java" + File.separator + "jdk1.7.0_13" + File.separator + "bin";
	public static String project = "C:" + File.separator + "Users" + File.separator + "bjs" + File.separator + "Desktop" + File.separator + "haha" + File.separator + "AutomaticSoftkeyboardMaker" + File.separator + "android";
	public static String keyName = "TestKey";
	public static String keyPassword = "123456";
	public static String apkName = "test.apk";
	public static String unsignedApkName = "test.unsigned.apk";
	public static String signedApkName = "test.signed.apk";
	public static String androidSupport = sdk + File.separator + "extras" + File.separator + "android" + File.separator + "support" + File.separator + "v4" + File.separator + "android-support-v4.jar";
	public static String bin = project+File.separator+"bin";
	public static String res = project+File.separator+"res";
	public static String gen = project+File.separator+"gen";
	public static String lib = project+File.separator+"libs";
	public static String apkPath = bin;
	
	

	//keytool, aapt, dx, jarsigner, zipalign
	public static String keytool;
	public static String aapt;
	public static String dx;
	public static String jarsigner;
	public static String zipalign;
	
	
	
	
	public static void setSdkPath(String sdkPath){
		Configurator.sdk = sdkPath;
		Configurator.androidSupport = sdk + File.separator + "extras" + File.separator + "android" + File.separator + "support" + File.separator + "v4" + File.separator + "android-support-v4.jar";
	}
	public static void setJdkPath(String jdkPath){
		Configurator.jdk = jdkPath;
	}
	public static void setApkPath(String apkPath){
		Configurator.apkPath = apkPath;
	}
	public static void setAPKName(String apkName){
		Configurator.apkName = apkName + ".apk";
		Configurator.unsignedApkName = apkName + ".unsigned.apk";
		Configurator.signedApkName = apkName + ".signed.apk";
	}
	public static void setKeyName(String keyName){
		Configurator.keyName = keyName;
	}
	public static void setKeyPassword(String keyPassword){
		Configurator.keyPassword = keyPassword;
	}
	public static void setProjectPath(String projectPath){
		Configurator.project = projectPath;	
		Configurator.bin = project+File.separator+"bin";
		Configurator.res = project+File.separator+"res";
		Configurator.gen = project+File.separator+"gen";
		Configurator.lib = project+File.separator+"libs";
	}
}
