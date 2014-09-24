package tool.panel.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JPanel;

import tool.frame.ErrorDialog;
import tool.key.KeyButton;
import tool.manager.MYKeyManager;

public class CompositionPanel extends JPanel implements MouseListener,
		MouseMotionListener {
	private static final int NON = -1;
	private static double verticalRate = 0.4;
	private static int oneCellWidth, oneCellHeight;
	private static int col = 20;
	private static int row = 10;
	private boolean isOriginalPanel;
	private CompositionPanel otherPanel = null;
	private Vector<KeyButton> keyButtons = new Vector<KeyButton>();

	private int mouseStartX = NON, mouseStartY = NON;
	private int mouseCurrentX = NON, mouseCurrentY = NON;

	public CompositionPanel(boolean isOriginalPanel) {
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.isOriginalPanel = isOriginalPanel;
		if (isOriginalPanel) {
			MYKeyManager.getManager().setOriginalCompositionPanel(this);
		} else {
			MYKeyManager.getManager().setShiftCompositionPanel(this);
		}
	}

	public static int getOneCellWidth() {
		return oneCellWidth;
	}

	public static int getOneCellHeight() {
		return oneCellHeight;
	}

	public static double getVerticalRate() {
		return verticalRate;
	}

	public static void setVerticalRate(double rate) {
		verticalRate = rate;
	}

	public static Point getPrefferedSize(int width, int height) {
		int cellWidth = width / col;
		int cellHeight = height / row;
		Point point = new Point(cellWidth * col, cellHeight * row);
		return point;
	}

	public static void setMatrix(int row, int col) {
		if (checkPosibleToFixMatrix(row, col)) {
			CompositionPanel.row = row;
			CompositionPanel.col = col;
		}
	}

	public static boolean checkPosibleToFixMatrix(int row, int col) {
		Vector vec = MYKeyManager.getManager().getOriginalKeyButtons();
		if (vec.size() == 0) {
			return true;
		} else if (row <= CompositionPanel.row || col <= CompositionPanel.col) {
			ErrorDialog.error("버튼이 있는 상태에서 행/열 조절은 확대밖에 지원하지 않습니다.");
			return false;
		} else {
			return true;
		}
	}

	public boolean isOriginalPanel() {
		return isOriginalPanel;
	}

	public void setOtherCompositionPanel(CompositionPanel panel) {
		otherPanel = panel;
	}

	public CompositionPanel getOtherCompositionPanel() {
		return otherPanel;
	}

	public void addKeyButtonWithOtherPanel(int startRow, int startCol,
			int rowCellNum, int colCellNum) {
		this.addKeyButton(startRow, startCol, rowCellNum, colCellNum);
		otherPanel.addKeyButton(startRow, startCol, rowCellNum, colCellNum);
	}

	public void addKeyButton(int startRow, int startCol, int rowCellNum,
			int colCellNum) {
		KeyButton kb = new KeyButton(startRow, startCol, rowCellNum,
				colCellNum, this, true);
		this.add(kb);
		keyButtons.add(kb);
		MYKeyManager.getManager().addKeyButton(kb);
		this.repaint();
	}

	public KeyButton loadKeyButton(int startRow, int startCol, int rowCellNum,
			int colCellNum, int keyCode, String text, String imagePath) {
		KeyButton kb = new KeyButton(startRow, startCol, rowCellNum,
				colCellNum, this, true);
		kb.setKeyCode(keyCode);
		kb.setLabelName(text);
		kb.setTextKey();
		kb.setImagePath(imagePath);
		if (imagePath != null) {
			File f = new File(imagePath);
			if (f.exists()) {
				kb.setImageKey();
			} else {
				kb.setImagePath(null);
			}
		}
		this.add(kb);
		keyButtons.add(kb);
		MYKeyManager.getManager().addKeyButton(kb);
		this.repaint();
		return kb;
	}

	public void removeKeyButton(KeyButton btn) {
		keyButtons.remove(btn);
		this.remove(btn);
		this.repaint();
	}

	public void removeKeyButtonWithOtherPanel(KeyButton btn) {
		otherPanel
				.removeKeyButtonOfMatrix(btn.getStartRow(), btn.getStartCol());
		removeKeyButton(btn);
	}

	public void removeKeyButtonOfMatrix(int row, int col) {
		for (int i = 0; i < keyButtons.size(); i++) {
			KeyButton kb = keyButtons.get(i);
			if (kb.getStartRow() == row && kb.getStartCol() == col) {
				kb.removeOnly();
				return;
			}
		}
	}

	public void relocatingButton(int originRow, int originCol,
			int originRowCellNum, int originColCellNum, int targetRow,
			int targetCol, int targetRowCellNum, int targetColCellNum) {
		KeyButton btn = findKeyButtonFromMatrix(originRow, originCol);
		if (btn == null) {
			return;
		}
		btn.saveMatrixAndLocation(targetRow, targetCol, targetRowCellNum,
				targetColCellNum);
		this.repaint();
	}

	public KeyButton findKeyButtonFromMatrix(int row, int col) {
		for (int i = 0; i < keyButtons.size(); i++) {
			KeyButton btn = keyButtons.get(i);
			if (btn.getStartRow() == row && btn.getStartCol() == col) {
				return btn;
			}
		}
		return null;
	}

	public static int getColumn() {
		return col;
	}

	public static int getRow() {
		return row;
	}

	public static int getLocationRow(int y) {
		return (y - (y % oneCellHeight)) / (oneCellHeight);
	}

	public static int getLocationCol(int x) {
		return (x - (x % oneCellWidth)) / (oneCellWidth);
	}

	public void registerMouseListeners() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void unregisterMouseListeners() {
		this.removeMouseListener(this);
		this.removeMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.getWidth() == 0 || this.getHeight() == 0) {
			return;
		}
		oneCellWidth = this.getWidth() / col;
		oneCellHeight = this.getHeight() / row;
		g.setColor(Color.gray);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				g.drawRect(j * oneCellWidth, i * oneCellHeight, oneCellWidth,
						oneCellHeight);
			}
		}
		if (mouseStartX != NON && mouseCurrentX != NON) {
			int startX = mouseStartX, endX = mouseCurrentX;
			int startY = mouseStartY, endY = mouseCurrentY;
			if (startX > endX) {
				int tmp = startX;
				startX = endX;
				endX = tmp;
			}
			if (startY > endY) {
				int tmp = startY;
				startY = endY;
				endY = tmp;
			}
			g.setColor(Color.GRAY);
			g.fillRoundRect(startX, startY, endX - startX + oneCellWidth, endY
					- startY + oneCellHeight, (endX - startX) / oneCellWidth,
					(endY - startY) / oneCellHeight);
			g.setColor(Color.CYAN);
			g.drawRoundRect(startX, startY, endX - startX + oneCellWidth, endY
					- startY + oneCellHeight, (endX - startX) / oneCellWidth,
					(endY - startY) / oneCellHeight);
		}
	}

	public int getXLeftTopLocation(int x) {
		int xloc = x - (x % oneCellWidth);
		if (xloc < 0) {
			xloc = 0;
		} else if (xloc >= col * oneCellWidth) {
			xloc = (col - 1) * oneCellWidth;
		}
		return xloc;
	}

	public int getYLeftTopLocation(int y) {
		int yloc = y - (y % oneCellHeight);
		if (yloc < 0) {
			yloc = 0;
		} else if (yloc >= row * oneCellHeight) {
			yloc = (row - 1) * oneCellHeight;
		}
		return yloc;
	}

	public void mouseDragged(MouseEvent e) {
		mouseCurrentX = getXLeftTopLocation(e.getX());
		mouseCurrentY = getYLeftTopLocation(e.getY());
		this.repaint();
	}

	public void mousePressed(MouseEvent e) {

		if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
			mouseStartX = mouseStartY = mouseCurrentX = mouseCurrentY = NON;
			this.repaint();
			return;
		}
		mouseStartX = getXLeftTopLocation(e.getX());
		mouseStartY = getYLeftTopLocation(e.getY());

	}

	public void mouseReleased(MouseEvent e) {
		if (mouseStartX != NON && mouseCurrentX != NON) {
			int startCol = getLocationCol(mouseStartX), endCol = getLocationCol(mouseCurrentX);
			int startRow = getLocationRow(mouseStartY), endRow = getLocationRow(mouseCurrentY);
			if (startCol > endCol) {
				int tmp = startCol;
				startCol = endCol;
				endCol = tmp;
			}
			if (startRow > endRow) {
				int tmp = startRow;
				startRow = endRow;
				endRow = tmp;
			}
			int colCellNum = endCol - startCol + 1, rowCellNum = endRow
					- startRow + 1;
			if (!checkPossibleToSetKeyButton(startRow, startCol, rowCellNum,
					colCellNum, null)) {
				// ////////////////////////////////////////////////////////////////////////
				// Error 처리
			} else {
				addKeyButtonWithOtherPanel(startRow, startCol, rowCellNum,
						colCellNum);
				this.repaint();
			}
		}
		mouseStartX = mouseStartY = mouseCurrentX = mouseCurrentY = NON;
		this.repaint();
	}

	public boolean checkPossibleToSetKeyButton(int row, int col,
			int rowCellNum, int colCellNum, KeyButton btn) {
		if (row < 0 || col < 0 || rowCellNum <= 0 || colCellNum <= 0
				|| col + colCellNum > this.col || row + rowCellNum > this.row) {
			return false;
		}
		int startX = col, startY = row, endX = col + colCellNum, endY = row
				+ rowCellNum;
		for (int i = 0; i < keyButtons.size(); i++) {
			KeyButton kb = keyButtons.get(i);
			if (!kb.equals(btn)) {
				if (startX >= kb.getStartCol() + kb.getColCellNum()) {
					continue;
				} else if (endX <= kb.getStartCol()) {
					continue;
				} else if (startY >= kb.getStartRow() + kb.getRowCellNum()) {
					continue;
				} else if (endY <= kb.getStartRow()) {
					continue;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
