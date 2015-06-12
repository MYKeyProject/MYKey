package com.example.android.softkeyboard;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.softkeyboard.LatinKeyboard.LatinKey;

public class StringKeyHandler extends SQLiteOpenHelper{
	private static StringKeyHandler instance = null;
	private static final String DB_NAME = "stringkey.db";
	
	private SQLiteDatabase db = null;
	

	public static StringKeyHandler getInstance(Context context){
		if(instance == null)
			instance = new StringKeyHandler(context);
		
		return instance;
	}
	
	
	
	private StringKeyHandler(Context context){
		super(context, DB_NAME, null, 1);
		
		db = this.getWritableDatabase();
		createTable();
		initDB();
	}
	
	private void createTable(){
		String sql = "CREATE TABLE IF NOT EXISTS key (keyCode TEXT PRIMARY KEY, keyLabel TEXT, keyText TEXT);";
		db.execSQL(sql);
	}
	
	private void initDB(){
		String sql = "SELECT count(*) FROM key;";
		Cursor c = db.rawQuery(sql, null);
		
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
	
	private void insertMember(String keyCode, String keyLabel, String keyText){
		ContentValues values = new ContentValues();
		values.put("keyCode", keyCode);
		values.put("keyLabel", keyLabel);
		values.put("keyText", keyText);
		
		db.insert("key", null, values);
	}
	
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
	
	public void updateStringKeyInfo(StringKeyItem item){
		String sql = "UPDATE key SET keyText='" + item.getKeyText() + "' WHERE keyCode='" + item.getKeyCode() +"'";
		
		db.execSQL(sql);
	}
	
	
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
	}



	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	
}
