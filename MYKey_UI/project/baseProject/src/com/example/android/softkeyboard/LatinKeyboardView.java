/*
 * Copyright (C) 2008-2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.example.android.softkeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodSubtype;

public class LatinKeyboardView extends KeyboardView {
	
    static final int KEYCODE_OPTIONS = -100;
    private boolean shifted;
    private boolean toggled;
    
    

    public LatinKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        shifted = false;
        toggled = false;
      }

    public LatinKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        shifted = false;
        toggled = false;
    }

    
    
    public boolean getShifted(){
    	return shifted;
    }
    
    public boolean getToggled(){
    	return toggled;
    }

    
    
    @Override
	public boolean setShifted(boolean inputShift) {
    	if(!shifted){
    		shifted = true;
    		super.setShifted(true);
    		
    	}
    	else if(!toggled){
    		toggled = true;
    	}
    	else if(toggled){
    		shifted = false;
    		toggled = false;
    		super.setShifted(false);
    		invalidateAllKeys();
    		return false;
    	}
    	
    	invalidateAllKeys();
    	return true;
	}
    
    public void initShiftState(){
    	shifted = false;
    	toggled = false;
    	super.setShifted(false);
    	
    	invalidateAllKeys();
    }
    
    public void initShifted(){
    	if(shifted && !toggled){
    		super.setShifted(false);
    		shifted = false;
    	}
    	invalidateAllKeys();
    }

	@Override
	public void setKeyboard(Keyboard keyboard) {
		super.setKeyboard(keyboard);
		
	}

	void setSubtypeOnSpaceKey(final InputMethodSubtype subtype) {
        final LatinKeyboard keyboard = (LatinKeyboard)getKeyboard();
        keyboard.setSpaceIcon(getResources().getDrawable(subtype.getIconResId()));
        invalidateAllKeys();
    }

}
