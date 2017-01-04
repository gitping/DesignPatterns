package com.yto.suixingoustore;

import java.util.HashMap;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.yto.suixingoustore.activity.AdressListActivity;
import com.yto.suixingoustore.activity.FLoginActivity;
import com.yto.suixingoustore.activity.StoreMyExpressActivity;
import com.yto.suixingoustore.message.MessageManager;
import com.yto.suixingouuser.util.MyUtils;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.view.FLeftMeunPoup;
import com.yto.zhang.util.iphoneDialog.FrameDialogBuilder;
import com.yto.zhang.util.iphoneDialog.FrameDialogCallBack;

public abstract class FBaseActivity extends Activity {
	public Activity mActivity;
	public FrameApplication mApp;
	protected  Context mContext;
	protected MyUtils utils;
	/**
	 * 每个activity首先调用 依次做初始化、界面设置、处理来自其他的activity的参数
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null){
			AdressListActivity.str=savedInstanceState.getString("1");
			StoreMyExpressActivity.hashmap=(HashMap<String, Integer>) savedInstanceState.getSerializable("2");
			MessageManager.curMsgNum=savedInstanceState.getInt("3");
			FMainActivity.hashmap=(HashMap<String, Integer>) savedInstanceState.getSerializable("4");
		}
		mContext=this;
		mActivity = this;
		utils=new MyUtils();
		mApp = FrameApplication.getInstance();
		mApp.activities.add(this);
		
		init();
		setupView();
		setViewOnClickListener();
		handleIntentData();
		baseRequest();
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("1", AdressListActivity.str);
		outState.putSerializable("2", StoreMyExpressActivity.hashmap);
		outState.putInt("3", MessageManager.curMsgNum);
		outState.putSerializable("4", FMainActivity.hashmap);
	}
	/**
	 * 20140414--suixingou--store
	 */
	/** 退出应用 */
	public void exitApp() {
		UtilAndroid.exitApp(mApp.activities);
	}

	/** 判断网络是否有效 */
	public boolean isNetAvailable() {
		return UtilAndroid.isNetAvailable(this);
	}

	/**
	 * 负责有关handler等对象实例化 非界面ui组件的初始化
	 */
	protected abstract void init();

	/**
	 * 设置界面ui 如果使用xml描述ui, 先设置setContentView(R.layout.xx);
	 * 然后依次初始化ui对象\通知注册事件监听器registerListener()
	 */
	protected abstract void setupView();

	/**
	 * 加控制监听
	 */
	protected abstract void setViewOnClickListener();

	/**
	 * 读取intent参数, 比如处理传递的数据
	 */
	protected abstract void handleIntentData();

	/**
	 * 默认的请求
	 */
	protected abstract void baseRequest();
	
	
	/**
	 * 公共点击事件20140415
	 * @param Linken
	 */
	public void fBaseOnclick(RelativeLayout relative,View v){
		switch (v.getId()) {
		case R.id.stitlebarMenu:
			FLeftMeunPoup.getPopInstance().showMeum(relative, (Button)v);
			break;
		case R.id.storemain_lin_kddd:
			Intent intent=new Intent(this,StoreMyExpressActivity.class);
			startActivity(intent);
			break;
		case R.id.storemain_lin_spdd:
			Intent in=new Intent(this,FMainActivity.class);
			startActivity(in);
			finish();
			break;
		
		}
	}

	/**
	 * 销毁当前activity
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 返回按键
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	/**
	 * 恢复当前activity
	 */
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		IntentFilter filter = new IntentFilter();  
        filter.addAction("closeAll");  
        registerReceiver(this.broadcastReceiver, filter); // 注册广播，用于退出关闭所有的activity

	}
	
	 // 写一个广播的内部类，当收到动作时，结束activity  
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            unregisterReceiver(this); // 这句话必须要写要不会报错，虽然能关闭，不过会报一堆错  
            FBaseActivity.this.finish();
        }  
    };  

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	public void fail(int failCode){

		switch(failCode){
		case 61:
			FrameDialogBuilder fdb = new FrameDialogBuilder(this, "提示", "账号不存在，请重新登录.", "确定");
			fdb.setCallBackListener(new FrameDialogCallBack() {
				@Override
				public void rightButtonListener() {
					
				}
				@Override
				public void leftButtonListener() {
					UtilAndroid.saveStringValue("UUID", "");//清除
					startActivity(new Intent(FBaseActivity.this,FLoginActivity.class));
					finish();
				}
			});
			fdb.show();
			break;
		}
		if(failCode > 10 && failCode != 61){
			utils.showLongToast(this, "网络异常");
		}
		
		
	}
}