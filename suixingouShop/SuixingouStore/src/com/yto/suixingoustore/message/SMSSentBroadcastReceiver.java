package com.yto.suixingoustore.message;

import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.activity.express.DialogActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 短信发送是否成功通知
 * @author ShenHua
 * 2015年5月26日上午9:12:19
 */
public class SMSSentBroadcastReceiver extends BroadcastReceiver{

	public static String SENT = "sms_sent";
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		int flag = intent.getIntExtra("flag", 0);
		if(action.equals(SENT)){
			int code = getResultCode();
			if(code == Activity.RESULT_OK){
				FUtils.showToast(context, "发送成功!");
			}else{
				Intent it = new Intent(context, DialogActivity.class);
				it.putExtra("flag", flag);
				it.putExtra("telephone", intent.getStringExtra("telephone"));
				it.putExtra("content", intent.getStringExtra("content"));
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(it);
			}
		}
	}
}
