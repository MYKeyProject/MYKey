package korean_automata;

import java.util.*;

/**
 * Calculate alone phoneme.
 * @author sunghoonpark
 *
 */
public class StFirst extends CombinationState {
	
	public StFirst(){
		repeat = false;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(false);

		combPhon = new int[1];

		changePhonemeCategory(phoArr);
		
		combPhon[0] = calcPhoneme(phoArr.get(0));

		ka.setEnter(true);
		return combPhon;
	}

}
