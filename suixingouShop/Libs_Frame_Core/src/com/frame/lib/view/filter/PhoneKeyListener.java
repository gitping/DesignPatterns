package com.frame.lib.view.filter;

import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.BaseKeyListener;

import com.frame.lib.utils.Util;

public class PhoneKeyListener extends BaseKeyListener implements InputFilter {

	@Override
	public int getInputType() {
		// TODO Auto-generated method stub
		return  InputType.TYPE_CLASS_TEXT;
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		// TODO Auto-generated method stub
		CharSequence re = "";
		if(dstart > 0 && dstart < 11){
			if(Util.isNum(source.toString())){
				re = source;
			}
		}else if(dstart == 0){
			if(source.toString().equals("1")){
				re = source;
			}
		}
		return re;
	}

}
