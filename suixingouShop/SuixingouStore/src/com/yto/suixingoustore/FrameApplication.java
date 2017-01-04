package com.yto.suixingoustore;

import java.util.Stack;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import cn.jpush.android.api.JPushInterface;

import com.baidu.mapapi.SDKInitializer;
import com.frame.lib.log.C;
import com.frame.lib.utils.SPUtils;
import com.yto.suixingouuser.model.ShopDetail;
import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;


public class FrameApplication extends Application {
	public static FinalBitmap fb;
	public static FinalDb fd;
	public static Context context;
	public static FrameApplication mApp;
	public Stack<Activity> activities;
	public ShopDetail shopDetail;				//店铺详细信息保存
	
	public static FrameApplication getInstance(){
		if(mApp==null){
			mApp=new FrameApplication();
		}
		return mApp;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		mApp=this;
		context=this.getApplicationContext();
		SPUtils.initCon(context);
		UtilAndroid.initCon(context);
		C.init(this);
		Trace.initCon(context);
		fb=FinalBitmap.create(this);
		fb.configMemoryCachePercent(0.5f);
		fb.configDiskCacheSize(4);
		fd = FinalDb.create(context);
		
		activities = new Stack<Activity>();
		shopDetail = new ShopDetail();
		
		//初始化JPush
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
	}
	public static void close() {  
        Intent intent = new Intent();  
        intent.setAction("closeAll"); // 说明动作  
        context.sendBroadcast(intent);// 该函数用于发送广播  
//        FConstants.clear();
    } 
}
