package com.example.android.softkeyboard;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.mykey.R;

public class StringKeyListAdapter extends ArrayAdapter<StringKeyItem>{
	private ArrayList<StringKeyItem> items;

	public StringKeyListAdapter(Context context,
			int textViewResourceId, ArrayList<StringKeyItem> items) {
		super(context, textViewResourceId, items);
		
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if(v == null){
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			v = vi.inflate(R.layout.string_key_list, null);
		}
		
		StringKeyItem item = items.get(position);
		
		if(item != null){
			TextView keyLabelView = (TextView)v.findViewById(R.id.String_Key_Modify_Label);
			TextView keyTextView = (TextView)v.findViewById(R.id.String_Key_Modify_Edit);
			
			keyLabelView.setText(item.getKeyLabel());
			keyTextView.setText(item.getKeyText());
		}
		
		return v;
	}

	
}


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