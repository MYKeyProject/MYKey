package tool.panel.keysetting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import tool.key.KeyButton;

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
 * 
 * @author Kim TaeWan
 *
 */
public class KeySettingMainPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	KeyButton keyButton;
	KeySettingPanel keySettingPanel;
	PhonemeSettingPanel phonemeSettingPanel;
	JInternalFrame parent;
	public KeySettingMainPanel(KeyButton keyButton) {
		this.keyButton = keyButton;
		this.setLayout(new BorderLayout());
		// this.setBorder(new TitledBorder("Key Setting"));
		this.setBorder(new LineBorder(Color.BLACK, 3));
		keySettingPanel = new KeySettingPanel(keyButton,this);
		phonemeSettingPanel = new PhonemeSettingPanel(keyButton.getKeyInfoLabels());
		this.add(keySettingPanel, "Center");
		this.add(phonemeSettingPanel, "East");
		this.add(new FinishSettingPanel(),"South");
		this.setVisible(true);
	}
	public void exit(){
		DesktopPane.setKeySettingMainPanel(null);
		parent.dispose();
	}
	public void setParent(JInternalFrame parent){
		this.parent = parent;
	}
	
	class FinishSettingPanel extends JPanel{
		JButton okButton = new JButton("Ok");
		JButton cancelButton = new JButton("Cancel");
		public FinishSettingPanel(){
			this.setLayout(new BorderLayout());
			okButton.addActionListener(new OkButtonListener());
			cancelButton.addActionListener(new CancelButtonListener());
			this.add(okButton,"West");
			this.add(cancelButton,"East");
		}
	}
	
	class OkButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isRepeatable = keySettingPanel.isRepeatable();
			boolean isTextKey = keySettingPanel.isTextKey();
			keyButton.setRepeatable(isRepeatable);
			if(isTextKey){
				String label;
				if(keySettingPanel.getLabel().length() == 0){
					label = null;
				}else{
					label = keySettingPanel.getLabel();
				}
				keyButton.setLabelName(label);
				keyButton.setImagePath(null);
				keyButton.setTextKey();
			}else{
				File file = new File(keySettingPanel.getImagePath());
				String filePath;
				if(!file.exists()){
					filePath = null;
				}else{
					filePath = keySettingPanel.getImagePath();
				}
				keyButton.setLabelName(null);
				keyButton.setImagePath(filePath);
				keyButton.setImageKey();
			}
			keyButton.setKeyInfoSequence(phonemeSettingPanel.getPhonemeLabels());
			keyButton.repaint();
			exit();
		}
		
	}
	class CancelButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			DesktopPane.setKeySettingMainPanel(null);
			exit();
		}
	}

}

