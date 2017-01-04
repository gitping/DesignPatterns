package com.yto.suixingoustore.util.pinyinsort;

import java.util.Comparator;

import com.yto.zhang.util.modle.ExpressBean;

/**
 * 
 * @author Mr.Z
 */
public class PinyinComparator implements Comparator<ExpressBean> {

	public int compare(ExpressBean o1, ExpressBean o2) {
		if(o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if(o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
