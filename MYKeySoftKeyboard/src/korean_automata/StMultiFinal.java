package korean_automata;

import java.util.*;

public class StMultiFinal extends CombinationState {
	
	public StMultiFinal(){
		repeat = false;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(true);
		
		combPhon = new int[1];

		combPhon[0] = calcPhoneme(phoArr.get(0), phoArr.get(1), phoArr.get(2), phoArr.get(3));
		
		ka.setEnter(true);
		return combPhon;
	}

}
