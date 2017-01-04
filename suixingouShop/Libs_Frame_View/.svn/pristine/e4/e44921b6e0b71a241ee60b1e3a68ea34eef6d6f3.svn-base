package com.frame.view.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.LinearLayout;

public class NoScrollGridView extends GridView {

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
	
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		((LinearLayout)getParent()).getParent().requestDisallowInterceptTouchEvent(true);
//		onTouchEvent(ev);
//		return true;
//	}
}
