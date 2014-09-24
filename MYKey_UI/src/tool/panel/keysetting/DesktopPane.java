package tool.panel.keysetting;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import tool.manager.MYKeyManager;
import tool.panel.phonemesetting.ConsonantInfoKeyPanel;
import tool.panel.phonemesetting.DisplayPhonemePanel;
import tool.panel.phonemesetting.OptionKeyPanel;
import tool.panel.phonemesetting.SpecialKeyInfoPanel;
import tool.panel.phonemesetting.VowelKeyInfoPanel;

public class DesktopPane extends JDesktopPane {

	public DesktopPane() {
		setBackground(Color.LIGHT_GRAY);
		MYKeyManager.getManager().setDesktopPane(this);
	}

	public void addKeySettingPanel(KeySettingMainPanel panel) {
		JInternalFrame inFrame = new JInternalFrame("Key Setting", true, true,
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
