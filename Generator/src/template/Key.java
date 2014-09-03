package template;

public class Key {

	/**
	 * issue,
	 * first, keyCode must be array type.
	 * second, detail option will be changed.
	 */
	
	// key default attribute
	int[] keyCode = null;
	int keyWidth = 0;
	int keyHeight = 0;
	String keyLabel = null; // label on the key
	String keyIcon = null; // image icon on the key
	int keyEdgeFlags = 0; // left or right

	// detail attribute
	boolean isRepeatable = false;
	boolean isModified = false;
	boolean isSticky = false;

	/**
	 * Constructor
	 * 
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
	 * detail option Constructor
	 * 
	 * @param isRepeatable
	 * @param isModified
	 * @param isSticky
	 */
	public Key(int[] keyCode, int keyWidth, int keyHeight, String keyLabel,
			String keyIcon, int keyEdgeFlags, boolean isRepeatable,
			boolean isModified, boolean isSticky) {
		this(keyCode, keyWidth, keyHeight, keyLabel, keyIcon, keyEdgeFlags);
		this.isRepeatable = isRepeatable;
		this.isModified = isModified;
		this.isSticky = isSticky;
	}
}
