package com.yto.suixingouuser.util.view;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.yto.suixingoustore.FMainActivity;
import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.activity.ProductImportActivity;
import com.yto.suixingoustore.activity.RedEnvelopeDealMessageActivity;
import com.yto.suixingoustore.activity.StoreAboutActivity;
import com.yto.suixingoustore.activity.StoreMyBackAccountActivity;
import com.yto.suixingoustore.activity.StoreMyShopActivity;
import com.yto.suixingoustore.activity.StoreShopEditActivity;

/**
 * 左边菜单
 * @author Andy
 * Create on 2014 2014-4-9 ����11:54:21
 */
public class FLeftMeunPoup {
	private static PopupWindow popupWindow;
	private static View view;
	private Context con;
	private static FLeftMeunPoup menupop;
	
	public static FLeftMeunPoup getPopInstance(){
		if(menupop==null){
			menupop=new FLeftMeunPoup();
		}
		return menupop; 
	}

	/**
	 * 显示左边菜单
	 * @param parent
	 */
	public void showMeum(View v,final Button but) {

		if (popupWindow == null) {
			this.con = FrameApplication.context;
			LayoutInflater layoutInflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.fleftmeumpoup, null);
			popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			BntOnclickLister bol = new BntOnclickLister();
			view.findViewById(R.id.bnt0).setOnClickListener(bol);
			view.findViewById(R.id.bnt1).setOnClickListener(bol);
			view.findViewById(R.id.bnt2).setOnClickListener(bol);
			view.findViewById(R.id.bnt3).setOnClickListener(bol);
			view.findViewById(R.id.bnt5).setOnClickListener(bol);
			view.findViewById(R.id.bnt6).setOnClickListener(bol);
			view.findViewById(R.id.bnt7).setOnClickListener(bol);
			view.findViewById(R.id.bnt9).setOnClickListener(bol);
			
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.FLeftMeunPoup);
		popupWindow.showAsDropDown(v, 0, 0);
		but.setBackgroundResource(R.drawable.menu_close);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				but.setBackgroundResource(R.drawable.menu_open);
			}
		});
	}

	class BntOnclickLister implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			fLeftMenuClick();
			int id = v.getId();
			switch (id) {
			case R.id.bnt0:
				Intent intent=new Intent();
				intent.setClass(con, FMainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				con.startActivity(intent);
				break;
			case R.id.bnt1:
				Intent in=new Intent();
				in.setClass(con, StoreMyShopActivity.class);
				in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				con.startActivity(in);
				break;
			case R.id.bnt2:
				Intent it=new Intent();
				it.setClass(con, StoreShopEditActivity.class);
				it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				con.startActivity(it);
				break;
			case R.id.bnt3:
				Intent min=new Intent();
				min.setClass(con, StoreMyBackAccountActivity.class);
				min.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				con.startActivity(min);
				break;
			case R.id.bnt5:
				Intent m=new Intent();
				m.setClass(con, StoreAboutActivity.class);
				m.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				con.startActivity(m);
				break;
			case R.id.bnt6:
				close();
				break;
			case R.id.bnt7:
				Intent nt=new Intent();
				nt.setClass(con, ProductImportActivity.class);
				nt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				con.startActivity(nt);
				break;
			case R.id.bnt9:
				Intent men=new Intent();
				men.setClass(con, RedEnvelopeDealMessageActivity.class);
				men.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				con.startActivity(men);
				break;
			}
		}

	}

	public static void fLeftMenuClick() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}
	
	   public void close() {  
	        Intent intent = new Intent();  
	        intent.setAction("closeAll"); // 说明动作  
	        con.sendBroadcast(intent);// 该函数用于发送广播  
	    }
	
	public void returnAPP() {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			con.startActivity(startMain);
			System.exit(0);
		} else {// android2.1
			ActivityManager am = (ActivityManager) con.getSystemService(con.ACTIVITY_SERVICE);
			am.restartPackage(con.getPackageName());
		}
	}
}
