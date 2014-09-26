package com.example.android.softkeyboard;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;

public class KeyProcessor {
	private Buffer buffer;
	private KoreanAutomata korAt;
	private SoftKeyboard ime;
	private KeyboardView mInputView;
	private KeyInfo keyInfo;
	private int keyCode;
	private int inputCount;
	private boolean isRenew;
	private boolean isInitShift;
	
	private boolean isWordSeparator() {
		String tmpSeparators = ime.getWordSeparators();
		return tmpSeparators.contains(String.valueOf((char) keyCode));
	}


	public void processKey(int keyCode) {
		isInitShift = true;
		
		this.keyCode = keyCode;

		if (isWordSeparator()) {
			if (buffer.getComBuffLength() != 0)
				buffer.commitTyped();
			
			buffer.commitOther(keyCode);
			initAutomata();
		}
		else if (keyCode <= -1 && keyCode >= -8) {
			specialKey();
		}
		else if (keyCode / 1000 == 3) {
			koreanKey();
		}
		else if (keyCode / 1000 == 4){
			
		}
		else {
			otherKey();
		}
		
		
		if(isInitShift)
			ime.initKeyboardShifted();
	}
	
	
	

	private void specialKey() {
		final int KEYCODE_IME_PREFERENCE = -2;
		final int KEYCODE_MODE_CHANGE_TO_KOR = -6;
		final int KEYCODE_MODE_CHANGE_TO_ENG = -7;
		final int KEYCODE_MODE_CHANGE_TO_NUM = -8;
		final int KEYCODE_BLANK = -4;
		
		boolean isKorKeyboard = false;
		

		switch (keyCode) {
		case Keyboard.KEYCODE_DELETE:
			final int length = buffer.getComBuffLength();

			if (length > 1) {
				buffer.deleteComBuff();
				buffer.commitTyped();
			}
			else if (length > 0) {
				buffer.initBuffer();
				buffer.commitDelete();
			}
			else {
				buffer.commitKeyEvent(KeyEvent.KEYCODE_DEL);
				inputCount = 0;
			}
			break;

		case Keyboard.KEYCODE_SHIFT:
			isKorKeyboard = ime.handleShift();
			isInitShift = false;
			break;

		case KEYCODE_MODE_CHANGE_TO_KOR:
		case KEYCODE_MODE_CHANGE_TO_ENG:
		case KEYCODE_MODE_CHANGE_TO_NUM:
			ime.switchKeyboard(keyCode);
			buffer.commitTyped();
			break;

		case Keyboard.KEYCODE_CANCEL:
			buffer.commitTyped();
			ime.requestHideSelf(0);
			if (mInputView != null)
				mInputView.closing();
			break;

		case KEYCODE_BLANK:
			return;
			
		case KEYCODE_IME_PREFERENCE:
			ime.startImePreferenceActivity();
	        return;
		}
		
		if(!isKorKeyboard)
			initAutomata();
	}

	
	private void koreanKey() {
		int korPhoneme = 0;
		boolean isFinal;

		isFinal = korAt.getIsFinalInput();
		
		korPhoneme = keyInfo.getPhoneme(keyCode, isFinal);
		
		
		if (keyInfo.isReentrance() || inputCount == 0){
			isRenew = false;
			buffer.appendPhoneme(korPhoneme);
		}
		else{
			isRenew = true;
			buffer.renewPhoneme(korPhoneme);
		}
		
		inputCount++;

		korAt.startCombine(isRenew);
	}

	
	private void otherKey() {
		
		if (keyCode == 32) {
			if (buffer.getComBuffLength() == 0){
				buffer.commitOther(keyCode);
			}else{
				buffer.commitTyped();
			}
		} 
		else {
			if(isAlpha() && mInputView.isShifted())
				keyCode = Character.toUpperCase(keyCode);
			
			buffer.commitOther(keyCode);
		}

		initAutomata();
	}
	
	
	private boolean isAlpha(){
		if (Character.isLetter(keyCode)) {
            return true;
        } else {
            return false;
        }
	}
	

	public KeyProcessor() {
		buffer = new Buffer();
		korAt = new KoreanAutomata(buffer);
		keyInfo = new KeyInfo();
	}

	
	public void initAutomata(SoftKeyboard ime, KeyboardView mInputView) {
		keyCode = 0;
		inputCount = 0;
		
		this.ime = ime;
		this.mInputView = mInputView;

		buffer.initBuffer(ime);
		korAt.initKoreaAutomataState();
		keyInfo.initKeyInfo();
		
	}
	
	private void initAutomata(){
		keyCode = 0;
		inputCount = 0;
		
		buffer.initBuffer();
		korAt.initKoreaAutomataState();
		keyInfo.initKeyInfo();
		
	}
	
	
	public void processText(CharSequence text){
		InputConnection ic = ime.getCurrentInputConnection();
		
		if (ic == null)
			return;
		
		if (buffer.getComBuffLength() > 0) {
			buffer.commitTyped();
		}
		
		ic.commitText(text, 0);
		ime.initKeyboardShifted();
	}
	
}
