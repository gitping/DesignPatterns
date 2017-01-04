package com.yto.suixingoustore.message;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yto.suixingoustore.FMainActivity;
import com.yto.suixingoustore.activity.StoreMyExpressActivity;

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
		int msgId = intent.getIntExtra("msgId", -1);
		switch (msgId) {
		case 0: //跳转到订单
			it.setClass(context, FMainActivity.class);
			break;
		case 1://跳转到快递单
			it.setClass(context, StoreMyExpressActivity.class);
			break;
		default:
			break;
		}
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(it);
	}
}
