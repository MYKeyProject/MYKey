package tool.helper;

import javax.swing.JFrame;

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
 *         MainHelpFrame is Frame which help user to show how can use MYKey
 * 
 */
public class MainHelpFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public MainHelpFrame() {
		this.setTitle("Make Your Keyboard");
		this.setSize(400, 600);

		this.add(new MainHelpPanel());
		this.setVisible(true);
		this.setResizable(false);
	}
}
