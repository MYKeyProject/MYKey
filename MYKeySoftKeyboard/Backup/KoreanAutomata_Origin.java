package korean_automata;

import java.util.*;

import key_process.*;

public class KoreanAutomata_Origin {
	public static final int ST_EMPTY = 0; 						// 초기 EMPTY상태
	public static final int ST_FIRST = 1; 						// 초성
	public static final int ST_VOWEL = 2; 						// 중성
	public static final int ST_AREA = 3;						// 아레아
	public static final int ST_FIRST_VOWEL = 4; 				// 초성+중성
	public static final int ST_FIRST_AREA = 5;					// 초성+아레아
	public static final int ST_FIRST_VOWEL_AND_FIRST = 6;		// [초성+중성] + [초성] 상태
	public static final int ST_SINGLE_FINAL = 7; 				// 단일종성
	public static final int PROC1 = 8;							// <초성+중성> + [초성+아레아] 상태
	public static final int PROC2 = 9;							// <초성+중성> + [초성+중성] 상태
	public static final int VERIFYCOMB = 10; 					// 종성간 조합 가능여부 조사 상태
	public static final int ST_MULTI_FINAL = 11; 				// 복합종성
	public static final int ST_SINGLE_FINAL_AND_FIRST = 12; 	// [단일종성]+[초성] 상태
	public static final int PROC3 = 13;							// <단일종성> + [초성+아레아] 상태
	public static final int PROC4 = 14;							// <단일종성> + [초성+중성] 상태
	public static final int ST_ERROR = 15; 						// 에러 처리 상태

	public static final int FIRST_CONSONANT = 0; 	// 초성
	public static final int VOWEL = 1; 				// 중성
	public static final int FINAL_CONSONANT = 2; 	// 종성
	public static final int AREA = 3;				// 아레아
	public static final int UNKNOWN = 4;

	private static final int NUM_STATE = 16;
	private static final int NUM_INPUT = 4;
	
	private int currentState;
	private int previousState;
	private boolean isFinalInput;
	private Buffer buffer;
	private CombinationState resultState;
	private int phonemeInt[];
	private ArrayList<CombinationState> stateArr;
	private int stTrTable[][];
	private boolean isEnter;
	
	
	public void startCombine(boolean isRenew) {
		ArrayList<Integer> tmpBuffer = buffer.getPhoBuffer();
		
		if(isRenew){
			currentState = previousState;
		}
		
		do{
			if(isEnter){
				int phoneme = tmpBuffer.get(tmpBuffer.size()-1);
				int category = verifyPhonemeCategory(phoneme);
				
				previousState = currentState;
				currentState = stTrTable[currentState][category];
			}
			
			resultState = stateArr.get(currentState);
			phonemeInt = resultState.combine(buffer.getPhoBuffer(), this);
		}while(resultState.isRepeat());
		
		
		buffer.appendLetter(phonemeInt);
	}


	public int getPreviousState(){
		return previousState;
	}
	
