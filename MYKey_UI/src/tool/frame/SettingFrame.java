package tool.frame;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import tool.manager.MYKeyManager;
import tool.panel.display.CompositionPanel;

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
 * @author Bae Jinshik
 * 
 *         SettingFrame is Frame which help to set Keyboard Row and Col
 * 
 */
public class SettingFrame extends JFrame implements ActionListener {
	private static int MAX = 30;
	JTextField colTextField, rowTextField;
	JButton okBtn, cancelBtn;

	public SettingFrame() {
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(3, 2));
		colTextField = new JTextField();
		rowTextField = new JTextField();
		colTextField.setText(Integer.toString(CompositionPanel.getColumn()));
		rowTextField.setText(Integer.toString(CompositionPanel.getRow()));
		okBtn = new JButton("ok");
		cancelBtn = new JButton("cancel");
		okBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		c.add(new JLabel("행 개수"));
		c.add(rowTextField);
		c.add(new JLabel("열 개수"));
		c.add(colTextField);
		c.add(okBtn);
		c.add(cancelBtn);
		this.setSize(300, 150);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (btn == okBtn) {
			if (!check(rowTextField)) {
				return;
			} else if (!check(colTextField)) {
				return;
			}
			int row = Integer.parseInt(rowTextField.getText());
			int col = Integer.parseInt(colTextField.getText());
			MYKeyManager.getManager().setMatrix(row, col);
			this.dispose();
		} else if (btn == cancelBtn) {
			this.dispose();
		}
	}

	public boolean check(JTextField text) {
		String str = text.getText();
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9') {
				ErrorDialog.error("입력한 값이 잘못되었습니다. 숫자만 입력 가능합니다.");
				return false;
			}
		}
		int num = Integer.parseInt(str);
		if (num > MAX || num < 1) {
			ErrorDialog.error("입력한 값이 잘못되었습니다. 입력 가능한 숫자는 0-" + MAX + " 입니다.");
			return false;
		}
		return true;
	}
}