package com.frame.lib.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.PendingIntent.CanceledException;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.frame.lib.config.R;
import com.frame.lib.log.L;
import com.frame.lib.modle.FRequestCallBack;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.core.BitmapSize;

@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
public class FUtils {
	/**
	 * 这里都是一些通用的方法
	 */
	private static Toast toast;
	private static int temS;
	private static int temFlat;
	private static byte[] b = new byte[0];
	private static boolean countdownFlat = true;
	private static Context context;
	private static SharedPreferences sp;
	private static String spName;
	private static Editor editor;
	private static String uuid;

	private static double rad(double d) {
		return d * Math.PI / 180.0000;
	}

	private final static double EARTH_RADIUS = 6378137.0;

	public static void getInstance(Context context) {
		FUtils.context = context;
	}

	/**
	 * @param activity
	 * @param content
	 *            显示长时间toast的方法
	 */
	public static void showToast(Context activity, String content) {
		if(content != null && content.length() >0){
			if (toast == null) {
				toast = Toast.makeText(activity, content, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				toast.setText(content);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		}
	}
	
	/**
	 * @param activity
	 * @param content
	 * @param gravity
	 * @param xOffset
	 * @param yOffset
	 * 显示长时间,能自定义位置的toast的方法
	 */
	public static void showToast(Context activity, String content, int gravity, int xOffset, int yOffset) {
		if(content != null && content.length() >0){
			if (toast == null) {
				toast = Toast.makeText(activity, content, Toast.LENGTH_LONG);
				toast.setGravity(gravity, xOffset, yOffset);
				toast.show();
			} else {
				toast.setText(content);
				toast.setGravity(gravity, xOffset, yOffset);
				toast.show();
			}
		}
	}

	/**
	 * 建立一个sharePreference,返回EDITOR
	 * 
	 * @param context
	 * @param name
	 *            SHAREPREFERENCE的名字
	 * @return
	 */
	@SuppressLint("CommitPrefEdits")
	public static void CreatSharePreference(String name) {
		spName = name;
		SharedPreferences share = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		editor = share.edit();
	}

	/**
	 * 判断当前activity是否处于活动状态
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isTopActivity(Activity activity, String packageName) {
		ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 传一个时间,返回当前时间与这个时间相加
	 * 
	 * @param seconds
	 *            秒
	 * @returnString
	 */
	public static String timeAddCurTime(int seconds) {
		String str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		str = sdf.format(new Date().getTime() + seconds * 1000);
		return str;
	}

	/**
	 * 把日期字符串转换成时间的方法
	 * 
	 * @param time
	 * @return
	 */
	public static long dateToLong(String time) {
		long rand = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d2 = null;
			try {
				d2 = sdf.parse(time);// 将String to Date类型
				rand = d2.getTime();
			} catch (ParseException e) {
				e.printStackTrace(); // To change body of catch statement use
										// File | Settings | File Templates.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rand;
	}

	/**
	 * 判断当前的软件是否在运行
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isSoftWearRunning(Context context, String packageName) {
		@SuppressWarnings("static-access")
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		// get the info from the currently running task
		List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
		ComponentName componentInfo = taskInfo.get(0).topActivity;
		// 假如软件正在运行，里面的字符串是应用的包名
		if (componentInfo.getPackageName().equalsIgnoreCase(packageName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检测wifi是否可用
	 * 
	 * @param inContext
	 * @return
	 */
	public static boolean isWiFiActive(Context inContext) {
		Context context = inContext.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * currentTime:(获得当前时间的方法(年月日时分秒))
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTime() {
		Date nowTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
		String nowTimeStr = dateFormat.format(nowTime);
		return nowTimeStr;
	}

	/**
	 * currentTime:(获得当前时间的方法(年月日时分秒))
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTime(String format) {
		Date nowTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);// 可以方便地修改日期格式
		String nowTimeStr = dateFormat.format(nowTime);
		return nowTimeStr;
	}

	/**
	 * currentDate:(获得当日日期)年月日
	 * 
	 * @param @return 设定文件
	 * @return String DOM对象
	 * @throws
	 */
	public static String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDate = sdf.format(date); // 当期日期
		return currentDate;
	}

	/**
	 * 判断字符串是不是为空，包括null "null" ""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmPty(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 把DATE类型格式化成字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDate = sdf.format(date); // 当期日期
		return currentDate;
	}
	
	/**
	 * 把DATE类型格式化成字符串
	 * 
	 * @param date 时间Date类型
	 * @param format 转成String的格式
	 * @return
	 */
	public static String DateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String currentDate = sdf.format(date); // 当期日期
		return currentDate;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 判断当前日期周几 ,0-6依次代表周日到周六
	 */
	public static int CurrentDateGetWeek(Date date) {
		int w = getTimeField(date, Calendar.DAY_OF_WEEK);
		int cw = 0;
		switch (w) {
		case Calendar.SUNDAY:
			cw = 0;
			break;
		case Calendar.MONDAY:
			cw = 1;
			break;
		case Calendar.TUESDAY:
			cw = 2;
			break;
		case Calendar.WEDNESDAY:
			cw = 3;
			break;
		case Calendar.THURSDAY:
			cw = 4;
			break;
		case Calendar.FRIDAY:
			cw = 5;
			break;
		case Calendar.SATURDAY:
			cw = 6;
			break;
		default:
			break;
		}
		return cw;

	}

	private static int getTimeField(Date date, int field) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(field);
	}

	/**
	 * 判断是否为手机号
	 * 
	 * @param phoneNum
	 * @returnboolean
	 */
	public static boolean isPhoneNum(String phoneNum) {
		return matches(phoneNum, "^1\\d{10}$");
	}
	
	/**
	 * 判断只有中英文字符的名字
	 * 
	 * @param phoneNum
	 * @returnboolean
	 */
	public static boolean isName(String name) {
		return matches(name, "^[A-Za-z\u4e00-\u9fa5.]+$");
	}

	/**
	 * 是不是( 9999.99 )形式的数据
	 * 
	 * @param num
	 * @returnboolean
	 */
	public static boolean isOnlyTwoDecimals(String num) {
		return matches(num, "^(([1-9]{1}([0-9]{1,3})?(\\.[0-9]{1,2})?)|([0]{1}(\\.[1-9]{1,2})))$");
	}

	/**
	 * 是不是( 9999.99 )形式的数据
	 * 
	 * @param num
	 * @returnboolean
	 */
	public static boolean isNumber(String num) {
		return matches(num, "[0-9]+");
	}

	/**
	 * 比对正则
	 * 
	 * @param str
	 * @param reg
	 * @returnboolean
	 */
	public static boolean matches(String str, String reg) {
		boolean boo = false;
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(str);
		boo = mat.matches();
		return boo;
	}

	/**
	 * 判断time2是否比time1大
	 * 
	 * @param time1
	 * @param time2
	 * @returnboolean
	 */
	public static boolean isbigTime(String time1, String time2) {
		boolean re = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = sdf.parse(time1);
			Date dt2 = sdf.parse(time2);
			if (dt2.getTime() > dt1.getTime()) {
				re = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		return re;
	}

	/**
	 * getBytesFromFile:(把文件转换为btye数组的方法)
	 */
	@SuppressWarnings("resource")
	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		// 获取文件大小
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// 文件太大，无法读取
			L.e("File is to large " + file.getName());
			throw new IOException("File is to large " + file.getName());
		}// 创建一个数据来保存文件数据
		byte[] bytes = new byte[(int) length];
		// 读取数据到byte数组中
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}// 确保所有数据均被读取
		if (offset < bytes.length) {
			L.e("Could not completely read file " + file.getName());
			throw new IOException("Could not completely read file " + file.getName());
		}
		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	/***
	 * 图片的缩放方法
	 * 
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight)
	// 获取这个图片的宽和高
	{

		float width = bgimage.getWidth();
		float height = bgimage.getHeight();

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
		return bitmap;
	}

	/**
	 * 复制文件的方法
	 * 
	 * @param targetFile
	 * @param file
	 */
	public static void copyFile(File targetFile, File fromFile) {
		if (targetFile.exists()) {
			L.e("文件" + targetFile.getAbsolutePath() + "已经存在，跳过该文件！");
			return;
		} else {
			createFile(targetFile, true);
		}
		L.e("复制文件" + fromFile.getAbsolutePath() + "到" + targetFile.getAbsolutePath());
		try {
			InputStream is = new FileInputStream(fromFile);
			FileOutputStream fos = new FileOutputStream(targetFile);
			byte[] buffer = new byte[1024];
			while (is.read(buffer) != -1) {
				fos.write(buffer);
			}
			is.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createFile(File file, boolean isFile) {
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				createFile(file.getParentFile(), false);
			} else {
				if (isFile) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					file.mkdir();
				}
			}
		}
	}

