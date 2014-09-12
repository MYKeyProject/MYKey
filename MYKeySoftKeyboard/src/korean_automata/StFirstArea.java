package korean_automata;

import java.util.*;

public class StFirstArea extends CombinationState {

	public StFirstArea() {
		repeat = false;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(false);
		
		combPhon = new int[2];
		
		changePhonemeCategory(phoArr);
		
		combPhon[0] = calcPhoneme(phoArr.get(0));
		combPhon[1] = calcPhoneme(phoArr.get(1));
		
		ka.setEnter(true);
		
		return combPhon;
	}

}
