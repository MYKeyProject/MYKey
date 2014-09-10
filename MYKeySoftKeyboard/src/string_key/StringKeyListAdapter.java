package string_key;

import java.util.*;

import com.android.softkeyboard.*;

import android.content.*;
import android.view.*;
import android.widget.*;

/**
 * This class is adapter for user customized list view.
 * It contains StringKeyItem objects by ArrayAdapter.
 * @author sunghoonpark
 */
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

