package com.example.android.softkeyboard;

import java.util.ArrayList;

public class StError extends CombinationState {
	
	public StError(){
		repeat = true;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.getBuffer().commitTyped();
		
		ka.initKoreaAutomataState();

		ka.getBuffer().removePhoArr();
		
		changePhonemeCategory(phoArr);

		ka.setEnter(true);
		return null;
	}

}
