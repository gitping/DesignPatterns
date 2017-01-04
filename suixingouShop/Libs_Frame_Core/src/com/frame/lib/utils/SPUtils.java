package com.frame.lib.utils;

import com.frame.lib.log.L;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
	private static Context con;
	private static SharedPreferences sp;
	public static String fileName = "ytoxlPreferences";
	public static void initCon(Context con) {
		SPUtils.con = con;
	}
	/**
	 * 保存信息
	 * 
	 * @param name
	 * @param value
	 */
	public static void saveStringValue(String name, String value) {
		if (con == null) {
			L.d("UtilAndroid,saveStringValue() con is null: " + name);
			return;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(name, value);
		editor.commit();
	}

	/**
	 * 根据key把SharedPreferences中的value取出
	 * 
	 * @param key
	 * @return
	 */
	public static String getStringValue(String key) {
		String value = "";
		if (con == null) {
			L.d("UtilAndroid,getStringValue() con is null");
			return value;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		value = sp.getString(key, value);
		return value;
	}
	/**
	 * 保存信息
	 * 
	 * @param name
	 * @param value
	 */
	public static void saveBooleanValue(String name, boolean value) {
		if (con == null) {
			L.d("UtilAndroid,saveStringValue() con is null: " + name);
			return;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean(name, value);
		editor.commit();
	}
	
	/**
	 * 根据key把SharedPreferences中的value取出
	 * @param key
	 * @return
	 */
	public static boolean getBooleanValue(String key) {
		boolean value = false;
		if (con == null) {
			L.d("UtilAndroid,getStringValue() con is null");
			return false;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		value = sp.getBoolean(key, value);
		return value;
	}

	/**
	 * 保存信息
	 * 
	 * @param name
	 * @param value
	 */
	public static void saveIntValue(String name, int value) {
		if (con == null) {
			L.d("UtilAndroid,save() con is null: " + name);
			return;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(name, value);
		editor.commit();
	}
	/**
	 * 保存信息
	 * 
	 * @param name
	 * @param value
	 */
	public static void saveLongValue(String name, long value) {
		if (con == null) {
			L.d("UtilAndroid,save() con is null: " + name);
			return;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		SharedPreferences.Editor editor = sp.edit();
		editor.putLong(name, value);
		editor.commit();
	}

	/**
	 * 根据key把SharedPreferences中的value取出
	 * 
	 * @param key
	 * @return
	 */
	public static int getIntValue(String key) {
		int value = 0;
		if (con == null) {
			L.d("UtilAndroid,getValue() con is null");
			return value;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		value = sp.getInt(key, value);
		return value;
	}

	/**
	 * 根据key把SharedPreferences中的value取出
	 * 
	 * @param key
	 * @return
	 */
	public static long getLongValue(String key) {
		long value = 0;
		if (con == null) {
			L.d("UtilAndroid,getValue() con is null");
			return value;
		}
		if (sp == null) {
			sp = con.getSharedPreferences(fileName, 0);
		}
		value = sp.getLong(key, value);
		return value;
	}
}
