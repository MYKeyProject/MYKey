package string_key;

/**
 * StringKeyItem class include attribute of StringKey.
 * KeyText is content what is shown when user press the string key.
 * KeyCode distinguish each other in DB System.
 * KeyLabel is same letter displayed on MYKey soft keyboard button.
 * @author sunghoonpark
 */
class StringKeyItem{
	private String keyLabel;
	private String keyText;
	private String keyCode;
	
	public StringKeyItem(String keyCode, String keyLabel, String keyText){
		this.keyCode = keyCode;
		this.keyLabel = keyLabel;
		this.keyText = keyText;
	}

	public String getKeyLabel() {
		return keyLabel;
	}

	public String getKeyText() {
		return keyText;
	}
	
	public String getKeyCode(){
		return keyCode;
	}
	
	public void setKeyText(String keyText){
		this.keyText = keyText;
	}
	
}