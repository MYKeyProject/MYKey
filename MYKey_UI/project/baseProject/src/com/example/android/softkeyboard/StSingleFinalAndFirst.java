package com.example.android.softkeyboard;


import java.util.ArrayList;

public class StSingleFinalAndFirst extends CombinationState {
	
	public StSingleFinalAndFirst(){
		repeat = false;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(true);
		
		combPhon = new int[2];
		
		combPhon[0] = calcPhoneme(phoArr.get(0), phoArr.get(1), phoArr.get(2));
		
		combPhon[1] = calcPhoneme(PhonemeCategoryConverter.changePhonemeIdxFnToFr(phoArr.get(3)));
		
		ka.setEnter(true);
		return combPhon;
	}

}
