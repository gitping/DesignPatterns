package com.frame.lib.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.lidroid.xutils.BitmapUtils;

public class SysApplication extends Application {  
    private List<Activity> mList = new LinkedList<Activity>();  
   private static SysApplication instance;   
   private static BitmapUtils bitmapUtils;
    private SysApplication(){}  
    public synchronized static SysApplication getInstance(){   
    	if (null == instance) {
            instance = new SysApplication();   
        }   
        return instance;   
    } 
    public  static BitmapUtils getBitmapInstance(Context context){   
    	if (null == bitmapUtils) {
    		bitmapUtils = new BitmapUtils(context);
        }   
        return bitmapUtils;   
    } 
    
    /**'
     * 关闭上一个页面
     */
    public void closeLastActivity()
    {
    	mList.get(mList.size()-2).finish();
    }
    
   // add Activity    
    public void addActivity(Activity activity) {   
        mList.add(activity);   
    }   
    public void exit() {    
       try {   
            for (Activity activity:mList) {   
             if (activity != null)   
                  activity.finish();   
            }   
       } catch (Exception e) {             e.printStackTrace();   
        } finally {   
        }   
    }   
    public void onLowMemory() {   
        super.onLowMemory();       
        System.gc();   
    }    
    
    /**
     * 关闭单个activity
     * @param clazz
     */
    public void closeActivity(Class<?> clazz)
    {
    	for (Activity act : mList) {
			if (act.getClass()==clazz) {
				act.finish();
			}
		}}

}  

