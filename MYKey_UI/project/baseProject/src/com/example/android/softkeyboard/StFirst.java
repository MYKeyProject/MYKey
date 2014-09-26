package com.example.android.softkeyboard;

import java.util.ArrayList;

public class StFirst extends CombinationState {
	
	public StFirst(){
		repeat = false;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(false);

		combPhon = new int[1];

		changePhonemeCategory(phoArr);
		
		combPhon[0] = calcPhoneme(phoArr.get(0));

		ka.setEnter(true);
		return combPhon;
	}

}