	/**
	 * 通过网络URL来返回一张bitmap
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpImage(String url) {
		try {
			URL picUrl = new URL(url);
			Bitmap bitmap = BitmapFactory.decodeStream(picUrl.openStream());
			return bitmap;
		} catch (Exception e) {
			L.e("getHttpImage Method; error=" + e.getMessage());
			return null;
		}

	}

	/**
	 * 跳转到短信发送界面，
	 * 
	 * @param activity
	 * @param phoneNum
	 */
	public static void SendSMS(Activity activity, String phoneNum, String msgBody) {
		Uri uri = Uri.parse("smsto:" + phoneNum);
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		it.putExtra("sms_body", msgBody);
		activity.startActivity(it);
	}
	
	/**
	 * 直接发送短信到指定号码
	 * @param phoneNum
	 * @param msgBody
	 */
	public static void SendSMSDirect(String phoneNum, String msgBody, PendingIntent sentPI, PendingIntent deliveredPI){
		SmsManager smsMgr = SmsManager.getDefault();
		//smsMgr.sendTextMessage(phoneNum, null, msgBody, sentPI, deliveredPI);
		//防止在短信长度大于70的时候出现报空指针的情况（大于70时分多条短信发出）
		if(msgBody.length() > 70){
            ArrayList<String> msgs = smsMgr.divideMessage(msgBody);
            for(String msg : msgs) {
                if (msg != null) {
                	smsMgr.sendTextMessage(phoneNum, null, msg, sentPI, deliveredPI);
                }
            }
        } else {
        	smsMgr.sendTextMessage(phoneNum, null, msgBody, sentPI, deliveredPI);
        }
	}

