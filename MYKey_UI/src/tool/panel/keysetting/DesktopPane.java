package tool.panel.keysetting;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import tool.frame.ErrorDialog;
import tool.manager.MYKeyManager;
import tool.panel.phonemesetting.ConsonantInfoKeyPanel;
import tool.panel.phonemesetting.DisplayPhonemePanel;
import tool.panel.phonemesetting.OptionKeyPanel;
import tool.panel.phonemesetting.SpecialKeyInfoPanel;
import tool.panel.phonemesetting.VowelKeyInfoPanel;

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
public class DesktopPane extends JDesktopPane {
	public static KeySettingMainPanel keySettingPanel = null;
	public DesktopPane() {
		setBackground(Color.LIGHT_GRAY);
		MYKeyManager.getManager().setDesktopPane(this);
	}
	
	public static void setKeySettingMainPanel(KeySettingMainPanel kp){
		keySettingPanel = kp;
	}

	public void addKeySettingPanel(KeySettingMainPanel panel) {
		if(keySettingPanel != null){
			ErrorDialog.warning("이미 키 설정 창이 떠있습니다.");
			return;
		}
		keySettingPanel = panel;
		JInternalFrame inFrame = new JInternalFrame("Key Setting", true, false,
				true, true);
		panel.setParent(inFrame);
		inFrame.setSize(500, 300);
		inFrame.setVisible(true);
		inFrame.setLocation(100, 400);
		inFrame.add(panel);
		this.add(inFrame);
	}


	/**
	 *TODO 이 팬에 음소 세팅 정보들이 삽입
	 *
	 * 현재 Layout이 Grid로 되어 있으나 추후 알파 버전에 대해 Layout을 변경할것
	 * 추후에 조합 Panel과 도움말 Panel을 추가할것
	 */
	public void addPhonemeSettingPanel() {
		JInternalFrame inFrame = new JInternalFrame("Phoneme Setting", false, false, false, true);
		
		Container con = inFrame.getContentPane();
		con.setLayout(new GridLayout(5,1));
		inFrame.setPreferredSize(new Dimension(500,600));
		
		
		DisplayPhonemePanel displayPhon = new DisplayPhonemePanel(inFrame);
		
		SpecialKeyInfoPanel specialKeyPan = new SpecialKeyInfoPanel("Special");
		
		ConsonantInfoKeyPanel consonantKeyPan = new ConsonantInfoKeyPanel("Consonant");
		
		VowelKeyInfoPanel vowelKeyPan = new VowelKeyInfoPanel("Vowel");
		
		OptionKeyPanel optionKeyPan = new OptionKeyPanel("Option");
		
		

		//조합창(이곳에서 Begin과 End버튼이 추가됨) 모음 스페셜 키 선택시 비활성화
//		CombinationPanel comPan = new CombinationPanel("Combination");
		
		//도움말
//		HelpPanel helpPan = new HelpPanel("Help");
		
		
		//추가
		con.add(displayPhon);
		con.add(specialKeyPan);
		con.add(consonantKeyPan);
		con.add(vowelKeyPan);
		con.add(optionKeyPan);
//		con.add(comPan);
//		con.add(helpPan);
		
		//InternalFrame 설정
		inFrame.pack();
		inFrame.setLocation(100, 100);
		inFrame.setVisible(true);
		inFrame.setBackground(new Color(240,240,240));
		this.add(inFrame);
	}
	
}
