package tool.panel.phonemesetting;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * 성훈 사용 패널
 * @author sunghoonpark
 *
 */
public class KeyInfoPanel extends JPanel {
	protected static final int MAX = 23;
	
	public KeyInfoPanel(String title){
		setBorder(BorderFactory.createTitledBorder(title));
		setOpaque(false);
	}
}
