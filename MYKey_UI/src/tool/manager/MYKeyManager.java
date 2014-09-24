package tool.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

import tool.file.FileHandler;
import tool.file.ProcessControlThread;
import tool.frame.MFrame;
import tool.key.KeyButton;
import tool.key.KeyInfo;
import tool.key.KeySequence;
import tool.key.info.SpecialKeyInfo;
import tool.panel.display.CompositionPanel;
import tool.panel.display.DevicePanel;
import tool.panel.keysetting.DesktopPane;
import tool.panel.keysetting.KeySettingMainPanel;
import tool.panel.phonemesetting.DisplayPhonemePanel;
import tool.tempUse.SequenceMainPanel;
import tool.tempUse.SequencePanel;

public class MYKeyManager {
	public static int KOREAN_PHONEME_MAX_IDX = 3999;
	public static int KOREAN_PHONEME_MIN_IDX = 3000;
	private static MYKeyManager me = null;

	private KeyInfo pressedKeyInfo = null;
	private KeySequence keySequence = null;
	private DesktopPane desktopPane = null;
	private Vector<KeyInfo> allKeyInfos = new Vector<KeyInfo>();
	private Vector<KeyButton> allKeyButtons = new Vector<KeyButton>();
	private Vector<KeyButton> allShiftKeyButtons = new Vector<KeyButton>();
	private Vector<KeySequence> allKeySequences = new Vector<KeySequence>();

	private static CompositionPanel originalCompositionPanel,
			shiftCompositionPanel;
	private DisplayPhonemePanel displayPhonemePanel;
	private int consonant, vowel;
	private String loadFilePath = null;
	private String currentDirectoryPath = Paths.get("").toAbsolutePath()
			.toString();
	private String baseProjectPath = currentDirectoryPath + File.separator
			+ "project" + File.separator + "baseProject";
	private String targetProjectPath = currentDirectoryPath + File.separator
			+ "project" + File.separator + "targetProject";
	private String builderPath = currentDirectoryPath + File.separator + "libs"
			+ File.separator + "builder.jar";
	private String jdkPath = currentDirectoryPath + File.separator + "libs"
			+ File.separator + "jdk6u26" + File.separator + "bin";
	private String sdkPath = currentDirectoryPath + File.separator + "libs"
			+ File.separator + "sdk";
	private String jrePath = currentDirectoryPath + File.separator + "libs"
			+ File.separator + "jdk6u26" + File.separator +"jre" + File.separator + "bin";

	private SequenceMainPanel sequenceMainPanel = null;
	private SequencePanel sequencePanel = null;

	public static MYKeyManager getManager() {
		if (me == null) {
			me = new MYKeyManager();
		}
		return me;
	}

	public Vector<KeyButton> getOriginalKeyButtons() {
		return allKeyButtons;
	}

	public Vector<KeyButton> getShiftKeyButtons() {
		return allShiftKeyButtons;
	}

	public Vector<KeyInfo> getKeyInfos() {
		return allKeyInfos;
	}

	private MYKeyManager() {
	}

	public void setSequenceMain(SequenceMainPanel ss) {
		this.sequenceMainPanel = ss;
	}

	public void setSequencePanel(SequencePanel sp) {
		this.sequencePanel = sp;
	}

	public void setSequenceKeyInfo(KeyInfo ki) {
		sequencePanel.setKeyInfo(ki);
	}

	public void initComposing() {
		loadFilePath = null;
		int size = allKeyButtons.size();
		for (int i = 0; i < size; i++) {
			allKeyButtons.get(0).removeOnly();
		}
		size = allShiftKeyButtons.size();
		for (int i = 0; i < size; i++) {
			allShiftKeyButtons.get(0).removeOnly();
		}
		sequenceMainPanel.init();
		if(pressedKeyInfo != null){
			pressedKeyInfo.cancelClick();
			setPressedKeyInfo(null);
		}
		setKeySeqnence(null);
	}

