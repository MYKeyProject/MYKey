package korean_automata;

import java.util.*;

/**
 * Calculate letter for first phoneme and vowel.
 * @author sunghoonpark
 *
 */
public class StFirstVowel extends CombinationState {
	
	public StFirstVowel(){
		repeat = false;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(true);
		
		combPhon = new int[1];
		
		changePhonemeCategory(phoArr);
		
		combPhon[0] = calcPhoneme(phoArr.get(0), phoArr.get(1));
		
		ka.setEnter(true);
		return combPhon;
	}

}
