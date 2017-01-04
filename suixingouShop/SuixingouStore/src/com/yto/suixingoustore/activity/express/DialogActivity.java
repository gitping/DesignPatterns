package com.yto.suixingoustore.activity.express;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.message.SMSSentBroadcastReceiver;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * 短信发送广播弹出dialog承载的activity
 * @author ShenHua
 * 2015年5月26日上午10:48:23
 */
public class DialogActivity extends Activity{

	private int flag;
	private String telephone;
	private String content;
	private String dialogContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		flag = getIntent().getIntExtra("flag", 0);
		telephone = getIntent().getStringExtra("telephone");
		content = getIntent().getStringExtra("content");
		dialogContent = "自提通知短信未发送成功，请重发";
		
		if(flag == 0){//第一次重发
			DialogUtil.showOneDialog(this, "短信发送不成功", dialogContent, "重发", new DialogClickCallBack() {							
				@Override
				public void confirmClick(Object obj) {
					Intent it = new Intent(SMSSentBroadcastReceiver.SENT);
					it.putExtra("flag", 1);
					it.putExtra("telephone", telephone);
					it.putExtra("content", content);
					PendingIntent sentPI = PendingIntent.getBroadcast(DialogActivity.this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
					FUtils.SendSMSDirect(telephone, content, sentPI, null);
					DialogActivity.this.finish();
				}
			}, false, getResources().getColor(R.color.mainColor), null);
		}else{//第二次重发
			DialogUtil.showTwoBntTextDialog(this, "短信发送不成功", dialogContent, false, null, "取消", "重发", new DialogClickCallBack() {				
				@Override
				public void confirmClick(Object obj) {
					Intent it = new Intent(SMSSentBroadcastReceiver.SENT);
					it.putExtra("flag", 1);
					it.putExtra("telephone", telephone);
					it.putExtra("content", content);
					PendingIntent sentPI = PendingIntent.getBroadcast(DialogActivity.this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
					FUtils.SendSMSDirect(telephone, content, sentPI, null);
					DialogActivity.this.finish();
				}
				
				@Override
				public void cancelClick(Object obj) {
					super.cancelClick(obj);
					DialogActivity.this.finish();
				}
			});
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "短信发送失败页面");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "短信发送失败页面");
	}
}
