package korean_automata;

import java.util.*;

/**
 * Calculate letter for first phoneme, vowel, first_final phoneme and second_final phoneme.
 * @author sunghoonpark
 *
 */
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
