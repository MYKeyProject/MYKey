package tool.key.info;

import tool.key.KeyInfo;

public class ConsonantKeyInfo extends KeyInfo {	
	public static int MAX_KEY = 5;
	public ConsonantKeyInfo(int phoneme, String keyLabel) {
		super(KeyInfo.CONSONANT_KEY, phoneme, keyLabel, true);
	}
	@Override
	public int getMaxKeyNum() {
		return MAX_KEY;
	}
}
