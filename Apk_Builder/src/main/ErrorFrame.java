package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ErrorFrame extends JFrame{
	public ErrorFrame(String errorStr){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		JLabel lab = new JLabel();
		lab.setText(errorStr);
		lab.setSize(150,50);
		this.add(lab,"Center");
		
		JButton btn = new JButton("OK");
		btn.setSize(50, 50);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(99);
			}
		});
		this.add(btn,"South");
		this.setSize(200,100);
		this.setVisible(true);
	}
}
