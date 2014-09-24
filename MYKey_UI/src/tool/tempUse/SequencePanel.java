package tool.tempUse;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import tool.key.KeyInfo;
import tool.panel.phonemesetting.EmptyPanel;

public class SequencePanel extends JPanel {
	GridLayout gl;
	public SequencePanel() {
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