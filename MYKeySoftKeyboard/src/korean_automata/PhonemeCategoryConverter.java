package korean_automata;

public class PhonemeCategoryConverter {
	public static int changePhonemeIdxFrToFn(int phonemeIdx) {
		int finalPhoneme = phonemeIdx;

		switch (phonemeIdx) {

		// ㄱ
		case 4000:
			finalPhoneme = 4201;
			break;

		// ㄲ
		case 4001:
			finalPhoneme = 4202;
			break;

		// ㄴ
		case 4002:
			finalPhoneme = 4204;
			break;

		// ㄷ
		case 4003:
			finalPhoneme = 4207;
			break;

		// ㄹ
		case 4005:
			finalPhoneme = 4208;
			break;

		// ㅁ
		case 4006:
			finalPhoneme = 4216;
			break;

		// ㅂ
		case 4007:
			finalPhoneme = 4217;
			break;

		// ㅅ
		case 4009:
			finalPhoneme = 4219;
			break;

		// ㅆ
		case 4010:
			finalPhoneme = 4220;
			break;

		// ㅇ
		case 4011:
			finalPhoneme = 4221;
			break;

		// ㅈ
		case 4012:
			finalPhoneme = 4222;
			break;

		// ㅊ
		case 4014:
			finalPhoneme = 4223;
			break;

		// ㅋ
		case 4015:
			finalPhoneme = 4224;
			break;

		// ㅌ
		case 4016:
			finalPhoneme = 4225;
			break;

		// ㅍ
		case 4017:
			finalPhoneme = 4226;
			break;

		// ㅎ
		case 4018:
			finalPhoneme = 4227;
			break;
		}

		return finalPhoneme;
	}

	

	/**
	 * 종성의 Idx를 초성의 Idx로 변환
	 * @param phonemeIdx
	 * @return
	 */
	public static int changePhonemeIdxFnToFr(int phonemeIdx) {
		int firstPhoneme = phonemeIdx;

		switch (phonemeIdx) {

		// ㄱ
		case 4201:
			firstPhoneme = 4000;
			break;

		// ㄲ
		case 4202:
			firstPhoneme = 4001;
			break;

		// ㄴ
		case 4204:
			firstPhoneme = 4002;
			break;

		// ㄷ
		case 4207:
			firstPhoneme = 4003;
			break;

		// ㄹ
		case 4208:
			firstPhoneme = 4005;
			break;

		// ㅁ
		case 4216:
			firstPhoneme = 4006;
			break;

		// ㅂ
		case 4217:
			firstPhoneme = 4007;
			break;

		// ㅅ
		case 4219:
			firstPhoneme = 4009;
			break;

		// ㅆ
		case 4220:
			firstPhoneme = 4010;
			break;

		// ㅇ
		case 4221:
			firstPhoneme = 4011;
			break;

		// ㅈ
		case 4222:
			firstPhoneme = 4012;
			break;

		// ㅊ
		case 4223:
			firstPhoneme = 4014;
			break;

		// ㅋ
		case 4224:
			firstPhoneme = 4015;
			break;

		// ㅌ
		case 4225:
			firstPhoneme = 4016;
			break;

		// ㅍ
		case 4226:
			firstPhoneme = 4017;
			break;

		// ㅎ
		case 4227:
			firstPhoneme = 4018;
			break;
		}

		return firstPhoneme;
	}


	/**
	 * 초성이나 종성의 Idx를 독립자음 Idx로 변환
	 * @param phonemeIdx
	 * @return
	 */
	public static int changePhonemeFnAFrToAl(int phonemeIdx) {
		int alonePhoneme = phonemeIdx;

		switch (phonemeIdx) {

		// ㄱ
		case 4000:
		case 4201:
			alonePhoneme = 0;
			break;

		// ㄲ
		case 4001:
		case 4202:
			alonePhoneme = 1;
			break;

		// ㄳ
		case 4203:
			alonePhoneme = 2;
			break;

		// ㄴ
		case 4002:
		case 4204:
			alonePhoneme = 3;
			break;

		// ㄵ
		case 4205:
			alonePhoneme = 4;
			break;

		// ㄶ
		case 4206:
			alonePhoneme = 5;
			break;

		// ㄷ
		case 4003:
		case 4207:
			alonePhoneme = 6;
			break;

		// ㄸ
		case 4004:
			alonePhoneme = 7;
			break;

		// ㄹ
		case 4005:
		case 4208:
			alonePhoneme = 8;
			break;

		// ㄺ
		case 4209:
			alonePhoneme = 9;
			break;

		// ㄻ
		case 4210:
			alonePhoneme = 10;
			break;

		// ㄼ
		case 4211:
			alonePhoneme = 11;
			break;

		// ㄽ
		case 4212:
			alonePhoneme = 12;
			break;

		// ㄾ
		case 4213:
			alonePhoneme = 13;
			break;

		// ㄿ
		case 4214:
			alonePhoneme = 14;
			break;

		// ㅀ
		case 4215:
			alonePhoneme = 15;
			break;

		// ㅁ
		case 4006:
		case 4216:
			alonePhoneme = 16;
			break;

		// ㅂ
		case 4007:
		case 4217:
			alonePhoneme = 17;
			break;

		// ㅃ
		case 4008:
			alonePhoneme = 18;
			break;

		// ㅄ
		case 4218:
			alonePhoneme = 19;
			break;

		// ㅅ
		case 4009:
		case 4219:
			alonePhoneme = 20;
			break;

		// ㅆ
		case 4010:
		case 4220:
			alonePhoneme = 21;
			break;

		// ㅇ
		case 4011:
		case 4221:
			alonePhoneme = 22;
			break;

		// ㅈ
		case 4012:
		case 4222:
			alonePhoneme = 23;
			break;

		// ㅉ
		case 4013:
			alonePhoneme = 24;
			break;

		// ㅊ
		case 4014:
		case 4223:
			alonePhoneme = 25;
			break;

		// ㅋ
		case 4015:
		case 4224:
			alonePhoneme = 26;
			break;

		// ㅌ
		case 4016:
		case 4225:
			alonePhoneme = 27;
			break;

		// ㅍ
		case 4017:
		case 4226:
			alonePhoneme = 28;
			break;

		// ㅎ
		case 4018:
		case 4227:
			alonePhoneme = 29;
			break;
		}

		return alonePhoneme;
	}
}