	/**
	 * 获取号码打进入拨号界面
	 * 
	 * @param phoneNum
	 */
	public void tackCall(Activity activity, String phoneNum) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
		activity.startActivity(intent);
	}

	/**
	 * 关闭程序
	 * 
	 * @param activity
	 */
	public static void closeSoftwear(Context activity) {
		SysApplication.getInstance().exit();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 页面跳转
	 * 
	 * @param context
	 * @param clazz
	 */
	public static void startActivity(Context context, Class<?> clazz, String key, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		intent.putExtra(key, bundle);
		context.startActivity(intent);
	}

	/**
	 * 页面跳转
	 * 
	 * @param context
	 * @param clazz
	 */
	public static void startActivity(Class<?> clazz) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		context.startActivity(intent);
	}

	/**
	 * 页面跳转
	 * 
	 * @param context
	 * @param clazz
	 */
	public static void startActivity(Context context, Class<?> clazz, String key, String value) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		intent.putExtra(key, value);
		context.startActivity(intent);
	}

	/**
	 * 判断gps是否开启的方法
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isGPSOn(Context context) {
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		boolean GPS_status = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		return GPS_status;
	}

	/**
	 * 开启或关闭gps
	 * @param context
	 * @return
	 */
	public static void openGPS(Context context) {
		Intent gpsIntent = new Intent();
		gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
		gpsIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(context, 0, gpsIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 检查网络链接的方法
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean checkNetWork(Context activity) {
		ConnectivityManager cwjManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取设备唯一标示（UUID）
	 * 
	 * @param context
	 * @return
	 */
	public static String getUUID(Context context) {

		if (uuid == null) {
			final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			final String tmDevice, tmSerial, androidId;
			tmDevice = "" + tm.getDeviceId();
			tmSerial = "" + tm.getSimSerialNumber();
			androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
			UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
			String uniqueId = deviceUuid.toString();
			uuid = MD5Util.MD5(uniqueId);
		}
		return uuid;
	}

	/**
	 * 上传文件的方法
	 * 
	 * @param actionUrl
	 * @param fileName
	 * @param uploadFile
	 */
	public static void uploadFile(String actionUrl, String fileName, File uploadFile) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; " + "name=\"useravatars\";filename=\"" + fileName + "\"" + end);
			ds.writeBytes(end);
			FileInputStream fStream = new FileInputStream(uploadFile);
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			while ((length = fStream.read(buffer)) != -1) {
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			fStream.close();
			ds.flush();
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			ds.close();
			if (con.getResponseCode() != 200)
				throw new RuntimeException("请求url失败");
			InputStream cis = con.getInputStream();// 获取返回数据
			String result = readData(cis, "UTF-8");
			L.d("tag", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String readData(InputStream inSream, String charsetName) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inSream.close();
		return new String(data, charsetName);
	}

	/*
	 * 检查手机网络连接类型 获取当前的网络状态 -1：没有网络 1：WIFI网络 2：wap网络 3：net网络
	 */
	public static int getNetWorkType(Context context) {
		int netType = -1;
		final int CMNET = 3, CMWAP = 2, WIFI = 1;
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();

		if (nType == ConnectivityManager.TYPE_MOBILE) {
			L.e("networkInfo.getExtraInfo()", "networkInfo.getExtraInfo() is " + networkInfo.getExtraInfo());
			if (networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet")) {

				netType = CMNET;
			} else {
				netType = CMWAP;
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = WIFI;
		}
		return netType;
	}

	/**
	 * 下载的帮助方法
	 * 
	 * @param dlm
	 *            android自带的下载帮助类
	 * @param u
	 *            下载地址
	 * @param fileName
	 *            文件名
	 */
	@SuppressLint("NewApi")
	public static void DownloadFile(DownloadManager dlm, String u, String fileName) {
		Uri uri = Uri.parse(u);
		Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs();
		dlm.enqueue(new DownloadManager.Request(uri)
				.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE).setAllowedOverRoaming(false)
				.setTitle("Demo").setDescription("Something useful. No, really.")
				.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName));

	}

	/**
	 * 设置Listview高度动态根据ITEM的个数
	 * 
	 * @param listView
	 *            传入一个LISTVIEW
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 下载好了APK跳转到安装程序的方法
	 * 
	 * @param activity
	 *            启动的activity
	 * @param path
	 *            软件下载的路径的文件包
	 */
	public static void tackToInstall(Activity activity, File file) {
		Intent install = new Intent();
		install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		install.setAction(android.content.Intent.ACTION_VIEW);
		String uString = "file://" + file.getPath();
		install.setDataAndType(Uri.parse(uString), "application/vnd.android.package-archive");
		activity.startActivity(install);// 安装
	}

	/**
	 * 关闭键盘
	 * 
	 * @param con
	 */
	public static void closeKeyboard(Activity con) {
		View view = con.getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 到计时
	 * 
	 * @param handler
	 *            用来接收到计时回传数据
	 * @param start
	 *            从哪个数据开始
	 * @param end
	 *            结束数字
	 * @return
	 */
	public static synchronized boolean countdown(int start, final int end, final FRequestCallBack frcb) {
		boolean flat = false;
		if (start != end) {
			final int times = Math.abs(start - end);
			temS = start;
			if (start > end) {// from big to small
				temFlat = -1;
			} else { // from small to big
				temFlat = 1;
			}
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i <= times; i++) {
						if (!getcountdownFlat()) {
							break;
						}
						frcb.onLoading(end, temS);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						temS = temS + temFlat;
					}
					frcb.onSuccess(temS);
				}
			};
			new Thread(runnable).start();
			flat = true;
		}
		return flat;
	}

	private static boolean getcountdownFlat() {
		synchronized (b) {
			return countdownFlat;
		}
	}

	/**
	 * 根据key把SharedPreferences中的Stirng类型的value取出
	 * 
	 * @param key
	 * @return
	 */
	public static String getSpString(String key) {
		String value = "";
		if (context == null) {
			L.i("UtilAndroid,getStringValue() con is null");
			return value;
		}
		if (sp == null) {
			sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
		}
		value = sp.getString(key, value);
		return value;
	}

	/**
	 * 根据key把SharedPreferences中的int类型value取出
	 * 
	 * @param key
	 * @return
	 */
	public static int getSpInt(String key) {
		int value = 0;
		if (context == null) {
			L.i("UtilAndroid,getintValue() con is null");
			return value;
		}
		if (sp == null) {
			sp = context.getSharedPreferences(spName, 0);
		}
		value = sp.getInt(key, value);
		return value;
	}

	/**
	 * 根据key把SharedPreferences中的boolean类型value取出
	 * 
	 * @param key
	 * @return
	 */
	public static boolean getSpBoolean(String key) {
		boolean value = false;
		if (context == null) {
			L.i("UtilAndroid,getbooleanValue() con is null");
			return value;
		}
		if (sp == null) {
			sp = context.getSharedPreferences(spName, 0);
		}
		value = sp.getBoolean(key, value);
		return value;
	}

	/**
	 * 保存boolean信息
	 * 
	 * @param name
	 * @param value
	 */
	public static void saveSpValue(String name, boolean value) {
		if (context == null) {
			L.i("UtilAndroid,saveStringValue() con is null: " + name);
			return;
		}
		if (sp == null) {
			sp = context.getSharedPreferences(spName, 0);
		}
		editor = sp.edit();
		editor.putBoolean(name, value);
		editor.commit();
	}

	/**
	 * 保存Int信息
	 * 
	 * @param name
	 * @param value
	 */
	public static void saveSpValue(String name, int value) {
		if (context == null) {
			L.i("UtilAndroid,save() con is null: " + name);
			return;
		}
		if (sp == null) {
			sp = context.getSharedPreferences(spName, 0);

		}
		editor = sp.edit();
		editor.putInt(name, value);
		editor.commit();
	}

	/**
	 * 保存SHAREPREFENCE 的string类型的信息
	 * 
	 * @param name
	 * @param value
	 */
	public static void saveSpValue(String name, String value) {
		if (context == null) {
			L.i("UtilAndroid,saveStringValue() con is null: " + name);
			return;
		}
		if (sp == null) {
			sp = context.getSharedPreferences(spName, 0);

		}
		editor = sp.edit();
		editor.putString(name, value);
		editor.commit();
	}

	/**
	 * 通过经纬度计算距离
	 * 
	 * @param lat_a
	 * @param lng_a
	 * @param lat_b
	 * @param lng_b
	 * @return
	 */
	public static double getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
		double radLat1 = rad(lat_a);
		double radLat2 = rad(lat_b);
		double a = radLat1 - radLat2;
		double b = rad(lng_a) - rad(lng_b);
		double distance = 2 * Math
				.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		distance = distance * EARTH_RADIUS;
		distance = Math.round(distance * 10000) / 10000;
		return distance;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 实体类转Map
	 * 
	 * @param obj
	 * @return
	 */
	public static HashMap<String, Object> ConvertObjToMap(Object obj) {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null)
			return null;
		Field[] fields_father = obj.getClass().getSuperclass().getDeclaredFields();
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields_father.length; i++) {

				Field f = obj.getClass().getSuperclass().getDeclaredField(fields_father[i].getName());
				f.setAccessible(true);
				Object o = f.get(obj);
				reMap.put(fields_father[i].getName(), o);
			}
			for (int i = 0; i < fields.length; i++) {
				Field f = obj.getClass().getDeclaredField(fields[i].getName());
				f.setAccessible(true);
				Object o = f.get(obj);
				reMap.put(fields[i].getName(), o);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reMap;
	}

	/**
	 * bitmaputils封装方法，如果没有本地缓存的情况下获取网络图片
	 * 
	 * @param context
	 * @param v
	 * @param uri
	 */
	public static void getCacheBitmap(Context context, ImageView v, String uri) {
		BitmapDisplayConfig config = new BitmapDisplayConfig();
		config.setLoadingDrawable(context.getResources().getDrawable(R.drawable.default_user));
		// config.setBitmapMaxSize(new BitmapSize(200, 200));
		config.setBitmapConfig(Config.RGB_565);
		config.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(context));
		// bitmapUtils.configDefaultDisplayConfig(config)
		File f = SysApplication.getBitmapInstance(context).getBitmapFileFromDiskCache(uri);
		if (f != null) {
			// LogUtil.d("get bitmap from local!!! path="+f.getPath());
			Bitmap bit = BitmapFactory.decodeFile(f.getPath());
			v.setImageBitmap(bit);
		} else {
			// LogUtil.d("get bitmap from net!!!");
			SysApplication.getBitmapInstance(context).display(v, uri, config);
		}

	}

	/**
	 * bitmaputils封装方法，如果没有本地缓存的情况下获取网络图片，可以传入默认图片
	 * 
	 * @param context
	 * @param v
	 * @param uri
	 */
	public static void getCacheBitmap(Context context, ImageView v, String uri, int defaultpic) {
		BitmapDisplayConfig config = new BitmapDisplayConfig();
		config.setLoadingDrawable(context.getResources().getDrawable(defaultpic));
		config.setLoadFailedDrawable(context.getResources().getDrawable(defaultpic));
		config.setBitmapMaxSize(new BitmapSize(200, 200));
		config.setBitmapConfig(Config.RGB_565);
		config.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(context));
		SysApplication.getBitmapInstance(context).configDefaultDisplayConfig(config);
		File f = SysApplication.getBitmapInstance(context).getBitmapFileFromDiskCache(uri);
		if (f != null) {
			Bitmap bit = BitmapFactory.decodeFile(f.getPath());
			v.setImageBitmap(bit);
		} else {
			SysApplication.getBitmapInstance(context).display(v, uri, config);
		}

	}

	/**
	 * bitmaputils封装方法，如果没有本地缓存的情况下获取网络图片，可以传入默认图片
	 * 
	 * @param context
	 * @param v
	 * @param uri
	 */
	public static void getCacheBitmap(Context context, ImageView v, String uri, int defaultpic, BitmapLoadCallBack<View> callBack) {
		BitmapDisplayConfig config = new BitmapDisplayConfig();
		config.setLoadingDrawable(context.getResources().getDrawable(defaultpic));
		config.setLoadFailedDrawable(context.getResources().getDrawable(defaultpic));
		config.setBitmapMaxSize(new BitmapSize(200, 200));
		config.setBitmapConfig(Config.RGB_565);
		config.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(context));
		SysApplication.getBitmapInstance(context).configDefaultDisplayConfig(config);
		File f = SysApplication.getBitmapInstance(context).getBitmapFileFromDiskCache(uri);
		if (f != null) {
			Bitmap bit = BitmapFactory.decodeFile(f.getPath());
			v.setImageBitmap(bit);
		} else {
			SysApplication.getBitmapInstance(context).display(v, uri, config, callBack);
		}
	}
	
	/**获取手机的存储位置,优先从SD卡上获取
	 * @param context
	 * @param fileName
	 * @param createFlat  是否创建
	 * @returnString
	 */
	public static String getAbsolutePath(Context context,String fileName,boolean createFlat){
		String path = "";
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sxgou" + File.separator;
		} else {
			if (context != null) {
				path = context.getFilesDir().getAbsolutePath() + File.separator + "sxgou" + File.separator;
			}
		}
		if(createFlat){
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			File name = new File(path+fileName);
			if(!name.exists()){
				try {
					name.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return path + fileName;
	}
	
	/**
	 * 横竖屏判断
	 * @param context
	 */
	public static boolean isScreenOriatationPortrait(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}
	
	/**
	 * 判断字符串是否为空
	 */
	public static boolean isStringNull(String s){
		if(s == null||"".equals(s)){
			return true;
		}else{
			return false;
		}	
	}
	
}
