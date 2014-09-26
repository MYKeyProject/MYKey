package tool.panel.keysetting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


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
public class PhonemeSettingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	final int maxNumOfPhoneme = 5;
	int numOfPhoneme;
	JPanel phonemePanel[];
	JPanel phonemeButtonPanel[];

	JLabel phoneme[];

	JButton upButton[];
	JButton deleteButton[];
	JButton downButton[];

	public int getNumOfPhoneme() {
		return numOfPhoneme;
	}

	public synchronized void decreaseNumOfPhoneme() {
		numOfPhoneme--;
	}

	public JLabel[] getPhonemes() {
		return phoneme;
	}

	public JButton[] getDownButtons() {
		return downButton;
	}

	public JButton[] getUpButtons() {
		return upButton;
	}

	public JButton[] getDeleteButtons() {
		return deleteButton;
	}

	public JPanel[] getPhonemePanels() {
		return phonemePanel;
	}

	public String[] getPhonemeLabels() {
		String[] str = new String[numOfPhoneme];
		for (int i = 0; i < str.length; i++) {
			str[i] = phoneme[i].getText();
		}
		return str;
	}

	public PhonemeSettingPanel(String[] phonemeLabels) {
		this.setLayout(new GridLayout(numOfPhoneme, 1));
		this.setPreferredSize(new Dimension(150, 0));
		this.numOfPhoneme = phonemeLabels.length;
		phonemePanelSetting();
		phonemeSetting(phonemeLabels);
		phonemeButtonPanelSetting();
		phonemeButtonSetting();
	}

	public void phonemePanelSetting() {
		phonemePanel = new JPanel[maxNumOfPhoneme];
		phonemeButtonPanel = new JPanel[numOfPhoneme];
		phoneme = new JLabel[numOfPhoneme];

		for (int idx = 0; idx < numOfPhoneme; idx++) {
			phonemePanel[idx] = new JPanel();
			phonemePanel[idx].setOpaque(false);
			phonemePanel[idx].setLayout(new BorderLayout());
			phonemePanel[idx].setBorder(new TitledBorder((idx + 1) + "번 째 음소"));

			this.add(phonemePanel[idx]);
		}
	}

	public void phonemeButtonPanelSetting() {
		for (int idx = 0; idx < numOfPhoneme; idx++) {
			phonemeButtonPanel[idx] = new JPanel();
			phonemeButtonPanel[idx].setPreferredSize(new Dimension(20, 0));
			phonemeButtonPanel[idx].setLayout(new GridLayout(3, 1, 1, 6));
			phonemePanel[idx].add(phonemeButtonPanel[idx], "East");
		}
	}

	public void phonemeSetting(String[] phonemeLabels) {
		for (int idx = 0; idx < numOfPhoneme; idx++) {
			phoneme[idx] = new JLabel();
			phoneme[idx].setText(phonemeLabels[idx]);
			phoneme[idx].setFont(new Font("Gothic", Font.BOLD, 14));
			phonemePanel[idx].add(phoneme[idx], "West");
		}
	}

	public void phonemeButtonSetting() {
		upButton = new JButton[numOfPhoneme];
		downButton = new JButton[numOfPhoneme];
		deleteButton = new JButton[numOfPhoneme];
		for (int idx = 0; idx < numOfPhoneme; idx++) {
			upButton[idx] = new JButton("↑");
			upButton[idx].setBorder(new LineBorder(Color.BLACK, 1, false));
			upButton[idx].setOpaque(false);
			upButton[idx].addActionListener(new PhonemeUpButtonListener());

			deleteButton[idx] = new JButton("X");
			deleteButton[idx].setBorder(new LineBorder(Color.BLACK, 1, false));
			deleteButton[idx].setOpaque(false);
			deleteButton[idx]
					.addActionListener(new PhonemeDeleteButtonListener());

			downButton[idx] = new JButton("↓");
			downButton[idx].setBorder(new LineBorder(Color.BLACK, 1, false));
			downButton[idx].setOpaque(false);
			downButton[idx].addActionListener(new PhonemeDownButtonListener());

			phonemeButtonPanel[idx].add(upButton[idx]);
			phonemeButtonPanel[idx].add(deleteButton[idx]);
			phonemeButtonPanel[idx].add(downButton[idx]);
		}
		if (numOfPhoneme != 0) {
			upButton[0].setEnabled(false);
			downButton[numOfPhoneme - 1].setEnabled(false);
		}
	}

	class PhonemeUpButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton clickedBtn = (JButton) e.getSource();
			int numOfPhoneme = getNumOfPhoneme();
			JButton upButton[] = getUpButtons();
			JLabel phoneme[] = getPhonemes();

			int selectedIdx = 0;
			for (int idx = 0; idx < numOfPhoneme; idx++) {
				if (clickedBtn == upButton[idx]) {
					selectedIdx = idx;
					break;
				}
			}

			if (selectedIdx != 0) {
				String selectedLabel = phoneme[selectedIdx].getText();
				String downLabel = phoneme[selectedIdx - 1].getText();
				phoneme[selectedIdx - 1].setText(selectedLabel);
				phoneme[selectedIdx].setText(downLabel);
			}
		}
	}

	class PhonemeDownButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton clickedBtn = (JButton) e.getSource();
			int numOfPhoneme = getNumOfPhoneme();
			JButton downButton[] = getDownButtons();
			JLabel phoneme[] = getPhonemes();
			int selectedIdx = 0;
			for (int idx = 0; idx < numOfPhoneme; idx++) {
				if (clickedBtn == downButton[idx]) {
					selectedIdx = idx;
					break;
				}
			}

			if (selectedIdx != (numOfPhoneme - 1)) {
				String selectedLabel = phoneme[selectedIdx].getText();

				String upLabel = phoneme[selectedIdx + 1].getText();
				phoneme[selectedIdx + 1].setText(selectedLabel);
				phoneme[selectedIdx].setText(upLabel);
			}
		}
	}

	class PhonemeDeleteButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton clickedBtn = (JButton) e.getSource();
			int numOfPhoneme = getNumOfPhoneme();
			JButton deleteButton[] = getDeleteButtons();
			JButton upButton[] = getUpButtons();
			JButton downButton[] = getDownButtons();
			JLabel phoneme[] = getPhonemes();
			JPanel phonemePanel[] = getPhonemePanels();
			int selectedIdx = 0;
			for (int idx = 0; idx < numOfPhoneme; idx++) {
				if (clickedBtn == deleteButton[idx]) {
					selectedIdx = idx;
					break;
				}
			}

			for (int idx = selectedIdx; idx < numOfPhoneme; idx++) {
				if (idx != (numOfPhoneme - 1)) {
					phoneme[idx].setText(phoneme[idx + 1].getText());
				} else {
					phoneme[idx].setText("");
				}
			}

			upButton[numOfPhoneme - 1].setEnabled(false);
			deleteButton[numOfPhoneme - 1].setEnabled(false);
			phonemePanel[numOfPhoneme - 1].setVisible(false);
			if (numOfPhoneme == 1) {
				downButton[numOfPhoneme - 1].setEnabled(false);
			} else {
				downButton[numOfPhoneme - 2].setEnabled(false);
			}
			decreaseNumOfPhoneme();
		}
	}

}