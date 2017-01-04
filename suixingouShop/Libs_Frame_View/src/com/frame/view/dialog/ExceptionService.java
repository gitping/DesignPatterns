package com.frame.view.dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.frame.view.R;

public class ExceptionService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	public void onCreate() {
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		globalDialog(new DialogCallBack() {
			@Override
			public void onConfirm() {
				reStart();
			}
			@Override
			public void onCancel() {
			}
		});
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void reStart() {
		Intent i = getApplicationContext().getPackageManager().getLaunchIntentForPackage(getApplicationContext().getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);

	}
    public void close() {  
        Intent intent = new Intent();  
        intent.setAction("closeAll"); // 说明动作  
        sendBroadcast(intent);// 该函数用于发送广播  
    }
    
    
    
    
	private Dialog dialog;
	DialogCallBack dcb;
	public Dialog globalDialog(DialogCallBack dcb){
		this.dcb = dcb;
		Builder builder=new Builder(getApplicationContext());
		View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.uncaseexceptiondialog, null);
		v.findViewById(R.id.exception_cancel).setOnClickListener(new GlobalDialogClick());
		v.findViewById(R.id.exception_confirm).setOnClickListener(new GlobalDialogClick());
//		builder.setView(v);
		builder.setCancelable(true);
//		Dialog dialog=builder.create();
		AlertDialog dialog=builder.create();
	    dialog.setView(v, 0, 0, 0, 0);
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
		this.dialog = dialog;
		return dialog;
	}
	
	class GlobalDialogClick implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			dialog.dismiss();
//			switch (v.getId()) {
//			case R.id.exception_cancel:
//				dcb.onCancel();
//				break;
//			case R.id.exception_confirm:
//				dcb.onConfirm();
//				break;
//
//			}
		}
		
	}
}