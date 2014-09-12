package key_process;

import java.util.*;

import com.android.mykeysoftkeyboard.*;

import android.view.*;

public class Buffer {

	private SoftKeyboard ime;
	private StringBuilder comBuffer;
	private ArrayList<Integer> phoBuffer;
	
	
	public void removePhoArr(){
		int tmpPhoneme = phoBuffer.get(phoBuffer.size()-1);
		phoBuffer.clear();
		phoBuffer.add(tmpPhoneme);
	}

	public void handingPhoArrAt(int i) {
		for (int j = 0; j < i; j++) {
			phoBuffer.remove(0);
		}
	}
	
	public ArrayList<Integer> getPhoBuffer() {
		return phoBuffer;
	}


	public void appendLetter(int[] phonemeInt) {
		comBuffer.delete(0, comBuffer.length());

		for (int i = 0; i < phonemeInt.length; i++) {
			comBuffer.append(String.valueOf((char)phonemeInt[i]));
		}

		ime.getCurrentInputConnection().setComposingText(comBuffer, 1);
	}

	public void appendPhoneme(int phoneme) {
		phoBuffer.add(phoneme);
	}

	public void renewPhoneme(int phoneme) {
		phoBuffer.remove(phoBuffer.size() - 1);
		phoBuffer.add(phoneme);
	}

	public int getComBuffLength() {
		return comBuffer.length();
	}

	public void deleteComBuff() {
		comBuffer.deleteCharAt(comBuffer.length() - 1);
	}

	public void commitTyped() {
		if (comBuffer.length() > 0) {
			ime.getCurrentInputConnection().commitText(comBuffer, comBuffer.length());
			comBuffer.setLength(0);
		}
	}

	public void commitOther(int keyCode) {
		commitTyped();
		ime.getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
	}

	public void commitDelete() {
		ime.getCurrentInputConnection().commitText("", 0);
	}

	public void commitKeyEvent(int keyEventCode) {
		ime.getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
		ime.getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
	}

	
	public Buffer() {
		comBuffer = new StringBuilder();
		phoBuffer = new ArrayList<Integer>();

	}

	public void initBuffer(SoftKeyboard ime) {
		this.ime = ime;
		initBuffer();
	}
	
	public void initBuffer(){
		comBuffer.setLength(0);
		phoBuffer.clear();
	}
}
