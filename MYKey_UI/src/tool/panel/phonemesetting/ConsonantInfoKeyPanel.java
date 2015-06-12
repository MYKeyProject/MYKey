package tool.panel.phonemesetting;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import tool.key.info.ConsonantKeyInfo;

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
 * Show Consonant Key Informations
 * @author BaeJinshik
 *
 */
public class ConsonantInfoKeyPanel extends KeyInfoPanel {
	public static final int KEY_NUM = 18;

	public ConsonantInfoKeyPanel(String title) {
		super(title);
		
		GridLayout gl = new GridLayout(2, 12);
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		gl.setVgap((int)res.getHeight()/100);
		gl.setHgap((int)res.getWidth()/200);
		setLayout(gl);

		this.add(new ConsonantKeyInfo(4000,"ㄱ"));
		this.add(new ConsonantKeyInfo(4001,"ㄲ"));
		this.add(new ConsonantKeyInfo(4002,"ㄴ"));
		this.add(new ConsonantKeyInfo(4003,"ㄷ"));
		this.add(new ConsonantKeyInfo(4004,"ㄸ"));
		this.add(new ConsonantKeyInfo(4005,"ㄹ"));
		this.add(new ConsonantKeyInfo(4006,"ㅁ"));
		this.add(new ConsonantKeyInfo(4007,"ㅂ"));
		this.add(new ConsonantKeyInfo(4008,"ㅃ"));
		this.add(new ConsonantKeyInfo(4009,"ㅅ"));
		this.add(new ConsonantKeyInfo(4010,"ㅆ"));
		this.add(new ConsonantKeyInfo(4011,"ㅇ"));
		this.add(new ConsonantKeyInfo(4012,"ㅈ"));
		this.add(new ConsonantKeyInfo(4013,"ㅉ"));
		this.add(new ConsonantKeyInfo(4014,"ㅊ"));
		this.add(new ConsonantKeyInfo(4015,"ㅋ"));
		this.add(new ConsonantKeyInfo(4016,"ㅌ"));
		this.add(new ConsonantKeyInfo(4017,"ㅍ"));
		this.add(new ConsonantKeyInfo(4018,"ㅎ"));
		
		for(int i = KEY_NUM; i < MAX; i++){
			this.add(new EmptyPanel());
		}
	}

}
