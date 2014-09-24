package tool.key.info;

import tool.key.KeyInfo;

public class SpecialKeyInfo extends KeyInfo{
	public static int MAX_KEY = 1;
	private int num;
	public SpecialKeyInfo(int phoneme, String keyLabel, boolean primary) {
		super(KeyInfo.SPECIAL_KEY, phoneme, keyLabel, primary);
		num = 0;
	}
	@Override
	public int getMaxKeyNum() {
		return MAX_KEY;
	}
}
