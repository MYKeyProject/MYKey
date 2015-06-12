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
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;

public class LatinKeyboard extends Keyboard {

    private Key mEnterKey;
    private Key mSpaceKey;
    private Key mShiftKey;
    
    
    public LatinKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }
    

    public LatinKeyboard(Context context, int layoutTemplateResId, 
            CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, 
            XmlResourceParser parser) {
        Key key = new LatinKey(res, parent, x, y, parser);
        
        if (key.codes[0] == 10) {
            mEnterKey = key;
        } else if (key.codes[0] == ' ') {
            mSpaceKey = key;
        }
        else if(key.codes[0] == -1){
        	mShiftKey = key;
        }
        
        
        if(key.icon != null){
        	Drawable tmpDrawable = key.icon;
        	Bitmap tmpBitmap = Bitmap.createBitmap(tmpDrawable.getIntrinsicWidth(), tmpDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        	Canvas canvas = new Canvas(tmpBitmap);
        	tmpDrawable.setBounds(0, 0, tmpDrawable.getIntrinsicWidth(), tmpDrawable.getIntrinsicHeight());
        	tmpDrawable.draw(canvas);
        	
        	int keyWidth;
        	int keyHeight;
        	float sWidth;
        	float sHeight;
        	
        	keyWidth = key.width -10;
        	keyHeight = key.height - 10;
        	
        	sWidth = tmpBitmap.getWidth();
        	sHeight = tmpBitmap.getHeight();
        	
        	if(sWidth > keyWidth){
        		float percente = sWidth / 100;
        		float scale = keyWidth / percente;
        		
        		sWidth *= (scale/100);
        		sHeight *=(scale/100);
        	}
        	
        	if(sHeight > keyHeight){
        		float percente = sHeight / 100;
        		float scale = keyHeight / percente;
        		
        		sWidth *= (scale/100);
        		sHeight *=(scale/100);
        	}
        	
        	tmpBitmap = Bitmap.createScaledBitmap(tmpBitmap, (int)sWidth, (int)sHeight, true);
        	
        	tmpDrawable = new BitmapDrawable(tmpBitmap);
        	key.icon =tmpDrawable;
        }
        
        
        return key;
    }

	void setSpaceIcon(final Drawable icon) {
        if (mSpaceKey != null) {
            mSpaceKey.icon = icon;
        }
    }
	
	
	

    public static class LatinKey extends Keyboard.Key {
        
        public LatinKey(Resources res, Keyboard.Row parent, int x, int y, XmlResourceParser parser) {
            super(res, parent, x, y, parser);
        }
        
        /**
         * Overriding this method so that we can reduce the target area for the key that
         * closes the keyboard. 
         */
        @Override
        public boolean isInside(int x, int y) {
            return super.isInside(x, codes[0] == KEYCODE_CANCEL ? y - 10 : y);
        }
    }

}

