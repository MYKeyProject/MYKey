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
import tool.helper.MainHelpFrame;
import tool.manager.MYKeyManager;


/* 
 * Copyright (C) 2008-2009 The Android Open Source Project 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */


/**
 * MenuBar of MYKey
 * @author Bae Jinshik
 * 
 */
public class MYKeyMenubar extends JMenuBar implements ActionListener {
	JMenu file;
	JMenuItem newFile;
	JMenuItem open;
	JMenuItem save;
	JMenuItem saveAs;

	JMenu apk;
	JMenuItem make;

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
		} else if (e.getSource() == setting) {
			new SettingFrame();
		} else if (e.getSource() == helpItem) {
			new MainHelpFrame();

		}
	}
}
