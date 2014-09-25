package korean_automata;

import java.util.*;

/**
 * Calculate two letters, one of letters is first phoneme, vowel and final phoneme, and the other is alone phoneme.   
 * @author sunghoonpark
 *
 */
public class StSingleFinalAndFirst extends CombinationState {
	
	public StSingleFinalAndFirst(){
		repeat = false;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(true);
		
		combPhon = new int[2];
		
		combPhon[0] = calcPhoneme(phoArr.get(0), phoArr.get(1), phoArr.get(2));
		
		combPhon[1] = calcPhoneme(PhonemeCategoryConverter.changePhonemeIdxFnToFr(phoArr.get(3)));
		
		ka.setEnter(true);
		return combPhon;
	}

}