	public void setEnter(boolean isEnter){
		this.isEnter = isEnter;
	};
	
	
	public void setKoreanAutomataState(int currentState, int previousState) {
		this.currentState = currentState;
		this.previousState = previousState;
	}

	
	public boolean getIsFinalInput() {
		return isFinalInput;
	}

	
	public void setIsFinalInput(boolean isFinalInput) {
		this.isFinalInput = isFinalInput;
	}

	
	private void createCombinationTable() {
		
		for(int i = 0; i < stTrTable.length; i++){
			for(int j = 0; j <stTrTable[i].length; j++){
				stTrTable[i][j] = ST_ERROR;
			}
		}
		
		//[EMPTY]에서 전이
		stTrTable[ST_EMPTY][FIRST_CONSONANT] = ST_FIRST; //EMPTY -초성-> 초성
		stTrTable[ST_EMPTY][VOWEL] = ST_VOWEL;           //EMPTY -중성-> 중성
		stTrTable[ST_EMPTY][FINAL_CONSONANT] = ST_FIRST; //EMPTY -종성-> 초성
		stTrTable[ST_EMPTY][AREA] = ST_AREA; 			 //EMPTY -아레아-> 아레아
		
		//[초성]에서 전이
		stTrTable[ST_FIRST][VOWEL] = ST_FIRST_VOWEL;     //초성 -중성-> 초성+중성
		stTrTable[ST_FIRST][AREA] = ST_FIRST_AREA; 		 //초성 -아레아-> 초성+아레아
		
		//[초성+중성]에서 전이
		stTrTable[ST_FIRST_VOWEL][FIRST_CONSONANT] = ST_FIRST_VOWEL_AND_FIRST; // 초성+중성 -초성-> [초성+중성]+[초성]
		stTrTable[ST_FIRST_VOWEL][FINAL_CONSONANT] = ST_SINGLE_FINAL;          // 초성+중성 -종성-> 단일종성
		
		//[초성+중성]+[초성]에서 전이
		stTrTable[ST_FIRST_VOWEL_AND_FIRST][VOWEL] = PROC2;           // [초성+중성]+[초성] -중성-> <초성+중성> + [초성+중성] (PROC2)
		stTrTable[ST_FIRST_VOWEL_AND_FIRST][AREA] = PROC1;			  // [초성+중성]+[초성] -아레아-> <초성+중성> + [초성+아레아] (PROC1)
		
		//단일종성에서 전이
		stTrTable[ST_SINGLE_FINAL][FIRST_CONSONANT] = VERIFYCOMB; 			// 단일종성 -초성-> 조합검증
		stTrTable[ST_SINGLE_FINAL][VOWEL] = PROC2;                      	// 단일종성 -중성-> <초성+중성> + [초성+중성] (PROC2)
		stTrTable[ST_SINGLE_FINAL][FINAL_CONSONANT] = VERIFYCOMB;           // 단일종성 -종성-> 조합검증 (ST_VERIFY_COMB)
		stTrTable[ST_SINGLE_FINAL][AREA] = PROC1;                      		// 단일종성 -아레아-> <초성+중성> + [초성+아레아] (PROC1)
		
		//복합종성에서 전이
		stTrTable[ST_MULTI_FINAL][VOWEL] = PROC4;           // 복합종성 -중성-> <단일종성> + [초성+중성] (PROC4)
		stTrTable[ST_MULTI_FINAL][AREA] = PROC3;            // 복합종성 -아레아-> <단일종성> + [초성+아레아] (PROC3)
		
		//[단일종성]+[초성]에서 전이
		stTrTable[ST_SINGLE_FINAL_AND_FIRST][VOWEL] = PROC4;           // [단일종성]+[초성] -중성-> <단일종성> + [초성+중성] (PROC4)
		stTrTable[ST_SINGLE_FINAL_AND_FIRST][AREA] = PROC3;            // [단일종성]+[초성] -중성-> <단일종성> + [초성+아레아] (PROC3)

	}

	// 상태 저장
	private void createState() {
		stateArr.add(new StEmpty()); 				// 초기상태 0
		stateArr.add(new StFirst());			    // 초성 1
		stateArr.add(new StVowel()); 				// 중성 2
		stateArr.add(new StArea());					// 아레아 3
		stateArr.add(new StFirstVowel()); 			// 초성+중성 4
		stateArr.add(new StFirstArea());			// 초성+아레아 5
		stateArr.add(new StFirstVowelAndFirst());   // [초성+중성] + [초성] 상태 6
		stateArr.add(new StSingleFinal()); 			// 단일종성 7
		stateArr.add(new Proc1());					// <초성+중성> + [초성+아레아] 8
		stateArr.add(new Proc1());					// <초성+중성> + [초성+중성] 9
		stateArr.add(new VerifyComb()); 			// 조합 가능여부 조사 상태 10
		stateArr.add(new StMultiFinal()); 			// 복합종성 11
		stateArr.add(new StSingleFinalAndFirst());  // [단일종성] + [초성] 상태 12
		stateArr.add(new Proc3());					// <단일종성> + [초성+아레아] 13
		stateArr.add(new Proc2());					//<단일종성> + [초성+중성]  14
		stateArr.add(new StError()); 				// 에러 처리 상태 15
	}

	
	private int verifyPhonemeCategory(int phoneme) {
		int tmp = phoneme / 100;

		if (tmp == 40)
			return FIRST_CONSONANT;
		else if (tmp == 41)
			return VOWEL;
		else if(tmp == 82 || tmp == 87)
			return AREA;
		else if(tmp == 42)
			return FINAL_CONSONANT;
		else
			return UNKNOWN;
	}

	
	public void initPhonemeInt() {
		for (int i = 0; i < phonemeInt.length; i++)
			phonemeInt[i] = 0;
	}

	public Buffer getBuffer() {
		return buffer;
	}

	public void initKoreaAutomataState() {
		isEnter = true;
		currentState = ST_EMPTY;
		previousState = currentState;
	}

	public KoreanAutomata_Origin(Buffer buffer) {
		this.buffer = buffer;
		
		isEnter = true;
		phonemeInt = new int[3];
		currentState = ST_EMPTY;
		previousState = currentState;

		isFinalInput = false;

		stateArr = new ArrayList<CombinationState>();
		createState();

		stTrTable = new int[NUM_STATE][NUM_INPUT];
		createCombinationTable();
	}


	public void initPhonemeInt() {
			for (int i = 0; i < phonemeInt.length; i++)
				phonemeInt[i] = 0;
		}
	
		public Buffer getBuffer() {
			return buffer;
		}
	
		public void initKoreaAutomataState() {
			isEnter = true;
			currentState = ST_EMPTY;
			previousState = currentState;
		}

}
