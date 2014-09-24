package tool.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import tool.frame.SettingFrame;
import tool.manager.MYKeyManager;

public class MYKeyMenubar extends JMenuBar implements ActionListener {
	JMenu file;
	JMenuItem newFile;
	JMenuItem open;
	JMenuItem save;
	JMenuItem saveAs;

	JMenu apk;
	JMenuItem make;
	JMenuItem install;

	JMenu option;
	JMenuItem setting;

	JMenu help;
	JMenuItem helpItem;

	public MYKeyMenubar() {
		file = new JMenu("File");
		newFile = new JMenuItem("new file");
		newFile.addActionListener(this);
		open = new JMenuItem("open");
		open.addActionListener(this);
		save = new JMenuItem("save");
		save.addActionListener(this);
		saveAs = new JMenuItem("save as");
		saveAs.addActionListener(this);

		apk = new JMenu("Apk");
		make = new JMenuItem("make");
		make.addActionListener(this);
		install = new JMenuItem("install");
		install.addActionListener(this);

		option = new JMenu("Option");
		setting = new JMenuItem("setting");
		setting.addActionListener(this);

		help = new JMenu("help");
		helpItem = new JMenuItem("help");
		helpItem.addActionListener(this);

		file.add(newFile);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		this.add(file);

		apk.add(make);
		apk.add(install);
		this.add(apk);

		option.add(setting);
		this.add(option);

		help.add(helpItem);
		this.add(help);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newFile) {
			int result = JOptionPane.showConfirmDialog(null,
					"Close Current Working Space?", "New File",
					JOptionPane.YES_NO_OPTION);

			switch (result) {
			case JOptionPane.CLOSED_OPTION: // cancel
				break;

			case JOptionPane.YES_OPTION: // create new file
				MYKeyManager.getManager().initComposing();
				break;

			case JOptionPane.NO_OPTION: // cancel
				break;
			}
		} else if (e.getSource() == open) {
			JFileChooser fileExplorer = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Only MYKey Saved info", "myk");
			fileExplorer.setFileFilter(filter);

			int ret = fileExplorer.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) { // cancel
				JOptionPane.showMessageDialog(null, "canceled", "Cancel",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else {
				JOptionPane.showMessageDialog(null, ret + "load complete",
						"load", JOptionPane.PLAIN_MESSAGE);
				MYKeyManager.getManager().loadTables(
						(fileExplorer.getSelectedFile().getAbsolutePath()));
				return;
			}

		} else if (e.getSource() == save) {
			if (MYKeyManager.getManager().getLoadFilePath() != null) {
				MYKeyManager.getManager().saveTables(
						MYKeyManager.getManager().getLoadFilePath());
				return;
			}
			JFileChooser fileExplorer = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Only MYKey Saved info", "myk");
			fileExplorer.setFileFilter(filter);

			int ret = fileExplorer.showSaveDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) { // cancel
				JOptionPane.showMessageDialog(null, "canceled", "Cancel",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else {
				JOptionPane.showMessageDialog(null, "save complete", "save",
						JOptionPane.PLAIN_MESSAGE);
				MYKeyManager.getManager().saveTables(
						(fileExplorer.getSelectedFile().getAbsolutePath()));
				return;
			}

		} else if (e.getSource() == saveAs) {
			JFileChooser fileExplorer = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Only MYKey Saved info", "myk");
			fileExplorer.setFileFilter(filter);

			int ret = fileExplorer.showSaveDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) { // cancel
				JOptionPane.showMessageDialog(null, "canceled", "Cancel",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else {
				JOptionPane.showMessageDialog(null, "save complete", "save",
						JOptionPane.PLAIN_MESSAGE);
				MYKeyManager.getManager().saveTables(
						(fileExplorer.getSelectedFile().getAbsolutePath()));
				return;
			}
		} else if (e.getSource() == make) {
			JFileChooser fileExplorer = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"apkFile", "apk");
			fileExplorer.setFileFilter(filter);

			int ret = fileExplorer.showSaveDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) { // cancel
				JOptionPane.showMessageDialog(null, "canceled", "Cancel",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else {
				File f = fileExplorer.getSelectedFile();
				int result = JOptionPane.showConfirmDialog(null,
						"Install Connected Android Device??", "Install Option",
						JOptionPane.YES_NO_OPTION);

				switch (result) {
				case JOptionPane.CLOSED_OPTION: //
					break;
				case JOptionPane.YES_OPTION: //
					MYKeyManager.getManager().makeAPKofCurrentWorkingSpace(
							f.getParent(), f.getName(), true);
					break;

				case JOptionPane.NO_OPTION: //
					MYKeyManager.getManager().makeAPKofCurrentWorkingSpace(
							f.getParent(), f.getName(), false);
					break;
				}
				return;
			}
		} else if (e.getSource() == install) {

		} else if (e.getSource() == setting) {
			new SettingFrame();
		} else if (e.getSource() == helpItem) {

		}
	}
}
