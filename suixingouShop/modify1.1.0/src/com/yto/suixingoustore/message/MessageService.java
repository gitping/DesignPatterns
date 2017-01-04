package com.yto.suixingoustore.message;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MessageService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		flags = START_STICKY;
		Log.i("andy", "onStartCommand");
		PlaySoundPool.getInstance();//初始化一下声音
		MessageManager mm = MessageManager.getMsgManager(this);
		mm.messageStart();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("andy", "Service onDestroy");
	}


}