	public void setMatrix(int row, int col) {
		CompositionPanel.setMatrix(row, col);
		originalCompositionPanel.repaint();
		shiftCompositionPanel.repaint();
	}

	public void setDisplayPhonemePanel(DisplayPhonemePanel panel) {
		this.displayPhonemePanel = panel;
	}

	public void addKeyInfo(KeyInfo ki) {
		allKeyInfos.add(ki);
	}

	public void addKeyButton(KeyButton btn) {
		if (btn.getParent().isOriginalPanel()) {
			allKeyButtons.add(btn);
		} else {
			allShiftKeyButtons.add(btn);
		}
	}

	public void removeKeyButton(KeyButton btn) {
		Iterator<KeySequence> it = allKeySequences.iterator();
		while (it.hasNext()) {
			KeySequence ks = it.next();
			if (ks.hasKeyButton(btn)) {
				ks.remove();
			}
		}

		if (btn.getParent().isOriginalPanel()) {
			allKeyButtons.remove(btn);
		} else {
			allShiftKeyButtons.remove(btn);
		}
	}

	public void setDesktopPane(DesktopPane pane) {
		desktopPane = pane;
	}

	public void setOriginalCompositionPanel(CompositionPanel panel) {
		originalCompositionPanel = panel;
	}

	public void setShiftCompositionPanel(CompositionPanel panel) {
		shiftCompositionPanel = panel;
	}

	public void showKeySetting(KeySettingMainPanel panel) {
		desktopPane.addKeySettingPanel(panel);
	}

	public void setPressedKeyInfo(KeyInfo ki) {
		if (pressedKeyInfo != null) {
			pressedKeyInfo.cancelClick();
		}
		pressedKeyInfo = ki;
		if (pressedKeyInfo == null) {
			sequenceMainPanel.setStartEnable(false);
			sequenceMainPanel.setEndEnable(false);
			displayPhonemePanel.initText();
		} else {
			if (pressedKeyInfo instanceof SpecialKeyInfo) {
				sequenceMainPanel.setStartEnable(false);
			} else {
				sequenceMainPanel.setStartEnable(true);
			}
			sequenceMainPanel.setEndEnable(false);
			displayPhonemePanel.setText(pressedKeyInfo.getLabel());
		}
	}

	public KeyInfo getPressedKeyInfo() {
		return pressedKeyInfo;
	}

	public void setKeySeqnence(KeySequence ks) {
		keySequence = ks;
	}

	public KeySequence getKeySequence() {
		return keySequence;
	}

	public Vector<KeySequence> getAllKeySequences() {
		return allKeySequences;
	}

	public void addKeySequence(KeySequence ks) {
		this.allKeySequences.add(ks);
	}

	public void removeKeySequence(KeySequence ks) {
		this.allKeySequences.remove(ks);
	}

	public String getLoadFilePath() {
		return loadFilePath;
	}

	public synchronized void showContainedButton(KeyInfo info) {
		for (int i = 0; i < allKeyButtons.size(); i++) {
			KeyButton btn = allKeyButtons.get(i);
			if (btn.hasKeyInfo(info)) {
				btn.show();
			}
		}
		for (int i = 0; i < allShiftKeyButtons.size(); i++) {
			KeyButton btn = allShiftKeyButtons.get(i);
			if (btn.hasKeyInfo(info)) {
				btn.show();
			}
		}
	}

	public synchronized void finishShowingContainedButton(KeyInfo info) {
		for (int i = 0; i < allKeyButtons.size(); i++) {
			KeyButton btn = allKeyButtons.get(i);
			if (btn.hasKeyInfo(info)) {
				btn.finishShow();
			}
		}
		for (int i = 0; i < allShiftKeyButtons.size(); i++) {
			KeyButton btn = allShiftKeyButtons.get(i);
			if (btn.hasKeyInfo(info)) {
				btn.finishShow();
			}
		}
	}

