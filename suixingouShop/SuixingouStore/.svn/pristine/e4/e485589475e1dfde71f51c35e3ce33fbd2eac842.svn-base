package com.yto.suixingoustore.receive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.frame.lib.log.L;
import com.frame.lib.utils.FUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.suixingoustore.activity.express.PushMessageActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class PushHandlerReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		if(bundle != null){
			/**
			 * 解析扩展字段
			 */
			Gson gson = new Gson();
			String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
			if(!FUtils.isStringNull(extra)){//抢单的三种类型
				Map<String, String> map = gson.fromJson(extra, HashMap.class);
			    String extdata = map.get("extdata");
			    String extcontent = map.get("extcontent");
			    L.i("extdata" + extdata);
			    if(!FUtils.isStringNull(extdata)){
			    	/*PushMessage pushMessage = gson.fromJson(extdata, PushMessage.class);
					
					if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			        	//打开自定义的Activity
			        	Intent i = new Intent(context, GrabOrderActivity.class);
			        	i.putExtra("pushMessage", pushMessage);
			        	i.putExtra(SkipConstants.GRAB_SKIP_KEY, SkipConstants.GRAB_SKIP_PUSH);
			        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        	context.startActivity(i);       	
			        } else {
			        	//打开服务，开始语音播报及提醒计时
			        	Intent i = new Intent(context, PushHandleService.class);
			        	i.putExtra("pushMessage", pushMessage);
			        	i.putExtras(bundle);
			        	context.startService(i);
			        }*/
			    }else if(!FUtils.isStringNull(extcontent)){
			    	if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			        	//打开自定义的Activity
			        	Intent i = new Intent(context, PushMessageActivity.class);
			        	i.putExtras(bundle);
			        	i.putExtra("extcontent", extcontent);
			        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        	context.startActivity(i);
			    	}
			        /*} else {
			        	Intent i = new Intent(context, PushHandleService.class);
			        	i.putExtras(bundle);
			        	context.startService(i);
			        }*/
			    }
			}
		}
	}
	
	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} 
			else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
}
