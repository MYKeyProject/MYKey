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
package com.example.android.softkeyboard;

import java.util.*;

import android.content.*;
import android.inputmethodservice.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.view.inputmethod.*;

import com.example.android.mykey.*;
import com.example.android.softkeyboard.LatinKeyboard.LatinKey;

public class SoftKeyboard extends InputMethodService implements
		KeyboardView.OnKeyboardActionListener {
	static final boolean DEBUG = false;
	static final boolean PROCESS_HARD_KEYS = true;

	private String mWordSeparators;
	private int mLastDisplayWidth;

	private KeyProcessor keyPro;
	private InputMethodManager mInputMethodManager;
	private LatinKeyboardView mInputView;

	private LatinKeyboard mSymbolsKeyboard;
	private LatinKeyboard mSymbolsShiftedKeyboard;
	private LatinKeyboard mKorQwertyKeyboard;
	private LatinKeyboard mCurKeyboard;
	private LatinKeyboard mEngQwertyKeyboard;
	private LatinKeyboard mKorQwertyShiftKeyboard;

	private ArrayList<LatinKeyboard> keyboardArr;
	private ArrayList<LatinKey> strKeyArr;

	@Override
	public void onCreate() {
		super.onCreate();
		mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		mWordSeparators = getResources().getString(R.string.word_separators);

		keyPro = new KeyProcessor();

	}

	@Override
	public void onInitializeInterface() {
		if (mKorQwertyKeyboard != null) {
			int displayWidth = getMaxWidth();

			if (displayWidth == mLastDisplayWidth)
				return;

			mLastDisplayWidth = displayWidth;
		}

		keyboardArr = new ArrayList<LatinKeyboard>();

		mKorQwertyKeyboard = new LatinKeyboard(this, R.xml.mykey_keyboard);
		mSymbolsKeyboard = new LatinKeyboard(this, R.xml.symbols);
		mSymbolsShiftedKeyboard = new LatinKeyboard(this, R.xml.symbols_shift);
		mEngQwertyKeyboard = new LatinKeyboard(this, R.xml.en_qwerty);
		mKorQwertyShiftKeyboard = new LatinKeyboard(this,R.xml.mykey_shift_keyboard);

		keyboardArr.add(mKorQwertyKeyboard);
		keyboardArr.add(mSymbolsKeyboard);
		keyboardArr.add(mSymbolsShiftedKeyboard);
		keyboardArr.add(mEngQwertyKeyboard);
		keyboardArr.add(mKorQwertyShiftKeyboard);

		getStringKeys();
	}

	@Override
	public View onCreateInputView() {
		mInputView = (LatinKeyboardView) getLayoutInflater().inflate(
				R.layout.input, null);
		mInputView.setOnKeyboardActionListener(this);
		mInputView.setKeyboard(mKorQwertyKeyboard);

		return mInputView;
	}

	@Override
	public void onStartInput(EditorInfo attribute, boolean restarting) {
		super.onStartInput(attribute, restarting);

		getSettings();
		setStringKeys();
	}

	@Override
	public void onFinishInput() {
		super.onFinishInput();

		mCurKeyboard = mKorQwertyKeyboard;
		if (mInputView != null) {
			mInputView.closing();
		}
		
	}

	@Override
	public void onStartInputView(EditorInfo attribute, boolean restarting) {
		super.onStartInputView(attribute, restarting);

		keyPro.initAutomata(this, mInputView);

		mInputView.initShiftState();

		mCurKeyboard = mKorQwertyKeyboard;
		mInputView.setKeyboard(mCurKeyboard);
		mInputView.closing();
	}

	@Override
	public void onKey(int primaryCode, int[] keyCodes) {
		keyPro.processKey(primaryCode);
	}

	public void switchKeyboard(int keyCode) {
		final int TO_KOR = -6;
		final int TO_ENG = -7;
		final int TO_NUM = -8;

		if (mInputView == null) {
			return;
		}

		Keyboard current = mInputView.getKeyboard();

		switch (keyCode) {
		case TO_KOR:

			if (current == mKorQwertyKeyboard
					|| current == mKorQwertyShiftKeyboard) {
				return;
			} else if (current == mEngQwertyKeyboard) {
				current = mKorQwertyKeyboard;
			} else if (current == mSymbolsKeyboard
					|| current == mSymbolsShiftedKeyboard) {
				current = mKorQwertyKeyboard;
			}

			break;

		case TO_ENG:

			if (current == mKorQwertyKeyboard
					|| current == mKorQwertyShiftKeyboard) {
				current = mEngQwertyKeyboard;
			} else if (current == mEngQwertyKeyboard) {
				return;
			} else if (current == mSymbolsKeyboard
					|| current == mSymbolsShiftedKeyboard) {
				current = mEngQwertyKeyboard;
			}

			break;

		case TO_NUM:

			if (current == mKorQwertyKeyboard
					|| current == mKorQwertyShiftKeyboard) {
				current = mSymbolsKeyboard;
			} else if (current == mEngQwertyKeyboard) {
				current = mSymbolsKeyboard;
			} else if (current == mSymbolsKeyboard
					|| current == mSymbolsShiftedKeyboard) {
				return;
			}

			break;

		}
		mInputView.setKeyboard(current);
		mInputView.initShiftState();
	}

	@Override
	public void onText(CharSequence text) {
		keyPro.processText(text);
	}

	public void initKeyboardShifted() {
		Keyboard currentKeyboard = mInputView.getKeyboard();
		if (currentKeyboard == mKorQwertyShiftKeyboard
				&& !mInputView.getToggled()) {
			mInputView.setKeyboard(mKorQwertyKeyboard);
		} else if (currentKeyboard == mSymbolsShiftedKeyboard
				&& !mInputView.getToggled()) {
			mInputView.setKeyboard(mSymbolsKeyboard);
		}
		mInputView.initShifted();
	}

	public boolean handleShift() {
		if (mInputView == null) {
			return false;
		}

		Keyboard currentKeyboard = mInputView.getKeyboard();

		if (mKorQwertyKeyboard == currentKeyboard) {
			mInputView.setKeyboard(mKorQwertyShiftKeyboard);
			mInputView.setShifted(true);
			return true;
		} else if (currentKeyboard == mKorQwertyShiftKeyboard) {
			if (mInputView.getShifted() && !mInputView.getToggled()) {
				mInputView.setShifted(true);
			} else if (mInputView.getShifted() && mInputView.getToggled()) {
				mInputView.setKeyboard(mKorQwertyKeyboard);
				mInputView.initShiftState();
			}
			return true;
		} else if (currentKeyboard == mEngQwertyKeyboard) {
			mInputView.setShifted(true);
		} else if (currentKeyboard == mSymbolsKeyboard) {
			mInputView.setKeyboard(mSymbolsShiftedKeyboard);
			mInputView.setShifted(true);
			return true;
		} else if (currentKeyboard == mSymbolsShiftedKeyboard) {
			if (mInputView.getShifted() && !mInputView.getToggled()) {
				mInputView.setShifted(true);
			} else if (mInputView.getShifted() && mInputView.getToggled()) {
				mInputView.setKeyboard(mSymbolsKeyboard);
				mInputView.initShiftState();
			}
			return true;
		}

		mInputView.invalidateAllKeys();
		return false;
	}

	public String getWordSeparators() {
		return mWordSeparators;
	}

	
	private void getStringKeys() {
		strKeyArr = new ArrayList<LatinKey>();

		Iterator keyboardIt = keyboardArr.iterator();
		while (keyboardIt.hasNext()) {
			LatinKeyboard tmpKeyboard = (LatinKeyboard) keyboardIt.next();
			List tmpList = tmpKeyboard.getKeys();

			for (int i = 0; i < tmpList.size(); i++) {
				LatinKey tmpKey = (LatinKey) tmpList.get(i);
				if (tmpKey.codes[0] / 100 == 44)
					strKeyArr.add(tmpKey);
			}

		}
	}

	private void setStringKeys() {
		StringKeyHandler strKeyHnd = StringKeyHandler
				.getInstance(getApplicationContext());
		strKeyHnd.setStringKeyInfo(strKeyArr);
	}

	public void startImePreferenceActivity() {
		Intent intent = new Intent();
		intent.setClass(this, ImePreferencesActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private boolean mVibrateOn = false;
	private boolean mSoundOn = false;
	private boolean mSilentMode = false;

	private Vibrator mVibrator = null;
	private AudioManager mAudioManager = null;

	public void getSettings() {
		SharedPreferences setting = getSharedPreferences(
				ImePreferencesActivity.PREF_NAME, Context.MODE_PRIVATE);

		mVibrateOn = setting.getBoolean(ImePreferencesActivity.PREF_VIBRATION,
				false);
		mSoundOn = setting.getBoolean(ImePreferencesActivity.PREF_SOUND, false);

	}

	private void setVibrate() {
		if (mVibrateOn)
			mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		if(mVibrator != null)
			mVibrator.vibrate(40);
	}

	private void setSound() {
		if (mAudioManager != null)
			mSilentMode = (mAudioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL);
		else
			mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		if (mSoundOn && !mSilentMode) {
			int sound = AudioManager.FX_KEYPRESS_STANDARD;
			if(mAudioManager != null)
				mAudioManager.playSoundEffect(sound, 1.0f);
		}
	}

	@Override
	public void swipeRight() {
	}

	@Override
	public void swipeLeft() {
	}

	@Override
	public void swipeDown() {
	}

	@Override
	public void swipeUp() {
	}

	@Override
	public void onPress(int primaryCode) {
		mInputView.setPreviewEnabled(false);
		setVibrate();
		setSound();
	}

	@Override
	public void onRelease(int primaryCode) {
	}
}
