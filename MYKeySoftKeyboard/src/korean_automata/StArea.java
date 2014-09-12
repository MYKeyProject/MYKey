package korean_automata;

import java.util.*;

public class StArea extends CombinationState {

	public StArea() {
		repeat = false;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(false);
		
		combPhon = new int[1];
		
		combPhon[0] = calcPhoneme(phoArr.get(0));
		
		ka.setEnter(true);
		
		return combPhon;
	}

}
