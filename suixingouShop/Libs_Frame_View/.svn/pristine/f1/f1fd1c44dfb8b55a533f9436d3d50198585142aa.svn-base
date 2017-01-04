package com.frame.view.dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.frame.view.R;

/**
 * 全局的Dialog,不需要Activity,Context可以是Application
 * @author Andy
 * Create on 2014 2014-5-29 下午1:32:55
 */
public class ViewDialog {
	private static Context con;
	
	public static ViewDialog vd;
	private ViewDialog(){}
	public static ViewDialog getInstance(Context con){
		if(vd == null){
			vd = new ViewDialog();
		}
		if(con != null){
			ViewDialog.con = con;
		}
		return vd;
	}
	
	
	/**需要把XML的id传过来
	 * @param xmlId
	 * @returnDialog
	 */
	public Dialog globalDialog(int xmlId){
		Builder builder=new Builder(con);
		builder.setView(LayoutInflater.from(con).inflate(xmlId, null));
		builder.setCancelable(true);
		Dialog dialog=builder.create();
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PRIORITY_PHONE);
		dialog.show();
		return dialog;
	}
	
	private Dialog dialog;
	DialogCallBack dcb;
	public Dialog globalDialog(DialogCallBack dcb){
		this.dcb = dcb;
		Builder builder=new Builder(con);
		View v = LayoutInflater.from(con).inflate(R.layout.uncaseexceptiondialog, null);
		v.findViewById(R.id.exception_cancel).setOnClickListener(new GlobalDialogClick());
		v.findViewById(R.id.exception_confirm).setOnClickListener(new GlobalDialogClick());
		builder.setView(v);
		builder.setCancelable(true);
		Dialog dialog=builder.create();
//		AlertDialog dialog=builder.create();
//	    dialog.setView(v, 0, 0, 0, 0);
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
		this.dialog = dialog;
		return dialog;
	}
	
	class GlobalDialogClick implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			dialog.dismiss();
			switch (v.getId()) {
//			case R.id.exception_cancel:
//				dcb.onCancel();
//				break;
//			case R.id.exception_confirm:
//				dcb.onConfirm();
//				break;

			}
		}
		
	}
	
	
	public void globalDialogClick(View v){}

}
