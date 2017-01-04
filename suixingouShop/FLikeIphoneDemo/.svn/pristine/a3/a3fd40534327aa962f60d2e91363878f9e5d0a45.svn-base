package com.zhang.ytoxl;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yto.zhang.util.iphoneDialog.IphoneDialogDate;
import com.yto.zhang.util.iphoneDialog.IphoneDialogUtil;
import com.yto.zhang.util.resources.HaiResources;

public class MainActivity extends Activity {
	
	private Button button;
	private IphoneDialogUtil dialogUtil;
//	private String[] PickDateTimes = ;
	private String[] date = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dialogUtil = new IphoneDialogUtil(MainActivity.this);
		button=(Button)findViewById(R.id.button_select_date);
		

		
			
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				DateDialogUtil.getDateDialogUtil().getDateDialog(MainActivity.this, IphoneDialogDate.TYPE_SELLER,"2014-4-28 12:50", new DateDialogCallBack() {
//					
//					@Override
//					public void onSuccess(Object t) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void onFail(Object t) {
//						// TODO Auto-generated method stub
//						
//					}
//				});
//				DateDialogUtil.getDateDialogUtil().getDateDialog(MainActivity.this, IphoneDialogDate.TYPE_CUSTOMER,"360000", new DateDialogCallBack() {
//					
//					@Override
//					public void onSuccess(Object t) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void onFail(Object t) {
//						// TODO Auto-generated method stub
//						
//					}
//				});
//				
//				DateDialogUtil.getDateDialogUtil().getDate(MainActivity.this, 1800000, new DateDialogCallBack() {
//					
//					@Override
//					public void onSuccess(Object t) {
//						// TODO Auto-generated method stub
//						Log.i("andy", "getDate: " + t);
//						
//					}
//					
//					@Override
//					public void onFail(Object t) {
//						// TODO Auto-generated method stub
//						
//					}
//				});
				
//				dialogUtil.getOneTwoDialog(new DialogClickListener(), HaiResources.getTimeShow(1800000)).show();
			}
		});
		button.setText("请选择取件时间");
	}
	
	

	
	
	
	
	
	/**
	 * 选择日期的对话框监听
	 * 
	 * @author administrator
	 * 
	 */
	public class DialogClickListener implements DialogInterface.OnClickListener {
		

		@Override
		public void onClick(DialogInterface dialog, int which) {

//			date = dialogUtil.getDate();

				if (date != null) {
					HaiResources.START_DATE = date[0];
					HaiResources.START_TIME = date[1];
				}
				if (which == DialogInterface.BUTTON_POSITIVE) {
						HaiResources.END_DATE = HaiResources.getAddDate(HaiResources.START_DATE);
						HaiResources.END_TIME = HaiResources.START_TIME;
						button.setText(HaiResources.START_DATE + "  " + HaiResources.START_TIME);
						dialog.dismiss();
				}

				if (which == DialogInterface.BUTTON_POSITIVE) {
						dialog.dismiss();
				}
			}
		}

	

}
