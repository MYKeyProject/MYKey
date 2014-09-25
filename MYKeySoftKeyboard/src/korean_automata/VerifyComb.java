package korean_automata;

import java.util.*;

import key_process.*;

/**
 * Verify that possibility of combine by first_final phoneme and second_final phoneme.
 * If is it possible, state will be ST_MULTI_FINAL, if not, state will be ST_SINGLE_FINAL_AND_FIRST.
 * @author sunghoonpark
 *
 */
public class VerifyComb extends CombinationState {
	
	public VerifyComb(){
		repeat = true;
	}

	@Override
	public int[] combine(ArrayList<Integer> phoArr, KoreanAutomata ka) {
				int firstFinal = phoArr.get(2);
				int secondFinal = phoArr.get(3);
				int result;
				
				if(secondFinal / 100 == 40){
					result = FinalCombinationInfo.ERROR;
				}
				else{
					result = FinalCombinationInfo.checkFinalComb(firstFinal,secondFinal);
				}
				
				if (result == FinalCombinationInfo.ERROR)
					ka.setKoreanAutomataState(KoreanAutomata.ST_SINGLE_FINAL_AND_FIRST, ka.getPreviousState());
				else
					ka.setKoreanAutomataState(KoreanAutomata.ST_MULTI_FINAL, ka.getPreviousState());

				ka.setEnter(false);
				return null;
	}

}
