package tool.helper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* 
 * Copyright (C) 2008-2009 The Android Open Source Project 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
/**
 * 
 * @author Kim Taewan
 * 
 *         MainHelpPanel is Panel which help user to show how can use MYKey
 * 
 */

public class MainHelpPanel extends JPanel {
	final int numOfHelper = 5;
	private static final long serialVersionUID = 1L;
	Image img[];
	int currentHelper = 0;

	static Image bgImg = new ImageIcon(Paths.get("").toAbsolutePath()
			.toString()
			+ File.separator
			+ "res"
			+ File.separator
			+ "img"
			+ File.separator
			+ "help1.png").getImage();
	JButton nextBtn;
	JButton prevBtn;
	JLabel currentPage;

	public MainHelpPanel() {
		this.setPreferredSize(new Dimension(400, 400));
		this.setLayout(new BorderLayout());
		this.add(new HelpButtonPanel(), "South");

		img = new Image[numOfHelper];
		for (int idx = 0; idx < numOfHelper; idx++) {
			img[idx] = new ImageIcon(Paths.get("").toAbsolutePath().toString()
					+ File.separator + "res" + File.separator + "img"
					+ File.separator + "help" + (idx + 1) + ".png").getImage();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img[currentHelper], 0, 0, getWidth(), getHeight(), this);
	}

	class HelpButtonPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		public HelpButtonPanel() {
			this.setPreferredSize(new Dimension(400, 30));
			this.setLayout(new BorderLayout());
			this.setOpaque(false);

			currentPage = new JLabel((currentHelper + 1) + " / " + numOfHelper);
			currentPage.setHorizontalAlignment(JLabel.CENTER);

			nextBtn = new JButton("Next");
			nextBtn.setBackground(Color.GRAY);
			nextBtn.setOpaque(false);
			nextBtn.addActionListener(new ButtonListener());

			prevBtn = new JButton("Prev");
			prevBtn.setBackground(Color.GRAY);
			prevBtn.setOpaque(false);
			prevBtn.addActionListener(new ButtonListener());

			this.add(currentPage, "Center");
			this.add(nextBtn, "East");
			this.add(prevBtn, "West");
		}
	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton clickedBtn = (JButton) e.getSource();

			if ((clickedBtn == nextBtn) && (currentHelper != numOfHelper - 1)) {
				currentHelper++;
			} else if (clickedBtn == prevBtn && (currentHelper != 0)) {
				currentHelper--;
			}
			currentPage.setText((currentHelper + 1) + " / " + numOfHelper);
			repaint();
		}
	}
}
