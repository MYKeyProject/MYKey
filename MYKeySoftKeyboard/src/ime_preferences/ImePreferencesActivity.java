package ime_preferences;

import string_key.*;

import com.android.softkeyboard.*;

import android.content.*;
import android.os.*;
import android.preference.*;

/**
* Displays the IME preferences inside the input method setting.
*/
public class ImePreferencesActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
	
	private CheckBoxPreference mVibration = null;
	private CheckBoxPreference mSound = null;
	private Preference mStringKey = null;

	public static final String PREF_VIBRATION = "Vibration";
	public static final String PREF_SOUND = "Sound";
	public static final String PREF_STRING_KEY = "String_Key";
	public static final String PREF_NAME = "mykey_pref";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       
       addPreferencesFromResource(R.xml.setting);
       
       mVibration = (CheckBoxPreference) findPreference(PREF_VIBRATION);
       mVibration.setOnPreferenceChangeListener(this);
       
       mSound = (CheckBoxPreference) findPreference(PREF_SOUND);
       mSound.setOnPreferenceChangeListener(this);
       
       mStringKey = findPreference(PREF_STRING_KEY);
       
       Intent intent = new Intent(ImePreferencesActivity.this, StringKeyModifyActivity.class);
       mStringKey.setIntent(intent);
   }

   
   
   
	@Override
	protected void onStop() {
		super.onStop();
		
		SharedPreferences setting = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = setting.edit();
		
		editor.putBoolean(PREF_VIBRATION, mVibration.isChecked());
		editor.putBoolean(PREF_SOUND, mSound.isChecked());
		
		editor.commit();
	}

	
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		String key = preference.getKey();
		
		if(key.equals(PREF_VIBRATION)){
			
			if(mVibration.isChecked())
				mVibration.setChecked(false);
			else
				mVibration.setChecked(true);
			
		}
		else if(key.equals(PREF_SOUND)){
			
			if(mSound.isChecked())
				mSound.setChecked(false);
			else
				mSound.setChecked(true);
		
		}
		
		return false;
	}

}
