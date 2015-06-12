package tool.panel.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JSplitPane;


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
 * Panel of Screen and KeyBoard will be show
 * @author Bae Jinshik
 * 
 */
public class DevicePanel extends JSplitPane {
	public static final int MAX_RATE = 80;
	public static final int MIN_RATE = 20;
	public static final int STANDARD_CHANGE_RATE = 5;
	public static int compositionVerticalRate = 40;
	public static int dividerSize = 5;
	public static int panelHeight = 0;
	CompositionPanel compositionPanel;
	ScreenPanel showScreenPanel;

	public DevicePanel(CompositionPanel compositionPanel,
			ScreenPanel showScreenPanel) {
		super(JSplitPane.VERTICAL_SPLIT, showScreenPanel, compositionPanel);
		this.compositionPanel = compositionPanel;
		this.showScreenPanel = showScreenPanel;
		this.setDividerSize(dividerSize);
		this.setVisible(true);
		this.repaint();
		this.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent changeEvent) {
						JSplitPane sourceSplitPane = (JSplitPane) changeEvent
								.getSource();
						int dividerHeight = sourceSplitPane
								.getDividerLocation();
						int rate = (dividerHeight * 100)
								/ sourceSplitPane.getHeight();
						if (rate <= MIN_RATE) {
							compositionVerticalRate = (100 - MIN_RATE);
							sourceSplitPane.repaint();
							return;
						} else if (rate >= MAX_RATE) {
							compositionVerticalRate = (100 - MAX_RATE);
							sourceSplitPane.repaint();
							return;
						}
						int rest = rate % STANDARD_CHANGE_RATE;
						if (rest > STANDARD_CHANGE_RATE / 2) {
							rate += (STANDARD_CHANGE_RATE - rest);
						} else {
							rate -= rest;
						}
						compositionVerticalRate = (100 - rate);
						sourceSplitPane.repaint();

					}
				});
	}

	public static void setCompositionVerticalRate(int rate) {
		compositionVerticalRate = rate;
	}

	public static int getVerticalRate() {
		return compositionVerticalRate;
	}

	@Override
	public void paint(Graphics g) {
		panelHeight = this.getHeight();
		if (this.getDividerLocation() != (panelHeight * (100 - compositionVerticalRate)) / 100) {
			this.setDividerLocation((panelHeight * (100 - compositionVerticalRate)) / 100);
		}
		super.paint(g);

		g.setColor(Color.yellow);
		for (int i = 0; i <= 50; i += 2) {
			g.drawLine(this.getWidth() * i / 50, this.getHeight() * 60 / 100,
					this.getWidth() * (i + 1) / 50, this.getHeight() * 60 / 100);
		}
		System.out.println("VerticalRate = " + compositionVerticalRate);
	}
}
