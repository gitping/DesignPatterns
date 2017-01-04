package com.frame.view.edittextfilter;

import java.util.regex.Pattern;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 汉字 
 * @author Andy
 * Create on 2014 2014-5-21 下午6:44:57
 */
public class ChineseIdeographFilter implements InputFilter {
	
	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
			return isChineseIdeograph(source.toString()) ? source:"";
	}
	
	public boolean isChineseIdeograph(String str){
		String rex = "^[\u4E00-\u9FA5]{0,}$";
		boolean b = Pattern.matches(rex, str);
		return b;
	}
	
}

