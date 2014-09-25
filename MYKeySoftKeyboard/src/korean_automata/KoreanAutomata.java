package korean_automata;

import java.util.*;

import key_process.*;


/**
 * This class is typical class file of Korean Automata Module.
 * It's only conduct Korean combination.
 * This module has a lot of states, and it's driven by table of state.
 * In addition, It will been written when I make comment type of example letter.
 * Because, if it's not written by Korean, it never explain to what is mean.   
 * @author sunghoonpark
 */
public class KoreanAutomata {
	public static final int ST_EMPTY = 0;						//No letters.
	public static final int ST_FIRST = 1;						//First consonant only.															ex>'ㄱ'
	public static final int ST_VOWEL = 2;						//Vowel only.																	ex>'ㅏ'
	public static final int ST_FIRST_VOWEL = 3;					//Combined by first consonant and vowel.										ex>'가'
	public static final int ST_FIRST_VOWEL_AND_FIRST = 4;		//Combined by first consonant and vowel and first consonant.					ex>'가''ㅉ'
	public static final int ST_SINGLE_FINAL = 5;				//Combined by first consonant, vowel and final consonant.						ex>'간'
	public static final int PROC1 = 6;							//Process to choice state when inserted vowel from ST_FIRST_VOWEL_AND_FIRST. 
	public static final int VERIFYCOMB = 7;						//Verify that combination is possible or impossible and transfer state to ST_SINGLE_FINAL_AND_FIRST or ST_MULTI_FINAL from ST_SINGLE_FINAL.
	public static final int ST_MULTI_FINAL = 8;					//Combined by first consonant, vowel and two final consonant.					ex>'갂'					
	public static final int ST_SINGLE_FINAL_AND_FIRST = 9;		//Combined by first consonant, vowel and final consonant, and first consonant. 	ex>'각''ㄴ'
	public static final int PROC2 = 10;							//Process to choice state when inserted vowel from ST_SINGLE_FINAL_AND_FIRST.
	public static final int ST_ERROR = 11;						//When inserted letter is can't combine from existed letter, it will transfer ST_ERROR
	
	public static final int FIRST_CONSONANT = 0;				//It's type of Korean letter. In Korean Chosung.		ex>'ㄱ', 'ㄴ', 'ㄷ', ...
	public static final int VOWEL = 1;							//It's type of Korean letter. In Korean Jongsung.		ex>'ㅏ', 'ㅑ', 'ㅓ', ...
	public static final int FINAL_CONSONANT = 2;				//It's type of Korean letter. In Korean Jungsung.		ex>'ㄱ', 'ㄱㅅ', 'ㄲ', ...
	public static final int UNKNOWN = 3;						//Unknown type input, it exist for processing error.
	
	public static final int NUM_STATE = 12;						//Number of state.
	public static final int NUM_INPUT = 4;						//Number of input types.
	

	private CombinationState resultState;						//This reference exist for Strategy Pattern.
	private ArrayList<CombinationState> stateArr;				//This template really has instance of state.
	
	private int currentState;									//Current state.
	private int previousState;									//Previous state.
	private boolean isFinalInput;								//Flag to check final consonant.
	private int stTrTable[][];									//Table of state. It's made up of integer array. Because table driven method use static integer variable.
	
	private Buffer buffer;										//TODO: Repeat discussion Is it really need?.	
	private boolean isEnter;									//TODO: Repeat discussion Is it really need?.
	private int phonemeInt[];									//TODO: Repeat discussion. Is it really need??
	
	
	/**
	 * Create Korean Automata instance and initiate.
	 * @param buffer
	 */
	public KoreanAutomata(Buffer buffer) {
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
	
	
	/**
	 * Create Korean Automata's states.
	 * This sequence of creating states is very important and have to follow.
	 */
	private void createState() {
		stateArr.add(new StEmpty()); 				
		stateArr.add(new StFirst());			    
		stateArr.add(new StVowel()); 				
		stateArr.add(new StFirstVowel());
		stateArr.add(new StFirstVowelAndFirst());
		stateArr.add(new StSingleFinal()); 			
		stateArr.add(new Proc1());										
		stateArr.add(new VerifyComb()); 			
		stateArr.add(new StMultiFinal()); 			
		stateArr.add(new StSingleFinalAndFirst());
		stateArr.add(new Proc2());
		stateArr.add(new StError()); 				
	}
	
	
	/**
	 * Create what table of Korean Automata is use.
	 * Korean Automata use table driven method.  
	 */
	private void createCombinationTable() {
		
		for(int i = 0; i < stTrTable.length; i++){
			for(int j = 0; j <stTrTable[i].length; j++){
				stTrTable[i][j] = ST_ERROR;
			}
		}
		
		
		stTrTable[ST_EMPTY][FIRST_CONSONANT] = ST_FIRST; 
		stTrTable[ST_EMPTY][VOWEL] = ST_VOWEL;           
		stTrTable[ST_EMPTY][FINAL_CONSONANT] = ST_FIRST; 
		
		stTrTable[ST_FIRST][VOWEL] = ST_FIRST_VOWEL;     

		stTrTable[ST_FIRST_VOWEL][FIRST_CONSONANT] = ST_FIRST_VOWEL_AND_FIRST; 
		stTrTable[ST_FIRST_VOWEL][FINAL_CONSONANT] = ST_SINGLE_FINAL;          
		
		stTrTable[ST_FIRST_VOWEL_AND_FIRST][VOWEL] = PROC1;          
		
		stTrTable[ST_SINGLE_FINAL][FIRST_CONSONANT] = VERIFYCOMB; 			
		stTrTable[ST_SINGLE_FINAL][VOWEL] = PROC2;                      	
		stTrTable[ST_SINGLE_FINAL][FINAL_CONSONANT] = VERIFYCOMB;           
		
		stTrTable[ST_MULTI_FINAL][VOWEL] = PROC2;
		
		stTrTable[ST_SINGLE_FINAL_AND_FIRST][VOWEL] = PROC2;
	}
	
	
	/**
	 * Most important method of Korean Automata.
	 * This method conduct combining korean letters.
	 * @param isRenew
	 */
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
	
	
	/**
	 * Verify input method for combination
	 * Each letters divided by number and categorize to each kind of letter.
	 * @param phoneme
	 * @return
	 */
	private int verifyPhonemeCategory(int phoneme) {
		int tmp = phoneme / 100;

		if (tmp == 40)
			return FIRST_CONSONANT;
		else if (tmp == 41)
			return VOWEL;
		else if(tmp == 42)
			return FINAL_CONSONANT;
		else
			return UNKNOWN;
	}
	
	
	//Getter and Setter
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
	
	

	public Buffer getBuffer() {
		return buffer;
	}
	
	
	
	//Initializer
	public void initPhonemeInt() {
		for (int i = 0; i < phonemeInt.length; i++)
			phonemeInt[i] = 0;
	}

	public void initKoreaAutomataState() {
		isEnter = true;
		currentState = ST_EMPTY;
		previousState = currentState;
	}
}
