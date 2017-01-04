package com.frame.lib.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.frame.lib.config.R;
import com.frame.lib.constant.FrameConstant;
import com.frame.lib.modle.FPoupCallBack;

/**
 * 显示poupwindow
 * 
 * @author Andy Create on 2014 2014-11-10 下午6:03:57
 */
public class PopupWindowUtil {
	private static PopupWindowUtil pwu;

	public static PopupWindowUtil getFleftMenuPoup() {
		if (pwu == null) {
			pwu = new PopupWindowUtil();
		}
		return pwu;
	}

	private static PopupWindow popupWindow;
	private View view;

	public void showMeum(View parent, int layId, int bnt1, int bnt2, final FPoupCallBack fcb) {
		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) FrameConstant.appCon.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(layId, null);
			popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			view.findViewById(bnt1).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					fcb.bnt1();
					popupWindowClose();
				}
			});
			view.findViewById(bnt2).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					fcb.bnt2();
					popupWindowClose();
				}
			});
			
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					popupWindowClose();
				}
			});
		}
		popupWindow.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(-00000);
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.setOutsideTouchable(true);
//		popupWindow.setAnimationStyle(R.style.FPoupWindowUtil);
		popupWindow.showAsDropDown(parent, 0, 0);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {

			}
		});
	}

	public static void popupWindowClose() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}

}
