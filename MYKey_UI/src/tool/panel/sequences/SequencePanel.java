package tool.panel.sequences;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import tool.key.KeyInfo;
import tool.panel.phonemesetting.EmptyPanel;

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
 * @author Park Sunghoon
 *
 */
public class SequencePanel extends JPanel {
	GridLayout gl;
	public SequencePanel() {
		Font ft = new Font("Gothic", Font.PLAIN, 30);		
		TitledBorder topBorder = BorderFactory.createTitledBorder("조합된 키 정보");
		topBorder.setTitlePosition(TitledBorder.TOP);
		this.setBorder(topBorder);
		
		gl = new GridLayout(1, 12);
		this.setBackground(Color.white);
		this.setLayout(gl);
		this.setVisible(true);
	}

	public void setKeyInfo(KeyInfo ki) {
		this.removeAll();
		if (ki == null) {
			this.setLayout(new GridLayout(1, 12));
			for(int i=0;i<12;i++){
				this.add(new EmptyPanel());
			}
			this.validate();
			this.repaint();
			return;
		}
		final int COL_NUM = 12;
		int i;
		int numOfElement = ki.getKeySequences().size();
		int rowNum = (numOfElement / COL_NUM)+1;
		this.setLayout((gl = new GridLayout(rowNum, COL_NUM)));
		gl.setVgap(1);
		gl.setHgap(1);
		for (i = 0; i < numOfElement; i++) {
			this.add(ki.getKeySequences().get(i));
		}
		for (i = i % COL_NUM; i < COL_NUM; i++) {
			this.add(new EmptyPanel());
		}
		this.validate();
		this.repaint();
		return;
	}
}