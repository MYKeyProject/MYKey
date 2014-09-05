package template;

/**
 * This class is Key Framework for use to ease
 * @author Kim TaeWan
 */
public class Key {

	// key default attribute.
	int[] keyCode = null;
	int keyWidth = 0;
	int keyHeight = 0;
	String keyLabel = null; // label on the key.
	String keyIcon = null; // image icon on the key.
	int keyEdgeFlags = 0; // left or right.

	// detail attribute.
	int isRepeatable = 0;

	/**
	 * Constructor.
	 * @param keyCode
	 * @param keyWidth
	 * @param keyHeight
	 * @param keyLabel
	 * @param keyIcon
	 * @param keyEdgeFlags
	 */
	public Key(int[] keyCode, int keyWidth, int keyHeight, String keyLabel,
			String keyIcon, int keyEdgeFlags) {
		this.keyCode = keyCode;
		this.keyWidth = keyWidth;
		this.keyHeight = keyHeight;
		this.keyLabel = keyLabel;
		this.keyIcon = keyIcon;
		this.keyEdgeFlags = keyEdgeFlags;
	}

	/**
	 * detail option Constructor.
	 * @param isRepeatable
	 * @param isModified
	 * @param isSticky
	 */
	public Key(int[] keyCode, int keyWidth, int keyHeight, String keyLabel,
			String keyIcon, int keyEdgeFlags, int isRepeatable) {
		this.keyCode = keyCode;
		this.keyWidth = keyWidth;
		this.keyHeight = keyHeight;
		this.keyLabel = keyLabel;
		this.keyIcon = keyIcon;
		this.keyEdgeFlags = keyEdgeFlags;
		this.isRepeatable = isRepeatable;
	}
	
	/*
	 *  getter method of class attribute
	 */
	public int[] getKeyCode() {
		return keyCode;
	}

	public int getKeyWidth() {
		return keyWidth;
	}

	public int getKeyHeight() {
		return keyHeight;
	}

	public String getKeyLabel() {
		return keyLabel;
	}

	public String getKeyIcon() {
		return keyIcon;
	}

	public int getKeyEdgeFlags() {
		return keyEdgeFlags;
	}

	public int getIsRepeatable() {
		return isRepeatable;
	}
}
