package com.example.android.softkeyboard;


import java.util.ArrayList;


public class StSingleFinal extends CombinationState {
	
	public StSingleFinal(){
		repeat = false;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(true);
		
		combPhon = new int[1];
		
		changePhonemeCategory(phoArr);
		
		combPhon[0] = calcPhoneme(phoArr.get(0), phoArr.get(1), phoArr.get(2));

		ka.setEnter(true);
		return combPhon;
	}

}