	public boolean checkPossibleToFinishComposing() {
		for (int i = 0; i < allKeyInfos.size(); i++) {
			KeyInfo ki = allKeyInfos.get(i);
			if ((ki.isPrimary()) && (ki.getNum() <= 0)) {
				return false;
			}
		}
		return true;
	}

	public KeyButton findKeyButtonFromKeycode(int keyCode) {
		for (int i = 0; i < allKeyButtons.size(); i++) {
			if (allKeyButtons.get(i).getKeyCode() == keyCode) {
				return allKeyButtons.get(i);
			}
		}
		for (int i = 0; i < allShiftKeyButtons.size(); i++) {
			if (allShiftKeyButtons.get(i).getKeyCode() == keyCode) {
				return allShiftKeyButtons.get(i);
			}
		}
		return null;
	}

	public KeyInfo findKeyInfoFromPhoneme(int phoneme) {
		for (int i = 0; i < allKeyInfos.size(); i++) {
			if (allKeyInfos.get(i).getphoneme() == phoneme) {
				return allKeyInfos.get(i);
			}
		}
		return null;
	}

	public KeyInfo findKeyInfoFromLabel(String label) {
		for (int i = 0; i < allKeyInfos.size(); i++) {
			if (label.equals(allKeyInfos.get(i).getLabel())) {
				return allKeyInfos.get(i);
			}
		}
		return null;
	}

	public Vector<KeyLocation> findKeyInfoLocation(KeyInfo ki) {
		Vector<KeyLocation> vec = new Vector<KeyLocation>();
		KeyButton kb;
		Vector<Integer> locations;
		for (int i = 0; i < allKeyButtons.size(); i++) {
			kb = allKeyButtons.get(i);
			locations = kb.getKeyInfoLocations(ki);
			for (int j = 0; j < locations.size(); j++) {
				vec.add(new KeyLocation(kb, locations.get(j)));
			}
		}
		for (int i = 0; i < allShiftKeyButtons.size(); i++) {
			kb = allShiftKeyButtons.get(i);
			locations = kb.getKeyInfoLocations(ki);
			for (int j = 0; j < locations.size(); j++) {
				vec.add(new KeyLocation(kb, locations.get(j)));
			}
		}
		return vec;
	}

	public void sortKeyButton(Vector<KeyButton> vec) {
		Collections.sort(vec, new MatrixCompare());
	}

	public void generateXmlAndJava(String tablePath, String projectPath) {
		String xmlPath = projectPath + File.separator + "res" + File.separator
				+ "xml";
		String javaPath = projectPath + File.separator + "src" + File.separator
				+ "com" + File.separator + "example" + File.separator
				+ "android" + File.separator + "softkeyboard";
//		new TableReader(tablePath, xmlPath, javaPath);
// have to remove comment after merge with generator
	}

