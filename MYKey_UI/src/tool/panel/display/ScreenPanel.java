package tool.panel.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
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
 * Panel of screen will be show
 * @author Bae Jinshik
 * 
 */
public class ScreenPanel extends JPanel {
	Image img = new ImageIcon(Paths.get("").toAbsolutePath().toString()
			+ File.separator + "res" + File.separator + "img" + File.separator
			+ "screen.png").getImage();

	public ScreenPanel() {
		this.setBackground(Color.black);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(
				img,
				0,
				this.getHeight() - this.getParent().getHeight(),
				CompositionPanel.getOneCellWidth()
						* CompositionPanel.getColumn(), this.getParent()
						.getHeight(), this);
	}
}
