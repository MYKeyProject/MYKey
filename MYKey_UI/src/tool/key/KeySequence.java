package tool.key;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.nio.file.Paths;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tool.manager.MYKeyManager;




public class KeySequence extends JPanel implements MouseListener, MouseMotionListener{
	private KeyInfo parent;
	private Vector<KeyButton> keyButtons = new Vector<KeyButton>();
	private boolean isRemovable = false;
	private static Image removeImg = new ImageIcon(Paths.get("").toAbsolutePath()
			.toString()
			+ File.separator
			+ "res"
			+ File.separator
			+ "img"
			+ File.separator
			+ "remove.jpg").getImage();
	
	public KeySequence(KeyInfo parent){
		this.setBackground(Color.red);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.parent = parent;
		this.setVisible(true);
		this.setOpaque(true);
	}
	/**
	 * append KeyButton to keysequence
	 * use it when KeySequence is not appended 
	 * @param KeyButton
	 */
	public void appendKeyButtons(KeyButton btn){
		if(btn.getKeyStatus() == KeyInfo.EMPTY_KEY){
			keyButtons.add(btn);	
		}else if(parent.getKeyStatus() == btn.getKeyStatus()){
			keyButtons.add(btn);
		}else{
			//error.............
		}
	}
	public int getNumOfKeyButton(){
		return keyButtons.size();
	}
	public Vector<KeyButton> getKeyButtons(){
		return keyButtons;
	}
	public KeyInfo getKeyInfo(){
		return parent;
	}
	
	public boolean checkPossibleToAdd(){
		Vector<KeySequence> vec = MYKeyManager.getManager().getAllKeySequences();
		int status;
		if(keyButtons.size() < 2){
			return false;
		}
		KeyButton btn = keyButtons.get(0);
		status = btn.getKeyStatus();
		boolean allSame = true;
		for(int i=1;i<keyButtons.size();i++){
			KeyButton button = keyButtons.get(i);
			if(status == KeyInfo.EMPTY_KEY && button.getKeyStatus() != KeyInfo.EMPTY_KEY){
				status = button.getKeyStatus();
			}else if(status != KeyInfo.EMPTY_KEY && button.getKeyStatus() != status){
				return false;
			}
			if(btn != keyButtons.get(i)){
				allSame = false;
			}
		}
		if(allSame){
			return false;
		}
		for(int i=0;i<vec.size();i++){
			if(vec.get(i).equals(this)){
				return false;
			}
		}		
		return true;
	}
	
	public void update(){
		if(!checkPossibleToAdd()){
			//  error cannot update KeySuequence..... and init keysequence
			return;
		}
		for(int i=0;i<keyButtons.size();i++){
			keyButtons.get(i).increaseKeySequenceNum();
		}
		parent.addKeySequence(this);
		MYKeyManager.getManager().addKeySequence(this);
		MYKeyManager.getManager().setSequenceKeyInfo(parent);
	}
	public void remove(){
		for(int i=0;i<keyButtons.size();i++){
			keyButtons.get(i).decreaseKeySequenceNum();
		}
		parent.removeKeySequence(this);
		MYKeyManager.getManager().removeKeySequence(this);
		MYKeyManager.getManager().setSequenceKeyInfo(parent);
	}
	
	public boolean hasKeyButton(KeyButton btn){
		for(int i=0;i<keyButtons.size();i++){
			if(keyButtons.get(i) == btn){
				return true;
			}
		}
		return false;
	}
	
	public boolean equals(KeySequence ks){
		if(this.getKeyButtons().size() != ks.getKeyButtons().size()){
			return false;
		}
		for(int i=0;i<this.getKeyButtons().size();i++){
			if(this.getKeyButtons().get(i) != ks.getKeyButtons().get(i)){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(isRemovable){
			g.drawImage(removeImg, this.getWidth()*9/10, 0, this.getWidth()/10, this.getHeight()/10,this);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		for(int i=0;i<keyButtons.size();i++){
			keyButtons.get(i).addSequenceNum(i+1);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for(int i=0;i<keyButtons.size();i++){
			keyButtons.get(i).removeSequenceNums();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(isRemovable){
			for(int i=0;i<keyButtons.size();i++){
				keyButtons.get(i).removeSequenceNums();
			}
			remove();
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		KeySequence parent = (KeySequence)e.getSource();
		if(e.getX() >= parent.getWidth()*9/10 && e.getY() <= parent.getHeight()/10){
			isRemovable = true;
		}else{
			isRemovable = false;
		}
		this.repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		KeySequence parent = (KeySequence)e.getSource();
		if(e.getX() >= parent.getWidth()*9/10 && e.getY() <= parent.getHeight()/10){
			isRemovable = true;
		}else{
			isRemovable = false;
		}
		this.repaint();
	}
}
