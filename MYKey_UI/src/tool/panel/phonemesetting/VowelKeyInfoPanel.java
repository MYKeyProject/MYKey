package tool.panel.phonemesetting;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import tool.key.info.VowelKeyInfo;

public class VowelKeyInfoPanel extends KeyInfoPanel {

	public VowelKeyInfoPanel(String title) {
		super(title);
		
		GridLayout gl = new GridLayout(2, 12);
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		gl.setVgap((int)res.getHeight()/100);
		gl.setHgap((int)res.getWidth()/200);
		setLayout(gl);

		this.add(new VowelKeyInfo(4100, "ㅏ"));
		this.add(new VowelKeyInfo(4101, "ㅐ"));
		this.add(new VowelKeyInfo(4102, "ㅑ"));
		this.add(new VowelKeyInfo(4103, "ㅒ"));
		this.add(new VowelKeyInfo(4104, "ㅓ"));
		this.add(new VowelKeyInfo(4105, "ㅔ"));
		this.add(new VowelKeyInfo(4106, "ㅕ"));
		this.add(new VowelKeyInfo(4107, "ㅖ"));
		this.add(new VowelKeyInfo(4108, "ㅗ"));
		this.add(new VowelKeyInfo(4109, "ㅘ"));
		this.add(new VowelKeyInfo(4110, "ㅙ"));
		this.add(new VowelKeyInfo(4111, "ㅚ"));
		this.add(new VowelKeyInfo(4112, "ㅛ"));
		this.add(new VowelKeyInfo(4113, "ㅜ"));
		this.add(new VowelKeyInfo(4114, "ㅝ"));
		this.add(new VowelKeyInfo(4115, "ㅞ"));
		this.add(new VowelKeyInfo(4116, "ㅟ"));
		this.add(new VowelKeyInfo(4117, "ㅠ"));
		this.add(new VowelKeyInfo(4118, "ㅡ"));
		this.add(new VowelKeyInfo(4119, "ㅢ"));
		this.add(new VowelKeyInfo(4120, "ㅣ"));
	}

}
