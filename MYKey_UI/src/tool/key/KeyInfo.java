package tool.key;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import tool.manager.MYKeyManager;

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
 * @author Bae Jinshik
 * 
 *         KeyInfo help to know what Consonant it is
 * 
 */
public abstract class KeyInfo extends JPanel implements MouseListener {
	public static final int EMPTY_KEY = -4;
	public static final int SPECIAL_KEY = 0;
	public static final int CONSONANT_KEY = 1;
	public static final int VOWEL_KEY = 2;
	public static Color colorNormal = Color.WHITE;
	public static Color colorExist = Color.gray;
	public static Color colorClicked = Color.yellow;
	public static Color colorEnter = Color.lightGray;
	protected int keyState;
	protected int phoneme;
	protected int num = 0;
	protected String keyLabel;
	protected boolean primary;
	protected boolean isClicked = false;
	protected boolean isEntered = false;
	protected Vector<KeySequence> keySequences = new Vector<KeySequence>();

	public KeyInfo(int keyState, int phoneme, String keyLabel, boolean primary) {
		this.keyState = keyState;
		this.phoneme = phoneme;
		this.keyLabel = keyLabel;
		this.primary = primary;
		this.addMouseListener(this);
		this.setVisible(true);
		this.setOpaque(true);

		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border lowerdbevel = BorderFactory.createLoweredBevelBorder();
		this.setBorder(BorderFactory.createCompoundBorder(raisedbevel,
				lowerdbevel));
		init();
		MYKeyManager.getManager().addKeyInfo(this);
	}

	public synchronized void increaseNum() {
		num++;
		this.repaint();
	}

	public synchronized void decreaseNum() {
		if (num != 0) {
			num--;
			this.repaint();
		}
	}

	public int getNum() {
		return num;
	}

	public abstract int getMaxKeyNum();

	public void init() {
		System.out.println("init");
		isClicked = false;
		isEntered = false;
		this.setBackground(colorNormal);
		this.num = 0;
	}

	public synchronized void addKeySequence(KeySequence ks) {
		keySequences.add(ks);
		increaseNum();
	}

	public synchronized void removeKeySequence(KeySequence ks) {
		keySequences.remove(ks);
		decreaseNum();
	}

	public void cancelClick() {
		isClicked = false;
		repaint();
	}

	public void mouseEnter() {
		if (getNum() > 0) {
			MYKeyManager.getManager().showContainedButton(this);
		}
		if (MYKeyManager.getManager().getPressedKeyInfo() == null) {
			MYKeyManager.getManager().setSequenceKeyInfo(this);
		}
		System.out.println("keySequence Size : " + keySequences.size());
		isEntered = true;
		repaint();
	}

	public void mouseClick() {
		isClicked = !isClicked;
		if (isClicked) {
			MYKeyManager.getManager().setPressedKeyInfo(this);
			MYKeyManager.getManager().setSequenceKeyInfo(this);
		} else {
			MYKeyManager.getManager().setPressedKeyInfo(null);
			MYKeyManager.getManager().setSequenceKeyInfo(null);
		}
		repaint();
	}

	public void mouseExit() {
		if (getNum() > 0) {
			MYKeyManager.getManager().finishShowingContainedButton(this);
		}
		if (MYKeyManager.getManager().getPressedKeyInfo() == null) {
			MYKeyManager.getManager().setSequenceKeyInfo(null);
		}
		isEntered = false;
		repaint();
	}

	public int getphoneme() {
		return phoneme;
	}

	public int getKeyStatus() {
		return keyState;
	}

	public boolean isPrimary() {
		return primary;
	}

	public String getLabel() {
		return keyLabel;
	}

	public Vector<KeySequence> getKeySequences() {
		return keySequences;
	}

	@Override
	public void paint(Graphics g) {
		if (isClicked) {
			setBackground(colorClicked);
		} else if (isEntered) {
			setBackground(colorEnter);
		} else if (getNum() > 0) {
			setBackground(colorExist);
		} else {
			setBackground(colorNormal);
		}
		super.paint(g);
		g.drawString(keyLabel, this.getWidth() / 3, this.getHeight() / 2);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClick();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseEnter();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseExit();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
