package com.example.android.softkeyboard;

import java.util.ArrayList;

public class Proc1 extends CombinationState {
	
	public Proc1(){
		repeat = true;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		Buffer tmpBuffer = ka.getBuffer();
		
		combPhon = new int[1];
		
		combPhon[0] = calcPhoneme(phoArr.get(0), phoArr.get(1));
		
		tmpBuffer.appendLetter(combPhon);
		tmpBuffer.commitTyped();
		
		tmpBuffer.handingPhoArrAt(2);

		ka.setKoreanAutomataState(KoreanAutomata.ST_FIRST_AREA, KoreanAutomata.ST_FIRST);
		
		ka.setEnter(false);
		
		return null;
	}

}
