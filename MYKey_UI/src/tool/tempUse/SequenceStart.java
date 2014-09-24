package tool.tempUse;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tool.key.KeyButton;
import tool.key.KeyInfo;
import tool.key.KeySequence;
import tool.manager.MYKeyManager;

public class SequenceStart extends JFrame {
	JButton start = new JButton("Start");
	JButton end = new JButton("End");
	JPanel sequencePanel;

	public SequenceStart() {
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(2, 2));

		ButtonActionEvent bae = new ButtonActionEvent();

		start.addActionListener(bae);
		end.addActionListener(bae);
		start.setEnabled(false);
		end.setEnabled(false);
		
		JButton tmpBtn = new JButton("Check");
		tmpBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("============================Original KeyButtons=========================");
				Vector<KeyButton> vec = MYKeyManager.getManager().getOriginalKeyButtons();
				for(int i=0;i<vec.size();i++){
					KeyButton btn = vec.get(i);
					System.out.println(btn.getStartRow() + " , " + btn.getStartCol() + " : " + btn.getKeyStatus());
				}
				System.out.println();
				System.out.println();
				System.out.println("============================Shift KeyButtons=========================");
				vec = MYKeyManager.getManager().getShiftKeyButtons();
				for(int i=0;i<vec.size();i++){
					KeyButton btn = vec.get(i);
					System.out.println(btn.getStartRow() + " , " + btn.getStartCol() + " : " + btn.getKeyStatus());
				}
				System.out.println();
				System.out.println();

				System.out.println("============================KeyInfos=========================");
				Vector<KeyInfo> vec2 = MYKeyManager.getManager().getKeyInfos();
				for(int i=0;i<vec2.size();i++){
					KeyInfo ki = vec2.get(i);
					System.out.println(ki.getLabel() + " : " + ki.getNum());
				}
			}
		});

		SequencePanel sp = new SequencePanel();
		MYKeyManager.getManager().setSequenceStart(this);
		MYKeyManager.getManager().setSequencePanel(sp);

		c.add(start);
		c.add(end);
		c.add(sp);
		c.add(tmpBtn);
		this.setSize(200, 400);
		this.setVisible(true);
	}

	public void setStartEnable(boolean bool) {
		start.setEnabled(bool);
	}

	public void setEndEnable(boolean bool) {
		end.setEnabled(bool);
	}

	class ButtonActionEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			if (btn == start) {
				if (MYKeyManager.getManager().getPressedKeyInfo() != null) {
					setStartEnable(false);
					setEndEnable(true);
					MYKeyManager.getManager().setKeySeqnence(
							new KeySequence(MYKeyManager.getManager()
									.getPressedKeyInfo()));
				}
			} else if (btn == end) {
				setStartEnable(true);
				setEndEnable(false);
				MYKeyManager.getManager().getKeySequence().update();
				MYKeyManager.getManager().setKeySeqnence(null);
			}
		}
	}

}
