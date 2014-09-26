package com.example.android.softkeyboard;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.mykey.R;

public class StringKeyModifyActivity extends Activity implements OnItemClickListener{
	private ArrayList<StringKeyItem> strKeyItems;
	private StringKeyListAdapter adapter;
	private ListView strKeyListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.string_key_modify_activity);
		
		StringKeyHandler strKeyHnd = StringKeyHandler.getInstance(getApplicationContext());
		strKeyItems = strKeyHnd.getItems();
		
		adapter = new StringKeyListAdapter(this, android.R.layout.simple_list_item_1, strKeyItems);
	
		strKeyListView = (ListView) findViewById(R.id.stringListView);
		strKeyListView.setOnItemClickListener(this);
		strKeyListView.setAdapter(adapter);
	}
	

	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		final StringKeyItem tmpItem = strKeyItems.get(position);
		
		KeyInfoDialog kd = new KeyInfoDialog(StringKeyModifyActivity.this);
		kd.setTitle("Modify");
		kd.setKeyItem(tmpItem);
		
		
		kd.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				KeyInfoDialog tmpKd = (KeyInfoDialog) dialog;
				tmpItem.setKeyText(tmpKd.getKeyText());
				
				StringKeyHandler strKeyHnd = StringKeyHandler.getInstance(getApplicationContext());
				strKeyHnd.updateStringKeyInfo(tmpItem);
				
				strKeyItems = strKeyHnd.getItems();
				
				adapter = new StringKeyListAdapter(StringKeyModifyActivity.this, android.R.layout.simple_list_item_1, strKeyItems);
				strKeyListView.setAdapter(adapter);
			}
		});
		
		kd.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				
			}
		});
		
		
		kd.show();
	}
	
	class KeyInfoDialog extends Dialog implements OnTouchListener{
		private TextView keyLabelView;
		private EditText keyTextView;
		private Button commitBtn;
		private Button cancelBtn;
		private StringKeyItem item;
		private String beforeModify;

		public KeyInfoDialog(Context context) {
			super(context);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.key_text_modify);
			
			keyLabelView = (TextView) findViewById(R.id.keyLabelTextView);
			keyTextView = (EditText) findViewById(R.id.keyTextEditView);
			commitBtn = (Button) findViewById(R.id.commitButton);
			cancelBtn = (Button) findViewById(R.id.cancelButton);
			
			keyLabelView.setText(item.getKeyLabel());
			keyTextView.setText(item.getKeyText());
			beforeModify = keyTextView.getText().toString();
			
			commitBtn.setOnTouchListener(this);
			cancelBtn.setOnTouchListener(this);
		}
		
		
		public void setKeyItem(StringKeyItem item){
			this.item = item;
		}
		
		public String getKeyText(){
			return keyTextView.getText().toString();
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(v.equals(commitBtn)){
				dismiss();
			}
			else if(v.equals(cancelBtn)){
				keyTextView.setText(beforeModify);
				cancel();
			}
			
			return false;
		}

		
	}
}

