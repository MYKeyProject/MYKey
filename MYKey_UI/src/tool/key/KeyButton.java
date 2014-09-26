package tool.key;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import tool.frame.ErrorDialog;
import tool.manager.MYKeyManager;
import tool.panel.display.CompositionPanel;
import tool.panel.keysetting.KeySettingMainPanel;

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
 *         KeyButton is Button that user will be click in own device
 * 
 * 
 */

public class KeyButton extends JButton {
	private static final int NOTHING = 0;
	private static final int RESIZING = 1;
	private static final int RELOCATING = 2;
	private static final int COPYING = 3;
	private static final int IMAGE_GAP = 5;
	private static final int LEFT = 1, RIGHT = 2;
	private static final int UP = 1, DOWN = 2;
	private static final int MAX_SIZE = 5;

	private static final int RESIZE_GAP = 2;
	private int keyCode; // key's identification
	private int mouseStatus = NOTHING; // status that means mouse's location (
										// on or not )
	private int keySequenceNum = 0; // num of KeySequences that KeyButton has
	private int keyStatus = KeyInfo.EMPTY_KEY; // status that Keybutton is using
	private int horizontalResizing, verticalResizing;
	private boolean isRepeatable = false;
	private boolean isTextButton = true;
	private boolean isOriginalKey;
	private String label;
	private String imagePath;
	private Image img;

	private int pressedX, pressedY;
	private Vector<KeyInfo> keyInfos = new Vector<KeyInfo>(); // KeyInfos that
																// KeyButton has
	private Vector<SavedKeyInfo> savedKeyInfos = new Vector<SavedKeyInfo>(); // KeyInfos
																				// which
																				// use
																				// temporarily
																				// when
																				// load
																				// a
																				// work
	private Vector<Integer> vowelSequenceNums = new Vector<Integer>();
	private CompositionPanel parent; // Parent panel
	private KeyButton copyBtn; // shift or not shiftted Panel's keybutton which
								// is in same location
	private int tmpRow, tmpCol, tmpRowCellNum, tmpColCellNum; // size and
																// loaction of
																// keybutton
																// when
																// keybutton is
																// changing
	private int startRow, startCol, rowCellNum, colCellNum; // size and location
															// of keybutton
	private static Image keyImg = new ImageIcon(Paths.get("").toAbsolutePath()
			.toString()
			+ File.separator
			+ "res"
			+ File.separator
			+ "img"
			+ File.separator
			+ "key.png").getImage();
	private static Color colorShow = Color.gray;
	private static Color colorNormal = Color.black;

	public KeyButton(int row, int col, int rowCellNum, int colCellNum,
			CompositionPanel parent, boolean isOriginalKey) {
		super(null, null);
		int oneCellWidth = CompositionPanel.getOneCellWidth();
		int oneCellHeight = CompositionPanel.getOneCellHeight();
		this.parent = parent;
		this.setLocation(col * oneCellWidth, row * oneCellHeight);
		this.setSize(colCellNum * oneCellWidth, rowCellNum * oneCellHeight);
		this.startRow = row;
		this.startCol = col;
		this.rowCellNum = rowCellNum;
		this.colCellNum = colCellNum;
		KeyButtonListener lis = new KeyButtonListener();
		this.isOriginalKey = isOriginalKey;
		this.addMouseMotionListener(lis);
		this.addMouseListener(lis);
		this.setBackground(colorNormal);
		// this.setBorder(null);
		// if (isOriginalKey) {
		// MYKeyManager.getMYKeyManager().addKeyButton(this);
		// }
	}

	/**
	 * increaseKeySequenceNum ( plus one )
	 */
	public synchronized void increaseKeySequenceNum() {
		if (MYKeyManager.getManager().getPressedKeyInfo() == null) {
			return;
		}
		if (keyStatus == KeyInfo.EMPTY_KEY) {
			setKeyStatus(MYKeyManager.getManager().getPressedKeyInfo()
					.getKeyStatus());
		}
		keySequenceNum++;
	}

	/**
	 * decreaseKeySequenceNum ( minus one )
	 */
	public synchronized void decreaseKeySequenceNum() {
		keySequenceNum--;
		if (keySequenceNum == 0 && keyInfos.size() == 0) {
			setKeyCode(KeyInfo.EMPTY_KEY);
		}
	}

