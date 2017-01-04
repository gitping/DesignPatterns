package com.yto.suixingoustore.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BootBrodcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
//		LogUtil.d("boot it ！");
		//context.startService(new Intent(context,MessageService.class));  //启动消息推送
	}

}
