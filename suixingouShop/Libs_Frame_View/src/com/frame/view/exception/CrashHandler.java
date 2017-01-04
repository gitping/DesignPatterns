package com.frame.view.exception;

import java.lang.Thread.UncaughtExceptionHandler;

import test.view.slidealphapostion.BBBActivity;

import android.content.Context;
import android.content.Intent;

import com.frame.view.dialog.DialogCallBack;
import com.frame.view.dialog.ViewDialog;

public class CrashHandler implements UncaughtExceptionHandler {
	private Context mContext;
	private static CrashHandler INSTANCE = new CrashHandler();
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private CrashHandler() {
	}

	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	public void init(Context ctx) {
		mContext = ctx;
		
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
		
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		try {
			ex.printStackTrace();
			if(mDefaultHandler != null){
				mDefaultHandler.uncaughtException(thread, ex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
//		Intent it = new Intent(mContext, BBBActivity.class);
//		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		mContext.startActivity(it);
//		ViewDialog vd = ViewDialog.getInstance(mContext);
//		vd.globalDialog(new DialogCallBack() {
//			@Override
//			public void onConfirm() {
//				reStart();
//			}
//			@Override
//			public void onCancel() {
//				close();
//			}
//		});
//		Intent intent=new Intent();
//    	intent.setAction("com.frame.view.dialog.ExceptionService");
//		mContext.startService(intent);
	}

	private void reStart() {
		Intent i = mContext.getApplicationContext().getPackageManager().getLaunchIntentForPackage(mContext.getApplicationContext().getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mContext.startActivity(i);

	}
    public void close() {  
        Intent intent = new Intent();  
        intent.setAction("closeAll"); // 说明动作  
        mContext.sendBroadcast(intent);// 该函数用于发送广播  
    } 

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}

		return true;
	}
}