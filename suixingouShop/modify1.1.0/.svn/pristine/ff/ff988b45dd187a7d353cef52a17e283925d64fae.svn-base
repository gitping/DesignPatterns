package com.yto.suixingoustore;

import java.util.Stack;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.yto.suixingouuser.util.android.UtilAndroid;
import com.yto.suixingouuser.util.log.Trace;


public class FrameApplication extends Application {
	public static FinalBitmap fb;
	public static FinalDb fd;
	public static Context context;
	public static FrameApplication mApp;
	public Stack<Activity> activities;
	
	public static FrameApplication getInstance(){
		if(mApp==null){
			mApp=new FrameApplication();
		}
		return mApp;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mApp=this;
		context=this.getApplicationContext();
		UtilAndroid.initCon(context);
		Trace.initCon(context);
		fb=FinalBitmap.create(this);
		fb.configMemoryCachePercent(0.5f);
		fb.configDiskCacheSize(4);
		fd = FinalDb.create(context);
		
		activities = new Stack<Activity>();
		
	}
}
