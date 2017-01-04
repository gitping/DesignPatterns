package com.frame.view.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.frame.view.R;

/**
 * 自定义loading
 * @author Andy
 * Create on 2014 2014-12-1 下午5:09:29
 */
public class DialogLoading extends ProgressDialog {
	private static Activity ac1;
	private boolean cancel;
	
	private static DialogLoading dl;
	public static DialogLoading getInstance(Activity ac){
		if(dl == null){
			dl = new DialogLoading(ac);
		}else if(!ac1.equals(ac)){
			dl = new DialogLoading(ac);
		}
		return dl;
	}
	public static DialogLoading getInstance(Activity ac,boolean back){
		if(dl == null){
			dl = new DialogLoading(ac,back);
		}else if(!ac1.equals(ac)){
			dl.dismiss();
			dl = new DialogLoading(ac,back);
		}
		return dl;
	}
	
	public DialogLoading(Activity ac) {
		super(ac);
		DialogLoading.ac1 = ac;
	}
	public DialogLoading(Activity ac,boolean cancel) {
		super(ac);
		DialogLoading.ac1 = ac;
		this.cancel = cancel;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = this.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 1.0f;// 透明度
		lp.dimAmount = 0.1f;// 黑暗度
		window.setAttributes(lp);
		setContentView(R.layout.dialogloading);
		this.setCancelable(false);
	}
	
	@Override
	public void show() {
		if(!this.isShowing() && ac1 != null && !ac1.isFinishing()){
			super.show();
		}
	}
	
	@Override
	public void dismiss() {
		if(this.isShowing() && ac1 != null && !ac1.isFinishing()){
			super.dismiss();
		}
	}
	
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && cancel) {
			ac1.finish();
		} else {
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}
}
