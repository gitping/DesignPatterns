package com.yto.suixingouuser.util.log;

import android.content.Context;
import android.util.Log;

public class Trace {
	public static boolean logFlat = true;
	
	public static boolean storeFlat = true;
	
	private static String TAG = "ytoxl";
	
	private static Context con;
	
	public static void i(String TAG,String msg){
		if(logFlat){
			Log.i(TAG, msg);
		}
	}
	public static void initCon(Context con){
		Trace.con = con;
	}
	
	public static void i(String msg){
		if(msg == null){
			msg = "null String";
		}
		if(storeFlat && con != null){
			if(StoreFile.INSTANCE == null){
				StoreFile.getInstance(con).start();
			}
			StoreFile.queue.offer(msg);
		}
		if(logFlat){
			Log.i(TAG, msg);
		}
	}
	
	
	
	

}
