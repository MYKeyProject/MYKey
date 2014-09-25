package korean_automata;

import java.util.*;

import key_process.*;

/**
 * Handling finish state and transition for new state from ST_FIRST_VOWEL to ST_FIRST
 * Calculate one letter by first phoneme with vowel and print it to device.
 * Also print one letter by first phoneme.
 * @author sunghoonpark
 */
public class Proc1 extends CombinationState {
	
	public Proc1(){
		repeat = true;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		combPhon = new int[1];
		
		combPhon[0] = calcPhoneme(phoArr.get(0), phoArr.get(1));

		Buffer tmpBuffer = ka.getBuffer();

		tmpBuffer.appendLetter(combPhon);
		tmpBuffer.commitTyped();

		tmpBuffer.handingPhoArrAt(2);

		ka.setKoreanAutomataState(KoreanAutomata.ST_FIRST_VOWEL, KoreanAutomata.ST_FIRST);
		ka.setEnter(false);
		
		return null;
	}

}
