package com.yto.suixingoustore.message;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yto.suixingoustore.FMainActivity;
import com.yto.suixingoustore.activity.AppCollectActivity;

public class MessageReceiver extends BroadcastReceiver {
	Intent it = new Intent();
	@Override
	public void onReceive(Context context, Intent intent) {
		MessageManager.setCurMsgNum(0);
		if(MessageManager.manager == null){
			NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.cancel(100);
		}else{
			MessageManager.manager.cancelAll();
		}
		PlaySoundPool.stop();//停止播放声音
		int objectType = intent.getIntExtra("objectType", -1);
		FMainActivity.remberStatus=1;
		if(objectType == 3){//
//			it.putExtra("objectId", intent.getIntExtra("objectId", 0));
//			it.setClass(context, AppCollectActivity.class);
		}else{
			it.setClass(context, FMainActivity.class);
			it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivity(it);
		}
	}
}
