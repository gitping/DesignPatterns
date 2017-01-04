package com.frame.lib.view.filter;

import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.BaseKeyListener;

import com.frame.lib.utils.JUtils;

public class CommonKeyListener extends BaseKeyListener implements InputFilter{

	@Override
	public int getInputType() {
		return InputType.TYPE_CLASS_TEXT;
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		CharSequence re = "";
		if(JUtils.isCommonChar(source.toString())){
			re = source;
		}
		return re;
	}
	
}