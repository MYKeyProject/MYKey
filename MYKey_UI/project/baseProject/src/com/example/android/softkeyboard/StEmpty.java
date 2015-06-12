package com.example.android.softkeyboard;

import java.util.ArrayList;


public class StEmpty extends CombinationState {
	
	public StEmpty(){
		repeat = true;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(false);

		ka.setEnter(false);
		return null;
	}

}
