package com.yto.suixingoustore;

import java.util.HashMap;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;
import cn.jpush.android.api.JPushInterface;

import com.baidu.mobstat.StatService;
import com.frame.lib.modle.DialogClickCallBack;
import com.frame.lib.utils.DialogUtil;
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.SysApplication;
import com.frame.lib.utils.SystemUtil;
import com.frame.sxgou.constants.SXGConstants;
import com.frame.sxgou.model.CodeEnum;
import com.umeng.analytics.MobclickAgent;
import com.yto.suixingoustore.activity.AdressListActivity;
import com.yto.suixingoustore.activity.StoreMyExpressActivity;
import com.yto.suixingoustore.activity.express.FLoginActivity;
import com.yto.suixingoustore.activity.express.RegisterTelFillInActivity;
import com.yto.suixingoustore.message.MessageManager;
import com.yto.suixingouuser.activity.helper.MainHelper;
import com.yto.suixingouuser.activity.helper.model.FConstants;
import com.yto.suixingouuser.util.MyUtils;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.zhang.util.modle.ExpressBean;

public abstract class FBaseActivity extends FragmentActivity {
	public Activity mActivity;
	public FrameApplication mApp;
	protected Context mContext;
	protected MyUtils utils;
	public MainHelper mainHelper = MainHelper.getInstance();
	//public MainHelper mainHelper = new MainHelper();;

	/**
	 * 每个activity首先调用 依次做初始化、界面设置、处理来自其他的activity的参数
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			AdressListActivity.str = savedInstanceState.getString("1");
			StoreMyExpressActivity.hashmap = (HashMap<String, Integer>) savedInstanceState.getSerializable("2");
			MessageManager.curMsgNum = savedInstanceState.getInt("3");
			FMainActivity.hashmap = (HashMap<String, Integer>) savedInstanceState.getSerializable("4");
			FConstants.bean = (ExpressBean) savedInstanceState.getSerializable("5");
		}
		mActivity = this;
		utils = new MyUtils();
		mApp = FrameApplication.getInstance();
		mApp.activities.add(this);
		SysApplication.getInstance().addActivity(this);
		SystemUtil.getInstance(this);
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
		outState.putSerializable("5", FConstants.bean);
	}

	/**
	 * 20140414--suixingou--store
	 */
	/** 退出应用 */
	public void exitApp() {
		SysApplication.getInstance().exit();
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
	protected void setViewOnClickListener() {
	}

	/**
	 * 读取intent参数, 比如处理传递的数据
	 */
	protected void handleIntentData() {
	}

	/**
	 * 默认的请求
	 */
	protected void baseRequest() {
	}

	/**
	 * 公共点击事件20140415
	 * 
	 * @param Linken
	 */
	public void fMenuOnclick(RelativeLayout relative, View v) {
		switch (v.getId()) {
		case R.id.but_topright:
			if (this.getClass().getName().equals("com.yto.suixingoustore.activity.RegisterTelFillInActivity")) {
				((RegisterTelFillInActivity) this).saveTel();
			}
			finish();
			break;
		}
	}

	public void finishOnclick(View v) {
		finish();
	}

	/**
	 * 销毁当前activity
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// unregisterReceiver(broadcastReceiver);
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
		mContext = this;
		//StatService.onResume(this);	
		JPushInterface.onResume(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		IntentFilter filter = new IntentFilter();
		filter.addAction("closeAll");
		registerReceiver(broadcastReceiver, filter); // 注册广播，用于退出关闭所有的activity
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(broadcastReceiver);
	}

	// 写一个广播的内部类，当收到动作时，结束activity
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			//unregisterReceiver(this); // 这句话必须要写要不会报错，虽然能关闭，不过会报一堆错
			FBaseActivity.this.finish();
		}
	};

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		//StatService.onPause(this);
		JPushInterface.onPause(this);
	}

	public void fail(int failCode) {
		switch (failCode) {
		case 61:
			DialogUtil.showOneDialog(mContext, mContext.getString(R.string.sxg_offline_title),mContext.getString(R.string.sxg_offline_context), new DialogClickCallBack() {

				@Override
				public void confirmClick(Object obj) {
					SXGConstants.setUUID(SXGConstants.UUIDCON);
					SXGConstants.setCipher(SXGConstants.CipherCON);
					startActivity(new Intent(FBaseActivity.this, FLoginActivity.class));
					finish();

				}
			}, false, getResources().getColor(R.color.mainColor), null);
			break;
		case 78:
			UtilAndroid.toastMsg("该银行卡已经被绑定.");
			break;
		case 62:
			UtilAndroid.toastMsg("验证码不匹配");
			break;
		default:
			FUtils.showToast(this, CodeEnum.getNameByCode(failCode));
			break;
		}
		// if(failCode > 10 && failCode != 61&& failCode != 62 && failCode !=
		// 78||failCode==0){
		// utils.showLongToast(this, "网络异常");
		// }

	}

	public void fail(int failCode, String msg) {
		switch (failCode) {
		case 61:
			DialogUtil.showOneDialog(mContext, mContext.getString(R.string.sxg_offline_title),mContext.getString(R.string.sxg_offline_context), new DialogClickCallBack() {

				@Override
				public void confirmClick(Object obj) {
					SXGConstants.setUUID(SXGConstants.UUIDCON);
					SXGConstants.setCipher(SXGConstants.CipherCON);
					startActivity(new Intent(FBaseActivity.this, FLoginActivity.class));
					finish();

				}
			}, false, getResources().getColor(R.color.mainColor), null);
			break;
		case 1033:
			DialogUtil.showOneDialog(mContext, "账号不存在，请重新登录.", new DialogClickCallBack() {
				
				@Override
				public void confirmClick(Object obj) {
//					UtilAndroid.saveStringValue("UUID", "");// 清除
					SXGConstants.setUUID(SXGConstants.UUIDCON);
					SXGConstants.setCipher(SXGConstants.CipherCON);
					startActivity(new Intent(FBaseActivity.this, FLoginActivity.class));
					finish();
					
				}
			}, false, getResources().getColor(R.color.mainColor), null);
			break;
		case 0:
			FUtils.showToast(this, "服务器亚历山大，请稍后再试");
			break;
		default:
			FUtils.showToast(this, msg);
			break;
		}
	}
}