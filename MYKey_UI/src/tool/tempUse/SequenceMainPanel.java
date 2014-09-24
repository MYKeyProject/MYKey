package tool.tempUse;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import tool.key.KeyButton;
import tool.key.KeyInfo;
import tool.key.KeySequence;
import tool.manager.MYKeyManager;

public class SequenceMainPanel extends JPanel {
	JButton start = new JButton("시작");
	JButton end = new JButton("완료");
	JButton cancel = new JButton("취소");
	JPanel sequencePanel;

	public SequenceMainPanel() {
		this.setLayout(new BorderLayout());
		Font ft = new Font("Gothic", Font.PLAIN, 30);		
		TitledBorder topBorder = BorderFactory.createTitledBorder("키 조합");
		topBorder.setTitlePosition(TitledBorder.TOP);
		this.setBorder(topBorder);

		ButtonActionEvent bae = new ButtonActionEvent();

		start.addActionListener(bae);
		end.addActionListener(bae);
		cancel.addActionListener(bae);
		init();
		
		ButtonPanel bp = new ButtonPanel();
		bp.add(start);
		bp.add(end);
		bp.add(cancel);
		
		SequencePanel sp = new SequencePanel();
		MYKeyManager.getManager().setSequenceMain(this);
		MYKeyManager.getManager().setSequencePanel(sp);

		this.add(bp,"West");
		this.add(sp,"Center");
		this.setVisible(true);
	}

	public void init(){
		setStartEnable(false);
		setEndEnable(false);
		setCancelEnable(false);
	}
	
	public void setStartEnable(boolean bool) {
		start.setEnabled(bool);
	}

	public void setEndEnable(boolean bool) {
		end.setEnabled(bool);
	}
	public void setCancelEnable(boolean bool){
		cancel.setEnabled(bool);
	}

	class ButtonActionEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			if (btn == start) {
				if (MYKeyManager.getManager().getPressedKeyInfo() != null) {
					setStartEnable(false);
					setEndEnable(true);
					setCancelEnable(true);
					MYKeyManager.getManager().setKeySeqnence(
							new KeySequence(MYKeyManager.getManager()
									.getPressedKeyInfo()));
				}
			} else if (btn == end) {
				setStartEnable(true);
				setEndEnable(false);
				setCancelEnable(false);
				MYKeyManager.getManager().getKeySequence().update();
				MYKeyManager.getManager().setKeySeqnence(null);
			} else if(btn == cancel){
				setStartEnable(true);
				setEndEnable(false);
				setCancelEnable(false);
				MYKeyManager.getManager().setKeySeqnence(null);
			}
		}
	}

}

class ButtonPanel extends JPanel{
	public ButtonPanel(){
		Font ft = new Font("Gothic", Font.PLAIN, 30);		
		TitledBorder topBorder = BorderFactory.createTitledBorder("");
		topBorder.setTitlePosition(TitledBorder.TOP);
		this.setBorder(topBorder);
		
		GridLayout gl = new GridLayout(3,1);
		gl.setVgap(10);
		this.setLayout(gl);
		this.setVisible(true);
	}
}

