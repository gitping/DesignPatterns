package com.yto.suixingouuser.util.view;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.ContrabandActivity;
import com.yto.suixingoustore.activity.PriceOfContryActivity;

public class MyPopuWindow {
	private static PopupWindow popupWindow;
	private View view;
	private static MyPopuWindow myPopuWindow;
	private Context con;
	
	
	public static MyPopuWindow getInstance(){
		if(myPopuWindow==null){
			myPopuWindow=new MyPopuWindow();
		}
		return myPopuWindow;
	}
	
	
	public void showMenu(final View v){
		if(popupWindow==null){
			this.con=FrameApplication.context;
			LayoutInflater inflater=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view=inflater.inflate(R.layout.mypopulay, null);
			popupWindow=new PopupWindow(view, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			BntOnclickLister bol = new BntOnclickLister();
			view.findViewById(R.id.searallprice).setOnClickListener(bol);
			view.findViewById(R.id.seargoods).setOnClickListener(bol);
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.TopPopupAnimation);
		popupWindow.showAsDropDown(v, 0, 0);
//		popupWindow.showAtLocation(v, Gravity.TOP|Gravity.RIGHT, 0, 0);
	}
	
	class BntOnclickLister implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (popupWindow != null) {
				popupWindow.dismiss();
			}
			int id = v.getId();
			switch (id) {
			case R.id.searallprice:
				Intent intent=new Intent();
				intent.setClass(con, PriceOfContryActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				con.startActivity(intent);
				break;
			case R.id.seargoods:
				Intent in=new Intent();
				in.setClass(con, ContrabandActivity.class);
				in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				con.startActivity(in);
				break;
			}
		}

	}

}
