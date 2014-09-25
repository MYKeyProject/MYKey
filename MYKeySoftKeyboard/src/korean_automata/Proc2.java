package korean_automata;

import java.util.*;

import key_process.*;

/**
 * Handling finish state and transition for new state from ST_SINGLE_FINAL to ST_FIRST
 * Calculate one letter by first phoneme with vowel, final phoneme and print it to device.
 * Also print one letter by first phoneme.
 * @author sunghoonpark
 */
public class Proc2 extends CombinationState {

	public Proc2() {
		repeat = true;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		combPhon = new int[1];

		combPhon[0] = calcPhoneme(phoArr.get(0), phoArr.get(1), phoArr.get(2));

		Buffer tmpBuffer = ka.getBuffer();

		tmpBuffer.appendLetter(combPhon);
		tmpBuffer.commitTyped();

		tmpBuffer.handingPhoArrAt(3);

		ka.setKoreanAutomataState(KoreanAutomata.ST_FIRST_VOWEL,KoreanAutomata.ST_FIRST);
		ka.setEnter(false);

		return null;
	}

}
