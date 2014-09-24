package tool.panel.phonemesetting;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import tool.key.info.SpecialKeyInfo;

/**
 * TODO 성훈 사용
 * 키 간격과 문자열들을 재정리
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
