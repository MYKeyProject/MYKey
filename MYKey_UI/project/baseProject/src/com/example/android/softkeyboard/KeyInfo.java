package com.example.android.softkeyboard;

import java.util.HashMap;


public class KeyInfo {
	private HashMap<String, Integer> keyHashMap = null;
	private String prevKey = null;
	private String currentKey = null;
	private boolean isReentrance = false;
	
	
	
	public int getPhoneme(int keyCode, boolean isFinal){
		int phoneme = 0;
		
		if(prevKey == null)
			currentKey = Integer.toString(keyCode);
		else
			currentKey = prevKey + "-" + keyCode;
		
		
		if(keyHashMap.get(currentKey) == null){
			isReentrance = false;
			prevKey = null;
			currentKey = Integer.toString(keyCode);	
		}
		else{
			isReentrance = true;
		}
		
		phoneme = keyHashMap.get(currentKey);
		prevKey = currentKey;
		
		if(isFinal)
			phoneme = PhonemeCategoryConverter.changePhonemeIdxFrToFn(phoneme);
		
		return phoneme;
	}
	
	
	
	public void initKeyInfo(){
		prevKey = null;
		currentKey = null;
		isReentrance = false;
	}
	
	public boolean isReentrance(){
		return !isReentrance;
	}
	
	public KeyInfo(){
		KeyMap tmpKeyMap = new KeyMap();
		
		
		keyHashMap = tmpKeyMap.getKeyMap();
		
		isReentrance = false;
	}
	
}