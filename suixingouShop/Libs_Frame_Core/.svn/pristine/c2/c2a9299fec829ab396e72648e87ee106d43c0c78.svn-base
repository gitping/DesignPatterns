package com.frame.lib.utils;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

import com.frame.lib.log.L;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * 
 * 系统参数类
 * @author Cayte
 * @email xusw@dne.com.cn
 * @date 2012-2-27 下午06:34:02
 */
public class SystemUtil {
	private final String TAG = "SystemParams";
	private static SystemUtil params;
	public static int SCREEN_WIDTH = 0;// 屏幕宽度，单位为px
	public static int SCREEN_HEIGHT = 0;// 屏幕高度，单位为px
	public int densityDpi;// 屏幕密度，单位为dpi
	public float scale;// 缩放系数，值为 densityDpi/160
	public float fontScale;// 文字缩放系数，同scale
	public final static int SCREEN_ORIENTATION_VERTICAL = 1; // 屏幕状态：横屏
	public final static int SCREEN_ORIENTATION_HORIZONTAL = 2; // 屏幕状态：竖屏
	public static int screenOrientation = SCREEN_ORIENTATION_VERTICAL;// 当前屏幕状态，默认为竖屏
	public static String versionName="";
	public static int versionCode;
	private static Activity activity;
	public static HashMap<String, String> sysinfo_map;
	public final static String DEVICE_ID="DEVICE_ID";//手机序列号的KEY
	public final static String ROOT="USER";//用户权限的KEY,value值是"root"代表获取了root权限
	public final static String Android_Version="Android_Version";//获取android版本的KEy值
	public final static String PhoneNum="PhoneNum";//获取手机号的KEY
	public static String iPAdress;
	/**
	 * 私有构造方法
	 * @param activity
	 */
	private SystemUtil(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		SCREEN_WIDTH = dm.widthPixels;
		SCREEN_HEIGHT = dm.heightPixels;
		densityDpi = dm.densityDpi;
		scale = dm.density;
		fontScale = dm.scaledDensity;
		screenOrientation = SCREEN_HEIGHT > SCREEN_WIDTH ? SCREEN_ORIENTATION_VERTICAL
				: SCREEN_ORIENTATION_HORIZONTAL;
		versionCode=getVersionCode();
		versionName=getVersionName();
		sysinfo_map=getMobileInfo();
		iPAdress=getLocalIpAddress();
		L.d("sysinfo Hashmap="+sysinfo_map.toString());
	}

	/**
	 * 获取本机IP地址
	 * @return
	 */
	public  String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {

		}
		return null;
	}
	/**
	 * 获取实例
	 * 
	 * @param activity
	 * @return
	 */
	public static SystemUtil getInstance(Activity activity2) {
		activity=activity2;
		if (params == null) {
			params = new SystemUtil(activity);
		}
	
		return params;
	}

	/**
	 * 获取实例
	 * 
	 * @param activity
	 * @return
	 */
	public static SystemUtil instance() {
		return params;
	}

	/**
	 * 获取一个新实例
	 * 
	 * @param activity
	 * @return
	 */
	public static SystemUtil newInstance(Activity activity2) {
		params = null;
		activity=activity2;
		return getInstance(activity);
	}

	/**
	 * 参数信息
	 */
	public String toString() {
		return TAG
				+ ":[screenWidth: "
				+ SCREEN_WIDTH
				+ " screenHeight: "
				+ SCREEN_HEIGHT
				+ " scale: "
				+ scale
				+ " fontScale: "
				+ fontScale
				+ " densityDpi: "
				+ densityDpi
				+ " screenOrientation: "
				+ (screenOrientation == SCREEN_ORIENTATION_VERTICAL ? "vertical"
						: "horizontal") + "]";
	}
	/**
	 * 获取手机的版本名称
	 * 
	 * @return
	 */
	private String getVersionName() {
		try {
			PackageManager pm = activity.getPackageManager();
			PackageInfo info = pm.getPackageInfo(activity.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "Version unknown!";
		}
	}
	
	/**
	 * 获取手机的版本号
	 * 
	 * @return
	 */
	private int getVersionCode() {
		try {
			PackageManager pm = activity.getPackageManager();
			PackageInfo info = pm.getPackageInfo(activity.getPackageName(), 0);
			return info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 获取手机的硬件信息
	 * 
	 * @return
	 */
	private HashMap<String, String> getMobileInfo() {
		StringBuffer sb = new StringBuffer();
		if (activity!=null) {
			L.d("activity is not null");
		}else
		{
			L.d("activity is null");
		}
		 TelephonyManager tm = (TelephonyManager) activity.getBaseContext().
				getSystemService(Context.TELEPHONY_SERVICE);
		// 通过反射获取系统的硬件信息
		HashMap<String, String>	sysinfo_map2=null;
		try {
			Field[] fields = Build.class.getDeclaredFields();
			sysinfo_map2=new HashMap<String, String>();
			for (Field field : fields) {
				// 暴力反射 ,获取私有的信息
				field.setAccessible(true);
				String name = field.getName();
				String value = field.get(null).toString();
				sb.append(name + "=" + value);
				sb.append("\n");
				sysinfo_map2.put(name, value);
			}
			sysinfo_map2.put("DEVICE_ID", tm.getDeviceId());
			sysinfo_map2.put("Android_Version", android.os.Build.VERSION.SDK_INT+"");
			sysinfo_map2.put(PhoneNum, tm.getLine1Number());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysinfo_map2;
	}
}
