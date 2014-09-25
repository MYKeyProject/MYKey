package korean_automata;

import java.util.*;


/**
 * Calculate letter for first phoneme, vowel and final phoneme.
 * @author sunghoonpark
 *
 */
public class StSingleFinal extends CombinationState {
	
	public StSingleFinal(){
		repeat = false;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(true);
		
		combPhon = new int[1];
		
		changePhonemeCategory(phoArr);
		
		combPhon[0] = calcPhoneme(phoArr.get(0), phoArr.get(1), phoArr.get(2));

		ka.setEnter(true);
		return combPhon;
	}

}
