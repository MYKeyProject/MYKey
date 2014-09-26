package com.example.android.softkeyboard;

import java.util.ArrayList;


public abstract class CombinationState {
	protected boolean repeat;
	protected int combPhon[];
	abstract public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka);
	
	
	
	public boolean isRepeat(){
		return repeat;
	}
	
	protected void changePhonemeCategory(ArrayList<Integer> phoArr){
		if (phoArr.get(0) / 100 == 42) {
			phoArr.set(0, PhonemeCategoryConverter.changePhonemeIdxFnToFr(phoArr.get(0)));
		}
	}
	
	
	protected int calcPhoneme(int phoneme){
		int resultPhoneme = 0;
		
		switch(phoneme/100){
		case 0:
		case 40:
		case 42:
			resultPhoneme = PhonemeCategoryConverter.changePhonemeFnAFrToAl(phoneme) + 12593;
			break;
			
		case 41:
			resultPhoneme = (phoneme - 4100)+ 12623;
			break;
			
		case 82:
		case 87:
			resultPhoneme = phoneme;
		}
		
		return resultPhoneme;
	}
	
	protected int calcPhoneme(int first, int vowel){
		int resultPhoneme = 0;
		
		resultPhoneme = 44032 + (first - 4000) * 21 * 28 + (vowel  - 4100) * 28 + 0;
		
		return resultPhoneme;
	}
	
	protected int calcPhoneme(int first, int vowel, int finalPhoneme){
		int resultPhoneme = 0;
		
		resultPhoneme = 44032 + (first - 4000) * 21 * 28 + (vowel - 4100) * 28 + (finalPhoneme - 4200);
		
		return resultPhoneme;
	}
	
	protected int calcPhoneme(int first, int vowel, int firFinal, int secFinal){
		int resultPhoneme = 0;
		int resultFinal = 0;
		
		resultFinal = FinalCombinationInfo.checkFinalComb(firFinal, secFinal);
		resultFinal += 4200;
		
		resultPhoneme = calcPhoneme(first, vowel, resultFinal);
		
		return resultPhoneme;
	}
}
