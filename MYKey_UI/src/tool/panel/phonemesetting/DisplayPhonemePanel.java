package tool.panel.phonemesetting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import tool.manager.MYKeyManager;
import tool.panel.sequences.SequenceMainPanel;

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
 * @author Sunghoon Park
 *
 */
public class DisplayPhonemePanel extends JPanel {
	private JLabel phonemeLabel;
	private JInternalFrame parent;
	
	public DisplayPhonemePanel(JInternalFrame parent){
		this.parent = parent;
		Dimension tmpDi = parent.getPreferredSize();
		int width = (int) (tmpDi.getWidth());
		int height = (int) (tmpDi.getHeight()/6);
		setPreferredSize(new Dimension(width, height));
		
		setLayout(new BorderLayout());
		
		
		phonemeLabel = new JLabel();
		Font ft = new Font("Gothic", Font.PLAIN, 30);
		phonemeLabel.setFont(ft);
		phonemeLabel.setOpaque(true);
		phonemeLabel.setBackground(Color.white);
		phonemeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		phonemeLabel.setPreferredSize(new Dimension(120,300));
		
		TitledBorder topBorder = BorderFactory.createTitledBorder("Phoneme");
		topBorder.setTitlePosition(TitledBorder.TOP);
		TitledBorder doubleBorder = new TitledBorder(topBorder, "Selected", TitledBorder.RIGHT , TitledBorder.BOTTOM);
		phonemeLabel.setBorder(doubleBorder);
		phonemeLabel.setOpaque(false);
		
		MYKeyManager.getManager().setDisplayPhonemePanel(this);
		
		this.add(phonemeLabel,"West");
		this.add(new SequenceMainPanel(),"Center");
		this.setOpaque(false);
	}
	
	
	
	public void setText(String text){
		phonemeLabel.setText(text);
	}
	
	public void initText(){
		phonemeLabel.setText("");
	}
	
}
