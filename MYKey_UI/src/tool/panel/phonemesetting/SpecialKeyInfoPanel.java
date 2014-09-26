package tool.panel.phonemesetting;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import tool.key.info.SpecialKeyInfo;
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
 * Show Special Key Informations
 * @author BaeJinshik
 *
 */
public class SpecialKeyInfoPanel extends KeyInfoPanel {
	public static final int KEY_NUM = 3;
	public static final int SPECIAL_MAX = 9;

	public SpecialKeyInfoPanel(String title) {
		super(title);
		
		GridLayout gl = new GridLayout(2, 5);
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		gl.setVgap((int)res.getHeight()/100);
		gl.setHgap((int)res.getWidth()/200);
		setLayout(gl);
		
		this.add(new SpecialKeyInfo(-7, "English",true));
		this.add(new SpecialKeyInfo(-5, "Delete",true));
		this.add(new SpecialKeyInfo(10, "Enter",true));
		this.add(new SpecialKeyInfo(32, "Space",true));
		
		for(int i = KEY_NUM; i < SPECIAL_MAX; i++){
			this.add(new EmptyPanel());
		}
	}
}
