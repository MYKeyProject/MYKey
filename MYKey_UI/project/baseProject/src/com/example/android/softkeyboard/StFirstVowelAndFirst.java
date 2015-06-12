package com.example.android.softkeyboard;

import java.util.ArrayList;




public class StFirstVowelAndFirst extends CombinationState {
	
	public StFirstVowelAndFirst(){
		repeat = false;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(true);
		
		combPhon = new int[2];
		
		changePhonemeCategory(phoArr);
		
		combPhon[0] = calcPhoneme(phoArr.get(0), phoArr.get(1));
		
		combPhon[1] = calcPhoneme(PhonemeCategoryConverter.changePhonemeFnAFrToAl(phoArr.get(2)));
		
		ka.setEnter(true);
		return combPhon;
	}

}
