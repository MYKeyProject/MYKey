package tool.frame;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import tool.manager.MYKeyManager;

public class SettingFrame extends JFrame implements ActionListener{
	JTextField colTextField, rowTextField;
	JButton okBtn, cancelBtn;
	public SettingFrame(){
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(3,2));
		colTextField = new JTextField();
		rowTextField = new JTextField();
		okBtn = new JButton("ok");
		cancelBtn = new JButton("cancel");
		okBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		c.add(new JLabel("행 개수"));
		c.add(rowTextField);
		c.add(new JLabel("열 개수"));
		c.add(colTextField);
		c.add(okBtn);
		c.add(cancelBtn);
		this.setSize(300,150);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e){
		JButton btn = (JButton)e.getSource();
		if(btn == okBtn){
			int row = Integer.parseInt(rowTextField.getText());
			int col = Integer.parseInt(colTextField.getText());
			MYKeyManager.getManager().setMatrix(row, col);
			this.dispose();
		}else if(btn == cancelBtn){
			this.dispose();
		}
	}
}