	/**
	 * set KeyButton size
	 */
	public void setOriginalSize(int rowCellNum, int colCellNum) {
		this.rowCellNum = rowCellNum;
		this.colCellNum = colCellNum;
	}

	/**
	 * set KeyButton location
	 */
	public void setOriginalLocation(int startRow, int startCol) {
		this.startRow = startRow;
		this.startCol = startCol;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public int getRowCellNum() {
		return rowCellNum;
	}

	public int getColCellNum() {
		return colCellNum;
	}

	public String getLabelName() {
		return this.label;
	}

	public void setLocationWithMatrix(int row, int col) {
		this.setLocation(col * CompositionPanel.getOneCellWidth(), row
				* CompositionPanel.getOneCellHeight());
	}

	public void setSizeWithMatrix(int rowCellNum, int colCellNum) {
		this.setSize(colCellNum * CompositionPanel.getOneCellWidth(),
				rowCellNum * CompositionPanel.getOneCellHeight());
	}

	public void setRepeatable(boolean bool) {
		this.isRepeatable = bool;
	}

	public void setLabelName(String label) {
		if (label == null || label.length() == 0) {
			this.label = null;
		} else {
			this.label = label;
		}
	}

	public void setImagePath(String path) {
		this.imagePath = path;
		if (path != null) {
			img = new ImageIcon(path).getImage();
		}
	}

	public void setTextKey() {
		this.isTextButton = true;
	}

	public void setImageKey() {
		this.isTextButton = false;
	}

	public void saveMatrixAndLocation(int row, int col, int rowCellNum,
			int colCellNum) {
		int oneCellWidth = CompositionPanel.getOneCellWidth(), oneCellHeight = CompositionPanel
				.getOneCellHeight();
		this.startRow = row;
		this.startCol = col;
		this.rowCellNum = rowCellNum;
		this.colCellNum = colCellNum;
		this.setLocation(col * oneCellWidth, row * oneCellHeight);
		this.setSize(colCellNum * oneCellWidth, rowCellNum * oneCellHeight);
		this.repaint();
	}

	public boolean addKeyInfo(KeyInfo ki) {
		int keyStatus = getKeyStatus();
		if (keyStatus == KeyInfo.EMPTY_KEY
				|| (keyStatus == ki.getKeyStatus() && keyInfos.size() < ki
						.getMaxKeyNum())) {
			setKeyStatus(ki.getKeyStatus());
			ki.increaseNum();
			keyInfos.add(ki);
			return true;
		} else if (keyInfos.size() >= ki.getMaxKeyNum()) {
			ErrorDialog.error("해당 음소는 최대 " + ki.getMaxKeyNum() + "개 추가 가능합니다.");
		} else {
			ErrorDialog.error("들어가 있는 음소 종류가 다릅니다. 다른 버튼을 선택해 주세요.");
		}
		return false;
	}

	public String[] getKeyInfoLabels() {
		String[] labels = new String[keyInfos.size()];
		for (int i = 0; i < keyInfos.size(); i++) {
			labels[i] = keyInfos.get(i).getLabel();
		}
		return labels;
	}

	public void removeKeyInfo(KeyInfo ki) {
		if (keyInfos.remove(ki)) {
			ki.decreaseNum();
			if (keyInfos.size() == 0 && keySequenceNum == 0) {
				setKeyStatus(KeyInfo.EMPTY_KEY);
			}
		}
	}

	public void removeAllKeyInfos() {
		int size = keyInfos.size();
		for (int i = 0; i < size; i++) {
			System.out.println();
			removeKeyInfo(keyInfos.get(0));
		}
	}

	public void setKeyInfoSequence(String[] phonemes) {

		removeAllKeyInfos();
		for (int i = 0; i < phonemes.length; i++) {
			KeyInfo ki = MYKeyManager.getManager().findKeyInfoFromLabel(
					phonemes[i]);
			if (ki != null) {
				addKeyInfo(ki);
			}
		}
	}

	public Vector<KeyInfo> getKeyInfos() {
		return keyInfos;
	}

	public Vector<Integer> getKeyInfoLocations(KeyInfo ki) {
		Vector<Integer> vec = new Vector<Integer>();
		for (int i = 0; i < keyInfos.size(); i++) {
			if (keyInfos.get(i).equals(ki)) {
				vec.add((i + 1));
			}
		}
		return vec;
	}

	public CompositionPanel getParent() {
		return parent;
	}

	public boolean isRepeatable() {
		return isRepeatable;
	}

	public boolean isTextButton() {
		return isTextButton;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void show() {
		this.setBackground(colorShow);
	}

	public void finishShow() {
		this.setBackground(colorNormal);
	}

	public boolean isKeyInfoExist(String label) {
		for (int i = 0; i < keyInfos.size(); i++) {
			if (keyInfos.get(i).getLabel().equals(label)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasKeyInfo(KeyInfo keyInfo) {
		for (int i = 0; i < keyInfos.size(); i++) {
			if (keyInfos.get(i).equals(keyInfo)) {
				return true;
			}
		}
		return false;
	}

	public void setKeyCode(int code) {
		this.keyCode = code;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public int getSpecialPhoneme() {
		return keyInfos.get(0).getphoneme();
	}

	public void addSequenceNum(int num) {
		vowelSequenceNums.add(num);
		this.repaint();
	}

	public void removeSequenceNums() {
		vowelSequenceNums.removeAllElements();
		this.repaint();
	}

	/**
	 * remove KeyButton with copyKeyButton
	 */
	public void remove() {
		Iterator<KeyInfo> it = keyInfos.iterator();
		while (it.hasNext()) {
			KeyInfo ki = it.next();
			ki.decreaseNum();
		}

		Vector<KeySequence> vec = MYKeyManager.getManager()
				.getAllKeySequences();
		Vector<KeySequence> keySequences = new Vector<KeySequence>();
		for (int i = 0; i < vec.size(); i++) {
			KeySequence ks = vec.get(i);
			if (ks.hasKeyButton(this)) {
				keySequences.add(ks);
			}
		}
		Iterator<KeySequence> ksIt = keySequences.iterator();
		while (ksIt.hasNext()) {
			ksIt.next().remove();
		}
		parent.removeKeyButtonWithOtherPanel(this);
		MYKeyManager.getManager().removeKeyButton(this);
	}

	/**
	 * remove KeyButton without copyKeyButton
	 */
	public void removeOnly() {
		Iterator<KeyInfo> it = keyInfos.iterator();
		while (it.hasNext()) {
			KeyInfo ki = it.next();
			ki.decreaseNum();
		}

		Vector<KeySequence> vec = MYKeyManager.getManager()
				.getAllKeySequences();
		Vector<KeySequence> keySequences = new Vector<KeySequence>();
		for (int i = 0; i < vec.size(); i++) {
			KeySequence ks = vec.get(i);
			if (ks.hasKeyButton(this)) {
				keySequences.add(ks);
			}
		}
		Iterator<KeySequence> ksIt = keySequences.iterator();
		while (ksIt.hasNext()) {
			ksIt.next().remove();
		}
		parent.removeKeyButton(this);
		MYKeyManager.getManager().removeKeyButton(this);
	}

	public int getKeyStatus() {
		return keyStatus;
	}

	public void setKeyStatus(int status) {
		this.keyStatus = status;
	}

	public void saveKeyInfo(KeyInfo ki, int num) {
		savedKeyInfos.add(new SavedKeyInfo(ki, num));
	}

	public void updateSavedKeyInfo() {
		Collections.sort(savedKeyInfos, new SavedKeyInfoCompare());
		for (int i = 0; i < savedKeyInfos.size(); i++) {
			addKeyInfo(savedKeyInfos.get(i).getKeyInfo());
		}
		savedKeyInfos.removeAllElements();
	}

	@Override
	public void paint(Graphics g) {
		System.out.println(this.getX() + "," + this.getY() + ","
				+ this.getWidth() + "," + this.getHeight());
		if (mouseStatus == NOTHING && isOriginalKey) {
			int oneCellWidth = CompositionPanel.getOneCellWidth();
			int oneCellHeight = CompositionPanel.getOneCellHeight();
			this.setLocation(startCol * oneCellWidth, startRow * oneCellHeight);
			this.setSize(colCellNum * oneCellWidth, rowCellNum * oneCellHeight);
		}
		super.paint(g);
		g.drawImage(keyImg, IMAGE_GAP, IMAGE_GAP, this.getWidth()
				- (2 * IMAGE_GAP), this.getHeight() - (2 * IMAGE_GAP), this);
		if (isTextButton && label != null) {
			g.setColor(Color.white);
			// g.drawString(label, (this.getWidth() / 2)-label.length(),
			// this.getHeight() / 2);
			Font f = new Font("Gothic", Font.BOLD,
					CompositionPanel.getOneCellWidth() / 2);
			// code to create f
			Graphics2D g2d = (Graphics2D) g;
			FontRenderContext context = g2d.getFontRenderContext();

			TextLayout txt = new TextLayout(label, f, context);
			Rectangle2D bounds = txt.getBounds();
			int xString = (int) ((getWidth() - bounds.getWidth()) / 2.0);
			int yString = (int) ((getHeight() + bounds.getHeight()) / 2.0);
			// g2 is the graphics object
			g2d.setFont(f);
			g2d.drawString(label, xString, yString);
		} else if (img != null) {
			// rate = width / height;
			double imageRate = (double) img.getWidth(this)
					/ (double) img.getHeight(this);
			double buttonRate = (double) this.getWidth()
					/ (double) this.getHeight();
			int height, width, x, y;
			if (buttonRate > imageRate) {
				height = this.getHeight() - IMAGE_GAP * 4;
				width = (int) (this.getHeight() * imageRate) - IMAGE_GAP * 4;
				x = (this.getWidth() - width) / 2;
				y = IMAGE_GAP * 2;
			} else {
				width = this.getWidth() - IMAGE_GAP * 4;
				height = (int) (this.getWidth() / imageRate) - IMAGE_GAP * 4;
				x = IMAGE_GAP * 2;
				y = (this.getHeight() - height) / 2;
			}
			g.drawImage(img, x, y, width, height, this);
		} else if (keyInfos.size() != 0) {
			g.setColor(Color.white);
			// g.drawString(label, (this.getWidth() / 2)-label.length(),
			// this.getHeight() / 2);
			Font f = new Font("Gothic", Font.BOLD,
					CompositionPanel.getOneCellWidth() / 2);
			// code to create f
			Graphics2D g2d = (Graphics2D) g;
			FontRenderContext context = g2d.getFontRenderContext();

			String str = keyInfos.get(0).getLabel();
			for (int i = 1; i < keyInfos.size(); i++) {
				str += "," + keyInfos.get(i).getLabel();
			}

			TextLayout txt = new TextLayout(str, f, context);
			Rectangle2D bounds = txt.getBounds();
			int xString = (int) ((getWidth() - bounds.getWidth()) / 2.0);
			int yString = (int) ((getHeight() + bounds.getHeight()) / 2.0);
			// g2 is the graphics object
			g2d.setFont(f);
			g2d.drawString(str, xString, yString);
		}

		for (int i = 0; i < vowelSequenceNums.size(); i++) {
			final int row = 4;
			final int col = 5;
			int num = vowelSequenceNums.get(i);

			int x = this.getWidth() * ((num - 1) % col) / col;
			int y = this.getHeight() * ((num - 1) / col) / row;
			g.setColor(Color.YELLOW);
			g.fillArc(x, y, this.getWidth() / col, this.getHeight() / row, 0,
					360);
			g.setColor(Color.BLACK);
			Graphics2D g2d = (Graphics2D) g;

			Font f = new Font("Gothic", Font.BOLD, this.getHeight() / 4);
			g2d.setFont(f);

			g2d.drawString(Integer.toString(num), x + this.getWidth() / 20, y
					+ this.getHeight() / 5);
		}
	}

	class KeyButtonListener implements MouseListener, MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			if (e.getX() == pressedX && e.getY() == pressedY) {
				return;
			}
			KeyButton btn = (KeyButton) e.getSource();
			int oneCellWidth = CompositionPanel.getOneCellWidth();
			int oneCellHeight = CompositionPanel.getOneCellHeight();
			if (mouseStatus == RESIZING) {
				int tmpStartRow = startRow, tmpStartCol = startCol, tmpRowCellNum = rowCellNum, tmpColCellNum = colCellNum;
				int currentX = e.getX(), currentY = e.getY();
				int locationX = btn.getX(), locationY = btn.getY();
				int moveHorizontalCell = 0, moveVerticalCell = 0;
				if (horizontalResizing == LEFT) {
					moveHorizontalCell = (currentX - pressedX) / oneCellWidth;
					tmpStartCol += moveHorizontalCell;
					tmpColCellNum -= moveHorizontalCell;
				} else if (horizontalResizing == RIGHT) {
					moveHorizontalCell = (currentX - pressedX) / oneCellWidth;
					tmpColCellNum += moveHorizontalCell;
				}
				if (verticalResizing == UP) {
					moveVerticalCell = (currentY - pressedY) / oneCellHeight;
					tmpStartRow += moveVerticalCell;
					tmpRowCellNum -= moveVerticalCell;
				} else if (verticalResizing == DOWN) {
					moveVerticalCell = (currentY - pressedY) / oneCellHeight;
					tmpRowCellNum += moveVerticalCell;
				}

				if (parent.checkPossibleToSetKeyButton(tmpStartRow,
						tmpStartCol, tmpRowCellNum, tmpColCellNum, btn)
						&& (moveVerticalCell != 0 || moveHorizontalCell != 0)) {
					if (horizontalResizing == RIGHT) {
						pressedX += (moveHorizontalCell * oneCellWidth);
					}
					if (verticalResizing == DOWN) {
						pressedY += (moveVerticalCell * oneCellHeight);
					}
					KeyButton otherBtn = btn.getParent()
							.getOtherCompositionPanel()
							.findKeyButtonFromMatrix(startRow, startCol);
					otherBtn.saveMatrixAndLocation(tmpStartRow, tmpStartCol,
							tmpRowCellNum, tmpColCellNum);
					btn.saveMatrixAndLocation(tmpStartRow, tmpStartCol,
							tmpRowCellNum, tmpColCellNum);
				}
			} else if (mouseStatus == RELOCATING) {
				int tmpRow, tmpCol;
				tmpRow = CompositionPanel.getLocationRow(btn.getY() + e.getY()
						- pressedY);
				tmpCol = CompositionPanel.getLocationCol(btn.getX() + e.getX()
						- pressedX);
				btn.setLocationWithMatrix(tmpRow, tmpCol);
			} else if (mouseStatus == COPYING) {
				int col = CompositionPanel.getLocationCol(btn.getX() + e.getX()
						- pressedX);
				int row = CompositionPanel.getLocationRow(btn.getY() + e.getY()
						- pressedY);
				copyBtn.setLocationWithMatrix(row, col);

			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			KeyButton btn = (KeyButton) e.getSource();
			if (mouseStatus != RESIZING) {
				horizontalResizing = verticalResizing = NOTHING;
			}
			if (e.getX() <= RESIZE_GAP) { // Left Resizing
				horizontalResizing = LEFT;
			} else if (e.getX() >= btn.getWidth() - RESIZE_GAP) { // Right
																	// Resizing
				horizontalResizing = RIGHT;
			}
			if (e.getY() <= RESIZE_GAP) { // Up Resizing
				verticalResizing = UP;
			} else if (e.getY() >= btn.getHeight() - RESIZE_GAP) { // Down
																	// Resizing
				verticalResizing = DOWN;
			}
			if (horizontalResizing == RIGHT) {
				if (verticalResizing == UP) {
					btn.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
				} else if (verticalResizing == DOWN) {
					btn.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
				} else {
					btn.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
				}
			} else if (horizontalResizing == LEFT) {
				if (verticalResizing == UP) {
					btn.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
				} else if (verticalResizing == DOWN) {
					btn.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
				} else {
					btn.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
				}
			} else if (verticalResizing == UP) {
				btn.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			} else if (verticalResizing == DOWN) {
				btn.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
			} else {
				btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			KeyButton btn = (KeyButton) e.getSource();
			KeyInfo ki = MYKeyManager.getManager().getPressedKeyInfo();
			KeySequence ks = MYKeyManager.getManager().getKeySequence();
			if (ks != null) { // append KeyButton to KeySequence
				ks.appendKeyButtons(btn);
			} else if (ki != null) { // add KeyInfo to ClickedKeybutton
				btn.addKeyInfo(ki);
			} else { // show KeyButton setting
				MYKeyManager.getManager().showKeySetting(
						new KeySettingMainPanel(btn));
			}
			mouseStatus = NOTHING;
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {

		}

		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			KeyButton btn = (KeyButton) e.getSource();
			pressedX = e.getX();
			pressedY = e.getY();
			if (pressedX <= RESIZE_GAP) { // Left Resizing
				mouseStatus = RESIZING;
			} else if (pressedX >= btn.getWidth() - RESIZE_GAP) { // Right
																	// Resizing
				mouseStatus = RESIZING;
			} else if (pressedY <= RESIZE_GAP) { // Up Resizing
				mouseStatus = RESIZING;
			} else if (pressedY >= btn.getHeight() - RESIZE_GAP) { // Down
																	// Resizing
				mouseStatus = RESIZING;
			}

			if (mouseStatus != RESIZING) {
				if (SwingUtilities.isLeftMouseButton(e)) { // ReLocating
					mouseStatus = RELOCATING;
				} else if (SwingUtilities.isRightMouseButton(e)) { // Copy And
																	// Locating
					mouseStatus = COPYING;
					copyBtn = new KeyButton(btn.getStartRow(),
							btn.getStartCol(), btn.getRowCellNum(),
							btn.getColCellNum(), parent, false);
					parent.add(copyBtn);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			KeyButton btn = (KeyButton) e.getSource();
			if (mouseStatus == RELOCATING) {
				int tmpRow = CompositionPanel.getLocationRow(btn.getY()
						+ e.getY() - pressedY);
				int tmpCol = CompositionPanel.getLocationCol(btn.getX()
						+ e.getX() - pressedX);
				if (parent.checkPossibleToSetKeyButton(tmpRow, tmpCol,
						rowCellNum, colCellNum, btn)) {
					btn.getParent()
							.getOtherCompositionPanel()
							.relocatingButton(btn.getStartRow(),
									btn.getStartCol(), btn.getRowCellNum(),
									btn.getColCellNum(), tmpRow, tmpCol,
									rowCellNum, colCellNum);
					btn.saveMatrixAndLocation(tmpRow, tmpCol, rowCellNum,
							colCellNum);

				} else {
					int oneCellWidth = CompositionPanel.getOneCellWidth();
					int oneCellHeight = CompositionPanel.getOneCellHeight();
					btn.setLocation(startCol * oneCellWidth, startRow
							* oneCellHeight);
					btn.setSize(colCellNum * oneCellWidth, rowCellNum
							* oneCellHeight);
					// Error : Can not move that Location
				}
			} else if (mouseStatus == COPYING) {
				int col = CompositionPanel.getLocationCol(btn.getX() + e.getX()
						- pressedX);
				int row = CompositionPanel.getLocationRow(btn.getY() + e.getY()
						- pressedY);
				if (parent.checkPossibleToSetKeyButton(row, col, rowCellNum,
						colCellNum, copyBtn)) {
					parent.addKeyButtonWithOtherPanel(row, col, rowCellNum,
							colCellNum);
				}
				parent.remove(copyBtn);
				copyBtn = null;
				parent.repaint();
			}
			mouseStatus = NOTHING;
			btn.repaint();
		}

	}
}

/**
 * 
 * @author Bae Jinshik
 * 
 *         SavedKeyInfo help load a work when load the work need to be know
 *         sequence of keyInfo It contains sequence information
 * 
 */
class SavedKeyInfo {
	KeyInfo ki;
	int num;

	public SavedKeyInfo(KeyInfo ki, int num) {
		this.ki = ki;
		this.num = num;
	}

	public KeyInfo getKeyInfo() {
		return ki;
	}

	public int getNum() {
		return num;
	}
}

class SavedKeyInfoCompare implements Comparator<SavedKeyInfo> {

	public int compare(final SavedKeyInfo a, final SavedKeyInfo b) {
		if (a.getNum() < b.getNum()) {
			return -1;
		} else if (a.getNum() > b.getNum()) {
			return 1;
		} else {
			return 0;
		}
	}
}
