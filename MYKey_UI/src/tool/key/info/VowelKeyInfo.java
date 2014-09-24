package tool.key.info;

import tool.key.KeyInfo;

public class VowelKeyInfo extends KeyInfo {
	public static int MAX_KEY = 5;
	private int num;
	public VowelKeyInfo(int phoneme, String keyLabel) {
		super(KeyInfo.VOWEL_KEY, phoneme, keyLabel, true);
		num = 0;
	}

	@Override
	public int getMaxKeyNum() {
		return MAX_KEY;
	}
}
