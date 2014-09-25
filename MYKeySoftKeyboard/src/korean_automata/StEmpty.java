package korean_automata;

import java.util.*;

/**
 * Initial state of Korean Automata.
 * Nothing to do.
 * @author sunghoonpark
 *
 */
public class StEmpty extends CombinationState {
	
	public StEmpty(){
		repeat = true;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
		ka.setIsFinalInput(false);

		ka.setEnter(false);
		return null;
	}

}
