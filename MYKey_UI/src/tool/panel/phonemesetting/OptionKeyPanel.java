package tool.panel.phonemesetting;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import tool.key.info.SpecialKeyInfo;

public class OptionKeyPanel extends KeyInfoPanel {
	public static final int KEY_NUM = 3;
	public static final int OPTION_MAX = 9;

	public OptionKeyPanel(String title) {
		super(title);
		
		GridLayout gl = new GridLayout(2, 5);
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		gl.setVgap((int)res.getHeight()/100);
		gl.setHgap((int)res.getWidth()/200);
		setLayout(gl);
		
		this.add(new SpecialKeyInfo(-1, "Shift",false));	
		this.add(new SpecialKeyInfo(-3, "Exit",false));			
		this.add(new SpecialKeyInfo(-8, "123",false));		
		this.add(new SpecialKeyInfo(4400, "String",false));		
		
		for(int i = KEY_NUM; i < OPTION_MAX; i++){
			this.add(new EmptyPanel());
		}
	}
	
	

}
