package string_key;

import java.util.*;

import com.android.mykeysoftkeyboard.LatinKeyboard.LatinKey;

import key_process.*;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


/**
 * StringKeyHandler class is most important of all modify string key function.
 * It has SQLite DB and all of string key's informations are saved.
 * If user call modify string key function first, it will make new DB on user's Android device and save all of informations.
 * But, after that, it check that the DB exist in user's device or not.
 * So, DB is just made only one time.
 * @author sunghoonpark
 */
public class StringKeyHandler extends SQLiteOpenHelper{
	private static final String DB_NAME = "stringkey.db";		//DB name
	
	private static StringKeyHandler instance = null;			//This variable exist for 'Singleton Design Pattern'
	private SQLiteDatabase db = null;							//DB instance where informations're saved.

	
	
	public StringKeyHandler(Context context) {
		super(context, DB_NAME, null, 1);
		
		db = this.getWritableDatabase();
		
		//TODO 메소드 생략 가능한지 판단
		//createTable();
		//If there'is any table on user's device, make table.
		String sqlCreateQuery = "CREATE TABLE IF NOT EXISTS key (keyCode TEXT PRIMARY KEY, keyLabel TEXT, keyText TEXT);";
		db.execSQL(sqlCreateQuery);
		
		
		
		//TODO 메소드 생략 가능한지 판단		
		//initDB();
		//If any informations of string key aren't inserted in table, get informations from ArrayList and insert to table.
		String sqlCountQuery = "SELECT count(*) FROM key;";
		Cursor c = db.rawQuery(sqlCountQuery, null);
		
		c.moveToFirst();
		
		int result = Integer.parseInt(c.getString(0));
		
		if(result == 0){
			KeyMap tmpKeyInfo = new KeyMap();
			ArrayList<String> tmpKeyCodeList = tmpKeyInfo.getStringCodeList();
			ArrayList<String> tmpKeyLabelList = tmpKeyInfo.getStringLabelList();
			
			for(int i = 0; i < tmpKeyCodeList.size(); i++){
				insertMember(tmpKeyCodeList.get(i), tmpKeyLabelList.get(i), "");
			}
			
		}
	}
	
	/**
	 * This method for the 'Singleton Design Pattern'.
	 * @param context
	 * @return
	 */
	public static StringKeyHandler getInstance(Context context){
		if(instance == null)
			instance = new StringKeyHandler(context);
		
		return instance;
	}
	
//	private void createTable(){
//		String sql = "CREATE TABLE IF NOT EXISTS key (keyCode TEXT PRIMARY KEY, keyLabel TEXT, keyText TEXT);";
//		db.execSQL(sql);
//	}
	
//	private void initDB(){
//		String sql = "SELECT count(*) FROM key;";
//		Cursor c = db.rawQuery(sql, null);
//		
//		c.moveToFirst();
//		
//		int result = Integer.parseInt(c.getString(0));
//		
//		if(result == 0){
//			KeyMap tmpKeyInfo = new KeyMap();
//			ArrayList<String> tmpKeyCodeList = tmpKeyInfo.getStringCodeList();
//			ArrayList<String> tmpKeyLabelList = tmpKeyInfo.getStringLabelList();
//			
//			for(int i = 0; i < tmpKeyCodeList.size(); i++){
//				insertMember(tmpKeyCodeList.get(i), tmpKeyLabelList.get(i), "");
//			}
//			
//		}
//	}
	
	/**
	 * Insert keyCode, keyLabel, keyText to DB.
	 * @param keyCode
	 * @param keyLabel
	 * @param keyText
	 */
	private void insertMember(String keyCode, String keyLabel, String keyText){
		ContentValues values = new ContentValues();
		values.put("keyCode", keyCode);
		values.put("keyLabel", keyLabel);
		values.put("keyText", keyText);
		
		db.insert("key", null, values);
	}
	
	/**
	 * Set string key's contents from DB to soft keyboard string key.
	 * When soft keyboard create, this method is called by soft keyboard's method and set up letters of string key's contents.
	 * @param strKeyArr
	 */
	public void setStringKeyInfo(ArrayList<LatinKey> strKeyArr){
		String sql = "SELECT * FROM key;";
		
		Cursor c = db.rawQuery(sql, null);
		c.moveToFirst();
		
		for(int i = 0; i < c.getCount(); i++){
			int keyCode = Integer.parseInt(c.getString(0));
			String keyText = c.getString(2);
			
			for(int j = 0; j < strKeyArr.size(); j++){
				LatinKey tmpKey = strKeyArr.get(j);
				
				if(tmpKey.codes[0] == keyCode){
					tmpKey.text = keyText;
					break;
				}
			}
			
			c.moveToNext();
		}
	}
	
	
	/**
	 * Update string key information.
	 * @param item String key information instance.
	 */
	public void updateStringKeyInfo(StringKeyItem item){
		String sql = "UPDATE key SET keyText='" + item.getKeyText() + "' WHERE keyCode='" + item.getKeyCode() +"'";
		db.execSQL(sql);
	}
	
	
	/**
	 * Get string key information from DB and return to other instance by ArrayList for synchronize informations each others.
	 * @return
	 */
	public ArrayList<StringKeyItem> getItems(){
		String sql = "SELECT * FROM key;";
		Cursor c = db.rawQuery(sql, null);
		ArrayList<StringKeyItem> items = new ArrayList<StringKeyItem>();
		
		c.moveToFirst();
		
		for(int i = 0; i < c.getCount(); i++){
			String keyCode = c.getString(0);
			String keyLabel = c.getString(1);
			String keyText = c.getString(2);
			
			items.add(new StringKeyItem(keyCode, keyLabel, keyText));
			c.moveToNext();
		}
		
		return items;
	}
	
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Empty
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Empty		
	}

}