	public String saveTables(String path) {
		if (path == null) {
			return null;
		}
		copyAllKeyButtonImageToMYKey();
		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			consonant = 3000;
			vowel = 3500;
			makeKeyTable(allKeyButtons, bw);
			makeKeyTable(allShiftKeyButtons, bw);
			makePhonemeTable(allKeyInfos, bw);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			return null;
		}
		loadFilePath = path;
		return path;
	}

	private void makeKeyTable(Vector<KeyButton> vec, BufferedWriter bw) {
		final int FALSE = 0;
		final int TRUE = 1;
		final int LEFT_EDGE_FLAG = 2;
		final int RIGHT_EDGE_FLAG = 3;

		int prevYPos = -1;
		int keyCode, keyWidth, keyHeight, xPos, yPos;
		String keyLabel, keyIcon;
		int keyEdgeFlags, isRepeatable;
		sortKeyButton(vec);
		try {
			bw.write("MaxRow");
			bw.newLine();
			bw.write(CompositionPanel.getRow() + "");
			bw.newLine();
			bw.write("MaxCol");
			bw.newLine();
			bw.write(CompositionPanel.getColumn() + "");
			bw.newLine();
			bw.write("VerticalRate");
			bw.newLine();
			bw.write((int) (DevicePanel.getVerticalRate()) + "");
			bw.newLine();
			bw.write("LayoutTable");
			bw.newLine();
			for (int i = 0; i < vec.size(); i++) {
				KeyButton btn = vec.get(i);
				switch (btn.getKeyStatus()) {
				case KeyInfo.SPECIAL_KEY:
					keyCode = btn.getSpecialPhoneme();
					break;
				case KeyInfo.VOWEL_KEY:
					keyCode = vowel++;
					break;
				case KeyInfo.CONSONANT_KEY:
					keyCode = consonant++;
					break;
				case KeyInfo.EMPTY_KEY:
					keyCode = KeyInfo.EMPTY_KEY;
					break;
				default:
					keyCode = 0;
					break;
				}
				keyWidth = btn.getColCellNum();
				keyHeight = btn.getRowCellNum();
				xPos = btn.getStartCol();
				yPos = btn.getStartRow();
				if (btn.isTextButton()) {
					if (btn.getLabelName() != null) {
						keyLabel = btn.getLabelName();
					} else if (btn.getKeyInfos().size() != 0) {
						String str = btn.getKeyInfos().get(0).getLabel();
						for (int j = 1; j < btn.getKeyInfos().size(); j++) {
							str += "," + btn.getKeyInfos().get(j).getLabel();
						}
						keyLabel = str;
					} else {
						keyLabel = "!no";
					}
					keyIcon = "!no";
				} else {
					keyLabel = "!no";
					if (btn.getImagePath() != null) {
						keyIcon = getImagePathOfTable(btn.getImagePath());
					} else {
						keyIcon = "!no";
					}
				}
				if(keyLabel.equals("!no") && keyIcon.equals("!no")){
					keyLabel = " ";
				}
				keyEdgeFlags = 0;
				if (prevYPos != yPos) {
					keyEdgeFlags = LEFT_EDGE_FLAG;
				} else if ((i == (vec.size() - 1))
						|| (vec.get(i + 1).getY() != btn.getY())) {
					keyEdgeFlags = RIGHT_EDGE_FLAG;
				}
				prevYPos = yPos;
				if (btn.isRepeatable()) {
					isRepeatable = TRUE;
				} else {
					isRepeatable = FALSE;
				}
				btn.setKeyCode(keyCode);
				bw.write(keyCode + "\t" + keyWidth + "\t" + keyHeight + "\t"
						+ xPos + "\t" + yPos + "\t" + keyLabel + "\t" + keyIcon
						+ "\t" + keyEdgeFlags + "\t" + isRepeatable);
				bw.newLine();
			}
		} catch (Exception e) {
			return;
		}
	}

	private void makePhonemeTable(Vector<KeyInfo> vec, BufferedWriter bw) {
		try {
			bw.write("PhonemeTable");
			bw.newLine();
			for (int i = 0; i < vec.size(); i++) {
				KeyInfo ki = vec.get(i);
				if (ki instanceof SpecialKeyInfo) {
					continue;
				}
				bw.write(ki.getphoneme() + "");
				Vector<KeyLocation> locations = findKeyInfoLocation(ki);
				for (int j = 0; j < locations.size(); j++) {
					bw.write("\t");
					for (int k = 0; k < locations.get(j).getLocation(); k++) {
						if (k != 0) {
							bw.write("-");
						}
						bw.write(locations.get(j).getKeyCode() + "");
					}
				}
				for (int j = 0; j < ki.getKeySequences().size(); j++) {
					bw.write("\t");
					KeySequence ks = ki.getKeySequences().get(j);
					for (int k = 0; k < ks.getKeyButtons().size(); k++) {
						if (k != 0) {
							bw.write("-");
						}
						bw.write(ks.getKeyButtons().get(k).getKeyCode() + "");
					}
				}
				bw.newLine();
			}
		} catch (Exception e) {

		}
	}

	public void loadTables(String path) {
		try {
			initComposing();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "UTF-8"));
			br.readLine();
			int row = Integer.parseInt(br.readLine());
			br.readLine();
			int col = Integer.parseInt(br.readLine());
			br.readLine();
			int verticalRate = Integer.parseInt(br.readLine());
			String layoutstart = br.readLine();
			DevicePanel.setCompositionVerticalRate(verticalRate);
			CompositionPanel.setMatrix(row, col);
			String str;
			CompositionPanel panel = originalCompositionPanel;
			System.out.println("Width, height : "
					+ originalCompositionPanel.getWidth() + " , "
					+ originalCompositionPanel.getHeight());
			while ((str = br.readLine()) != null) {
				if (str.equals("LayoutTable")) {
					panel = shiftCompositionPanel;
				} else if (str.equals("PhonemeTable")) {
					break;
				}
				String[] split = str.split("\t");
				if (split.length != 9) {
					continue;
				}
				int keyCode = Integer.parseInt(split[0]);
				int colCellNum = Integer.parseInt(split[1]);
				int rowCellNum = Integer.parseInt(split[2]);
				int startCol = Integer.parseInt(split[3]);
				int startRow = Integer.parseInt(split[4]);
				String text = split[5];
				if (text.equals("!no")) {
					text = null;
				}
				String imagePath = split[6];
				if (imagePath.equals("!no")) {
					imagePath = null;
				}else{
					imagePath = getImagePathOfMYKey(imagePath);
				}
				KeyButton btn = panel.loadKeyButton(startRow, startCol,
						rowCellNum, colCellNum, keyCode, text, imagePath);
				if (keyCode < KOREAN_PHONEME_MIN_IDX
						|| keyCode > KOREAN_PHONEME_MAX_IDX) {
					KeyInfo ki = findKeyInfoFromPhoneme(keyCode);
					if (ki != null) {
						btn.addKeyInfo(ki);
					}
				}
			}

			while ((str = br.readLine()) != null) {
				String[] split = str.split("\t");
				if (split.length <= 1) {
					continue;
				}
				int phoneme = Integer.parseInt(split[0]);
				KeyInfo ki = findKeyInfoFromPhoneme(phoneme);
				if (ki == null) {
					continue;
				}
				for (int i = 1; i < split.length; i++) {
					if (split[i].length() <= 0) {
						continue;
					}
					String[] split2 = split[i].split("-");
					boolean isCombine = false;
					for (int j = 1; j < split2.length; j++) {
						if (!split2[j].equals(split2[0])) {
							isCombine = true;
							break;
						}
					}
					if (!isCombine) {
						KeyButton btn = findKeyButtonFromKeycode(Integer
								.parseInt(split2[0]));
						btn.saveKeyInfo(ki, split2.length);
					} else {
						KeySequence ks = new KeySequence(ki);
						for (int k = 0; k < split2.length; k++) {
							ks.appendKeyButtons(findKeyButtonFromKeycode(Integer
									.parseInt(split2[k])));
						}
						ks.update();
					}
				}
			}
			for (int i = 0; i < allKeyButtons.size(); i++) {
				allKeyButtons.get(i).updateSavedKeyInfo();
			}
		} catch (Exception e) {
			// /////////////////////////////////////////////////////////////////////////////error
		}
		loadFilePath = path;
		originalCompositionPanel.repaint();
	}

	public void copyBaseProject(String src, String dst) {
		FileHandler.deleteRecursive(dst);
		FileHandler.copyDirectory(src, dst);
	}

	public void copyAllKeyButtonImageToMYKey() {
		int num = 0;
		File imageNum = new File(currentDirectoryPath + File.separator + "res"
				+ File.separator + "img" + File.separator + "imagenum");

		if (imageNum.exists()) {
			FileReader fr;
			try {
				fr = new FileReader(imageNum);
				BufferedReader br = new BufferedReader(fr);
				num = Integer.parseInt(br.readLine());
				br.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < allKeyButtons.size(); i++) {
			KeyButton btn = allKeyButtons.get(i);
			if (btn.isTextButton()) {
				continue;
			}
			File f = new File(btn.getImagePath());
			if (!f.exists()) {
				btn.setImagePath(null);
				btn.setTextKey();
				continue;
			} else if (!f.getParent().equals(
					currentDirectoryPath + File.separator + "res"
							+ File.separator + "img")) {
				num++;				
				String fileExtension = FileHandler.findExtensionName(f.getName());
				String imageFileName = "key" + num + "." + fileExtension;

				// File copy to MYKey Resource
				FileHandler.copyFile(btn.getImagePath(), currentDirectoryPath
						+ File.separator + "res" + File.separator + "img"
						+ File.separator + imageFileName);

				btn.setImagePath(currentDirectoryPath + File.separator + "res"
						+ File.separator + "img" + File.separator
						+ imageFileName);
			} else {
				String imageFileName = f.getName();
				FileHandler.copyFile(btn.getImagePath(), targetProjectPath
						+ File.separator + "res" + File.separator
						+ "drawable-hpdi" + File.separator + imageFileName);
			}
		}
		for (int i = 0; i < allShiftKeyButtons.size(); i++) {
			KeyButton btn = allShiftKeyButtons.get(i);
			if (btn.isTextButton()) {
				continue;
			}
			File f = new File(btn.getImagePath());
			if (!f.exists()) {
				btn.setImagePath(null);
				btn.setTextKey();
				continue;
			} else if (!f.getParent().equals(
					currentDirectoryPath + File.separator + "res"
							+ File.separator)) {
				num++;
				String fileExtension = FileHandler.findExtensionName(f.getName());
				String imageFileName = "key" + num + "." + fileExtension;

				// File copy to MYKey Resource
				FileHandler.copyFile(btn.getImagePath(), currentDirectoryPath
						+ File.separator + "res" + File.separator + "img"
						+ File.separator + imageFileName);

				btn.setImagePath(currentDirectoryPath + File.separator + "res"
						+ File.separator + "img" + File.separator
						+ imageFileName);
			} else {
				String imageFileName = f.getName();
				FileHandler.copyFile(btn.getImagePath(), targetProjectPath
						+ File.separator + "res" + File.separator
						+ "drawable-hpdi" + File.separator + imageFileName);
			}
		}

		try {
			FileWriter fw = new FileWriter(imageNum);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Integer.toString(num));
			bw.flush();
			bw.close();
		} catch (Exception e) {

		}
	}
	
	public void copyAllKeyButtonImageToProject() {
		for (int i = 0; i < allKeyButtons.size(); i++) {
			KeyButton btn = allKeyButtons.get(i);
			if (btn.isTextButton()) {
				continue;
			}
			File f = new File(btn.getImagePath());
			if (!f.exists()) {
				btn.setImagePath(null);
				btn.setTextKey();
				continue;
			} else {
				// File copy to SoftKeyboard Project
				FileHandler.copyFile(btn.getImagePath(), targetProjectPath
						+ File.separator + "res" + File.separator
						+ "drawable-hdpi" + File.separator + f.getName());
			}
		}
		for (int i = 0; i < allShiftKeyButtons.size(); i++) {
			KeyButton btn = allShiftKeyButtons.get(i);
			if (btn.isTextButton()) {
				continue;
			}
			File f = new File(btn.getImagePath());
			if (!f.exists()) {
				btn.setImagePath(null);
				btn.setTextKey();
				continue;
			} else {
				// File copy to SoftKeyboard Project
				FileHandler.copyFile(btn.getImagePath(), targetProjectPath
						+ File.separator + "res" + File.separator
						+ "drawable-hpdi" + File.separator + f.getName());
			}
		}
	}

	public String getImagePathOfTable(String imagePath) { // use it when make
															// ImagePath
		File f = new File(imagePath);
		if (f.exists()) {
			String tableImageName = f.getName();
			tableImageName = tableImageName.substring(0,tableImageName.lastIndexOf("."));
			return "@drawable/" + tableImageName;
		} else {
			return "!no";
		}
	}

	public String getImagePathOfMYKey(String imagePath) {
		String[] str = imagePath.split("/");
		if (str.length != 2 || !str[0].equals("@drawable")) {
			return null;
		} else {
			File f = FileHandler.findFileWithoutExtension(currentDirectoryPath
					+ File.separator + "res" + File.separator + "img", str[1]);
			if (f == null) {
				return null;
			} else {
				return f.getAbsolutePath();
			}
		}
	}

	public void makeAPKofCurrentWorkingSpace(String apkPath, String apkName,
			boolean install) {
		String tempTablePath = currentDirectoryPath + File.separator
				+ "tmpTable";
		tempTablePath += ".myk";
		saveTables(tempTablePath);
		makeAPKofTable(apkPath, apkName, tempTablePath, install);
	}

	public void makeAPKofTable(String apkPath, String apkName,
			String tablePath, boolean install) {
		if (apkPath == null || tablePath == null) {
			return;
		}
		copyBaseProject(baseProjectPath, targetProjectPath);
		copyAllKeyButtonImageToProject();
		generateXmlAndJava(loadFilePath, targetProjectPath);

		try {
			ProcessBuilder pb;
			if (!install) {
				String args[] = { jrePath + File.separator + "java", "-jar",
						builderPath, "-project", targetProjectPath, "-sdk",
						sdkPath, "-jdk", jdkPath, "-keyname", "testKey",
						"-keypass", "123456", "-apkname", apkName, "-apkpath", apkPath, "-keytool",jrePath + File.separator + "keytool"};
				for (int i = 0; i < args.length; i++) {
					System.out.println("\"" + args[i] + "\",");
				}
				pb = new ProcessBuilder(args);
			} else {
				String args[] = { jrePath + File.separator + "java", "-jar",
						builderPath, "-project", targetProjectPath, "-sdk",
						sdkPath, "-jdk", jdkPath, "-keyname", "testKey",
						"-keypass", "123456", "-apkname", apkName, "-apkpath",
						apkPath, "-install" , "-keytool",jrePath + File.separator + "keytool"};
				for (int i = 0; i < args.length; i++) {
					System.out.println("\"" + args[i] + "\",");
				}
				pb = new ProcessBuilder(args);
			}
			Process p = pb.start();
			new ProcessControlThread(p);
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// String targs[] = { "buildername",
		// "-project", "D:\\MYKey\\project\\targetProject",
		// "-sdk","D:\\MYKey\\developtool\\sdk",
		// "-jdk","D:\\MYKey\\developtool\\jdk1.8.0_05\\bin",
		// "-sdkversion","19.0.1",
		// "-keyname","testKey",
		// "-keypass","123456",
		// "-apkname","testapk",
		// "-apkpath","D:\\new"
		// };
	}

	public static void main(String[] args) {
		new MFrame();
	}
}

class MatrixCompare implements Comparator<KeyButton> {

	public int compare(final KeyButton a, final KeyButton b) {
		if (a.getStartRow() < b.getStartRow()) {
			return -1;
		} else if (a.getStartRow() > b.getStartRow()) {
			return 1;
		} else {
			if (a.getStartCol() < b.getStartCol()) {
				return -1;
			} else if (a.getStartCol() > b.getStartCol()) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}

class KeyLocation {
	private KeyButton kb;
	private int location;

	public KeyLocation(KeyButton kb, int location) {
		this.kb = kb;
		this.location = location;
	}

	public int getKeyCode() {
		return kb.getKeyCode();
	}

	public int getLocation() {
		return location;
	}

	public KeyButton getKeyButton() {
		return kb;
	}
}
