package korean_automata;

import java.util.*;

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
