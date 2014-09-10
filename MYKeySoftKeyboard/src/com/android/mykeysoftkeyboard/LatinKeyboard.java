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

package com.android.mykeysoftkeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.*;
import android.graphics.drawable.*;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.*;
import android.view.inputmethod.EditorInfo;

import com.android.softkeyboard.R;

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
        	//TODO: if문 내의 기능을 함수로 변환하여 삽입. 기능테스트 필요
        	resizeKeyIcon(key);
        	
        	//resizeIcon()메소드 정상 작동시 삭제
//        	Drawable tmpDrawable = key.icon;
//        	Bitmap tmpBitmap = Bitmap.createBitmap(tmpDrawable.getIntrinsicWidth(), tmpDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        	Canvas canvas = new Canvas(tmpBitmap);
//        	tmpDrawable.setBounds(0, 0, tmpDrawable.getIntrinsicWidth(), tmpDrawable.getIntrinsicHeight());
//        	tmpDrawable.draw(canvas);
//        	
//        	int keyWidth;
//        	int keyHeight;
//        	float sWidth;
//        	float sHeight;
//        	
//        	keyWidth = key.width -10;
//        	keyHeight = key.height - 10;
//        	
//        	sWidth = tmpBitmap.getWidth();
//        	sHeight = tmpBitmap.getHeight();
//        	
//        	if(sWidth > keyWidth){
//        		float percente = sWidth / 100;
//        		float scale = keyWidth / percente;
//        		
//        		sWidth *= (scale/100);
//        		sHeight *=(scale/100);
//        	}
//        	
//        	if(sHeight > keyHeight){
//        		float percente = sHeight / 100;
//        		float scale = keyHeight / percente;
//        		
//        		sWidth *= (scale/100);
//        		sHeight *=(scale/100);
//        	}
//        	
//        	tmpBitmap = Bitmap.createScaledBitmap(tmpBitmap, (int)sWidth, (int)sHeight, true);
//        	
//        	tmpDrawable = new BitmapDrawable(tmpBitmap);
//        	key.icon =tmpDrawable;
        }
        
        
        return key;
    }
    
    
    /**
     * Key가 생성될때 Key에 삽입되는 이미지의 사이즈를 Key 사이즈에 알맞게 변경합니다.
     * 이미지는 비율이 유지된 채로 축소되며 x,y 양축 모두 Key 사이즈 보다 작아지게 됩니다.
     * @param key 이미지가 삽일될 키입니다.
     * @return 이미지가 삽입된 키입니다.
     */
    private Key resizeKeyIcon(Key key){
        int keyWidth;
    	int keyHeight;
    	float sWidth;
    	float sHeight;
    	
    	Drawable tmpDrawable = key.icon;
    	Bitmap tmpBitmap = Bitmap.createBitmap(tmpDrawable.getIntrinsicWidth(), tmpDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    	Canvas canvas = new Canvas(tmpBitmap);
    	tmpDrawable.setBounds(0, 0, tmpDrawable.getIntrinsicWidth(), tmpDrawable.getIntrinsicHeight());
    	tmpDrawable.draw(canvas);
    	
    	
    	
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
    	
    	
    	return key;
    }
    
    
    /**
     * This looks at the ime options given by the current editor, to set the
     * appropriate label on the keyboard's enter key (if it has one).
     */
//    void setImeOptions(Resources res, int options) {
//        if (mEnterKey == null) {
//            return;
//        }
//        
//        switch (options&(EditorInfo.IME_MASK_ACTION|EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
//            case EditorInfo.IME_ACTION_GO:
//                mEnterKey.iconPreview = null;
//                mEnterKey.icon = null;
//                mEnterKey.label = res.getText(R.string.label_go_key);
//                break;
//            case EditorInfo.IME_ACTION_NEXT:
//                mEnterKey.iconPreview = null;
//                mEnterKey.icon = null;
//                mEnterKey.label = res.getText(R.string.label_next_key);
//                break;
//            case EditorInfo.IME_ACTION_SEARCH:
//                mEnterKey.icon = res.getDrawable(R.drawable.sym_keyboard_search);
//                mEnterKey.label = null;
//                break;
//            case EditorInfo.IME_ACTION_SEND:
//                mEnterKey.iconPreview = null;
//                mEnterKey.icon = null;
//                mEnterKey.label = res.getText(R.string.label_send_key);
//                break;
//            default:
//                mEnterKey.icon = res.getDrawable(R.drawable.sym_keyboard_return);
//                mEnterKey.label = null;
//                break;
//        }
//    }

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
